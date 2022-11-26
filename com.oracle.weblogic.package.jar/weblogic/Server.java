package weblogic;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Future;
import org.glassfish.hk2.api.ServiceLocator;
import utils.ValidateJavaEE6EndorsedOverrides;
import weblogic.kernel.T3SrvrLogger;
import weblogic.management.DomainDir;
import weblogic.security.utils.SecurityUtils;
import weblogic.server.GlobalServiceLocator;
import weblogic.t3.srvr.ShutdownOnExitThread;
import weblogic.t3.srvr.T3ServerFuture;
import weblogic.t3.srvr.T3Srvr;
import weblogic.utils.ArrayUtils;
import weblogic.utils.Classpath;
import weblogic.utils.FileUtils;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.JarClassFinder;
import weblogic.utils.classloaders.MultiClassFinder;
import weblogic.utils.io.ExtensionFilter;
import weblogic.work.ExecuteThread;

public final class Server {
   private static final String WEBLOGIC_EXTENSION_DIRS = "weblogic.ext.dirs";
   private static final String PATH_SEPARATOR_STRING;
   public static final String CLASSLOADER_PREPROCESSOR = "weblogic.classloader.preprocessor";
   private static final String WEBLOGIC_NFS_HEALTHCHECK = "weblogic.nfs.healthcheck";

   public static Future embeddedMain(String[] argv) throws Exception {
      if (argv.length <= 0 || !argv[0].equals("-help") && !argv[0].equals("-?")) {
         ValidateJavaEE6EndorsedOverrides.validateEndorsedOverrides(true);
         SecurityUtils.turnOffCryptoJDefaultJCEVerification();
         SecurityUtils.changeCryptoJDefaultPRNG();
         System.setProperty("javax.management.builder.initial", "weblogic.management.jmx.mbeanserver.WLSMBeanServerBuilder");
         intializeClassloader();
         ServiceLocator locator = GlobalServiceLocator.getServiceLocator();
         T3Srvr t3Srvr = (T3Srvr)locator.getService(T3Srvr.class, new Annotation[0]);
         T3ServerFuture exitStatus = t3Srvr.run(argv);
         ShutdownOnExitThread.exitViaServerLifeCycle = true;
         return exitStatus;
      } else {
         System.out.println(getUsage());
         return new T3ServerFuture(0);
      }
   }

   public static void main(String[] argv) {
      int retVal = 0;
      String jvmVer = System.getProperty("java.version");
      if (jvmVer != null && "1.8".compareTo(jvmVer) > 0) {
         System.err.println("weblogic.Server isn't supported by JDK versions less than 1.8");
         retVal = 1;

         try {
            System.exit(retVal);
         } catch (Throwable var10) {
            return;
         }
      }

      if (Boolean.getBoolean("weblogic.nfs.healthcheck")) {
         String[] jarsToCheck = new String[]{"weblogic.jar"};
         long time = System.currentTimeMillis();
         ArrayList warnings = Classpath.sanityCheck(jarsToCheck);
         time = System.currentTimeMillis() - time;
         System.out.println("NFS check took " + time + " milliseconds");
         if (!warnings.isEmpty()) {
            System.err.println("***************************************************************************");
            System.err.println("NFS health check ended with warnings: ");
            Iterator var7 = warnings.iterator();

            while(var7.hasNext()) {
               String warning = (String)var7.next();
               System.err.println(warning);
            }

            System.err.println("***************************************************************************");
         }
      }

      new T3ServerFuture(0);

      try {
         Future exitStatus = embeddedMain(argv);
         retVal = (Integer)exitStatus.get();
      } catch (Exception var11) {
         if (var11 instanceof AccessControlException) {
            System.err.println("***************************************************************************");
            System.err.println("The WebLogic Server encountered a critical failure");
            System.err.println("Exception raised: '" + var11 + "'");
            System.err.println("Check you have both java.security.manager and java.security.policy defined and java.security.policy has the correct entries");
            System.err.println("***************************************************************************");
         }
      }

      try {
         System.exit(retVal);
      } catch (Throwable var9) {
      }

   }

   private static URL[] getURLs(File[] jars) {
      if (jars != null && jars.length != 0) {
         URL[] urls = new URL[jars.length];

         for(int i = 0; i < jars.length; ++i) {
            try {
               urls[i] = jars[i].toURL();
            } catch (MalformedURLException var4) {
               throw new AssertionError(var4);
            }
         }

         return urls;
      } else {
         return null;
      }
   }

   private static File[] getJars(File file) {
      if (file.exists()) {
         if (file.isFile()) {
            return new File[]{file};
         }

         if (file.isDirectory()) {
            return FileUtils.find(file, new ExtensionFilter("jar"));
         }
      }

      return null;
   }

   private static void appendToClassPath(File[] jars) {
      if (jars != null) {
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < jars.length; ++i) {
            Classpath.append(jars[i]);

            try {
               if (i != 0) {
                  sb.append(PATH_SEPARATOR_STRING);
               }

               sb.append(jars[i].getCanonicalPath());
            } catch (IOException var4) {
            }
         }

         T3SrvrLogger.logDomainLibPath(sb.toString());
      }
   }

   private static void intializeClassloader() throws IOException {
      ClassLoader cl = ClassLoader.getSystemClassLoader();
      File[] jars = getExtensionJars();
      MultiClassFinder finder = new MultiClassFinder();
      if (jars != null) {
         File[] var3 = jars;
         int var4 = jars.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            File j = var3[var5];
            finder.addFinder(new JarClassFinder(j));
         }
      }

      GenericClassLoader weblogicExtensionLoader = new GenericClassLoader(finder, cl);
      weblogicExtensionLoader.setAnnotation(weblogic.utils.classloaders.Annotation.createNonAppAnnotation("System:WebLogicExtension"));
      if (jars != null && jars.length > 0) {
         appendToClassPath(jars);
      }

      ExecuteThread.setWeblogicSystemLoader(weblogicExtensionLoader);
      Thread.currentThread().setContextClassLoader(weblogicExtensionLoader);
   }

   private static File[] getExtensionJars() {
      File[] contents = getLibraryExtensions();
      if (contents != null && contents.length != 0) {
         ArrayList jarList = new ArrayList();

         for(int count = 0; count < contents.length; ++count) {
            File[] files = getJars(contents[count]);
            if (files != null && files.length != 0) {
               Arrays.sort(files);
               ArrayUtils.addAll(jarList, files);
            }
         }

         File[] jarsArray = new File[jarList.size()];
         jarList.toArray(jarsArray);
         return jarsArray;
      } else {
         return null;
      }
   }

   private static final File[] getLibraryExtensions() {
      ArrayList exts = new ArrayList();
      String paths = System.getProperty("weblogic.ext.dirs");
      boolean replace = false;
      if (paths != null) {
         if (paths.startsWith("=")) {
            replace = true;
            paths = paths.substring(1);
         }

         String[] contents = StringUtils.splitCompletely(paths, PATH_SEPARATOR_STRING);

         for(int i = 0; i < contents.length; ++i) {
            exts.add(new File(contents[i]));
         }
      }

      if (!replace) {
         exts.add(new File(new File(DomainDir.getRootDir()), "lib"));
         File wlhome = new File(Home.getPath());
         if (wlhome.getParentFile() != null) {
            exts.add(new File(wlhome.getParentFile(), "common" + File.separator + "lib" + File.separator + "ext"));
         }

         exts.add(new File(wlhome, "lib" + File.separator + "ext"));
      }

      return (File[])((File[])exts.toArray(new File[exts.size()]));
   }

   public String toString() {
      return "WebLogic Server";
   }

   public static String getUsage() {
      return "Usage: java [options] weblogic.Server [args...]\n\nWhere WebLogic options include:\n\t-Djava.security.policy=<value>\t the location of the security policy\n\t\t\t\t\t file\n\t-Dweblogic.Domain=<value>\t WebLogic domain name\n\t-Dweblogic.Name=<value>\t\t WebLogic server name\n\t-Dweblogic.ext.dirs=<value>\n\t\t\t\t\t '" + PATH_SEPARATOR_STRING + "' separated list of directories to pick up jars from \n\t\t\t\t\t and add to the end of the server classpath.\n\t\t\t\t\t The list can also contain individual jars.\n\t-Dweblogic.management.server=<value>\n\t\t\t\t\t WebLogic Admin Server URL for starting\n\t\t\t\t\t a Managed Server, the value can be:\n\t\t\t\t\t host:port or \n\t\t\t\t\t http://host:port or\n\t\t\t\t\t https://host:port\n\t-Dweblogic.home=<value>\n\t\t\t\t\t The location of the WebLogic Server\n\t\t\t\t\t product install.  By default, this will\n\t\t\t\t\t be derived from the classpath.\n\t-Dweblogic.RootDirectory=<value>\n\t\t\t\t\t The root directory of your domain,\n\t\t\t\t\t where your configuration is housed.\n\t\t\t\t\t default is the current working\n\t\t\t\t\t directory\n\t-Dweblogic.management.username=<value>\n\t\t\t\t\t user name\n\t-Dweblogic.management.password=<value>\n\t\t\t\t\t user password\n\t-Dweblogic.management.pkpassword=<value>\n\t\t\t\t\t private key password\n\t-Dweblogic.security.unixrealm.authProgram=<value>\n\t\t\t\t\t the name of the program used to\n\t\t\t\t\t authenticate users in the unix\n\t\t\t\t\t security realm\n\t-Dweblogic.<ServerAttributeName>=<value>\n\t\t\t\t\t specify a server attribute, it will\n\t\t\t\t\t override the attribute value set in\n\t\t\t\t\t config.xml for this server\n\t-Dweblogic.admin.host=<value>\t same as weblogic.management.server, an\n\t\t\t\t\t old property \n\t-javaagent:$WL_HOME/server/lib/diagnostics-agent.jar\n\t\t\t\t\t enable diagnostics hot code-swap for application classes\nAnd WebLogic args include:\n\t-? -help\t\t\t print this help message\n";
   }

   static {
      PATH_SEPARATOR_STRING = File.pathSeparator;
   }
}
