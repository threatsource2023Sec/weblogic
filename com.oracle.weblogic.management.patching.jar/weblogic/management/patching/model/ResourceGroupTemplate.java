package weblogic.management.patching.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import weblogic.management.patching.ApplicationProperties;

public class ResourceGroupTemplate implements Target, Serializable {
   private String rgtName;
   private boolean isTargeted = false;
   private List applicationPropertyList;

   public void setTargeted(boolean isTargeted) {
      this.isTargeted = isTargeted;
   }

   public boolean isTargeted() {
      return this.isTargeted;
   }

   public void applyTargetedAndPropagateValue() {
      this.setTargeted(true);
   }

   public ResourceGroupTemplate(String rgtName) {
      this.rgtName = rgtName;
      this.applicationPropertyList = new ArrayList();
   }

   public String getName() {
      return this.rgtName;
   }

   public List getApplicationPropertyList() {
      return this.applicationPropertyList;
   }

   public void addApplicationProperties(ApplicationProperties app) {
      this.applicationPropertyList.add(app);
   }
}
