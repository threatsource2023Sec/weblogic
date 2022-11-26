package weblogic.nodemanager.rest.model;

import org.glassfish.admin.rest.composite.RestModel;
import org.glassfish.admin.rest.composite.metadata.LegalValues;
import org.glassfish.admin.rest.composite.metadata.ReadOnly;

public interface Version extends RestModel {
   @ReadOnly
   String getVersion();

   void setVersion(String var1);

   @ReadOnly
   boolean getIsLatest();

   void setIsLatest(boolean var1);

   @ReadOnly
   @LegalValues(
      values = {"active", "deprecated"}
   )
   String getLifecycle();

   void setLifecycle(String var1);
}
