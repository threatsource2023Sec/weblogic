package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Response;

public final class BEConsumerIsActiveResponse extends Response implements Externalizable {
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int ISACTIVE_MASK = 256;
   static final long serialVersionUID = 7162255911562909284L;
   public boolean consumerIsActive;

   public BEConsumerIsActiveResponse(boolean consumerIsActive) {
      this.consumerIsActive = consumerIsActive;
   }

   public BEConsumerIsActiveResponse() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      if (this.consumerIsActive) {
         mask |= 256;
      }

      out.writeInt(mask);
      super.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.consumerIsActive = (mask & 256) != 0;
      }
   }
}
