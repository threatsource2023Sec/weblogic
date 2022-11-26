package weblogic.deployment.jms;

import javax.jms.JMSException;

public class WrappedClassManager {
   protected static final int NUM_WRAPPED_CLASSES = 30;
   protected static final int WRAP_SESSION_CLASS = 0;
   protected static final int WRAP_QSESSION_CLASS = 1;
   protected static final int WRAP_TSESSION_CLASS = 2;
   protected static final int WRAP_SESSION_MDB_CLASS = 3;
   protected static final int WRAP_QSESSION_MDB_CLASS = 4;
   protected static final int WRAP_TSESSION_MDB_CLASS = 5;
   protected static final int WRAP_SESSION_TRANS_CLASS = 6;
   protected static final int WRAP_QSESSION_TRANS_CLASS = 7;
   protected static final int WRAP_TSESSION_TRANS_CLASS = 8;
   protected static final int WRAP_PRODUCER_CLASS = 9;
   protected static final int WRAP_CONSUMER_CLASS = 10;
   protected static final int WRAP_QSENDER_CLASS = 11;
   protected static final int WRAP_QRECEIVER_CLASS = 12;
   protected static final int WRAP_TPUBLISHER_CLASS = 13;
   protected static final int WRAP_TSUBSCRIBER_CLASS = 14;
   protected static final int WRAP_QBROWSER_CLASS = 15;
   protected static final int WRAP_XARESOURCE_CLASS = 16;
   protected static final int WRAP_CONNECTION_CLASS = 17;
   protected static final int WRAP_QCONNECTION_CLASS = 18;
   protected static final int WRAP_TCONNECTION_CLASS = 19;
   protected static final int WRAP_CONNECTION_NP_CLASS = 20;
   protected static final int WRAP_QCONNECTION_NP_CLASS = 21;
   protected static final int WRAP_TCONNECTION_NP_CLASS = 22;
   protected static final int WRAP_MESSAGE_CLASS = 23;
   protected static final int WRAP_PRIMARY_CONTEXT_CLASS = 24;
   protected static final int WRAP_PRIMARY_CONTEXT_NP_CLASS = 25;
   protected static final int WRAP_SECONDARY_CONTEXT_CLASS = 26;
   protected static final int WRAP_SECONDARY_CONTEXT_TRANS_CLASS = 27;
   protected static final int WRAP_JMSPRODUCER_CLASS = 28;
   protected static final int WRAP_JMSCONSUMER_CLASS = 29;
   private JMSWrapperFactory factory = new JMSWrapperFactory();

   public synchronized Object getWrappedInstance(int type, Object wrappedObj) throws JMSException {
      try {
         Class wrapperClass = this.getWrapperClass(type, wrappedObj);
         if (wrapperClass == null) {
            throw JMSExceptions.getJMSException(JMSPoolLogger.logWrappedClassErrorLoggable(wrappedObj.getClass().getName()));
         } else {
            return wrapperClass.newInstance();
         }
      } catch (Exception var4) {
         throw JMSExceptions.getJMSException(JMSPoolLogger.logWrappedClassErrorLoggable(wrappedObj.getClass().getName()), var4);
      }
   }

   private Class getWrapperClass(int type, Object wrappedObj) throws JMSException {
      String className;
      switch (type) {
         case 0:
         case 1:
         case 2:
            className = "weblogic.deployment.jms.PooledSession";
            break;
         case 3:
         case 4:
         case 5:
            className = "weblogic.deployment.jms.MDBSession";
            break;
         case 6:
         case 7:
         case 8:
            className = "weblogic.deployment.jms.WrappedTransactionalSession";
            break;
         case 9:
         case 11:
         case 13:
            className = "weblogic.deployment.jms.WrappedMessageProducer";
            break;
         case 10:
         case 12:
         case 14:
            className = "weblogic.deployment.jms.WrappedMessageConsumer";
            break;
         case 15:
            className = "weblogic.deployment.jms.WrappedQueueBrowser";
            break;
         case 16:
            className = "weblogic.deployment.jms.WrappedXAResource";
            break;
         case 17:
         case 18:
         case 19:
            className = "weblogic.deployment.jms.PooledConnection";
            break;
         case 20:
         case 21:
         case 22:
            className = "weblogic.deployment.jms.WrappedConnection";
            break;
         case 23:
            className = "weblogic.deployment.jms.WrappedMessage";
            break;
         case 24:
            className = "weblogic.deployment.jms.PooledPrimaryContext";
            break;
         case 25:
            className = "weblogic.deployment.jms.WrappedPrimaryContext";
            break;
         case 26:
            className = "weblogic.deployment.jms.PooledSecondaryContext";
            break;
         case 27:
            className = "weblogic.deployment.jms.WrappedTransactionalSecondaryContext";
            break;
         case 28:
            className = "weblogic.deployment.jms.WrappedJMSProducer";
            break;
         case 29:
            className = "weblogic.deployment.jms.WrappedJMSConsumer";
            break;
         default:
            throw JMSExceptions.getJMSException(JMSPoolLogger.logWrappedClassErrorLoggable(wrappedObj.getClass().getName()));
      }

      return this.factory.getWrapperClass(className, wrappedObj, false, WrappedClassManager.class.getClassLoader());
   }
}
