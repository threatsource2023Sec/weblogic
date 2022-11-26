package weblogic.ejb.container.interfaces;

import java.util.Collection;

public interface MethodPermission {
   Collection getAllMethodDescriptors();

   Collection getAllRoleNames();

   boolean isUnchecked();
}
