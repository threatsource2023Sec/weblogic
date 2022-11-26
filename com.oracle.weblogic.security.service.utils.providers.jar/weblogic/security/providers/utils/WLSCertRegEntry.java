package weblogic.security.providers.utils;

public class WLSCertRegEntry {
   private String domainName;
   private String realmName;
   private String registryName;
   private String cn;
   private String wlsCertRegSubjectDN;
   private String wlsCertRegIssuerDN;
   private String wlsCertRegSerialNumber;
   private String wlsCertRegSubjectKeyIdentifier;
   private byte[] userCertificate;

   public String getDomainName() {
      return this.domainName;
   }

   public void setDomainName(String domainName) {
      this.domainName = domainName;
   }

   public String getRealmName() {
      return this.realmName;
   }

   public void setRealmName(String realmName) {
      this.realmName = realmName;
   }

   public String getRegistryName() {
      return this.registryName;
   }

   public void setRegistryName(String registryName) {
      this.registryName = registryName;
   }

   public String getCn() {
      return this.cn;
   }

   public void setCn(String cn) {
      this.cn = cn;
   }

   public byte[] getUserCertificate() {
      return this.userCertificate;
   }

   public void setUserCertificate(byte[] userCertificate) {
      this.userCertificate = userCertificate;
   }

   public String getWlsCertRegIssuerDN() {
      return this.wlsCertRegIssuerDN;
   }

   public void setWlsCertRegIssuerDN(String wlsCertRegIssuerDN) {
      this.wlsCertRegIssuerDN = wlsCertRegIssuerDN;
   }

   public String getWlsCertRegSerialNumber() {
      return this.wlsCertRegSerialNumber;
   }

   public void setWlsCertRegSerialNumber(String wlsCertRegSerialNumber) {
      this.wlsCertRegSerialNumber = wlsCertRegSerialNumber;
   }

   public String getWlsCertRegSubjectDN() {
      return this.wlsCertRegSubjectDN;
   }

   public void setWlsCertRegSubjectDN(String wlsCertRegSubjectDN) {
      this.wlsCertRegSubjectDN = wlsCertRegSubjectDN;
   }

   public String getWlsCertRegSubjectKeyIdentifier() {
      return this.wlsCertRegSubjectKeyIdentifier;
   }

   public void setWlsCertRegSubjectKeyIdentifier(String wlsCertRegSubjectKeyIdentifier) {
      this.wlsCertRegSubjectKeyIdentifier = wlsCertRegSubjectKeyIdentifier;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof WLSCertRegEntry)) {
         return false;
      } else {
         WLSCertRegEntry o = (WLSCertRegEntry)other;
         return (this.cn == o.cn || this.cn != null && this.cn.equals(o.cn)) && (this.registryName == o.registryName || this.registryName != null && this.registryName.equals(o.registryName)) && (this.domainName == o.domainName || this.domainName != null && this.domainName.equals(o.domainName)) && (this.realmName == o.realmName || this.realmName != null && this.realmName.equals(o.realmName));
      }
   }

   public int hashCode() {
      return (this.cn != null ? this.cn.hashCode() : 0) ^ (this.registryName != null ? this.registryName.hashCode() : 0) ^ (this.domainName != null ? this.domainName.hashCode() : 0) ^ (this.realmName != null ? this.realmName.hashCode() : 0);
   }

   public String toString() {
      return "cn=" + this.cn + ",registryName=" + this.registryName + ",realm=" + this.realmName + ",domain=" + this.domainName;
   }
}
