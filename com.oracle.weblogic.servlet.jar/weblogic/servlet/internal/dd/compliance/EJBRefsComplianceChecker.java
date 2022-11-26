package weblogic.servlet.internal.dd.compliance;

import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.EjbReferenceDescriptionBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.j2ee.validation.ModuleValidationInfo;
import weblogic.utils.ErrorCollectionException;

public class EJBRefsComplianceChecker extends BaseComplianceChecker {
   private static final String EJB_LOCAL_REF = "<ejb-local-ref>";
   private static final String EJB_REF = "<ejb-ref>";
   private static final String SESSION = "Session";
   private static final String ENTITY = "Entity";

   public void check(DeploymentInfo info) throws ErrorCollectionException {
      WebAppBean mbean = info.getWebAppBean();
      if (mbean != null) {
         EjbRefBean[] refs = mbean.getEjbRefs();
         EjbLocalRefBean[] localrefs = mbean.getEjbLocalRefs();
         int i;
         if (refs != null) {
            for(i = 0; i < refs.length; ++i) {
               this.checkEJBRef(refs[i], info);
            }
         }

         if (localrefs != null && localrefs.length > 0) {
            for(i = 0; i < localrefs.length; ++i) {
               this.checkEJBLocalRef(localrefs[i], info);
            }
         }

         this.checkForExceptions();
      }
   }

   private void checkEJBLocalRef(EjbLocalRefBean localref, DeploymentInfo info) {
      if (localref != null) {
         this.validate(localref.getEjbRefName(), localref.getLocalHome(), localref.getLocal(), localref.getEjbRefType(), localref.getEjbLink(), true, info, true);
      }
   }

   private void checkEJBRef(EjbRefBean ejbRef, DeploymentInfo info) {
      if (ejbRef != null) {
         this.validate(ejbRef.getEjbRefName(), ejbRef.getHome(), ejbRef.getRemote(), ejbRef.getEjbRefType(), ejbRef.getEjbLink(), false, info, false);
      }
   }

   private void validate(String refName, String home, String remote, String refType, String link, boolean local, DeploymentInfo info, boolean isProvisional) {
      WeblogicWebAppBean ext = info.getWeblogicWebAppBean();
      if (ext == null && (link == null || link.length() == 0)) {
         this.addDescriptorError(this.fmt.NO_EJBLINK_AND_JNDI_NAME(local ? "<ejb-local-ref>" : "<ejb-ref>", refName));
      } else {
         if (ext != null && (link == null || link.length() == 0)) {
            boolean foundEntry = false;
            EjbReferenceDescriptionBean[] wlEjbRefs = null;
            wlEjbRefs = ext.getEjbReferenceDescriptions();
            if (wlEjbRefs != null) {
               for(int i = 0; i < wlEjbRefs.length; ++i) {
                  if (refName.equals(wlEjbRefs[i].getEjbRefName())) {
                     foundEntry = true;
                     break;
                  }
               }
            }

            if (!foundEntry) {
               this.addDescriptorError(this.fmt.NO_EJBLINK_AND_JNDI_NAME(local ? "<ejb-local-ref>" : "<ejb-ref>", refName));
               return;
            }
         }

         ModuleValidationInfo validationInfo = info.getModuleValidationInfo();
         if (validationInfo != null && link != null) {
            validationInfo.addEJBRef(refName, local, remote, home, refType, link, isProvisional);
         }

      }
   }
}
