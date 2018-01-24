package com.accenture.logreader.console;

import com.accenture.logreader.Environment;
import com.accenture.logreader.https.FileReader;
import com.accenture.logreader.ssh.SSHConnector;
import com.accenture.logreader.ssh.SSHFilePrinter;
import com.jcraft.jsch.Session;

public class ConsoleApplication {

    private String[] args;

    public ConsoleApplication(String[] args) {
        this.args = args;
    }

    public void start() {
        Environment env = resolveEnvironment(args, Environment.C7);
        String file = resolveFilename(args, "/mnt/cndev/mcd01-cn76c1-log/app/cn/mcd-ws/cn76c1/logs/app.log");
        String correlationId = resolveCorrelationId(args, "2018-01-24 19");
        getLogSsh(env, file, correlationId);
        //getLogHttps(env, file);
    }

    private void getLogHttps(Environment env, String file) {
        FileReader.read();
    }

    private Environment resolveEnvironment(String[] args, Environment _default) {
        final String[] ENVIRONMENT_ARGS = {"-e", "-env", "-environment"};
        for (int i = 0; i < args.length; i++) {
            if (contains(ENVIRONMENT_ARGS, args[i])) {
                try {
                    return Environment.valueOf(args[i + 1]);
                } catch (IllegalArgumentException ex) {
                    throw new RuntimeException("No such environment: " + args[i + 1]);
                }
            }
        }
        System.out.println("No environment added. Using default (" + _default.name() + ").");
        return _default;
    }

    private String resolveFilename(String[] args, String _default) {
        final String[] FILENAME_ARGS = {"-f", "-file"};
        for (int i = 0; i < args.length; i++) {
            if (contains(FILENAME_ARGS, args[i])) {
                return args[i + 1];
            }
        }
        return _default;
    }

    private String resolveCorrelationId(String[] args, String _default) {
        final String[] FILENAME_ARGS = {"-correlationId", "-cId"};
        for (int i = 0; i < args.length; i++) {
            if (contains(FILENAME_ARGS, args[i])) {
                return args[i + 1];
            }
        }
        return _default;
    }

    private boolean contains(String[] arr, String s) {
        for (String anArr : arr) {
            if (anArr.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private void getLogSsh(Environment env, String file, String correlationId) {
        String prvtKey = "c:\\Users\\szabolcs.szakacs\\Documents\\Homecredit\\private_key.ppk";
        SSHConnector connector = new SSHConnector(env.getHost(), env.getUser(), prvtKey);
        Session session = null;
        try {
            session = connector.connect();
            SSHFilePrinter fileReader = new SSHFilePrinter(session);
            String content = fileReader.getString(file, correlationId);
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.disconnect();
            }
        }
    }
}