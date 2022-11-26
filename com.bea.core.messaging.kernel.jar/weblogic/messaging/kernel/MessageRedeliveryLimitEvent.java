package weblogic.messaging.kernel;

public interface MessageRedeliveryLimitEvent extends MessageRemoveEvent {
   int getRedeliveryLimit();

   int getDeliveryCount();
}
