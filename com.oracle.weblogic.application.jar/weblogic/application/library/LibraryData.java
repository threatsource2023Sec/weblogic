package weblogic.application.library;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import weblogic.application.internal.library.BasicLibraryData;
import weblogic.application.internal.library.util.DeweyDecimal;
import weblogic.deploy.api.internal.utils.SimpleLibraryData;
import weblogic.management.configuration.LibraryMBean;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class LibraryData extends BasicLibraryData implements Serializable, SimpleLibraryData {
   private static final long serialVersionUID = 1648716277382804679L;
   private File location = null;
   private final LibraryConstants.AutoReferrer[] autoRef;
   private final Attributes attributes;

   public static LibraryData newEmptyInstance(File f) {
      Attributes attributes = null;
      LibraryConstants.AutoReferrer[] autoRef = null;

      try {
         attributes = getManifestAttributes(f);
         autoRef = checkAutoRefLib(attributes);
      } catch (IOException var4) {
      }

      return new LibraryData((String)null, (DeweyDecimal)null, (String)null, f, autoRef, attributes);
   }

   public static LibraryData newInstance(String name, String specVersion, String implVersion, File location) throws IOException, IllegalSpecVersionTypeException {
      return new LibraryData(name, specVersion, implVersion, location);
   }

   public static LibraryData initFromManifest(File location) throws IOException, IllegalSpecVersionTypeException {
      return new LibraryData(location, getManifestAttributes(location));
   }

   public static LibraryData initFromManifest(File location, Attributes attributes) throws IllegalSpecVersionTypeException {
      return new LibraryData(location, attributes);
   }

   public static LibraryData initFromMBean(LibraryMBean mbean, File loc) throws IOException, IllegalSpecVersionTypeException {
      return new LibraryData(mbean, loc);
   }

   public static LibraryData cloneWithNewName(String name, LibraryData d) {
      return new LibraryData(name, d.getSpecificationVersion(), d.getImplementationVersion(), d.getLocation(), d.getAutoRef(), d.getAttributes());
   }

   private LibraryData(String name, String spec, String impl, File location) throws IOException, IllegalSpecVersionTypeException {
      super(name, spec, impl);
      this.location = location;
      this.attributes = getManifestAttributes(location);
      this.autoRef = checkAutoRefLib(this.attributes);
   }

   private LibraryData(String name, DeweyDecimal spec, String impl, File location, LibraryConstants.AutoReferrer[] autoRef, Attributes attributes) {
      super(name, spec, impl);
      this.location = location;
      this.attributes = attributes;
      this.autoRef = autoRef;
   }

   private LibraryData(LibraryMBean mbean, File location) throws IOException, IllegalSpecVersionTypeException {
      super(mbean);
      this.location = location;
      this.attributes = getManifestAttributes(location);
      this.autoRef = checkAutoRefLib(this.attributes);
   }

   private LibraryData(File location, Attributes attributes) throws IllegalSpecVersionTypeException {
      super(attributes);
      this.location = location;
      this.attributes = attributes;
      this.autoRef = checkAutoRefLib(attributes);
   }

   private LibraryData(BasicLibraryData data, File location, LibraryConstants.AutoReferrer[] autoRef, Attributes attributes) {
      super(data.getName(), data.getSpecificationVersion(), data.getImplementationVersion());
      this.location = location;
      this.autoRef = autoRef;
      this.attributes = attributes;
   }

   public File getLocation() {
      return this.location;
   }

   public LibraryConstants.AutoReferrer[] getAutoRef() {
      return this.autoRef;
   }

   public Attributes getAttributes() {
      return this.attributes;
   }

   void setLocation(File location) {
      this.location = location;
   }

   public LibraryData importData(LibraryData in) {
      BasicLibraryData imported = super.importData(in);
      File f = this.getLocation() == null ? in.getLocation() : this.getLocation();
      LibraryConstants.AutoReferrer[] autoRef = this.getAutoRef().length == 0 ? in.getAutoRef() : this.getAutoRef();
      Attributes attributes = this.getAttributes() == null ? in.getAttributes() : this.getAttributes();
      return new LibraryData(imported, f, autoRef, attributes);
   }

   private static Attributes getManifestAttributes(File f) throws IOException {
      Attributes rtn = null;
      VirtualJarFile jar = null;

      try {
         jar = VirtualJarFactory.createVirtualJar(f);
      } catch (IOException var7) {
         closeJar(jar);
         throw var7;
      }

      try {
         Manifest m = jar.getManifest();
         if (m != null) {
            rtn = m.getMainAttributes();
         }
      } finally {
         closeJar(jar);
      }

      return rtn;
   }

   private static void closeJar(VirtualJarFile f) {
      try {
         if (f != null) {
            f.close();
         }
      } catch (IOException var2) {
      }

   }

   private static LibraryConstants.AutoReferrer[] checkAutoRefLib(Attributes atts) {
      String value = null;
      if (atts != null) {
         value = atts.getValue("Auto-Ref-By");
      }

      if (value == null) {
         return new LibraryConstants.AutoReferrer[0];
      } else {
         Set autoRefByList = new HashSet();
         String[] var3 = value.split(",");
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String autoRef = var3[var5];
            autoRefByList.add(LibraryConstants.AutoReferrer.valueOf(autoRef.trim()));
         }

         return (LibraryConstants.AutoReferrer[])autoRefByList.toArray(new LibraryConstants.AutoReferrer[0]);
      }
   }
}
