package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class FESessionCreateRequest extends Request implements Externalizable {
   static final long serialVersionUID = 5179168005654945285L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int TRANSACTED_MASK = 256;
   private static final int XA_SESSION_MASK = 512;
   private boolean transacted;
   private boolean xaSession;
   private int acknowledgeMode;
   private transient String workManager;

   public FESessionCreateRequest(JMSID connectionId, boolean transacted, boolean xaSession, int acknowledgeMode, String workManager) {
      super(connectionId, 6663);
      this.transacted = transacted;
      this.xaSession = xaSession;
      this.acknowledgeMode = acknowledgeMode;
      this.workManager = workManager;
   }

   public final boolean getTransacted() {
      return this.transacted;
   }

   public final boolean getXASession() {
      return this.xaSession;
   }

   public final int getAcknowledgeMode() {
      return this.acknowledgeMode;
   }

   public String getPushWorkManager() {
      return this.workManager;
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return new FESessionCreateResponse();
   }

   public FESessionCreateRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      byte version = 1;
      int mask = version;
      if (this.transacted) {
         mask = version | 256;
      }

      if (this.xaSession) {
         mask |= 512;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      out.writeShort((short)this.acknowledgeMode);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.transacted = (mask & 256) != 0;
         this.xaSession = (mask & 512) != 0;
         this.acknowledgeMode = in.readShort();
      }
   }
}
