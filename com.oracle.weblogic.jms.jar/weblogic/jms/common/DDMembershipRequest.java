package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.dispatcher.DispatcherWrapper;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public class DDMembershipRequest extends Request implements Externalizable {
   private static final long serialVersionUID = -7299611253175553856L;
   private static final int EXTVERSIONDIABLO = 1;
   private static final int VERSION_MASK = 255;
   private String ddConfigName;
   private String ddJndiName;
   DispatcherWrapper dispatcherWrapper;

   public DDMembershipRequest(String ddConfigName, String ddJndiName, DispatcherWrapper dispatcherWrapper) {
      super((JMSID)null, 18455);
      this.ddConfigName = ddConfigName;
      this.ddJndiName = ddJndiName;
      this.dispatcherWrapper = dispatcherWrapper;
      if (ddConfigName == null) {
         throw new Error(" Call BEA Support. DDMembershipRequest.ddConfigName = null");
      } else if (ddJndiName == null) {
         throw new Error(" Call BEA Support. DDMembershipRequest.ddJndiName = null");
      }
   }

   public DispatcherWrapper getDispatcherWrapper() {
      return this.dispatcherWrapper;
   }

   public String getDDConfigName() {
      return this.ddConfigName;
   }

   public String getDDJndiName() {
      return this.ddJndiName;
   }

   public int remoteSignature() {
      return 18;
   }

   public boolean isServerToServer() {
      return false;
   }

   public Response createResponse() {
      return new DDMembershipResponse();
   }

   public DDMembershipRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int flags = 1;
      out.writeInt(flags);
      super.writeExternal(out);
      this.dispatcherWrapper.writeExternal(out);
      out.writeUTF(this.ddConfigName);
      out.writeUTF(this.ddJndiName);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      int version = flags & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.dispatcherWrapper = new DispatcherWrapper();
         this.dispatcherWrapper.readExternal(in);
         this.ddConfigName = in.readUTF();
         this.ddJndiName = in.readUTF();
      }
   }
}
