package weblogic.descriptor.codegen;

import com.bea.util.jam.JClass;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Properties;
import weblogic.apache.org.apache.velocity.Template;
import weblogic.apache.org.apache.velocity.app.VelocityEngine;
import weblogic.apache.org.apache.velocity.context.Context;
import weblogic.apache.org.apache.velocity.exception.MethodInvocationException;
import weblogic.apache.org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import weblogic.apache.org.apache.velocity.runtime.resource.loader.FileResourceLoader;

public class CodeGenerator {
   protected CodeGenOptions opts;

   protected CodeGenerator() {
   }

   protected CodeGenerator(CodeGenOptions opts) {
      this.opts = opts;
   }

   public CodeGenOptions getOpts() {
      return this.opts;
   }

   public AbstractCollection getContextCollection() throws IOException {
      return new ContextCollection(this);
   }

   public ClassLoader getClassLoader() {
      return this.getClass().getClassLoader();
   }

   public void generate() throws Exception {
      if (this.opts.getTemplateName() == null) {
         throw new IllegalArgumentException("No template file provided");
      } else {
         String beanFactoryClassName = this.opts.getBeanFactory();
         BeanFactory beanFactory = null;
         if (beanFactoryClassName != null) {
            beanFactory = new BeanFactory(beanFactoryClassName);
         }

         AbstractCollection ctxs = this.getContextCollection();
         VelocityEngine ve = new VelocityEngine();
         Properties p = new Properties();
         p.setProperty("resource.loader", "file, class");
         p.setProperty("file.resource.loader.class", FileResourceLoader.class.getName());
         p.setProperty("file.resource.loader.path", this.opts.getTemplateDir());
         p.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
         p.setProperty("runtime.introspector.uberspect", "weblogic.apache.org.apache.velocity.util.introspection.UberspectImpl");
         ve.init(p);
         Template template = ve.getTemplate(this.opts.getTemplateName());
         Iterator iterator = ctxs.iterator();

         while(true) {
            Context ctx;
            do {
               if (!iterator.hasNext()) {
                  if (beanFactory != null) {
                     if (ctxs instanceof ContextCollection) {
                        ContextCollection contextCollection = (ContextCollection)ctxs;
                        beanFactory.setTimeStamp(contextCollection.getLastModified());
                     }

                     beanFactory.generate(this.getBeanFactoryFile(this.opts.getBeanFactory()), this.getExternalTimeStampFile(this.opts.getBeanFactory()));
                  }

                  if (this.opts.isVerbose()) {
                     System.out.println("done generating code");
                  }

                  return;
               }

               ctx = (Context)iterator.next();
            } while(ctx == null);

            AnnotatableClass source = (AnnotatableClass)ctx.get("source");
            String qualifiedName = null;
            if (this.opts.isGenerateToCustom()) {
               Annotation customizer = source.getAnnotation("customizer");
               if (customizer != null) {
                  qualifiedName = customizer.getStringValue();
               }
            }

            if (qualifiedName == null) {
               qualifiedName = source.getQualifiedName();
            }

            String resourceBundle;
            if (this.opts.getPackage() != null) {
               ctx.put("package", this.opts.getPackage());
            } else {
               int lastIndex = qualifiedName.lastIndexOf(46);
               if (lastIndex < 0) {
                  CodeGenerationException exception = new CodeGenerationException("Missing package name in source for " + qualifiedName);
                  throw exception;
               }

               resourceBundle = qualifiedName.substring(0, lastIndex);
               ctx.put("package", resourceBundle);
            }

            File target = this.getTargetFileFromInterfaceName(qualifiedName);
            if (beanFactory != null) {
               BasicCodeGeneratorContext bctx = (BasicCodeGeneratorContext)ctx;
               beanFactory.putImplementation(bctx.jClass.getQualifiedName(), this.getTargetClass(qualifiedName));
               beanFactory.putRoleInfo(bctx.production.getAnnotatableSource());
            }

            resourceBundle = null;
            File resourceBundleTargetDirectory = this.getResourceBundleTargetDirectoryFromInterfaceName(qualifiedName);
            if (resourceBundleTargetDirectory != null) {
               Writer resourceBundle = new PrintWriter(new BufferedWriter(new FileWriter(resourceBundleTargetDirectory + File.separator + "MBeanDescriptions.properties", true)));
               ctx.put("bundle", resourceBundle);
               ctx.put("utils", new Utils());
            }

            OutputStreamWriter o = new FileWriter(target);

            try {
               if (this.opts.isVerbose()) {
                  System.out.println("Generating for " + qualifiedName + " to " + target.getAbsolutePath());
               }

               template.merge(ctx, o);
            } catch (Exception var17) {
               if (var17 instanceof MethodInvocationException) {
                  MethodInvocationException mie = (MethodInvocationException)var17;
                  mie.getWrappedThrowable().printStackTrace();
               }

               target.delete();
               throw var17;
            }

            o.flush();
            o.close();
         }
      }
   }

   private String getTargetClass(String qualifiedName) {
      String pkgName = this.opts.getPackage();
      int classNameIndex = qualifiedName.lastIndexOf(46);
      if (pkgName == null) {
         pkgName = qualifiedName.substring(0, classNameIndex);
      }

      String className = qualifiedName.substring(classNameIndex + 1) + this.opts.getSuffix();
      return pkgName + "." + className;
   }

   private File getBeanFactoryFile(String qualifiedName) {
      String factoryFileName = this.opts.getTargetDirectory() + File.separatorChar + qualifiedName.replace('.', File.separatorChar) + ".java";
      if (this.opts.isVerbose()) {
         System.out.println("Generating Bean Factory Class to " + factoryFileName);
      }

      return new File(factoryFileName);
   }

   private File getExternalTimeStampFile(String qualifiedName) {
      if (this.opts.getExternalDir() == null) {
         return null;
      } else {
         String externalTimeStampFileName = this.opts.getExternalDir() + File.separatorChar + qualifiedName.replace('.', File.separatorChar) + ".tstamp";
         if (this.opts.isVerbose()) {
            System.out.println("Generating Bean Factory External Timestamp to " + externalTimeStampFileName);
         }

         return new File(externalTimeStampFileName);
      }
   }

   private File getTargetFileFromInterfaceName(String qualifiedName) {
      File rootDir = new File(this.opts.getTargetDirectory());
      String pkgName = this.opts.getPackage();
      if (pkgName == null) {
         pkgName = qualifiedName.substring(0, qualifiedName.lastIndexOf(46));
      }

      File pkgDir = new File(rootDir, pkgName.replace('.', File.separatorChar));
      String fileName = qualifiedName.substring(qualifiedName.lastIndexOf(46) + 1) + this.opts.getSuffix() + this.opts.getTargetExtension();
      File target = new File(pkgDir, fileName);
      String path = target.getParent();
      if (path != null) {
         File dir = new File(path);
         if (!dir.exists()) {
            dir.mkdirs();
         }
      }

      return target;
   }

   private File getResourceBundleTargetDirectoryFromInterfaceName(String qualifiedName) {
      String targetDirectory = this.opts.getResourceBundleTargetDirectory();
      if (targetDirectory == null) {
         return null;
      } else {
         File rootDir = new File(targetDirectory);
         String pkgName = this.opts.getPackage();
         if (pkgName == null) {
            pkgName = qualifiedName.substring(0, qualifiedName.lastIndexOf(46));
         }

         String packageSuffix = this.opts.getPackageSuffix();
         if (this.isNotEmpty(packageSuffix)) {
            pkgName = pkgName + "." + packageSuffix;
         }

         File bundleDir = new File(rootDir, pkgName.replace('.', File.separatorChar));
         if (!bundleDir.exists()) {
            bundleDir.mkdirs();
         }

         return bundleDir;
      }
   }

   public BasicCodeGeneratorContext getCodeGeneratorContext(JClass jClass) {
      return new BasicCodeGeneratorContext(jClass, this);
   }

   public Production getProduction(JClass jClass) {
      return new Production(jClass, this);
   }

   private boolean isNotEmpty(String string) {
      return string != null && string.trim().length() > 0;
   }
}
