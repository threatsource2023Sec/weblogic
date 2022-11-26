package weblogic.management.mbeans.custom;

import weblogic.j2ee.descriptor.wl.DestinationKeyBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public final class JMSDestinationKey extends ConfigurationMBeanCustomizer {
   private transient DestinationKeyBean delegate;

   public JMSDestinationKey(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void useDelegates(DestinationKeyBean paramDelegate) {
      this.delegate = paramDelegate;
   }

   public String getProperty() {
      if (this.delegate != null && this.delegate.isSet("Property")) {
         return this.delegate.getProperty();
      } else {
         Object retVal = this.getValue("Property");
         return retVal != null && retVal instanceof String ? (String)retVal : "JMSMessageID";
      }
   }

   public void setProperty(String value) {
      this.putValue("Property", value);
      if (this.delegate != null) {
         this.delegate.setProperty(value);
      }

   }

   public String getKeyType() {
      if (this.delegate != null && this.delegate.isSet("KeyType")) {
         return this.delegate.getKeyType();
      } else {
         Object retVal = this.getValue("KeyType");
         return retVal != null && retVal instanceof String ? (String)retVal : "String";
      }
   }

   public void setKeyType(String value) {
      this.putValue("KeyType", value);
      if (this.delegate != null) {
         this.delegate.setKeyType(value);
      }

   }

   public String getDirection() {
      if (this.delegate != null && this.delegate.isSet("SortOrder")) {
         return this.delegate.getSortOrder();
      } else {
         Object retVal = this.getValue("Direction");
         return retVal != null && retVal instanceof String ? (String)retVal : "Ascending";
      }
   }

   public void setDirection(String value) {
      this.putValue("Direction", value);
      if (this.delegate != null) {
         this.delegate.setSortOrder(value);
      }

   }

   public String getNotes() {
      if (this.delegate != null && this.delegate.isSet("Notes")) {
         return this.delegate.getNotes();
      } else {
         Object retVal = this.getValue("Notes");
         return retVal != null && retVal instanceof String ? (String)retVal : null;
      }
   }

   public void setNotes(String value) {
      this.putValue("Notes", value);
      if (this.delegate != null) {
         this.delegate.setNotes(value);
      }

   }
}
