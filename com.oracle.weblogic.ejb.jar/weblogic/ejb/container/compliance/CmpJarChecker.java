package weblogic.ejb.container.compliance;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.ejb.container.interfaces.CMPInfo;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.persistence.PersistenceType;
import weblogic.ejb20.dd.DescriptorErrorInfo;
import weblogic.utils.ErrorCollectionException;

public final class CmpJarChecker extends BaseComplianceChecker {
   private final DeploymentInfo di;

   public CmpJarChecker(DeploymentInfo di) {
      this.di = di;
   }

   public void checkCmpJar() throws ErrorCollectionException {
      PersistenceType jarType = null;
      boolean first = true;
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var4 = this.di.getEntityBeanInfos().iterator();

      while(var4.hasNext()) {
         EntityBeanInfo ebi = (EntityBeanInfo)var4.next();
         if (!ebi.getIsBeanManagedPersistence()) {
            CMPInfo cmpi = ebi.getCMPInfo();
            if (cmpi.uses20CMP()) {
               PersistenceType persistenceType = cmpi.getPersistenceType();
               if (first) {
                  jarType = persistenceType;
                  first = false;
               } else {
                  if (jarType != persistenceType) {
                     errors.add(new ComplianceException(this.getFmt().NOT_ALL_BEANS_USE_SAME_PERSISTENCE(ebi.getEJBName()), new DescriptorErrorInfo("<persistence-type>", ebi.getEJBName(), persistenceType)));
                  }

                  if (!errors.isEmpty()) {
                     throw errors;
                  }
               }
            }
         }
      }

   }

   public void checkUniqueAbstractSchemaNames() throws ErrorCollectionException {
      Set names = new HashSet();
      ErrorCollectionException errors = new ErrorCollectionException();
      Iterator var3 = this.di.getEntityBeanInfos().iterator();

      while(var3.hasNext()) {
         EntityBeanInfo ebi = (EntityBeanInfo)var3.next();
         if (!ebi.getIsBeanManagedPersistence()) {
            CMPInfo cmpi = ebi.getCMPInfo();
            if (cmpi.uses20CMP() && cmpi.getAbstractSchemaName() != null && !cmpi.getAbstractSchemaName().equals("")) {
               if (names.contains(cmpi.getAbstractSchemaName())) {
                  errors.add(new ComplianceException(this.getFmt().ABSTRACT_SCHEMA_NAME_NOT_UNIQUE(ebi.getEJBName()), new DescriptorErrorInfo("<abstract-schema-name>", ebi.getEJBName(), cmpi.getAbstractSchemaName())));
               }

               names.add(cmpi.getAbstractSchemaName());
            }
         }
      }

      if (!errors.isEmpty()) {
         throw errors;
      }
   }
}
