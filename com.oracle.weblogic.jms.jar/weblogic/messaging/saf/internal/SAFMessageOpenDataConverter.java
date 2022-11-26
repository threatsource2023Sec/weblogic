package weblogic.messaging.saf.internal;

import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import javax.transaction.xa.Xid;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.runtime.OpenDataConverter;
import weblogic.messaging.saf.SAFMessageInfo;
import weblogic.messaging.saf.SAFRequest;

public final class SAFMessageOpenDataConverter implements OpenDataConverter {
   private final RemoteEndpointRuntimeDelegate destination;

   public SAFMessageOpenDataConverter(RemoteEndpointRuntimeDelegate destination) {
      this.destination = destination;
   }

   public CompositeData createCompositeData(Object object) throws OpenDataException {
      if (object == null) {
         return null;
      } else if (!(object instanceof MessageElement)) {
         throw new OpenDataException("Unexpected class " + object.getClass().getName());
      } else {
         MessageElement me = (MessageElement)object;
         String xidString = null;
         Xid xid = me.getXid();
         if (xid != null) {
            xidString = xid.toString();
         }

         SAFMessageInfo info = new SAFMessageInfo(me.getInternalSequenceNumber(), me.getState(), xidString, me.getInternalSequenceNumber(), me.getConsumerID(), (SAFRequest)me.getMessage(), this.destination.getURL());
         return info.toCompositeData();
      }
   }
}
