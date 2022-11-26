package com.bea.common.security.store.data;

import com.bea.common.security.utils.Pair;
import java.util.Map;

public abstract class PartnerId extends DomainRealmScopeId {
   private String name;

   public PartnerId() {
   }

   public PartnerId(String domainName, String realmName, String name) {
      super(domainName, realmName);
      this.name = name;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof PartnerId)) {
         return false;
      } else {
         PartnerId o = (PartnerId)other;
         return this.name == o.name || this.name != null && this.name.equals(o.name);
      }
   }

   public int hashCode() {
      return (this.name != null ? this.name.hashCode() : 0) ^ super.hashCode();
   }

   public String toString() {
      return "name=" + ApplicationIdUtil.encode(this.name) + ',' + super.toString();
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   protected Pair prepareQuery() {
      Pair p = super.prepareQuery();
      Map params = (Map)p.getRight();
      params.put("name", this.name);
      StringBuilder filter = new StringBuilder();
      filter.append((String)p.getLeft());
      filter.append(" && this.name == name");
      return new Pair(filter.toString(), params);
   }
}
