package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class MessageDrivenDestinationMBeanImpl extends XMLElementMBeanDelegate implements MessageDrivenDestinationMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_subscriptionDurability = false;
   private String subscriptionDurability;
   private boolean isSet_destinationType = false;
   private String destinationType;

   public String getSubscriptionDurability() {
      return this.subscriptionDurability;
   }

   public void setSubscriptionDurability(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.subscriptionDurability;
      this.subscriptionDurability = value;
      this.isSet_subscriptionDurability = value != null;
      this.checkChange("subscriptionDurability", old, this.subscriptionDurability);
   }

   public String getDestinationType() {
      return this.destinationType;
   }

   public void setDestinationType(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.destinationType;
      this.destinationType = value;
      this.isSet_destinationType = value != null;
      this.checkChange("destinationType", old, this.destinationType);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<message-driven-destination");
      result.append(">\n");
      if (null != this.getDestinationType()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<destination-type>").append(this.getDestinationType()).append("</destination-type>\n");
      }

      if (null != this.getSubscriptionDurability()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<subscription-durability>").append(this.getSubscriptionDurability()).append("</subscription-durability>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</message-driven-destination>\n");
      return result.toString();
   }
}
