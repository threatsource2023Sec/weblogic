package weblogic.connector.work;

import java.security.AccessController;
import javax.resource.spi.work.SecurityContext;
import javax.resource.spi.work.WorkContext;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import weblogic.connector.common.Debug;
import weblogic.connector.security.SubjectStack;
import weblogic.connector.security.layer.SecurityContextImpl;
import weblogic.connector.security.layer.WorkContextWrapper;
import weblogic.connector.security.work.CallbackHandlerFactory;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.container.jca.jaspic.ConnectorCallbackHandler;
import weblogic.security.service.PrivilegedActions;

public class SecurityContextProcessor extends BaseWorkContextProcessor {
   private SubjectStack stack;
   private CallbackHandlerFactory callbackHandlerFactory;
   AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public SecurityContextProcessor(SubjectStack stack, CallbackHandlerFactory callbackHandlerFactory) {
      this.stack = stack;
      this.callbackHandlerFactory = callbackHandlerFactory;
   }

   public CallbackHandlerFactory getCallbackHandlerFactory() {
      return this.callbackHandlerFactory;
   }

   public Class getSupportedContextClass() {
      return SecurityContext.class;
   }

   public void setupContext(WorkContextWrapper context, WorkRuntimeMetadata work) throws LoginException {
      SecurityContextImpl sic = (SecurityContextImpl)context;
      ConnectorCallbackHandler handler = (ConnectorCallbackHandler)this.callbackHandlerFactory.getCallBackHandler();
      Subject executionSubject = new Subject();
      Subject serviceSubject = null;
      sic.setupSecurityContext(handler, executionSubject, (Subject)serviceSubject);
      if (Debug.isWorkEnabled()) {
         Debug.work("SecurityContextProcessor: setupContext: new executionSubject:" + executionSubject);
      }

      AuthenticatedSubject subject = null;
      subject = handler.setupExecutionSubject(executionSubject);
      if (Debug.isWorkEnabled()) {
         Debug.work("SecurityContextProcessor: setupContext: use WLS Subject:" + subject);
      }

      this.stack.pushGivenSubject(this.kernelId, subject);
      work.setEstablishedSubject(subject);
   }

   public void cleanupContext(WorkContextWrapper context, boolean executionSuccess, WorkRuntimeMetadata work) {
      if (Debug.isWorkEnabled()) {
         Debug.work("SecurityContextProcessor: cleanupContext: will clean up established subject: " + work.getEstablishedSubject());
      }

      if (work.getEstablishedSubject() != null) {
         work.setEstablishedSubject((AuthenticatedSubject)null);
         this.stack.popSubject(this.kernelId);
      }

   }

   public WorkContextWrapper createWrapper(WorkContext originalWorkContext, SubjectStack adapterLayer, AuthenticatedSubject kernelId) {
      return new SecurityContextImpl((SecurityContext)originalWorkContext, adapterLayer, kernelId);
   }
}
