package weblogic.management.mbeans.custom;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSTemplateMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public abstract class JMSDestination extends JMSDestCommon {
   private transient DestinationBean delegate;
   private String localJNDIName;
   private boolean localJNDIReplicated = true;

   public JMSDestination(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void useDelegates(DomainMBean domain, JMSBean interopModule, DestinationBean paramDelegate) {
      this.delegate = paramDelegate;
      super.useDelegates(domain, interopModule, this.delegate);
   }

   public JMSTemplateMBean getTemplate() {
      if (this.delegate == null) {
         Object retVal = this.getValue("Template");
         return retVal != null && retVal instanceof JMSTemplateMBean ? (JMSTemplateMBean)retVal : null;
      } else {
         TemplateBean template = this.delegate.getTemplate();
         String templateName = template == null ? null : template.getName();
         return templateName == null ? null : this.domain.lookupJMSTemplate(templateName);
      }
   }

   public void setTemplate(JMSTemplateMBean value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("Template", value);
      } else {
         String name = value == null ? null : value.getName();
         if (name == null) {
            this.delegate.setTemplate((TemplateBean)null);
         } else {
            TemplateBean template = this.interopModule.lookupTemplate(name);
            if (template == null) {
               throw new InvalidAttributeValueException("Could find the template " + name + " in the interop module while setting the template attribute of a destination");
            }

            this.delegate.setTemplate(template);
         }
      }

   }

   public String getJNDIName() {
      if (this.delegate == null) {
         return this.localJNDIName;
      } else {
         return this.localJNDIReplicated ? this.delegate.getJNDIName() : this.delegate.getLocalJNDIName();
      }
   }

   public void setJNDIName(String name) {
      this.localJNDIName = name;
      if (this.delegate != null) {
         if (this.localJNDIReplicated) {
            this.delegate.setJNDIName(this.localJNDIName);
         } else {
            this.delegate.setLocalJNDIName(this.localJNDIName);
         }

      }
   }

   public boolean isJNDINameReplicated() {
      return this.localJNDIReplicated;
   }

   public void setJNDINameReplicated(boolean replicated) {
      boolean originalValue = this.localJNDIReplicated;
      this.localJNDIReplicated = replicated;
      if (this.delegate != null && this.localJNDIReplicated != originalValue) {
         if (this.localJNDIReplicated) {
            this.delegate.setLocalJNDIName((String)null);
            this.delegate.setJNDIName(this.localJNDIName);
         } else {
            this.delegate.setJNDIName((String)null);
            this.delegate.setLocalJNDIName(this.localJNDIName);
         }

      }
   }

   public String getStoreEnabled() {
      if (this.delegate == null) {
         Object retVal = this.getValue("StoreEnabled");
         return retVal != null && retVal instanceof String ? (String)retVal : "default";
      } else {
         String deliveryModeString = this.delegate.getDeliveryParamsOverrides().getDeliveryMode();
         if (deliveryModeString.equals("No-Delivery")) {
            return "default";
         } else {
            return deliveryModeString.equals("Persistent") ? "true" : "false";
         }
      }
   }

   public void setStoreEnabled(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("StoreEnabled", value);
      }

   }
}
