package weblogic.management.rest.lib.bean.utils;

import java.util.HashMap;
import java.util.Map;

public class BeanResourceRegistryCategory {
   private Map customDefs = new HashMap();
   private ResourceDefs defaultDefs;

   public BeanResourceRegistryCategory(ResourceDef defaultDef, String... trees) {
      this.defaultDefs = new ResourceDefs(defaultDef, trees);
   }

   public void register(String beanIntf, String resourceName, ResourceDef def, String... beanTrees) {
      Map name2Defs = (Map)this.customDefs.get(beanIntf);
      if (name2Defs == null) {
         name2Defs = new HashMap();
         this.customDefs.put(beanIntf, name2Defs);
      }

      ResourceDefs defs = (ResourceDefs)((Map)name2Defs).get(resourceName);
      if (defs == null) {
         defs = new ResourceDefs(def, beanTrees);
         ((Map)name2Defs).put(resourceName, defs);
      } else {
         defs.register(def, beanTrees);
      }

   }

   public ResourceDefs get(String beanIntf, String resourceName) {
      Map name2Defs = this.get(beanIntf);
      if (name2Defs != null) {
         ResourceDefs defs = (ResourceDefs)name2Defs.get(resourceName);
         if (defs != null) {
            return defs;
         }
      }

      return this.defaultDefs;
   }

   public Map get(String beanIntf) {
      return (Map)this.customDefs.get(beanIntf);
   }
}
