package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class IIOPSecurityDescriptorMBeanImpl extends XMLElementMBeanDelegate implements IIOPSecurityDescriptorMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_identityAssertion = false;
   private String identityAssertion;
   private boolean isSet_transportRequirements = false;
   private TransportRequirementsMBean transportRequirements;
   private boolean isSet_clientAuthentication = false;
   private String clientAuthentication;

   public String getIdentityAssertion() {
      return this.identityAssertion;
   }

   public void setIdentityAssertion(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.identityAssertion;
      this.identityAssertion = value;
      this.isSet_identityAssertion = value != null;
      this.checkChange("identityAssertion", old, this.identityAssertion);
   }

   public TransportRequirementsMBean getTransportRequirements() {
      return this.transportRequirements;
   }

   public void setTransportRequirements(TransportRequirementsMBean value) {
      TransportRequirementsMBean old = this.transportRequirements;
      this.transportRequirements = value;
      this.isSet_transportRequirements = value != null;
      this.checkChange("transportRequirements", old, this.transportRequirements);
   }

   public String getClientAuthentication() {
      return this.clientAuthentication;
   }

   public void setClientAuthentication(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.clientAuthentication;
      this.clientAuthentication = value;
      this.isSet_clientAuthentication = value != null;
      this.checkChange("clientAuthentication", old, this.clientAuthentication);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<iiop-security-descriptor");
      result.append(">\n");
      if (null != this.getTransportRequirements()) {
         result.append(this.getTransportRequirements().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getClientAuthentication()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<client-authentication>").append(this.getClientAuthentication()).append("</client-authentication>\n");
      }

      if (null != this.getIdentityAssertion()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<identity-assertion>").append(this.getIdentityAssertion()).append("</identity-assertion>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</iiop-security-descriptor>\n");
      return result.toString();
   }
}
