package weblogic.management.security.credentials;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;

public interface CredentialCacheMBean extends StandardInterface, DescriptorBean {
   boolean isCredentialCachingEnabled();

   void setCredentialCachingEnabled(boolean var1) throws InvalidAttributeValueException;

   int getCredentialsCacheSize();

   void setCredentialsCacheSize(int var1) throws InvalidAttributeValueException;

   int getCredentialCacheTTL();

   void setCredentialCacheTTL(int var1) throws InvalidAttributeValueException;
}
