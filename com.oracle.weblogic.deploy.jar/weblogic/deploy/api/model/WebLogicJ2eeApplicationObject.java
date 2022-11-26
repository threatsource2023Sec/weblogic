package weblogic.deploy.api.model;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.model.J2eeApplicationObject;
import javax.enterprise.deploy.model.XpathListener;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.exceptions.InvalidModuleException;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.internal.utils.LibrarySpec;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;

public class WebLogicJ2eeApplicationObject extends WebLogicDeployableObject implements J2eeApplicationObject {
   private static final boolean debug = Debug.isDebug("model");
   protected ApplicationBean app;

   public void close() {
      super.close();
   }

   protected WebLogicJ2eeApplicationObject(File module, File installDir, File plan, File plandir) throws IOException {
      super(module, ModuleType.EAR, (WebLogicDeployableObject)null, (String)null, (String)null, installDir, plan, plandir);
   }

   public void addXpathListener(ModuleType type, String xpath, XpathListener xpl) {
   }

   public void removeXpathListener(ModuleType type, String xpath, XpathListener xpl) {
   }

   public String[] getText(ModuleType type, String xpath) {
      ConfigHelper.checkParam("ModuleType", type);
      ConfigHelper.checkParam("xpath", xpath);
      if (type == ModuleType.EAR) {
         return this.getDDBeanRoot().getText(xpath);
      } else {
         DeployableObject[] objs = this.getDeployableObjects(type);
         if (objs == null) {
            return null;
         } else {
            List pathList = new ArrayList();

            for(int i = 0; i < objs.length; ++i) {
               DeployableObject obj = objs[i];
               if (obj.getType() == type) {
                  pathList.addAll(Arrays.asList((Object[])obj.getText(xpath)));
               }
            }

            return (String[])((String[])pathList.toArray(new String[0]));
         }
      }
   }

   public DDBean[] getChildBean(ModuleType type, String xpath) {
      ConfigHelper.checkParam("ModuleType", type);
      ConfigHelper.checkParam("xpath", xpath);
      if (type == ModuleType.EAR) {
         return this.getDDBeanRoot().getChildBean(xpath);
      } else {
         DeployableObject[] objs = this.getDeployableObjects(type);
         if (objs == null) {
            return null;
         } else {
            List childList = new ArrayList();

            for(int i = 0; i < objs.length; ++i) {
               DeployableObject obj = objs[i];
               if (obj.getType().getValue() == type.getValue()) {
                  DDBean[] ddbs = obj.getChildBean(xpath);
                  if (ddbs != null) {
                     childList.addAll(Arrays.asList(ddbs));
                  }
               }
            }

            if (childList.isEmpty()) {
               return null;
            } else {
               return (DDBean[])((DDBean[])childList.toArray(new DDBean[0]));
            }
         }
      }
   }

   public String[] getModuleUris() {
      return this.getModuleUris(this.getDeployableObjects());
   }

   public String[] getModuleUris(ModuleType type) {
      ConfigHelper.checkParam("ModuleType", type);
      return type == ModuleType.EAR ? null : this.getModuleUris(this.getDeployableObjects(type));
   }

   public DeployableObject[] getDeployableObjects() {
      return this.subModules.size() == 0 ? null : (DeployableObject[])((DeployableObject[])this.subModules.toArray(new WebLogicDeployableObject[0]));
   }

   public DeployableObject[] getDeployableObjects(ModuleType type) {
      ConfigHelper.checkParam("ModuleType", type);
      List doList = new ArrayList();
      if (type == ModuleType.EAR) {
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
      WebLogicDeployableObject obj = null;
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

   public DescriptorBean getDescriptorBean() {
      return (DescriptorBean)this.app;
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

   public void setDescriptorBean(DescriptorBean db) {
      if (!(db instanceof ApplicationBean)) {
         if (debug) {
            Thread.dumpStack();
         }

         throw new AssertionError(SPIDeployerLogger.unexpectedDD(this.getArchive().getPath()));
      } else {
         this.app = (ApplicationBean)db;
      }
   }

   protected void addModule(ModuleBean module) throws InvalidModuleException, IOException, URISyntaxException {
      ModuleType modType = null;
      String uri = null;
      if (debug) {
         Debug.say("Adding embedded module");
      }

      if (module.getEjb() != null) {
         modType = ModuleType.EJB;
         uri = module.getEjb();
      } else if (module.getJava() != null) {
         modType = ModuleType.CAR;
         uri = module.getJava();
      } else if (module.getWeb() != null) {
         modType = ModuleType.WAR;
         uri = module.getWeb().getWebUri();
      } else if (module.getConnector() != null) {
         modType = ModuleType.RAR;
         uri = module.getConnector();
      }

      if (debug) {
         Debug.say("module type is : " + modType);
      }

      if (modType != null) {
         if (this.getDeployableObject(uri) == null) {
            File modPath = this.getModulePath(uri);
            this.subModules.add(new WebLogicDeployableObject(modPath, modType, this, uri, module.getAltDd(), this.installDir.getInstallDir(), (File)null, (File)null, (LibrarySpec[])null, this.lazy));
         }
      }
   }

   protected File getModulePath(String uri) throws URISyntaxException {
      return new File(this.getVirtualJarFile().getEntry(uri).toString());
   }
}
