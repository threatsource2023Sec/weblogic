package weblogic.application.internal.library;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryReference;
import weblogic.diagnostics.debug.DebugLogger;

public class HierarchicalLibraryRegistry extends LibraryRegistry {
   private final LibraryRegistry mainRegistry = new LibraryRegistry();
   private final Map subRegistries = new HashMap();
   private static final DebugLogger debugger = DebugLogger.getDebugLogger("DebugAppContainer");

   HierarchicalLibraryRegistry() {
   }

   public synchronized void register(LibraryDefinition def, String subRegistryName) throws LibraryRegistrationException {
      if (subRegistryName != null && !subRegistryName.isEmpty()) {
         if (!"DOMAIN".equals(subRegistryName)) {
            LibraryRegistry subRegistry = (LibraryRegistry)this.subRegistries.get(subRegistryName);
            if (subRegistry == null) {
               subRegistry = new LibraryRegistry();
               if (debugger.isDebugEnabled()) {
                  debugger.debug("Sub-registry not found, creating one " + subRegistryName);
               }

               this.subRegistries.put(subRegistryName, subRegistry);
            }

            if (debugger.isDebugEnabled()) {
               debugger.debug("Registering to sub-registry " + subRegistryName + " library definition " + def);
            }

            subRegistry.register(def, subRegistryName);
         } else {
            if (debugger.isDebugEnabled()) {
               debugger.debug("Registering to main registry library definition " + def);
            }

            this.mainRegistry.register(def, subRegistryName);
         }

      } else {
         throw new IllegalArgumentException("registryName must not be null or empty");
      }
   }

   public LibraryDefinition lookup(LibraryReference ref, String subRegistryName) {
      if (subRegistryName != null && !subRegistryName.isEmpty()) {
         if (debugger.isDebugEnabled()) {
            debugger.debug("Looking up library given sub-registry name \"" + subRegistryName == null ? "" : subRegistryName + "\" " + ref);
         }

         LibraryDefinition foundLibraryDefinition;
         if (!"DOMAIN".equals(subRegistryName)) {
            LibraryRegistry subRegistry = (LibraryRegistry)this.subRegistries.get(subRegistryName);
            if (subRegistry != null) {
               foundLibraryDefinition = subRegistry.lookup(ref, subRegistryName);
               if (foundLibraryDefinition != null) {
                  if (debugger.isDebugEnabled()) {
                     debugger.debug("Looking up library given sub-registry name \"" + subRegistryName == null ? "" : subRegistryName + "\" " + ref + ", entry found: " + foundLibraryDefinition);
                  }

                  return foundLibraryDefinition;
               }

               if (debugger.isDebugEnabled()) {
                  debugger.debug("Looking up library given sub-registry name \"" + subRegistryName == null ? "" : subRegistryName + "\" " + ref + ": Not found");
               }
            } else if (debugger.isDebugEnabled()) {
               debugger.debug("Sub-registry not found " + subRegistryName);
            }
         }

         foundLibraryDefinition = this.mainRegistry.lookup(ref, subRegistryName);
         if (foundLibraryDefinition != null) {
            debugger.debug("Looking up library in main registry given sub-registry name \"" + subRegistryName == null ? "" : subRegistryName + "\" " + ref + ", entry found: " + foundLibraryDefinition);
         } else {
            debugger.debug("Looking up library in main registry given sub-registry name \"" + subRegistryName == null ? "" : subRegistryName + "\" " + ref + ": Not found");
         }

         return foundLibraryDefinition;
      } else {
         throw new IllegalArgumentException("registryName must not be null or empty");
      }
   }

   public synchronized void remove(LibraryDefinition def, String subRegistryName) {
      if (subRegistryName != null && !subRegistryName.isEmpty()) {
         if (debugger.isDebugEnabled()) {
            debugger.debug("Removing definition " + def + " given sub-registry " + "name \"" + subRegistryName == null ? "" : subRegistryName + "\"");
         }

         if (!"DOMAIN".equals(subRegistryName)) {
            LibraryRegistry subRegistry = (LibraryRegistry)this.subRegistries.get(subRegistryName);
            if (subRegistry == null) {
               throw new IllegalStateException("Attempting to remove library  definition " + def + "but Library sub-registry " + subRegistryName + " not found");
            }

            subRegistry.remove(def, subRegistryName);
         } else {
            this.mainRegistry.remove(def, subRegistryName);
         }

      } else {
         throw new IllegalArgumentException("registryName must not be null or empty");
      }
   }

   public synchronized int size() {
      int size = this.mainRegistry.size();

      LibraryRegistry subRegistry;
      for(Iterator var2 = this.subRegistries.values().iterator(); var2.hasNext(); size += subRegistry.size()) {
         subRegistry = (LibraryRegistry)var2.next();
      }

      return size;
   }

   public synchronized String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("-------------\n");
      sb.append("Main Registry\n");
      sb.append("-------------\n");
      sb.append(this.mainRegistry.toString());
      sb.append("-------------\n");
      Iterator var2 = this.subRegistries.keySet().iterator();

      while(var2.hasNext()) {
         String subRegistryName = (String)var2.next();
         sb.append("-------------\n");
         sb.append("Sub Registry: ").append(subRegistryName).append("\n");
         sb.append("-------------\n");
         sb.append(((LibraryRegistry)this.subRegistries.get(subRegistryName)).toString());
         sb.append("-------------\n");
      }

      return sb.toString();
   }

   public synchronized Collection getAll() {
      return this.mainRegistry.getAll();
   }
}
