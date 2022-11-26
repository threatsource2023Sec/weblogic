package weblogic.management.mbeans.custom;

import weblogic.j2ee.descriptor.wl.DistributedTopicBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSDistributedTopicMBean;
import weblogic.management.configuration.JMSDistributedTopicMemberMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.utils.ArrayUtils;

public class JMSDistributedTopic extends JMSDistributedDestination {
   static final long serialVersionUID = 175403281312376358L;
   private DistributedTopicBean delegate;

   public JMSDistributedTopic(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void useDelegates(DistributedTopicBean paramDelegate, SubDeploymentMBean subDeployment) {
      this.delegate = paramDelegate;
      super.useDelegates(this.delegate, subDeployment);
   }

   public JMSDistributedTopicMemberMBean createJMSDistributedTopicMember(String name, JMSDistributedTopicMemberMBean toClone) {
      try {
         return (JMSDistributedTopicMemberMBean)this.getMbean().createChildCopyIncludingObsolete("JMSDistributedTopicMember", toClone);
      } catch (IllegalArgumentException var4) {
         throw new Error(var4);
      }
   }

   public void destroyJMSDistributedTopicMember(String name, JMSDistributedTopicMemberMBean toDelete) {
      this.removeMember(toDelete);
   }

   public JMSDistributedTopicMemberMBean[] getMembers() {
      return ((JMSDistributedTopicMBean)this.getMbean()).getJMSDistributedTopicMembers();
   }

   /** @deprecated */
   @Deprecated
   public void setMembers(JMSDistributedTopicMemberMBean[] members) {
      ArrayUtils.computeDiff(this.getMembers(), members, new ArrayUtils.DiffHandler() {
         public void addObject(Object toAdd) {
            JMSDistributedTopic.this.addMember((JMSDistributedTopicMemberMBean)toAdd);
         }

         public void removeObject(Object toRemove) {
            JMSDistributedTopic.this.removeMember((JMSDistributedTopicMemberMBean)toRemove);
         }
      });
   }

   /** @deprecated */
   @Deprecated
   public boolean addMember(JMSDistributedTopicMemberMBean member) {
      JMSDistributedTopicMBean bean = (JMSDistributedTopicMBean)this.getMbean();
      if (bean.lookupJMSDistributedTopicMember(member.getName()) != null) {
         return true;
      } else {
         JMSDistributedTopicMemberMBean var10000 = (JMSDistributedTopicMemberMBean)this.getMbean().createChildCopy("JMSDistributedTopicMember", member);
         DomainMBean domain = (DomainMBean)bean.getParentBean();
         domain.destroyJMSDistributedTopicMember(member);
         return true;
      }
   }

   /** @deprecated */
   @Deprecated
   public boolean removeMember(JMSDistributedTopicMemberMBean member) {
      JMSDistributedTopicMBean bean = (JMSDistributedTopicMBean)this.getMbean();
      DomainMBean domain = (DomainMBean)bean.getParentBean();
      if (bean.lookupJMSDistributedTopicMember(member.getName()) == null) {
         return true;
      } else {
         domain.createJMSDistributedTopicMember(member.getName(), member);
         bean.destroyJMSDistributedTopicMember(member);
         return true;
      }
   }
}
