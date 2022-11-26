package weblogic.jms.client;

import javax.jms.JMSException;
import javax.jms.XAQueueSession;
import javax.jms.XASession;
import javax.jms.XATopicSession;

public final class XAConnectionInternalImpl extends WLConnectionImpl implements XAConnectionInternal {
   public XAConnectionInternalImpl(JMSXAConnectionFactory xaConnFac, JMSXAConnection xaConn) throws JMSException {
      super(xaConnFac, xaConn);
   }

   public XAQueueSession createXAQueueSession() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         return ((JMSXAConnection)physical).createXAQueueSession();
      } catch (weblogic.jms.common.JMSException var5) {
         return ((JMSXAConnection)this.computeJMSConnection(startTime, physical, var5)).createXAQueueSession();
      }
   }

   public XATopicSession createXATopicSession() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         return ((JMSXAConnection)physical).createXATopicSession();
      } catch (weblogic.jms.common.JMSException var5) {
         return ((JMSXAConnection)this.computeJMSConnection(startTime, physical, var5)).createXATopicSession();
      }
   }

   public XASession createXASession() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         return ((JMSXAConnection)physical).createXASession();
      } catch (weblogic.jms.common.JMSException var5) {
         return ((JMSXAConnection)this.computeJMSConnection(startTime, physical, var5)).createXASession();
      }
   }
}
