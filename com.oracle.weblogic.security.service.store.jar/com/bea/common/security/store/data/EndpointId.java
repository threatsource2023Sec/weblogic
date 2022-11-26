package com.bea.common.security.store.data;

import com.bea.common.security.utils.Pair;
import java.util.Map;

public class EndpointId extends DomainRealmScopeId {
   private String bindingLocation;
   private String partnerName;
   private String bindingType;
   private String serviceType;

   public String getBindingType() {
      return this.bindingType;
   }

   public void setBindingType(String bindingType) {
      this.bindingType = bindingType;
   }

   public String getPartnerName() {
      return this.partnerName;
   }

   public void setPartnerName(String partnerCN) {
      this.partnerName = partnerCN;
   }

   public String getServiceType() {
      return this.serviceType;
   }

   public void setServiceType(String serviceType) {
      this.serviceType = serviceType;
   }

   public EndpointId() {
   }

   public EndpointId(String domainName, String realmName, String bindingLocation, String bindingType, String serviceType, String partnerCN) {
      super(domainName, realmName);
      this.bindingLocation = bindingLocation;
      this.bindingType = bindingType;
      this.serviceType = serviceType;
      this.partnerName = partnerCN;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof EndpointId)) {
         return false;
      } else {
         EndpointId o = (EndpointId)other;
         boolean localFlag = this.bindingLocation == o.bindingLocation || this.bindingLocation != null && this.bindingLocation.equals(o.bindingLocation);
         boolean typeFlag = this.bindingType == o.bindingType || this.bindingType != null && this.bindingType.equals(o.bindingType);
         boolean serviceTypeFlag = this.serviceType == o.serviceType || this.serviceType != null && this.serviceType.equals(o.serviceType);
         boolean partnerNameFlag = this.partnerName == o.partnerName || this.partnerName != null && this.partnerName.equals(o.partnerName);
         return localFlag && typeFlag && serviceTypeFlag && partnerNameFlag;
      }
   }

   public int hashCode() {
      return (this.bindingLocation != null ? this.bindingLocation.hashCode() : 0) ^ super.hashCode();
   }

   public String toString() {
      return "location=" + ApplicationIdUtil.encode(this.bindingLocation) + ',' + super.toString();
   }

   public String getBindingLocation() {
      return this.bindingLocation;
   }

   public void setBindingLocation(String bindingLocation) {
      this.bindingLocation = bindingLocation;
   }

   protected Pair prepareQuery() {
      Pair p = super.prepareQuery();
      Map params = (Map)p.getRight();
      params.put("bindingLocation", this.bindingLocation);
      params.put("bindingType", this.bindingType);
      params.put("serviceType", this.serviceType);
      params.put("partnerName", this.partnerName);
      StringBuilder filter = new StringBuilder();
      filter.append((String)p.getLeft());
      filter.append(" && this.bindingLocation == bindingLocation && this.bindingType == bindingType && this.serviceType == serviceType && this.partnerName == partnerName");
      return new Pair(filter.toString(), params);
   }
}
