package weblogic.ejb.container.interfaces;

import weblogic.ejb20.interfaces.PrincipalNotFoundException;
import weblogic.security.acl.internal.AuthenticatedSubject;

public interface ISecurityHelper {
   AuthenticatedSubject getSubjectForPrincipal(String var1) throws PrincipalNotFoundException;

   boolean isCallerInRole(String var1, String var2, String var3);
}
