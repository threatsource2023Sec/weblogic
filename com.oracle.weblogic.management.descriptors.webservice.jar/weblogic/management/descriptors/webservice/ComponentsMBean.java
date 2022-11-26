package weblogic.management.descriptors.webservice;

import weblogic.management.descriptors.XMLElementMBean;

public interface ComponentsMBean extends XMLElementMBean {
   boolean getUseMultiplePorts();

   void setUseMultiplePorts(boolean var1);

   boolean getUsePortTypeName();

   void setUsePortTypeName(boolean var1);

   StatelessEJBMBean[] getStatelessEJBs();

   void setStatelessEJBs(StatelessEJBMBean[] var1);

   void addStatelessEJB(StatelessEJBMBean var1);

   void removeStatelessEJB(StatelessEJBMBean var1);

   JavaClassMBean[] getJavaClassComponents();

   void setJavaClassComponents(JavaClassMBean[] var1);

   void addJavaClassComponent(JavaClassMBean var1);

   void removeJavaClassComponent(JavaClassMBean var1);

   StatefulJavaClassMBean[] getStatefulJavaClassComponents();

   void setStatefulJavaClassComponents(StatefulJavaClassMBean[] var1);

   void addStatefulJavaClassComponent(StatefulJavaClassMBean var1);

   void removeStatefulJavaClassComponent(StatefulJavaClassMBean var1);

   JMSSendDestinationMBean[] getJMSSendDestinations();

   void setJMSSendDestinations(JMSSendDestinationMBean[] var1);

   void addJMSSendDestination(JMSSendDestinationMBean var1);

   void removeJMSSendDestination(JMSSendDestinationMBean var1);

   JMSReceiveTopicMBean[] getJMSReceiveTopics();

   void setJMSReceiveTopics(JMSReceiveTopicMBean[] var1);

   void addJMSReceiveTopic(JMSReceiveTopicMBean var1);

   void removeJMSReceiveTopic(JMSReceiveTopicMBean var1);

   JMSReceiveQueueMBean[] getJMSReceiveQueues();

   void setJMSReceiveQueues(JMSReceiveQueueMBean[] var1);

   void addJMSReceiveQueue(JMSReceiveQueueMBean var1);

   void removeJMSReceiveQueue(JMSReceiveQueueMBean var1);
}
