package com.accenture.logreader.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SSHConnector {

    private String host;
    private String user;
    private String prvtKey;

    public SSHConnector(String host, String user, String prvtKey) {
        this.host = host;
        this.user = user;
        this.prvtKey = prvtKey;
    }

    public Session connect() throws JSchException {
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        jsch.addIdentity(prvtKey);
        Session session = jsch.getSession(user, host, 22);
        session.setConfig(config);
        session.connect();
        return session;
    }
}
