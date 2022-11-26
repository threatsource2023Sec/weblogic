package weblogic.application.internal.library;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryReference;
import weblogic.diagnostics.debug.DebugLogger;

public class LibraryRegistryManager extends LibraryRegistry {
   private final Map registries = new HashMap();
   private static final DebugLogger debugger = DebugLogger.getDebugLogger("DebugAppContainer");

   LibraryRegistryManager() {
   }

   public synchronized void register(LibraryDefinition def, String registryName) throws LibraryRegistrationException {
      if (registryName != null && !registryName.isEmpty()) {
         LibraryRegistry registry = (LibraryRegistry)this.registries.get(registryName);
         if (registry == null) {
            registry = new LibraryRegistry();
            if (debugger.isDebugEnabled()) {
               debugger.debug("Registry not found, creating one " + registryName);
            }

            this.registries.put(registryName, registry);
         }

         if (debugger.isDebugEnabled()) {
            debugger.debug("Registering to Registry " + registryName + " library definition " + def);
         }

         registry.register(def, registryName);
      } else {
         throw new IllegalArgumentException("registryName must not be null or empty");
      }
   }

   public LibraryDefinition lookup(LibraryReference ref, String registryName) {
      if (registryName != null && !registryName.isEmpty()) {
         if (debugger.isDebugEnabled()) {
            debugger.debug("Looking up library given Registry name \"" + registryName == null ? "" : registryName + "\" " + ref);
         }

         LibraryRegistry registry = (LibraryRegistry)this.registries.get(registryName);
         if (registry != null) {
            LibraryDefinition foundLibraryDefinition = registry.lookup(ref, registryName);
            if (foundLibraryDefinition != null) {
               if (debugger.isDebugEnabled()) {
                  debugger.debug("Looking up library given Registry name \"" + registryName == null ? "" : registryName + "\" " + ref + ", entry found: " + foundLibraryDefinition);
               }

               return foundLibraryDefinition;
            } else {
               if (debugger.isDebugEnabled()) {
                  debugger.debug("Looking up library given Registry name \"" + registryName == null ? "" : registryName + "\" " + ref + ": Not found");
               }

               return null;
            }
         } else {
            if (debugger.isDebugEnabled()) {
               debugger.debug("Registry not found " + registryName);
            }

            return null;
         }
      } else {
         throw new IllegalArgumentException("registryName must not be null or empty");
      }
   }

   public synchronized void remove(LibraryDefinition def, String registryName) {
      if (registryName != null && !registryName.isEmpty()) {
         if (debugger.isDebugEnabled()) {
            debugger.debug("Removing definition " + def + " given Registry " + "name \"" + registryName == null ? "" : registryName + "\"");
         }

         LibraryRegistry subRegistry = (LibraryRegistry)this.registries.get(registryName);
         if (subRegistry == null) {
            throw new IllegalStateException("Attempting to remove library  definition " + def + "but Library Registry " + registryName + " not found");
         } else {
            subRegistry.remove(def, registryName);
         }
      } else {
         throw new IllegalArgumentException("registryName must not be null or empty");
      }
   }

   public synchronized int size() {
      int size = 0;

      LibraryRegistry subRegistry;
      for(Iterator var2 = this.registries.values().iterator(); var2.hasNext(); size += subRegistry.size()) {
         subRegistry = (LibraryRegistry)var2.next();
      }

      return size;
   }

   public synchronized String toString() {
      StringBuilder sb = new StringBuilder();
      Iterator var2 = this.registries.keySet().iterator();

      while(var2.hasNext()) {
         String subRegistryName = (String)var2.next();
         sb.append("-------------\n");
         sb.append("Registry: ").append(subRegistryName).append("\n");
         sb.append("-------------\n");
         sb.append(((LibraryRegistry)this.registries.get(subRegistryName)).toString());
         sb.append("-------------\n");
      }

      return sb.toString();
   }

   public synchronized Collection getAll(String registryName) {
      if (registryName != null && !registryName.isEmpty()) {
         if (debugger.isDebugEnabled()) {
            debugger.debug("looking for registry with name " + registryName);
         }

         LibraryRegistry registry = (LibraryRegistry)this.registries.get(registryName);
         if (registry != null) {
            if (debugger.isDebugEnabled()) {
               debugger.debug("getAll for registry " + registryName);
            }

            return registry.getAll();
         } else {
            if (debugger.isDebugEnabled()) {
               debugger.debug("getAll: No registry found for name " + registryName);
            }

            return new HashSet();
         }
      } else {
         throw new IllegalArgumentException("registryName must not be null or empty");
      }
   }
}
