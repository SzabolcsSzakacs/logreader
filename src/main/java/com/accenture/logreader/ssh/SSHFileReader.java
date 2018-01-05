package com.accenture.logreader.ssh;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SSHFileReader {

    private Session session;

    public SSHFileReader(Session session) {
        this.session = session;
    }

    public String getString(String filePath) throws SftpException, JSchException, IOException {
        ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
        sftp.connect();
        InputStream stream = sftp.get(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        StringBuilder contentBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            contentBuilder.append(line);
        }
        sftp.disconnect();
        return contentBuilder.toString();
    }
}
