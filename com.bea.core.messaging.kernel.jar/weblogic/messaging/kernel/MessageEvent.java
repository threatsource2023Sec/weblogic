package weblogic.messaging.kernel;

import weblogic.messaging.Message;

public interface MessageEvent extends DestinationEvent {
   Message getMessage();

   int getDeliveryCount();
}
