package weblogic.jms.dispatcher;

import javax.jms.JMSException;
import weblogic.messaging.dispatcher.Dispatcher;
import weblogic.messaging.dispatcher.DispatcherCommon;

public interface JMSDispatcher extends DispatcherCommon {
   void dispatchNoReply(weblogic.messaging.dispatcher.Request var1) throws JMSException;

   void dispatchNoReplyWithId(weblogic.messaging.dispatcher.Request var1, int var2) throws JMSException;

   weblogic.messaging.dispatcher.Response dispatchSync(weblogic.messaging.dispatcher.Request var1) throws JMSException;

   weblogic.messaging.dispatcher.Response dispatchSyncTran(weblogic.messaging.dispatcher.Request var1) throws JMSException;

   weblogic.messaging.dispatcher.Response dispatchSyncNoTran(weblogic.messaging.dispatcher.Request var1) throws JMSException;

   weblogic.messaging.dispatcher.Response dispatchSyncNoTranWithId(weblogic.messaging.dispatcher.Request var1, int var2) throws JMSException;

   Dispatcher getDelegate();
}
