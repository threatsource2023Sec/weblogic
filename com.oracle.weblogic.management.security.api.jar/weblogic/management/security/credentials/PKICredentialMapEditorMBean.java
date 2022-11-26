package weblogic.management.security.credentials;

import weblogic.management.utils.CreateException;
import weblogic.management.utils.NotFoundException;

public interface PKICredentialMapEditorMBean extends PKICredentialMapReaderMBean {
   void setKeypairCredential(String var1, String var2, boolean var3, String var4, String var5, char[] var6) throws CreateException;

   void setCertificateCredential(String var1, String var2, boolean var3, String var4, String var5) throws CreateException;

   void removePKICredentialMapping(String var1, String var2, boolean var3, String var4, String var5) throws NotFoundException;
}
