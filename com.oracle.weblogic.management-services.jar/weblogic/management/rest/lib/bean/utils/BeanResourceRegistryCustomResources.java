package weblogic.management.rest.lib.bean.utils;

import java.util.HashMap;
import java.util.Map;

public class BeanResourceRegistryCustomResources {
   private BeanResourceRegistryCategory customResources = new BeanResourceRegistryCategory((ResourceDef)null, new String[0]);
   private Map alwaysVisibleToPartitions = new HashMap();
   private Map internal = new HashMap();

   public BeanResourceRegistryCategory getCustomResources() {
      return this.customResources;
   }

   public void register(String beanIntf, String resourceName, ResourceDef def, String... beanTrees) {
      this.register(beanIntf, true, resourceName, def, beanTrees);
   }

   public void register(String beanIntf, Boolean alwaysVisibleToPartitions, String resourceName, ResourceDef def, String... beanTrees) {
      this.register(beanIntf, alwaysVisibleToPartitions, false, resourceName, def, beanTrees);
   }

   public void register(String beanIntf, Boolean alwaysVisibleToPartitions, Boolean internal, String resourceName, ResourceDef def, String... beanTrees) {
      this.customResources.register(beanIntf, resourceName, def, beanTrees);
      Map map = (Map)this.alwaysVisibleToPartitions.get(beanIntf);
      if (map == null) {
         map = new HashMap();
         this.alwaysVisibleToPartitions.put(beanIntf, map);
      }

      ((Map)map).put(resourceName, alwaysVisibleToPartitions);
      map = (Map)this.internal.get(beanIntf);
      if (map == null) {
         map = new HashMap();
         this.internal.put(beanIntf, map);
      }

      ((Map)map).put(resourceName, internal);
   }

   public Boolean isAlwaysVisibleToPartitions(String beanIntf, String resourceName) {
      return (Boolean)((Map)this.alwaysVisibleToPartitions.get(beanIntf)).get(resourceName);
   }

   public Boolean isInternal(String beanIntf, String resourceName) {
      return (Boolean)((Map)this.internal.get(beanIntf)).get(resourceName);
   }

   public ResourceDefs get(String beanIntf, String resourceName) {
      return this.customResources.get(beanIntf, resourceName);
   }

   public Map get(String beanIntf) {
      return this.customResources.get(beanIntf);
   }
}
