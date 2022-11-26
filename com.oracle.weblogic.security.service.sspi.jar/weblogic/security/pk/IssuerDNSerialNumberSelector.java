package weblogic.security.pk;

import com.bea.common.security.SecurityLogger;
import java.math.BigInteger;

public final class IssuerDNSerialNumberSelector implements CertPathSelector {
   private String issuerDN;
   private BigInteger serialNumber;

   public IssuerDNSerialNumberSelector(String issuerDN, BigInteger serialNumber) {
      if (issuerDN != null && issuerDN.length() >= 1) {
         if (serialNumber == null) {
            throw new IllegalArgumentException(SecurityLogger.getIssuerDNSerialNumberSelectorIllegalSerialNumber());
         } else {
            this.issuerDN = issuerDN;
            this.serialNumber = serialNumber;
         }
      } else {
         throw new IllegalArgumentException(SecurityLogger.getIssuerDNSerialNumberSelectorIllegalIssuerDN());
      }
   }

   public String getIssuerDN() {
      return this.issuerDN;
   }

   public BigInteger getSerialNumber() {
      return this.serialNumber;
   }

   public String toString() {
      return "IssuerDNSerialNumberSelector, issuerDN=\"" + this.issuerDN + "\", serialNumber=" + this.serialNumber;
   }
}
