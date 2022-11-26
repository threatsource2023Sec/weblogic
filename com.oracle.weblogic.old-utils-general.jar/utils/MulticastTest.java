package utils;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.Hashtable;

public class MulticastTest {
   static String sampleAddress = "237.0.0.1";
   static int BUF_SIZE = 32768;
   static int DEF_PORT = 7001;
   static int DEF_TIMEOUT_SECS = 600;
   static long DEF_SEND_PAUSE_SECS = 2L;
   private byte[] message = null;
   private String messageString = null;
   private long sendPause = 0L;
   private InetAddress groupAddr = null;
   private int port = 0;
   private MulticastSocket socket = null;
   private int seq = 0;
   private Hashtable neighbors = null;
   private int timeout = 0;
   private String interfaceAddress = null;
   private byte ttl;

   public MulticastTest(String address, int port, int timeout, String messageString, long sendPause, String interfaceAddress, int timeToLive, boolean bindSocketToMulticastAddr) {
      this.sendPause = sendPause;
      this.port = port;
      this.timeout = timeout;
      this.messageString = messageString;
      this.neighbors = new Hashtable();

      try {
         byte[] messageContents = messageString.getBytes();
         this.message = new byte[BUF_SIZE];
         System.arraycopy(messageContents, 0, this.message, 25 - messageContents.length, messageContents.length);

         for(int i = 25; i < BUF_SIZE; ++i) {
            this.message[i] = 97;
         }

         if (bindSocketToMulticastAddr) {
            InetSocketAddress bindAddr = new InetSocketAddress(address, port);
            this.socket = new MulticastSocket(bindAddr);
         } else {
            this.socket = new MulticastSocket(port);
         }

         System.out.println("Bound socket to " + this.socket.getLocalSocketAddress());
         if (interfaceAddress != null) {
            try {
               InetAddress iAddress = InetAddress.getByName(interfaceAddress);
               System.out.println("Using interface at " + iAddress.getHostAddress());
               this.socket.setInterface(iAddress);
            } catch (SocketException var12) {
               throw var12;
            }
         }

         if (timeout > 0) {
            this.socket.setSoTimeout(timeout * 1000);
         }

         if (timeToLive > 1) {
            this.socket.setTimeToLive(timeToLive);
         }

         this.groupAddr = InetAddress.getByName(address);
         this.socket.joinGroup(this.groupAddr);
         System.out.println("Using multicast address " + address + ":" + port);
         System.out.println("\tJoined Group with InetAddress " + this.groupAddr);
         System.out.println("Will send messages under the name " + messageString + " every " + sendPause + " seconds");
         System.out.println("Will print warning every " + timeout + " seconds if no messages are received\n");
         (new Sender()).start();
         (new Receiver()).start();
      } catch (Exception var13) {
         System.out.println("*****Problem*******");
         var13.printStackTrace();
         shutdown();
      }

   }

   private void handleBytes(byte[] bytes, int start, int len) {
      if (len == 0) {
         System.out.println("Timed out");
      } else {
         int thisSeq = readInt(bytes, 0);
         String identifier = (new String(bytes, 4, 21)).trim();
         HasAnInt seqHolder = (HasAnInt)this.neighbors.get(identifier);
         if (seqHolder == null) {
            this.neighbors.put(identifier, new HasAnInt(thisSeq));
            if (!identifier.equals(this.messageString)) {
               System.out.println("New Neighbor " + identifier + " found on message number " + thisSeq);
            }
         } else if (seqHolder.value + 1 == thisSeq) {
            if (len < BUF_SIZE) {
               System.out.println("Received message " + seqHolder.value + " from " + identifier + ". Lost " + (BUF_SIZE - len) + " bytes of total of " + BUF_SIZE + " bytes");
            } else {
               System.out.println("Received message " + ++seqHolder.value + " from " + identifier);
            }
         } else if (seqHolder.value + 1 < thisSeq) {
            System.out.println("Missed message for " + identifier + "?  Last Seq " + seqHolder.value + " and just received " + thisSeq);
            seqHolder.value = thisSeq;
         } else if (seqHolder.value == thisSeq) {
            System.out.println("Duplicate message from " + identifier + ": " + thisSeq);
         } else if (seqHolder.value > thisSeq) {
            System.out.println("Out of order for " + identifier + "?  Last Seq " + seqHolder.value + " and just received " + thisSeq);
         }
      }

   }

   private static void shutdown() {
      shutdown(false);
   }

   private static void shutdown(boolean printUsage) {
      if (printUsage) {
         printUsage();
      }

      System.exit(0);
   }

   public static void main(String[] args) {
      String address = null;
      int port = DEF_PORT;
      int timeout = DEF_TIMEOUT_SECS;
      String messageString = "" + System.currentTimeMillis() / 1000L;
      long sendPause = DEF_SEND_PAUSE_SECS;
      String interfaceAddress = null;
      boolean bindToMulticastAddr = false;
      int timeToLive = 1;
      if (args.length < 2) {
         shutdown(true);
      }

      for(int i = 0; i < args.length; ++i) {
         if (args[i].startsWith("-") && args[i].length() >= 2) {
            try {
               switch (args[i].charAt(1)) {
                  case 'A':
                  case 'a':
                     if (args.length >= i + 2) {
                        address = args[i + 1];
                     }
                     break;
                  case 'B':
                  case 'b':
                     if (args.length >= i + 2) {
                        bindToMulticastAddr = Boolean.parseBoolean(args[i + 1]);
                     }
                  case 'C':
                  case 'D':
                  case 'E':
                  case 'F':
                  case 'G':
                  case 'J':
                  case 'K':
                  case 'M':
                  case 'O':
                  case 'Q':
                  case 'R':
                  case 'U':
                  case 'V':
                  case 'W':
                  case 'X':
                  case 'Y':
                  case 'Z':
                  case '[':
                  case '\\':
                  case ']':
                  case '^':
                  case '_':
                  case '`':
                  case 'c':
                  case 'd':
                  case 'e':
                  case 'f':
                  case 'g':
                  case 'j':
                  case 'k':
                  case 'm':
                  case 'o':
                  case 'q':
                  case 'r':
                  default:
                     break;
                  case 'H':
                  case 'h':
                     shutdown(true);
                     break;
                  case 'I':
                  case 'i':
                     if (args.length > i + 1) {
                        interfaceAddress = args[i + 1];
                     }
                     break;
                  case 'L':
                  case 'l':
                     if (args.length > i + 1) {
                        timeToLive = Integer.parseInt(args[i + 1]);
                     }
                     break;
                  case 'N':
                  case 'n':
                     if (args.length >= i + 2) {
                        messageString = args[i + 1];
                     }
                     break;
                  case 'P':
                  case 'p':
                     if (args.length >= i + 2) {
                        port = Integer.parseInt(args[i + 1]);
                     }
                     break;
                  case 'S':
                  case 's':
                     if (args.length >= i + 2) {
                        sendPause = Long.parseLong(args[i + 1]) > 0L ? Long.parseLong(args[i + 1]) : DEF_SEND_PAUSE_SECS;
                     }
                     break;
                  case 'T':
                  case 't':
                     if (args.length >= i + 2) {
                        timeout = Integer.parseInt(args[i + 1]);
                     }
               }
            } catch (Exception var14) {
            }
         }
      }

      System.out.println("***** WARNING ***** WARNING ***** WARNING *****\nDo NOT use the same multicast address as a running WLS cluster.");

      try {
         Thread.sleep(5000L);
      } catch (InterruptedException var13) {
      }

      System.out.println("\n\nStarting test.  Hit any key to abort\n\n");
      MulticastTest test = new MulticastTest(address, port, timeout, messageString, sendPause, interfaceAddress, timeToLive, bindToMulticastAddr);

      try {
         System.in.read();
      } catch (Exception var12) {
         var12.printStackTrace();
      }

      shutdown();
   }

   private static void printUsage() {
      System.out.println("\nUsage: java utils.MulticastTest [options]\n");
      System.out.println("\t -N <name> REQUIRED ");
      System.out.println("\t\t Unique identifier for process.");
      System.out.println("\t -A <address> The multicast address REQUIRED");
      System.out.println("\t\t Sample multicast address:  " + sampleAddress);
      System.out.println("\t -P <portNumber>");
      System.out.println("\t\t The multicast port.  Default: " + DEF_PORT);
      System.out.println("\t -T <timeout> ");
      System.out.println("\t\t The idle timeout, in seconds.  Default: " + DEF_TIMEOUT_SECS);
      System.out.println("\t -S <sendPause>");
      System.out.println("\t\t  The pause between sends, in seconds.  Default: " + DEF_SEND_PAUSE_SECS);
      System.out.println("\t -I <interfaceAddress>");
      System.out.println("\t\t  Address of interface card to use.  Uses the default NIC if none is specified");
      System.out.println("\t -L <timeToLive>");
      System.out.println("\t\t  Sets time-to-live for multicast packet.\nThis specifies how many hops a packet can make before being\ndiscarded.  Defaults to 1 which restricts the packet to the\nsubnet.");
      System.out.println("\t -B true|false");
      System.out.println("\t\t  If true, bind the socket to multicast address(specified in option -A) instead of IP_ANY. Default is false");
      System.out.println("\t -H Help (this)\n");
   }

   public static int readInt(byte[] data, int offset) {
      int ret = (data[offset++] & 255) << 24;
      ret |= (data[offset++] & 255) << 16;
      ret |= (data[offset++] & 255) << 8;
      ret |= data[offset] & 255;
      return ret;
   }

   public static int writeInt(byte[] data, int offset, int anInt) {
      data[offset++] = (byte)(anInt >>> 24);
      data[offset++] = (byte)(anInt >>> 16);
      data[offset++] = (byte)(anInt >>> 8);
      data[offset] = (byte)anInt;
      return anInt;
   }

   class Sender extends Thread {
      public void run() {
         try {
            while(true) {
               sleep(MulticastTest.this.sendPause * 1000L);
               MulticastTest.writeInt(MulticastTest.this.message, 0, ++MulticastTest.this.seq);
               DatagramPacket packet = new DatagramPacket(MulticastTest.this.message, MulticastTest.this.message.length, MulticastTest.this.groupAddr, MulticastTest.this.port);
               MulticastTest.this.socket.send(packet);
               System.out.println("\t\tI (" + MulticastTest.this.messageString + ") sent message num " + MulticastTest.this.seq);
            }
         } catch (Exception var2) {
            var2.printStackTrace();
         }
      }
   }

   class Receiver extends Thread {
      byte[] buffer = null;

      public void run() {
         try {
            this.buffer = new byte[MulticastTest.BUF_SIZE];

            while(true) {
               DatagramPacket packet = new DatagramPacket(this.buffer, this.buffer.length);
               MulticastTest.this.socket.receive(packet);
               MulticastTest.this.handleBytes(this.buffer, 0, packet.getLength());
            }
         } catch (Exception var2) {
            var2.printStackTrace();
         }
      }
   }

   class HasAnInt {
      public int value = 0;

      public HasAnInt(int value) {
         this.value = value;
      }
   }
}
