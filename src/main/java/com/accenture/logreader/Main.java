package com.accenture.logreader;

import com.accenture.logreader.ssh.SSHConnector;
import com.accenture.logreader.ssh.SSHFileReader;
import com.jcraft.jsch.Session;

public class Main {

    public static void main(String[] args) {
        try {
            Environment env = resolveEnvironment(args, Environment.C7);
            String file = resolveFilename(args, "/mnt/cndev/mcd01-cn76c1-log/app/cn/mcd-ws/cn76c1/logs/audit.log");
            getLog(env, file);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static Environment resolveEnvironment(String[] args, Environment _default) {
        final String[] ENVIRONMENT_ARGS = {"-e", "-env", "-environment"};
        for (int i = 0; i < args.length; i++) {
            if (contains(ENVIRONMENT_ARGS, args[i])) {
                return Environment.valueOf(args[i + 1]);
            }
        }
        return _default;
    }

    private static String resolveFilename(String[] args, String _default) {
        final String[] FILENAME_ARGS = {"-f", "-file"};
        for (int i = 0; i < args.length; i++) {
            if (contains(FILENAME_ARGS, args[i])) {
                return args[i + 1];
            }
        }
        return _default;
    }

    private static boolean contains(String[] arr, String s) {
        for (String anArr : arr) {
            if (anArr.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private static void getLog(Environment env, String file) {
        String prvtKey = "c:\\Users\\szabolcs.szakacs\\Documents\\Homecredit\\private_key.ppk";
        SSHConnector connector = new SSHConnector(env.getHost(), env.getUser(), prvtKey);
        Session session = null;
        try {
            session = connector.connect();
            SSHFileReader fileReader = new SSHFileReader(session);
            long start = System.currentTimeMillis();
            String content = fileReader.getString(file);
            long end = System.currentTimeMillis();
            System.out.println(content);
            System.out.println("[Downloaded in " + ((end - start) / 1000) + " sec]");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.disconnect();
            }
        }
    }
}
