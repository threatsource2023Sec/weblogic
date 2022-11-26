package weblogic.management.commo;

import com.bea.staxb.buildtime.internal.mbean.MBeanJava2Schema;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JamServiceFactory;
import com.bea.util.jam.JamServiceParams;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.jar.JarFile;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.types.FileSet;
import weblogic.utils.FileUtils;
import weblogic.utils.jars.JarFileUtils;

public class JavaToSchemaUtil {
   static final String MBEAN_IMPL_SRC_JAR_NAME = "wlManagementImplSource.jar";
   static final String BASE_SCHEMA_JAR = "weblogic-domain-binding.jar";
   static final String COMPATIBILITY_BASE_SCHEMA_JAR = "weblogic-domain-binding-compatibility.jar";

   public static void main(String[] args) throws Exception {
      String mbt = args.length == 3 ? args[2] : null;
      generateSchema(args[0], args[1], mbt);
   }

   private static String getManagementImplSrcJarName(String mbeantypesDir) {
      Class bootStrapClass = null;
      Method getPathRelativeWebLogicHome = null;
      String mbeanImplSrcJarName = null;

      try {
         String fromInstaller = System.getProperty("weblogic.SPUpgrade.FromInstaller");
         if (fromInstaller != null) {
            mbeanImplSrcJarName = System.getProperty("weblogic.SPUpgrade.MBeanImplJarPath");
         } else {
            if (mbeantypesDir == null) {
               mbeantypesDir = System.getProperty("mbeantypesDir");
            }

            System.out.println("MBEAN TYPES DIR : " + mbeantypesDir);
            if (mbeantypesDir == null) {
               bootStrapClass = Class.forName("weblogic.management.bootstrap.BootStrap");
               getPathRelativeWebLogicHome = bootStrapClass.getMethod("getPathRelativeWebLogicHome", String.class);
               String basePath = (String)getPathRelativeWebLogicHome.invoke((Object)null, "lib");
               mbeanImplSrcJarName = basePath + File.separator + "mbeantypes" + File.separator + "wlManagementImplSource.jar";
            } else {
               mbeanImplSrcJarName = mbeantypesDir + File.separator + "wlManagementImplSource.jar";
            }
         }

         return mbeanImplSrcJarName;
      } catch (Exception var6) {
         throw new Error(var6);
      }
   }

   private static String getWebLogicHome() {
      Class bootStrapClass = null;
      Method getPathRelativeWebLogicHome = null;

      try {
         bootStrapClass = Class.forName("weblogic.management.bootstrap.BootStrap");
         getPathRelativeWebLogicHome = bootStrapClass.getMethod("getPathRelativeWebLogicHome", String.class);
         String basePath = (String)getPathRelativeWebLogicHome.invoke((Object)null, "lib");
         return basePath;
      } catch (Exception var3) {
         System.out.println("WebLogic installation not found");
         return null;
      }
   }

   private static void generateSchema(String filesDirectory, String schemaJarName, String mbeantypesDir) throws IOException {
      System.out.println("Generating schema for security provider mbeans ... ");
      String tempDir = "securitySchemaTempDir";
      File tempDirectory = new File(tempDir);
      if (tempDirectory.exists()) {
         FileUtils.remove(tempDirectory);
      }

      tempDirectory.mkdir();
      File tempFileDir = new File("tempFileDirForSchema");
      JarFile jarFile = new JarFile(new File(getManagementImplSrcJarName(mbeantypesDir)));
      JarFileUtils.extract(jarFile, tempFileDir);
      FileCopier copier = new FileCopier();
      copier.setIncludeEmptyDirs(false);
      copier.setTodir(tempFileDir);
      FileSet fset = new FileSet();
      fset.setDir(new File(filesDirectory));
      fset.setExcludes("**/*.xml");
      fset.setIncludes("**/*MBeanImpl.java");
      copier.addFileset(fset);
      copier.execute();
      deleteEmptyDirectories(tempFileDir);
      deleteAllExceptMBeanImplFiles(tempFileDir.getAbsolutePath(), (String)null);
      JamServiceFactory jf = JamServiceFactory.getInstance();
      JamServiceParams params = jf.createServiceParams();
      params.includeSourcePattern(new File[]{tempFileDir}, "**/*Impl.java");
      params.excludeSourcePattern(new File[]{tempFileDir}, "**/*MBI.java");
      JClass[] classes = jf.createService(params).getAllClasses();
      MBeanJava2Schema j2s = new MBeanJava2Schema();

      for(int i = 0; i < classes.length; ++i) {
         j2s.addClassToBind(classes[i]);
      }

      File f = null;
      File compatibilityJar = null;
      String fromInstaller = System.getProperty("weblogic.SPUpgrade.FromInstaller");
      if (fromInstaller != null) {
         f = new File(System.getProperty("weblogic.SPUpgrade.BindingJarPath"));
      } else {
         String wls_home = getWebLogicHome();
         if (wls_home != null) {
            f = new File(wls_home + "/schema/" + "weblogic-domain-binding.jar");
            compatibilityJar = new File(wls_home + "/schema/" + "weblogic-domain-binding-compatibility.jar");
         }
      }

      j2s.setAttributeFormDefaultQualified(false);
      j2s.setElementFormDefaultQualified(true);
      if (f != null) {
         System.out.println("SET BASE LIB " + f);
         j2s.setBaseLibraries(new File[]{f});
      }

      if (compatibilityJar != null) {
         j2s.setExcludeLibraries(new File[]{compatibilityJar});
      }

      j2s.bindAsExplodedTylar(new File(filesDirectory));
      FileUtils.remove(tempDirectory);
      FileUtils.remove(tempFileDir);
   }

   private static void deleteAllExceptMBeanImplFiles(String baseDir, String dir) {
      String fullDir = null;
      if (dir == null) {
         fullDir = baseDir;
      } else {
         fullDir = baseDir + File.separator + dir;
      }

      File df = new File(fullDir);
      String[] files = df.list();

      for(int i = 0; i < files.length; ++i) {
         String fileName = null;
         if (dir != null) {
            fileName = dir + File.separator + files[i];
         } else {
            fileName = files[i];
         }

         File file = new File(baseDir + File.separator + fileName);
         if (file.isDirectory()) {
            deleteAllExceptMBeanImplFiles(baseDir, fileName);
         } else if (!file.getName().endsWith("MBeanImpl.java")) {
            FileUtils.remove(file);
         }
      }

   }

   private static void deleteEmptyDirectories(File rootDir) {
      File[] dirs = rootDir.listFiles();
      if (dirs.length == 0) {
         File parentFile = new File(rootDir.getParentFile().getAbsolutePath());
         FileUtils.remove(rootDir);
         deleteEmptyDirectories(parentFile);
      } else {
         for(int i = 0; i < dirs.length; ++i) {
            if (dirs[i].isDirectory()) {
               deleteEmptyDirectories(dirs[i]);
            }
         }
      }

   }

   static class FileCopier extends Copy {
      public FileCopier() {
         this.project = new Project();
         this.project.init();
         this.taskType = "copy";
         this.taskName = "copy";
         this.target = new Target();
      }
   }
}
