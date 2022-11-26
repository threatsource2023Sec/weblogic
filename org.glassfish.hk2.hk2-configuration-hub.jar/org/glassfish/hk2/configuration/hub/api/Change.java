package org.glassfish.hk2.configuration.hub.api;

import java.util.List;

public interface Change {
   ChangeCategory getChangeCategory();

   Type getChangeType();

   String getInstanceKey();

   Instance getInstanceValue();

   Instance getOriginalInstanceValue();

   List getModifiedProperties();

   public static enum ChangeCategory {
      REMOVE_TYPE,
      ADD_TYPE,
      ADD_INSTANCE,
      REMOVE_INSTANCE,
      MODIFY_INSTANCE;
   }
}
