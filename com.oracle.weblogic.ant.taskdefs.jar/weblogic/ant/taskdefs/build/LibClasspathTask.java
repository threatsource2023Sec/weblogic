package weblogic.ant.taskdefs.build;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import weblogic.ant.taskdefs.utils.AntLibraryUtils;
import weblogic.ant.taskdefs.utils.LibraryElement;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.library.LibraryInitializer;
import weblogic.application.library.LibraryManager;
import weblogic.application.library.LibraryProcessingException;
import weblogic.application.library.LibraryReference;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.application.utils.LibraryUtils;
import weblogic.ejb.spi.EJBLibraryFactory;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.servlet.internal.StaleProber;
import weblogic.servlet.internal.War;
import weblogic.servlet.internal.WarDefinition;
import weblogic.servlet.internal.WarLibraryFactory;
import weblogic.servlet.internal.WebAppDescriptor;
import weblogic.servlet.utils.WarUtils;
import weblogic.servlet.utils.WebAppLibraryUtils;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public final class LibClasspathTask extends Task {
   private File tmpdir = null;
   private boolean userSetTmpdir = false;
   private File basedir;
   private File basewar = null;
   private String property;
   private String classpathproperty;
   private String resourcepathproperty;
   private final Collection libdirs = new ArrayList();
   private final Collection libraries = new ArrayList();

   public void addConfiguredLibrary(LibraryElement library) {
      if (library.getFile() == null) {
         throw new BuildException("Location of Library must be set");
      } else {
         this.libraries.add(library);
      }
   }

   public void addConfiguredLibrarydir(LibraryElement library) {
      if (library.getDir() == null) {
         throw new BuildException("Library dir must be set");
      } else {
         this.libdirs.add(library.getDir());
      }
   }

   public void setProperty(String property) {
      this.property = property;
   }

   public void setClasspathProperty(String classpathproperty) {
      this.classpathproperty = classpathproperty;
   }

   public void setResourcepathProperty(String resourcepathproperty) {
      this.resourcepathproperty = resourcepathproperty;
   }

   public void setlibraryDir(File libdir) {
      this.libdirs.add(libdir);
   }

   public void settmpdir(File tmpdir) {
      this.tmpdir = tmpdir;
      this.userSetTmpdir = true;
   }

   public void setBasedir(File basedir) {
      this.basedir = basedir;
   }

   public void setBasewar(File basewar) {
      this.basewar = basewar;
   }

   private void checkProperty() {
      if (this.property != null && this.classpathproperty == null) {
         this.classpathproperty = this.property;
         this.log("The \"property\" attribute has been deprecated, please use the \"classpathproperty\" attribute instead.");
      }

      if (this.classpathproperty == null && this.resourcepathproperty == null) {
         throw new BuildException("The \"classpathproperty\" and/or \"resourcepathproperty\" attributes must be set.");
      }
   }

   private void checktmpdir() {
      if (!this.userSetTmpdir) {
         throw new BuildException("tmpdir must be set.");
      } else {
         if (this.tmpdir.exists()) {
            if (!this.tmpdir.isDirectory()) {
               throw new BuildException("tmpdir: " + this.tmpdir.getAbsolutePath() + " is not a directory.");
            }
         } else if (!this.tmpdir.mkdirs()) {
            throw new BuildException("tmpdir " + this.tmpdir.getAbsolutePath() + " does not exist, and we were unable to create it.");
         }

      }
   }

   private void checkbasedir() {
      if (this.basewar == null) {
         if (this.basedir == null) {
            this.basedir = new File(this.getProject().getProperty("basedir"));
            if (this.basedir == null) {
               this.basedir = new File(System.getProperty("user.dir"));
            }
         }

         if (!this.basedir.exists()) {
            throw new BuildException("basedir " + this.basedir.getAbsolutePath() + " does not exist.");
         }

         if (!this.basedir.isDirectory()) {
            throw new BuildException("basedir: " + this.basedir.getAbsolutePath() + " is not a directory.");
         }
      } else {
         ArrayList chklib = new ArrayList(1);
         LibraryElement le = new LibraryElement();
         le.setFile(this.basewar);
         chklib.add(le);
         AntLibraryUtils.validateLibraries((Collection)this.libdirs, chklib);
      }

   }

   private void checkParameters() {
      this.checkProperty();
      this.checktmpdir();
      this.checkbasedir();
      AntLibraryUtils.validateLibraries(this.libdirs, this.libraries);
   }

   public void execute() {
      this.checkParameters();

      try {
         this.initLibraries();
         LibraryResources resources = this.getLibraryResouces();
         if (resources != null) {
            if (resources.getClassPath() != null && this.classpathproperty != null) {
               this.getProject().setProperty(this.classpathproperty, resources.getClassPath());
            }

            if (resources.getResourcePath() != null && this.resourcepathproperty != null) {
               this.getProject().setProperty(this.resourcepathproperty, resources.getResourcePath());
            }

            return;
         }

         this.log("basedir does not point to an application or to a module that uses libraries");
      } finally {
         LibraryLoggingUtils.partialCleanupAndRemove();
         this.libdirs.clear();
         this.libraries.clear();
      }

   }

   public static ApplicationFactoryManager getDumbLibraryApplicationFactoryManager() {
      ApplicationFactoryManager afm = ApplicationFactoryManager.getEmptyApplicationFactoryManager();
      afm.addLibraryFactory(new WarLibraryFactory.Noop());
      afm.addLibraryFactory(new EJBLibraryFactory());
      return afm;
   }

   private void initLibraries() {
      LibraryInitializer libraryInitializer = new LibraryInitializer(this.tmpdir, getDumbLibraryApplicationFactoryManager());
      AntLibraryUtils.registerLibraries(libraryInitializer, (File[])((File[])this.libdirs.toArray(new File[this.libdirs.size()])), (LibraryElement[])((LibraryElement[])this.libraries.toArray(new LibraryElement[this.libraries.size()])), false);
      this.log("Registered libraries: ", 3);
      AntLibraryUtils.logRegistryContent(this.getProject(), 3);

      try {
         libraryInitializer.initRegisteredLibraries();
      } catch (LoggableLibraryProcessingException var3) {
         var3.printStackTrace();
         throw new BuildException(var3.getLoggable().getMessage(), var3);
      }
   }

   private LibraryResources getLibraryResouces() {
      if (this.basewar != null) {
         return this.getWebAppLibResources(this.basewar);
      } else if ((new File(this.basedir, "META-INF/weblogic-application.xml")).exists()) {
         return this.getAppLibResources(this.basedir);
      } else {
         return (new File(this.basedir, WarUtils.WEBLOGIC_XML)).exists() ? this.getWebAppLibResources(this.basedir) : null;
      }
   }

   private LibraryResources getAppLibResources(File srcDir) {
      BuildCtx ctx = new BuildCtx();

      LibraryResources var5;
      try {
         LibraryReference[] libraryRefs = LibraryUtils.initLibRefs(srcDir);
         LibraryManager mgr = new LibraryManager(LibraryUtils.initAppReferencer(), "DOMAIN", libraryRefs);
         this.initAppLibManager(mgr);
         LibraryUtils.importAppLibraries(mgr, ctx, ctx);
         var5 = new LibraryResources(AntLibraryUtils.getClassPath(ctx.getLibraryFiles(), ctx.getLibraryClassFinder()), (String)null);
      } catch (LoggableLibraryProcessingException var10) {
         throw new BuildException(var10.getLoggable().getMessage(), var10);
      } catch (LibraryProcessingException var11) {
         throw new BuildException(var11);
      } finally {
         ctx.getLibraryClassFinder().close();
      }

      return var5;
   }

   private LibraryResources getWebAppLibResources(File f) {
      VirtualJarFile vjf = null;
      War war = null;

      LibraryResources var8;
      try {
         vjf = VirtualJarFactory.createVirtualJar(f);
         WebAppDescriptor desc = new WebAppDescriptor(vjf);
         WeblogicWebAppBean wlBean = WarUtils.getWlWebAppBean(desc);
         LibraryManager mgr = WebAppLibraryUtils.getEmptyWebAppLibraryManager(f.getName());
         WebAppLibraryUtils.initWebAppLibraryManager(mgr, wlBean, f.getName());
         WarDefinition warDef = new WarDefinition();
         warDef.setUri("libclasspath");
         warDef.setVirtualJarFile(vjf);
         war = warDef.extract(this.tmpdir, (StaleProber)null);
         WebAppLibraryUtils.addWebAppLibraries(mgr, war);
         var8 = this.getWebAppLibResources(war);
      } catch (IOException var18) {
         throw new BuildException(var18);
      } catch (ToolFailureException var19) {
         throw new BuildException(var19);
      } finally {
         if (vjf != null) {
            try {
               vjf.close();
            } catch (IOException var17) {
            }
         }

         if (war != null) {
            war.getClassFinder().close();
         }

      }

      return var8;
   }

   private LibraryResources getWebAppLibResources(War war) {
      String classpath = war.getClassFinder().getClassPath();
      String resourcepath = null;
      List extensionRoots = war.getExtensionRoots();
      if (extensionRoots != null && extensionRoots.size() > 0) {
         StringBuffer sb = new StringBuffer();
         Iterator iter = extensionRoots.iterator();

         while(iter.hasNext()) {
            File f = (File)iter.next();
            sb.append(f.getAbsolutePath());
            if (iter.hasNext()) {
               sb.append(File.pathSeparator);
            }
         }

         resourcepath = sb.toString();
      }

      return new LibraryResources(classpath, resourcepath);
   }

   private void initAppLibManager(LibraryManager mgr) {
      try {
         LibraryLoggingUtils.verifyLibraryReferences(mgr);
         mgr.initializeReferencedLibraries();
      } catch (LoggableLibraryProcessingException var3) {
         throw new BuildException(var3.getLoggable().getMessage(), var3);
      }
   }

   private static final class LibraryResources {
      private final String classpath;
      private final String resourcepath;

      LibraryResources(String classpath, String resourcepath) {
         if (classpath != null && classpath.trim().length() > 0) {
            this.classpath = classpath;
         } else {
            this.classpath = null;
         }

         if (resourcepath != null && resourcepath.trim().length() > 0) {
            this.resourcepath = resourcepath;
         } else {
            this.resourcepath = null;
         }

      }

      String getClassPath() {
         return this.classpath;
      }

      String getResourcePath() {
         return this.resourcepath;
      }
   }
}
