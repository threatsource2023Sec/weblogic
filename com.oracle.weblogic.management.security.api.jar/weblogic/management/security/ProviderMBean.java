package weblogic.management.security;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;

public interface ProviderMBean extends StandardInterface, DescriptorBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   RealmMBean getRealm();

   String getName();

   void setName(String var1) throws InvalidAttributeValueException;

   String getCompatibilityObjectName();
}
