package weblogic.descriptor.codegen;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import weblogic.utils.Getopt2;
import weblogic.utils.codegen.ImplementationFactory;

public class CodeGenOptions implements CodeGenOptionsBean {
   public static final String SOURCE_PATH = "sourcepath";
   public static final String CLASS_PATH = "classpath";
   public static final String SOURCE_DIR = "sourcedir";
   public static final String TEMPLATE_DIR = "templatedir";
   public static final String OUTPUT_DIRECTORY = "d";
   public static final String BUNDLE_OUTPUT_DIRECTORY = "bundle-output";
   public static final String TEMPLATE = "template";
   public static final String SUFFIX = "suffix";
   public static final String TARGET_EXTENSION = "target-extension";
   public static final String SOURCE_EXTENSION = "source-extension";
   public static final String EXCLUDE = "exclude";
   public static final String PACKAGE = "package";
   public static final String PACKAGE_SUFFIX = "package-suffix";
   public static final String GENERATE_TO_CUSTOM = "generate-to-custom";
   public static final String BEAN_FACTORY = "beanfactory";
   public static final String EXTERNAL_DIR = "externaldir";
   public static final String BASE_BEAN_FACTORY = "basebeanfactory";
   public static final String VERBOSE = "verbose";
   private String sourceDir;
   private String templateDir;
   private String sourcePath;
   private String targetDirectory;
   private String resourceBundleTargetDirectory;
   private String outputPackage;
   private String packageSuffix;
   private String implPackageSuffix;
   private boolean generateToCustom = false;
   private String templateName;
   private String[] sources;
   private String classpath = null;
   private String[] excludes = new String[0];
   private String suffix = "Impl";
   private String targetExtension = ".java";
   private String sourceExtension = ".java";
   private String externalDir;
   private long timestamp = 0L;
   private String[] baseBeanFactories = new String[0];
   private ImplementationFactory[] implementationFactories = null;
   private String beanFactory = null;
   protected String defaultTemplate = null;
   private boolean verbose = false;

   public static void addOptionsToGetOpts(Getopt2 opts) {
      opts.addOption("sourcepath", "pathlist", "Specify additional directory for referenced sources files");
      opts.addOption("classpath", "pathlist", "Specify directories or jar files for referenced class/source files");
      opts.addOption("sourcedir", "directory", "Specify where to find sources files");
      opts.addOption("templatedir", "directory", "Specify where to find template files");
      opts.addOption("d", "directory", "Specify where to place generated files");
      opts.addOption("bundle-output", "directory", "Specify where to place generated resource bundle files");
      opts.addOption("suffix", "string", "Specify the suffix used to construct the name for each generated file");
      opts.addOption("package", "directory", "The package into which the implementation files reside");
      opts.addOption("package-suffix", "string", "The package suffix to be added to the package of the interface class in which the implementation files reside");
      opts.addOption("generate-to-custom", "string", "Generate files based on the customizer tag");
      opts.addMultiOption("exclude", "string", "Specify a file to exclude from the file set to generate from");
      opts.addOption("template", "<template>", "Template name");
      opts.addOption("beanfactory", "string", "The name of the factory class to generate.");
      opts.addOption("externaldir", "directory", "Specify where to place external timestamp files");
      opts.addMultiOption("basebeanfactory", "string", "Specify a base beanfactory for looking up base classes for interfaces");
      opts.addOption("verbose", "boolean", "true to output certain debug info such as inputs to codegen");
   }

   public void readOptions(Getopt2 opts, String defaultTemplate) {
      this.setDefaultTemplate(defaultTemplate);
      this.setTargetDirectory(opts.getOption("d", "."));
      this.setResourceBundleTargetDirectory(opts.getOption("bundle-output", "."));
      this.setPackage(opts.getOption("package", (String)null));
      this.setPackageSuffix(opts.getOption("package-suffix", (String)null));
      this.setTemplateName(opts.getOption("template", (String)null));
      this.setSourcePath(opts.getOption("sourcepath", (String)null));
      this.setClasspath(opts.getOption("classpath", (String)null));
      this.setSourceDir(opts.getOption("sourcedir", "."));
      this.setTemplateDir(opts.getOption("templatedir", (String)null));
      this.setSuffix(opts.getOption("suffix", "Impl"));
      this.setTargetExtension(opts.getOption("target-extension", ".java"));
      this.setSourceExtension(opts.getOption("source-extension", ".java"));
      this.setPackage(opts.getOption("package", (String)null));
      this.setExcludes(opts.getMultiOption("exclude", this.excludes));
      this.setGenerateToCustom(opts.getBooleanOption("generate-to-custom", false));
      this.setBeanFactory(opts.getOption("beanfactory", (String)null));
      this.setExternalDir(opts.getOption("externaldir", (String)null));
      this.setBaseBeanFactories(opts.getMultiOption("basebeanfactory", this.baseBeanFactories));
      this.setSources(opts.args());
      this.setVerbose(opts.getBooleanOption("verbose", false));
   }

   public String[] getExcludes() {
      return this.excludes;
   }

   public String getSourceDir() {
      return this.sourceDir;
   }

   public void setSourceDir(String sourceDir) {
      this.sourceDir = sourceDir;
   }

   public String getTemplateDir() {
      return this.templateDir == null ? this.getSourceDir() : this.templateDir;
   }

   public void setTemplateDir(String templateDir) {
      this.templateDir = templateDir;
   }

   public String getSourcePath() {
      return this.sourcePath;
   }

   public void setSourcePath(String sourcePath) {
      this.sourcePath = sourcePath;
   }

   public String getTargetDirectory() {
      return this.targetDirectory;
   }

   public void setTargetDirectory(String targetDirectory) {
      this.targetDirectory = targetDirectory;
   }

   public String getResourceBundleTargetDirectory() {
      return this.resourceBundleTargetDirectory;
   }

   public void setResourceBundleTargetDirectory(String targetDirectory) {
      this.resourceBundleTargetDirectory = targetDirectory;
   }

   public String getPackage() {
      return this.outputPackage;
   }

   public void setPackage(String outputPackage) {
      this.outputPackage = outputPackage;
   }

   public String getPackageSuffix() {
      return this.packageSuffix;
   }

   public void setPackageSuffix(String packageSuffix) {
      if (packageSuffix != null && packageSuffix.trim().length() >= 1) {
         this.packageSuffix = packageSuffix;
      } else {
         this.packageSuffix = null;
      }

   }

   public String getTemplateName() {
      if (this.templateName == null) {
         this.templateName = this.defaultTemplate;
      }

      return this.templateName;
   }

   public void setTemplateName(String templateName) {
      this.templateName = templateName;
   }

   public String getImplPackageSuffix() {
      return this.implPackageSuffix;
   }

   public void setImplPackageSuffix(String implPackageSuffix) {
      if (implPackageSuffix != null && implPackageSuffix.trim().length() >= 1) {
         this.implPackageSuffix = implPackageSuffix;
      } else {
         this.implPackageSuffix = null;
      }

   }

   public void setDefaultTemplate(String defaultTemplate) {
      this.defaultTemplate = defaultTemplate;
   }

   public void setSuffix(String suffix) {
      this.suffix = suffix;
   }

   public String getSuffix() {
      return this.suffix;
   }

   public void setTargetExtension(String targetExtension) {
      this.targetExtension = targetExtension;
   }

   public String getTargetExtension() {
      return this.targetExtension;
   }

   public void setSourceExtension(String sourceExtension) {
      this.sourceExtension = sourceExtension;
   }

   public String getSourceExtension() {
      return this.sourceExtension;
   }

   public void setGenerateToCustom(boolean booleanOption) {
      this.generateToCustom = booleanOption;
   }

   public String getBeanFactory() {
      return this.beanFactory;
   }

   public void setBeanFactory(String beanFactory) {
      this.beanFactory = beanFactory;
   }

   public boolean isGenerateToCustom() {
      return this.generateToCustom;
   }

   public void setSources(String[] sources) {
      this.sources = sources;
   }

   public String[] getSources() {
      return this.sources;
   }

   public void setClasspath(String classpath) {
      this.classpath = classpath;
   }

   public String getClasspath() {
      return this.classpath;
   }

   public void setExcludes(String[] excludes) {
      this.excludes = excludes;

      for(int i = 0; i < excludes.length; ++i) {
         if (!excludes[i].endsWith(".java")) {
            excludes[i] = excludes[i].replace('.', File.separatorChar) + ".java";
         } else {
            excludes[i] = excludes[i].replace('/', File.separatorChar);
         }
      }

   }

   public String getExternalDir() {
      return this.externalDir;
   }

   public void setExternalDir(String externalDir) {
      this.externalDir = externalDir;
   }

   public String[] getBaseBeanFactories() {
      return this.baseBeanFactories;
   }

   public void setBaseBeanFactories(String[] basebeanfactories) {
      this.baseBeanFactories = basebeanfactories;
   }

   public String getClassForInterface(String interfaceName) {
      if (!this.hasImplementationFactories()) {
         return null;
      } else {
         for(int i = 0; i < this.implementationFactories.length; ++i) {
            String result = this.implementationFactories[i].getImplementationClassName(interfaceName);
            if (result != null) {
               return result;
            }
         }

         return null;
      }
   }

   private ClassLoader getClassLoader() {
      ClassLoader toLoadFrom = CodeGenOptions.class.getClassLoader();
      if (this.classpath == null) {
         return toLoadFrom;
      } else {
         StringTokenizer st = new StringTokenizer(this.classpath, File.pathSeparator);
         URL[] urls = new URL[st.countTokens()];
         int x = 0;

         while(st.hasMoreTokens()) {
            String token = st.nextToken();

            try {
               File file = new File(token);
               URL url = file.toURL();
               urls[x++] = url;
            } catch (MalformedURLException var8) {
               System.out.println("Unable to process Classpath for " + token);
               var8.printStackTrace();
               return CodeGenOptions.class.getClassLoader();
            }
         }

         return new URLClassLoader(urls, toLoadFrom);
      }
   }

   private boolean hasImplementationFactories() {
      if (this.baseBeanFactories.length == 0) {
         return false;
      } else {
         if (this.implementationFactories == null) {
            synchronized(this) {
               if (this.implementationFactories == null) {
                  ArrayList factoryList = new ArrayList();

                  for(int i = 0; i < this.baseBeanFactories.length; ++i) {
                     String baseBeanFactory = this.baseBeanFactories[i];

                     try {
                        Class baseBeanFactoryClass = Class.forName(baseBeanFactory, true, this.getClassLoader());
                        Method newInstanceMethod = baseBeanFactoryClass.getMethod("getInstance", (Class[])null);
                        ImplementationFactory factory = (ImplementationFactory)newInstanceMethod.invoke((Object)null, (Object[])null);
                        factoryList.add(factory);
                     } catch (ClassNotFoundException var9) {
                        System.out.println("Unable to create base bean factory " + baseBeanFactory);
                        var9.printStackTrace();
                     } catch (IllegalAccessException var10) {
                        System.out.println("Unable to instantiate base bean factory " + baseBeanFactory);
                        var10.printStackTrace();
                     } catch (NoSuchMethodException var11) {
                        System.out.println("Unable to instantiate base bean factory " + baseBeanFactory);
                        var11.printStackTrace();
                     } catch (IllegalArgumentException var12) {
                        System.out.println("Unable to instantiate base bean factory " + baseBeanFactory);
                        var12.printStackTrace();
                     } catch (InvocationTargetException var13) {
                        System.out.println("Unable to instantiate base bean factory " + baseBeanFactory);
                        var13.printStackTrace();
                     }
                  }

                  this.implementationFactories = (ImplementationFactory[])factoryList.toArray(new ImplementationFactory[factoryList.size()]);
               }
            }
         }

         return true;
      }
   }

   public boolean isVerbose() {
      return this.verbose;
   }

   public void setVerbose(boolean isVerbose) {
      this.verbose = isVerbose;
   }

   public String toString() {
      return "CodeGenOptions{baseBeanFactories=" + (this.baseBeanFactories == null ? null : Arrays.asList(this.baseBeanFactories)) + ", sourceDir='" + this.sourceDir + '\'' + ", templateDir='" + this.templateDir + '\'' + ", sourcePath='" + this.sourcePath + '\'' + ", targetDirectory='" + this.targetDirectory + '\'' + ", outputPackage='" + this.outputPackage + '\'' + ", generateToCustom=" + this.generateToCustom + ", templateName='" + this.templateName + '\'' + ", sources=" + (this.sources == null ? null : (this.sources.length > 20 ? this.sources.length + " files" : Arrays.asList(this.sources))) + ", excludes=" + (this.excludes == null ? null : Arrays.asList(this.excludes)) + ", suffix='" + this.suffix + '\'' + ", targetExtension='" + this.targetExtension + '\'' + ", sourceExtension='" + this.sourceExtension + '\'' + ", externalDir='" + this.externalDir + '\'' + ", implementationFactories=" + (this.implementationFactories == null ? null : Arrays.asList(this.implementationFactories)) + ", beanFactory='" + this.beanFactory + '\'' + ", defaultTemplate='" + this.defaultTemplate + '\'' + ", classpath='" + this.classpath + '\'' + ", verbose='" + this.verbose + '\'' + '}';
   }
}
