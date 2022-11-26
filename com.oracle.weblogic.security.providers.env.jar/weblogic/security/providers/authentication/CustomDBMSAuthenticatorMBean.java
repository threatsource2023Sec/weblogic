package weblogic.security.providers.authentication;

import java.util.Properties;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;

public interface CustomDBMSAuthenticatorMBean extends StandardInterface, DescriptorBean, DBMSAuthenticatorMBean {
   String getProviderClassName();

   String getPluginClassName();

   void setPluginClassName(String var1) throws InvalidAttributeValueException;

   Properties getPluginProperties();

   void setPluginProperties(Properties var1) throws InvalidAttributeValueException;

   String getName();
}
