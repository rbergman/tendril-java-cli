package com.tendrilinc.dev.cli;

import java.io.Console;

public class App
{
    public static void main( String[] args )
        throws Exception
    {
        if (System.console() == null) {
            System.err.println("Console is unavailable");
            System.exit(1);
        }

        String username = prompt("Username: ", false);
        String password = prompt("Password: ", true);

        System.out.println("Logging in as " + username + "...");
        AccessGrant auth = PasswordOAuth.login(username, password);

        System.out.println("Access Token: " + auth.getAccessToken());
    }

    private static String prompt(String message, boolean secret)
    {
        String line;
        Console console = System.console();

        if (secret) {
            line = new String(console.readPassword("%s", message));
        }
        else {
            line = console.readLine("%s", message);
        }

        if (line == null || line.trim().length() == 0) {
            System.err.println("You must enter a non-trivial value; please try again.");
            prompt(message, secret);
        }

        return line;
    }
}
