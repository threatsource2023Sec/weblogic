package com.bea.common.security.store.data;

import com.bea.common.security.utils.Pair;
import java.util.Map;

public abstract class RegistryScopeId extends DomainRealmScopeId {
   public static final String REGISTRYNAME = "registryName";
   private String registryName;

   public RegistryScopeId() {
   }

   public RegistryScopeId(String domainName, String realmName, String registryName) {
      super(domainName, realmName);
      this.registryName = registryName;
   }

   public RegistryScopeId(String binding) {
      super(binding);
      Map p = ApplicationIdUtil.parse(binding);
      this.registryName = (String)p.get("registryName");
   }

   public RegistryScopeId(RegistryScope obj) {
      super((DomainRealmScope)obj);
      this.registryName = obj.getRegistryName();
   }

   protected Pair prepareQuery() {
      Pair p = super.prepareQuery();
      Map params = (Map)p.getRight();
      params.put("registryName", this.registryName);
      StringBuilder filter = new StringBuilder();
      filter.append((String)p.getLeft());
      filter.append(" && this.registryName == registryName");
      return new Pair(filter.toString(), params);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof RegistryScopeId)) {
         return false;
      } else {
         RegistryScopeId o = (RegistryScopeId)other;
         return this.registryName == o.registryName || this.registryName != null && this.registryName.equals(o.registryName);
      }
   }

   public int hashCode() {
      return (this.registryName != null ? this.registryName.hashCode() : 0) ^ super.hashCode();
   }

   public String toString() {
      return "registryName=" + ApplicationIdUtil.encode(this.registryName) + ',' + super.toString();
   }

   public String getRegistryName() {
      return this.registryName;
   }

   public void setRegistryName(String registryName) {
      this.registryName = registryName;
   }
}
