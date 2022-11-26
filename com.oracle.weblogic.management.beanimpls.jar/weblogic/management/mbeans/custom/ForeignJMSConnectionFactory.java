package weblogic.management.mbeans.custom;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.ForeignConnectionFactoryBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public class ForeignJMSConnectionFactory extends ForeignJNDIObject {
   private ForeignConnectionFactoryBean delegate;

   public ForeignJMSConnectionFactory(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void useDelegates(ForeignConnectionFactoryBean paramDelegate) {
      this.delegate = paramDelegate;
      super.useDelegates(this.delegate);
   }

   public String getUsername() {
      if (this.delegate == null) {
         Object retVal = this.getValue("Username");
         return retVal != null && retVal instanceof String ? (String)retVal : null;
      } else {
         return this.delegate.getUsername();
      }
   }

   public void setUsername(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("Username", value);
      } else {
         this.delegate.setUsername(value);
      }

   }

   public String getPassword() {
      if (this.delegate == null) {
         Object retVal = this.getValue("Password");
         return retVal != null && retVal instanceof String ? (String)retVal : null;
      } else {
         return this.delegate.getPassword();
      }
   }

   public void setPassword(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("Password", value);
      } else {
         this.delegate.setPassword(value);
      }

   }
}
