package weblogic.ant.taskdefs.build;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.zip.ZipFile;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Jar;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;

public final class AnnotationManifestTask extends Task {
   private Path _classpath;
   private Path _searchPath;
   private String _moduleDir;
   private String _moduleName;
   private String _stagingDir = ".staging";
   private boolean _verbose = false;
   private String _version = "";
   private static final String MANIFEST_DIR = "META-INF";
   private static final String MANIFEST_FILENAME = "annotation-manifest.xml";
   private static final String MANIFESTMERGE_CLASS = "weblogic.controls.properties.ManifestMerger";
   private static final String GENERATEMANIFEST_CLASS = "weblogic.controls.properties.GenerateManifest";
   private static final String JAR_MANIFEST_TARGET_NAME = "weblogic.insert.manifest";

   public Path createClasspath() {
      if (this._classpath == null) {
         this._classpath = new Path(this.getProject());
      }

      return this._classpath.createPath();
   }

   public void setClasspath(Path classpath) {
      if (this._classpath == null) {
         this._classpath = classpath;
      } else {
         this._classpath.append(classpath);
      }

   }

   public void setClasspathRef(Reference ref) {
      this.createClasspath().setRefid(ref);
   }

   public Path createSearchClasspath() {
      if (this._searchPath == null) {
         this._searchPath = new Path(this.getProject());
      }

      return this._searchPath.createPath();
   }

   public void setSearchClasspath(Path classpath) {
      if (this._searchPath == null) {
         this._searchPath = classpath;
      } else {
         this._searchPath.append(classpath);
      }

   }

   public void setSearchClasspathRef(Reference ref) {
      this.createSearchClasspath().setRefid(ref);
   }

   public void setModuleDir(String moduleDir) {
      this._moduleDir = moduleDir;
      if (this._moduleDir != null && this._moduleDir.length() != 0) {
         this._moduleName = (new File(this._moduleDir)).getName();
      } else {
         throw new BuildException("Invalid moduleDir: " + this._moduleDir);
      }
   }

   public void setStagingDir(String dir) {
      if (dir != null && !dir.equals("")) {
         this._stagingDir = dir;
      }

   }

   public void setVerbose(String verbose) {
      if (verbose != null) {
         Boolean b = Boolean.valueOf(verbose);
         if (b != null) {
            this._verbose = b;
         }
      }

   }

   public void setVersion(String version) {
      if (version != null) {
         this._version = version;
      }

   }

   public void execute() throws BuildException {
      this.checkParameters();
      System.out.println("========================================================");
      System.out.println("| Start Building Annotation Manifest for " + this._moduleName);
      System.out.println("========================================================");
      long t1 = System.currentTimeMillis();
      GenManifestTarget t = new GenManifestTarget(this.getProject(), this._moduleDir, this._stagingDir, this._classpath, this._searchPath);
      t.setVerbose(this._verbose);
      t.setVersion(this._version);
      t.execute();
      long t2 = System.currentTimeMillis();
      System.out.println("========================================================");
      System.out.println("| Finished Building Annotation Manifest in " + (t2 - t1) + " millis");
      System.out.println("========================================================");
   }

   private void checkParameters() throws BuildException {
      if (this._classpath != null && this._classpath.size() != 0) {
         if (this._searchPath != null && this._searchPath.size() != 0) {
            if (this._stagingDir != null && this._stagingDir.length() != 0) {
               if (this._moduleDir != null && this._moduleDir.length() != 0) {
                  File moduleDir = new File(this._moduleDir);
                  if (!moduleDir.exists()) {
                     throw new BuildException("Module directory does not exist: " + moduleDir.getAbsolutePath());
                  }
               } else {
                  throw new BuildException("Must define module directory");
               }
            } else {
               throw new BuildException("Must define staging directory");
            }
         } else {
            throw new BuildException("Must define searchClasspath");
         }
      } else {
         throw new BuildException("Must define classpath");
      }
   }

   private static void handleGeneralBuildError(Throwable t) {
      Throwable cause = t;
      if (t instanceof InvocationTargetException) {
         cause = t.getCause();
      }

      throw new BuildException(cause);
   }

   private static class GenManifestTarget extends Target {
      private String _moduleDir;
      private String _stagingDir;
      private String[] _classpathEntries;
      private String[] _searchPathEntries;
      private boolean _verbose;
      private String _version;
      private static final String UNJAR_TASK_NAME = "unjar";
      private static final String JAR_TASK_NAME = "jar";
      private static final String TEMP_JAR_NAME = "temp.jar";

      protected GenManifestTarget(Project project, String moduleDir, String stagingDir, Path classpath, Path searchPath) {
         this.setName("weblogic.insert.manifest");
         this.setDescription("Generates Annotation Manifest for module");
         this.setProject(project);
         this._moduleDir = moduleDir;
         this._stagingDir = stagingDir;

         assert classpath != null && searchPath != null;

         this._classpathEntries = classpath.list();
         this._searchPathEntries = searchPath.list();
         this._verbose = false;
      }

      public void execute() throws BuildException {
         this.deleteStagedFiles();
         ClassLoader classLoader = this.getClassLoader(this._classpathEntries, this._searchPathEntries);

         for(int i = 0; i < this._searchPathEntries.length; ++i) {
            File file = new File(this._searchPathEntries[i]);
            if (!file.exists()) {
               if (this._verbose) {
                  System.out.println(file.getName() + " does not exist.");
               }
            } else if (file.isDirectory()) {
               String manifest = file.getAbsolutePath() + File.separator + "META-INF" + File.separator + "annotation-manifest.xml";
               if (this._verbose) {
                  System.out.println("Generating manifest for class directory " + file.getAbsolutePath());
               }

               this.invokeGenerateManifest(manifest, file.getAbsolutePath(), classLoader);
            } else if (file.isFile()) {
               ZipFile zipFile = null;

               try {
                  zipFile = new ZipFile(file);
               } catch (Exception var6) {
                  System.out.println(file.getName() + " is not a valid jar file");
                  if (this._verbose) {
                     var6.printStackTrace();
                  }
               }

               if (zipFile != null) {
                  this.fixJar(file, classLoader);
               }
            }
         }

         this.invokeMergeManifest(this._searchPathEntries, this._moduleDir, classLoader);
         this.deleteStagedFiles();
      }

      public void setVerbose(boolean b) {
         this._verbose = b;
      }

      public void setVersion(String version) {
         this._version = version;
      }

      private void deleteStagedFiles() {
         try {
            File stagingDir = new File(this._stagingDir);
            if (stagingDir.exists()) {
               FileSet fileset = new FileSet();
               Delete delete = new Delete();
               delete.setTaskName("delete");
               delete.setProject(this.getProject());
               delete.setIncludeEmptyDirs(true);
               fileset.setDir(stagingDir);
               fileset.setIncludes("**/**");
               delete.addFileset(fileset);
               delete.execute();
            }
         } catch (Exception var4) {
            System.out.println("Unable to delete files from " + this._stagingDir);
         }

      }

      private void fixJar(File jarFile, ClassLoader classLoader) {
         if (this._verbose) {
            System.out.println("Generating manifest for " + jarFile.getName());
         }

         String staging = this._stagingDir + File.separator + jarFile.getName();
         File stagingDir = new File(staging);
         File manifestDir = new File(staging + File.separator + "META-INF");
         if (!manifestDir.exists()) {
            manifestDir.mkdirs();
         }

         Expand unjar = new Expand();
         unjar.setTaskName("unjar");
         unjar.setProject(this.getProject());
         unjar.setDest(stagingDir);
         unjar.setSrc(jarFile);
         unjar.setOverwrite(true);
         unjar.execute();
         String manifestFile = staging + File.separator + "META-INF" + File.separator + "annotation-manifest.xml";
         this.invokeGenerateManifest(manifestFile, stagingDir.getAbsolutePath(), classLoader);
         File generated = new File(manifestFile);
         if (!generated.exists()) {
            if (this._verbose) {
               System.out.println(jarFile.getName() + " does not have configurable annotations.");
            }

         } else {
            Jar jar = new Jar();
            jar.setTaskName("jar");
            jar.setProject(this.getProject());
            File tempJar = new File(staging + File.separator + "temp.jar");
            if (tempJar.exists()) {
               tempJar.delete();
            }

            jar.setDestFile(tempJar);
            jar.setBasedir(stagingDir);
            jar.execute();
            Copy copy = new Copy();
            copy.setTaskName(jarFile.getName());
            copy.setProject(this.getProject());
            copy.setFile(tempJar);
            copy.setTofile(jarFile);
            copy.setOverwrite(true);
            copy.execute();
            if (this._verbose) {
               System.out.println("Inserted manifest for " + jarFile.getName());
            }

         }
      }

      private void invokeGenerateManifest(String manifestFile, String classDir, ClassLoader classLoader) throws BuildException {
         try {
            Class genManifest = classLoader.loadClass("weblogic.controls.properties.GenerateManifest");
            Class[] signature = new Class[]{String[].class};
            Method main = genManifest.getDeclaredMethod("doGenerate", signature);
            Object[] args = new Object[]{new String[]{classDir, manifestFile, Boolean.toString(this._verbose), this._version}};
            main.invoke((Object)null, args);
         } catch (ClassNotFoundException var8) {
            throw new BuildException("weblogic.controls.properties.GenerateManifest is not on the classpath");
         } catch (Exception var9) {
            AnnotationManifestTask.handleGeneralBuildError(var9);
         }

      }

      private void invokeMergeManifest(String[] moduleClasspath, String modulePath, ClassLoader classLoader) throws BuildException {
         try {
            Class mergeManifest = classLoader.loadClass("weblogic.controls.properties.ManifestMerger");
            Class[] signature = new Class[]{String.class, String[].class, Boolean.class};
            Method mergeAll = mergeManifest.getDeclaredMethod("mergeAll", signature);
            Object[] args = new Object[]{modulePath, moduleClasspath, this._verbose};
            mergeAll.invoke((Object)null, args);
         } catch (ClassNotFoundException var8) {
            throw new BuildException("weblogic.controls.properties.ManifestMerger is not on the classpath");
         } catch (Exception var9) {
            AnnotationManifestTask.handleGeneralBuildError(var9);
         }

      }

      private ClassLoader getClassLoader(String[] runtimeClasspath, String[] moduleClasspath) {
         assert runtimeClasspath != null && moduleClasspath != null;

         URL[] urls = new URL[runtimeClasspath.length + 1 + moduleClasspath.length];
         int index = 0;

         int i;
         URL url;
         for(i = 0; i < runtimeClasspath.length; ++i) {
            url = this.getURL(runtimeClasspath[i]);
            if (url != null) {
               urls[index++] = url;
            }
         }

         for(i = 0; i < moduleClasspath.length; ++i) {
            url = this.getURL(moduleClasspath[i]);
            if (url != null) {
               urls[index++] = url;
            }
         }

         urls[index++] = this.getURL(this._stagingDir);
         return new URLClassLoader(urls, System.class.getClassLoader());
      }

      private URL getURL(String path) {
         URL result = null;

         try {
            result = (new File(path)).toURL();
         } catch (Exception var4) {
            if (this._verbose) {
               var4.printStackTrace();
            }
         }

         return result;
      }
   }
}
