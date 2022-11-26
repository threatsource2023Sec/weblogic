package weblogic.nodemanager.rest.model;

import org.glassfish.admin.rest.composite.RestModel;
import org.glassfish.admin.rest.composite.RestModelImpl;
import org.glassfish.admin.rest.composite.metadata.ReadOnly;

public class Domain extends RestModelImpl implements RestModel {
   private String name;

   @ReadOnly
   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
