/*
 * Copyright (c) Max Brito
 * License: EUPL-1.2
 */
package utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import utils.www.WebResponse;

/**
 * A set of methods to access the Internet for downloading pages and other
 * useful functions.
 *
 * @author Max Brito
 * @date 10th August 2010 in Coimbra, Portugal.
 */
public class internet {

    public static String userAgent = "Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:40.0) Gecko/20100101 Firefox/40.0",
            userAgentOriginal = "Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:40.0) Gecko/20100101 Firefox/40.0";

    /**
     * Downloads a file from the Internet to the disk
     *
     * @param filename
     * @param URL
     * @copyright Ben Noland
     * @retrieved 2015-08-29
     * @retriever Max Brito
     * @origin
     * http://stackoverflow.com/questions/921262/how-to-download-and-save-a-file-from-internet-using-java
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static void downloadFile(final File filename, final String URL) {
        try {
            BufferedInputStream in;
            FileOutputStream fout;

            in = new BufferedInputStream(new URL(URL).openStream());
            fout = new FileOutputStream(filename);

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
            in.close();
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * <b>Bare Bones Browser Launch for Java</b><br>
     * Utility class to open a web page from a Swing application in the user's
     * default browser.<br>
     * Supports: Mac OS X, GNU/Linux, Unix, Windows XP/Vista/7<br>
     * Example Usage:<code><br> &nbsp; &nbsp; String url =
     * "http://www.google.com/";<br> &nbsp; &nbsp;
     * BareBonesBrowserLaunch.openURL(url);<br></code> Latest Version:
     * <a href="http://www.centerkey.com/java/browser/">www.centerkey.com/java/browser</a><br>
     * Author: Dem Pilafian<br>
     * Public Domain Software -- Free to Use as You Like
     *
     * @version 3.0, February 7, 2010
     */
    static final String[] browsers = {"google-chrome", "firefox", "opera",
        "konqueror", "epiphany", "seamonkey", "galeon", "kazehakase", "mozilla"};
    static final String errMsg = "Error attempting to launch web browser";

    /**
     * Opens the specified web page in the user's default browser
     *
     * @param url A web address (URL) of a web page (ex:
     * "http://www.google.com/")
     */
    public static void openURL(String url) {
        try {  //attempt to use Desktop library from JDK 1.6+ (even if on 1.5)
            Class<?> d = Class.forName("java.awt.Desktop");
            d.getDeclaredMethod("browse", new Class[]{java.net.URI.class}).invoke(
                    d.getDeclaredMethod("getDesktop").invoke(null),
                    new Object[]{java.net.URI.create(url)});
            //above code mimics:
            //   java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (Exception ignore) {  //library not available or failed
            String osName = System.getProperty("os.name");
            try {
                if (osName.startsWith("Mac OS")) {
                    Class.forName("com.apple.eio.FileManager").getDeclaredMethod(
                            "openURL", new Class[]{String.class}).invoke(null,
                                    new Object[]{url});
                } else if (osName.startsWith("Windows")) {
                    Runtime.getRuntime().exec(
                            "rundll32 url.dll,FileProtocolHandler " + url);
                } else { //assume Unix or Linux
                    boolean found = false;
                    for (String browser : browsers) {
                        if (!found) {
                            found = Runtime.getRuntime().exec(
                                    new String[]{"which", browser}).waitFor() == 0;
                            if (found) {
                                Runtime.getRuntime().exec(new String[]{browser, url});
                            }
                        }
                    }
                    if (!found) {
                        throw new Exception(Arrays.toString(browsers));
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, errMsg + "\n" + e.toString());
            }
        }
    }

    /**
     * Returns the new URL for the cases of HTTP redirection.
     *
     * @param urlOriginal
     * @return null if there is no redirection happening, otherwise return the
     * new URL
     */
    public static String getRedirection(String urlOriginal) {
        String location;
        URL resourceUrl;
        HttpURLConnection conn;

        userAgent = generateHeader();

        try {
            resourceUrl = new URL(urlOriginal);
            conn = (HttpURLConnection) resourceUrl.openConnection();
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestProperty("User-Agent", userAgent);

            switch (conn.getResponseCode()) {
                case HttpURLConnection.HTTP_MOVED_PERM:
                case HttpURLConnection.HTTP_MOVED_TEMP:
                    location = conn.getHeaderField("Location");
                    location = URLDecoder.decode(location, "UTF-8");
//               // check if this is the final redirect
//               String test = getRedirection(location);
//               if(test.hashCode() != location.hashCode()){
//                   return test;
//               }
                    return location;
            }
        } catch (Exception me) {
            return null;
        }
        return null;
    }

    /**
     * Get a given page from an URL address. This method also handles the cases
     * of www redirection to somewhere else.
     *
     * @param url
     * @return
     * @author Nathan Reynolds, https://stackoverflow.com/users/294317/nathan
     * @see https://stackoverflow.com/a/26046079
     * @license CC-BY-SA-3.0
     */
    public static String getWebPage(String url) {
        WebResponse response = getWebPage(url, false);
        return response.getText();
    }

    /**
     * Get a given page from an URL address. This method also handles the cases
     * of www redirection to somewhere else.
     *
     * @param url
     * @param followRedirect
     * @return never null, always a valid object. Returns -1 when having an
     * error
     * @author Nathan Reynolds, https://stackoverflow.com/users/294317/nathan
     * @see https://stackoverflow.com/a/26046079
     * @license CC-BY-SA-3.0
     */
    public static WebResponse getWebPage(String url, boolean followRedirect) {
        // perhaps in the future we can use something like http://goo.gl/03WQp
        // provide a holder for the reply
        String result = "";
        String location; // = address;
        URL resourceUrl, base, next;
        HttpURLConnection conn;
        int responseValue = -1;
        userAgent = generateHeader();
        try {
            while (true) {
                resourceUrl = new URL(url);
                conn = (HttpURLConnection) resourceUrl.openConnection();
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(15000);
                // Make the logic below easier to detect redirections
                conn.setInstanceFollowRedirects(followRedirect);
                conn.setRequestProperty("User-Agent", userAgent);
                // get the respective value from the connection
                responseValue = conn.getResponseCode();

                switch (conn.getResponseCode()) {
                    case HttpURLConnection.HTTP_MOVED_PERM:
                    case HttpURLConnection.HTTP_MOVED_TEMP:
                        location = conn.getHeaderField("Location");
                        location = URLDecoder.decode(location, "UTF-8");
                        base = new URL(url);
                        next = new URL(base, location);  // Deal with relative URLs
                        url = next.toExternalForm();
                        continue;
                }
                break;
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // Process each line.
                result = result.concat(inputLine + "\n");
            }
            in.close();
        } catch (Exception me) {
            //logError("Error I231: " + text.getExceptionAsText(me));
            result = "";
        }

        // if the default way didn't work, try now with normal redirect
        if (followRedirect == false && utils.text.isEmpty(result)) {
            return getWebPage(url, true);
        }
        WebResponse response = new WebResponse(responseValue, result);
        return response;
    }

    private static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen 
            e.printStackTrace();
        }
    }

    /**
     * Get a given page from an URL address. This method also handles the cases
     * of www redirection to somewhere else.
     *
     * @param url
     * @param file
     * @param followRedirect
     * @return never null, always a valid object. Returns -1 when having an
     * error
     * @author Nathan Reynolds, https://stackoverflow.com/users/294317/nathan
     * @throws java.lang.Exception
     * @see https://stackoverflow.com/a/26046079
     * @license CC-BY-SA-3.0
     */
    public static WebResponse getWebPageFile(String url, File file, boolean followRedirect) throws Exception {
        // perhaps in the future we can use something like http://goo.gl/03WQp
        // provide a holder for the reply
        String result = "";
        String location; // = address;
        URL resourceUrl, base, next;
        HttpsURLConnection conn;
        //HttpsURLConnection conn;
        int responseValue = -1;
        try {
            while (true) {

                resourceUrl = new URL(url);
                conn = (HttpsURLConnection) resourceUrl.openConnection();
                conn.setConnectTimeout(15000);
                conn.setReadTimeout(15000);
                // Make the logic below easier to detect redirections
                conn.setInstanceFollowRedirects(followRedirect);
                //conn.setRequestProperty("User-Agent", userAgent);

                conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
                conn.setRequestProperty("Sec-Fetch-Dest", "document");
                conn.setRequestProperty("Sec-Fetch-Mode", "navigate");
                conn.setRequestProperty("Sec-Fetch-Site", "none");
                conn.setRequestProperty("Sec-Fetch-User", "?1");
                conn.setRequestProperty("Host", "ptchan.org:443");
                conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
                conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");
                conn.setRequestProperty("Cookie", "__cfduid:d9a2be9d7646e57f1e87b5a921db79b4c1598377890");

                // get the respective value from the connection
                responseValue = conn.getResponseCode();

                switch (conn.getResponseCode()) {
                    case HttpURLConnection.HTTP_MOVED_PERM:
                    case HttpURLConnection.HTTP_MOVED_TEMP:
                        location = conn.getHeaderField("Location");
                        location = URLDecoder.decode(location, "UTF-8");
                        base = new URL(url);
                        next = new URL(base, location);  // Deal with relative URLs
                        url = next.toExternalForm();
                        continue;
                }
                break;
            }

            BufferedInputStream in;
            FileOutputStream fout;

            in = new BufferedInputStream(new URL(url).openStream());
            fout = new FileOutputStream(file);

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
            in.close();
            fout.close();

//            String inputLine;
//            while ((inputLine = in.readLine()) != null) {
//                // Process each line.
//                result = result.concat(inputLine + "\n");
//            }
//            in.close();
        } catch (Exception me) {
            System.out.println("Download error: " + me.getLocalizedMessage());
            //logError("Error I231: " + text.getExceptionAsText(me));
            result = "";
        }

        // if the default way didn't work, try now with normal redirect
        if (followRedirect == false && utils.text.isEmpty(result)) {
            return getWebPage(url, true);
        }
        WebResponse response = new WebResponse(responseValue, result);
        return response;
    }

    /**
     * Generate a new header
     *
     * @return
     */
    public static String generateHeader() {
        // "Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:40.0) Gecko/20100101 Firefox/40.0",
        String text = utils.internet.userAgentOriginal;

        int newVersion = utils.math.random(40, 80);
        int newRevision = utils.math.random(25, 50);

        String[] browsersText = new String[]{
            "Firefox",
            "Chrome",
            "IE"
        };

        String[] os = new String[]{
            "X11; Fedora; Linux",
            "X11; Ubuntu; Linux",
            "MacOSX",
            "Windows"
        };

        int newBrowser = utils.math.random(0, browsersText.length - 1);
        int newOS = utils.math.random(0, os.length - 1);

        text = text.replace("rv:40.0", "rv:" + newRevision + ".0");
        text = text.replace("Firefox/40.0", "Firefox/" + newVersion + ".0");
        text = text.replace("Firefox", browsersText[newBrowser]);
        text = text.replace("X11; Fedora; Linux x86_64", os[newOS]);

        return text;
    }

    public static void downloadUsingWget(String url, File file) {
        String[] command = new String[]{
            "wget",
            "\"" + url + "\"",
            "-O " + file.getPath()
        };
        platform.executeCommand(command);
    }

    private void log(String gender, String message) {
        System.out.println("internet [" + gender + "] " + message);
    }

    /**
     * Do a threaded getWebPage to prevent congestions. This method is not
     * working as originally intended but at least is still useful for making
     * requests that do not require a reply back.
     *
     * @param URL
     */
    public static void threadedWebGet(String URL) {
        // start a new thread for this purpose
        WebGetThread a = new WebGetThread(URL);
        a.start();
    }

    // derived from http://stackoverflow.com/questions/8765578/get-local-ip-address-without-connecting-to-the-internet
    static public String getLocalIP() {
        String result = "";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface current = interfaces.nextElement();
                //System.out.println(current);
                if (!current.isUp() || current.isLoopback() || current.isVirtual()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = current.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress current_addr = addresses.nextElement();
                    if (current_addr.isLoopbackAddress()) {
                        continue;
                    }
                    if (current_addr instanceof Inet4Address) {
                        result = result.concat(current_addr.getHostAddress() + "\n");
                    }
                    //else if (current_addr instanceof Inet6Address)
                    // System.out.println(current_addr.getHostAddress());
                    //System.out.println(current_addr.getHostAddress());
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * Tries to reach some machine
     * @param ipAddress 
     * @return  
     */
    @SuppressWarnings("UseSpecificCatch")
    public static boolean sendPingRequest(String ipAddress){
        try {
            InetAddress target = InetAddress.getByName(ipAddress);
            System.out.println("Sending Ping Request to " + ipAddress);
            return target.isReachable(5000);
        } catch (Exception ex) {
            Logger.getLogger(internet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Checks if the network adapters are connected, it does not check that we
     * can connect to the Internet.
     *
     * @return
     */
    public static boolean checkThatNetworkIsAvailable() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface interf = interfaces.nextElement();
                if (interf.isUp() && !interf.isLoopback()) {
                    List<InterfaceAddress> adrs = interf.getInterfaceAddresses();
                    for (InterfaceAddress adr : adrs) {
                        InetAddress inadr = adr.getAddress();
                        if (inadr instanceof Inet4Address) {
                            return true;
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(internet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}

class WebGetThread extends Thread {

    private String URL = "";  // the target URL address

    /**
     * Public constructor
     */
    public WebGetThread(String URL) {
        this.URL = URL;
    }

    @Override
    public void run() {
        // get the page onto a string
        utils.internet.getWebPage(URL);
    }

}
