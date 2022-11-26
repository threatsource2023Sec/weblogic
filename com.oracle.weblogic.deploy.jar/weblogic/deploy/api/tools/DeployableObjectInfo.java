package weblogic.deploy.api.tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.model.exceptions.DDBeanCreateException;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.model.WebLogicDDBeanRoot;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.deploy.api.model.WebLogicJ2eeApplicationObject;
import weblogic.deploy.api.model.internal.DDBeanRootImpl;
import weblogic.deploy.api.model.sca.internal.WebLogicScaApplicationObject;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.api.spi.WebLogicDConfigBeanRoot;
import weblogic.deploy.api.spi.WebLogicDeploymentConfiguration;
import weblogic.deploy.api.spi.config.BasicDConfigBeanRoot;
import weblogic.deploy.utils.JMSModuleDefaultingHelper;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.DataSourceBean;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.EnterpriseBeanBean;
import weblogic.j2ee.descriptor.EnterpriseBeansBean;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.WebBean;
import weblogic.j2ee.descriptor.WebserviceDescriptionBean;
import weblogic.j2ee.descriptor.WebservicesBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.RestWebserviceDescriptionBean;
import weblogic.j2ee.descriptor.wl.RestWebservicesBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.utils.application.WarDetector;

public class DeployableObjectInfo extends ModuleInfo {
   private static final String REST_SERVICE_NAME_FORMAT = "[ %1s ] %2s @ %3s";
   private static final String REST_SERVICE_URI_SEPARATOR = " | ";
   private static final String REST_SERVICE_JAXRS_APPLICATION_AUTOMATICALLY_REGISTERED = "$$";
   private static final String[] WSEE_URIS = new String[]{"WEB-INF/webservices.xml", "META-INF/webservices.xml", "WEB-INF/web-services.xml", "META-INF/web-services.xml"};
   private static final String[] REST_URIS = new String[]{"weblogic.j2ee.descriptor.wl.RestWebservicesBean"};
   private WebLogicDeployableObject dobj = null;
   private WebLogicDeploymentConfiguration dc = null;

   protected DeployableObjectInfo(WebLogicDeployableObject dobj, WebLogicDeploymentConfiguration dc, String name) throws IOException, ConfigurationException {
      this.name = name;
      if (name == null) {
         this.name = dobj.getArchive().getName();
      }

      this.type = dobj.getType();
      this.archived = this.checkIfArchived(dobj);
      this.dobj = dobj;
      this.dc = dc;
      this.addSubModules();
      this.addWlsModules();
      this.addWebServices();
      this.addRestServices();
      this.addDataSources();
   }

   public String[] getBeans() {
      if (this.beans != null) {
         return this.beans;
      } else {
         WebLogicDDBeanRoot ddRoot = null;

         try {
            if (this.dobj.getType().getValue() == ModuleType.EJB.getValue()) {
               ddRoot = (WebLogicDDBeanRoot)this.dobj.getDDBeanRoot();
            } else if (this.dobj.getType().getValue() == ModuleType.WAR.getValue()) {
               ddRoot = (WebLogicDDBeanRoot)this.dobj.getDDBeanRoot("WEB-INF/ejb-jar.xml");
            }

            if (ddRoot != null) {
               DescriptorBean dd = ddRoot.getDescriptorBean();
               EnterpriseBeansBean allbeans = ((EjbJarBean)dd).getEnterpriseBeans();
               List bl = new ArrayList();
               SessionBeanBean[] var5 = allbeans.getSessions();
               int var6 = var5.length;

               int var7;
               for(var7 = 0; var7 < var6; ++var7) {
                  EnterpriseBeanBean session = var5[var7];
                  bl.add(session.getEjbName());
               }

               EntityBeanBean[] var10 = allbeans.getEntities();
               var6 = var10.length;

               for(var7 = 0; var7 < var6; ++var7) {
                  EnterpriseBeanBean entity = var10[var7];
                  bl.add(entity.getEjbName());
               }

               MessageDrivenBeanBean[] var11 = allbeans.getMessageDrivens();
               var6 = var11.length;

               for(var7 = 0; var7 < var6; ++var7) {
                  EnterpriseBeanBean mdb = var11[var7];
                  bl.add(mdb.getEjbName());
               }

               this.beans = (String[])bl.toArray(new String[0]);
            } else {
               this.beans = new String[0];
            }
         } catch (Exception var9) {
         }

         return this.beans;
      }
   }

   public String[] getSubDeployments() {
      if (this.subDeployments != null) {
         return this.subDeployments;
      } else {
         if (WebLogicModuleType.JMS.equals(this.type)) {
            try {
               BasicDConfigBeanRoot dConfig = (BasicDConfigBeanRoot)this.dc.getDConfigBeanRoot(this.dobj.getDDBeanRoot());
               JMSBean jmsModule = (JMSBean)dConfig.getDescriptorBean();
               this.subDeployments = JMSModuleDefaultingHelper.getSubDeploymentNames(jmsModule);
            } catch (ConfigurationException var3) {
               throw new Error("Impossible Exception " + var3);
            }
         }

         return this.subDeployments;
      }
   }

   public String[] getContextRoots() {
      if (this.roots != null) {
         return this.roots;
      } else if (this.dobj.getType().getValue() != ModuleType.WAR.getValue()) {
         return null;
      } else {
         if (this.getParent() == null) {
            this.roots = this.getStandAloneContextRoots();
         } else {
            String root = this.dobj.getContextRoot();
            this.roots = new String[]{root};
         }

         return this.roots;
      }
   }

   private void addSubModules() throws IOException, ConfigurationException {
      if (this.getType() == ModuleType.EAR) {
         this.addEarSubModules();
      } else if (this.getType() == WebLogicModuleType.SCA_COMPOSITE) {
         this.addScaSubModules();
      }

   }

   private void addEarSubModules() throws IOException, ConfigurationException {
      WebLogicJ2eeApplicationObject ear = (WebLogicJ2eeApplicationObject)this.dobj;
      String[] uris = ear.getModuleUris();
      if (uris != null) {
         ArrayList processedUris = new ArrayList();

         for(int i = 0; i < uris.length; ++i) {
            String uri = uris[i];
            if (!processedUris.contains(uri)) {
               DeployableObject[] deployableObjects = ear.getDeployableObjects(uri);

               for(int j = 0; j < deployableObjects.length; ++j) {
                  this.addModuleInfo(ModuleInfo.createModuleInfo((WebLogicDeployableObject)deployableObjects[j], (WebLogicDeploymentConfiguration)null, uri));
               }

               processedUris.add(uris[i]);
            }
         }

      }
   }

   private void addScaSubModules() throws IOException, ConfigurationException {
      WebLogicScaApplicationObject sca = (WebLogicScaApplicationObject)this.dobj;
      String[] uris = sca.getModuleUris();
      if (uris != null) {
         ArrayList processedUris = new ArrayList();

         for(int i = 0; i < uris.length; ++i) {
            String uri = uris[i];
            if (!processedUris.contains(uri)) {
               DeployableObject[] deployableObjects = sca.getDeployableObjects(uri);

               for(int j = 0; j < deployableObjects.length; ++j) {
                  this.addModuleInfo(ModuleInfo.createModuleInfo((WebLogicDeployableObject)deployableObjects[j], (WebLogicDeploymentConfiguration)null, uri));
               }

               processedUris.add(uris[i]);
            }
         }

      }
   }

   private String[] getContextRootsFromEar() {
      List rt = new ArrayList();
      WebLogicDeployableObject p = ((DeployableObjectInfo)this.getParent()).getDeployableObject();

      try {
         ApplicationBean ear = (ApplicationBean)p.getDescriptorBean();
         ModuleBean[] mods = ear.getModules();

         for(int i = 0; i < mods.length; ++i) {
            ModuleBean mod = mods[i];
            WebBean webmod = mod.getWeb();
            if (webmod != null && webmod.getWebUri().equals(this.getName())) {
               rt.add(mod.getWeb().getContextRoot());
            }
         }
      } catch (IOException var8) {
      }

      return (String[])((String[])rt.toArray(new String[0]));
   }

   private String[] getStandAloneContextRoots() {
      if (this.dc == null) {
         return new String[]{this.sansExtension(this.getName())};
      } else {
         String cr = null;

         try {
            WebLogicDConfigBeanRoot web = (WebLogicDConfigBeanRoot)this.dc.getDConfigBeanRoot(this.dobj.getDDBeanRoot());
            if (web != null) {
               WeblogicWebAppBean wb = (WeblogicWebAppBean)web.getDescriptorBean();
               cr = wb.getContextRoots().length > 0 ? wb.getContextRoots()[0] : null;
            }
         } catch (ConfigurationException var4) {
         }

         return cr == null ? new String[]{this.sansExtension(this.getName())} : new String[]{cr};
      }
   }

   private String sansExtension(String name) {
      return WarDetector.instance.stem(name);
   }

   private void addWebServices() {
      try {
         for(int i = 0; i < WSEE_URIS.length; ++i) {
            String uri = WSEE_URIS[i];
            if (this.dobj.hasDDBean(uri)) {
               this.extractWebServices(uri);
               return;
            }
         }
      } catch (IOException var3) {
      }

   }

   private void addRestServices() {
      try {
         for(int i = 0; i < REST_URIS.length; ++i) {
            String uri = REST_URIS[i];
            if (this.dobj.hasDDBean(uri)) {
               this.extractRestServices(uri);
               return;
            }
         }
      } catch (ConfigurationException | IOException var3) {
      }

   }

   private void extractRestServices(String uri) throws IOException, ConfigurationException {
      DDBeanRootImpl root;
      try {
         root = (DDBeanRootImpl)this.dobj.getDDBeanRoot(uri);
      } catch (DDBeanCreateException var6) {
         return;
      }

      RestWebservicesBean restWebservicesBean = (RestWebservicesBean)root.getDescriptorBean();
      if (restWebservicesBean != null) {
         RestWebserviceDescriptionBean[] allRestDescriptors = restWebservicesBean.getRestWebserviceDescriptions();
         this.restservices = new String[allRestDescriptors.length];

         for(int i = 0; i < allRestDescriptors.length; ++i) {
            this.restservices[i] = this.formatRestServiceName(allRestDescriptors[i]);
         }
      }

   }

   private String formatRestServiceName(RestWebserviceDescriptionBean restWebservice) throws ConfigurationException {
      String applicationClassName;
      if (restWebservice.getApplicationClassName() != null) {
         applicationClassName = restWebservice.getApplicationClassName();
      } else {
         applicationClassName = "$$";
      }

      StringBuilder uriSB = new StringBuilder();

      for(int i = 0; i < restWebservice.getApplicationBaseUris().length; ++i) {
         if (i > 0) {
            uriSB.append(" | ");
         }

         uriSB.append(restWebservice.getApplicationBaseUris()[i]);
      }

      String servletOrFilterName;
      if (restWebservice.getServletName() != null) {
         servletOrFilterName = restWebservice.getServletName();
      } else {
         if (restWebservice.getFilterName() == null) {
            throw new ConfigurationException("Illegal state: nor servlet neither filter set!");
         }

         servletOrFilterName = restWebservice.getFilterName();
      }

      String restServiceName = String.format("[ %1s ] %2s @ %3s", uriSB.toString(), applicationClassName, servletOrFilterName);
      return restServiceName;
   }

   private void addDataSources() {
      DescriptorBean descBean = null;

      try {
         descBean = this.dobj.getDescriptorBean();
      } catch (IOException var6) {
      }

      if (descBean != null) {
         List dsList = new ArrayList();
         if (descBean instanceof ApplicationBean) {
            ApplicationBean app = (ApplicationBean)descBean;
            this.addDataSourcesToList(dsList, app.getDataSources());
         } else if (descBean instanceof WebAppBean) {
            WebAppBean webapp = (WebAppBean)descBean;
            this.addDataSourcesToList(dsList, webapp.getDataSources());

            try {
               WebLogicDDBeanRoot root = (WebLogicDDBeanRoot)this.dobj.getDDBeanRoot("WEB-INF/ejb-jar.xml");
               if (root != null) {
                  descBean = root.getDescriptorBean();
                  if (descBean != null) {
                     this.addDataSourcesFromEJB(dsList, (EjbJarBean)descBean);
                  }
               }
            } catch (Exception var5) {
            }
         } else if (descBean instanceof EjbJarBean) {
            this.addDataSourcesFromEJB(dsList, (EjbJarBean)descBean);
         }

         if (dsList.size() > 0) {
            this.datasources = (String[])((String[])dsList.toArray(new String[0]));
         }

      }
   }

   protected WebLogicDeployableObject getDeployableObject() {
      return this.dobj;
   }

   private void extractWebServices(String uri) throws IOException {
      DDBeanRootImpl root;
      try {
         root = (DDBeanRootImpl)this.dobj.getDDBeanRoot(uri);
      } catch (DDBeanCreateException var8) {
         return;
      }

      List ws = new ArrayList();
      WebservicesBean wsBean = (WebservicesBean)root.getDescriptorBean();
      if (wsBean != null) {
         WebserviceDescriptionBean[] wd = wsBean.getWebserviceDescriptions();

         for(int i = 0; i < wd.length; ++i) {
            WebserviceDescriptionBean bean = wd[i];
            ws.add(bean.getWebserviceDescriptionName());
         }
      }

      this.webservices = (String[])((String[])ws.toArray(new String[0]));
   }

   private void addWlsModules() throws ConfigurationException {
      if (this.dobj.getType() == ModuleType.EAR) {
         if (this.dc != null) {
            WebLogicDConfigBeanRoot dcb = (WebLogicDConfigBeanRoot)this.dc.getDConfigBeanRoot(this.dobj.getDDBeanRoot());
            this.addModules((WeblogicApplicationBean)dcb.getDescriptorBean());
         }

      }
   }

   private void addModules(WeblogicApplicationBean wls) {
      WeblogicModuleBean[] mods = wls.getModules();

      for(int i = 0; i < mods.length; ++i) {
         WeblogicModuleBean mod = mods[i];
         this.addModuleInfo(mod, this.getDescriptorBean(mod));
      }

   }

   private DescriptorBean getDescriptorBean(WeblogicModuleBean mod) {
      DescriptorBean beanTree = null;

      try {
         DDBeanRoot ddBeanRoot = this.getDDBeanRoot(mod);
         if (ddBeanRoot != null && this.dc != null && this.dobj != null) {
            BasicDConfigBeanRoot dConfig = (BasicDConfigBeanRoot)this.dc.getDConfigBeanRoot(this.dobj.getDDBeanRoot());
            BasicDConfigBeanRoot dcForSecondaryModule = (BasicDConfigBeanRoot)dConfig.getDConfigBean(ddBeanRoot);
            beanTree = dcForSecondaryModule.getDescriptorBean();
         }

         return beanTree;
      } catch (ConfigurationException var6) {
         return beanTree;
      }
   }

   private DDBeanRoot getDDBeanRoot(WeblogicModuleBean mod) {
      try {
         return this.dobj.getDDBeanRoot(mod.getPath());
      } catch (FileNotFoundException var3) {
         return null;
      } catch (DDBeanCreateException var4) {
         return null;
      }
   }

   private void addDataSourcesFromEJB(List list, EjbJarBean ejb) {
      if (ejb != null) {
         EnterpriseBeansBean allbeans = ejb.getEnterpriseBeans();
         if (allbeans != null) {
            SessionBeanBean[] var4 = allbeans.getSessions();
            int var5 = var4.length;

            int var6;
            for(var6 = 0; var6 < var5; ++var6) {
               EnterpriseBeanBean session = var4[var6];
               this.addDataSourcesToList(list, session.getEjbName(), session.getDataSources());
            }

            EntityBeanBean[] var8 = allbeans.getEntities();
            var5 = var8.length;

            for(var6 = 0; var6 < var5; ++var6) {
               EnterpriseBeanBean entity = var8[var6];
               this.addDataSourcesToList(list, entity.getEjbName(), entity.getDataSources());
            }

            MessageDrivenBeanBean[] var9 = allbeans.getMessageDrivens();
            var5 = var9.length;

            for(var6 = 0; var6 < var5; ++var6) {
               EnterpriseBeanBean mdb = var9[var6];
               this.addDataSourcesToList(list, mdb.getEjbName(), mdb.getDataSources());
            }

         }
      }
   }

   private void addDataSourcesToList(List list, DataSourceBean[] dsBeans) {
      DataSourceBean[] var3 = dsBeans;
      int var4 = dsBeans.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         DataSourceBean dsBean = var3[var5];
         list.add(dsBean.getName());
      }

   }

   private void addDataSourcesToList(List list, String ejbName, DataSourceBean[] dsBeans) {
      DataSourceBean[] var4 = dsBeans;
      int var5 = dsBeans.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         DataSourceBean dsBean = var4[var6];
         list.add(ejbName + "@" + dsBean.getName());
      }

   }
}
