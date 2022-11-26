package weblogic.j2ee;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.resource.spi.Activation;
import javax.resource.spi.ConnectionDefinition;
import javax.resource.spi.ConnectionDefinitions;
import javax.resource.spi.Connector;
import weblogic.application.ApplicationFileManager;
import weblogic.application.archive.utils.ArchiveUtils;
import weblogic.application.utils.AnnotationDetector;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.ejb.spi.DDConstants;
import weblogic.logging.Loggable;
import weblogic.management.ApplicationException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.utils.jars.VirtualJarFile;

public class J2EEUtils {
   private static final String sep;
   public static final String APP_DD_NAME = "application.xml";
   public static final String APP_DD_PATH;
   public static final String APP_DD_URI = "META-INF/application.xml";
   public static final String WLAPP_DD_NAME = "weblogic-application.xml";
   public static final String WLAPP_DD_URI = "META-INF/weblogic-application.xml";
   public static final String WLAPP_DD_PATH;
   public static final String WEB_DD_NAME = "web.xml";
   public static final String WEB_DD_URI = "WEB-INF/web.xml";
   public static final String WEB_DD_PATH;
   public static final String WLWEB_DD_NAME = "weblogic.xml";
   public static final String WLWEB_DD_URI = "WEB-INF/weblogic.xml";
   public static final String WEBSERVICE_DD_NAME = "web-services.xml";
   public static final String WEBSERVICE_DD_URI = "WEB-INF/web-services.xml";
   public static final String WEBSERVICE_DD_PATH;
   public static final String EJB_DD_NAME = "ejb-jar.xml";
   public static final String EJB_DD_URI = "META-INF/ejb-jar.xml";
   public static final String WAR_EJB_DD_URI = "WEB-INF/ejb-jar.xml";
   public static final String EJB_DD_PATH;
   public static final String WAR_EJB_DD_PATH;
   public static final String PERCONF_DD_NAME = "persistence-configuration.xml";
   public static final String PERCONF_DD_URI = "META-INF/persistence-configuration.xml";
   public static final String PERCONF_DD_PATH;
   public static final String WLEJB_DD_NAME = "weblogic-ejb-jar.xml";
   public static final String WLEJB_DD_URI = "META-INF/weblogic-ejb-jar.xml";
   public static final String WAR_WLEJB_DD_URI = "WEB-INF/weblogic-ejb-jar.xml";
   public static final String WLEJBCMP_DD_NAME = "weblogic-rdbms-jar.xml";
   public static final String RAR_DD_NAME = "ra.xml";
   public static final String RAR_DD_URI = "META-INF/ra.xml";
   public static final String RAR_DD_PATH;
   public static final String WLRAR_DD_NAME = "weblogic-ra.xml";
   public static final String WLRAR_DD_URI = "META-INF/weblogic-ra.xml";
   public static final String WLRAR_DD_PATH;
   public static final String CAR_DD_NAME = "application-client.xml";
   public static final String CAR_DD_URI = "META-INF/application-client.xml";
   public static final String CAR_DD_PATH;
   public static final String WLCAR_DD_NAME = "weblogic-application-client.xml";
   public static final String WLCAR_DD_URI = "META-INF/weblogic-application-client.xml";
   public static final String WLCAR_DD_PATH;
   public static final String APP_INF = "APP-INF";
   public static final String APP_INF_LIB;
   public static final String APP_INF_CLASS;
   public static final int EAR = 0;
   public static final int COMPONENT = 1;
   public static final int EXPLODED_EAR = 2;
   public static final int EXPLODED_COMPONENT = 3;
   public static final int UNKNOWN = 4;
   public static final int SINGLE_FILE_COMPONENT = 5;
   public static final int NOT_INITIALIZED = 6;
   private static String EJB_MODULE_TYPE;
   private static String WEB_MODULE_TYPE;
   private static String CONNECTOR_MODULE_TYPE;
   private static String WEBSERVICE_MODULE_TYPE;
   private static String JDBCPOOL_MODULE_TYPE;
   private static String JMS_MODULE_TYPE;
   private static String APPCLIENT_MODULE_TYPE;
   public static final AnnotationDetector EJB_ANNOTATION_DETECTOR;

   public static final String getArchivePostfix(String name) {
      return !ArchiveUtils.isValidArchiveName(name) ? null : name.substring(name.length() - 3, name.length());
   }

   public static final String getWLSModulePostfix(String name) {
      return !ArchiveUtils.isValidWLSModuleName(name) ? null : name.substring(name.length() - 3, name.length());
   }

   public static final String getArchiveName(String name) {
      return !ArchiveUtils.isValidArchiveName(name) ? name : name.substring(0, name.length() - 4);
   }

   public static final String getWLSModuleName(String name) {
      return !ArchiveUtils.isValidWLSModuleName(name) ? name : name.substring(0, name.length() - 4);
   }

   public static int getDeploymentCategory(ApplicationMBean aMB) throws IOException {
      int result = 4;
      if (aMB != null) {
         String appPath = aMB.getPath();
         if (appPath != null) {
            File path = new File(appPath);
            if (!path.exists()) {
               throw new FileNotFoundException("No such path: " + path);
            }

            if (path.isDirectory()) {
               ApplicationFileManager appFileManager = ApplicationFileManager.newInstance(path.getCanonicalPath());
               VirtualJarFile vjf = appFileManager.getVirtualJarFile();
               if (vjf.getEntry(APP_DD_PATH) != null) {
                  result = 2;
               } else {
                  ComponentMBean[] cMB = aMB.getComponents();
                  if (cMB != null && cMB.length > 0) {
                     if (ArchiveUtils.isValidArchiveName(cMB[0].getURI())) {
                        result = 1;
                     } else {
                        File f = new File(aMB.getPath() + File.separatorChar + cMB[0].getURI());
                        if (f.isDirectory()) {
                           result = 3;
                        } else {
                           result = 5;
                        }
                     }
                  }
               }
            } else if (path.toString().endsWith(".ear")) {
               result = 0;
            }
         }
      }

      return result;
   }

   public static void addAppInfToClasspath(StringBuffer classpath, File rootDir) {
      File classesDir = new File(rootDir, APP_INF_CLASS);
      if (classesDir.exists() && classesDir.isDirectory()) {
         classpath.append(classesDir.getPath());
         classpath.append(File.pathSeparator);
      }

      File libDir = new File(rootDir, APP_INF_LIB);
      if (libDir.exists() && libDir.isDirectory()) {
         File[] libs = libDir.listFiles();
         if (libs != null) {
            for(int i = 0; i < libs.length; ++i) {
               if (!libs[i].isDirectory()) {
                  classpath.append(libs[i].getPath());
                  classpath.append(File.pathSeparator);
               }
            }
         }
      }

   }

   public static String getAppScopedLinkPath(String link, String jarName, Context baseCtx) throws NamingException {
      if (link.indexOf("#") > 0) {
         if (link.startsWith("../")) {
            link = makePathAbsolute(link, jarName);
         }

         return link;
      } else {
         NamingEnumeration bindings = baseCtx.listBindings("");

         String name;
         do {
            if (!bindings.hasMoreElements()) {
               return findByName(baseCtx, link);
            }

            name = ((Binding)bindings.nextElement()).getName();
         } while(!name.equals(jarName + "#" + link));

         return name;
      }
   }

   public static String makePathAbsolute(String relativePath, String jarName) {
      jarName = normalizeJarName(jarName);
      int i = 0;
      int j = jarName.length();

      while(relativePath.regionMatches(i, "../", 0, 3)) {
         i += 3;
         if (j >= 0) {
            j = jarName.lastIndexOf(47, j - 1);
         }
      }

      return jarName.substring(0, j + 1) + relativePath.substring(i);
   }

   public static String normalizeJarName(String n) {
      return escapeChars(n, new char[]{'\'', '"', '.'});
   }

   public static String normalizeJNDIName(String n) {
      return escapeChars(n, new char[]{'/', '.'});
   }

   public static String escapeChars(String n, char[] chars) {
      if (n == null) {
         return null;
      } else {
         StringBuffer s = new StringBuffer();

         for(int i = 0; i < n.length(); ++i) {
            char c = n.charAt(i);
            if (c == '\\' && i < n.length() - 1) {
               StringBuffer var10000 = s.append(c);
               ++i;
               var10000.append(n.charAt(i));
            } else {
               for(int j = 0; j < chars.length; ++j) {
                  if (c == chars[j]) {
                     s.append('\\');
                     break;
                  }
               }

               s.append(c);
            }
         }

         return s.toString();
      }
   }

   private static String findByName(Context ctx, String ejbLink) throws NamingException {
      NamingEnumeration bindings = ctx.listBindings("");

      String name;
      do {
         if (!bindings.hasMoreElements()) {
            return null;
         }

         Binding binding = (Binding)bindings.nextElement();
         name = binding.getName();
      } while(!name.endsWith("#" + ejbLink));

      return name;
   }

   public static WebLogicMBean createComponentMBean(String name, String version, String type, ApplicationMBean appMBean) throws ApplicationException {
      String id = ApplicationVersionUtils.getApplicationId(name, version);
      WebLogicMBean result = null;
      if (type.equals(EJB_MODULE_TYPE)) {
         result = appMBean.createEJBComponent(id);
      } else if (type.equals(WEB_MODULE_TYPE)) {
         result = appMBean.createWebAppComponent(id);
      } else if (type.equals(CONNECTOR_MODULE_TYPE)) {
         result = appMBean.createConnectorComponent(id);
      } else if (type.equals(WEBSERVICE_MODULE_TYPE)) {
         result = appMBean.createWebServiceComponent(id);
      } else if (type.equals(JDBCPOOL_MODULE_TYPE)) {
         result = appMBean.createJDBCPoolComponent(id);
      } else {
         if (!type.equals(JMS_MODULE_TYPE)) {
            throw new AssertionError("Invalid Module type specified  :  " + type);
         }

         result = appMBean.createDummyComponent(id);
      }

      if (result == null) {
         Loggable l = J2EELogger.logMBeanCreationFailureLoggable(name, type, "Could not create MBean", (Throwable)null);
         throw new ApplicationException(l.getMessage());
      } else {
         return (WebLogicMBean)result;
      }
   }

   public static boolean isEJB(File f) throws IOException {
      if (f.isDirectory()) {
         File ejbJar = new File(f, EJB_DD_PATH);
         return ejbJar.exists() && ejbJar.length() > 0L || EJB_ANNOTATION_DETECTOR.isAnnotated(f);
      } else if (f.getName().endsWith(".jar") && f.exists()) {
         JarFile jf = null;

         boolean var3;
         try {
            jf = new JarFile(f);
            ZipEntry ze = jf.getEntry("META-INF/ejb-jar.xml");
            var3 = ze != null && ze.getSize() != 0L || EJB_ANNOTATION_DETECTOR.isAnnotated((ZipFile)jf);
         } finally {
            if (jf != null) {
               jf.close();
            }

         }

         return var3;
      } else {
         return false;
      }
   }

   public static boolean isEJB(VirtualJarFile jf) throws IOException {
      ZipEntry ze = jf.getEntry("META-INF/ejb-jar.xml");
      return ze != null && ze.getSize() != 0L || EJB_ANNOTATION_DETECTOR.isAnnotated(jf);
   }

   public static boolean isRar(File f) {
      if (f.isDirectory()) {
         return !(new File(f, "META-INF" + File.separator + "ra.xml")).exists() && !(new File(f, "META-INF" + File.separator + "weblogic-ra.xml")).exists() ? detectRARAnnotation(f) : true;
      } else {
         return f.getName().endsWith(".rar");
      }
   }

   public static boolean detectRARAnnotation(File f) {
      return (new RARAnnotationDetector()).foundJCAAnnotation(f);
   }

   static {
      sep = File.separator;
      APP_DD_PATH = sep + "META-INF" + sep + "application.xml";
      WLAPP_DD_PATH = sep + "META-INF" + sep + "weblogic-application.xml";
      WEB_DD_PATH = sep + "WEB-INF" + sep + "web.xml";
      WEBSERVICE_DD_PATH = "WEB-INF" + sep + "web-services.xml";
      EJB_DD_PATH = sep + "META-INF" + sep + "ejb-jar.xml";
      WAR_EJB_DD_PATH = sep + "WEB-INF" + sep + "ejb-jar.xml";
      PERCONF_DD_PATH = sep + "META-INF" + sep + "persistence-configuration.xml";
      RAR_DD_PATH = sep + "META-INF" + sep + "ra.xml";
      WLRAR_DD_PATH = sep + "META-INF" + sep + "weblogic-ra.xml";
      CAR_DD_PATH = sep + "META-INF" + sep + "application-client.xml";
      WLCAR_DD_PATH = sep + "META-INF" + sep + "weblogic-application-client.xml";
      APP_INF_LIB = "APP-INF" + sep + "lib";
      APP_INF_CLASS = "APP-INF" + sep + "classes";
      EJB_MODULE_TYPE = "EJBComponent";
      WEB_MODULE_TYPE = "WebAppComponent";
      CONNECTOR_MODULE_TYPE = "ConnectorComponent";
      WEBSERVICE_MODULE_TYPE = "WebServiceComponent";
      JDBCPOOL_MODULE_TYPE = "JDBCPoolComponent";
      JMS_MODULE_TYPE = "JMSComponent";
      APPCLIENT_MODULE_TYPE = "AppClientComponent";
      EJB_ANNOTATION_DETECTOR = new AnnotationDetector((Class[])DDConstants.COMPONENT_DEFINING_ANNOS.toArray(new Class[0]));
   }

   private static class RARAnnotationDetector {
      private static AnnotationDetector rarAnnotationDetector = new AnnotationDetector(new Class[]{Connector.class, Activation.class, ConnectionDefinition.class, ConnectionDefinitions.class});

      private RARAnnotationDetector() {
      }

      public boolean foundJCAAnnotation(File f) {
         return this.foundInJar(f) ? true : this.foundInNestedJars(f);
      }

      private boolean foundInJar(File f) {
         boolean found = false;

         try {
            found = rarAnnotationDetector.isAnnotated(f);
         } catch (IOException var4) {
         }

         return found;
      }

      private boolean foundInNestedJars(File f) {
         String[] list = f.list();
         String[] var3 = list;
         int var4 = list.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String name = var3[var5];
            File nested = new File(f, name);
            if (nested.isDirectory()) {
               if (this.foundInNestedJars(nested)) {
                  return true;
               }

               if (name.endsWith(".jar") && this.foundInJar(nested)) {
                  return true;
               }
            }
         }

         return false;
      }

      // $FF: synthetic method
      RARAnnotationDetector(Object x0) {
         this();
      }
   }
}
