package weblogic.management.mbeans.custom;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.ForeignJNDIObjectBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public abstract class ForeignJNDIObject extends ConfigurationMBeanCustomizer {
   private ForeignJNDIObjectBean delegate;

   public ForeignJNDIObject(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void useDelegates(ForeignJNDIObjectBean paramDelegate) {
      this.delegate = paramDelegate;
   }

   public String getLocalJNDIName() {
      if (this.delegate == null) {
         Object retVal = this.getValue("LocalJNDIName");
         return retVal != null && retVal instanceof String ? (String)retVal : null;
      } else {
         return this.delegate.getLocalJNDIName();
      }
   }

   public void setLocalJNDIName(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("LocalJNDIName", value);
      } else {
         this.delegate.setLocalJNDIName(value);
      }

   }

   public String getRemoteJNDIName() {
      if (this.delegate == null) {
         Object retVal = this.getValue("RemoteJNDIName");
         return retVal != null && retVal instanceof String ? (String)retVal : null;
      } else {
         return this.delegate.getRemoteJNDIName();
      }
   }

   public void setRemoteJNDIName(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("RemoteJNDIName", value);
      } else {
         this.delegate.setRemoteJNDIName(value);
      }

   }
}
