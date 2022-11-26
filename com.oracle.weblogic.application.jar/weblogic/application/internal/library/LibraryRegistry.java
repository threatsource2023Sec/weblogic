package weblogic.application.internal.library;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import weblogic.application.Type;
import weblogic.application.internal.Controls;
import weblogic.application.internal.library.util.NodeModificationException;
import weblogic.application.internal.library.util.SortedNodeTree;
import weblogic.application.library.LibraryConstants;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryReference;

public class LibraryRegistry {
   private static LibraryRegistry registry;
   private final SortedNodeTree libTree = new SortedNodeTree();
   Map singletonAutoRefLibNames = new HashMap();

   LibraryRegistry() {
   }

   public static synchronized LibraryRegistry getRegistry() {
      if (registry == null) {
         if (Controls.exposedomainlibraryregistry.enabled) {
            registry = new HierarchicalLibraryRegistry();
         } else {
            registry = new LibraryRegistryManager();
         }
      }

      return registry;
   }

   public synchronized void register(LibraryDefinition def, String registryName) throws LibraryRegistrationException {
      if (registryName != null && !registryName.isEmpty()) {
         this.validate(def);
         this.verifyAutoRefLibraryIsSingleton(def.getType(), def.getAutoRef(), def.getName());
         RegistryKey[] key = RegistryKey.newInstance(def);
         if (this.libTree.hasElement(key)) {
            throw new LibraryRegistrationException("Cannot register the same Library twice - this Library has already been registered: " + def.toString());
         } else {
            try {
               this.libTree.put(key, def);
            } catch (NodeModificationException var5) {
               this.handleNodeModificationException(def, var5);
            }

         }
      } else {
         throw new IllegalArgumentException("registryName must not be null or empty");
      }
   }

   private void validate(LibraryDefinition def) throws LibraryRegistrationException {
      if (def.getName() == null) {
         throw new LibraryRegistrationException(LibraryConstants.NAME_MUST_BE_SET_ERROR);
      } else if (def.getImplementationVersion() != null && def.getSpecificationVersion() == null) {
         throw new LibraryRegistrationException(LibraryConstants.NO_IMPL_WITHOUT_SPEC_ERROR);
      }
   }

   public LibraryDefinition lookup(LibraryReference ref, String registryName) {
      if (registryName != null && !registryName.isEmpty()) {
         return this.lookup(RegistryKey.newInstance(ref), ref.getExactMatch(), ref.getType());
      } else {
         throw new IllegalArgumentException("registryName must not be null or empty");
      }
   }

   private synchronized LibraryDefinition lookup(RegistryKey[] key, boolean exactMatch, Type refType) {
      LibraryLookup lookup = new LibraryLookup(key, exactMatch);
      this.libTree.traverse(lookup);
      LibraryDefinition rtn = lookup.getMatch();
      return rtn != null && refType != null && refType != rtn.getType() ? null : rtn;
   }

   public synchronized void remove(LibraryDefinition def, String registryName) {
      if (registryName != null && !registryName.isEmpty()) {
         try {
            this.validate(def);
         } catch (LibraryRegistrationException var4) {
            return;
         }

         this.clearAutoRefLibrarySingletonChecker(def.getType(), def.getAutoRef(), def.getName());
         this.libTree.remove(RegistryKey.newInstance(def));
      } else {
         throw new IllegalArgumentException("registryName must not be null or empty");
      }
   }

   public synchronized int size() {
      return this.libTree.size();
   }

   public synchronized String toString() {
      return this.libTree.toString();
   }

   public Collection getAll(String registryName) {
      if (registryName != null && !registryName.isEmpty()) {
         return this.getAll();
      } else {
         throw new IllegalArgumentException("registryName must not be null or empty");
      }
   }

   public synchronized Collection getAll() {
      Collection rtn = new HashSet();
      this.libTree.getAll(rtn);
      return rtn;
   }

   private void handleNodeModificationException(LibraryDefinition def, NodeModificationException ex) throws LibraryRegistrationException {
      String keyName = null;
      if (ex.getDepth() == 2) {
         keyName = LibraryConstants.SPEC_VERSION_NAME;
      } else if (ex.getDepth() == 3) {
         keyName = LibraryConstants.IMPL_VERSION_NAME;
      }

      String message = null;
      if (ex.getType() == NodeModificationException.Type.ADDING_VALUE_TO_NON_LEAF_NODE) {
         message = "Must provide " + keyName + " for Library, because it is set for all other registered Libraries with name \"" + def.getName() + "\"";
      } else if (ex.getType() == NodeModificationException.Type.ADDING_EDGE_TO_LEAF_NODE) {
         message = "Cannot register Library \"" + def.getName() + "\" with " + keyName + " set, because other registered Libraries with the same name do not have " + keyName + " set";
      }

      throw new LibraryRegistrationException(message);
   }

   public void verifyAutoRefLibraryIsSingleton(Type type, LibraryConstants.AutoReferrer[] autoRefs, String name) throws LibraryRegistrationException {
      synchronized(this.singletonAutoRefLibNames) {
         if (autoRefs != null && autoRefs.length > 0) {
            String singletonAutoRefLibName = (String)this.singletonAutoRefLibNames.get(type);
            if (singletonAutoRefLibName == null) {
               this.singletonAutoRefLibNames.put(type, name);
            } else if (!singletonAutoRefLibName.equals(name)) {
               throw new LibraryRegistrationException("Only one auto reference library allowed for type " + type + ". " + singletonAutoRefLibName + " is already deployed as an auto reference library");
            }
         }

      }
   }

   public void clearAutoRefLibrarySingletonChecker(Type type, LibraryConstants.AutoReferrer[] autoRefs, String name) {
      synchronized(this.singletonAutoRefLibNames) {
         if (autoRefs != null && autoRefs.length > 0) {
            String singletonAutoRefLibName = (String)this.singletonAutoRefLibNames.get(type);
            if (singletonAutoRefLibName != null && singletonAutoRefLibName.equals(name)) {
               this.singletonAutoRefLibNames.remove(type);
            }
         }

      }
   }
}
