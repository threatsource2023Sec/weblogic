package org.apache.openjpa.lib.ant;

import java.io.File;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.ConfigurationImpl;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.ProductDerivations;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.tools.ant.AntClassLoader;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Path;

public abstract class AbstractTask extends MatchingTask {
   private static final Localizer _loc = Localizer.forPackage(AbstractTask.class);
   protected final List fileSets = new ArrayList();
   protected boolean haltOnError = true;
   protected Path classpath = null;
   protected boolean useParent = false;
   protected boolean isolate = false;
   private ConfigurationImpl _conf = null;
   private AntClassLoader _cl = null;

   public void setHaltOnError(boolean haltOnError) {
      this.haltOnError = haltOnError;
   }

   public void setIsolate(boolean isolate) {
      this.isolate = isolate;
   }

   public void setUseParentClassloader(boolean useParent) {
      this.useParent = useParent;
   }

   public Configuration getConfiguration() {
      if (this._conf == null) {
         this._conf = this.newConfiguration();
      }

      return this._conf;
   }

   protected abstract ConfigurationImpl newConfiguration();

   protected abstract void executeOn(String[] var1) throws Exception;

   protected ClassLoader getClassLoader() {
      if (this._cl != null) {
         return this._cl;
      } else {
         if (this.classpath != null) {
            this._cl = new AntClassLoader(this.project, this.classpath, this.useParent);
         } else {
            this._cl = new AntClassLoader(this.project.getCoreLoader(), this.project, new Path(this.project), this.useParent);
         }

         this._cl.setIsolated(this.isolate);
         return this._cl;
      }
   }

   protected void assertFiles(String[] files) {
      if (files.length == 0) {
         throw new BuildException(_loc.get("no-filesets").getMessage());
      }
   }

   public void setClasspath(Path classPath) {
      this.createClasspath().append(classPath);
   }

   public Path createClasspath() {
      if (this.classpath == null) {
         this.classpath = new Path(this.project);
      }

      return this.classpath.createPath();
   }

   public Object createConfig() {
      return this.getConfiguration();
   }

   public void addFileset(FileSet set) {
      this.fileSets.add(set);
   }

   public void execute() throws BuildException {
      if (this._conf == null) {
         this._conf = this.newConfiguration();
      }

      if (this._conf.getPropertiesResource() == null) {
         ConfigurationProvider cp = ProductDerivations.loadDefaults((ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(this._conf.getClass())));
         if (cp != null) {
            cp.setInto(this._conf);
         }
      }

      String[] files = this.getFiles();

      try {
         this.executeOn(files);
      } catch (Throwable var6) {
         var6.printStackTrace();
         if (this.haltOnError) {
            throw new BuildException(var6);
         }
      } finally {
         this._conf.close();
         this._conf = null;
      }

   }

   private String[] getFiles() {
      List files = new ArrayList();
      Iterator i = this.fileSets.iterator();

      while(i.hasNext()) {
         FileSet fs = (FileSet)i.next();
         DirectoryScanner ds = fs.getDirectoryScanner(this.project);
         String[] dsFiles = ds.getIncludedFiles();

         for(int j = 0; j < dsFiles.length; ++j) {
            File f = new File(dsFiles[j]);
            if (!(Boolean)AccessController.doPrivileged(J2DoPrivHelper.isFileAction(f))) {
               f = new File(ds.getBasedir(), dsFiles[j]);
            }

            files.add((String)AccessController.doPrivileged(J2DoPrivHelper.getAbsolutePathAction(f)));
         }
      }

      return (String[])((String[])files.toArray(new String[files.size()]));
   }
}
