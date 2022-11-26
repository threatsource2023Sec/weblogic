package weblogic.servlet.internal.dd.compliance;

import weblogic.j2ee.descriptor.JspConfigBean;
import weblogic.j2ee.descriptor.ListenerBean;
import weblogic.j2ee.descriptor.LoginConfigBean;
import weblogic.j2ee.descriptor.ResourceEnvRefBean;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.j2ee.descriptor.SessionConfigBean;
import weblogic.j2ee.descriptor.TagLibBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.ResourceDescriptionBean;
import weblogic.j2ee.descriptor.wl.ResourceEnvDescriptionBean;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.StringUtils;

public class WebAppDescriptorComplianceChecker extends BaseComplianceChecker {
   private static final String PARENT_CLASS = "java.util.EventListener";
   private static final String[] LISTENER_CLASSES = new String[]{"javax.servlet.ServletContextListener", "javax.servlet.ServletContextAttributeListener", "javax.servlet.ServletRequestListener", "javax.servlet.ServletRequestAttributeListener", "javax.servlet.http.HttpSessionListener", "javax.servlet.http.HttpSessionIdListener", "javax.servlet.http.HttpSessionActivationListener", "javax.servlet.http.HttpSessionAttributeListener", "javax.servlet.http.HttpSessionBindingListener"};
   private static final String SHAREABLE = "Shareable";
   private static final String UNSHAREABLE = "Unshareable";
   private static final String AUTH_APPLICATION = "Application";
   private static final String AUTH_CONTAINER = "Container";
   private static final String AM_BASIC = "BASIC";
   private static final String AM_DIGEST = "DIGEST";
   private static final String AM_FORM = "FORM";
   private static final String AM_CLIENT_CERT = "CLIENT-CERT";

   public void check(DeploymentInfo info) throws ErrorCollectionException {
      WebAppBean bean = info.getWebAppBean();
      SessionConfigBean[] sessions = bean.getSessionConfigs();
      if (sessions != null && sessions.length > 0) {
         this.validateSessionConfig(sessions[0]);
      }

      JspConfigBean[] configs = bean.getJspConfigs();
      int i;
      if (configs != null && configs.length > 0) {
         TagLibBean[] taglibs = configs[0].getTagLibs();
         if (taglibs != null) {
            for(i = 0; i < taglibs.length; ++i) {
               this.validateTagLib(taglibs[i]);
            }
         }
      }

      ListenerBean[] listeners = bean.getListeners();
      if (listeners != null) {
         for(i = 0; i < listeners.length; ++i) {
            this.validateListeners(listeners[i], info);
         }
      }

      ResourceEnvRefBean[] refEnvs = bean.getResourceEnvRefs();
      this.validateResourceEnvRefs(refEnvs, info);
      LoginConfigBean[] loginConfigs = bean.getLoginConfigs();
      if (loginConfigs != null && loginConfigs.length > 0) {
         this.validateLoginDescriptor(loginConfigs[0]);
      }

      this.checkForExceptions();
   }

   private void validateSessionConfig(SessionConfigBean session) {
      if (session != null) {
         ;
      }
   }

   private void validateListeners(ListenerBean listenerBean, DeploymentInfo info) throws ErrorCollectionException {
      if (listenerBean != null) {
         String className = listenerBean.getListenerClass();
         ClassLoader cl = info.getClassLoader();
         if (className != null && cl != null) {
            Class clazz = null;
            Class parent = null;

            try {
               clazz = cl.loadClass(className);
               parent = cl.loadClass("java.util.EventListener");
            } catch (ClassNotFoundException var11) {
               this.addDescriptorError(this.fmt.CLASS_NOT_FOUND("listener", className));
               return;
            }

            boolean assign = parent.isAssignableFrom(clazz);
            if (!assign) {
               this.addDescriptorError(this.fmt.INVALID_LISTENER_CLASS(className));
               return;
            }

            boolean isValidClass = false;

            for(int i = 0; i < LISTENER_CLASSES.length; ++i) {
               try {
                  Class listener = cl.loadClass(LISTENER_CLASSES[i]);
                  isValidClass = listener.isAssignableFrom(clazz);
                  if (isValidClass) {
                     break;
                  }
               } catch (ClassNotFoundException var12) {
                  this.addDescriptorError(this.fmt.CLASS_NOT_FOUND("listener", LISTENER_CLASSES[i]));
                  this.checkForExceptions();
               }
            }

            if (!isValidClass) {
               this.addDescriptorError(this.fmt.INVALID_LISTENER_CLASS(className));
               this.checkForExceptions();
            }
         }

      }
   }

   private void validateTagLib(TagLibBean taglib) {
      String location = taglib.getTagLibLocation();
      String uri = taglib.getTagLibUri();
      if (uri == null || uri.length() == 0) {
         this.addDescriptorError(this.fmt.NO_TAGLIB_URI());
      }

      if (location == null || location.length() == 0) {
         this.addDescriptorError(this.fmt.NO_TAGLIB_LOCATION());
      }

   }

   private void validateResourceReferences(ResourceRefBean[] refs, DeploymentInfo info) {
      if (refs != null) {
         ResourceDescriptionBean[] resourceDescriptions = null;
         if (info.getWeblogicWebAppBean() != null) {
            resourceDescriptions = info.getWeblogicWebAppBean().getResourceDescriptions();
         }

         for(int i = 0; i < refs.length; ++i) {
            String jndiName = null;
            String name = refs[i].getResRefName();
            String sharingScope = refs[i].getResSharingScope();
            String authType = refs[i].getResAuth();
            boolean found = false;
            if (name != null && resourceDescriptions != null) {
               for(int j = 0; j < resourceDescriptions.length; ++j) {
                  String ref = resourceDescriptions[j].getResRefName();
                  if (name.equals(ref)) {
                     found = true;
                     jndiName = resourceDescriptions[j].getJNDIName();
                     break;
                  }
               }

               if (!found) {
                  this.addDescriptorError(this.fmt.NO_RES_DESC_FOR_RESOURCE_REF(name));
               } else if (jndiName == null || jndiName.equals("")) {
                  this.addDescriptorError(this.fmt.NO_JNDI_NAME_FOR_RESOURCE_DESCRIPTOR(name));
               }
            }

            if (sharingScope != null && !"Shareable".equalsIgnoreCase(sharingScope) && !"Unshareable".equalsIgnoreCase(sharingScope)) {
               this.addDescriptorError(this.fmt.INVALID_SHARING_SCOPE(sharingScope, name));
            }

            if (!"Application".equalsIgnoreCase(authType) && !"Container".equalsIgnoreCase(authType)) {
               this.addDescriptorError(this.fmt.INVALID_RES_AUTH(name, authType));
            }
         }

      }
   }

   private void validateResourceEnvRefs(ResourceEnvRefBean[] refEnvs, DeploymentInfo info) {
      if (refEnvs != null) {
         ResourceEnvDescriptionBean[] wlEnvs = null;
         if (info.getWeblogicWebAppBean() != null) {
            wlEnvs = info.getWeblogicWebAppBean().getResourceEnvDescriptions();
         }

         if (wlEnvs != null) {
            for(int i = 0; i < refEnvs.length; ++i) {
               String name = refEnvs[i].getResourceEnvRefName();
               boolean found = false;
               if (wlEnvs != null && name != null) {
                  for(int j = 0; j < wlEnvs.length; ++j) {
                     if (name.equals(wlEnvs[j].getResourceEnvRefName())) {
                        found = true;
                        break;
                     }
                  }
               }

               if (!found) {
                  this.addDescriptorError(this.fmt.NO_RES_DESC_FOR_ENV_REF(name));
               }
            }
         }

      }
   }

   private void validateLoginDescriptor(LoginConfigBean login) {
      if (login != null) {
         String authMethod = login.getAuthMethod();
         if (authMethod != null) {
            String[] tokens = StringUtils.splitCompletely(authMethod, ", ");
            if (tokens.length == 0) {
               this.addDescriptorError(this.fmt.INVALID_AUTH_METHOD(authMethod));
            }

            for(int i = 0; i < tokens.length; ++i) {
               if (!tokens[i].equals("BASIC") && !tokens[i].equals("FORM") && !tokens[i].equals("CLIENT-CERT") && !tokens[i].equals("DIGEST")) {
                  this.addDescriptorError(this.fmt.INVALID_AUTH_METHOD(tokens[i]));
               }

               if ((tokens[i].equals("BASIC") || tokens[i].equals("FORM")) && i != tokens.length - 1) {
                  this.addDescriptorError(this.fmt.INVALID_AUTH_METHOD(authMethod));
               }

               if (tokens[i].equals("FORM")) {
                  if (login.getFormLoginConfig() == null || login.getFormLoginConfig().getFormLoginPage() == null || login.getFormLoginConfig().getFormLoginPage().length() < 1) {
                     this.addDescriptorError(this.fmt.LOGIN_PAGE_MISSING());
                  }

                  if (login.getFormLoginConfig() == null || login.getFormLoginConfig().getFormErrorPage() == null || login.getFormLoginConfig().getFormErrorPage().length() < 1) {
                     this.addDescriptorError(this.fmt.ERROR_PAGE_MISSING());
                  }
               }

               if (tokens[i].equals("DIGEST")) {
                  this.update(this.fmt.warning() + this.fmt.DIGEST_NOT_IMPLEMENTED());
               }
            }

         }
      }
   }
}
