package weblogic.iiop.contexts;

import weblogic.common.internal.PackageInfo;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.VersionInfo;
import weblogic.corba.client.Version;
import weblogic.iiop.VendorInfoConstants;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.iiop.protocol.IiopProtocolFacade;

public final class VendorInfo extends ServiceContext implements Version, VendorInfoConstants {
   protected byte majorVersion = 14;
   protected byte minorVersion = 1;
   private byte minorServicePack = 1;
   private byte rollingPatch = 0;
   private static PackageInfo pkgInfo;
   private volatile PeerInfo peerinfo;
   public static final VendorInfo VENDOR_INFO;

   private VendorInfo() {
      super(1111834880);
      if (IiopProtocolFacade.isServer()) {
         this.majorVersion = (byte)pkgInfo.getMajor();
         this.minorVersion = (byte)pkgInfo.getMinor();
         this.minorServicePack = (byte)pkgInfo.getServicePack();
      }

   }

   public VendorInfo(byte major, byte minor, byte sp, byte patch) {
      super(1111834880);
      this.majorVersion = major;
      this.minorVersion = minor;
      this.minorServicePack = sp;
      this.rollingPatch = patch;
   }

   protected VendorInfo(CorbaInputStream in) {
      super(1111834880);
      this.readEncapsulatedContext(in);
   }

   public int getMinorVersion() {
      return this.minorVersion;
   }

   public PeerInfo getPeerInfo() {
      if (this.peerinfo == null) {
         this.peerinfo = new PeerInfo(this.majorVersion, this.minorVersion, this.minorServicePack, this.rollingPatch);
      }

      return this.peerinfo;
   }

   static ServiceContext readServiceContext(int context_id, CorbaInputStream in) {
      switch (context_id) {
         case 1111834880:
            return new VendorInfo(in);
         case 1111834881:
            return new VendorInfoTx(in);
         case 1111834882:
            return new VendorInfoSecurity(in);
         case 1111834883:
            return new VendorInfoCluster(in);
         case 1111834884:
         case 1111834885:
         case 1111834886:
         case 1111834887:
         case 1111834888:
         case 1111834889:
         default:
            return new ServiceContext(context_id, in);
         case 1111834890:
            return new VendorInfoTrace(in);
         case 1111834891:
            return new WorkAreaContext(in);
         case 1111834892:
            return new FutureObjectIdServiceContext(in);
         case 1111834893:
            return new DiscardSecurityContext(in);
         case 1111834894:
            return new RequestUrlServiceContext(in);
         case 1111834895:
            return new VendorInfoReplicaVersion(in);
      }
   }

   public void write(CorbaOutputStream out) {
      this.writeEncapsulatedContext(out);
   }

   protected void readEncapsulation(CorbaInputStream in, long handle) {
      this.majorVersion = in.read_octet();
      this.minorVersion = in.read_octet();
      this.minorServicePack = in.read_octet();
      this.rollingPatch = in.bytesLeft(handle) > 0 ? in.read_octet() : 0;
   }

   protected void writeEncapsulation(CorbaOutputStream out) {
      out.write_octet(this.majorVersion);
      out.write_octet(this.minorVersion);
      out.write_octet(this.minorServicePack);
      out.write_octet(this.rollingPatch);
   }

   public boolean equals(Object o) {
      return this == o || o != null && this.getClass() == o.getClass() && this.equals((VendorInfo)o);
   }

   private boolean equals(VendorInfo other) {
      return this.majorVersion == other.majorVersion && this.minorVersion == other.minorVersion && this.minorServicePack == other.minorServicePack && this.rollingPatch == other.rollingPatch;
   }

   public int hashCode() {
      int result = this.majorVersion;
      result = 31 * result + this.minorVersion;
      result = 31 * result + this.minorServicePack;
      result = 31 * result + this.rollingPatch;
      return result;
   }

   public String toString() {
      return "VendorInfo Context for " + this.majorVersion + "." + this.minorVersion + "." + this.minorServicePack;
   }

   static {
      if (IiopProtocolFacade.isServer()) {
         pkgInfo = VersionInfo.theOne().getPackages()[0];
      }

      VENDOR_INFO = new VendorInfo();
   }
}
