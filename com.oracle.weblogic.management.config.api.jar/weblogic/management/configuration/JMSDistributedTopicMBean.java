package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.DistributedTopicBean;
import weblogic.management.DistributedManagementException;

/** @deprecated */
@Deprecated
public interface JMSDistributedTopicMBean extends JMSDistributedDestinationMBean {
   JMSDistributedTopicMemberMBean[] getMembers();

   void setMembers(JMSDistributedTopicMemberMBean[] var1) throws InvalidAttributeValueException;

   boolean addMember(JMSDistributedTopicMemberMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   boolean removeMember(JMSDistributedTopicMemberMBean var1) throws InvalidAttributeValueException, DistributedManagementException;

   void useDelegates(DistributedTopicBean var1, SubDeploymentMBean var2);

   JMSDistributedTopicMemberMBean[] getJMSDistributedTopicMembers();

   JMSDistributedTopicMemberMBean createJMSDistributedTopicMember(String var1);

   JMSDistributedTopicMemberMBean createJMSDistributedTopicMember(String var1, JMSDistributedTopicMemberMBean var2);

   void destroyJMSDistributedTopicMember(JMSDistributedTopicMemberMBean var1);

   void destroyJMSDistributedTopicMember(String var1, JMSDistributedTopicMemberMBean var2);

   JMSDistributedTopicMemberMBean lookupJMSDistributedTopicMember(String var1);
}
