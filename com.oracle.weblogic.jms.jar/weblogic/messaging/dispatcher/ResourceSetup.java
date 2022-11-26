package weblogic.messaging.dispatcher;

import weblogic.jms.dispatcher.DispatcherPartition4rmic;

public interface ResourceSetup {
   void giveRequestResource(Request var1);

   DispatcherPartition4rmic getDispatcherPartition4rmic();
}
