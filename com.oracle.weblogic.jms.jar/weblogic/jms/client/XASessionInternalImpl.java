package weblogic.jms.client;

import javax.jms.JMSException;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicSession;
import javax.transaction.xa.XAResource;

public class XASessionInternalImpl extends WLSessionImpl implements XASessionInternal {
   public XASessionInternalImpl(JMSXASession jmsXaSess, XAConnectionInternalImpl rXaCon) {
      super(jmsXaSess, rXaCon);
   }

   public QueueSession getQueueSession() throws JMSException {
      return ((JMSXASession)this.getPhysicalJMSSession()).getQueueSession();
   }

   public Session getSession() throws JMSException {
      return ((JMSXASession)this.getPhysicalJMSSession()).getSession();
   }

   public XAResource getXAResource() {
      return ((JMSXASession)this.getPhysicalJMSSession()).getXAResource();
   }

   public TopicSession getTopicSession() throws JMSException {
      return ((JMSXASession)this.getPhysicalJMSSession()).getTopicSession();
   }
}
