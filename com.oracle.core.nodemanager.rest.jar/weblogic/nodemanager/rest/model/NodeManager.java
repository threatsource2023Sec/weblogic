package weblogic.nodemanager.rest.model;

import org.glassfish.admin.rest.composite.RestModel;
import org.glassfish.admin.rest.composite.RestModelImpl;
import org.glassfish.admin.rest.composite.metadata.ReadOnly;

public class NodeManager extends RestModelImpl implements RestModel {
   private String version;

   @ReadOnly
   public String getVersion() {
      return this.version;
   }

   public void setVersion(String version) {
      this.version = version;
   }
}
