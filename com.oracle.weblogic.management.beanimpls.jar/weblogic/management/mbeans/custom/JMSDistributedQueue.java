package weblogic.management.mbeans.custom;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.DistributedQueueBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSDistributedQueueMBean;
import weblogic.management.configuration.JMSDistributedQueueMemberMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.utils.ArrayUtils;

public class JMSDistributedQueue extends JMSDistributedDestination {
   static final long serialVersionUID = -5251076212640474307L;
   private DistributedQueueBean delegate;

   public JMSDistributedQueue(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void useDelegates(DistributedQueueBean paramDelegate, SubDeploymentMBean subDeployment) {
      this.delegate = paramDelegate;
      super.useDelegates(this.delegate, subDeployment);
   }

   public JMSDistributedQueueMemberMBean createJMSDistributedQueueMember(String name, JMSDistributedQueueMemberMBean toClone) {
      try {
         return (JMSDistributedQueueMemberMBean)((JMSDistributedQueueMemberMBean)this.getMbean().createChildCopy("JMSDistributedQueueMember", toClone));
      } catch (IllegalArgumentException var4) {
         throw new Error(var4);
      }
   }

   public void destroyJMSDistributedQueueMember(String name, JMSDistributedQueueMemberMBean toDelete) {
      this.removeMember(toDelete);
   }

   public JMSDistributedQueueMemberMBean[] getMembers() {
      return ((JMSDistributedQueueMBean)this.getMbean()).getJMSDistributedQueueMembers();
   }

   /** @deprecated */
   @Deprecated
   public void setMembers(JMSDistributedQueueMemberMBean[] members) {
      ArrayUtils.computeDiff(this.getMembers(), members, new ArrayUtils.DiffHandler() {
         public void addObject(Object toAdd) {
            JMSDistributedQueue.this.addMember((JMSDistributedQueueMemberMBean)toAdd);
         }

         public void removeObject(Object toRemove) {
            JMSDistributedQueue.this.removeMember((JMSDistributedQueueMemberMBean)toRemove);
         }
      });
   }

   /** @deprecated */
   @Deprecated
   public boolean addMember(JMSDistributedQueueMemberMBean member) {
      JMSDistributedQueueMBean bean = (JMSDistributedQueueMBean)this.getMbean();
      if (bean.lookupJMSDistributedQueueMember(member.getName()) != null) {
         return true;
      } else {
         JMSDistributedQueueMemberMBean var10000 = (JMSDistributedQueueMemberMBean)this.getMbean().createChildCopy("JMSDistributedQueueMember", member);
         DomainMBean domain = (DomainMBean)bean.getParentBean();
         domain.destroyJMSDistributedQueueMember(member);
         return true;
      }
   }

   /** @deprecated */
   @Deprecated
   public boolean removeMember(JMSDistributedQueueMemberMBean member) {
      JMSDistributedQueueMBean bean = (JMSDistributedQueueMBean)this.getMbean();
      DomainMBean domain = (DomainMBean)bean.getParentBean();
      if (bean.lookupJMSDistributedQueueMember(member.getName()) == null) {
         return true;
      } else {
         domain.createJMSDistributedQueueMember(member.getName(), member);
         bean.destroyJMSDistributedQueueMember(member);
         return true;
      }
   }

   public int getForwardDelay() {
      if (this.delegate == null) {
         Object retVal = this.getValue("ForwardDelay");
         return retVal != null && retVal instanceof Integer ? (Integer)retVal : -1;
      } else {
         return this.delegate.getForwardDelay();
      }
   }

   public void setForwardDelay(int value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("ForwardDelay", new Integer(value));
      } else {
         this.delegate.setForwardDelay(value);
      }

   }

   public boolean getResetDeliveryCountOnFoward() {
      if (this.delegate == null) {
         Object retVal = this.getValue("ResetDeliveryCountOnForward");
         return retVal != null && retVal instanceof Boolean ? (Boolean)retVal : true;
      } else {
         return this.delegate.getResetDeliveryCountOnForward();
      }
   }

   public void setResetDeliveryCountOnForward(boolean value) {
      if (this.delegate == null) {
         this.putValue("ResetDeliveryCountOnForward", new Boolean(value));
      } else {
         this.delegate.setResetDeliveryCountOnForward(value);
      }

   }
}
