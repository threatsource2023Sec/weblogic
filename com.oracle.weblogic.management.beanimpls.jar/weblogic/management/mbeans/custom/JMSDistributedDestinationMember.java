package weblogic.management.mbeans.custom;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public abstract class JMSDistributedDestinationMember extends ConfigurationMBeanCustomizer {
   private DistributedDestinationMemberBean delegate;

   public JMSDistributedDestinationMember(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void useDelegates(DistributedDestinationMemberBean paramDelegate) {
      this.delegate = paramDelegate;
   }

   public int getWeight() {
      if (this.delegate == null) {
         Object retVal = this.getValue("Weight");
         return retVal != null && retVal instanceof Integer ? (Integer)retVal : 1;
      } else {
         return this.delegate.getWeight();
      }
   }

   public void setWeight(int value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("Weight", new Integer(value));
      } else {
         this.delegate.setWeight(value);
      }

   }

   public String getNotes() {
      if (this.delegate == null) {
         Object retVal = this.getValue("Notes");
         return retVal != null && retVal instanceof String ? (String)retVal : null;
      } else {
         return this.delegate.getNotes();
      }
   }

   public void setNotes(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("Notes", value);
      } else {
         this.delegate.setNotes(value);
      }

   }
}
