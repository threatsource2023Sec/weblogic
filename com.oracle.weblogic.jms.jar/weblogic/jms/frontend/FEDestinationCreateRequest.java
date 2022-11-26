package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSDestinationCreateResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class FEDestinationCreateRequest extends Request implements Externalizable {
   static final long serialVersionUID = -4660550797390037314L;
   private String destinationName;
   private int destinationType;
   private boolean temporary;
   private JMSServerId backEndId;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int TEMPORARY_MASK = 256;
   static final int START = 0;
   static final int CONTINUE = 1;

   public FEDestinationCreateRequest(String destinationName, int destinationType, boolean temporary) {
      super((JMSID)null, 3841);
      this.destinationName = destinationName;
      this.destinationType = destinationType;
      this.temporary = temporary;
   }

   public final String getDestinationName() {
      return this.destinationName;
   }

   public final void setDestinationName(String destinationName) {
      this.destinationName = destinationName;
   }

   public final int getDestType() {
      return this.destinationType;
   }

   public final JMSServerId getBackEndId() {
      return this.backEndId;
   }

   public final void setBackEndId(JMSServerId backEndId) {
      this.backEndId = backEndId;
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return new JMSDestinationCreateResponse();
   }

   public FEDestinationCreateRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int mask = 1;
      if (this.temporary) {
         mask |= 256;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      out.writeUTF(this.destinationName);
      out.writeInt(this.destinationType);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.destinationName = in.readUTF();
         this.destinationType = in.readInt();
         this.temporary = (mask & 256) != 0;
      }
   }
}
