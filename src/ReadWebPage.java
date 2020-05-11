import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Class with a static method to read a web page as a String.
 */
public class ReadWebPage {
    /**
     * @param URLname
     * @return String containing the web page content.
     */
    public static String readWebPage(String URLname) {
        // connect to URL and read web page as a String
        StringBuilder sb = null;
        try {
            URL url = new URL(URLname);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
        } catch (Exception e) {
            System.out.println("Error: exception while attempting to read from URL " + URLname);
            System.out.println(e);
            System.exit(3);
        }
        return sb.toString().toLowerCase();
    }
}