package weblogic.security.service;

import java.security.PrivilegedAction;

/** @deprecated */
@Deprecated
public class GetKernelIdentityAction implements PrivilegedAction {
   public static final GetKernelIdentityAction THE_ONE = new GetKernelIdentityAction();

   private GetKernelIdentityAction() {
   }

   public Object run() {
      return SecurityManager.getKernelIdentity();
   }
}
