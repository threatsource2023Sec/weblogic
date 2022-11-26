package weblogic.management.jmx;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.jmx.modelmbean.WLSModelMBeanInstanceContext;

public abstract class ObjectNameManagerBase implements ObjectNameManager {
   private final Map objectNamesByObject;
   private final Map objectsByObjectName;
   private Class[] mappedBaseClass;
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXCore");

   public ObjectNameManagerBase(Class clazz) {
      this(new Class[]{clazz});
   }

   public ObjectNameManagerBase(Class[] mappedBaseClass) {
      this.mappedBaseClass = new Class[0];
      this.objectNamesByObject = Collections.synchronizedMap(new IdentityHashMap());
      this.objectsByObjectName = Collections.synchronizedMap(new HashMap());
      this.mappedBaseClass = mappedBaseClass;
   }

   public boolean isClassMapped(Class clazz) {
      if (clazz.isPrimitive()) {
         return false;
      } else if (clazz == String.class) {
         return false;
      } else {
         for(int i = 0; i < this.mappedBaseClass.length; ++i) {
            if (this.mappedBaseClass[i].isAssignableFrom(clazz)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean isFiltered(Object instance) {
      return false;
   }

   public ObjectName lookupObjectName(Object instance) {
      return (ObjectName)this.objectNamesByObject.get(instance);
   }

   public ObjectName lookupRegisteredObjectName(Object instance) {
      return (ObjectName)this.objectNamesByObject.get(instance);
   }

   public ObjectName buildObjectName(Object instance) {
      return this.buildObjectName(instance, (WLSModelMBeanInstanceContext)null);
   }

   public ObjectName buildObjectName(Object instance, WLSModelMBeanInstanceContext instanceContext) {
      return null;
   }

   public Object lookupObject(ObjectName name) {
      if (name == null) {
         return null;
      } else {
         Object instance = this.objectsByObjectName.get(name.getCanonicalName());
         return instance;
      }
   }

   public void registerObject(ObjectName name, Object instance) {
      if (name != null && instance != null) {
         String canonicalName = name.getCanonicalName();
         if (debug.isDebugEnabled()) {
            debug.debug("ObjectNameManagerBase.registerObject name: " + name + " instance: " + instance);
         }

         Object existing = this.objectsByObjectName.put(canonicalName, instance);
         if (existing != null && existing != instance && debug.isDebugEnabled()) {
            debug.debug("Registered more than one instance with the same objectName : " + name + " new:" + instance + " existing " + existing);
         }

         this.objectNamesByObject.put(instance, name);
      } else {
         throw new IllegalArgumentException("Neither name or instance may be null Name:" + name + " Instance:" + instance);
      }
   }

   public ObjectName unregisterObjectInstance(Object instance) {
      if (debug.isDebugEnabled()) {
         debug.debug("ObjectNameManagerBase.unregisterObjectInstance: " + instance);
      }

      ObjectName unregistered = (ObjectName)this.objectNamesByObject.get(instance);
      if (unregistered == null) {
         return null;
      } else {
         this.unregisterObject(unregistered);
         return unregistered;
      }
   }

   public void unregisterObject(ObjectName name) {
      if (debug.isDebugEnabled()) {
         debug.debug("ObjectNameManagerBase.unregisterObject: " + name);
      }

      String canonicalName = name.getCanonicalName();
      Object instance = this.objectsByObjectName.remove(canonicalName);
      if (instance == null) {
         if (debug.isDebugEnabled()) {
            debug.debug("ObjectNameManagerBase.unregisterObject instance is null ");
         }

      } else {
         ObjectName removed = (ObjectName)this.objectNamesByObject.remove(instance);
         if (removed == null || !removed.getCanonicalName().equals(canonicalName)) {
            throw new IllegalArgumentException("Failed to properly unregister " + instance + " for ObjectName " + name);
         }
      }
   }

   public Collection getAllObjectNames() {
      return this.objectNamesByObject.values();
   }

   public static Hashtable quoteObjectNameEntries(Hashtable table) {
      Iterator iter = table.keySet().iterator();

      while(iter.hasNext()) {
         String key = (String)iter.next();
         String val = ((String)table.get(key)).intern();
         String newVal = quoteIfNecessary(val).intern();
         if (newVal != val) {
            table.put(key, newVal);
         }
      }

      return table;
   }

   private static String quoteIfNecessary(String s) {
      int len = s.length();
      if (len >= 2 && s.charAt(0) == '"' && s.charAt(len - 1) == '"') {
         return s;
      } else {
         int i = 0;

         while(i < len) {
            switch (s.charAt(i)) {
               case '\n':
               case '"':
               case '*':
               case ',':
               case ':':
               case '=':
               case '?':
                  return i == len ? s : ObjectName.quote(s);
               default:
                  ++i;
            }
         }

         return i == len ? s : ObjectName.quote(s);
      }
   }
}
