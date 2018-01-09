package com.accenture.logreader.https;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {
    final PasswordAuthentication authentication;

    public MyAuthenticator(String userName, String password) {
        authentication = new PasswordAuthentication(userName, password.toCharArray());
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return authentication;
    }
}