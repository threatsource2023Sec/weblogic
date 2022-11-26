package utils;

import java.net.InetAddress;
import java.net.ServerSocket;

public class BindTest {
   static String usage = "java BindTest -ip 127.0.0.1 -port 7001";
   static String[] args;
   int port;
   String ipAddrString;

   public static void main(String[] args) {
      BindTest bt = new BindTest();
      BindTest.args = args;
      bt.parseArgs();
      bt.bind();
   }

   public void bind() {
      InetAddress bindAddr = null;

      try {
         bindAddr = InetAddress.getByName(this.ipAddrString);
      } catch (Exception var4) {
         this.exit(var4, "\nError getting Address : " + this.ipAddrString);
      }

      try {
         new ServerSocket(this.port, 50, bindAddr);
      } catch (Exception var3) {
         this.exit(var3, "\nException binding to port : " + this.port + " on : " + this.ipAddrString);
      }

      System.out.println("Success!  Bound to " + this.ipAddrString + ":" + this.port);
   }

   public void parseArgs() {
      if (args == null || args.length == 0 || args.length < 4) {
         this.exit(usage);
      }

      for(int i = 0; i < args.length; ++i) {
         try {
            if (args[i].equals("-port")) {
               int temp = 0;

               try {
                  ++i;
                  temp = Integer.parseInt(args[i]);
               } catch (Exception var4) {
                  this.exit("Error parsing port arg : " + var4.toString() + "\n" + usage);
               }

               this.port = temp;
            } else if (args[i].equals("-ip")) {
               ++i;
               this.ipAddrString = args[i];
            }
         } catch (Exception var5) {
            this.exit("Error parsing args : " + var5.toString() + "\n" + usage);
         }
      }

   }

   void exit() {
      this.exit((String)null);
   }

   void exit(String say) {
      this.exit((Throwable)null, say);
   }

   void exit(Throwable t, String say) {
      if (say != null) {
         System.out.println(say);
         System.out.println("");
      }

      if (t != null) {
         t.printStackTrace();
      }

      System.exit(-1);
   }
}
