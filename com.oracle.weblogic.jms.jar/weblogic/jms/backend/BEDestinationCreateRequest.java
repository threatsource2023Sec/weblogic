package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSDestinationCreateResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class BEDestinationCreateRequest extends Request implements Externalizable {
   static final long serialVersionUID = 2406860847787652474L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int JMS_CREATE_IDENTIFIER = 256;
   private String destinationName;
   private int destType;
   private boolean forCreateDestination;

   public BEDestinationCreateRequest(JMSID backEndId, String destinationName, int destType, boolean paramForCreateDestination) {
      super(backEndId, 11534);
      this.destinationName = destinationName;
      this.destType = destType;
      this.forCreateDestination = paramForCreateDestination;
   }

   public final String getDestinationName() {
      return this.destinationName;
   }

   public final int getDestType() {
      return this.destType;
   }

   public int remoteSignature() {
      return 18;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return new JMSDestinationCreateResponse();
   }

   public BEDestinationCreateRequest() {
   }

   boolean isForCreateDestination() {
      return this.forCreateDestination;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      if (this.forCreateDestination) {
         mask |= 256;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      out.writeUTF(this.destinationName);
      out.writeInt(this.destType);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         this.forCreateDestination = (mask & 256) != 0;
         super.readExternal(in);
         this.destinationName = in.readUTF();
         this.destType = in.readInt();
      }
   }
}
