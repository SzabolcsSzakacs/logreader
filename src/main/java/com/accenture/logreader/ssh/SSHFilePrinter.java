package com.accenture.logreader.ssh;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SSHFilePrinter {

    private Session session;

    public SSHFilePrinter(Session session) {
        this.session = session;
    }

    public String getString(String filePath, String searchText) throws JSchException, IOException {
        ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
        channelExec.setCommand(getCommand(filePath, searchText));
        channelExec.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(channelExec.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line).append("\n");
        }

        channelExec.disconnect();
        return response.toString();
    }

    private String getCommand(String fileAbsolutePath, String searchText) {
        String folder = getFolder(fileAbsolutePath);
        String file = getFilename(fileAbsolutePath);
        String command = "cd " + folder + " && grep '" + searchText + "' " + file;
        System.out.println(command);
        return command;
    }

    private String getFolder(String fileAbsolutePath) {
        return fileAbsolutePath.substring(0, fileAbsolutePath.lastIndexOf("/"));
    }

    private String getFilename(String fileAbsolutePath) {
        return fileAbsolutePath.substring(fileAbsolutePath.lastIndexOf("/") + 1);
    }
}