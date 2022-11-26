package weblogic.deploy.api.internal.utils;

import java.io.File;
import java.lang.annotation.Annotation;
import weblogic.application.internal.library.util.DeweyDecimal;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.server.GlobalServiceLocator;

public class LibrarySpec {
   private SimpleLibraryData lib;
   private boolean mark = false;
   private Object key;

   public LibrarySpec(String name, String specVersion, String implVersion, File location) throws IllegalArgumentException {
      try {
         ConfigHelper.checkParam("File", location);
         LibraryDataFactory ldf = (LibraryDataFactory)GlobalServiceLocator.getServiceLocator().getService(LibraryDataFactory.class, new Annotation[0]);
         this.lib = ldf.create(name, specVersion, implVersion, location);
      } catch (LoggableLibraryProcessingException var6) {
         throw new IllegalArgumentException(var6);
      }
   }

   public SimpleLibraryData getLibraryData() {
      return this.lib;
   }

   public String getName() {
      return this.getLibraryData().getName();
   }

   public String getSpecVersion() {
      DeweyDecimal sv = this.getLibraryData().getSpecificationVersion();
      return sv == null ? null : sv.toString();
   }

   public String getImplVersion() {
      return this.getLibraryData().getImplementationVersion();
   }

   public File getLocation() {
      return this.getLibraryData().getLocation();
   }

   public void mark() {
      this.mark = true;
   }

   public boolean isMarked() {
      return this.mark;
   }

   public void setKey(Object key) {
      this.key = key;
   }

   public Object getKey() {
      return this.key;
   }
}
