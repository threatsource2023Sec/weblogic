package weblogic.iiop.messages;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

final class TaggedProfile {
   public int tag;
   private byte[] profile_data;

   public TaggedProfile() {
   }

   TaggedProfile(CorbaInputStream is) {
      this.read(is);
   }

   public TaggedProfile(int i, byte[] profile_data) {
      this.tag = i;
      this.profile_data = profile_data;
   }

   public void read(CorbaInputStream in) {
      this.tag = in.read_ulong();
      this.profile_data = in.read_octet_sequence();
   }

   public void write(CorbaOutputStream out) {
      out.write_ulong(this.tag);
      out.write_octet_sequence(this.profile_data);
   }
}
