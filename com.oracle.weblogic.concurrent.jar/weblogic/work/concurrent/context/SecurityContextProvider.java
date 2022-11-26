package weblogic.work.concurrent.context;

import java.security.AccessController;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.work.concurrent.spi.ContextHandle;
import weblogic.work.concurrent.spi.ContextProvider;

public class SecurityContextProvider implements ContextProvider {
   private static final long serialVersionUID = -1995306912815840919L;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrentContext");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   protected static final SubjectManager subjectManager = SubjectManager.getSubjectManager();
   private static final SecurityContextProvider securityCP = new SecurityContextProvider();

   protected SecurityContextProvider() {
   }

   public static SecurityContextProvider getInstance() {
      return securityCP;
   }

   public ContextHandle save(Map executionProperties) {
      return new SecurityContextHandle(subjectManager.getCurrentSubject(kernelId));
   }

   public ContextHandle setup(ContextHandle contextHandle) {
      if (!(contextHandle instanceof SecurityContextHandle)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("skip Security setup: " + contextHandle);
         }

         return null;
      } else {
         SecurityContextHandle handle = (SecurityContextHandle)contextHandle;
         subjectManager.pushSubject(kernelId, handle.getSubject());
         return new SecurityContextHandle((AbstractSubject)null);
      }
   }

   public void reset(ContextHandle contextHandle) {
      if (!(contextHandle instanceof SecurityContextHandle)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("skip Security reset: " + contextHandle);
         }

      } else {
         subjectManager.popSubject(kernelId);
      }
   }

   public String getContextType() {
      return "security";
   }

   public int getConcurrentObjectType() {
      return 13;
   }

   public static class SecurityContextHandle implements ContextHandle {
      private static final long serialVersionUID = -8026254870205409542L;
      private final AbstractSubject subject;

      public SecurityContextHandle(AbstractSubject subject) {
         this.subject = subject;
      }

      public AbstractSubject getSubject() {
         return this.subject;
      }
   }
}
