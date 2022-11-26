package weblogic.ant.taskdefs.utils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;
import weblogic.descriptor.beangen.BeanGenOptions;
import weblogic.descriptor.beangen.BeanGenOptionsBean;
import weblogic.descriptor.beangen.BeanGenerationException;
import weblogic.descriptor.beangen.BeanGenerator;

public class BeanGenTask extends MatchingTask implements BeanGenOptionsBean {
   private BeanGenerator generator;
   private BeanGenOptions options;
   private Path mClasspath = null;
   private boolean validatorMode = false;
   private static final String PROPERTY_UNTESTED_BEANS = "UntestedBeans";
   private static final String PROPERTY_UNTESTED_BEANS_SEP = ";";

   public void setsrcdir(String source) {
      this.options.setSourceDir(source);
   }

   public void setSourceDir(String sourceCodeDirectory) {
      this.options.setSourceDir(sourceCodeDirectory);
   }

   public void setTemplateDir(String templateDirectory) {
      this.options.setTemplateDir(templateDirectory);
   }

   public void setSourcePath(String source) {
      this.options.setSourcePath(source);
   }

   public void setTargetDirectory(String targetDirectory) {
      this.options.setTargetDirectory(targetDirectory);
   }

   public void setGenerateToCustom(boolean generateToCustom) {
      this.options.setGenerateToCustom(generateToCustom);
   }

   public void setBeanFactory(String beanFactory) {
      this.options.setBeanFactory(beanFactory);
   }

   public void setTemplateName(String templateName) {
      this.options.setTemplateName(templateName);
   }

   public void setSuffix(String suffix) {
      this.options.setSuffix(suffix);
   }

   public void setTargetExtension(String targetExtension) {
      this.options.setTargetExtension(targetExtension);
   }

   public void setSourceExtension(String sourceExtension) {
      this.options.setSourceExtension(sourceExtension);
   }

   public void setBaseClassName(String baseClassName) {
      this.options.setBaseClassName(baseClassName);
   }

   public void setBaseInterfaceName(String baseInterfaceName) {
      this.options.setBaseInterfaceName(baseInterfaceName);
   }

   public void setPackage(String packageName) {
      this.options.setPackage(packageName);
   }

   public void setNoLocalValidation(boolean flag) {
      this.options.setNoLocalValidation(flag);
   }

   public void setNoSynthetics(boolean flag) {
      this.options.setNoSynthetics(flag);
   }

   public void setVerbose(boolean flag) {
      this.options.setVerbose(flag);
   }

   public void setTemplateExtension(String template) {
      this.options.setTemplateExtension(template);
   }

   public void setTargetNamespace(String namespace) {
      this.options.setTargetNamespace(namespace);
   }

   public void setSchemaLocation(String location) {
      this.options.setSchemaLocation(location);
   }

   public void setClasspath(Path path) {
      if (this.mClasspath == null) {
         this.mClasspath = path;
      } else {
         this.mClasspath.append(path);
      }

   }

   public void setClasspathRef(Reference r) {
      this.createClasspath().setRefid(r);
   }

   public Path createClasspath() {
      if (this.mClasspath == null) {
         this.mClasspath = new Path(this.getProject());
      }

      return this.mClasspath.createPath();
   }

   public void init() {
      this.options = new BeanGenOptions();
   }

   public void setValidatorMode(boolean mode) {
      this.validatorMode = mode;
   }

   public void execute() throws BuildException {
      if (!this.validatorMode) {
         try {
            if (this.mClasspath != null) {
               this.options.setClasspath(this.mClasspath.toString());
            }

            String sourceDirOption = this.options.getSourceDir();
            if (sourceDirOption == null) {
               throw new NullPointerException("sourceDirOption=null is illegal");
            }

            File baseDir;
            if (sourceDirOption.startsWith(".")) {
               baseDir = this.getProject().getBaseDir();
               if (sourceDirOption.length() > 1) {
                  sourceDirOption = baseDir.getAbsolutePath() + sourceDirOption.substring(1);
               } else {
                  sourceDirOption = baseDir.getAbsolutePath();
               }

               this.options.setSourceDir(sourceDirOption);
            }

            baseDir = new File(sourceDirOption);
            DirectoryScanner ds = this.getDirectoryScanner(baseDir);
            ds.scan();
            String[] sources = ds.getIncludedFiles();
            if (sources.length == 0) {
               throw new BuildException("Bean generation failure: No source files matched");
            }

            this.options.setSources(sources);
            if (ds.getExcludedFiles() != null) {
               this.options.setExcludes(ds.getExcludedFiles());
            }

            this.generator = new BeanGenerator(this.options);
            this.generator.generate();
            Iterator untestedBeans = this.generator.untestedBeans();
            if (untestedBeans.hasNext()) {
               this.setPropertyForValidation(untestedBeans);
            }

            this.options = new BeanGenOptions();
            this.generator = null;
         } catch (Exception var6) {
            var6.printStackTrace();
            throw new BuildException("Bean generation failure", var6);
         }
      } else {
         this.validateUntestedBeans();
      }

   }

   private void setPropertyForValidation(Iterator classNames) {
      String existingValue = this.getProject().getProperty("UntestedBeans");
      StringBuffer untestedBeans = new StringBuffer();
      if (existingValue != null) {
         untestedBeans.append(existingValue);
      }

      while(classNames.hasNext()) {
         untestedBeans.append((String)classNames.next());
         untestedBeans.append(";");
      }

      if (untestedBeans.length() > 0) {
         this.getProject().setProperty("UntestedBeans", untestedBeans.toString());
      }

   }

   private void validateUntestedBeans() {
      String sClassNames = this.getProject().getProperty("UntestedBeans");
      if (sClassNames != null && sClassNames.length() > 0) {
         String[] aClassNames = sClassNames.split(";");

         for(int count = 0; count < aClassNames.length; ++count) {
            System.out.println("Validating generated bean " + aClassNames[count]);
            this.testGeneratedBean(aClassNames[count]);
         }

         this.getProject().setProperty("UntestedBeans", "");
      }

   }

   private void testGeneratedBean(String className) {
      try {
         Class beanClass = Class.forName(className);

         try {
            Method validateMethod = beanClass.getMethod("validateGeneration");

            try {
               validateMethod.invoke(beanClass);
            } catch (IllegalAccessException var5) {
               var5.printStackTrace();
            } catch (InvocationTargetException var6) {
               throw new BeanGenerationException("Unable to validate generated bean " + className + " :Message: " + var6.getTargetException().getMessage(), var6.getTargetException());
            }
         } catch (NoSuchMethodException var7) {
            var7.printStackTrace();
         }
      } catch (ClassNotFoundException var8) {
         var8.printStackTrace();
      }

   }
}
