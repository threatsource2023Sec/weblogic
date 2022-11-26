package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.dispatcher.Response;

public final class JMSPushExceptionRequest extends Request implements Externalizable {
   static final long serialVersionUID = -8924769504929515114L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private JMSException exception;

   public JMSPushExceptionRequest(int invocableType, JMSID invocableId, JMSException exception) {
      super(invocableId, 15360 | invocableType);
      this.exception = exception;
   }

   public void setInvocableType(int invocableType) {
      this.methodId = this.methodId & 16776960 & invocableType;
   }

   public JMSException getException() {
      return this.exception;
   }

   public int remoteSignature() {
      return 64;
   }

   public boolean isServerOneWay() {
      return true;
   }

   public boolean isServerToServer() {
      return false;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public JMSPushExceptionRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      out.writeInt(mask);
      super.writeExternal(out);
      out.writeObject(this.exception);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.exception = (JMSException)in.readObject();
      }
   }
}
