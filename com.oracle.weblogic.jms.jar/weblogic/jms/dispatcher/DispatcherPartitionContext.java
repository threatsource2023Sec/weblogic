package weblogic.jms.dispatcher;

import javax.jms.JMSException;
import javax.naming.Context;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.messaging.dispatcher.Dispatcher;
import weblogic.messaging.dispatcher.DispatcherId;

public interface DispatcherPartitionContext extends DispatcherPartition4rmic {
   void removeJMSDispatcherManager();

   int getConfiguredThreadPoolSizeForClient();

   void removeDispatcherWrapperStateReference();

   void removeDispatcherReference(JMSDispatcher var1);

   void removeDispatcherReference(Dispatcher var1, boolean var2);

   JMSDispatcher dispatcherFindOrCreate(Context var1, DispatcherId var2) throws weblogic.messaging.dispatcher.DispatcherException;

   JMSDispatcher registerDispatcher(weblogic.messaging.dispatcher.DispatcherWrapper var1) throws weblogic.messaging.dispatcher.DispatcherException;

   DispatcherWrapper getLocalDispatcherWrapper() throws JMSException;

   void exportLocalDispatcher() throws JMSException;

   void unexportLocalDispatcher() throws JMSException;

   JMSDispatcher dispatcherAdapterOrPartitionAdapter(DispatcherWrapper var1) throws weblogic.messaging.dispatcher.DispatcherException;

   JMSDispatcher dispatcherCreateForCDS(Context var1, DispatcherId var2) throws JMSException, weblogic.messaging.dispatcher.DispatcherException;

   boolean isLocal(weblogic.messaging.dispatcher.DispatcherWrapper var1);

   ComponentInvocationContext getCIC(String var1);
}
