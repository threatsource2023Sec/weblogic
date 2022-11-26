package weblogic.cluster;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OptionalDataException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Locale;
import weblogic.common.internal.WLObjectInputStream;
import weblogic.kernel.Kernel;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerIdentity;
import weblogic.rmi.spi.HostID;
import weblogic.security.HMAC;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.EncryptionService;
import weblogic.utils.ByteArrayDiffChecker;
import weblogic.utils.io.UnsyncByteArrayInputStream;
import weblogic.utils.net.InetAddressHelper;

public final class MulticastMonitor implements MulticastSessionIDConstants {
   static final int MAX_FRAGMENT_SIZE = 65536;
   private MulticastSocket sock = null;
   private byte[] fragmentBuffer;
   private final int bufSize = 131072;
   private final int localDomainNameHash;
   private final int localClusterNameHash;
   private byte[] salt = null;
   private byte[] key = null;
   private String domainDir;
   private EncryptionService es;
   private boolean esEnabled = false;
   private static final String osName = initOSNameProp();
   private static final boolean isLinux;

   public MulticastMonitor(String mcastaddr, int port, String domainName, String clusterName, String domainDir) throws IOException, UnknownHostException {
      if (isLinux()) {
         boolean hasIPv6Stack = InetAddressHelper.hasIPv6Stack();
         boolean hasIPv4Stack = InetAddressHelper.hasIPv4Stack();
         boolean isIPv4MulticastAddress = InetAddressHelper.isIPv4MulticastAddress(mcastaddr);
         boolean isIPv6MulticastAddress = InetAddressHelper.isIPv6MulticastAddress(mcastaddr);
         if (hasIPv4Stack && !hasIPv6Stack && isIPv4MulticastAddress || hasIPv6Stack && isIPv6MulticastAddress) {
            try {
               this.sock = new MulticastSocket(new InetSocketAddress(mcastaddr, port));
            } catch (BindException var11) {
               var11.printStackTrace();
            }
         }
      }

      if (this.sock == null) {
         this.sock = new MulticastSocket(port);
      }

      this.sock.setSoTimeout(30000);
      this.sock.setReceiveBufferSize(this.bufSize);
      this.localDomainNameHash = this.hashCode(domainName);
      this.localClusterNameHash = this.hashCode(clusterName);
      this.sock.joinGroup(InetAddress.getByName(mcastaddr));
      this.domainDir = domainDir;
      Kernel.ensureInitialized();
      this.initES();
   }

   private void initES() {
      if (this.domainDir != null) {
         System.setProperty("weblogic.RootDirectory", this.domainDir);
         this.es = SerializedSystemIni.getExistingEncryptionService();
         if (this.es != null) {
            this.salt = SerializedSystemIni.getSalt();
            this.key = SerializedSystemIni.getEncryptedSecretKey();
            this.esEnabled = true;
         }
      }
   }

   private boolean verify(byte[] in, byte[] digest) {
      if (!this.esEnabled) {
         return false;
      } else {
         byte[] md5 = HMAC.digest(in, this.key, this.salt);
         return ByteArrayDiffChecker.diffByteArrays(digest, md5) == null;
      }
   }

   public void run() throws Exception {
      while(true) {
         try {
            this.fragmentBuffer = new byte[65536];
            DatagramPacket packet = new DatagramPacket(this.fragmentBuffer, 65536);
            this.sock.receive(packet);
            WLObjectInputStream ois = getInputStream(packet.getData());
            int domainNameHash = ois.readInt();
            int clusterNameHash = ois.readInt();
            HostID memberID = (HostID)ois.readObjectWL();
            byte[] remoteDigest = null;
            byte[] payload = null;
            byte[] clear = null;
            String messageVersion = ois.readString();
            byte[] remoteDigest = (byte[])((byte[])ois.readObject());

            byte[] payload;
            try {
               payload = (byte[])((byte[])ois.readObject());
            } catch (IOException var21) {
               payload = remoteDigest;
               remoteDigest = null;
            }

            if (remoteDigest != null) {
               if (!this.esEnabled) {
                  System.err.println("Cannot handle encrypted multicast traffic.  Make sure domaindir is specified and correct");
                  usage();
               }

               if (this.verify(payload, remoteDigest)) {
                  clear = this.es.decryptBytes(payload);
               } else {
                  System.err.println("Message digest mismatch - ignoring packet");
                  System.exit(1);
               }
            } else {
               clear = payload;
            }

            ois = getInputStream(clear);
            MulticastSessionId multicastSessionId = (MulticastSessionId)ois.readObject();
            long seqNum = ois.readLong();
            int fragNum = ois.readInt();
            int size = ois.readInt();
            int offset = ois.readInt();
            boolean isRecover = ois.readBoolean();
            boolean retryEnabled = ois.readBoolean();
            boolean useHTTPForSD = ois.readBoolean();
            byte[] data = ois.readBytes();
            if (!this.isFragmentFromForeignCluster(domainNameHash, clusterNameHash)) {
               String msg;
               if (multicastSessionId.equals(ANNOUNCEMENT_MANAGER_ID)) {
                  size = data.length;
                  msg = " Received announcement message of size ";
               } else if (multicastSessionId.equals(ATTRIBUTE_MANAGER_ID)) {
                  msg = " Received attribute message of size ";
               } else if (multicastSessionId.equals(HEARTBEAT_SENDER_ID)) {
                  msg = " Received heartbeat message of size ";
               } else {
                  msg = " Received multicast message of size ";
               }

               System.out.println(msg + size + " from " + ((ServerIdentity)memberID).getServerName() + " @ " + new Date() + " messageVersion:" + messageVersion + " seqNum:" + seqNum + " fragment # " + fragNum);
            } else {
               System.out.println("Received multicast message of size " + size + " from foreign cluster " + memberID);
            }
         } catch (InterruptedIOException var22) {
         } catch (OptionalDataException var23) {
            System.err.println("Failed with OptionalDataException - EOF = " + var23.eof + " Length = " + var23.length);
            System.exit(1);
         } catch (IOException var24) {
            System.err.println("Failed with IOException " + var24);
            System.exit(1);
         }
      }
   }

   private boolean isFragmentFromForeignCluster(int domainNameHash, int clusterNameHash) {
      return this.localDomainNameHash != domainNameHash || this.localClusterNameHash != clusterNameHash;
   }

   public int hashCode(String s) {
      int h = 0;

      for(int i = 0; i < s.length(); ++i) {
         h = 31 * h + s.charAt(i);
      }

      return h;
   }

   private static WLObjectInputStream getInputStream(byte[] buffer) throws IOException {
      UnsyncByteArrayInputStream isb = new UnsyncByteArrayInputStream(buffer);
      WLObjectInputStream ios = new WLObjectInputStream(isb);
      ios.setReplacer(new MulticastReplacer(LocalServerIdentity.getIdentity()));
      return ios;
   }

   public static void main(String[] args) {
      String domainDir = null;

      try {
         if (args.length < 4 || args.length > 5) {
            usage();
         }

         if (args.length == 5) {
            domainDir = args[4];
         }

         MulticastMonitor monitor = new MulticastMonitor(args[0], Integer.parseInt(args[1]), args[2], args[3], domainDir);
         monitor.run();
      } catch (Exception var3) {
         usage();
      }

   }

   public static void usage() {
      System.out.println("java weblogic.cluster.MulticastMonitor <multicastaddress> <port> <domainname> <clustername> [<domaindir>] ");
      System.exit(1);
   }

   private static String initOSNameProp() {
      String s = "UNKNOWN";

      try {
         s = System.getProperty("os.name", "UNKNOWN").toLowerCase(Locale.ENGLISH);
      } catch (SecurityException var2) {
      }

      return s;
   }

   private static boolean isLinux() {
      return isLinux;
   }

   static {
      isLinux = "linux".equals(osName);
   }
}
