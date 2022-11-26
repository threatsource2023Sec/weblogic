package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public final class host2ior {
   public static final String servletName = "bea_wls_internal/getior";

   public static void main(String[] args) {
      if (args.length < 2) {
         System.out.println("Usage:  java utils.host2ior hostName port");
         System.out.println("Example:  java utils.host2ior localhost 7001");
      } else {
         String hostname = args[0];
         int port = true;

         int port;
         try {
            port = Integer.parseInt(args[1]);
         } catch (NumberFormatException var6) {
            System.err.println("Could not parse argument as an integer: " + args[1]);
            return;
         }

         try {
            System.out.println(getIOR(hostname, port));
            System.out.println("\nIOR for IIOP/SSL:");
            System.out.println(getIOR(hostname, port, true));
         } catch (MalformedURLException var4) {
            System.err.println("Malformed URL: " + var4);
         } catch (Exception var5) {
            System.err.println(var5);
         }

      }
   }

   public static String getIOR(String hostname, int port) throws MalformedURLException, IOException {
      return getIOR(hostname, port, false);
   }

   public static String getIOR(String hostname, int port, boolean secure) throws MalformedURLException, IOException {
      String urlstr = "http://" + hostname + ":" + port + "/" + "bea_wls_internal/getior" + (secure ? "?secure=true" : "");
      URL url = new URL(urlstr);
      URLConnection urlc = url.openConnection();
      InputStream istr = urlc.getInputStream();
      BufferedReader bufr = new BufferedReader(new InputStreamReader(istr));
      String data = null;
      StringBuffer ior = new StringBuffer();

      while((data = bufr.readLine()) != null) {
         ior.append(data);
      }

      data = ior.toString();
      return data.substring(data.indexOf("IOR:") + 4).trim();
   }
}
