package weblogic.management.mbeanservers.domainruntime.internal;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.visibility.utils.MBeanNameUtil;

public class ManagedMBeanServerObjectNameManager {
   private String location;
   private Map objectNamesByScoped = new MyLinkedHashMap();
   private Map scopedByObjectName = new MyLinkedHashMap();
   private Map objectNameExceptions = new HashMap();
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXDomain");

   public synchronized void registerObject(ObjectName name, ObjectName scoped) {
      this.scopedByObjectName.put(name, scoped);
      this.objectNamesByScoped.put(scoped, name);
      if (name != null && name.getKeyProperty("Location") != null) {
         this.objectNameExceptions.put(scoped, name);
      }

   }

   public synchronized ObjectName unregisterObjectInstance(ObjectName instance) {
      this.objectNameExceptions.remove(instance);
      ObjectName unregistered = (ObjectName)this.objectNamesByScoped.get(instance);
      if (unregistered == null) {
         return null;
      } else {
         this.unregisterObject(unregistered);
         return unregistered;
      }
   }

   public synchronized void unregisterObject(ObjectName name) {
      Object instance = this.scopedByObjectName.remove(name.getCanonicalName());
      ObjectName removed = (ObjectName)this.objectNamesByScoped.remove(instance);
   }

   public ManagedMBeanServerObjectNameManager(String location) {
      this.location = location;
   }

   public synchronized ObjectName lookupObjectName(ObjectName instance) {
      ObjectName result = (ObjectName)this.objectNamesByScoped.get(instance);
      if (result == null) {
         result = (ObjectName)this.objectNameExceptions.get(instance);
      }

      if (result == null) {
         result = MBeanNameUtil.removeLocation(instance);
         this.registerObject(result, instance);
      }

      return result;
   }

   public synchronized ObjectName lookupScopedObjectName(ObjectName name) {
      if (name == null) {
         return null;
      } else {
         ObjectName result = (ObjectName)this.scopedByObjectName.get(name);
         if (result == null) {
            if (name.getKeyProperty("Location") != null && this.objectNamesByScoped.get(name) != null) {
               if (debug.isDebugEnabled()) {
                  debug.debug("ManagedMBeanServerObjectNameManager: lookupScopedObjectName existing Location entry found for " + name);
               }

               return name;
            }

            result = MBeanNameUtil.addLocation(name, this.location);
            this.registerObject(name, result);
         }

         return result;
      }
   }

   public static class MyLinkedHashMap extends LinkedHashMap {
      private static final long serialVersionUID = 1L;
      private static final int MAX_ENTRIES = Integer.parseInt(System.getProperty("weblogic.management.domainruntime.ObjectNameCacheSize", "100"));

      protected boolean removeEldestEntry(Map.Entry eldest) {
         return this.size() > MAX_ENTRIES;
      }
   }
}
