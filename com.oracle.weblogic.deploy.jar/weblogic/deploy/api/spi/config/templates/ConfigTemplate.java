package weblogic.deploy.api.spi.config.templates;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.spi.DConfigBean;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.j2ee.descriptor.wl.AdminObjectGroupBean;
import weblogic.j2ee.descriptor.wl.AdminObjectsBean;
import weblogic.j2ee.descriptor.wl.ApplicationSecurityRoleAssignmentBean;
import weblogic.j2ee.descriptor.wl.EntityDescriptorBean;
import weblogic.j2ee.descriptor.wl.MessageDrivenDescriptorBean;
import weblogic.j2ee.descriptor.wl.PersistenceBean;
import weblogic.j2ee.descriptor.wl.PersistenceUseBean;
import weblogic.j2ee.descriptor.wl.SecurityBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBean;
import weblogic.j2ee.descriptor.wl.WeblogicApplicationBeanDConfig;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBeanDConfig;
import weblogic.j2ee.descriptor.wl.WeblogicModuleBean;
import weblogic.management.config.templates.DeployConfigTemplateService;

public class ConfigTemplate {
   private static final boolean debug = Debug.isDebug("config");

   public static boolean requireEjbRefDConfig(DDBean ddb, DConfigBean parent) {
      if (ddb.getXpath().endsWith("ejb-ref") || ddb.getXpath().endsWith("ejb-local-ref")) {
         DDBean[] link = ddb.getChildBean("ejb-link");
         if (link != null) {
            return false;
         }
      }

      return true;
   }

   public static void configureSecurity(WeblogicApplicationBeanDConfig dcb) {
      if (debug) {
         Debug.say("configuring ear security");
      }

      DDBean appDD = dcb.getDDBean();
      DDBean[] roleNames = appDD.getChildBean(dcb.applyNamespace("security-role/role-name"));
      if (roleNames != null) {
         SecurityBean sb = dcb.getSecurity();
         WeblogicApplicationBean appBean = (WeblogicApplicationBean)dcb.getDescriptorBean();
         if (sb == null) {
            if (debug) {
               Debug.say("Creating SecurityBean");
            }

            sb = appBean.createSecurity();
         }

         ApplicationSecurityRoleAssignmentBean[] sras = sb.getSecurityRoleAssignments();
         List roles = new ArrayList();
         if (sras != null) {
            for(int i = 0; i < sras.length; ++i) {
               roles.add(sras[i].getRoleName());
            }
         }

         for(int i = 0; i < roleNames.length; ++i) {
            String role = ConfigHelper.getText(roleNames[i]);
            if (debug) {
               Debug.say("Checking role, " + role);
            }

            if (!roles.contains(role)) {
               if (debug) {
                  Debug.say("Adding role, " + role);
               }

               ApplicationSecurityRoleAssignmentBean sra = sb.createSecurityRoleAssignment();
               sra.setRoleName(role);
            }
         }

      }
   }

   public static void configureEntityDescriptor(WeblogicEnterpriseBeanBeanDConfig dcb) {
      if (debug) {
         Debug.say("configuring ejb");
      }

      WeblogicEnterpriseBeanBean webb = (WeblogicEnterpriseBeanBean)dcb.getDescriptorBean();
      DDBean bean = dcb.getDDBean();
      if (bean.getXpath().endsWith("entity")) {
         DDBean[] pts = bean.getChildBean(dcb.applyNamespace("persistence-type"));
         if (pts != null) {
            DDBean pt = pts[0];
            if ("Container".equals(ConfigHelper.getText(pt))) {
               EntityDescriptorBean edb = dcb.getEntityDescriptor();
               PersistenceBean pb = edb.getPersistence();
               PersistenceUseBean pu = pb.getPersistenceUse();
               if (debug) {
                  Debug.say("adding listener");
               }

               ((AbstractDescriptorBean)pu).addPropertyChangeListener(new TypeStorageListener(dcb));
               if (pu.getTypeIdentifier() == null) {
                  DDBean[] cvs = bean.getChildBean(dcb.applyNamespace("cmp-version"));
                  String version;
                  if (cvs == null) {
                     version = "2.x";
                  } else {
                     version = ConfigHelper.getText(cvs[0]);
                  }

                  if (version.startsWith("2.")) {
                     if (pu.getTypeIdentifier() == null) {
                        pu.setTypeIdentifier("WebLogic_CMP_RDBMS");
                     }

                     if (pu.getTypeVersion() == null) {
                        pu.setTypeVersion("6.0");
                     }
                  } else {
                     if (pu.getTypeIdentifier() == null) {
                        pu.setTypeIdentifier("WebLogic_CMP_RDBMS");
                     }

                     if (pu.getTypeVersion() == null) {
                        pu.setTypeVersion("5.1.0");
                     }
                  }
               }
            }
         }
      }

   }

   public static void configureMessageDrivenDescriptor(WeblogicEnterpriseBeanBeanDConfig dcb) {
      if (debug) {
         Debug.say("configuring mdb");
      }

      WeblogicEnterpriseBeanBean webb = (WeblogicEnterpriseBeanBean)dcb.getDescriptorBean();
      DDBean bean = dcb.getDDBean();
      if (bean.getXpath().endsWith("message-driven")) {
         DDBean[] mdts = bean.getChildBean(dcb.applyNamespace("message-destination-type"));
         if (mdts != null) {
            DDBean mdt = mdts[0];
            if ("javax.jms.Queue".equals(ConfigHelper.getText(mdt)) || "javax.jms.Topic".equals(ConfigHelper.getText(mdt))) {
               MessageDrivenDescriptorBean var5 = webb.getMessageDrivenDescriptor();
            }
         }
      }

   }

   public static void configureWeblogicApplication(WeblogicApplicationBeanDConfig dcb) {
      if (debug) {
         Debug.say("configuring app");
      }

      WeblogicApplicationBean app = (WeblogicApplicationBean)dcb.getDescriptorBean();
      WeblogicModuleBean[] wmbs = app.getModules();
      if (wmbs != null) {
         for(int i = 0; i < wmbs.length; ++i) {
            ((AbstractDescriptorBean)wmbs[i]).addPropertyChangeListener(new ModuleListener(dcb));
         }
      }

   }

   public static void configureAdminObj(WeblogicConnectorBeanDConfig dcb) {
      if (debug) {
         Debug.say("Configuring rar admin objects");
      }

      DDBean connDD = dcb.getDDBean();
      DDBean[] adminObjs = connDD.getChildBean(dcb.applyNamespace("connector/resourceadapter/adminobject/adminobject-interface"));
      if (adminObjs != null) {
         AdminObjectsBean aosb = dcb.getAdminObjects();
         WeblogicConnectorBean wlConnBean = (WeblogicConnectorBean)dcb.getDescriptorBean();
         if (aosb == null) {
            if (debug) {
               Debug.say("Get Default AdminOjbectsBean");
            }

            aosb = wlConnBean.getAdminObjects();
         }

         AdminObjectGroupBean[] aogbs = aosb.getAdminObjectGroups();
         List interfaces = new ArrayList();

         for(int i = 0; i < aogbs.length; ++i) {
            interfaces.add(aogbs[i].getAdminObjectInterface());
         }

         for(int i = 0; i < adminObjs.length; ++i) {
            String interfaceName = ConfigHelper.getText(adminObjs[i]);
            if (debug) {
               Debug.say("Checking interface, " + interfaceName);
            }

            if (!interfaces.contains(interfaceName)) {
               if (debug) {
                  Debug.say("Adding admin group for interface, " + interfaceName);
               }

               AdminObjectGroupBean aogb = aosb.createAdminObjectGroup();
               aogb.setAdminObjectInterface(interfaceName);
            }
         }

      }
   }

   @Service
   private static class DeployConfigTemplateServiceImpl implements DeployConfigTemplateService {
      public boolean requireEjbRefDConfig(DDBean ddb, DConfigBean parent) {
         return ConfigTemplate.requireEjbRefDConfig(ddb, parent);
      }

      public void configureSecurity(DConfigBean bean) {
         ConfigTemplate.configureSecurity((WeblogicApplicationBeanDConfig)bean);
      }

      public void configureWeblogicApplication(DConfigBean bean) {
         ConfigTemplate.configureWeblogicApplication((WeblogicApplicationBeanDConfig)bean);
      }

      public void configureEntityDescriptor(DConfigBean bean) {
         ConfigTemplate.configureEntityDescriptor((WeblogicEnterpriseBeanBeanDConfig)bean);
      }

      public void configureMessageDrivenDescriptor(DConfigBean bean) {
         ConfigTemplate.configureMessageDrivenDescriptor((WeblogicEnterpriseBeanBeanDConfig)bean);
      }

      public void configureAdminObj(DConfigBean bean) {
         ConfigTemplate.configureAdminObj((WeblogicConnectorBeanDConfig)bean);
      }
   }
}
