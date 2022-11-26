package weblogic.management.commo;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.jar.JarFile;
import weblogic.descriptor.beangen.BeanGenOptions;
import weblogic.descriptor.beangen.BeanGenerator;
import weblogic.utils.FileUtils;
import weblogic.utils.jars.JarFileUtils;

public class BeanGenDriver {
   private static char[] INVALID_CHAR = new char[]{'-', '.'};
   private static char REPL_CHAR = '_';

   private static void getFileNames(List fileNameArray, String baseDir, String dir) {
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
            getFileNames(fileNameArray, baseDir, fileName);
         } else if (file.getName().endsWith("MBean.java")) {
            fileNameArray.add(fileName);
         }
      }

   }

   private static String getManagementTempDir() {
      Class bootStrapClass = null;
      Method getPathRelativeWebLogicHome = null;
      String mbeanInterfacesJarPath = null;
      File tempDirectory = new File("wlMakerTempDir");

      try {
         String fromInstaller = System.getProperty("weblogic.SPUpgrade.FromInstaller");
         if (fromInstaller != null) {
            mbeanInterfacesJarPath = System.getProperty("weblogic.SPUpgrade.MBeanJarPath");
         } else {
            String mbeantypesDir = System.getProperty("mbeantypesDir");
            if (mbeantypesDir == null) {
               bootStrapClass = Class.forName("weblogic.management.bootstrap.BootStrap");
               getPathRelativeWebLogicHome = bootStrapClass.getMethod("getPathRelativeWebLogicHome", String.class);
               String basePath = (String)getPathRelativeWebLogicHome.invoke((Object)null, "lib");
               mbeanInterfacesJarPath = basePath + File.separator + "mbeantypes" + File.separator + "wlManagementMBean.jar";
            } else {
               mbeanInterfacesJarPath = mbeantypesDir + File.separator + "wlManagementMBean.jar";
            }
         }

         if (tempDirectory.exists()) {
            FileUtils.remove(tempDirectory);
         }

         tempDirectory.mkdir();
         System.out.println("EXTRACT FROM " + mbeanInterfacesJarPath);
         JarFile jarFile = new JarFile(new File(mbeanInterfacesJarPath));
         System.out.println("        INTO " + tempDirectory);
         JarFileUtils.extract(jarFile, tempDirectory);
      } catch (Exception var7) {
         var7.printStackTrace();
         throw new Error(var7);
      }

      return tempDirectory.getAbsolutePath();
   }

   public static void main(String[] args) throws Exception {
      String rootDir = args[0];
      String mjfJarName = args[1];
      String targetNameSpace = args[2];
      String schemaLocation = args.length > 3 ? args[3] : null;
      List fileNameList = new ArrayList();
      getFileNames(fileNameList, rootDir, (String)null);
      String[] sourceNames = (String[])((String[])fileNameList.toArray(new String[fileNameList.size()]));
      List mgmtFiles = null;
      String beanInfoFactoryClassName;
      File metaInfDir;
      File beanInfoFactoryFile;
      if (!rootDir.endsWith("wlManagementTemp")) {
         File mgmtDir = new File(getManagementTempDir());
         if (mgmtDir != null) {
            mgmtFiles = new ArrayList();
            getFileNames(mgmtFiles, mgmtDir.getAbsolutePath(), (String)null);
            Iterator it = mgmtFiles.iterator();

            while(it.hasNext()) {
               beanInfoFactoryClassName = (String)it.next();
               metaInfDir = new File(mgmtDir, beanInfoFactoryClassName);
               beanInfoFactoryFile = new File(rootDir, beanInfoFactoryClassName);
               FileUtils.copy(metaInfDir, beanInfoFactoryFile);
            }
         }

         FileUtils.remove(mgmtDir);
      }

      BeanGenOptions options = new BeanGenOptions();
      options.setTargetDirectory(rootDir);
      options.setTemplateDir(".");
      options.setBaseInterfaceName("weblogic.descriptor.DescriptorBean");
      options.setNoSynthetics(false);
      options.setSourcePath(rootDir);
      options.setSourceDir(rootDir);
      options.setSources(sourceNames);
      options.setExternalDir(System.getProperty("externalDir"));
      if (targetNameSpace != null) {
         options.setTargetNamespace(targetNameSpace);
      } else {
         options.setTargetNamespace("http://xmlns.oracle.com/weblogic/security");
      }

      if (schemaLocation != null && !"null".equals(schemaLocation)) {
         options.setSchemaLocation(schemaLocation);
      }

      System.out.println("\n\n\nGenerating the implementations for security MBeans");
      options.setBaseClassName("weblogic.management.commo.AbstractCommoConfigurationBean");
      options.setTemplateExtension("weblogic/management/internal/mbean/SecurityReadOnlyMBean.template");
      options.setSuffix("Impl");
      BeanGenerator beanGenerator = new BeanGenerator(options);
      beanGenerator.generate();
      System.out.println("\n\n\nGenerating the bean infos for security MBeans ...  ");
      options.setSuffix("ImplBeanInfo");
      options.setBaseClassName("weblogic.management.commo.AbstractCommoConfigurationBeanImplBeanInfo");
      options.setTemplateName("weblogic/management/internal/mbean/BeanInfoBinder.template");
      options.setNoSynthetics(true);
      beanInfoFactoryClassName = getMjfName(mjfJarName);
      options.setBeanFactory(beanInfoFactoryClassName);
      beanGenerator = new BeanGenerator(options);
      beanGenerator.generate();
      metaInfDir = new File(rootDir, "META-INF");
      metaInfDir.mkdir();
      beanInfoFactoryFile = new File(metaInfDir, "beaninfofactory.txt");
      FileWriter beanInfoFactoryFileWriter = new FileWriter(beanInfoFactoryFile);
      beanInfoFactoryFileWriter.write(beanInfoFactoryClassName);
      beanInfoFactoryFileWriter.close();
      if (mgmtFiles != null) {
         Iterator it = mgmtFiles.iterator();

         while(it.hasNext()) {
            String next = (String)it.next();
            File targetFile = new File(rootDir, next);
            targetFile.delete();
         }
      }

   }

   private static String getMjfName(String mjfJarName) {
      boolean containsInvalidChar = false;
      File jar = new File(mjfJarName);
      String mjfName = jar.getName();
      if (mjfName.endsWith(".jar")) {
         mjfName = mjfName.substring(0, mjfName.length() - 4);
      }

      for(int i = 0; i < INVALID_CHAR.length; ++i) {
         if (mjfName.indexOf(INVALID_CHAR[i]) > 1) {
            mjfName = mjfName.replace(INVALID_CHAR[i], REPL_CHAR);
            containsInvalidChar = true;
         }
      }

      mjfName = "weblogic.management.security." + mjfName.toUpperCase() + "BeanInfoFactory";
      return mjfName;
   }
}
