package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.DistributedQueueBean;
import weblogic.management.DistributedManagementException;

/** @deprecated */
@Deprecated
public interface JMSDistributedQueueMBean extends JMSDistributedDestinationMBean {
   JMSDistributedQueueMemberMBean[] getMembers();

   void setMembers(JMSDistributedQueueMemberMBean[] var1) throws InvalidAttributeValueException;

   boolean addMember(JMSDistributedQueueMemberMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean removeMember(JMSDistributedQueueMemberMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   void useDelegates(DistributedQueueBean var1, SubDeploymentMBean var2);

   JMSDistributedQueueMemberMBean[] getJMSDistributedQueueMembers();

   JMSDistributedQueueMemberMBean createJMSDistributedQueueMember(String var1);

   JMSDistributedQueueMemberMBean createJMSDistributedQueueMember(String var1, JMSDistributedQueueMemberMBean var2);

   void destroyJMSDistributedQueueMember(JMSDistributedQueueMemberMBean var1);

   void destroyJMSDistributedQueueMember(String var1, JMSDistributedQueueMemberMBean var2);

   JMSDistributedQueueMemberMBean lookupJMSDistributedQueueMember(String var1);

   int getForwardDelay();

   void setForwardDelay(int var1) throws InvalidAttributeValueException;

   boolean getResetDeliveryCountOnForward();

   void setResetDeliveryCountOnForward(boolean var1) throws InvalidAttributeValueException;
}
