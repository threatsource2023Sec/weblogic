package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.jms.ServerSessionPool;
import weblogic.jms.client.JMSServerSessionPool;
import weblogic.jms.dispatcher.Response;

public final class JMSServerSessionPoolCreateResponse extends Response implements Externalizable {
   static final long serialVersionUID = -1327407705330866950L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private ServerSessionPool serverSessionPool;

   public JMSServerSessionPoolCreateResponse(ServerSessionPool serverSessionPool) {
      this.serverSessionPool = serverSessionPool;
   }

   public final ServerSessionPool getServerSessionPool() {
      return this.serverSessionPool;
   }

   public JMSServerSessionPoolCreateResponse() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeInt(1);
      super.writeExternal(out);
      ((JMSServerSessionPool)this.serverSessionPool).writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.serverSessionPool = new JMSServerSessionPool();
         ((JMSServerSessionPool)this.serverSessionPool).readExternal(in);
      }
   }
}
