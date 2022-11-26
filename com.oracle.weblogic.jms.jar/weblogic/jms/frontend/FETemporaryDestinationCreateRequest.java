package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class FETemporaryDestinationCreateRequest extends Request implements Externalizable {
   static final long serialVersionUID = 8448408102807078690L;
   private int destinationType;
   private boolean temporary;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int TEMPORARY_MASK = 256;

   public FETemporaryDestinationCreateRequest(JMSID connectionId, int destinationType, boolean temporary) {
      super(connectionId, 7687);
      this.destinationType = destinationType;
      this.temporary = temporary;
   }

   public final int getDestType() {
      return this.destinationType;
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return new FETemporaryDestinationCreateResponse();
   }

   public FETemporaryDestinationCreateRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int mask = 1;
      if (this.temporary) {
         mask |= 256;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      out.writeInt(this.destinationType);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.destinationType = in.readInt();
         this.temporary = (mask & 256) != 0;
      }
   }
}
