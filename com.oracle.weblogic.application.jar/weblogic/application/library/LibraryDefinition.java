package weblogic.application.library;

import java.io.File;
import weblogic.application.Type;
import weblogic.application.internal.library.LibraryRuntimeMBeanImpl;
import weblogic.application.metadatacache.Metadata;
import weblogic.application.metadatacache.MetadataEntry;
import weblogic.application.metadatacache.MetadataType;
import weblogic.management.runtime.LibraryRuntimeMBean;

public abstract class LibraryDefinition implements Library, Metadata, Comparable {
   private final LibraryData libData;
   private LibraryRuntimeMBeanImpl runtime = null;

   public LibraryDefinition(LibraryData libData, Type type) {
      this.libData = libData;
      libData.setType(type);
   }

   public void init() throws LibraryProcessingException {
   }

   public void cleanup() throws LibraryProcessingException {
   }

   public void remove() throws LibraryProcessingException {
   }

   public String getName() {
      return this.libData.getName();
   }

   public Type getType() {
      return this.libData.getType();
   }

   public String getSpecificationVersion() {
      return this.libData.getSpecificationVersion() == null ? null : this.libData.getSpecificationVersion().toString();
   }

   public String getImplementationVersion() {
      return this.libData.getImplementationVersion();
   }

   public File getLocation() {
      return this.libData.getLocation();
   }

   public void setLocation(File f) {
      this.libData.setLocation(f);
   }

   public void setRuntime(LibraryRuntimeMBeanImpl runtime) {
      this.runtime = runtime;
   }

   public LibraryRuntimeMBeanImpl getRuntimeImpl() {
      return this.runtime;
   }

   public LibraryRuntimeMBean getRuntime() {
      return this.getRuntimeImpl();
   }

   public String toString() {
      return this.libData.toString();
   }

   public LibraryData getLibData() {
      return this.libData;
   }

   public int compareTo(Object o) {
      return this.compareTo((LibraryDefinition)o);
   }

   public int compareTo(LibraryDefinition def) {
      return this.getName().compareTo(def.getName());
   }

   public LibraryReference[] getLibraryReferences() {
      return null;
   }

   public MetadataEntry[] findAllCachableEntry() {
      return new MetadataEntry[0];
   }

   public MetadataEntry getCachableEntry(MetadataType type) {
      return null;
   }

   public LibraryConstants.AutoReferrer[] getAutoRef() {
      return this.libData.getAutoRef();
   }
}
