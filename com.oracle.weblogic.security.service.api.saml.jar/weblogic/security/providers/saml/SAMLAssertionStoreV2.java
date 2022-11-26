package weblogic.security.providers.saml;

import org.w3c.dom.Element;

public interface SAMLAssertionStoreV2 extends SAMLAssertionStore {
   boolean storeAssertionInfo(String var1, String var2, long var3, Element var5);

   AssertionInfo retrieveAssertionInfo(String var1);

   public static class AssertionInfo {
      private String artifact = null;
      private String partnerId = null;
      private long expire = 0L;
      private Element assertion = null;

      public AssertionInfo(String artifact, String partnerId, long expire, Element assertion) {
         this.artifact = artifact;
         this.partnerId = partnerId;
         this.expire = expire;
         this.assertion = assertion;
      }

      public String getArtifact() {
         return this.artifact;
      }

      public String getPartnerId() {
         return this.partnerId;
      }

      public long getExpire() {
         return this.expire;
      }

      public Element getAssertion() {
         return this.assertion;
      }
   }
}
