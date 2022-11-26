package weblogic.jms.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.ClientParamsBean;
import weblogic.j2ee.descriptor.wl.DefaultDeliveryParamsBean;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.DestinationKeyBean;
import weblogic.j2ee.descriptor.wl.DistributedDestinationBean;
import weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean;
import weblogic.j2ee.descriptor.wl.DistributedQueueBean;
import weblogic.j2ee.descriptor.wl.DistributedTopicBean;
import weblogic.j2ee.descriptor.wl.FlowControlParamsBean;
import weblogic.j2ee.descriptor.wl.ForeignConnectionFactoryBean;
import weblogic.j2ee.descriptor.wl.ForeignDestinationBean;
import weblogic.j2ee.descriptor.wl.ForeignJNDIObjectBean;
import weblogic.j2ee.descriptor.wl.ForeignServerBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean;
import weblogic.j2ee.descriptor.wl.LoadBalancingParamsBean;
import weblogic.j2ee.descriptor.wl.PropertyBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.j2ee.descriptor.wl.QuotaBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.j2ee.descriptor.wl.TransactionParamsBean;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSLogger;
import weblogic.management.DistributedManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ForeignJMSConnectionFactoryMBean;
import weblogic.management.configuration.ForeignJMSDestinationMBean;
import weblogic.management.configuration.ForeignJMSServerMBean;
import weblogic.management.configuration.ForeignJNDIObjectMBean;
import weblogic.management.configuration.JMSConnectionFactoryMBean;
import weblogic.management.configuration.JMSConstants;
import weblogic.management.configuration.JMSDestinationKeyMBean;
import weblogic.management.configuration.JMSDestinationMBean;
import weblogic.management.configuration.JMSDistributedDestinationMBean;
import weblogic.management.configuration.JMSDistributedDestinationMemberMBean;
import weblogic.management.configuration.JMSDistributedQueueMBean;
import weblogic.management.configuration.JMSDistributedQueueMemberMBean;
import weblogic.management.configuration.JMSDistributedTopicMBean;
import weblogic.management.configuration.JMSDistributedTopicMemberMBean;
import weblogic.management.configuration.JMSQueueMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.JMSTemplateMBean;
import weblogic.management.configuration.JMSTopicMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.UpdateException;

public abstract class MBeanConverter {
   private static final int STORE_DEFAULT = 0;
   private static final int STORE_TRUE = 1;
   private static final int STORE_FALSE = 2;
   private static final int DMO_UNSET = 0;
   private static final int DMO_PERSISTENT = 1;
   private static final int DMO_NONPERSISTENT = 2;

   public static JMSConnectionFactoryBean addJMSConnectionFactory(JMSBean parent, JMSSystemResourceMBean deployable, JMSConnectionFactoryMBean mbean, String factoryName, TargetMBean[] targets) {
      JMSConnectionFactoryBean cf;
      if ((cf = parent.lookupConnectionFactory(factoryName)) != null) {
         return cf;
      } else {
         cf = parent.createConnectionFactory(factoryName);
         fillInJMSConnectionFactory(deployable, cf, mbean, targets);
         return cf;
      }
   }

   private static void fillInJMSConnectionFactory(JMSSystemResourceMBean deployable, JMSConnectionFactoryBean cf, JMSConnectionFactoryMBean mbean, TargetMBean[] targets) {
      if (mbean.getNotes() != null) {
         cf.setNotes(mbean.getNotes());
      }

      String sValue = mbean.getJNDIName();
      if (sValue != null) {
         cf.setJNDIName(sValue);
      }

      if (deployable != null) {
         SubDeploymentMBean smut;
         if ((smut = deployable.lookupSubDeployment(cf.getName())) == null) {
            smut = deployable.createSubDeployment(cf.getName());
         }

         try {
            smut.setTargets(targets);
         } catch (InvalidAttributeValueException var7) {
            throw new AssertionError("ERROR: Could not set the targets of JMS connection factory " + cf.getName() + " due to " + var7);
         } catch (DistributedManagementException var8) {
            throw new AssertionError("WARN: Could not set the targets of JMS connection factory " + cf.getName() + " due to " + var8);
         }
      }

      fillInDefaultDeliveryParams(cf, mbean);
      fillInClientParams(cf, mbean);
      fillInTransactionParams(cf, mbean);
      fillInFlowControlParams(cf, mbean);
      fillInLoadBalancingParams(cf, mbean);
   }

   private static void fillInDefaultDeliveryParams(JMSConnectionFactoryBean cf, JMSConnectionFactoryMBean mbean) {
      DefaultDeliveryParamsBean ddp = cf.getDefaultDeliveryParams();
      String sValue = mbean.getDefaultDeliveryMode();
      if (sValue != null && !sValue.equals("Persistent")) {
         ddp.setDefaultDeliveryMode(sValue);
      }

      int iValue = mbean.getDefaultPriority();
      if (iValue != 4) {
         ddp.setDefaultPriority(iValue);
      }

      long lValue = mbean.getDefaultTimeToDeliver();
      if (lValue != 0L) {
         ddp.setDefaultTimeToDeliver((new Long(lValue)).toString());
      }

      if ((lValue = mbean.getDefaultTimeToLive()) != 0L) {
         ddp.setDefaultTimeToLive(lValue);
      }

      if ((lValue = mbean.getSendTimeout()) != 10L) {
         ddp.setSendTimeout(lValue);
      }

      if ((lValue = mbean.getDefaultRedeliveryDelay()) != 0L) {
         ddp.setDefaultRedeliveryDelay(lValue);
      }

   }

   private static void fillInClientParams(JMSConnectionFactoryBean cf, JMSConnectionFactoryMBean mbean) {
      ClientParamsBean cp = cf.getClientParams();
      String sValue = mbean.getClientId();
      if (sValue != null) {
         cp.setClientId(sValue);
      }

      int iValue = mbean.getMessagesMaximum();
      if (iValue != 10) {
         cp.setMessagesMaximum(iValue);
      }

      sValue = mbean.getOverrunPolicy();
      if (sValue != null && !sValue.equals("KeepOld")) {
         cp.setMulticastOverrunPolicy(sValue);
      }

      sValue = mbean.getAcknowledgePolicy();
      if (sValue != null && !sValue.equals("All")) {
         cp.setAcknowledgePolicy(sValue);
      }

      boolean bValue = mbean.getAllowCloseInOnMessage();
      if (bValue) {
         cp.setAllowCloseInOnMessage(bValue);
      }

   }

   private static void fillInTransactionParams(JMSConnectionFactoryBean cf, JMSConnectionFactoryMBean mbean) {
      TransactionParamsBean tp = cf.getTransactionParams();
      long lValue = mbean.getTransactionTimeout();
      if (lValue != 3600L) {
         tp.setTransactionTimeout(lValue);
      }

      boolean bValue = mbean.isXAConnectionFactoryEnabled();
      if (bValue) {
         tp.setXAConnectionFactoryEnabled(bValue);
      }

      bValue = mbean.isUserTransactionsEnabled();
      tp.setXAConnectionFactoryEnabled(tp.isXAConnectionFactoryEnabled() || bValue);
      bValue = mbean.isXAServerEnabled();
      tp.setXAConnectionFactoryEnabled(tp.isXAConnectionFactoryEnabled() || bValue);
   }

   private static void fillInFlowControlParams(JMSConnectionFactoryBean cf, JMSConnectionFactoryMBean mbean) {
      FlowControlParamsBean fcp = cf.getFlowControlParams();
      boolean bValue = mbean.isFlowControlEnabled();
      if (!bValue) {
         fcp.setFlowControlEnabled(bValue);
      }

      int iValue = mbean.getFlowMinimum();
      if (iValue != 50) {
         fcp.setFlowMinimum(iValue);
      }

      if ((iValue = mbean.getFlowMaximum()) != 500) {
         fcp.setFlowMaximum(iValue);
      }

      if ((iValue = mbean.getFlowInterval()) != 60) {
         fcp.setFlowInterval(iValue);
      }

      if ((iValue = mbean.getFlowSteps()) != 10) {
         fcp.setFlowSteps(iValue);
      }

   }

   private static void fillInLoadBalancingParams(JMSConnectionFactoryBean cf, JMSConnectionFactoryMBean mbean) {
      LoadBalancingParamsBean lbp = cf.getLoadBalancingParams();
      boolean bValue = mbean.isLoadBalancingEnabled();
      if (!bValue) {
         lbp.setLoadBalancingEnabled(bValue);
      }

      if (!(bValue = mbean.isServerAffinityEnabled())) {
         lbp.setServerAffinityEnabled(bValue);
      }

      String sValue = mbean.getProducerLoadBalancingPolicy();
      if (sValue != null && !sValue.equals(JMSConstants.PRODUCER_LB_POLICY_DEFAULT)) {
         lbp.setProducerLoadBalancingPolicy(sValue);
      }

   }

   public static ForeignServerBean addForeignJMSServer(JMSBean parent, ForeignJMSServerMBean mbean) {
      return addForeignJMSServer(parent, (JMSSystemResourceMBean)null, mbean, mbean.getName(), mbean.getTargets());
   }

   public static ForeignServerBean addForeignJMSServer(JMSBean parent, JMSSystemResourceMBean deployable, ForeignJMSServerMBean mbean, String name, TargetMBean[] targets) {
      ForeignServerBean fs;
      if ((fs = parent.lookupForeignServer(name)) != null) {
         return fs;
      } else {
         fs = parent.createForeignServer(name);
         fillInForeignServer(deployable, fs, mbean, targets);
         return fs;
      }
   }

   private static void fillInForeignServer(JMSSystemResourceMBean deployable, ForeignServerBean fst, ForeignJMSServerMBean mbean, TargetMBean[] targets) {
      if (mbean.getNotes() != null) {
         fst.setNotes(mbean.getNotes());
      }

      if (deployable != null) {
         SubDeploymentMBean smut;
         if ((smut = deployable.lookupSubDeployment(fst.getName())) == null) {
            smut = deployable.createSubDeployment(fst.getName());
         }

         try {
            smut.setTargets(targets);
         } catch (InvalidAttributeValueException var9) {
            throw new AssertionError("ERROR: Could not set the targets of JMS foreign server " + fst.getName() + " due to " + var9);
         } catch (DistributedManagementException var10) {
            throw new AssertionError("WARN: Could not set the targets of JMS foreign server " + fst.getName() + " due to " + var10);
         }
      }

      String sValue = mbean.getInitialContextFactory();
      if (sValue != null && !sValue.equals("weblogic.jndi.WLInitialContextFactory")) {
         fst.setInitialContextFactory(sValue);
      }

      if ((sValue = mbean.getConnectionURL()) != null) {
         fst.setConnectionURL(sValue);
      }

      Properties p = mbean.getJNDIProperties();
      if (p != null) {
         Iterator it = p.keySet().iterator();

         while(it.hasNext()) {
            PropertyBean pb = null;
            String key = (String)it.next();
            pb = fst.createJNDIProperty(key);
            pb.setValue(p.getProperty(key));
         }
      }

      ForeignJMSDestinationMBean[] fdMBeans = mbean.getForeignJMSDestinations();

      for(int i = 0; i < fdMBeans.length; ++i) {
         addForeignDestination(fst, fdMBeans[i]);
      }

      ForeignJMSConnectionFactoryMBean[] fcfMBeans = mbean.getForeignJMSConnectionFactories();

      for(int i = 0; i < fcfMBeans.length; ++i) {
         addForeignConnectionFactory(fst, fcfMBeans[i]);
      }

   }

   public static ForeignDestinationBean addForeignDestination(ForeignServerBean fsBean, ForeignJMSDestinationMBean mbean) {
      ForeignDestinationBean fd;
      if ((fd = fsBean.lookupForeignDestination(mbean.getName())) != null) {
         return fd;
      } else {
         fd = fsBean.createForeignDestination(mbean.getName());
         fillInForeignDestination(fd, mbean);
         return fd;
      }
   }

   private static void fillInForeignDestination(ForeignDestinationBean fd, ForeignJMSDestinationMBean mbean) {
      fillInForeignJNDIObject(fd, mbean);
   }

   public static ForeignConnectionFactoryBean addForeignConnectionFactory(ForeignServerBean fsBean, ForeignJMSConnectionFactoryMBean mbean) {
      ForeignConnectionFactoryBean fcf;
      if ((fcf = fsBean.lookupForeignConnectionFactory(mbean.getName())) != null) {
         return fcf;
      } else {
         fcf = fsBean.createForeignConnectionFactory(mbean.getName());
         fillInForeignConnectionFactory(fcf, mbean);
         return fcf;
      }
   }

   private static void fillInForeignConnectionFactory(ForeignConnectionFactoryBean fcf, ForeignJMSConnectionFactoryMBean mbean) {
      fillInForeignJNDIObject(fcf, mbean);
      String sValue = mbean.getUsername();
      if (sValue != null) {
         fcf.setUsername(sValue);
      }

      if ((sValue = mbean.getPassword()) != null) {
         fcf.setPassword(sValue);
      }

   }

   private static void fillInForeignJNDIObject(ForeignJNDIObjectBean fjo, ForeignJNDIObjectMBean mbean) {
      if (mbean.getNotes() != null) {
         fjo.setNotes(mbean.getNotes());
      }

      fjo.setLocalJNDIName(mbean.getLocalJNDIName());
      fjo.setRemoteJNDIName(mbean.getRemoteJNDIName());
   }

   public static DistributedQueueBean addDistributedQueue(JMSBean parent, JMSSystemResourceMBean deployable, JMSDistributedQueueMBean mbean) throws UpdateException {
      DistributedQueueBean dqueue;
      if ((dqueue = parent.lookupDistributedQueue(mbean.getName())) != null) {
         return dqueue;
      } else {
         dqueue = parent.createDistributedQueue(mbean.getName());
         fillInDistributedDestination(deployable, dqueue, mbean);
         fillInDistributedQueue(parent, dqueue, mbean);
         return dqueue;
      }
   }

   public static DistributedTopicBean addDistributedTopic(JMSBean parent, JMSSystemResourceMBean deployable, JMSDistributedTopicMBean mbean) throws UpdateException {
      DistributedTopicBean dtopic;
      if ((dtopic = parent.lookupDistributedTopic(mbean.getName())) != null) {
         return dtopic;
      } else {
         dtopic = parent.createDistributedTopic(mbean.getName());
         fillInDistributedDestination(deployable, dtopic, mbean);
         fillInDistributedTopic(parent, dtopic, mbean);
         return dtopic;
      }
   }

   public static DistributedDestinationMemberBean addDistributedQueueMember(JMSBean parent, DistributedQueueBean dqBean, JMSDistributedQueueMemberMBean mbean) throws UpdateException {
      DistributedDestinationMemberBean ddqm;
      if ((ddqm = dqBean.lookupDistributedQueueMember(mbean.getName())) != null) {
         return ddqm;
      } else {
         ddqm = dqBean.createDistributedQueueMember(mbean.getName());
         fillInDistributedDestinationMember(ddqm, mbean);
         fillInDistributedQueueMember(parent, ddqm, mbean);
         return ddqm;
      }
   }

   public static DistributedDestinationMemberBean addDistributedTopicMember(JMSBean parent, DistributedTopicBean dtBean, JMSDistributedTopicMemberMBean mbean) throws UpdateException {
      DistributedDestinationMemberBean ddtm;
      if ((ddtm = dtBean.lookupDistributedTopicMember(mbean.getName())) != null) {
         return ddtm;
      } else {
         ddtm = dtBean.createDistributedTopicMember(mbean.getName());
         fillInDistributedDestinationMember(ddtm, mbean);
         fillInDistributedTopicMember(parent, ddtm, mbean);
         return ddtm;
      }
   }

   private static void fillInDistributedDestination(JMSSystemResourceMBean deployable, DistributedDestinationBean dd, JMSDistributedDestinationMBean mbean) {
      if (mbean.getNotes() != null) {
         dd.setNotes(mbean.getNotes());
      }

      String jndiName = mbean.getJNDIName();
      if (jndiName != null && jndiName.length() > 0) {
         dd.setJNDIName(jndiName);
      }

      String sValue = mbean.getLoadBalancingPolicy();
      if (sValue != null && !sValue.equals("Round-Robin")) {
         dd.setLoadBalancingPolicy(sValue);
      }

      JMSTemplateMBean distributedTemplate = mbean.getJMSTemplate();
      if (distributedTemplate != null) {
         JMSLogger.logTemplateOnDDNotSupported(mbean.getName(), distributedTemplate.getName());
      }

      distributedTemplate = mbean.getTemplate();
      if (distributedTemplate != null) {
         JMSLogger.logTemplateOnDDNotSupported(mbean.getName(), distributedTemplate.getName());
      }

      if (deployable != null) {
         String groupName = dd.getName();
         SubDeploymentMBean smut;
         if ((smut = deployable.lookupSubDeployment(groupName)) == null) {
            smut = deployable.createSubDeployment(groupName);
         }

         try {
            smut.setTargets(mbean.getTargets());
         } catch (InvalidAttributeValueException var9) {
            throw new AssertionError("ERROR: Could not set the targets of JMS distributed destination " + dd.getName() + " due to " + var9);
         } catch (DistributedManagementException var10) {
            throw new AssertionError("ERROR: Could not set the targets of JMS distributed destination " + dd.getName() + " due to" + var10);
         }
      }

   }

   private static void fillInDistributedQueue(JMSBean parent, DistributedQueueBean dq, JMSDistributedQueueMBean mbean) throws UpdateException {
      JMSDistributedQueueMemberMBean[] dqMemberMBeans = mbean.getJMSDistributedQueueMembers();
      int iValue;
      if (dqMemberMBeans != null) {
         for(iValue = 0; iValue < dqMemberMBeans.length; ++iValue) {
            addDistributedQueueMember(parent, dq, dqMemberMBeans[iValue]);
         }
      }

      iValue = mbean.getForwardDelay();
      if (iValue != -1) {
         dq.setForwardDelay(iValue);
      }

   }

   private static void fillInDistributedTopic(JMSBean parent, DistributedTopicBean dt, JMSDistributedTopicMBean mbean) throws UpdateException {
      JMSDistributedTopicMemberMBean[] dtMemberMBeans = mbean.getJMSDistributedTopicMembers();
      if (dtMemberMBeans != null) {
         for(int i = 0; i < dtMemberMBeans.length; ++i) {
            addDistributedTopicMember(parent, dt, dtMemberMBeans[i]);
         }
      }

   }

   private static void fillInDistributedDestinationMember(DistributedDestinationMemberBean ddm, JMSDistributedDestinationMemberMBean mbean) {
      if (mbean.getNotes() != null) {
         ddm.setNotes(mbean.getNotes());
      }

      int iValue = mbean.getWeight();
      if (iValue != 1) {
         ddm.setWeight(iValue);
      }

   }

   private static void fillInDistributedQueueMember(JMSBean parent, DistributedDestinationMemberBean dqm, JMSDistributedQueueMemberMBean mbean) throws UpdateException {
      JMSQueueMBean queueMemberDestination = mbean.getJMSQueue();
      if (queueMemberDestination != null) {
         String queueMemberDestinationName = queueMemberDestination.getName();
         if (JMSBeanHelper.findDestinationBean(queueMemberDestinationName, parent) == null) {
            addQueue(parent, queueMemberDestination);
         }

         dqm.setPhysicalDestinationName(queueMemberDestinationName);
      }

   }

   private static void fillInDistributedTopicMember(JMSBean parent, DistributedDestinationMemberBean dtm, JMSDistributedTopicMemberMBean mbean) throws UpdateException {
      JMSTopicMBean topicMemberDestination = mbean.getJMSTopic();
      if (topicMemberDestination != null) {
         String topicMemberDestinationName = topicMemberDestination.getName();
         if (JMSBeanHelper.findDestinationBean(topicMemberDestinationName, parent) == null) {
            addTopic(parent, topicMemberDestination);
         }

         dtm.setPhysicalDestinationName(topicMemberDestinationName);
      }

   }

   public static DestinationKeyBean addDestinationKey(JMSBean parent, JMSDestinationKeyMBean mbean) {
      DestinationKeyBean dk = parent.createDestinationKey(mbean.getName());
      if (mbean.getNotes() != null) {
         dk.setNotes(mbean.getNotes());
      }

      String property = mbean.getProperty();
      if (property != null && !"JMSMessageID".equals(property)) {
         dk.setProperty(property);
      }

      String type = mbean.getKeyType();
      if (type != null && !"String".equals(type)) {
         dk.setKeyType(type);
      }

      String sortOrder = mbean.getDirection();
      if (sortOrder != null && !"Ascending".equals(sortOrder)) {
         dk.setSortOrder(sortOrder);
      }

      return dk;
   }

   public static QueueBean addQueue(JMSBean parent, JMSQueueMBean mbean) throws UpdateException {
      return addQueue(parent, (JMSSystemResourceMBean)null, mbean);
   }

   public static QueueBean addQueue(JMSBean parent, JMSSystemResourceMBean deployable, JMSQueueMBean mbean) throws UpdateException {
      boolean fillInDestinationName = false;
      String myJMSServerName = null;
      QueueBean queue;
      if ((queue = parent.lookupQueue(mbean.getName())) != null) {
         TargetMBean[] targets = getTargetsFromDestination(mbean);
         JMSServerMBean myJMSServer = null;
         if (targets.length > 0) {
            myJMSServer = (JMSServerMBean)targets[0];
            myJMSServerName = myJMSServer.getName();
         }

         String sdName = queue.getSubDeploymentName();
         if (sdName != null && sdName.equals(myJMSServerName)) {
            return queue;
         }

         JMSLogger.logDestinationNameConflict("JMSQueue", mbean.getName());
         fillInDestinationName = true;
      }

      if (fillInDestinationName) {
         queue = parent.createQueue(mbean.getName() + "_" + myJMSServerName);
      } else {
         queue = parent.createQueue(mbean.getName());
      }

      fillInDestination(parent, deployable, queue, mbean, fillInDestinationName);
      return queue;
   }

   public static TopicBean addTopic(JMSBean parent, JMSTopicMBean mbean) throws UpdateException {
      return addTopic(parent, (JMSSystemResourceMBean)null, mbean);
   }

   public static TopicBean addTopic(JMSBean parent, JMSSystemResourceMBean deployable, JMSTopicMBean mbean) throws UpdateException {
      boolean fillInDestinationName = false;
      String myJMSServerName = null;
      TopicBean topic;
      if ((topic = parent.lookupTopic(mbean.getName())) != null) {
         TargetMBean[] targets = getTargetsFromDestination(mbean);
         JMSServerMBean myJMSServer = null;
         if (targets.length > 0) {
            myJMSServer = (JMSServerMBean)targets[0];
            myJMSServerName = myJMSServer.getName();
         }

         String sdName = topic.getSubDeploymentName();
         if (sdName != null && sdName.equals(myJMSServerName)) {
            return topic;
         }

         JMSLogger.logDestinationNameConflict("JMSTopic", mbean.getName());
         fillInDestinationName = true;
      }

      if (fillInDestinationName) {
         topic = parent.createTopic(mbean.getName() + "_" + myJMSServerName);
      } else {
         topic = parent.createTopic(mbean.getName());
      }

      fillInDestination(parent, deployable, topic, mbean, fillInDestinationName);
      String multicastAddress = mbean.getMulticastAddress();
      if (multicastAddress != null) {
         topic.getMulticast().setMulticastAddress(multicastAddress);
      }

      int iValue;
      if ((iValue = mbean.getMulticastPort()) != 6001) {
         topic.getMulticast().setMulticastPort(iValue);
      }

      if ((iValue = mbean.getMulticastTTL()) != 1) {
         topic.getMulticast().setMulticastTimeToLive(iValue);
      }

      return topic;
   }

   public static String constructQuotaNameFromDestinationName(String queueName) {
      return queueName + ".Quota";
   }

   private static TargetMBean[] getTargetsFromDestination(JMSDestinationMBean destination) {
      WebLogicMBean webM = destination.getParent();
      if (webM instanceof JMSServerMBean) {
         TargetMBean[] targets = new TargetMBean[]{(TargetMBean)webM};
         return targets;
      } else {
         return new TargetMBean[0];
      }
   }

   private static void warnDeliveryModeOverrideChange(String destinationName, JMSServerMBean jmsServer, String from) {
      String jmsServerName = jmsServer == null ? "<none>" : jmsServer.getName();
      JMSLogger.logChangingDeliveryModeOverride(destinationName, jmsServerName, from, "Non-Persistent");
   }

   private static String errorDeliveryModeOverride(String destinationName, JMSServerMBean jmsServer) {
      String jmsServerName = jmsServer == null ? "<none>" : jmsServer.getName();
      return JMSExceptionLogger.logDeliveryModeMismatchLoggable(destinationName, jmsServerName).getMessage();
   }

   private static void fillInDestination(JMSBean parent, JMSSystemResourceMBean deployable, DestinationBean dc, JMSDestinationMBean mbean, boolean fillInDestinationName) throws UpdateException {
      if (mbean.getNotes() != null) {
         dc.setNotes(mbean.getNotes());
      }

      JMSDestinationKeyMBean[] destKeys = mbean.getDestinationKeys();
      if (destKeys != null) {
         String[] keyStrs = new String[destKeys.length];

         for(int lcv = 0; lcv < destKeys.length; ++lcv) {
            String name = destKeys[lcv].getName();
            keyStrs[lcv] = name;
            if (parent.lookupDestinationKey(name) == null) {
               addDestinationKey(parent, destKeys[lcv]);
            }
         }

         dc.setDestinationKeys(keyStrs);
      }

      long lValue;
      if ((lValue = mbean.getBytesThresholdHigh()) >= 0L) {
         dc.getThresholds().setBytesHigh(lValue);
      }

      if ((lValue = mbean.getBytesThresholdLow()) >= 0L) {
         dc.getThresholds().setBytesLow(lValue);
      }

      if ((lValue = mbean.getMessagesThresholdHigh()) >= 0L) {
         dc.getThresholds().setMessagesHigh(lValue);
      }

      if ((lValue = mbean.getMessagesThresholdLow()) >= 0L) {
         dc.getThresholds().setMessagesLow(lValue);
      }

      int iValue;
      if ((iValue = mbean.getPriorityOverride()) >= 0) {
         dc.getDeliveryParamsOverrides().setPriority(iValue);
      }

      String sValue = mbean.getTimeToDeliverOverride();
      if (sValue != null && !sValue.equals("-1")) {
         dc.getDeliveryParamsOverrides().setTimeToDeliver(sValue);
      }

      if ((lValue = mbean.getRedeliveryDelayOverride()) >= 0L) {
         dc.getDeliveryParamsOverrides().setRedeliveryDelay(lValue);
      }

      JMSDestinationMBean errorDestination = mbean.getErrorDestination();
      String jndiName;
      if (errorDestination != null) {
         jndiName = errorDestination.getName();
         Object errorDestinationBean;
         if ((errorDestinationBean = JMSBeanHelper.findDestinationBean(jndiName, parent)) == null) {
            if (errorDestination instanceof JMSQueueMBean) {
               errorDestinationBean = addQueue(parent, (JMSQueueMBean)errorDestination);
            } else {
               if (!(errorDestination instanceof JMSTopicMBean)) {
                  throw new AssertionError("ERROR: Error destination " + jndiName + " for destination " + mbean.getName() + " is neither queue nor topic");
               }

               errorDestinationBean = addTopic(parent, (JMSTopicMBean)errorDestination);
            }
         }

         dc.getDeliveryFailureParams().setErrorDestination((DestinationBean)errorDestinationBean);
      }

      if ((iValue = mbean.getRedeliveryLimit()) >= 0) {
         dc.getDeliveryFailureParams().setRedeliveryLimit(iValue);
      }

      if ((lValue = mbean.getTimeToLiveOverride()) >= 0L) {
         dc.getDeliveryParamsOverrides().setTimeToLive(lValue);
      }

      sValue = mbean.getExpirationPolicy();
      if (sValue != null && !sValue.equals("Discard")) {
         dc.getDeliveryFailureParams().setExpirationPolicy(sValue);
      }

      if ((sValue = mbean.getExpirationLoggingPolicy()) != null) {
         dc.getDeliveryFailureParams().setExpirationLoggingPolicy(sValue);
      }

      if ((iValue = mbean.getMaximumMessageSize()) != Integer.MAX_VALUE) {
         dc.setMaximumMessageSize(iValue);
      }

      jndiName = mbean.getJNDIName();
      if (jndiName != null && jndiName.length() > 0) {
         boolean replicated = mbean.isJNDINameReplicated();
         if (replicated) {
            dc.setJNDIName(jndiName);
         } else {
            dc.setLocalJNDIName(jndiName);
         }
      }

      TargetMBean[] targets = getTargetsFromDestination(mbean);
      JMSServerMBean myJMSServer = null;
      if (targets.length > 0) {
         myJMSServer = (JMSServerMBean)targets[0];
         String groupName = targets[0].getName();
         dc.setSubDeploymentName(groupName);
         if (fillInDestinationName) {
            dc.setJMSCreateDestinationIdentifier(mbean.getName());
         }

         if (deployable != null) {
            SubDeploymentMBean smut;
            if ((smut = deployable.lookupSubDeployment(groupName)) == null) {
               smut = deployable.createSubDeployment(groupName);
            }

            try {
               smut.setTargets(targets);
            } catch (InvalidAttributeValueException var19) {
               throw new AssertionError("ERROR: Could not set the targets of JMS destination " + dc.getName() + " due to " + var19);
            } catch (DistributedManagementException var20) {
               throw new AssertionError("ERROR: Could not set the targets of JMS destination " + dc.getName() + " due to" + var20);
            }
         }
      }

      sValue = mbean.getStoreEnabled();
      byte storeValue;
      if (sValue != null && !sValue.equalsIgnoreCase("default")) {
         if (sValue.equalsIgnoreCase("true")) {
            storeValue = 1;
         } else {
            storeValue = 2;
         }
      } else {
         storeValue = 0;
      }

      sValue = mbean.getDeliveryModeOverride();
      byte deliveryModeOverride;
      if (sValue != null && !sValue.equalsIgnoreCase("No-Delivery")) {
         if (sValue.equalsIgnoreCase("Persistent")) {
            deliveryModeOverride = 1;
         } else {
            deliveryModeOverride = 2;
         }
      } else {
         deliveryModeOverride = 0;
      }

      boolean jmsServerHasStore;
      if (myJMSServer != null && myJMSServer.getStore() == null && myJMSServer.getPersistentStore() == null) {
         jmsServerHasStore = false;
      } else {
         jmsServerHasStore = true;
      }

      label124:
      switch (storeValue) {
         case 0:
            switch (deliveryModeOverride) {
               case 0:
                  if (!jmsServerHasStore) {
                     warnDeliveryModeOverrideChange(dc.getName(), myJMSServer, "No-Delivery");
                     dc.getDeliveryParamsOverrides().setDeliveryMode("Non-Persistent");
                  }
                  break label124;
               case 1:
                  if (jmsServerHasStore) {
                     dc.getDeliveryParamsOverrides().setDeliveryMode("Persistent");
                  } else {
                     warnDeliveryModeOverrideChange(dc.getName(), myJMSServer, "Persistent");
                     dc.getDeliveryParamsOverrides().setDeliveryMode("Non-Persistent");
                  }
                  break label124;
               case 2:
                  dc.getDeliveryParamsOverrides().setDeliveryMode("Non-Persistent");
                  break label124;
               default:
                  throw new AssertionError("Unknown deliveryModeOverride=" + deliveryModeOverride + " storeValue=" + storeValue);
            }
         case 1:
            switch (deliveryModeOverride) {
               case 0:
                  if (!jmsServerHasStore) {
                     throw new UpdateException(errorDeliveryModeOverride(dc.getName(), myJMSServer));
                  }
                  break label124;
               case 1:
                  if (!jmsServerHasStore) {
                     throw new UpdateException(errorDeliveryModeOverride(dc.getName(), myJMSServer));
                  }

                  dc.getDeliveryParamsOverrides().setDeliveryMode("Persistent");
                  break label124;
               case 2:
                  if (!jmsServerHasStore) {
                     throw new UpdateException(errorDeliveryModeOverride(dc.getName(), myJMSServer));
                  }

                  dc.getDeliveryParamsOverrides().setDeliveryMode("Non-Persistent");
                  break label124;
               default:
                  throw new AssertionError("Unknown deliveryModeOverride=" + deliveryModeOverride + " storeValue=" + storeValue);
            }
         case 2:
            switch (deliveryModeOverride) {
               case 0:
                  warnDeliveryModeOverrideChange(dc.getName(), myJMSServer, "No-Delivery");
                  dc.getDeliveryParamsOverrides().setDeliveryMode("Non-Persistent");
                  break label124;
               case 1:
                  warnDeliveryModeOverrideChange(dc.getName(), myJMSServer, "Persistent");
                  dc.getDeliveryParamsOverrides().setDeliveryMode("Non-Persistent");
                  break label124;
               case 2:
                  dc.getDeliveryParamsOverrides().setDeliveryMode("Non-Persistent");
                  break label124;
               default:
                  throw new AssertionError("Unknown deliveryModeOverride=" + deliveryModeOverride + " storeValue=" + storeValue);
            }
         default:
            throw new AssertionError("Unknown storeValue=" + storeValue);
      }

      JMSTemplateMBean tBean = mbean.getTemplate();
      if (tBean != null) {
         TemplateBean template = addTemplate(parent, tBean);
         dc.setTemplate(template);
      }

      if (mbean.getBytesMaximum() != -1L || mbean.getMessagesMaximum() != -1L) {
         QuotaBean quotaBean = addQuota(parent, constructQuotaNameFromDestinationName(mbean.getName()), mbean.getBytesMaximum(), mbean.getMessagesMaximum());
         dc.setQuota(quotaBean);
      }

   }

   private static QuotaBean addQuota(JMSBean parent, String name, long bytesMaximum, long messagesMaximum) {
      QuotaBean retObj;
      if ((retObj = parent.lookupQuota(name)) != null) {
         return retObj;
      } else {
         retObj = parent.createQuota(name);
         if (bytesMaximum >= 0L) {
            retObj.setBytesMaximum(bytesMaximum);
         }

         if (messagesMaximum >= 0L) {
            retObj.setMessagesMaximum(messagesMaximum);
         }

         retObj.setShared(false);
         return retObj;
      }
   }

   public static TemplateBean addTemplate(JMSBean parent, JMSTemplateMBean mbean) throws UpdateException {
      TemplateBean retObj;
      if ((retObj = parent.lookupTemplate(mbean.getName())) != null) {
         return retObj;
      } else {
         retObj = parent.createTemplate(mbean.getName());
         if (mbean.getNotes() != null) {
            retObj.setNotes(mbean.getNotes());
         }

         JMSDestinationKeyMBean[] destKeys = mbean.getDestinationKeys();
         if (destKeys != null) {
            String[] keyStrs = new String[destKeys.length];

            for(int lcv = 0; lcv < destKeys.length; ++lcv) {
               String name = destKeys[lcv].getName();
               keyStrs[lcv] = name;
               if (parent.lookupDestinationKey(name) == null) {
                  addDestinationKey(parent, destKeys[lcv]);
               }
            }

            retObj.setDestinationKeys(keyStrs);
         }

         long lValue;
         if ((lValue = mbean.getBytesThresholdHigh()) >= 0L) {
            retObj.getThresholds().setBytesHigh(lValue);
         }

         if ((lValue = mbean.getBytesThresholdLow()) >= 0L) {
            retObj.getThresholds().setBytesLow(lValue);
         }

         if ((lValue = mbean.getMessagesThresholdHigh()) >= 0L) {
            retObj.getThresholds().setMessagesHigh(lValue);
         }

         if ((lValue = mbean.getMessagesThresholdLow()) >= 0L) {
            retObj.getThresholds().setMessagesLow(lValue);
         }

         int iValue;
         if ((iValue = mbean.getPriorityOverride()) >= 0) {
            retObj.getDeliveryParamsOverrides().setPriority(iValue);
         }

         String sValue = mbean.getTimeToDeliverOverride();
         if (sValue != null && !sValue.equals("-1")) {
            retObj.getDeliveryParamsOverrides().setTimeToDeliver(sValue);
         }

         if ((lValue = mbean.getRedeliveryDelayOverride()) >= 0L) {
            retObj.getDeliveryParamsOverrides().setRedeliveryDelay(lValue);
         }

         JMSDestinationMBean errorDestination = mbean.getErrorDestination();
         if (errorDestination != null) {
            String errorDestinationName = errorDestination.getName();
            Object errorDestinationBean;
            if ((errorDestinationBean = JMSBeanHelper.findDestinationBean(errorDestinationName, parent)) == null) {
               if (errorDestination instanceof JMSQueueMBean) {
                  errorDestinationBean = addQueue(parent, (JMSQueueMBean)errorDestination);
               } else {
                  if (!(errorDestination instanceof JMSTopicMBean)) {
                     throw new AssertionError("ERROR: Error destination " + errorDestinationName + " of template " + mbean.getName() + " is neither queue nor topic");
                  }

                  errorDestinationBean = addTopic(parent, (JMSTopicMBean)errorDestination);
               }
            }

            retObj.getDeliveryFailureParams().setErrorDestination((DestinationBean)errorDestinationBean);
         }

         if ((iValue = mbean.getRedeliveryLimit()) >= 0) {
            retObj.getDeliveryFailureParams().setRedeliveryLimit(iValue);
         }

         if ((lValue = mbean.getTimeToLiveOverride()) >= 0L) {
            retObj.getDeliveryParamsOverrides().setTimeToLive(lValue);
         }

         sValue = mbean.getDeliveryModeOverride();
         if (sValue != null && !sValue.equals("No-Delivery")) {
            retObj.getDeliveryParamsOverrides().setDeliveryMode(sValue);
         }

         if ((sValue = mbean.getExpirationPolicy()) != null) {
            retObj.getDeliveryFailureParams().setExpirationPolicy(sValue);
         }

         if ((sValue = mbean.getExpirationLoggingPolicy()) != null) {
            retObj.getDeliveryFailureParams().setExpirationLoggingPolicy(sValue);
         }

         if ((iValue = mbean.getMaximumMessageSize()) != Integer.MAX_VALUE) {
            retObj.setMaximumMessageSize(iValue);
         }

         QuotaBean quotaBean = addQuota(parent, constructQuotaNameFromDestinationName(mbean.getName()), mbean.getBytesMaximum(), mbean.getMessagesMaximum());
         if (mbean.getBytesMaximum() >= 0L || mbean.getMessagesMaximum() >= 0L) {
            retObj.setQuota(quotaBean);
         }

         return retObj;
      }
   }

   public static JMSDestinationMBean findErrorQueue(DomainMBean domain, String errorQueueName) {
      if (domain != null && errorQueueName != null) {
         JMSServerMBean[] jmsServers = domain.getJMSServers();
         if (jmsServers == null) {
            return null;
         } else {
            for(int outer = 0; outer < jmsServers.length; ++outer) {
               JMSServerMBean jmsServer = jmsServers[outer];
               JMSQueueMBean[] queues = jmsServer.getJMSQueues();

               for(int lcv = 0; lcv < queues.length; ++lcv) {
                  JMSQueueMBean queue = queues[lcv];
                  if (errorQueueName.equals(queue.getName())) {
                     return queue;
                  }
               }

               JMSTopicMBean[] topics = jmsServer.getJMSTopics();

               for(int lcv = 0; lcv < topics.length; ++lcv) {
                  JMSTopicMBean topic = topics[lcv];
                  if (errorQueueName.equals(topic.getName())) {
                     return topic;
                  }
               }
            }

            return null;
         }
      } else {
         return null;
      }
   }

   public static HashMap splitDeployment(DeploymentMBean mbean) {
      HashMap splitDeployments = new HashMap();
      HashMap serverClusterPair = new HashMap();
      HashMap name2ServerMBeans = new HashMap();
      HashMap name2ClusterTargets = new HashMap();
      HashMap name2ServerTargets = new HashMap();
      TargetMBean[] targets = mbean.getTargets();
      if (targets != null && targets.length > 1) {
         int i;
         ArrayList servers;
         for(i = 0; i < targets.length; ++i) {
            if (targets[i] instanceof ClusterMBean && !name2ClusterTargets.containsValue((ClusterMBean)targets[i])) {
               name2ClusterTargets.put(targets[i].getName(), (ClusterMBean)targets[i]);
               String splitFactoryName = mbean.getName() + "_" + targets[i].getName();
               servers = new ArrayList();
               servers.add(targets[i]);
               splitDeployments.put(splitFactoryName, servers);
            }
         }

         for(i = 0; i < targets.length; ++i) {
            if (targets[i] instanceof ServerMBean) {
               ClusterMBean cluster = ((ServerMBean)targets[i]).getCluster();
               if (cluster != null) {
                  if (!name2ClusterTargets.containsValue(cluster)) {
                     serverClusterPair.put(targets[i].getName(), cluster);
                     name2ServerMBeans.put(targets[i].getName(), targets[i]);
                  }
               } else if (!name2ServerTargets.containsValue((ServerMBean)targets[i])) {
                  name2ServerTargets.put(targets[i].getName(), (ServerMBean)targets[i]);
                  String splitFactoryName = mbean.getName() + "_" + targets[i].getName();
                  ArrayList serverTargets = new ArrayList();
                  serverTargets.add(targets[i]);
                  splitDeployments.put(splitFactoryName, serverTargets);
               }
            }
         }

         Iterator clusters = serverClusterPair.values().iterator();
         Iterator serverNames = serverClusterPair.keySet().iterator();

         while(clusters.hasNext()) {
            servers = new ArrayList();
            String splitFactoryName = null;
            ClusterMBean cluster = (ClusterMBean)clusters.next();

            while(serverNames.hasNext()) {
               String serverName = (String)serverNames.next();
               ClusterMBean c = (ClusterMBean)serverClusterPair.get(serverName);
               if (c.getName().equals(cluster.getName())) {
                  TargetMBean t = (TargetMBean)name2ServerMBeans.get(serverName);
                  servers.add(t);
                  if (splitFactoryName != null) {
                     splitFactoryName = splitFactoryName + "_" + serverName;
                  } else {
                     splitFactoryName = mbean.getName() + "_" + serverName;
                  }
               }
            }

            if (splitFactoryName != null) {
               splitDeployments.put(splitFactoryName, servers);
            }
         }
      }

      return splitDeployments;
   }
}
