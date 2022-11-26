package weblogic.application.internal.flow;

import weblogic.application.ApplicationContextInternal;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.management.DeploymentException;

public final class CheckLibraryReferenceFlow extends BaseFlow {
   public CheckLibraryReferenceFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   public void prepare() throws DeploymentException {
      try {
         LibraryLoggingUtils.verifyLibraryReferences(this.appCtx.getLibraryManagerAggregate());
      } catch (LoggableLibraryProcessingException var2) {
         throw new DeploymentException(var2.getLoggable().getMessage());
      }
   }
}
