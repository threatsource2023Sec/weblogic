package weblogic.application.naming;

import java.util.Collection;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.compiler.ToolsContext;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.ejb.spi.ClientDrivenBeanInfo;
import weblogic.j2ee.J2EELogger;
import weblogic.logging.Loggable;

class EjbReferenceResolver implements ReferenceResolver {
   private final String applicationId;
   private final String moduleId;
   private final String moduleURI;
   private final String ejbRefName;
   private final String ejbLink;
   private final String homeClassName;
   private final String referenceClassName;
   private final boolean isLocal;
   private ClientDrivenBeanInfo resolvedInfo = null;
   private Object resolvedObject = null;
   private String bindableClassName = null;

   EjbReferenceResolver(String applicationId, String moduleId, String moduleURI, String ejbRefName, String ejbLink, String homeClassName, String referenceClassName, boolean isLocal) {
      this.applicationId = applicationId;
      this.moduleId = moduleId;
      this.moduleURI = moduleURI;
      this.ejbRefName = ejbRefName;
      this.ejbLink = ejbLink;
      this.homeClassName = homeClassName;
      this.referenceClassName = referenceClassName;
      this.isLocal = isLocal;
   }

   public Object get() {
      if (this.resolvedObject != null) {
         return this.resolvedObject;
      } else {
         if (this.resolvedInfo == null) {
            ApplicationContextInternal appCtx = ApplicationAccess.getApplicationAccess().getApplicationContext(this.applicationId);

            try {
               this.resolve(appCtx);
            } catch (ReferenceResolutionException var3) {
               throw new RuntimeException(var3);
            }
         }

         this.resolvedObject = this.resolvedInfo.getBindable(this.bindableClassName);
         return this.resolvedObject;
      }
   }

   public void resolve(ApplicationContextInternal appCtx) throws ReferenceResolutionException {
      this.resolveEjbRef(EnvUtils.getAppModuleContexts(appCtx, WebLogicModuleType.MODULETYPE_EJB, WebLogicModuleType.MODULETYPE_WAR));
   }

   public void resolve(ToolsContext appCtx) throws ReferenceResolutionException {
      this.resolveEjbRef(EnvUtils.getAppModuleContexts(appCtx, ModuleType.EJB, ModuleType.WAR));
   }

   private void resolveEjbRef(Collection moduleContexts) throws ReferenceResolutionException {
      this.bindableClassName = this.homeClassName != null ? this.homeClassName : this.referenceClassName;
      if (this.ejbLink == null) {
         this.resolvedInfo = EnvUtils.findInfoByReferenceClass(moduleContexts, this.applicationId, this.moduleURI, this.ejbRefName, this.bindableClassName);
      } else {
         this.resolvedInfo = EnvUtils.findInfoByEjbLink(moduleContexts, this.applicationId, this.moduleId, this.moduleURI, this.ejbRefName, this.ejbLink);
         if (this.homeClassName != null) {
            if (this.isLocal && this.resolvedInfo.getLocalHomeInterfaceName() != null) {
               this.bindableClassName = this.resolvedInfo.getLocalHomeInterfaceName();
            }

            if (!this.isLocal && this.resolvedInfo.getHomeInterfaceName() != null) {
               this.bindableClassName = this.resolvedInfo.getHomeInterfaceName();
            }
         } else if (this.referenceClassName == null) {
            String[] ifaces = this.resolvedInfo.getImplementedInterfaceNames();
            if (ifaces.length == 1) {
               this.bindableClassName = ifaces[0];
            } else {
               if (ifaces.length != 2) {
                  Loggable l = J2EELogger.logFailedToCreateEjbRefMultipleInterfacesLoggable(this.ejbRefName, this.moduleURI, ApplicationVersionUtils.getApplicationName(this.applicationId));
                  throw new ReferenceResolutionException(l.getMessage());
               }

               if (this.resolvedInfo.getHomeInterfaceName() != null && this.resolvedInfo.getLocalHomeInterfaceName() != null) {
                  if (this.isLocal) {
                     this.bindableClassName = this.resolvedInfo.getLocalHomeInterfaceName();
                  } else {
                     this.bindableClassName = this.resolvedInfo.getHomeInterfaceName();
                  }
               }
            }
         }

         if (!this.resolvedInfo.hasClientViewFor(this.bindableClassName)) {
            Loggable l = J2EELogger.logEJBRefTargetDoesNotImplementInterfaceLoggable(this.ejbRefName, this.moduleURI, ApplicationVersionUtils.getApplicationName(this.applicationId), this.bindableClassName);
            throw new ReferenceResolutionException(l.getMessage());
         }
      }

   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (!(obj instanceof EjbReferenceResolver)) {
         return false;
      } else {
         EjbReferenceResolver resolver = (EjbReferenceResolver)obj;
         return !EnvEntriesValidateHelper.areConflicting(this.ejbRefName, resolver.ejbRefName) && !EnvEntriesValidateHelper.areConflicting(this.ejbLink, resolver.ejbLink) && !EnvEntriesValidateHelper.areConflicting(this.homeClassName, resolver.homeClassName) && !EnvEntriesValidateHelper.areConflicting(this.referenceClassName, resolver.referenceClassName) && this.isLocal == resolver.isLocal;
      }
   }

   public int hashCode() {
      StringBuilder strBuilder = (new StringBuilder()).append(this.ejbRefName).append(this.ejbLink).append(this.homeClassName).append(this.referenceClassName).append(this.isLocal);
      return strBuilder.toString().hashCode();
   }
}
