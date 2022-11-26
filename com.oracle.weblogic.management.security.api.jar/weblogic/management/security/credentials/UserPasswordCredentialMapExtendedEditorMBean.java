package weblogic.management.security.credentials;

import weblogic.management.utils.NotFoundException;

public interface UserPasswordCredentialMapExtendedEditorMBean extends UserPasswordCredentialMapExtendedV2ReaderMBean {
   void setUserPasswordCredentialMapping(String var1, String var2, String var3, String var4) throws NotFoundException;

   void removeUserPasswordCredentialMapping(String var1, String var2, String var3) throws NotFoundException;
}
