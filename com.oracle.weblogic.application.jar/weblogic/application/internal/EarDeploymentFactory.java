package weblogic.application.internal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationDescriptor;
import weblogic.application.ApplicationFileManager;
import weblogic.application.ComponentMBeanFactory;
import weblogic.application.Deployment;
import weblogic.application.DeploymentFactory;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.utils.EarUtils;
import weblogic.application.utils.ManagementUtils;
import weblogic.j2ee.descriptor.ApplicationBean;
import weblogic.j2ee.descriptor.ModuleBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.JDBCConnectionPoolBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ApplicationMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ComponentMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.utils.jars.VirtualJarFile;

public final class EarDeploymentFactory extends BaseComponentMBeanFactory implements DeploymentFactory, ComponentMBeanFactory {
   public boolean isSupportedBasic(BasicDeploymentMBean mbean, File file) {
      return EarUtils.isEar(file);
   }

   public boolean isSupportedAdvanced(BasicDeploymentMBean mbean, File file) {
      return this.isSupportedBasic(mbean, file);
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, File f) throws DeploymentException {
      return new EarDeployment(mbean, f);
   }

   public Deployment createDeployment(SystemResourceMBean mbean, File f) throws DeploymentException {
      return null;
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, ApplicationArchive app) throws DeploymentException {
      return EarUtils.isEar(app) ? new EarDeployment(mbean, app) : null;
   }

   public Deployment createDeployment(SystemResourceMBean mbean, ApplicationArchive f) throws DeploymentException {
      return null;
   }

   private void addAppComponents(ApplicationFileManager appFileManager, VirtualJarFile vjf, List comps, ApplicationBean appDD, ApplicationMBean appMBean, AppDeploymentMBean appDepMBean) throws IOException {
      ModuleBean[] m = appDD.getModules();
      if (m != null) {
         Set wsModules = WebServiceUtils.getWebServiceUtils().findWebServices(appMBean, appFileManager, vjf, m);

         for(int i = 0; i < m.length; ++i) {
            ComponentMBeanFactory.MBeanFactory factory = null;
            String uri = null;
            String name = null;
            if (m[i].getEjb() != null) {
               factory = EJB_COMP;
               uri = m[i].getEjb();
            } else if (m[i].getWeb() == null) {
               if (m[i].getConnector() != null) {
                  uri = m[i].getConnector();
                  factory = CONNECTOR_COMP;
               }
            } else {
               uri = m[i].getWeb().getWebUri();
               name = EarUtils.fixAppContextRoot(m[i].getWeb().getContextRoot());
               if (name == null || "".equals(name) || "/".equals(name)) {
                  name = uri;
               }

               factory = wsModules.contains(uri) ? WEB_SERVICE_COMP : WEB_COMP;
            }

            if (factory != null) {
               if (appDepMBean != null) {
                  if (name == null) {
                     name = uri;
                  }

                  name = this.getCompatibilityName(name, appDepMBean);
               }

               comps.add(this.findOrCreateComponentMBean(factory, appMBean, name, uri));
            }
         }

      }
   }

   protected String getCompatibilityName(String name, AppDeploymentMBean appDepMBean) {
      SubDeploymentMBean sd = appDepMBean.lookupSubDeployment(name);
      if (sd == null) {
         return name;
      } else {
         String s = sd.getCompatibilityName();
         return s == null ? name : s;
      }
   }

   private void addWLAppComponents(List comps, WeblogicApplicationBean wlDD, ApplicationMBean appMBean) {
      if (wlDD != null) {
         JDBCConnectionPoolBean[] pool = wlDD.getJDBCConnectionPools();
         if (pool != null && pool.length != 0) {
            for(int i = 0; i < pool.length; ++i) {
               String uri = pool[i].getDataSourceJNDIName();
               comps.add(this.findOrCreateComponentMBean(JDBC_COMP, appMBean, uri));
            }

         }
      }
   }

   public boolean needsApplicationPathMunging() {
      return false;
   }

   public ComponentMBean[] findOrCreateComponentMBeans(ApplicationMBean appMBean, File f, AppDeploymentMBean appDepMBean) throws DeploymentException {
      List comps = new ArrayList();
      VirtualJarFile vjf = null;

      ComponentMBean[] var9;
      try {
         ApplicationFileManager appFileManager = ApplicationFileManager.newInstance(f);
         vjf = appFileManager.getVirtualJarFile();
         boolean isInternal = appDepMBean == null ? true : appDepMBean.isInternalApp();
         ApplicationDescriptor desc = new ApplicationDescriptor(vjf, (File)null, (DeploymentPlanBean)null, f.getName());
         if (isInternal && ManagementUtils.getDomainMBean().isInternalAppsDeployOnDemandEnabled()) {
            desc.setValidateSchema(false);
         }

         if (desc.getApplicationDescriptor() == null) {
            throw new DeploymentException("The EAR file " + f.getPath() + " has no META-INF/application.xml and no modules could be found in it");
         }

         this.addAppComponents(appFileManager, vjf, comps, desc.getApplicationDescriptor(), appMBean, appDepMBean);
         this.addWLAppComponents(comps, desc.getWeblogicApplicationDescriptor(), appMBean);
         var9 = (ComponentMBean[])((ComponentMBean[])comps.toArray(new ComponentMBean[comps.size()]));
      } catch (XMLStreamException var19) {
         throw new DeploymentException(var19.getMessage(), var19);
      } catch (IOException var20) {
         throw new DeploymentException(var20.getMessage(), var20);
      } finally {
         if (vjf != null) {
            try {
               vjf.close();
            } catch (IOException var18) {
            }
         }

      }

      return var9;
   }
}
