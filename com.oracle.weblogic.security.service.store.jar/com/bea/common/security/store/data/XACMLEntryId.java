package com.bea.common.security.store.data;

import com.bea.common.security.utils.Pair;
import java.util.Map;

public abstract class XACMLEntryId extends XACMLTypeScopeId {
   public static final String CN = "cn";
   public static final String XACMLVERSION = "xacmlVersion";
   private String cn;
   private String xacmlVersion;

   public XACMLEntryId() {
   }

   public XACMLEntryId(String domainName, String realmName, String typeName, String cn, String xacmlVersion) {
      super(domainName, realmName, typeName);
      this.cn = cn;
      this.xacmlVersion = xacmlVersion;
   }

   public XACMLEntryId(String binding) {
      super(binding);
      Map p = ApplicationIdUtil.parse(binding);
      this.cn = (String)p.get("cn");
      this.xacmlVersion = (String)p.get("xacmlVersion");
   }

   public XACMLEntryId(XACMLEntry obj) {
      super((XACMLTypeScope)obj);
      this.cn = obj.getCn();
      this.xacmlVersion = obj.getXacmlVersion();
   }

   protected Pair prepareQuery() {
      Pair p = super.prepareQuery();
      Map params = (Map)p.getRight();
      params.put("cn", this.cn);
      params.put("xacmlVersion", this.xacmlVersion);
      StringBuilder filter = new StringBuilder();
      filter.append((String)p.getLeft());
      filter.append(" && this.cn == cn && this.xacmlVersion == xacmlVersion");
      return new Pair(filter.toString(), params);
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!super.equals(other)) {
         return false;
      } else if (!(other instanceof XACMLEntryId)) {
         return false;
      } else {
         XACMLEntryId o = (XACMLEntryId)other;
         return this.cn == o.cn || this.cn != null && this.cn.equals(o.cn) && this.xacmlVersion == o.xacmlVersion || this.xacmlVersion != null && this.xacmlVersion.equals(o.xacmlVersion);
      }
   }

   public int hashCode() {
      return (this.cn != null ? this.cn.hashCode() : 0) ^ (this.xacmlVersion != null ? this.xacmlVersion.hashCode() : 0) ^ super.hashCode();
   }

   public String toString() {
      return "cn=" + ApplicationIdUtil.encode(this.cn) + ',' + "xacmlVersion" + '=' + ApplicationIdUtil.encode(this.xacmlVersion) + ',' + super.toString();
   }

   public String getCn() {
      return this.cn;
   }

   public void setCn(String cn) {
      this.cn = cn;
   }

   public String getXacmlVersion() {
      return this.xacmlVersion;
   }

   public void setXacmlVersion(String xacmlVersion) {
      this.xacmlVersion = xacmlVersion;
   }
}
