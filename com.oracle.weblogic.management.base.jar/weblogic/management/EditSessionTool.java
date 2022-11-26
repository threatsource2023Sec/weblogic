package weblogic.management;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.provider.EditAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;

@Contract
public interface EditSessionTool {
   void pushEditContext(AuthenticatedSubject var1, EditAccess var2);

   void popEditContext(AuthenticatedSubject var1);

   EditAccess getEditContext();
}
