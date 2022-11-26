package weblogic.ejb.container.compliance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb20.dd.DescriptorErrorInfo;
import weblogic.j2ee.descriptor.EjbJarBean;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.EnterpriseBeansBean;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.SessionBeanBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.utils.ErrorCollectionException;

public final class WeblogicJarChecker extends BaseComplianceChecker {
   private final DeploymentInfo di;

   public WeblogicJarChecker(DeploymentInfo di) {
      this.di = di;
   }

   public void checkWeblogicJar() throws ErrorCollectionException {
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var2 = this.di.getBeanInfos().iterator();

      do {
         if (!var2.hasNext()) {
            return;
         }

         BeanInfo bi = (BeanInfo)var2.next();
         this.doCheckNoDuplicateJNDINames(bi, errors);
         this.doCheckEJBReferenceDescriptions(bi, errors);
      } while(errors.isEmpty());

      throw errors;
   }

   public void doCheckNoDuplicateJNDINames(BeanInfo bi, ErrorCollectionException errors) {
      if (bi instanceof ClientDrivenBeanInfo) {
         ClientDrivenBeanInfo cdbi = (ClientDrivenBeanInfo)bi;
         Set jndiNames = new HashSet();
         String jndiName = cdbi.getJNDINameAsString();
         String localJndiName = cdbi.getLocalJNDINameAsString();
         if (jndiName != null) {
            if (jndiNames.contains(jndiName)) {
               errors.add(new ComplianceException(this.getFmt().DUPLICATE_JNDI_NAME(cdbi.getEJBName(), jndiName), new DescriptorErrorInfo("<jndi-name>", cdbi.getEJBName(), jndiName)));
            } else if (!cdbi.hasRemoteClientView()) {
               Log.getInstance().logWarning(this.getFmt().JNDI_NAME_MUST_HAVE_REMOTE_INTERFACE(cdbi.getDisplayName()));
            } else {
               if (jndiName.indexOf("java:comp/env") != -1) {
                  errors.add(new ComplianceException(this.getFmt().INCORRECT_JNDI_NAME(cdbi.getEJBName(), jndiName)));
               }

               jndiNames.add(jndiName);
            }
         }

         if (localJndiName != null) {
            if (jndiNames.contains(localJndiName)) {
               errors.add(new ComplianceException(this.getFmt().DUPLICATE_JNDI_NAME(cdbi.getEJBName(), localJndiName), new DescriptorErrorInfo("<local-jndi-name>", cdbi.getEJBName(), jndiName)));
            } else if (!cdbi.hasLocalClientView()) {
               Log.getInstance().logWarning(this.getFmt().LOCAL_JNDI_NAME_MUST_HAVE_LOCAL_INTERFACE(cdbi.getDisplayName()));
            } else {
               if (localJndiName.indexOf("java:comp/env") != -1) {
                  errors.add(new ComplianceException(this.getFmt().INCORRECT_JNDI_NAME(cdbi.getEJBName(), localJndiName)));
               }

               jndiNames.add(localJndiName);
            }
         }
      }

   }

   public void doCheckEJBReferenceDescriptions(BeanInfo bi, ErrorCollectionException errors) {
      Map ejbRefJNDINames = bi.getAllEJBReferenceJNDINames();
      if (!ejbRefJNDINames.isEmpty()) {
         Collection ejbRefNames = new HashSet();
         Iterator var5 = bi.getAllEJBReferences().iterator();

         while(var5.hasNext()) {
            EjbRefBean eref = (EjbRefBean)var5.next();
            ejbRefNames.add(eref.getEjbRefName());
         }

         var5 = ejbRefJNDINames.keySet().iterator();

         while(var5.hasNext()) {
            String refName = (String)var5.next();
            if (!ejbRefNames.contains(refName)) {
               errors.add(new ComplianceException(this.getFmt().noEJBRefForReferenceDescription(bi.getEJBName(), refName)));
            }
         }
      }

      Map ejbLocalRefJNDINames = bi.getAllEJBLocalReferenceJNDINames();
      if (!ejbLocalRefJNDINames.isEmpty()) {
         Collection ejbRefNames = new HashSet();
         Iterator var11 = bi.getAllEJBLocalReferences().iterator();

         while(var11.hasNext()) {
            EjbLocalRefBean eref = (EjbLocalRefBean)var11.next();
            ejbRefNames.add(eref.getEjbRefName());
         }

         var11 = ejbLocalRefJNDINames.keySet().iterator();

         while(var11.hasNext()) {
            String refName = (String)var11.next();
            if (!ejbRefNames.contains(refName)) {
               errors.add(new ComplianceException(this.getFmt().noEJBLocalRefForReferenceDescription(bi.getEJBName(), refName)));
            }
         }
      }

   }

   public static void validateEnterpriseBeansMinimalConfiguration(EjbJarBean ejbJar, WeblogicEjbJarBean wlsEjbJar, String uri) throws ComplianceException {
      if (ejbJar == null) {
         if (wlsEjbJar != null) {
            WeblogicEnterpriseBeanBean[] var9 = wlsEjbJar.getWeblogicEnterpriseBeans();
            int var10 = var9.length;

            for(int var14 = 0; var14 < var10; ++var14) {
               WeblogicEnterpriseBeanBean wlseb = var9[var14];
               Log.getInstance().logWarning(EJBComplianceTextFormatter.getInstance().WRONG_EJBNAME_IN_WEBLOGIC_EJB_JAR_XML(wlseb.getEjbName()));
            }

         }
      } else {
         ArrayList ejbBeanNames = new ArrayList();
         EnterpriseBeansBean ebs = ejbJar.getEnterpriseBeans();
         if (ebs != null && ebs.getEntities().length + ebs.getMessageDrivens().length + ebs.getSessions().length >= 1) {
            SessionBeanBean[] var5 = ebs.getSessions();
            int var6 = var5.length;

            int var7;
            for(var7 = 0; var7 < var6; ++var7) {
               SessionBeanBean sbb = var5[var7];
               if (sbb.getEjbClass() == null) {
                  throw new ComplianceException(EJBComplianceTextFormatter.getInstance().NO_SESSION_BEAN_CLASS_FOUND_FOR_EJB(sbb.getEjbName()));
               }

               if (!"Stateless".equalsIgnoreCase(sbb.getSessionType()) && !"Stateful".equalsIgnoreCase(sbb.getSessionType()) && !"Singleton".equalsIgnoreCase(sbb.getSessionType())) {
                  throw new ComplianceException(EJBComplianceTextFormatter.getInstance().SESSION_BEAN_NO_SESSION_TYPE(sbb.getEjbName()));
               }

               ejbBeanNames.add(sbb.getEjbName());
            }

            MessageDrivenBeanBean[] var11 = ebs.getMessageDrivens();
            var6 = var11.length;

            for(var7 = 0; var7 < var6; ++var7) {
               MessageDrivenBeanBean mdb = var11[var7];
               if (mdb.getEjbClass() == null) {
                  throw new ComplianceException(EJBComplianceTextFormatter.getInstance().NO_MDB_CLASS_FOUND_FOR_EJB(mdb.getEjbName()));
               }

               ejbBeanNames.add(mdb.getEjbName());
            }

            if (wlsEjbJar != null) {
               EntityBeanBean[] var12 = ebs.getEntities();
               var6 = var12.length;

               for(var7 = 0; var7 < var6; ++var7) {
                  EntityBeanBean ebb = var12[var7];
                  ejbBeanNames.add(ebb.getEjbName());
               }

               WeblogicEnterpriseBeanBean[] var13 = wlsEjbJar.getWeblogicEnterpriseBeans();
               var6 = var13.length;

               for(var7 = 0; var7 < var6; ++var7) {
                  WeblogicEnterpriseBeanBean wlseb = var13[var7];
                  if (!ejbBeanNames.contains(wlseb.getEjbName())) {
                     Log.getInstance().logWarning(EJBComplianceTextFormatter.getInstance().WRONG_EJBNAME_IN_WEBLOGIC_EJB_JAR_XML(wlseb.getEjbName()));
                  }
               }

            }
         } else {
            throw new ComplianceException(EJBComplianceTextFormatter.getInstance().NO_EJBS_FOUND_IN_EJB_JAR(uri.toString()));
         }
      }
   }
}
