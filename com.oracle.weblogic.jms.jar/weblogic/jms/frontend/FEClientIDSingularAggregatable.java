package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import weblogic.jms.JMSService;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.common.SingularAggregatable;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.extensions.ConsumerClosedException;

public final class FEClientIDSingularAggregatable extends SingularAggregatable implements Externalizable {
   static final long serialVersionUID = 9144798830174213957L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private String clientID;
   private JMSID feConnectionID;

   public FEClientIDSingularAggregatable(String clientID, JMSID feConnectionID) {
      this.clientID = clientID;
      this.feConnectionID = feConnectionID;
   }

   public final void hadConflict(boolean didWin) {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("FEClientIDSingularAggregatable.hadConflict clientId = " + this.toString() + " didWin=" + didWin);
      }

      if (!didWin) {
         try {
            JMSService svc = JMSService.getJMSServiceWithPartitionName(JMSService.getSafePartitionNameFromThread());
            if (svc == null) {
               return;
            }

            Invocable inv = (Invocable)svc.getInvocableManagerDelegate().invocableFind(7, this.feConnectionID);
            if (!(inv instanceof FEConnection)) {
               return;
            }

            FEConnection feConnection = (FEConnection)inv;
            feConnection.close(false, new ConsumerClosedException((MessageConsumer)null, "ClientID conflict, " + this.clientID));
         } catch (JMSException var5) {
         }

      }
   }

   public final String toString() {
      return new String("FEClientIDSingularAggregatable(" + super.toString() + ":" + this.clientID + ")");
   }

   public FEClientIDSingularAggregatable() {
   }

   public final void writeExternal(ObjectOutput out) throws IOException {
      out.writeInt(1);
      super.writeExternal(out);
      out.writeUTF(this.clientID);
      this.feConnectionID.writeExternal(out);
   }

   public final void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.clientID = in.readUTF();
         this.feConnectionID = new JMSID();
         this.feConnectionID.readExternal(in);
      }
   }
}
