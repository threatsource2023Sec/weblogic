package com.bea.common.security.store.data;

import com.bea.common.security.utils.Pair;
import java.util.HashMap;
import java.util.Map;

public abstract class DomainRealmScopeId extends TopId {
   public static final String DOMAIN = "domain";
   public static final String REALM = "realm";
   private String domainName;
   private String realmName;

   public DomainRealmScopeId() {
   }

   public DomainRealmScopeId(String domainName, String realmName) {
      this.domainName = domainName;
      this.realmName = realmName;
   }

   public DomainRealmScopeId(String binding) {
      super(binding);
      Map p = ApplicationIdUtil.parse(binding);
      this.domainName = (String)p.get("domain");
      this.realmName = (String)p.get("realm");
   }

   public DomainRealmScopeId(DomainRealmScope obj) {
      super((Top)obj);
      this.domainName = obj.getDomainName();
      this.realmName = obj.getRealmName();
   }

   protected Pair prepareQuery() {
      Map params = new HashMap();
      params.put("domainName", this.domainName);
      params.put("realmName", this.realmName);
      return new Pair("this.domainName == domainName && this.realmName == realmName", params);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof DomainRealmScopeId)) {
         return false;
      } else {
         DomainRealmScopeId o = (DomainRealmScopeId)other;
         return this.domainName == o.domainName || this.domainName != null && this.domainName.equals(o.domainName) && this.realmName == o.realmName || this.realmName != null && this.realmName.equals(o.realmName);
      }
   }

   public int hashCode() {
      return (this.domainName != null ? this.domainName.hashCode() : 0) ^ (this.realmName != null ? this.realmName.hashCode() : 0);
   }

   public String toString() {
      return "realm=" + ApplicationIdUtil.encode(this.realmName) + ',' + "domain" + '=' + ApplicationIdUtil.encode(this.domainName);
   }

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
}
