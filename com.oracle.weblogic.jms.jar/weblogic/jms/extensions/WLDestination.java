package weblogic.jms.extensions;

import javax.jms.Destination;
import javax.jms.JMSException;
import weblogic.messaging.dispatcher.DispatcherId;

public interface WLDestination extends Destination {
   boolean isQueue();

   boolean isTopic();

   String getCreateDestinationArgument() throws JMSException;

   void setReplicated(boolean var1);

   void setOneCopyPerServer(boolean var1);

   DispatcherId getDispatcherId();
}
