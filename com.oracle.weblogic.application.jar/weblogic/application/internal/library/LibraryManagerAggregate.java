package weblogic.application.internal.library;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import weblogic.application.library.Library;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryProvider;
import weblogic.application.utils.XMLWriter;

public class LibraryManagerAggregate {
   private static final LibraryRegistry libReg = LibraryRegistry.getRegistry();
   private final Collection libraryManagers = new HashSet();
   private final Map moduleMapping = new HashMap();
   private LibraryManager appLevelLibraryManager = null;
   private LibraryManager optionalPackagesManager = null;
   private String partitionName = null;

   public void setAppLevelLibraryManager(LibraryManager appLevelLibraryManager) {
      this.appLevelLibraryManager = appLevelLibraryManager;
      this.addLibraryManager(appLevelLibraryManager);
   }

   public void setOptionalPackagesManager(LibraryManager optionalPackagesManager) {
      this.optionalPackagesManager = optionalPackagesManager;
      this.addLibraryManager(optionalPackagesManager);
   }

   public void setPartitionName(String partitionName) {
      this.partitionName = partitionName;
   }

   public LibraryManager getOptionalPackagesManager() {
      return this.optionalPackagesManager;
   }

   public void addLibraryManager(String moduleId, LibraryManager mgr) {
      this.addLibraryManager(mgr);
      this.moduleMapping.put(moduleId, mgr);
   }

   public void removeLibraryManager(String moduleId, LibraryManager mgr) {
      this.libraryManagers.remove(mgr);
      this.moduleMapping.remove(moduleId);
   }

   public LibraryProvider getLibraryProvider(String moduleId) {
      return (LibraryProvider)(moduleId == null ? this.appLevelLibraryManager : (LibraryProvider)this.moduleMapping.get(moduleId));
   }

   public void addLibraryManager(LibraryManager mgr) {
      this.libraryManagers.add(mgr);
   }

   public boolean hasUnresolvedRefs() {
      Iterator iter = this.libraryManagers.iterator();

      LibraryManager mgr;
      do {
         if (!iter.hasNext()) {
            return false;
         }

         mgr = (LibraryManager)iter.next();
      } while(!mgr.hasUnresolvedReferences());

      return true;
   }

   public Library[] getUnreferencedLibraries() {
      Collection rtn = libReg.getAll(this.partitionName);
      Iterator iter = this.libraryManagers.iterator();

      while(iter.hasNext()) {
         LibraryManager m = (LibraryManager)iter.next();
         Library[] libs = m.getReferencedLibraries();

         for(int i = 0; i < libs.length; ++i) {
            rtn.remove(libs[i]);
         }
      }

      return (Library[])((Library[])rtn.toArray(new Library[rtn.size()]));
   }

   public String getUnresolvedRefsError() {
      StringBuffer rtn = new StringBuffer();
      Iterator iter = this.libraryManagers.iterator();

      while(iter.hasNext()) {
         LibraryManager mgr = (LibraryManager)iter.next();
         if (mgr.hasUnresolvedReferences()) {
            if (rtn.length() > 0) {
               rtn.append(" ");
            }

            mgr.getUnresolvedReferencesError(rtn);
         }
      }

      return rtn.toString();
   }

   public void writeDiagnosticImage(XMLWriter writer) {
      Iterator iter = this.libraryManagers.iterator();

      while(iter.hasNext()) {
         LibraryManager mgr = (LibraryManager)iter.next();
         mgr.writeDiagnosticImage(writer);
      }

   }
}
