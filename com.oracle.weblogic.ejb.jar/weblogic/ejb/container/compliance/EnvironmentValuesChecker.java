package weblogic.ejb.container.compliance;

import java.util.Iterator;
import java.util.Map;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.MessageDestinationRefBean;
import weblogic.utils.ErrorCollectionException;

public final class EnvironmentValuesChecker extends BaseComplianceChecker {
   private final DeploymentInfo di;

   public EnvironmentValuesChecker(DeploymentInfo di) {
      this.di = di;
   }

   public void checkEnvironmentValues() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var2 = this.di.getBeanInfos().iterator();

      while(var2.hasNext()) {
         BeanInfo beanInfo = (BeanInfo)var2.next();
         if (!beanInfo.isEJB30()) {
            this.checkEnvEntries(beanInfo, errors);
            this.checkMessageDestinationRefs(beanInfo, errors);
            this.checkEjbRefs(beanInfo, errors);
            this.checkEjbLocalRefs(beanInfo, errors);
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }

   private void checkEjbRefs(BeanInfo bi, ErrorCollectionException errors) {
      Map ejbRefJndiMap = bi.getAllEJBReferenceJNDINames();
      Iterator var4 = bi.getAllEJBReferences().iterator();

      while(var4.hasNext()) {
         EjbRefBean eref = (EjbRefBean)var4.next();
         if (!ejbRefJndiMap.containsKey(eref.getEjbRefName())) {
            String link = eref.getEjbLink();
            if (link == null) {
               errors.add(new ComplianceException(this.getFmt().EJB_REF_MUST_HAVE_EJB_LINK_OR_REF_DESC(bi.getEJBName(), eref.getEjbRefName())));
            } else {
               BeanInfo target = this.di.getBeanInfo(link);
               if (target != null && !(target instanceof ClientDrivenBeanInfo)) {
                  errors.add(new ComplianceException(this.getFmt().ILLEGAL_LOCAL_EJB_LINK_TO_MDB(bi.getEJBName(), eref.getEjbRefName())));
               }
            }
         }

         if (eref.getHome() == null && eref.getRemote() == null) {
            errors.add(new ComplianceException("The ejb-ref " + eref.getEjbRefName() + " does not have a home or remote interface configured."));
         }
      }

   }

   private void checkEjbLocalRefs(BeanInfo bi, ErrorCollectionException errors) {
      Map ejbRefJndiMap = bi.getAllEJBLocalReferenceJNDINames();
      Iterator var4 = bi.getAllEJBLocalReferences().iterator();

      while(var4.hasNext()) {
         EjbLocalRefBean eref = (EjbLocalRefBean)var4.next();
         if (!ejbRefJndiMap.containsKey(eref.getEjbRefName())) {
            String link = eref.getEjbLink();
            if (link == null) {
               errors.add(new ComplianceException(this.getFmt().EJB_REF_MUST_HAVE_EJB_LINK_OR_REF_DESC(bi.getEJBName(), eref.getEjbRefName())));
            } else {
               BeanInfo target = this.di.getBeanInfo(link);
               if (target != null && !(target instanceof ClientDrivenBeanInfo)) {
                  errors.add(new ComplianceException(this.getFmt().ILLEGAL_LOCAL_EJB_LINK_TO_MDB(bi.getEJBName(), eref.getEjbRefName())));
               }
            }
         }

         if (eref.getLocalHome() == null && eref.getLocal() == null) {
            errors.add(new ComplianceException("The ejb-local-ref " + eref.getEjbRefName() + " does not have a local-home or local interface configured."));
         }
      }

   }

   private void checkMessageDestinationRefs(BeanInfo beanInfo, ErrorCollectionException errors) {
      Iterator var3 = beanInfo.getAllMessageDestinationReferences().iterator();

      while(var3.hasNext()) {
         MessageDestinationRefBean ref = (MessageDestinationRefBean)var3.next();
         if (ref.getMessageDestinationLink() == null) {
            errors.add(new ComplianceException(this.getFmt().MESSAGE_DESTINATION_REF_NOT_LINKED(ref.getMessageDestinationRefName(), beanInfo.getEJBName())));
         }
      }

   }

   private void checkEnvEntries(BeanInfo beanInfo, ErrorCollectionException errors) {
      Iterator var3 = beanInfo.getAllEnvironmentEntries().iterator();

      while(true) {
         EnvEntryBean eemb;
         String value;
         do {
            do {
               if (!var3.hasNext()) {
                  return;
               }

               eemb = (EnvEntryBean)var3.next();
               value = eemb.getEnvEntryValue();
            } while("java.lang.String".equals(eemb.getEnvEntryType()));
         } while(value != null && value.trim().length() != 0);

         errors.add(new ComplianceException(this.getFmt().ENV_VALUE_CANNOT_BE_NULL(eemb.getEnvEntryName())));
      }
   }
}
