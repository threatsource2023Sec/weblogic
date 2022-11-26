package weblogic.jms.dotnet.transport.t3client;

import java.util.StringTokenizer;

public class T3PeerInfo {
   public static final int DEFAULT_PROTOCOL_VERSION_MAJOR = 10;
   public static final int DEFAULT_PROTOCOL_VERSION_MINOR = 0;
   public static final int DEFAULT_PROTOCOL_VERSION_SERVICEPACK = 0;
   public static final int DEFAULT_PROTOCOL_VERSION_ROLLINGPATCH = 0;
   public static final boolean DEFAULT_PROTOCOL_VERSION_TEMPORARYPATCH = false;
   public static final T3PeerInfo defaultPeerInfo = new T3PeerInfo(10, 0, 0, 0, false);
   private int major;
   private int minor;
   private int servicepack;
   private int rollingpatch;
   private boolean temporarypatch;

   T3PeerInfo(int major, int minor, int servicepack, int rollingpatch, boolean temporarypatch) {
      this.major = major;
      this.minor = minor;
      this.servicepack = servicepack;
      this.rollingpatch = rollingpatch;
      this.temporarypatch = temporarypatch;
   }

   T3PeerInfo(String version) throws Exception {
      StringTokenizer token = new StringTokenizer(version, ".");
      if (token.countTokens() != 5) {
         throw new Exception("Unknown version syntax " + version);
      } else {
         int count = 0;
         int major = false;
         int minor = false;
         int servicepack = false;
         int rollingpatch = false;
         boolean temporarypatch = false;

         while(token.hasMoreElements()) {
            ++count;
            String element = token.nextToken();
            switch (count) {
               case 1:
                  int major = Integer.parseInt(element);
                  break;
               case 2:
                  int minor = Integer.parseInt(element);
                  break;
               case 3:
                  int servicepack = Integer.parseInt(element);
                  break;
               case 4:
                  int rollingpatch = Integer.parseInt(element);
                  break;
               case 5:
                  temporarypatch = new Boolean(element);
            }
         }

      }
   }

   public int getMajor() {
      return this.major;
   }

   public int getMinor() {
      return this.minor;
   }

   public int getServicePack() {
      return this.servicepack;
   }

   public int getRollingPatch() {
      return this.rollingpatch;
   }

   public boolean isTemporaryPatch() {
      return this.temporarypatch;
   }

   public String getVersion() {
      return "" + this.major + "." + this.minor + "." + this.servicepack + "." + this.rollingpatch;
   }

   public void write(MarshalWriterImpl output) {
      output.writeInt(this.major);
      output.writeInt(this.minor);
      output.writeInt(this.servicepack);
      output.writeInt(this.rollingpatch);
      output.writeBoolean(this.temporarypatch);
   }

   public String getDefaultVersion() {
      return "10.0.0.0";
   }
}
