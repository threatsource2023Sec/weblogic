package weblogic.management.mbeans.custom;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.DistributedDestinationBean;
import weblogic.management.DistributedManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.JMSTemplateMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class JMSDistributedDestination extends ConfigurationMBeanCustomizer {
   private static final String TARGETS = "Targets";
   private static final long serialVersionUID = 5429833394456540954L;
   private transient DistributedDestinationBean delegate;
   private transient SubDeploymentMBean subDeployment;

   public JMSDistributedDestination(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void useDelegates(DistributedDestinationBean paramDelegate, SubDeploymentMBean paramSubDeployment) {
      this.delegate = paramDelegate;
      this.subDeployment = paramSubDeployment;
   }

   public TargetMBean[] getTargets() {
      if (this.subDeployment == null) {
         Object retVal = this.getValue("Targets");
         if (retVal == null) {
            return new TargetMBean[0];
         } else {
            if (!(retVal instanceof TargetMBean)) {
               if (!(retVal instanceof WebLogicMBean[])) {
                  return new TargetMBean[0];
               }

               WebLogicMBean[] webBeans = (WebLogicMBean[])((WebLogicMBean[])retVal);
               TargetMBean[] converted = new TargetMBean[webBeans.length];

               for(int lcv = 0; lcv < webBeans.length; ++lcv) {
                  WebLogicMBean webBean = webBeans[lcv];
                  if (!(webBean instanceof TargetMBean)) {
                     return new TargetMBean[0];
                  }

                  converted[lcv] = (TargetMBean)webBean;
               }

               retVal = converted;
            }

            return (TargetMBean[])((TargetMBean[])retVal);
         }
      } else {
         return this.subDeployment.getTargets();
      }
   }

   public void setTargets(TargetMBean[] targets) throws InvalidAttributeValueException, DistributedManagementException {
      if (this.subDeployment == null) {
         this.putValueNotify("Targets", targets);
      } else {
         this.subDeployment.setTargets(targets);
      }

   }

   public String getLoadBalancingPolicy() {
      if (this.delegate == null) {
         Object retVal = this.getValue("LoadBalancingPolicy");
         return retVal != null && retVal instanceof String ? (String)retVal : "Round-Robin";
      } else {
         return this.delegate.getLoadBalancingPolicy();
      }
   }

   public void setLoadBalancingPolicy(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("LoadBalancingPolicy", value);
      } else {
         this.delegate.setLoadBalancingPolicy(value);
      }

   }

   public JMSTemplateMBean getTemplate() {
      Object retVal = this.getValue("Template");
      return (JMSTemplateMBean)retVal;
   }

   public void setTemplate(JMSTemplateMBean value) throws InvalidAttributeValueException {
      this.putValue("Template", value);
   }

   public JMSTemplateMBean getJMSTemplate() {
      Object retVal = this.getValue("JMSTemplate");
      return (JMSTemplateMBean)retVal;
   }

   public void setJMSTemplate(JMSTemplateMBean value) throws InvalidAttributeValueException {
      this.putValue("JMSTemplate", value);
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

   public String getJNDIName() {
      if (this.delegate == null) {
         Object retVal = this.getValue("JNDIName");
         return retVal != null && retVal instanceof String ? (String)retVal : null;
      } else {
         return this.delegate.getJNDIName();
      }
   }

   public void setJNDIName(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("JNDIName", value);
      } else {
         this.delegate.setJNDIName(value);
      }

   }
}
