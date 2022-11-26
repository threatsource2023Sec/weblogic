package org.glassfish.admin.rest.utils;

import java.security.AccessController;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class WebAppUtil {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public static String getThreadWebAppId() {
      return ComponentInvocationContextManager.getInstance(kernelId).getCurrentComponentInvocationContext().getApplicationId();
   }
}
