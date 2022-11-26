package weblogic.jms.backend;

import weblogic.application.ModuleException;
import weblogic.j2ee.descriptor.wl.DestinationKeyBean;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.extensions.JMSMessageInfo;
import weblogic.messaging.kernel.MessageElement;

public class BECursorDestinationKey extends BEDestinationKey {
   protected static final int KEY_TYPE_JMS_BEA_XID = 101;
   protected static final int KEY_TYPE_JMS_BEA_STATE = 102;
   protected static final int KEY_TYPE_JMS_BEA_SEQUENCE_NUMBER = 103;

   public BECursorDestinationKey(BEDestinationImpl destination, DestinationKeyBean destinationKey) throws ModuleException {
      super(destination, destinationKey);
      if (this.property.equalsIgnoreCase("JMS_BEA_Xid")) {
         this.keyType = 101;
      } else if (this.property.equalsIgnoreCase("JMS_BEA_State")) {
         this.keyType = 102;
      } else if (this.property.equalsIgnoreCase("JMS_BEA_SequenceNumber")) {
         this.keyType = 103;
      }

   }

   long compareKey(MessageElement cmref1, MessageElement cmref2, boolean destIsLifo) {
      long ret = 0L;
      switch (this.keyType) {
         case 101:
            if (cmref1.getXid() != null && cmref2.getXid() != null) {
               ret = (long)cmref1.getXid().toString().compareTo(cmref2.getXid().toString());
            } else if (cmref1.getXid() != null) {
               ret = 1L;
            } else if (cmref2.getXid() != null) {
               ret = -1L;
            } else {
               ret = 0L;
            }
            break;
         case 102:
            ret = (long)JMSMessageInfo.getStateString(cmref1.getState()).compareTo(JMSMessageInfo.getStateString(cmref2.getState()));
            break;
         case 103:
            ret = cmref1.getInternalSequenceNumber() - cmref2.getInternalSequenceNumber();
            break;
         default:
            if (ret == 0L) {
               return super.compareKey((MessageImpl)cmref1.getMessage(), (MessageImpl)cmref2.getMessage(), destIsLifo);
            }
      }

      if (destIsLifo) {
         return this.direction == 0 ? -ret : ret;
      } else {
         return this.direction == 0 ? ret : -ret;
      }
   }
}
