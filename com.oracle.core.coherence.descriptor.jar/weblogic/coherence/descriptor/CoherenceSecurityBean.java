package weblogic.coherence.descriptor;

public class CoherenceSecurityBean {
   private CoherenceKeyStoreBean identityKeyStoreBean;
   private CoherenceKeyStoreBean[] trustKeyStoreBeans;
   private String signatureAlgorithm;

   public CoherenceKeyStoreBean getIdentityKeyStoreBean() {
      return this.identityKeyStoreBean;
   }

   public void setIdentityKeyStoreBean(CoherenceKeyStoreBean identityKeyStoreBean) {
      this.identityKeyStoreBean = identityKeyStoreBean;
   }

   public CoherenceKeyStoreBean[] getTrustKeyStoreBeans() {
      return this.trustKeyStoreBeans;
   }

   public void setTrustKeyStoreBeans(CoherenceKeyStoreBean[] trustKeyStoreBeans) {
      this.trustKeyStoreBeans = trustKeyStoreBeans;
   }

   public String getSignatureAlgorithm() {
      return this.signatureAlgorithm;
   }

   public void setSignatureAlgorithm(String algorithm) {
      this.signatureAlgorithm = algorithm;
   }
}
