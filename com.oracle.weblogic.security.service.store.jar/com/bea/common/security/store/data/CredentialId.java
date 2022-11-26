package com.bea.common.security.store.data;

import com.bea.common.security.utils.Pair;
import java.util.Map;

public abstract class CredentialId extends DomainRealmScopeId {
   public static final String CREDENTIALNAME = "credentialName";
   private String credentialName;

   public CredentialId() {
   }

   public CredentialId(String domainName, String realmName, String credentialName) {
      super(domainName, realmName);
      this.credentialName = credentialName;
   }

   public CredentialId(String binding) {
      super(binding);
      Map p = ApplicationIdUtil.parse(binding);
      this.credentialName = (String)p.get("credentialName");
   }

   public CredentialId(Credential obj) {
      super((DomainRealmScope)obj);
      this.credentialName = obj.getCredentialName();
   }

   protected Pair prepareQuery() {
      Pair p = super.prepareQuery();
      Map params = (Map)p.getRight();
      params.put("credentialName", this.credentialName);
      StringBuilder filter = new StringBuilder();
      filter.append((String)p.getLeft());
      filter.append(" && this.credentialName == credentialName");
      return new Pair(filter.toString(), params);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof CredentialId)) {
         return false;
      } else {
         CredentialId o = (CredentialId)other;
         return this.credentialName == o.credentialName || this.credentialName != null && this.credentialName.equals(o.credentialName);
      }
   }

   public int hashCode() {
      return (this.credentialName != null ? this.credentialName.hashCode() : 0) ^ super.hashCode();
   }

   public String toString() {
      return "credentialName=" + ApplicationIdUtil.encode(this.credentialName) + ',' + super.toString();
   }

   public String getCredentialName() {
      return this.credentialName;
   }

   public void setCredentialName(String credentialName) {
      this.credentialName = credentialName;
   }
}
