package weblogic.security.principal;

import java.io.Serializable;
import java.security.Principal;

public interface WLSPrincipal extends Principal, Serializable {
   byte[] getSignature();

   void setSignature(byte[] var1);

   byte[] getSignedData();

   byte[] getSalt();

   String getDn();

   String getGuid();
}
