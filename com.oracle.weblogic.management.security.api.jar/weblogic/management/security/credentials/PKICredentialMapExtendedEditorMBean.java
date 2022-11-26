package weblogic.management.security.credentials;

import weblogic.management.utils.CreateException;
import weblogic.management.utils.NotFoundException;

public interface PKICredentialMapExtendedEditorMBean extends PKICredentialMapExtendedReaderMBean {
   void setKeypairCredential(String var1, String var2, String var3, boolean var4, String var5, String var6, char[] var7) throws CreateException;

   void setCertificateCredential(String var1, String var2, String var3, boolean var4, String var5, String var6) throws CreateException;

   void removePKICredentialMapping(String var1, String var2, String var3, boolean var4, String var5, String var6) throws NotFoundException;
}
