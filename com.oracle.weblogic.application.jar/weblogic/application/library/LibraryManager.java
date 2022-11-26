package weblogic.application.library;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import weblogic.application.Type;
import weblogic.application.internal.library.BasicLibraryData;
import weblogic.application.internal.library.LibraryRegistry;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.application.utils.LibraryUtils;
import weblogic.application.utils.XMLWriter;
import weblogic.management.runtime.LibraryRuntimeMBean;

public class LibraryManager implements LibraryProvider {
   private static final LibraryRegistry libReg = LibraryRegistry.getRegistry();
   private final List libraries;
   private final List resolvedRefs;
   private final Collection unresolvedRefs;
   private final Collection allRefs;
   private final Set libRefs;
   private final LibraryReferencer referencer;
   private final String partitionName;
   private final List autoRefLibs;

   public LibraryManager(LibraryReferencer referencer, String partitionName) {
      this(referencer, partitionName, (LibraryReference[])null);
   }

   public LibraryManager(LibraryReferencer referencer, String partitionName, LibraryReference[] refs) {
      this.libraries = new ArrayList();
      this.resolvedRefs = new ArrayList();
      this.unresolvedRefs = new ArrayList();
      this.allRefs = new HashSet();
      this.libRefs = new HashSet();
      this.autoRefLibs = new ArrayList();
      if (partitionName == null) {
         throw new IllegalArgumentException("null partitionName not allowed");
      } else {
         this.partitionName = partitionName;
         if (referencer == null) {
            throw new IllegalArgumentException("null referencer not allowed");
         } else {
            this.lookup(refs);
            this.referencer = referencer;
         }
      }
   }

   public void lookup(LibraryReference[] inRefs) {
      if (inRefs != null) {
         for(int i = 0; i < inRefs.length; ++i) {
            this.lookup(inRefs[i]);
         }

      }
   }

   private void lookup(LibraryReference inRef) {
      if (inRef != null) {
         this.findReferencedLibraries(inRef);
      }
   }

   private boolean isDuplicateRef(LibraryReference ref) {
      if (this.allRefs.contains(ref)) {
         return true;
      } else {
         this.allRefs.add(ref);
         return false;
      }
   }

   public Library[] getUnreferencedLibraries() {
      Collection rtn = libReg.getAll(this.partitionName);
      Iterator iter = this.libraries.iterator();

      while(iter.hasNext()) {
         rtn.remove(iter.next());
      }

      return (Library[])((Library[])rtn.toArray(new Library[rtn.size()]));
   }

   public boolean hasReferencedLibraries() {
      return !this.libraries.isEmpty();
   }

   public Library[] getReferencedLibraries() {
      return (Library[])((Library[])this.libraries.toArray(new Library[this.libraries.size()]));
   }

   public Library[] getAutoReferencedLibraries() {
      return (Library[])this.autoRefLibs.toArray(new Library[this.autoRefLibs.size()]);
   }

   public LibraryReferencer getReferencer() {
      return this.referencer;
   }

   public void lookupAndAddAutoReferences(Type libType, LibraryConstants.AutoReferrer referencingType) {
      Collection libs = libReg.getAll(this.partitionName);
      SortedSet autoRefLibNames = new TreeSet();
      Iterator var5 = libs.iterator();

      while(true) {
         LibraryDefinition ld;
         boolean referencerMatched;
         do {
            do {
               if (!var5.hasNext()) {
                  try {
                     var5 = autoRefLibNames.iterator();

                     while(var5.hasNext()) {
                        String name = (String)var5.next();
                        BasicLibraryData data = new BasicLibraryData(name, (String)null, (String)null, libType);
                        LibraryDefinition autoRefLD = libReg.lookup(new J2EELibraryReference(data, false, (String)null), this.partitionName);
                        this.autoRefLibs.add(autoRefLD);
                        autoRefLD.getRuntimeImpl().addReference(this.referencer);
                     }
                  } catch (IllegalSpecVersionTypeException var10) {
                  }

                  return;
               }

               ld = (LibraryDefinition)var5.next();
               referencerMatched = false;
               LibraryConstants.AutoReferrer[] autoRefs = ld.getAutoRef();

               for(int i = 0; !referencerMatched && i < autoRefs.length; ++i) {
                  referencerMatched = autoRefs[i] == referencingType;
               }
            } while(!referencerMatched);
         } while(libType != null && !libType.equals(ld.getType()));

         if (this.libraries.contains(ld)) {
            if (LibraryUtils.isDebugOn()) {
               LibraryUtils.debug("The auto-ref library: " + ld.getName() + " is explicitly referred");
            }
         } else {
            autoRefLibNames.add(ld.getName());
         }
      }
   }

   public void initializeReferencedLibraries() throws LoggableLibraryProcessingException {
      LibraryDefinition lib;
      for(Iterator iter = this.libraries.iterator(); iter.hasNext(); LibraryLoggingUtils.initLibraryDefinition(lib)) {
         lib = (LibraryDefinition)iter.next();
         if (LibraryUtils.isDebugOn()) {
            LibraryUtils.debug("Calling init on " + lib);
         }
      }

   }

   public LibraryReference[] getLibraryReferences() {
      return (LibraryReference[])((LibraryReference[])this.resolvedRefs.toArray(new LibraryReference[this.resolvedRefs.size()]));
   }

   public LibraryRuntimeMBean[] getReferencedLibraryRuntimes() {
      LibraryRuntimeMBean[] rtn = new LibraryRuntimeMBean[this.libraries.size()];
      int size = this.libraries.size();

      for(int i = 0; i < size; ++i) {
         rtn[i] = ((Library)this.libraries.get(i)).getRuntime();
      }

      return rtn;
   }

   public boolean hasUnresolvedReferences() {
      return !this.unresolvedRefs.isEmpty();
   }

   public LibraryReference[] getUnresolvedReferences() {
      return (LibraryReference[])((LibraryReference[])this.unresolvedRefs.toArray(new LibraryReference[this.unresolvedRefs.size()]));
   }

   public void resetUnresolvedReferences() {
      this.unresolvedRefs.clear();
   }

   public String getUnresolvedReferencesAsString() {
      StringBuffer rtn = new StringBuffer();
      Iterator iter = this.unresolvedRefs.iterator();

      while(iter.hasNext()) {
         LibraryReference ref = (LibraryReference)iter.next();
         rtn.append("[").append(ref.toString()).append("]");
         if (iter.hasNext()) {
            rtn.append(", ");
         }
      }

      return rtn.toString();
   }

   public void getUnresolvedReferencesError(StringBuffer sb) {
      sb.append(this.referencer.getUnresolvedError()).append(" ").append(this.getUnresolvedReferencesAsString());
   }

   public String getUnresolvedReferencesError() {
      StringBuffer rtn = new StringBuffer();
      this.getUnresolvedReferencesError(rtn);
      return rtn.toString();
   }

   private void registerUnresolvedLibraryRef(LibraryReference ref) {
      this.unresolvedRefs.add(ref);
   }

   private void registerLibrary(LibraryReference ref, Library lib) {
      if (!this.libRefs.add(ref.getCompositeEntry(lib))) {
         if (LibraryUtils.isDebugOn()) {
            LibraryUtils.debug("Ignoroning entry --> " + lib.getLocation());
         }

      } else {
         this.resolvedRefs.add(ref);
         this.libraries.add(lib);
      }
   }

   private void findReferencedLibraries(LibraryReference ref) {
      if (this.isDuplicateRef(ref)) {
         if (LibraryUtils.isDebugOn()) {
            LibraryUtils.debug("Ignoring duplicate reference: " + ref);
         }

      } else {
         LibraryDefinition library = libReg.lookup(ref, this.partitionName);
         if (LibraryUtils.isDebugOn()) {
            LibraryUtils.debug("Referenced library is " + library);
         }

         if (library == null) {
            this.registerUnresolvedLibraryRef(ref);
         } else {
            if (LibraryUtils.isDebugOn()) {
               LibraryUtils.debug("Adding library to result list: " + library);
            }

            this.registerLibrary(ref, library);
            LibraryReference[] refs = library.getLibraryReferences();
            if (refs != null) {
               for(int i = 0; i < refs.length; ++i) {
                  if (LibraryUtils.isDebugOn()) {
                     LibraryUtils.debug("Found library reference " + refs[i]);
                  }

                  this.findReferencedLibraries(refs[i]);
               }
            } else if (LibraryUtils.isDebugOn()) {
               LibraryUtils.debug("This library does not reference other libraries");
            }

         }
      }
   }

   public void addReferences() {
      Iterator iter = this.libraries.iterator();

      while(iter.hasNext()) {
         LibraryDefinition lib = (LibraryDefinition)iter.next();
         lib.getRuntimeImpl().addReference(this.referencer);
      }

   }

   public void removeReferences() {
      Iterator iter;
      LibraryDefinition lib;
      for(iter = this.libraries.iterator(); iter.hasNext(); lib.getRuntimeImpl().removeReference(this.referencer)) {
         lib = (LibraryDefinition)iter.next();
         if (LibraryUtils.isDebugOn()) {
            LibraryUtils.debug("For library: " + lib.getName());
         }

         if (LibraryUtils.isDebugOn()) {
            LibraryUtils.debug("removing its ref to:" + this.referencer.getReferencerName());
         }

         if (LibraryUtils.isDebugOn()) {
            LibraryUtils.debug("library runtime is: " + lib.getRuntimeImpl());
         }
      }

      iter = this.autoRefLibs.iterator();

      while(iter.hasNext()) {
         lib = (LibraryDefinition)iter.next();
         lib.getRuntimeImpl().removeReference(this.referencer);
      }

      this.autoRefLibs.clear();
      this.libraries.clear();
      this.libRefs.clear();
   }

   public File[] getOptionalPackages(LibraryReference[] refs) {
      if (refs == null) {
         return null;
      } else {
         Collection rtn = new ArrayList(refs.length);

         for(int i = 0; i < refs.length; ++i) {
            LibraryDefinition lib = libReg.lookup(refs[i], this.partitionName);
            if (lib != null) {
               rtn.add(lib.getLocation());
            }

            if (!this.isDuplicateRef(refs[i])) {
               if (lib == null) {
                  this.registerUnresolvedLibraryRef(refs[i]);
               } else {
                  this.registerLibrary(refs[i], lib);
               }
            }
         }

         if (rtn.isEmpty()) {
            return null;
         } else {
            return (File[])((File[])rtn.toArray(new File[rtn.size()]));
         }
      }
   }

   public void writeDiagnosticImage(XMLWriter writer) {
      if (!this.libraries.isEmpty()) {
         String refName = this.referencer.getReferencerName();
         if (refName == null) {
            refName = "app";
         }

         writer.addElement("referencer");
         writer.addElement("name", refName);
         int size = this.libraries.size();

         for(int i = 0; i < size; ++i) {
            Library l = (Library)this.libraries.get(i);
            LibraryReference r = (LibraryReference)this.resolvedRefs.get(i);
            writer.addElement("reference", r.toString());
            writer.addElement("library", l.toString());
         }

         writer.closeElement();
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer("LibraryManager(");
      if (this.libraries.isEmpty()) {
         sb.append("No libraries available");
      } else {
         sb.append("Available libraries ").append(this.libraries.toString());
      }

      if (this.unresolvedRefs.isEmpty()) {
         sb.append(",sid=" + System.identityHashCode(this) + ")");
         return sb.toString();
      } else {
         sb.append("\n").append("Unresolved references ").append(this.unresolvedRefs.toString());
         sb.append(",sid=" + System.identityHashCode(this) + ")");
         return sb.toString();
      }
   }
}
