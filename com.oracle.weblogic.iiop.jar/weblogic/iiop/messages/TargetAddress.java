package weblogic.iiop.messages;

import org.omg.CORBA.MARSHAL;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class TargetAddress {
   private short addressingDisposition;
   private byte[] objectKey;
   private IORAddressingInfo addressingInfo;
   private TaggedProfile taggedProfile;

   public TargetAddress(byte[] objectKey) {
      this.addressingDisposition = 0;
      this.objectKey = objectKey;
   }

   public TargetAddress(CorbaInputStream is) {
      this.read(is);
   }

   public final void reset() {
      this.objectKey = null;
      this.addressingInfo = null;
      this.taggedProfile = null;
   }

   public final void read(CorbaInputStream is) {
      this.addressingDisposition = is.read_short();
      switch (this.addressingDisposition) {
         case 0:
            this.objectKey = is.read_octet_sequence(1048576);
            break;
         case 1:
            this.taggedProfile = new TaggedProfile(is);
            break;
         case 2:
            this.addressingInfo = new IORAddressingInfo(is);
            this.objectKey = this.addressingInfo.getIor().getProfile().getKey();
            break;
         default:
            throw new MARSHAL("Unknown addressing disposition: " + this.addressingDisposition);
      }

   }

   public final void write(CorbaOutputStream os) {
      os.write_short(this.addressingDisposition);
      switch (this.addressingDisposition) {
         case 0:
            os.write_octet_sequence(this.objectKey);
            break;
         case 1:
            this.taggedProfile.write(os);
            break;
         case 2:
            this.addressingInfo.write(os);
            break;
         default:
            throw new MARSHAL("Unknown addressing disposition: " + this.addressingDisposition);
      }

   }

   public byte[] getObjectKey() {
      return this.objectKey;
   }
}
