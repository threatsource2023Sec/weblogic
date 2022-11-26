package weblogic.messaging.kernel;

public interface UnitOfWorkEvent extends DestinationEvent {
   String getUnitOfWork();

   boolean isAdd();
}
