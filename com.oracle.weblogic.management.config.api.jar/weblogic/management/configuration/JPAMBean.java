package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.management.DistributedManagementException;

public interface JPAMBean extends ConfigurationMBean {
   String PROVIDER_KODO = "org.apache.openjpa.persistence.PersistenceProviderImpl";
   String PROVIDER_TOPLINK = "org.eclipse.persistence.jpa.PersistenceProvider";

   String getDefaultJPAProvider();

   void setDefaultJPAProvider(String var1) throws InvalidAttributeValueException, DistributedManagementException;
}
