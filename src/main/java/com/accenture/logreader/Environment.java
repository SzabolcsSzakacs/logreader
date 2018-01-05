package com.accenture.logreader;

public enum Environment {

    B7("sstone.cz.infra", "hcidev"),
    C7("sstone.cn.infra", "hcidev");

    private String host;
    private String user;

    Environment(String host, String user) {
        this.host = host;
        this.user = user;
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }
}
