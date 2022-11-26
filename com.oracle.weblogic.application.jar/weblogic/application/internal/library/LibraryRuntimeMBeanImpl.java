package weblogic.application.internal.library;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryReferencer;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.application.utils.ManagementUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.runtime.LibraryRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public final class LibraryRuntimeMBeanImpl extends RuntimeMBeanDelegate implements LibraryRuntimeMBean {
   private final ComponentMBean[] components;
   private final LibraryData libData;
   private final String libId;
   private final String partitionName;
   private final Collection references = new HashSet();

   public LibraryRuntimeMBeanImpl(LibraryData libData, String libId, ComponentMBean[] components) throws ManagementException {
      super(ApplicationVersionUtils.getNonPartitionName(libId), ManagementUtils.getPartitionRuntime(ApplicationVersionUtils.getPartitionName(libId)), true, (DescriptorBean)null);
      this.components = components;
      this.libData = libData;
      this.libId = libId;
      String pName = ApplicationVersionUtils.getPartitionName(libId);
      if ("DOMAIN".equals(pName)) {
         this.partitionName = null;
      } else {
         this.partitionName = pName;
      }

   }

   public ComponentMBean[] getComponents() {
      return this.components;
   }

   public String getLibraryName() {
      return this.libData.getName();
   }

   public File getLocation() {
      return this.libData.getLocation();
   }

   public String getLibraryIdentifier() {
      return this.libId;
   }

   public String getSpecificationVersion() {
      return this.libData.getSpecificationVersion() == null ? null : this.libData.getSpecificationVersion().toString();
   }

   public String getImplementationVersion() {
      return this.libData.getImplementationVersion();
   }

   public RuntimeMBean[] getReferencingRuntimes() {
      synchronized(this.references) {
         RuntimeMBean[] rtn = new RuntimeMBean[this.references.size()];
         int i = 0;

         LibraryReferencer ref;
         for(Iterator iter = this.references.iterator(); iter.hasNext(); rtn[i++] = ref.getReferencerRuntime()) {
            ref = (LibraryReferencer)iter.next();
         }

         return rtn;
      }
   }

   public String[] getReferencingNames() {
      synchronized(this.references) {
         String[] rtn = new String[this.references.size()];
         int i = 0;

         for(Iterator iter = this.references.iterator(); iter.hasNext(); rtn[i++] = ((LibraryReferencer)iter.next()).getReferencerName()) {
         }

         return rtn;
      }
   }

   public void addReference(LibraryReferencer in) {
      synchronized(this.references) {
         this.references.add(in);
      }
   }

   public void removeReference(LibraryReferencer in) {
      synchronized(this.references) {
         this.references.remove(in);
      }
   }

   public boolean isReferenced() {
      synchronized(this.references) {
         return !this.references.isEmpty();
      }
   }

   public String getPartitionName() {
      return this.partitionName;
   }
}
