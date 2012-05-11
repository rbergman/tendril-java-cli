package com.tendrilinc.dev.cli;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class PasswordOAuthTest
{
    @Test
    public void testPasswordOAuth()
        throws IOException
    {
        AccessGrant auth = PasswordOAuth.login("kim.deal@tendril.com", "password");
        assertTrue(auth.getAccessToken() != null);
    }
}
