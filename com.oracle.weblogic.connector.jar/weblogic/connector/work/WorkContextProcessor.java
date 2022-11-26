package weblogic.connector.work;

import javax.resource.spi.work.WorkContext;
import weblogic.connector.security.SubjectStack;
import weblogic.connector.security.layer.WorkContextWrapper;
import weblogic.security.acl.internal.AuthenticatedSubject;

public interface WorkContextProcessor {
   String VALIDATION_OK = null;

   Class getSupportedContextClass();

   String validate(WorkContextWrapper var1, WorkRuntimeMetadata var2);

   WMPreference getpreferredWM(WorkContextWrapper var1);

   void setupContext(WorkContextWrapper var1, WorkRuntimeMetadata var2) throws Exception;

   void cleanupContext(WorkContextWrapper var1, boolean var2, WorkRuntimeMetadata var3) throws Exception;

   WorkContextWrapper createWrapper(WorkContext var1, SubjectStack var2, AuthenticatedSubject var3);

   public static enum WMPreference {
      defaultWM,
      longRunningWM;
   }
}
