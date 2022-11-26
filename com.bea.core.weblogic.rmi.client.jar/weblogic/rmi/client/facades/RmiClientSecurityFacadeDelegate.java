package weblogic.rmi.client.facades;

import org.jvnet.hk2.annotations.Contract;
import weblogic.security.acl.internal.AuthenticatedSubject;

@Contract
public interface RmiClientSecurityFacadeDelegate {
   AuthenticatedSubject doGetCurrentSubject(AuthenticatedSubject var1);

   boolean doIsKernelIdentity(AuthenticatedSubject var1);

   String doGetUsername(AuthenticatedSubject var1);

   boolean doIsUserAnonymous(AuthenticatedSubject var1);

   AuthenticatedSubject doGetAnonymousSubject();
}
