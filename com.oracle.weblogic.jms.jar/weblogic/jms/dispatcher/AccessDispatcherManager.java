package weblogic.jms.dispatcher;

import java.rmi.UnmarshalException;
import javax.jms.JMSException;

public interface AccessDispatcherManager {
   DispatcherPartitionContext findDispatcherPartitionContextJMSException() throws JMSException;

   DispatcherPartitionContext findDispatcherPartitionContextJMSException(String var1) throws JMSException;

   DispatcherPartitionContext findDispatcherPartitionContextDispatcherException(String var1) throws weblogic.messaging.dispatcher.DispatcherException;

   JMSDispatcher findDispatcherByPartitionIdUnmarshalException(String var1) throws UnmarshalException;

   DispatcherPartitionContext lookupDispatcherPartitionContextById(String var1);

   DispatcherPartitionContext lookupDispatcherPartitionContextByName(String var1);
}
