package com.bea.common.security.store.data;

import com.bea.common.security.utils.Pair;
import java.util.Map;

public class WLSCredMapCollectionInfoId extends DomainRealmScopeId {
   public static final String WLSCOLLECTIONNAME = "wlsCollectionName";
   public String wlsCollectionName;

   public WLSCredMapCollectionInfoId() {
   }

   public WLSCredMapCollectionInfoId(String binding) {
      super(binding);
      Map p = ApplicationIdUtil.parse(binding);
      this.wlsCollectionName = (String)p.get("wlsCollectionName");
   }

   public WLSCredMapCollectionInfoId(String domainName, String realmName, String wlsCollectionName) {
      super(domainName, realmName);
      this.wlsCollectionName = wlsCollectionName;
   }

   public WLSCredMapCollectionInfoId(WLSCredMapCollectionInfo obj) {
      super((DomainRealmScope)obj);
      this.wlsCollectionName = obj.getWlsCollectionName();
   }

   public String toString() {
      return "wlsCollectionName=" + ApplicationIdUtil.encode(this.wlsCollectionName) + ',' + super.toString();
   }

   protected Pair prepareQuery() {
      Pair p = super.prepareQuery();
      Map params = (Map)p.getRight();
      params.put("wlsCollectionName", this.wlsCollectionName);
      StringBuilder filter = new StringBuilder();
      filter.append((String)p.getLeft());
      filter.append(" && this.wlsCollectionName == wlsCollectionName");
      return new Pair(filter.toString(), params);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof WLSCredMapCollectionInfoId)) {
         return false;
      } else {
         WLSCredMapCollectionInfoId o = (WLSCredMapCollectionInfoId)other;
         return this.wlsCollectionName == o.wlsCollectionName || this.wlsCollectionName != null && this.wlsCollectionName.equals(o.wlsCollectionName);
      }
   }

   public int hashCode() {
      return (this.wlsCollectionName != null ? this.wlsCollectionName.hashCode() : 0) ^ super.hashCode();
   }

   public String getWlsCollectionName() {
      return this.wlsCollectionName;
   }

   public void setWlsCollectionName(String wlsCollectionName) {
      this.wlsCollectionName = wlsCollectionName;
   }
}
