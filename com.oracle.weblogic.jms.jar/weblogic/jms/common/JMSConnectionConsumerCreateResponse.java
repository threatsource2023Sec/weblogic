package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.jms.ConnectionConsumer;
import weblogic.jms.client.JMSConnectionConsumer;
import weblogic.jms.dispatcher.Response;

public final class JMSConnectionConsumerCreateResponse extends Response implements Externalizable {
   static final long serialVersionUID = -3526422355578670715L;
   private static final byte EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private ConnectionConsumer connectionConsumer;

   public JMSConnectionConsumerCreateResponse(ConnectionConsumer connectionConsumer) {
      this.connectionConsumer = connectionConsumer;
   }

   public final ConnectionConsumer getConnectionConsumer() {
      return this.connectionConsumer;
   }

   public final void setConnectionConsumer(ConnectionConsumer connectionConsumer) {
      this.connectionConsumer = connectionConsumer;
   }

   public JMSConnectionConsumerCreateResponse() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      out.writeInt(1);
      super.writeExternal(out);
      ((JMSConnectionConsumer)this.connectionConsumer).writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.connectionConsumer = new JMSConnectionConsumer();
         ((JMSConnectionConsumer)this.connectionConsumer).readExternal(in);
      }
   }
}
