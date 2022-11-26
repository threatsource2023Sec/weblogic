package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.dispatcher.Response;

public final class JMSBrowserGetEnumerationResponse extends Response implements Externalizable {
   static final long serialVersionUID = 8459890622485517494L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private JMSID enumerationId;

   public JMSBrowserGetEnumerationResponse(JMSID enumerationId) {
      this.enumerationId = enumerationId;
   }

   public final JMSID getEnumerationId() {
      return this.enumerationId;
   }

   public JMSBrowserGetEnumerationResponse() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      out.writeInt(1);
      super.writeExternal(out);
      this.enumerationId.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         JMSUtilities.versionIOException(version, 1, 1);
      }

      super.readExternal(in);
      this.enumerationId = new JMSID();
      this.enumerationId.readExternal(in);
   }
}
