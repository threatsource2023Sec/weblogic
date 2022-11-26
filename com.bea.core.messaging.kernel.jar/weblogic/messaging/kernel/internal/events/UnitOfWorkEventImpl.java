package weblogic.messaging.kernel.internal.events;

import javax.transaction.xa.Xid;
import weblogic.messaging.kernel.Destination;
import weblogic.messaging.kernel.UnitOfWorkEvent;

public class UnitOfWorkEventImpl extends DestinationEventImpl implements UnitOfWorkEvent {
   String unitOfWork;
   boolean isAdd;

   public UnitOfWorkEventImpl(Destination destination, String unitOfWork, boolean isAdd) {
      super((String)null, destination, (Xid)null);
      this.unitOfWork = unitOfWork;
      this.isAdd = isAdd;
   }

   public String getUnitOfWork() {
      return this.unitOfWork;
   }

   public boolean isAdd() {
      return this.isAdd;
   }
}
