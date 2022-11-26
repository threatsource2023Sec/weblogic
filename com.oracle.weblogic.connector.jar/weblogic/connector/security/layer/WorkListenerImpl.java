package weblogic.connector.security.layer;

import javax.resource.spi.work.WorkEvent;
import javax.resource.spi.work.WorkListener;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.security.SubjectStack;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class WorkListenerImpl extends SecureBaseImpl implements WorkListener {
   public WorkListenerImpl(WorkListener listener, SubjectStack adapterLayer, AuthenticatedSubject kernelId) {
      super(listener, adapterLayer, "WorkListener", kernelId);
   }

   public void workAccepted(WorkEvent evt) {
      this.push();

      try {
         ((WorkListener)this.myObj).workAccepted(evt);
      } catch (Throwable var6) {
         ConnectorLogger.logUnexpectedExceptionDuringWorkEventNotification("WORK_ACCEPTED", var6);
      } finally {
         this.pop();
      }

   }

   public void workCompleted(WorkEvent evt) {
      this.push();

      try {
         ((WorkListener)this.myObj).workCompleted(evt);
      } catch (Throwable var6) {
         ConnectorLogger.logUnexpectedExceptionDuringWorkEventNotification("WORK_COMPLETED", var6);
      } finally {
         this.pop();
      }

   }

   public void workRejected(WorkEvent evt) {
      this.push();

      try {
         ((WorkListener)this.myObj).workRejected(evt);
      } catch (Throwable var6) {
         ConnectorLogger.logUnexpectedExceptionDuringWorkEventNotification("WORK_REJECTED", var6);
      } finally {
         this.pop();
      }

   }

   public void workStarted(WorkEvent evt) {
      this.push();

      try {
         ((WorkListener)this.myObj).workStarted(evt);
      } catch (Throwable var6) {
         ConnectorLogger.logUnexpectedExceptionDuringWorkEventNotification("WORK_STARTED", var6);
      } finally {
         this.pop();
      }

   }
}
