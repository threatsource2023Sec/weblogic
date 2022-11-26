package com.bea.common.security.store.data;

import com.bea.common.security.utils.Pair;
import java.util.Map;

public class ResourceMapId extends DomainRealmScopeId {
   public static final String CN = "cn";
   public String cn;

   public ResourceMapId() {
   }

   public ResourceMapId(String binding) {
      super(binding);
      Map p = ApplicationIdUtil.parse(binding);
      this.cn = (String)p.get(this.cn);
   }

   public ResourceMapId(String domainName, String realmName, String cn) {
      super(domainName, realmName);
      this.cn = cn;
   }

   public ResourceMapId(ResourceMap obj) {
      super((DomainRealmScope)obj);
      this.cn = obj.getCn();
   }

   public String toString() {
      return "cn=" + ApplicationIdUtil.encode(this.cn) + ',' + super.toString();
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
      } else if (!(other instanceof ResourceMapId)) {
         return false;
      } else {
         ResourceMapId o = (ResourceMapId)other;
         return this.cn == o.cn || this.cn != null && this.cn.equals(o.cn);
      }
   }

   public int hashCode() {
      return (this.cn != null ? this.cn.hashCode() : 0) ^ super.hashCode();
   }

   public String getCn() {
      return this.cn;
   }

   public void setCn(String cn) {
      this.cn = cn;
   }
}
