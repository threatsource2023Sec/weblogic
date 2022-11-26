package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class EntityClusteringMBeanImpl extends XMLElementMBeanDelegate implements EntityClusteringMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_homeLoadAlgorithm = false;
   private String homeLoadAlgorithm;
   private boolean isSet_homeCallRouterClassName = false;
   private String homeCallRouterClassName;
   private boolean isSet_useServersideStubs = false;
   private boolean useServersideStubs = false;
   private boolean isSet_homeIsClusterable = false;
   private boolean homeIsClusterable = true;

   public String getHomeLoadAlgorithm() {
      return this.homeLoadAlgorithm;
   }

   public void setHomeLoadAlgorithm(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.homeLoadAlgorithm;
      this.homeLoadAlgorithm = value;
      this.isSet_homeLoadAlgorithm = value != null;
      this.checkChange("homeLoadAlgorithm", old, this.homeLoadAlgorithm);
   }

   public String getHomeCallRouterClassName() {
      return this.homeCallRouterClassName;
   }

   public void setHomeCallRouterClassName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.homeCallRouterClassName;
      this.homeCallRouterClassName = value;
      this.isSet_homeCallRouterClassName = value != null;
      this.checkChange("homeCallRouterClassName", old, this.homeCallRouterClassName);
   }

   public boolean getUseServersideStubs() {
      return this.useServersideStubs;
   }

   public void setUseServersideStubs(boolean value) {
      boolean old = this.useServersideStubs;
      this.useServersideStubs = value;
      this.isSet_useServersideStubs = true;
      this.checkChange("useServersideStubs", old, this.useServersideStubs);
   }

   public boolean getHomeIsClusterable() {
      return this.homeIsClusterable;
   }

   public void setHomeIsClusterable(boolean value) {
      boolean old = this.homeIsClusterable;
      this.homeIsClusterable = value;
      this.isSet_homeIsClusterable = true;
      this.checkChange("homeIsClusterable", old, this.homeIsClusterable);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<entity-clustering");
      result.append(">\n");
      if (this.isSet_homeIsClusterable || !this.getHomeIsClusterable()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<home-is-clusterable>").append(ToXML.capitalize(Boolean.valueOf(this.getHomeIsClusterable()).toString())).append("</home-is-clusterable>\n");
      }

      if (null != this.getHomeLoadAlgorithm()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<home-load-algorithm>").append(this.getHomeLoadAlgorithm()).append("</home-load-algorithm>\n");
      }

      if (null != this.getHomeCallRouterClassName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<home-call-router-class-name>").append(this.getHomeCallRouterClassName()).append("</home-call-router-class-name>\n");
      }

      if (this.isSet_useServersideStubs || this.getUseServersideStubs()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<use-serverside-stubs>").append(ToXML.capitalize(Boolean.valueOf(this.getUseServersideStubs()).toString())).append("</use-serverside-stubs>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</entity-clustering>\n");
      return result.toString();
   }
}
