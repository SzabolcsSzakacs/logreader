package com.accenture.logreader.ssh;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class SSHFileReader {

    private Session session;

    public SSHFileReader(Session session) {
        this.session = session;
    }

    public String getString(String filePath, boolean a) throws SftpException, JSchException, IOException {
        ChannelSftp sftp = (ChannelSftp) session.openChannel("sftp");
        sftp.connect();
        InputStream stream = sftp.get(filePath);
        String content;
        if (a)
            content = parse(stream, 0);
        else
            content = parse(stream, getSkip(sftp, filePath));
        sftp.disconnect();
        return content;
    }

    private long getSkip(ChannelSftp sftp, String filePath) throws SftpException {
        long skip = getFileSizeInChar(sftp, filePath) - 10000;
        return skip < 0 ? 0 : skip;
    }

    private long getFileSizeInChar(ChannelSftp sftp, String filePath) throws SftpException {
        return getFileSizeInByte(sftp, filePath);
    }

    private long getFileSizeInByte(ChannelSftp sftp, String filePath) throws SftpException {
        Vector ls = sftp.ls(filePath);
        ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) ls.get(0);
        return entry.getAttrs().getSize();
    }

    private String parse(InputStream is, long skip) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        if (skip > 0) {
            System.out.println(skip + " characters have to skip.");
            System.out.println(br.skip(skip) + " characters skipped.");
        }
        StringBuilder contentBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            contentBuilder.append(line);
        }
        long end = System.currentTimeMillis();
        System.out.println("[Downloaded in " + ((end - start) / 1000) + " sec]");
        return contentBuilder.toString();
    }
}