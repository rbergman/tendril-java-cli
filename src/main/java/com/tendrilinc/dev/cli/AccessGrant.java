package com.tendrilinc.dev.cli;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessGrant
{
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private String expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    private String scope;

    private String route;

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getTokenType()
    {
        return tokenType;
    }

    public void setTokenType(String tokenType)
    {
        this.tokenType = tokenType;
    }

    public String getExpiresIn()
    {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn)
    {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String scope)
    {
        this.scope = scope;
    }

    public String getRoute()
    {
        return route;
    }

    public void setRoute(String route)
    {
        this.route = route;
    }
}
