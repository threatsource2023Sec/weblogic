package utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Date;
import weblogic.platform.VM;

public class ThreadDumper extends Thread {
   static String addr = null;
   static int port = -1;
   static InetAddress group = null;
   static MulticastSocket sock = null;
   static VM vm;
   static boolean disabled = true;
   static boolean alreadyInit = false;
   static transient boolean shutdownSignaled = false;
   static ThreadDumper staticTD = null;

   public static synchronized void init(boolean getVM) {
      if (!alreadyInit) {
         alreadyInit = true;
         if (getVM) {
            vm = VM.getVM();
         }

         addr = System.getProperty("weblogic.debug.dumpThreadAddr", (String)null);

         try {
            port = Integer.decode(System.getProperty("weblogic.debug.dumpThreadPort", "-1"));
         } catch (Exception var5) {
         }

         if (addr != null && port != -1) {
            try {
               group = InetAddress.getByName(addr);
            } catch (UnknownHostException var4) {
               System.out.println("*** " + addr + " is not a valid multicast address");
               var4.printStackTrace();
               return;
            }

            try {
               sock = new MulticastSocket(port);
            } catch (IOException var3) {
               System.out.println("*** Cannot create MultiCastSocket on addr=" + addr + " port=" + port);
               var3.printStackTrace();
               return;
            }

            try {
               sock.joinGroup(group);
            } catch (IOException var2) {
               System.out.println("*** Cannot join group for addr=" + addr + " port=" + port);
               var2.printStackTrace();
               return;
            }

            System.out.println("*** Joined group for addr=" + addr + " port=" + port);
            disabled = false;
         } else {
            System.out.println("Broadcast Thread dumps disabled: must specify weblogic.debug.dumpThreadAddr and weblogic.debug.dumpThreadPort");
         }
      }
   }

   public void informShutdown() {
      signalShutdown();
      System.out.println("ThreadDumper will no longer report socket IOExceptions, server is shutting down.");
   }

   private static void signalShutdown() {
      shutdownSignaled = true;
   }

   public void run() {
      if (!disabled) {
         while(true) {
            byte[] buf = new byte[2000];
            DatagramPacket recv = new DatagramPacket(buf, buf.length);

            try {
               sock.receive(recv);
            } catch (IOException var4) {
               if (!shutdownSignaled) {
                  System.out.println("*** MultiCast recv failed for addr=" + addr + " port=" + port);
                  var4.printStackTrace();
               }
            }

            String msg = new String(recv.getData(), 0, recv.getLength());
            if (msg.startsWith("DUMP")) {
               System.err.println(msg);
               vm.threadDump();
               System.err.println("Execute Queue State");
            }
         }
      }

   }

   public static void sendDumpMsg() {
      sendDumpMsg((String)null);
   }

   public static void sendDumpMsg(String text) {
      String outmsg = "DUMP at " + (new Date()).toString();
      if (text != null) {
         outmsg = outmsg + ": " + text;
      }

      byte[] data = outmsg.getBytes();
      DatagramPacket packet = new DatagramPacket(data, data.length, group, port);

      try {
         sock.send(packet);
      } catch (IOException var5) {
         if (!shutdownSignaled) {
            System.out.println("*** MultiCast send failed for addr=" + addr + " port=" + port);
            var5.printStackTrace();
         }
      }

   }

   public static void main(String[] args) throws Exception {
      if (args.length > 0 && args[0].equals("-test")) {
         init(true);
         ThreadDumper d = new ThreadDumper();
         d.start();
      } else {
         init(false);
         sendDumpMsg("I'm being dumped");
         System.exit(0);
      }

   }
}
