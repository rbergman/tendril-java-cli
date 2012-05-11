package com.tendrilinc.dev.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PasswordOAuth
{
    private static final String HOST = "https://dev.tendrilinc.com";
    private static final String PATH = "/oauth/access_token";
    private static final String URL = HOST + PATH;

    public static AccessGrant login(String username, String password)
        throws IOException
    {
        Properties config = new Properties();
        InputStream in = PasswordOAuth.class.getClassLoader().getResourceAsStream("META-INF/application.properties");
        if (in == null) throw new IOException("Unable to load application.properties file");
        config.load(in);

        HttpClient client = getHttpClient();
        HttpPost post = new HttpPost(URL);

        post.addHeader("Accept", "application/json");
        post.addHeader("Content-Type", "application/x-www-form-urlencoded");
        post.addHeader("X-Route", config.getProperty("provider.route", "sandbox"));

        List<NameValuePair> params = new ArrayList<NameValuePair>(6);
        params.add(new BasicNameValuePair("grant_type", "password"));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("client_id", config.getProperty("consumer.id")));
        params.add(new BasicNameValuePair("client_secret", config.getProperty("consumer.secret")));
        params.add(new BasicNameValuePair("scope", config.getProperty("consumer.scope", "account offline_access")));
        HttpEntity entity = new UrlEncodedFormEntity(params);
        post.setEntity(entity);

        HttpResponse res = client.execute(post);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(res.getEntity().getContent(), AccessGrant.class);
    }

    private static HttpClient getHttpClient()
    {
        // there's something amiss with our cert, so create a client that won't barf on it
        HttpClient base = new DefaultHttpClient();
        ClientConnectionManager ccm;

        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException { }
                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException { }
                public X509Certificate[] getAcceptedIssuers() { return null; }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return new DefaultHttpClient(ccm, base.getParams());
    }
}
