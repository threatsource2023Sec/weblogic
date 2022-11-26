package weblogic.deploy.api.tools;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.application.compiler.AppMerge;
import weblogic.application.io.mvf.MultiVersionFile;
import weblogic.application.io.mvf.MultiVersionFileFactory;
import weblogic.application.io.mvf.VersionFile;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.wl.AppDeploymentBean;
import weblogic.j2ee.descriptor.wl.DeploymentConfigOverridesBean;
import weblogic.j2ee.descriptor.wl.DeploymentConfigOverridesInputBean;
import weblogic.j2ee.descriptor.wl.LibraryBean;
import weblogic.utils.BadOptionException;
import weblogic.utils.StringUtils;
import weblogic.utils.compiler.Tool;
import weblogic.utils.compiler.ToolFailureException;

public class OverrideDeploymentConfig extends Tool {
   protected OverrideDeploymentConfig(String[] args) {
      super(args);
   }

   public void prepare() {
      this.setUsageName("weblogic.OverrideDeploymentConfig");
      this.opts.setUsageArgs("The path to deployment-config-overrides-input.xml file");
      this.opts.addOption("algorithm", "hash algorithm for version", "The algorithm to use for generating the checksum or hash version of the application");
      this.opts.addOption("output", "file", "The path to a directory where generated file will be written. The path cannot point to a directory where a deployment-config-overrides.xml already exists.");
      this.opts.addOption("print", "file", "The path to a generated deployment-config-overrides.xml file to print info");
      this.opts.addOption("tokens", "tokens", "Comma-separate name-value pairs in the \"name=value\" format. Any instance of \"name\" in the deployment-config-overrides.xml will be replaced by the corresponding \"value\"");
      this.opts.addFlag("ignoreMissingLibRefs", "Don't verify that all the libraries referred in the application are specified in command line library options. This may cause failures to load up application in case such libraries were needed");
      this.opts.addFlag("verbose", "Prints out additional messages");
   }

   public void runBody() throws ToolFailureException, IOException {
      DescriptorManager descriptorManager = new DescriptorManager();
      InputStream is = null;
      FileOutputStream outputStream = null;

      try {
         String deploymentConfigOverridesFile = this.opts.getOption("print");
         if (deploymentConfigOverridesFile != null) {
            if (this.opts.args().length > 0) {
               throw new BadOptionException("The print command only takes the deployment-config-overrides.xml as the parameter");
            } else {
               this.printDeploymentConfigOverrides(deploymentConfigOverridesFile);
            }
         } else {
            String algorithm = this.opts.getOption("algorithm");
            is = this.getInputStream(this.getInputFileName(), this.opts.getOption("tokens"));
            DeploymentConfigOverridesInputBean deploymentConfigOverridesInputBean = (DeploymentConfigOverridesInputBean)descriptorManager.createDescriptor(is).getRootBean();
            String output = this.opts.getOption("output");
            if (output == null) {
               throw new BadOptionException("The output option cannot be null. Please provide the path to a directory where generated file will be written");
            } else {
               File outputFile = new File(output, "deployment-config-overrides.xml");
               if (outputFile.exists()) {
                  throw new BadOptionException("The path cannot point to a directory where a deployment-config-overrides.xml already exists.");
               } else {
                  outputStream = new FileOutputStream(outputFile);
                  Descriptor outputDescriptor = descriptorManager.createDescriptorRoot(DeploymentConfigOverridesBean.class);
                  DeploymentConfigOverridesBean deploymentConfigOverridesBean = (DeploymentConfigOverridesBean)outputDescriptor.getRootBean();
                  StringBuffer sb = new StringBuffer(this.opts.asCommandLine());
                  sb.append(" " + this.getInputFileName());
                  deploymentConfigOverridesBean.setCommandLineOptions(sb.toString());
                  LibraryBean[] var12 = deploymentConfigOverridesInputBean.getLibraries();
                  int var13 = var12.length;

                  int var14;
                  MultiVersionFile multiVersionFile;
                  VersionFile versionFile;
                  for(var14 = 0; var14 < var13; ++var14) {
                     LibraryBean lib = var12[var14];
                     multiVersionFile = MultiVersionFileFactory.instance.createMultiVersionApplicationFile(lib.getSourcePath());
                     if (multiVersionFile == null) {
                        throw new ToolFailureException("The library " + lib.getName() + " must be in \"multi-version\" format.");
                     }

                     versionFile = multiVersionFile.getLatest();
                     LibraryBean newLib = deploymentConfigOverridesBean.createLibrary(lib.getName());
                     newLib.setSourcePath(versionFile.getFile().getPath());
                     newLib.setGeneratedVersion(versionFile.getVersion());
                  }

                  AppDeploymentBean[] var26 = deploymentConfigOverridesInputBean.getAppDeployments();
                  var13 = var26.length;

                  for(var14 = 0; var14 < var13; ++var14) {
                     AppDeploymentBean app = var26[var14];
                     multiVersionFile = MultiVersionFileFactory.instance.createMultiVersionApplicationFile(app.getSourcePath());
                     if (multiVersionFile == null) {
                        throw new ToolFailureException("The application " + app.getName() + " must be in \"multi-version\" format.");
                     }

                     versionFile = multiVersionFile.getLatest();
                     AppDeploymentBean newApp = deploymentConfigOverridesBean.createAppDeployment(app.getName());
                     newApp.setSourcePath(versionFile.getFile().getPath());
                     if (app.isRetireTimeoutSet()) {
                        newApp.setRetireTimeout(app.getRetireTimeout());
                     }

                     String[] libraries = new String[deploymentConfigOverridesBean.getLibraries().length];

                     for(int i = 0; i < deploymentConfigOverridesBean.getLibraries().length; ++i) {
                        libraries[i] = getLibraryInfo(deploymentConfigOverridesBean.getLibraries()[i]);
                     }

                     newApp.setGeneratedVersion(AppMerge.getApplicationVersion(algorithm, newApp.getSourcePath(), this.opts.hasOption("ignoreMissingLibRefs"), this.opts.hasOption("verbose"), libraries));
                  }

                  descriptorManager.writeDescriptorAsXML(outputDescriptor, outputStream);
               }
            }
         }
      } catch (BadOptionException var24) {
         throw new ToolFailureException(var24.getMessage());
      } finally {
         if (is != null) {
            is.close();
         }

         if (outputStream != null) {
            outputStream.close();
         }

      }
   }

   private Map parseTokens(String tokensOption) {
      Map tokenMap = new HashMap();
      if (tokensOption != null) {
         String[] valuePair = StringUtils.splitCompletely(tokensOption, ",", false);

         for(int i = 0; i < valuePair.length; ++i) {
            this.addToken(valuePair[i], tokenMap);
         }
      }

      return tokenMap;
   }

   private void addToken(String tokenString, Map tokenMap) {
      String[] tokens = StringUtils.splitCompletely(tokenString, "=");
      if (tokens.length != 2) {
         throw new AssertionError("Attributes must be defined as name value pairs, eg, name=\"value\" -- [" + tokenString + "]");
      } else {
         tokenMap.put(tokens[0], tokens[1]);
      }
   }

   private String getInputFileName() {
      String[] args = this.opts.args();
      return args[args.length - 1];
   }

   private InputStream getInputStream(String fileName, String tokensOption) throws BadOptionException {
      if (fileName == null) {
         throw new BadOptionException("The path to deployment-config-overrides-input.xml file is invalid");
      } else {
         try {
            Map tokenMap = this.parseTokens(tokensOption);
            String content = this.readFile(fileName, tokenMap);
            return new ByteArrayInputStream(content.getBytes());
         } catch (FileNotFoundException var5) {
            var5.printStackTrace();
            throw new BadOptionException(var5.getMessage());
         } catch (IOException var6) {
            var6.printStackTrace();
            throw new BadOptionException(var6.getMessage());
         }
      }
   }

   private String readFile(String fileName, Map tokenMap) throws IOException {
      String fileContent = this.readFile(fileName);
      return this.replaceTokens(fileContent, tokenMap);
   }

   private String readFile(String fileName) throws IOException {
      BufferedReader reader = new BufferedReader(new FileReader(fileName));
      StringBuilder sb = new StringBuilder();
      String ls = System.getProperty("line.separator");

      String var6;
      try {
         String line;
         while((line = reader.readLine()) != null) {
            sb.append(line).append(ls);
         }

         var6 = sb.toString();
      } finally {
         reader.close();
      }

      return var6;
   }

   private String replaceTokens(String origString, Map tokenMap) {
      String newString = origString;

      String key;
      for(Iterator var4 = tokenMap.keySet().iterator(); var4.hasNext(); newString = newString.replace(key, (CharSequence)tokenMap.get(key))) {
         key = (String)var4.next();
      }

      return newString;
   }

   private void printDeploymentConfigOverrides(String deploymentConfigOverridesFile) throws BadOptionException, IOException {
      InputStream is = null;

      try {
         is = new FileInputStream(deploymentConfigOverridesFile);
         DescriptorManager descriptorManager = new DescriptorManager();
         DeploymentConfigOverridesBean deploymentConfigOverridesBean = (DeploymentConfigOverridesBean)descriptorManager.createDescriptor(is).getRootBean();
         AppDeploymentBean[] var5 = deploymentConfigOverridesBean.getAppDeployments();
         int var6 = var5.length;

         int var7;
         for(var7 = 0; var7 < var6; ++var7) {
            AppDeploymentBean app = var5[var7];
            this.print(app);
         }

         LibraryBean[] var14 = deploymentConfigOverridesBean.getLibraries();
         var6 = var14.length;

         for(var7 = 0; var7 < var6; ++var7) {
            LibraryBean lib = var14[var7];
            this.print(lib);
         }
      } catch (FileNotFoundException var12) {
         var12.printStackTrace();
         throw new BadOptionException(var12.getMessage());
      } finally {
         if (is != null) {
            is.close();
         }

      }

   }

   private void print(AppDeploymentBean bean) {
      String configuredAppId = bean.getName();
      String appName = ApplicationVersionUtils.getNonVersionedName(bean.getName());
      String configuredVersion = ApplicationVersionUtils.getVersionId(bean.getName());
      String inferredVersion = null;
      if (configuredVersion != null && !configuredVersion.equals("")) {
         inferredVersion = configuredVersion + "." + bean.getGeneratedVersion();
      } else {
         inferredVersion = bean.getGeneratedVersion();
      }

      String inferredAppId = appName + "#" + inferredVersion;
      System.out.println(configuredAppId + " " + inferredAppId + " " + inferredVersion);
   }

   static String getLibraryInfo(LibraryBean bean) {
      String appName = ApplicationVersionUtils.getNonVersionedName(bean.getName());
      String configuredVersion = ApplicationVersionUtils.getVersionId(bean.getName());
      String inferredVersion = null;
      if (configuredVersion != null && !configuredVersion.equals("")) {
         inferredVersion = configuredVersion + "." + bean.getGeneratedVersion();
      } else {
         inferredVersion = bean.getGeneratedVersion();
      }

      return bean.getSourcePath() + "@name=" + appName + "@libspecver=" + inferredVersion;
   }

   public static void main(String[] args) throws Exception {
      (new OverrideDeploymentConfig(args)).run();
   }
}
