package utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class netAddresses {
   static boolean verbose = false;
   private boolean success = false;

   public static void main(String[] argv) {
      verbose = argv.length > 0 && argv[0].startsWith("-v");

      try {
         InetAddress[] addresses = null;
         InetAddress localhost = null;

         try {
            localhost = InetAddress.getLocalHost();
         } catch (UnknownHostException var8) {
            System.out.println("Couldn't even run InetAddress.getLocalHost(): " + var8 + "\nThere's no good way to work around that.");
            System.exit(-1);
         }

         String name;
         try {
            String localhostName = localhost.getHostName();
            say("Got host name: " + localhostName);
            addresses = InetAddress.getAllByName(localhostName);
            say("Found " + addresses.length + " addresse(s) for " + localhostName);
         } catch (UnknownHostException var7) {
            say("Couldn't determine host addresse(s) by name, trying by address.");

            try {
               name = localhost.getHostAddress();
               say("Got host addr: " + name);
               addresses = InetAddress.getAllByName(name);
               say("Found " + addresses.length + " addresse(s) for " + name);
            } catch (UnknownHostException var6) {
               System.out.println("Couldn't determine host addresse(s)");
               System.exit(-1);
            }
         }

         for(int i = 0; i < addresses.length; ++i) {
            name = addresses[i].getHostName();
            String ip = addresses[i].getHostAddress();
            System.out.println("Host " + name + " is assigned IP address: " + ip);
         }
      } catch (Exception var9) {
         internalError(var9);
      }

   }

   static void say(String msg) {
      if (verbose) {
         System.out.println("* " + msg);
      }

   }

   static void internalError(Throwable t) {
      System.out.println("Internal error:\nPlease mail the following stack trace to <support@weblogic.com>\n");
      t.printStackTrace(System.out);
   }
}
