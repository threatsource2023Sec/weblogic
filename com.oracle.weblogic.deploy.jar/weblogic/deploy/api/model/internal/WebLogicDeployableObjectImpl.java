package weblogic.deploy.api.model.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.compiler.AppMerge;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.deploy.api.shared.WebLogicModuleTypeUtil;
import weblogic.descriptor.DescriptorBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.utils.compiler.Tool;
import weblogic.utils.compiler.ToolFailureException;
import weblogic.utils.jars.VirtualJarFile;

public class WebLogicDeployableObjectImpl extends WebLogicDeployableObject implements EditableDeployableObject {
   private static final boolean debug = Debug.isDebug("model");
   private AppMerge appMergeToClose = null;
   private String altdd;

   protected WebLogicDeployableObjectImpl(File module, ModuleType moduleType, WebLogicDeployableObject parent, String uri, String altdd, File installDir, File plan, File plandir) throws IOException {
      super(module, moduleType, parent, uri, altdd, installDir, plan, plandir);
      this.altdd = altdd;
   }

   public void setVirtualJarFile(VirtualJarFile vjf) {
      super.setVirtualJarFile(vjf);
      if (this.moduleType == null) {
         this.setModuleType(WebLogicModuleTypeUtil.getFileModuleType(this.uri, vjf));
      }

   }

   public void setClassLoader(GenericClassLoader cl) {
      this.gcl = cl;
   }

   public void setRootBean(DescriptorBean db) {
      this.beanTree = db;
      this.setDDBeanRoot(new DDBeanRootImpl(this.altdd, this, this.moduleType, db, true));
   }

   public void addRootBean(String uriArg, DescriptorBean db, ModuleType mt) {
      if (db != null) {
         if (debug) {
            Debug.say("Adding " + mt + " rootbean to application at " + uriArg + " on wdo: " + this + "\nwith descriptor bean: " + db);
         }

         this.ddMap.put(uriArg, new DDBeanRootImpl(uriArg, this, mt, db, true));
      }
   }

   public void setModuleArchive(File module) {
      this.moduleArchive = module;
   }

   public void setAppMerge(Tool am) {
      if (am instanceof AppMerge) {
         this.appMergeToClose = (AppMerge)am;
      }

   }

   public InputStream getEntry(String name) {
      if (this.resourceFinder == null) {
         if (debug) {
            Debug.say("Resource finder is null, using virtual jar file from super");
         }

         return super.getEntry(name);
      } else {
         ConfigHelper.checkParam("name", name);

         try {
            if (debug) {
               Debug.say("in DO : " + this.moduleArchive.getName() + " with uri " + this.uri);
            }

            if (debug) {
               Debug.say("Getting stream for entry " + name);
            }

            Source s = this.resourceFinder.getSource(name);
            if (s != null) {
               return s.getInputStream();
            }

            if (debug) {
               Debug.say("No entry in archive for " + name);
            }
         } catch (IOException var3) {
            if (debug) {
               Debug.say("No entry in archive for " + name);
            }
         }

         return null;
      }
   }

   public void close() {
      super.close();
      if (this.appMergeToClose != null) {
         try {
            this.appMergeToClose.cleanup();
         } catch (ToolFailureException var2) {
            if (debug) {
               Debug.say("Unable to cleanup AppMerge object");
               var2.printStackTrace();
            }
         }

         this.appMergeToClose = null;
      }

   }
}
