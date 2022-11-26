package com.bea.common.security.store.data;

import com.bea.common.security.utils.Pair;
import java.util.Map;

public abstract class CredentialMapId extends DomainRealmScopeId {
   public static final String CN = "cn";
   private String cn;

   public CredentialMapId() {
   }

   public CredentialMapId(String domainName, String realmName, String cn) {
      super(domainName, realmName);
      this.cn = cn;
   }

   public CredentialMapId(String binding) {
      super(binding);
      Map p = ApplicationIdUtil.parse(binding);
      this.cn = (String)p.get("cn");
   }

   public CredentialMapId(CredentialMap obj) {
      super((DomainRealmScope)obj);
      this.cn = obj.getCn();
   }

   protected Pair prepareQuery() {
      Pair p = super.prepareQuery();
      Map params = (Map)p.getRight();
      params.put("cn", this.cn);
      StringBuilder filter = new StringBuilder();
      filter.append((String)p.getLeft());
      filter.append(" && this.cn == cn");
      return new Pair(filter.toString(), params);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof CredentialMapId)) {
         return false;
      } else {
         CredentialMapId o = (CredentialMapId)other;
         return this.cn == o.cn || this.cn != null && this.cn.equals(o.cn);
      }
   }

   public int hashCode() {
      return (this.cn != null ? this.cn.hashCode() : 0) ^ super.hashCode();
   }

   public String toString() {
      return "cn=" + ApplicationIdUtil.encode(this.cn) + ',' + super.toString();
   }

   public String getCn() {
      return this.cn;
   }

   public void setCn(String cn) {
      this.cn = cn;
   }
}
