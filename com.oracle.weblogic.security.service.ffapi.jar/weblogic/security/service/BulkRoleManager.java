package weblogic.security.service;

import java.util.List;
import java.util.Map;
import weblogic.security.acl.internal.AuthenticatedSubject;

public interface BulkRoleManager {
   Map getRoles(AuthenticatedSubject var1, List var2, ContextHandler var3);
}
