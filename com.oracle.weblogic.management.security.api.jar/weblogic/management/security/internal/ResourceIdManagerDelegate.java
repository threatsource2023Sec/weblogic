package weblogic.management.security.internal;

import weblogic.management.security.ResourceIdInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;

public interface ResourceIdManagerDelegate {
   String[] listRegisteredResourceTypes();

   void registerResourceType(AuthenticatedSubject var1, ResourceIdInfo var2) throws IllegalArgumentException;
}
