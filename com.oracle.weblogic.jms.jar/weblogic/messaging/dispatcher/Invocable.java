package weblogic.messaging.dispatcher;

import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.messaging.ID;

public interface Invocable {
   int invoke(Request var1) throws Throwable;

   ID getId();

   DispatcherPartition4rmic getDispatcherPartition4rmic();

   InvocableMonitor getInvocableMonitor();
}
