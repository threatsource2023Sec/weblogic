package weblogic.application.library;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import weblogic.application.Type;
import weblogic.application.internal.library.BasicLibraryData;
import weblogic.application.internal.library.LibraryRegistry;
import weblogic.application.internal.library.OptionalPackageReference;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.j2ee.descriptor.wl.LibraryRefBean;
import weblogic.utils.StringUtils;

public final class LibraryReferenceFactory {
   private LibraryReferenceFactory() {
   }

   public static J2EELibraryReference[] getAppLibReference(String partitionName) {
      return getAppLibReference(LibraryRegistry.getRegistry(), partitionName);
   }

   public static J2EELibraryReference[] getAppLibReference(LibraryRegistry reg, String partitionName) {
      return getMatchingJ2EELibRefs(reg, ApplicationLibrary.class, (Type)null, partitionName);
   }

   public static J2EELibraryReference[] getWebAppLibReference(String partitionName) {
      return getMatchingJ2EELibRefs(LibraryRegistry.getRegistry(), Type.WAR, partitionName);
   }

   public static J2EELibraryReference[] getWebAppLibReference(LibraryRegistry r, String partitionName) {
      return getMatchingJ2EELibRefs(r, Type.WAR, partitionName);
   }

   private static J2EELibraryReference[] getMatchingJ2EELibRefs(LibraryRegistry reg, Type libType, String partitionName) {
      return getMatchingJ2EELibRefs(reg, Library.class, libType, partitionName);
   }

   private static J2EELibraryReference[] getMatchingJ2EELibRefs(LibraryRegistry reg, Class libCategory, Type libType, String partitionName) {
      if (libCategory == null) {
         throw new IllegalArgumentException("libCategory cannot be null");
      } else {
         Collection rtn = new ArrayList();
         Collection c = reg.getAll(partitionName);
         Iterator iter = c.iterator();

         while(iter.hasNext()) {
            Library lib = (Library)iter.next();
            if (libCategory.isAssignableFrom(lib.getClass())) {
               try {
                  BasicLibraryData d = new BasicLibraryData(lib.getName(), lib.getSpecificationVersion(), lib.getImplementationVersion(), libType);
                  rtn.add(new J2EELibraryReference(d, true, (String)null));
               } catch (IllegalSpecVersionTypeException var9) {
                  throw new AssertionError(var9);
               }
            }
         }

         return (J2EELibraryReference[])((J2EELibraryReference[])rtn.toArray(new J2EELibraryReference[rtn.size()]));
      }
   }

   public static J2EELibraryReference[] getAppLibReference(LibraryRefBean[] ref) throws IllegalSpecVersionTypeException {
      return getReference(ref, (Type)null);
   }

   public static J2EELibraryReference[] getWebLibReference(LibraryRefBean[] ref) throws IllegalSpecVersionTypeException {
      return getReference(ref, (Type)null);
   }

   public static LibraryReference[] getOptPackReference(String src, Attributes attrs) {
      String extensionList = attrs.getValue(Name.EXTENSION_LIST);
      if (extensionList == null) {
         return null;
      } else {
         String[] names = StringUtils.splitCompletely(extensionList, " ");
         Collection rtn = new ArrayList(names.length);

         for(int i = 0; i < names.length; ++i) {
            String name = names[i].trim();
            if (name.length() != 0) {
               String extName = attrs.getValue(name + "-" + Name.EXTENSION_NAME);
               if (extName == null) {
                  LibraryLoggingUtils.warnMissingExtensionName(name, src);
               } else {
                  String extSpec = attrs.getValue(names[i] + "-" + Name.SPECIFICATION_VERSION);
                  String extImpl = attrs.getValue(names[i] + "-" + Name.IMPLEMENTATION_VERSION);
                  BasicLibraryData data = LibraryLoggingUtils.initOptionalPackageRefLibData(extName, extSpec, extImpl, src);
                  if (data != null) {
                     rtn.add(new OptionalPackageReference(data, src));
                  }
               }
            }
         }

         if (rtn.isEmpty()) {
            return null;
         } else {
            return (LibraryReference[])((LibraryReference[])rtn.toArray(new OptionalPackageReference[rtn.size()]));
         }
      }
   }

   private static J2EELibraryReference[] getReference(LibraryRefBean[] ref, Type type) throws IllegalSpecVersionTypeException {
      if (ref == null) {
         return null;
      } else {
         J2EELibraryReference[] rtn = new J2EELibraryReference[ref.length];

         for(int i = 0; i < ref.length; ++i) {
            BasicLibraryData data = new BasicLibraryData(ref[i].getLibraryName(), ref[i].getSpecificationVersion(), ref[i].getImplementationVersion(), type);
            rtn[i] = new J2EELibraryReference(data, ref[i].getExactMatch(), ref[i].getContextRoot());
         }

         return rtn;
      }
   }
}
