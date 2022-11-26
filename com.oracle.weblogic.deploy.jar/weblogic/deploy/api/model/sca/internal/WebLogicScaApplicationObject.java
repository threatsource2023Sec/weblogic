package weblogic.deploy.api.model.sca.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.compiler.AppMerge;
import weblogic.application.compiler.deploymentview.EditableDeployableObject;
import weblogic.application.compiler.deploymentview.EditableScaApplicationObject;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.deploy.api.model.internal.DDBeanRootImpl;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.DescriptorBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.compiler.Tool;
import weblogic.utils.compiler.ToolFailureException;

public class WebLogicScaApplicationObject extends WebLogicDeployableObject implements EditableScaApplicationObject {
   private static final boolean debug = Debug.isDebug("model");
   private AppMerge appMergeToClose = null;

   public WebLogicScaApplicationObject(File module, File installDir, File plan, File plandir) throws IOException {
      super(module, WebLogicModuleType.SCA_COMPOSITE, (WebLogicDeployableObject)null, (String)null, (String)null, installDir, plan, plandir);
   }

   public String[] getModuleUris() {
      return this.getModuleUris(this.getDeployableObjects());
   }

   public String[] getModuleUris(ModuleType type) {
      ConfigHelper.checkParam("ModuleType", type);
      return type == WebLogicModuleType.SCA_COMPOSITE ? null : this.getModuleUris(this.getDeployableObjects(type));
   }

   public DeployableObject[] getDeployableObjects() {
      return this.subModules.size() == 0 ? null : (DeployableObject[])((DeployableObject[])this.subModules.toArray(new WebLogicDeployableObject[0]));
   }

   public DeployableObject[] getDeployableObjects(ModuleType type) {
      ConfigHelper.checkParam("ModuleType", type);
      List doList = new ArrayList();
      if (type == WebLogicModuleType.SCA_COMPOSITE) {
         return new WebLogicDeployableObject[]{this};
      } else {
         Iterator subs = this.subModules.iterator();

         while(subs.hasNext()) {
            DeployableObject obj = (DeployableObject)subs.next();
            if (obj.getType().equals(type)) {
               doList.add(obj);
            }
         }

         if (doList.size() == 0) {
            return null;
         } else {
            return (DeployableObject[])((DeployableObject[])doList.toArray(new WebLogicDeployableObject[0]));
         }
      }
   }

   public DeployableObject getDeployableObject(String uri) {
      ConfigHelper.checkParam("uri", uri);
      DeployableObject[] objs = this.getDeployableObjects();
      WebLogicDeployableObject obj = null;
      if (objs != null) {
         for(int i = 0; i < objs.length; ++i) {
            WebLogicDeployableObject obj1 = (WebLogicDeployableObject)objs[i];
            if (uri.equals(obj1.getUri())) {
               obj = obj1;
               break;
            }
         }
      }

      return obj;
   }

   public DeployableObject[] getDeployableObjects(String uri) {
      ConfigHelper.checkParam("uri", uri);
      ArrayList deployableObjects = new ArrayList();
      DeployableObject[] objs = this.getDeployableObjects();
      if (objs != null) {
         for(int i = 0; i < objs.length; ++i) {
            WebLogicDeployableObject obj1 = (WebLogicDeployableObject)objs[i];
            if (uri.equals(obj1.getUri())) {
               deployableObjects.add(obj1);
            }
         }
      }

      return (DeployableObject[])((DeployableObject[])deployableObjects.toArray(new WebLogicDeployableObject[0]));
   }

   protected String[] getModuleUris(DeployableObject[] objs) {
      WebLogicDeployableObject obj = null;
      List objList = new ArrayList();
      if (objs != null) {
         for(int i = 0; i < objs.length; ++i) {
            obj = (WebLogicDeployableObject)objs[i];
            objList.add(obj.getUri());
         }
      }

      return objList.size() == 0 ? null : (String[])((String[])objList.toArray(new String[0]));
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

   public void addDeployableObject(EditableDeployableObject dobj) {
      WebLogicDeployableObject wdo = (WebLogicDeployableObject)dobj;
      if (debug) {
         Debug.say("Adding new deployable object at " + wdo.getUri() + " of type '" + wdo.getType() + "' on wdo: " + this);
      }

      wdo.setParent(this);
      this.subModules.add(wdo);
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
