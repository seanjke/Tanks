package Module;

import java.io.*;
import java.net.URL;

/**
 * Created by Hank on 20.06.15.
 */
public class OpenFile {

    public static String loadAsString(URL url) {
        StringBuilder result = new StringBuilder();
        try {
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);
            String buffer = "";
            while ((buffer = reader.readLine()) != null) {
                result.append(buffer + '\n');
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}


