package com.bea.common.security.store.data;

public class SAML2CacheEntryId extends DomainRealmScopeId {
   private static final long serialVersionUID = 2524347149515836298L;
   private static final String CACHENAME = "cacheName";
   private static final String KEY = "key";
   private String cacheName;
   private String key;

   public SAML2CacheEntryId() {
   }

   public SAML2CacheEntryId(String domainName, String realmName, String cacheName, String key) {
      super(domainName, realmName);
      this.cacheName = cacheName;
      this.key = key;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof SAML2CacheEntryId)) {
         return false;
      } else {
         SAML2CacheEntryId o = (SAML2CacheEntryId)other;
         return this.cacheName == o.cacheName || this.cacheName != null && this.cacheName.equals(o.cacheName) && this.key == o.key || this.key != null && this.key.equals(o.key);
      }
   }

   public int hashCode() {
      return (this.cacheName == null ? 0 : this.cacheName.hashCode()) ^ (this.key == null ? 0 : this.key.hashCode()) ^ super.hashCode();
   }

   public String toString() {
      return "cacheName=" + ApplicationIdUtil.encode(this.cacheName) + ',' + "key" + '=' + ApplicationIdUtil.encode(this.key) + ',' + super.toString();
   }

   public String getCacheName() {
      return this.cacheName;
   }

   public String getKey() {
      return this.key;
   }
}
