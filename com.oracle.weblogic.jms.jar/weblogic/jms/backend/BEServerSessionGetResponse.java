package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Response;

public final class BEServerSessionGetResponse extends Response implements Externalizable {
   static final long serialVersionUID = 4398629545912724093L;
   private static final byte EXTVERSION = 1;
   private BEServerSession serverSession;

   public BEServerSessionGetResponse(BEServerSession serverSession) {
      this.serverSession = serverSession;
   }

   public final BEServerSession getServerSession() {
      return this.serverSession;
   }

   public BEServerSessionGetResponse() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeByte(1);
      super.writeExternal(out);
      this.serverSession.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      byte version = in.readByte();
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.serverSession = new BEServerSession();
         this.serverSession.readExternal(in);
      }
   }
}
