package com.bea.staxb.buildtime.internal.mbean;

import com.bea.staxb.buildtime.BindingCompilerException;
import com.bea.staxb.buildtime.JavaBindTask;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.xml.sax.EntityResolver;

public class Both2BindTask extends JavaBindTask {
   private Both2BindProcessor processor = (Both2BindProcessor)super.getProcessor();
   private Path mSrc = null;
   private Path mBindingJars = null;
   private List mSchemaFilesets = new ArrayList();
   private File mSchema = null;
   private FileSet mBaseLibs = null;
   Project proj = null;

   public Both2BindTask() {
      super(new Both2BindProcessor());
   }

   public Path createSrc() {
      if (this.mSrc == null) {
         this.mSrc = new Path(this.getProject());
      }

      return this.mSrc.createPath();
   }

   public void setSrcdir(Path srcDir) {
      if (this.mSrc == null) {
         this.mSrc = srcDir;
      } else {
         this.mSrc.append(srcDir);
      }

   }

   public void setBindingJars(Path jars) {
      if (this.mBindingJars == null) {
         this.mBindingJars = jars;
      } else {
         this.mBindingJars.append(jars);
      }

   }

   public Path createBindingJars() {
      if (this.mBindingJars == null) {
         this.mBindingJars = new Path(this.getProject());
      }

      return this.mBindingJars.createPath();
   }

   public void setSchema(File file) {
      this.mSchema = file;
   }

   public void addSchema(FileSet fileSet) {
      this.mSchemaFilesets.add(fileSet);
   }

   public void setBaseURI(String paramBaseURI) {
      this.processor.setBaseURI(paramBaseURI);
   }

   public void setGeneratedDir(File generatedDir) {
      this.processor.setGeneratedDir(generatedDir);
   }

   public void setGenerateDefaultXmlBeans(boolean generateDefaultXmlBeans) {
      this.processor.setGenerateDefaultXmlBeans(generateDefaultXmlBeans);
   }

   public FileSet createBaseLibrary() {
      return this.mBaseLibs = new FileSet();
   }

   public void setTypeMatcher(String typeMatcherClassName) {
      if (typeMatcherClassName != null) {
         try {
            Class mclass = Class.forName(typeMatcherClassName);
            Object matcher = mclass.newInstance();
            if (!(matcher instanceof TypeMatcher)) {
               throw new BindingCompilerException(typeMatcherClassName + " does not implement " + TypeMatcher.class.getName());
            }

            this.processor.setTypeMatcher((TypeMatcher)matcher);
            this.log("both2Bind using matcher class " + typeMatcherClassName, 4);
         } catch (ClassNotFoundException var4) {
            throw new BuildException(var4);
         } catch (InstantiationException var5) {
            throw new BuildException(var5);
         } catch (IllegalAccessException var6) {
            throw new BuildException(var6);
         }
      }

   }

   public void setEntityResolver(String entityResolverClassName) {
      if (entityResolverClassName != null) {
         try {
            Class mclass = Class.forName(entityResolverClassName);
            Object o = mclass.newInstance();
            if (!(o instanceof EntityResolver)) {
               throw new BuildException(entityResolverClassName + " does not implement " + EntityResolver.class.getName());
            }

            this.processor.setEntityResolver((EntityResolver)o);
            this.log("both2Bind using entity resolver " + entityResolverClassName);
         } catch (ClassNotFoundException var4) {
            throw new BuildException(var4);
         } catch (InstantiationException var5) {
            throw new BuildException(var5);
         } catch (IllegalAccessException var6) {
            throw new BuildException(var6);
         }
      }

   }

   protected void populateProcessor() {
      this.processor.setJavaFiles(this.getJavaFiles());
      this.processor.setXsdFiles(this.getSchemaFiles());
      this.processor.setBaseLibraries(this.getBaseLibraries());
      this.processor.setBindingJarFiles(this.getBindingJarFiles());
      super.populateProcessor();
   }

   private File[] getBaseLibraries() {
      ArrayList libraries = new ArrayList();
      if (this.mBaseLibs != null) {
         String[] var2 = this.mBaseLibs.getDirectoryScanner(this.getProject()).getIncludedFiles();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String fileName = var2[var4];
            libraries.add(new File(this.mBaseLibs.getDir(this.getProject()), fileName));
         }
      }

      return (File[])libraries.toArray(new File[libraries.size()]);
   }

   private File[] getSchemaFiles() {
      ArrayList xsdFiles = new ArrayList();
      if (this.mSchema != null) {
         if (!this.mSchema.exists()) {
            throw new BuildException("schema " + this.mSchema + " does not exist!");
         }

         xsdFiles.add(this.mSchema);
      }

      Iterator var2 = this.mSchemaFilesets.iterator();

      while(var2.hasNext()) {
         FileSet fs = (FileSet)var2.next();
         File fromDir = fs.getDir(this.getProject());
         DirectoryScanner ds = fs.getDirectoryScanner(this.getProject());
         String[] srcFiles = ds.getIncludedFiles();
         String[] var7 = srcFiles;
         int var8 = srcFiles.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String srcFile = var7[var9];
            if (srcFile.endsWith(".xsd")) {
               xsdFiles.add(new File(fromDir, srcFile));
            }
         }
      }

      return (File[])xsdFiles.toArray(new File[xsdFiles.size()]);
   }

   private File[] getJavaFiles() {
      if (this.mSrc != null && this.mSrc.size() != 0) {
         ArrayList javaFiles = new ArrayList();
         String[] var2 = this.mSrc.list();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String aList = var2[var4];
            File srcDir = this.getProject().resolveFile(aList);
            if (!srcDir.exists()) {
               throw new BuildException("srcdir \"" + srcDir.getPath() + "\" does not exist!", this.getLocation());
            }

            DirectoryScanner ds = this.getDirectoryScanner(srcDir);
            String[] files = ds.getIncludedFiles();
            String[] var9 = files;
            int var10 = files.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               String file = var9[var11];
               if (file.endsWith(".java")) {
                  javaFiles.add(new File(srcDir, file));
               }
            }
         }

         return (File[])javaFiles.toArray(new File[javaFiles.size()]);
      } else {
         throw new BuildException("srcdir attribute must be set!", this.getLocation());
      }
   }

   private File[] getBindingJarFiles() {
      if (this.mBindingJars == null) {
         return new File[0];
      } else {
         ArrayList jarFiles = new ArrayList();
         String[] var2 = this.mBindingJars.list();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String fileName = var2[var4];
            jarFiles.add(this.getProject().resolveFile(fileName));
         }

         return (File[])jarFiles.toArray(new File[jarFiles.size()]);
      }
   }

   public static void main(String[] args) throws Exception {
      Both2BindTask task = new Both2BindTask();
      task.parseArgs(args);
      task.execute();
   }

   private void parseArgs(String[] args) throws Exception {
      for(int i = 0; i < args.length; ++i) {
         String generateDefaultXmlBeans;
         if (args[i].equals("-b")) {
            ++i;
            generateDefaultXmlBeans = args[i];
            this.setBaseURI(generateDefaultXmlBeans);
         } else {
            Path p1;
            if (args[i].equals("-c")) {
               ++i;
               generateDefaultXmlBeans = args[i];
               p1 = new Path(new Project(), generateDefaultXmlBeans);
               this.setClasspath(p1);
            } else if (args[i].equals("-d")) {
               ++i;
               generateDefaultXmlBeans = args[i];
               this.setDestDir(new File(generateDefaultXmlBeans));
            } else if (args[i].equals("-g")) {
               ++i;
               generateDefaultXmlBeans = args[i];
               this.setGeneratedDir(new File(generateDefaultXmlBeans));
            } else if (args[i].equals("-j")) {
               ++i;
               generateDefaultXmlBeans = args[i];
               p1 = new Path(new Project(), generateDefaultXmlBeans);
               this.setBindingJars(p1);
            } else if (args[i].equals("-m")) {
               ++i;
               generateDefaultXmlBeans = args[i];
               this.setTypeMatcher(generateDefaultXmlBeans);
            } else if (args[i].equals("-p")) {
               ++i;
               generateDefaultXmlBeans = args[i];
               p1 = new Path(new Project(), generateDefaultXmlBeans);
               this.setSourcepath(p1);
            } else if (args[i].equals("-s")) {
               ++i;
               generateDefaultXmlBeans = args[i];
               p1 = new Path(new Project(), generateDefaultXmlBeans);
               this.setSrcdir(p1);
            } else if (args[i].equals("-S")) {
               ++i;
               generateDefaultXmlBeans = args[i];
               FileSet fs = new FileSet();
               fs.setDir(new File(generateDefaultXmlBeans));
               ++i;
               String includes = args[i];
               fs.setIncludes(includes);
               this.addSchema(fs);
            } else if (args[i].equals("-t")) {
               ++i;
               generateDefaultXmlBeans = args[i];
               this.setTypeSystemName(generateDefaultXmlBeans);
            } else if (args[i].equals("-v")) {
               ++i;
               generateDefaultXmlBeans = args[i];
               if (generateDefaultXmlBeans.equalsIgnoreCase("false")) {
                  this.setVerbose(false);
               } else {
                  this.setVerbose(true);
               }
            } else if (args[i].equals("-x")) {
               ++i;
               generateDefaultXmlBeans = args[i];
               if (generateDefaultXmlBeans.equalsIgnoreCase("false")) {
                  this.setGenerateDefaultXmlBeans(false);
               } else {
                  this.setGenerateDefaultXmlBeans(true);
               }
            }
         }
      }

   }

   public Project getProject() {
      if (this.proj == null) {
         this.proj = new Project();
      }

      return this.proj;
   }
}
