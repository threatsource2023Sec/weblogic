package weblogic.management.mbeans.custom;

import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;

public final class JMSTopic extends JMSDestination {
   private static final long serialVersionUID = 6209479225384067069L;
   private long _creationTime = 1L;
   private transient TopicBean delegate;

   public JMSTopic(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void setCreationTime(long t) {
      this._creationTime = t;
   }

   public long getCreationTime() {
      return this._creationTime;
   }

   public void useDelegates(DomainMBean domain, JMSBean interopModule, TopicBean paramDelegate) {
      this.delegate = paramDelegate;
      super.useDelegates(domain, interopModule, this.delegate);
   }

   public String getMulticastAddress() {
      if (this.delegate == null) {
         Object retVal = this.getValue("MulticastAddress");
         return retVal != null && retVal instanceof String ? (String)retVal : null;
      } else {
         return this.delegate.getMulticast().getMulticastAddress();
      }
   }

   public void setMulticastAddress(String value) {
      if (this.delegate == null) {
         this.putValue("MulticastAddress", value);
      } else {
         this.delegate.getMulticast().setMulticastAddress(value);
      }

   }

   public int getMulticastTTL() {
      if (this.delegate == null) {
         Object retVal = this.getValue("MulticastTTL");
         return retVal != null && retVal instanceof Integer ? (Integer)retVal : 1;
      } else {
         return this.delegate.getMulticast().getMulticastTimeToLive();
      }
   }

   public void setMulticastTTL(int value) {
      if (this.delegate == null) {
         this.putValue("MulticastTTL", new Integer(value));
      } else {
         this.delegate.getMulticast().setMulticastTimeToLive(value);
      }

   }

   public int getMulticastPort() {
      if (this.delegate == null) {
         Object retVal = this.getValue("MulticastPort");
         return retVal != null && retVal instanceof Integer ? (Integer)retVal : 6001;
      } else {
         return this.delegate.getMulticast().getMulticastPort();
      }
   }

   public void setMulticastPort(int value) {
      if (this.delegate == null) {
         this.putValue("MulticastPort", new Integer(value));
      } else {
         this.delegate.getMulticast().setMulticastPort(value);
      }

   }
}
