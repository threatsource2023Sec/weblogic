package weblogic.messaging.kernel;

public interface MessageExpirationEvent extends MessageRemoveEvent {
   long getExpirationTime();
}
