package weblogic.deployment.jms;

import java.util.HashSet;
import java.util.Iterator;
import javax.jms.JMSException;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.XASession;

public class MDBSession extends WrappedSession {
   private HashSet temporaryDests;

   protected void init(Session session, int wrapStyle, WrappedClassManager manager) throws JMSException {
      JMSSessionHolder holder;
      if (session instanceof QueueSession) {
         holder = new JMSSessionHolder((JMSConnectionHelperService)null, session, (XASession)null, 1, 1, false, System.currentTimeMillis());
      } else {
         holder = new JMSSessionHolder((JMSConnectionHelperService)null, session, (XASession)null, 2, 1, false, System.currentTimeMillis());
      }

      super.init(holder, manager);
      this.setConnectionStarted(true);
      this.setWrapStyle(wrapStyle);
      this.temporaryDests = new HashSet(3);
   }

   protected void closeChildren() {
      Iterator temporaries = this.temporaryDests.iterator();

      while(temporaries.hasNext()) {
         JMSPoolDebug.logger.debug("In MDBSession.close, deleting a temporary destination");
         Object tmpDest = temporaries.next();

         try {
            if (tmpDest instanceof TemporaryQueue) {
               ((TemporaryQueue)tmpDest).delete();
            } else if (tmpDest instanceof TemporaryTopic) {
               ((TemporaryTopic)tmpDest).delete();
            }
         } catch (Exception var4) {
         }
      }

      this.temporaryDests.clear();
      super.closeChildren();
   }

   public TemporaryQueue createTemporaryQueue() throws JMSException {
      this.checkClosed();
      TemporaryQueue tq = this.session.createTemporaryQueue();
      this.temporaryDests.add(tq);
      return tq;
   }

   public TemporaryTopic createTemporaryTopic() throws JMSException {
      this.checkClosed();
      TemporaryTopic tt = this.session.createTemporaryTopic();
      this.temporaryDests.add(tt);
      return tt;
   }

   public synchronized void close() throws JMSException {
      this.closed = true;
      this.closeChildren();
   }

   public synchronized void reOpen() {
      this.closed = false;
   }
}
