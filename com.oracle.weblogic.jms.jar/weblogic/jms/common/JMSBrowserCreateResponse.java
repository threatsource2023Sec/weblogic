package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.dispatcher.Response;

public final class JMSBrowserCreateResponse extends Response implements Externalizable {
   static final long serialVersionUID = 4858043004642723716L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private JMSID browserId;

   public JMSBrowserCreateResponse(JMSID browserId) {
      this.browserId = browserId;
   }

   public JMSID getBrowserId() {
      return this.browserId;
   }

   public JMSBrowserCreateResponse() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      out.writeInt(1);
      super.writeExternal(out);
      this.browserId.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.browserId = new JMSID();
         this.browserId.readExternal(in);
      }
   }
}
