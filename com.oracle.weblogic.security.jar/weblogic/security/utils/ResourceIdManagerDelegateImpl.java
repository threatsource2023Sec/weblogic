package weblogic.security.utils;

import weblogic.management.security.ResourceIdInfo;
import weblogic.management.security.internal.ResourceIdManagerDelegate;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class ResourceIdManagerDelegateImpl implements ResourceIdManagerDelegate {
   public String[] listRegisteredResourceTypes() {
      return ResourceUtils.listRegisteredResourceTypes();
   }

   public void registerResourceType(AuthenticatedSubject subject, ResourceIdInfo info) throws IllegalArgumentException {
      ResourceUtils.registerResourceType(subject, info);
   }
}
