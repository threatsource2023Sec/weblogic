package weblogic.ant.taskdefs.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import weblogic.application.library.LibraryInitializer;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.j2ee.J2EELogger;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.ClasspathClassFinder2;
import weblogic.utils.classloaders.MultiClassFinder;

public final class AntLibraryUtils {
   private AntLibraryUtils() {
   }

   public static void validateLibraries(File libdir, Collection libraries) {
      Collection libdirs = null;
      if (libdir != null) {
         libdirs = new ArrayList(1);
         libdirs.add(libdir);
      }

      validateLibraries((Collection)libdirs, libraries);
   }

   public static void validateLibraries(Collection libdirs, Collection libraries) {
      Iterator iter;
      if (libdirs != null) {
         iter = libdirs.iterator();

         while(iter.hasNext()) {
            File libdir = (File)iter.next();
            if (!libdir.exists()) {
               throw new BuildException("librarydir: " + libdir.getAbsolutePath() + " does not exist or cannot be read.");
            }

            if (!libdir.isDirectory()) {
               throw new BuildException("librarydir: " + libdir.getAbsolutePath() + " is not a directory.");
            }
         }
      }

      iter = libraries.iterator();

      LibraryElement le;
      do {
         if (!iter.hasNext()) {
            return;
         }

         le = (LibraryElement)iter.next();
         if (le.getFile() == null) {
            throw new BuildException("Library's file attr must be set.");
         }
      } while(le.getFile().exists());

      throw new BuildException("Library " + le.getFile().getAbsolutePath() + " does not exist or cannot be read.");
   }

   public static void registerLibraries(LibraryInitializer libraryInitializer, File[] libdirs, LibraryElement[] libraries, boolean silent) {
      registerLibdirs(libraryInitializer, libdirs);
      registerLibraries(libraryInitializer, libraries, silent);
   }

   private static void registerLibdirs(LibraryInitializer libraryInitializer, File[] libdirs) {
      if (libdirs != null) {
         for(int i = 0; i < libdirs.length; ++i) {
            try {
               libraryInitializer.registerLibdir(libdirs[i].getAbsolutePath());
            } catch (LoggableLibraryProcessingException var4) {
               throw new BuildException(var4.getLoggable().getMessage());
            }
         }

      }
   }

   private static void registerLibraries(LibraryInitializer libraryInitializer, LibraryElement[] libraries, boolean silent) {
      boolean registrationSuccessful = true;

      for(int i = 0; i < libraries.length; ++i) {
         try {
            libraryInitializer.registerLibrary(libraries[i].getFile(), libraries[i].getLibraryData());
         } catch (LoggableLibraryProcessingException var6) {
            if (!silent) {
               var6.getLoggable().log();
            }

            registrationSuccessful = false;
         }
      }

      if (!registrationSuccessful) {
         throw new BuildException(J2EELogger.logAppcLibraryRegistrationFailedLoggable().getMessage());
      }
   }

   public static void logRegistryContent(Project project, int level) {
      project.log(LibraryLoggingUtils.registryToString(), level);
   }

   public static String getClassPath(File[] f, ClassFinder finder) {
      String rtn = "";
      MultiClassFinder cf = new MultiClassFinder();

      try {
         for(int i = 0; i < f.length; ++i) {
            if (!f[i].isFile() || f[i].getName().toLowerCase().endsWith(".jar")) {
               cf.addFinder(new ClasspathClassFinder2(f[i].getAbsolutePath()));
            }
         }

         cf.addFinder(finder);
         rtn = cf.getClassPath();
         return rtn;
      } finally {
         cf.close();
      }
   }

   public static List getLibraryFlags(File libdir, Collection libraries) {
      List flags = new ArrayList();
      if (libdir != null) {
         flags.add("-librarydir");
         flags.add(libdir.getAbsolutePath());
      }

      if (libraries.isEmpty()) {
         return flags;
      } else {
         flags.add("-library");
         StringBuffer sb = new StringBuffer();
         Iterator iter = libraries.iterator();

         while(iter.hasNext()) {
            LibraryElement l = (LibraryElement)iter.next();
            sb.append(l.getFile().getAbsolutePath());
            if (l.getName() != null) {
               sb.append("@").append("name").append("=").append(l.getName());
            }

            if (l.getSpecificationVersion() != null) {
               sb.append("@").append("libspecver").append("=").append(l.getSpecificationVersion());
            }

            if (l.getImplementationVersion() != null) {
               sb.append("@").append("libimplver").append("=").append(l.getImplementationVersion());
            }

            if (iter.hasNext()) {
               sb.append(",");
            }
         }

         flags.add(sb.toString());
         return flags;
      }
   }
}
