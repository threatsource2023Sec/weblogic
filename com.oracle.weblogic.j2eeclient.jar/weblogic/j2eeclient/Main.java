package weblogic.j2eeclient;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.utils.IOUtils;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.security.service.WLSPolicy;
import weblogic.utils.Getopt2;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class Main {
   private static final int NUMBER_OF_CONTAINER_ARGUMENTS = 2;
   private static final int URL_INDEX = 1;
   private static final int CLIENT_PATH_INDEX = 0;
   private static final AppClientTextTextFormatter TEXT_FORMATTER = AppClientTextTextFormatter.getInstance();

   public static void main(String[] argv) throws Exception {
      if (System.getSecurityManager() != null) {
         WLSPolicy wlsPolicy = new WLSPolicy();
         wlsPolicy.init();
      }

      Getopt2 opts = getOptions();
      opts.grok(argv);
      run(opts);
   }

   private static void run(Getopt2 opts) throws IOException, XMLStreamException, Exception {
      String clientName = opts.getOption("clientName");
      String applicationName = opts.getOption("serverApplicationName");
      String[] args = opts.args();
      if (args.length < 2) {
         System.out.println(opts.fullUsageMessage(Main.class.getName()));
         System.exit(1);
      }

      File pathToArchive = new File(args[0]);
      List libraries = new ArrayList();
      String clientUri = pathToArchive.isDirectory() ? processExplodedEar(pathToArchive, libraries) : null;
      if (clientName != null && !clientName.isEmpty()) {
         clientUri = clientName;
      }

      File pathToClientJar = clientUri == null ? pathToArchive : new File(pathToArchive, clientUri);
      String url = args[1];
      String configDirName = null;
      String planFileName = null;
      String[] userCodeArgs = getUserCodeArgs(args);
      AppClientContainer m = new AppClientContainer(pathToClientJar, url, userCodeArgs, (String)configDirName, (String)planFileName, clientName, applicationName, (File[])libraries.toArray(new File[libraries.size()]));
      m.run();
   }

   private static String[] getUserCodeArgs(String[] argv) {
      String[] userCodeArgs = new String[argv.length - 2];
      System.arraycopy(argv, 2, userCodeArgs, 0, userCodeArgs.length);
      return userCodeArgs;
   }

   private static Getopt2 getOptions() {
      Getopt2 opts = new Getopt2();
      String containing = TEXT_FORMATTER.wordContaining();
      opts.setUsageArgs("[clientjar|exploded ear " + containing + " clientjar] URL [client-args]...");
      String example = TEXT_FORMATTER.wordExample();
      opts.setUsageFooter(example + " java weblogic.j2eeclient.Main appclient.jar t3://localhost:7001");
      String clientNameMeaning = TEXT_FORMATTER.clientNameMeaning();
      opts.addOption("clientName", "client-jar-name", clientNameMeaning);
      String serverNameMeaning = TEXT_FORMATTER.serverNameMeaning();
      opts.addOption("serverApplicationName", "server-application-name", serverNameMeaning);
      return opts;
   }

   private static String processExplodedEar(File file, List libs) throws IOException, XMLStreamException {
      String clientJarUri = null;
      if (file.isDirectory()) {
         VirtualJarFile earVjf = VirtualJarFactory.createVirtualJar(file);

         try {
            ApplicationDescriptor appDesc = new ApplicationDescriptor(earVjf);
            ApplicationBean appBean = appDesc.getApplicationDescriptor();
            if (appBean != null) {
               ModuleBean[] modules = appBean.getModules();

               for(int count = 0; count < modules.length && clientJarUri == null; ++count) {
                  clientJarUri = modules[count].getJava();
               }

               if (clientJarUri != null) {
                  String applib = appBean.getLibraryDirectory();
                  if (applib != null) {
                     File libraryDirectory = new File(file, applib);
                     if (libraryDirectory.isDirectory()) {
                        File[] files = libraryDirectory.listFiles(new FileFilter() {
                           public boolean accept(File file) {
                              return !file.isDirectory() && file.getName().endsWith(".jar");
                           }
                        });
                        if (files != null) {
                           libs.addAll(Arrays.asList(files));
                        }
                     }
                  }
               }
            }
         } finally {
            IOUtils.forceClose(earVjf);
         }

         return clientJarUri;
      } else {
         throw new AssertionError("Expected and exploded ear");
      }
   }
}
