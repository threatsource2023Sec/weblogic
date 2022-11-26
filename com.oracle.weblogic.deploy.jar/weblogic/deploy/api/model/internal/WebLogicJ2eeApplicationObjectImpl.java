package weblogic.deploy.api.model.internal;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.compiler.AppMerge;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.application.compiler.deploymentview.EditableJ2eeApplicationObject;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.deploy.api.model.WebLogicJ2eeApplicationObject;
import weblogic.descriptor.DescriptorBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.Source;
import weblogic.utils.compiler.Tool;
import weblogic.utils.compiler.ToolFailureException;

public class WebLogicJ2eeApplicationObjectImpl extends WebLogicJ2eeApplicationObject implements EditableJ2eeApplicationObject {
   private static final boolean debug = Debug.isDebug("model");
   private AppMerge appMergeToClose = null;

   protected WebLogicJ2eeApplicationObjectImpl(File app, File appRoot, File plan, File plandir) throws IOException {
      super(app, appRoot, plan, plandir);
   }

   public void addDeployableObject(EditableDeployableObject dobj) {
      WebLogicDeployableObject wdo = (WebLogicDeployableObject)dobj;
      if (wdo.getType() == null) {
         throw new AssertionError("Unable to determine module type for : " + wdo.getArchive() + " with uri : " + wdo.getUri() + " in app " + this.getArchive());
      } else {
         if (debug) {
            Debug.say("Adding new deployable object at " + wdo.getUri() + " of type '" + wdo.getType() + "' on wdo: " + this);
         }

         ModuleType modType = wdo.getType();
         if (modType != ModuleType.EJB && modType != ModuleType.CAR && modType != ModuleType.WAR && modType != ModuleType.RAR) {
            try {
               if (wdo.isDBSet()) {
                  if (debug) {
                     Debug.say("Adding new ddbeanroot to application at " + wdo.getUri() + " on wdo: " + this + "\nwith descriptor bean: " + wdo.getDescriptorBean());
                  }

                  this.ddMap.put(wdo.getUri(), new DDBeanRootImpl(wdo.getUri(), this, wdo.getType(), wdo.getDescriptorBean(), true));
               }
            } catch (IOException var5) {
               if (debug) {
                  Debug.say("Problem getting descriptor bean from wdo: " + var5);
               }
            }

            if (debug) {
               Debug.say("WDO is not the standard module type\t" + wdo.getArchive() + " type : " + modType);
            }

            wdo.close();
         } else {
            if (debug) {
               Debug.say("adding module : " + wdo.getArchive() + " with db: " + wdo.isDBSet());
            }

            wdo.setParent(this);
            this.subModules.add(wdo);
         }

      }
   }

   public void setClassLoader(GenericClassLoader cl) {
      this.gcl = cl;
   }

   public void setRootBean(DescriptorBean db) {
      this.setDescriptorBean(db);
      this.setDDBeanRoot(new DDBeanRootImpl((String)null, this, this.moduleType, db, true));
   }

   public void addRootBean(String uriArg, DescriptorBean db, ModuleType mt) {
      if (db != null) {
         if (debug) {
            Debug.say("Adding " + mt + " rootbean to application at " + uriArg + " on wdo: " + this + "\nwith descriptor bean: " + db);
         }

         this.ddMap.put(uriArg, new DDBeanRootImpl(uriArg, this, mt, db, true));
      }
   }

   public void setAppMerge(Tool am) {
      if (am instanceof AppMerge) {
         this.appMergeToClose = (AppMerge)am;
      }

   }

   public InputStream getEntry(String name) {
      ConfigHelper.checkParam("name", name);

      try {
         if (debug) {
            Debug.say("in DO : " + this.moduleArchive.getName() + " with uri " + this.uri);
         }

         if (debug) {
            Debug.say("Getting stream for entry " + name);
         }

         if (this.resourceFinder != null) {
            Source s = this.resourceFinder.getSource(name);
            if (s != null) {
               return s.getInputStream();
            }
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

   public void close() {
      super.close();
      if (this.resourceFinder != null) {
         this.resourceFinder.close();
      }

      this.resourceFinder = null;
      if (this.appMergeToClose == null) {
         throw new AssertionError("AppMerge not closed leaving possibly open handles");
      } else {
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
