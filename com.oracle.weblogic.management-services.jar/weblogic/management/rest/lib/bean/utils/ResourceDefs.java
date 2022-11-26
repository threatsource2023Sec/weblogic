package weblogic.management.rest.lib.bean.utils;

import java.util.HashMap;
import java.util.Map;

public class ResourceDefs {
   private Map defs = new HashMap();

   public ResourceDefs() {
   }

   public ResourceDefs(ResourceDef def, String... beanTrees) {
      this.register(def, beanTrees);
   }

   public void register(ResourceDef def, String... beanTrees) {
      if (beanTrees.length < 1) {
         this.defs.put("any", def);
      } else {
         String[] var3 = beanTrees;
         int var4 = beanTrees.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String beanTree = var3[var5];
            this.defs.put(beanTree, def);
         }
      }

   }

   public ResourceDef get(String beanTree) {
      ResourceDef def = (ResourceDef)this.defs.get(beanTree);
      if (def != null) {
         return def;
      } else {
         def = (ResourceDef)this.defs.get("any");
         return def != null ? def : null;
      }
   }
}
