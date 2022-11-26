package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class TransportRequirementsMBeanImpl extends XMLElementMBeanDelegate implements TransportRequirementsMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_confidentiality = false;
   private String confidentiality;
   private boolean isSet_clientCertAuthentication = false;
   private String clientCertAuthentication;
   private boolean isSet_integrity = false;
   private String integrity;

   public String getConfidentiality() {
      return this.confidentiality;
   }

   public void setConfidentiality(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.confidentiality;
      this.confidentiality = value;
      this.isSet_confidentiality = value != null;
      this.checkChange("confidentiality", old, this.confidentiality);
   }

   public String getClientCertAuthentication() {
      return this.clientCertAuthentication;
   }

   public void setClientCertAuthentication(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.clientCertAuthentication;
      this.clientCertAuthentication = value;
      this.isSet_clientCertAuthentication = value != null;
      this.checkChange("clientCertAuthentication", old, this.clientCertAuthentication);
   }

   public String getIntegrity() {
      return this.integrity;
   }

   public void setIntegrity(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.integrity;
      this.integrity = value;
      this.isSet_integrity = value != null;
      this.checkChange("integrity", old, this.integrity);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<transport-requirements");
      result.append(">\n");
      if (null != this.getIntegrity()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<integrity>").append(this.getIntegrity()).append("</integrity>\n");
      }

      if (null != this.getConfidentiality()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<confidentiality>").append(this.getConfidentiality()).append("</confidentiality>\n");
      }

      if (null != this.getClientCertAuthentication()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<client-cert-authentication>").append(this.getClientCertAuthentication()).append("</client-cert-authentication>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</transport-requirements>\n");
      return result.toString();
   }
}
