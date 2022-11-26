package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ReliableDeliveryMBeanImpl extends XMLElementMBeanDelegate implements ReliableDeliveryMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_persistDuration = false;
   private int persistDuration = 600;
   private boolean isSet_inOrderDelivery = false;
   private boolean inOrderDelivery = false;
   private boolean isSet_retryInterval = false;
   private int retryInterval = 10;
   private boolean isSet_duplicateElimination = false;
   private boolean duplicateElimination = true;
   private boolean isSet_retries = false;
   private int retries = 60;

   public int getPersistDuration() {
      return this.persistDuration;
   }

   public void setPersistDuration(int value) {
      int old = this.persistDuration;
      this.persistDuration = value;
      this.isSet_persistDuration = value != -1;
      this.checkChange("persistDuration", old, this.persistDuration);
   }

   public boolean isInOrderDelivery() {
      return this.inOrderDelivery;
   }

   public void setInOrderDelivery(boolean value) {
      boolean old = this.inOrderDelivery;
      this.inOrderDelivery = value;
      this.isSet_inOrderDelivery = true;
      this.checkChange("inOrderDelivery", old, this.inOrderDelivery);
   }

   public int getRetryInterval() {
      return this.retryInterval;
   }

   public void setRetryInterval(int value) {
      int old = this.retryInterval;
      this.retryInterval = value;
      this.isSet_retryInterval = value != -1;
      this.checkChange("retryInterval", old, this.retryInterval);
   }

   public boolean isDuplicateElimination() {
      return this.duplicateElimination;
   }

   public void setDuplicateElimination(boolean value) {
      boolean old = this.duplicateElimination;
      this.duplicateElimination = value;
      this.isSet_duplicateElimination = true;
      this.checkChange("duplicateElimination", old, this.duplicateElimination);
   }

   public int getRetries() {
      return this.retries;
   }

   public void setRetries(int value) {
      int old = this.retries;
      this.retries = value;
      this.isSet_retries = value != -1;
      this.checkChange("retries", old, this.retries);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<reliable-delivery");
      if (this.isSet_persistDuration) {
         result.append(" persist-duration=\"").append(String.valueOf(this.getPersistDuration())).append("\"");
      }

      if (this.isSet_duplicateElimination) {
         result.append(" duplicate-elimination=\"").append(String.valueOf(this.isDuplicateElimination())).append("\"");
      }

      if (this.isSet_inOrderDelivery) {
         result.append(" in-order-delivery=\"").append(String.valueOf(this.isInOrderDelivery())).append("\"");
      }

      if (this.isSet_retryInterval) {
         result.append(" retry-interval=\"").append(String.valueOf(this.getRetryInterval())).append("\"");
      }

      if (this.isSet_retries) {
         result.append(" retries=\"").append(String.valueOf(this.getRetries())).append("\"");
      }

      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</reliable-delivery>\n");
      return result.toString();
   }
}
