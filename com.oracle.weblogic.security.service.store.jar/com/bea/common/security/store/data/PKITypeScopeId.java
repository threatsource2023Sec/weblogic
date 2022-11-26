package com.bea.common.security.store.data;

import com.bea.common.security.utils.Pair;
import java.util.Map;

public abstract class PKITypeScopeId extends DomainRealmScopeId {
   public static final String TYPENAME = "typeName";
   private String typeName;

   public PKITypeScopeId() {
   }

   public PKITypeScopeId(String domainName, String realmName, String typeName) {
      super(domainName, realmName);
      this.typeName = typeName;
   }

   public PKITypeScopeId(String binding) {
      super(binding);
      Map p = ApplicationIdUtil.parse(binding);
      this.typeName = (String)p.get("typeName");
   }

   public PKITypeScopeId(PKITypeScope obj) {
      super((DomainRealmScope)obj);
      this.typeName = obj.getTypeName();
   }

   protected Pair prepareQuery() {
      Pair p = super.prepareQuery();
      Map params = (Map)p.getRight();
      params.put("typeName", this.typeName);
      StringBuilder filter = new StringBuilder();
      filter.append((String)p.getLeft());
      filter.append(" && this.typeName == typeName");
      return new Pair(filter.toString(), params);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof PKITypeScopeId)) {
         return false;
      } else {
         PKITypeScopeId o = (PKITypeScopeId)other;
         return this.typeName == o.typeName || this.typeName != null && this.typeName.equals(o.typeName);
      }
   }

   public int hashCode() {
      return (this.typeName != null ? this.typeName.hashCode() : 0) ^ super.hashCode();
   }

   public String toString() {
      return "typeName=" + ApplicationIdUtil.encode(this.typeName) + ',' + super.toString();
   }

   public String getTypeName() {
      return this.typeName;
   }

   public void setTypeName(String typeName) {
      this.typeName = typeName;
   }
}
