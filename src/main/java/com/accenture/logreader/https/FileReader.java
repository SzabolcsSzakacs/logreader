package com.accenture.logreader.https;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.URL;

public class FileReader {

    public static void read() {
        try {
            URL url = new URL("https://logviewer.cz.infra/cn75b7az/mcd01-cn75b7az-log/app/cn/mcd-ws/cn75b7/logs/audit.log");
            Authenticator.setDefault(new MyAuthenticator("logviewer", "logviewer"));

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.connect();
            InputStream is = connection.getInputStream();
            System.out.println(parse(is));
            connection.disconnect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static String parse(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder contentBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            contentBuilder.append(line);
        }
        return contentBuilder.toString();
    }
}