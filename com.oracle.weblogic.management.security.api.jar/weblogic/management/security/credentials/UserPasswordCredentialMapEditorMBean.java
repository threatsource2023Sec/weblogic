package weblogic.management.security.credentials;

import weblogic.management.utils.NotFoundException;

public interface UserPasswordCredentialMapEditorMBean extends UserPasswordCredentialMapReaderMBean {
   void setUserPasswordCredential(String var1, String var2, String var3);

   void setUserPasswordCredentialMapping(String var1, String var2, String var3) throws NotFoundException;

   void removeUserPasswordCredential(String var1, String var2) throws NotFoundException;

   void removeUserPasswordCredentialMapping(String var1, String var2) throws NotFoundException;
}
