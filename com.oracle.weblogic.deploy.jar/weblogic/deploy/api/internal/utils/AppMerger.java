package weblogic.deploy.api.internal.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import weblogic.application.Type;
import weblogic.application.compiler.AppMerge;
import weblogic.application.compiler.DeploymentViewOptions;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryDefinition;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.deploy.api.model.WebLogicDeployableObjectFactory;
import weblogic.deploy.api.spi.config.DescriptorSupport;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.LibraryRefBean;

public abstract class AppMerger {
   private static final boolean debug = Debug.isDebug("config");
   private File app;
   private File plan;
   private File plandir;
   private LibrarySpec[] libs;
   private boolean basicView = false;
   private AppMerge appMerge = null;
   private String lightWeightAppName = null;
   private boolean beanScaffoldingEnabled = false;

   public abstract DescriptorSupport getDescriptorSupport();

   public WebLogicDeployableObject getMergedApp(File app, File plan, File plandir, LibrarySpec[] libraries, String lwAppName, WebLogicDeployableObjectFactory wdoFactory) throws IOException {
      return this.getMergedApp(app, plan, plandir, libraries, wdoFactory, false, lwAppName, false);
   }

   public WebLogicDeployableObject getMergedApp(File app, File plan, File plandir, LibrarySpec[] libraries, WebLogicDeployableObjectFactory wdoFactory, boolean basicView, String lwAppName, boolean beanScaffoldingEnabled) throws IOException {
      this.app = app;
      this.plan = plan;
      this.plandir = plandir;
      this.libs = libraries;
      this.basicView = basicView;
      this.lightWeightAppName = lwAppName;
      this.beanScaffoldingEnabled = beanScaffoldingEnabled;

      try {
         return this.merge(wdoFactory);
      } catch (Exception var11) {
         if (debug && var11.getCause() != null) {
            Debug.say(var11.getCause().getMessage());
         }

         IOException ioe = new IOException(var11.getMessage());
         ioe.initCause(var11);
         throw ioe;
      }
   }

   private WebLogicDeployableObject merge(WebLogicDeployableObjectFactory wdoFactory) throws IOException, Exception {
      String[] cmd = this.createMergeCommand();
      if (debug) {
         this.dumpAppMergeArgs(cmd);
      }

      DeploymentViewOptions opts = AppMerge.createDeploymentViewOptions(wdoFactory);
      if (this.libs == null) {
         opts.disableLibraryMerge();
      }

      opts.disableLibraryVerification();
      if (this.basicView) {
         opts.enableBasicView();
      }

      if (this.beanScaffoldingEnabled) {
         opts.enableBeanScaffolding();
      }

      this.appMerge = new AppMerge(cmd, opts);
      return (WebLogicDeployableObject)this.appMerge.merge();
   }

   public AppMerge getAppMerge() {
      return this.appMerge;
   }

   private void dumpAppMergeArgs(String[] cmd) {
      if (debug) {
         Debug.say("invokinging appmerge with");

         for(int i = 0; i < cmd.length; ++i) {
            String s = cmd[i];
            Debug.say("  " + s);
         }
      }

   }

   private String[] createMergeCommand() throws IOException {
      List cmd = new ArrayList();
      if (this.lightWeightAppName != null) {
         cmd.add("-lightweight");
         cmd.add(this.lightWeightAppName);
      }

      cmd.add("-noexit");
      cmd.add("-nopackage");
      if (debug) {
         cmd.add("-verbose");
      }

      if (this.plan != null) {
         cmd.add("-plan");
         cmd.add(this.plan.getAbsolutePath());
      }

      if (this.plandir != null) {
         cmd.add("-plandir");
         cmd.add(this.plandir.getAbsolutePath());
      }

      int added = 0;
      String ls = "";
      if (this.libs != null) {
         for(int i = 0; i < this.libs.length; ++i) {
            LibrarySpec lib = this.libs[i];
            if (added++ > 0) {
               ls = ls + ",";
            }

            String loc = lib.getLocation().getAbsolutePath();
            String spec = lib.getSpecVersion();
            String impl = lib.getImplVersion();
            String name = lib.getName();
            ls = ls + loc;
            if (name != null) {
               ls = ls + "@name=" + name;
            }

            if (spec != null) {
               ls = ls + "@libspecver=" + spec;
            }

            if (impl != null) {
               ls = ls + "@libimplver=" + impl;
            }
         }

         if (ls.length() > 0) {
            cmd.add("-library");
            cmd.add(ls);
         }
      }

      cmd.add("-readonly");
      cmd.add(this.app.getPath());
      return (String[])((String[])cmd.toArray(new String[0]));
   }

   protected abstract LibraryRefBean[] getLibraryRefs(DescriptorBean var1);

   class MyLibraryDefinition extends LibraryDefinition {
      MyLibraryDefinition(LibraryData lib, Type type) {
         super(lib, type);
      }
   }
}
