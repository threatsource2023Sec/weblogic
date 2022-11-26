package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.dispatcher.Response;
import weblogic.messaging.path.helper.KeyString;

public final class BEOrderUpdateRequest extends Request implements Externalizable {
   static final long serialVersionUID = 6686162300621162069L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private KeyString key;
   private BEUOOMember oldMember;
   private BEUOOMember newMember;

   public BEOrderUpdateRequest(JMSID destinationId, KeyString key, BEUOOMember oldMember, BEUOOMember newMember) {
      super(destinationId, 17940);
      this.key = key;
      this.oldMember = oldMember;
      this.newMember = newMember;
   }

   KeyString getKey() {
      return this.key;
   }

   BEUOOMember getOldMember() {
      return this.oldMember;
   }

   BEUOOMember getNewMember() {
      return this.newMember;
   }

   public int remoteSignature() {
      return 34;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public BEOrderUpdateRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      out.writeInt(mask);
      super.writeExternal(out);
      this.key.writeExternal(out);
      this.oldMember.writeExternal(out);
      this.newMember.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.key = new KeyString();
         this.key.readExternal(in);
         this.oldMember = new BEUOOMember();
         this.oldMember.readExternal(in);
         this.newMember = new BEUOOMember();
         this.newMember.readExternal(in);
      }
   }
}
