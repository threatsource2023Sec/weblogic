package weblogic.jms.common;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import javax.transaction.xa.Xid;
import weblogic.jms.extensions.JMSMessageInfo;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.runtime.OpenDataConverter;

public class JMSMessageOpenDataConverter implements OpenDataConverter {
   boolean bodyIncluded;

   public JMSMessageOpenDataConverter(boolean bodyIncluded) {
      this.bodyIncluded = bodyIncluded;
   }

   public CompositeData createCompositeData(Object object) throws OpenDataException {
      if (object == null) {
         return null;
      } else if (!(object instanceof MessageElement)) {
         throw new OpenDataException("Unexpected class " + object.getClass().getName());
      } else {
         MessageElement me = (MessageElement)object;
         MessageImpl msg = (MessageImpl)me.getMessage();
         if (msg == null) {
            throw new OpenDataException("MessageElement " + me + " contained null msg");
         } else {
            msg = msg.cloneit();
            msg.setDeliveryCount(me.getDeliveryCount());
            msg.setPropertiesWritable(false);
            msg.setBodyWritable(false);
            msg.includeJMSXDeliveryCount(true);
            String xidString = null;
            Xid xid = me.getXid();
            if (xid != null) {
               xidString = xid.toString();
            }

            String destinationName = null;
            Queue queue = me.getQueue();
            if (queue != null) {
               destinationName = queue.getName();
            }

            JMSMessageInfo info = new JMSMessageInfo(me.getInternalSequenceNumber(), me.getState(), xidString, me.getInternalSequenceNumber(), me.getConsumerID(), msg, destinationName, this.bodyIncluded);
            return info.toCompositeData();
         }
      }
   }
}
