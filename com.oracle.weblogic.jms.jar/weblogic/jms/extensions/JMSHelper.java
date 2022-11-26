package weblogic.jms.extensions;

import java.util.ArrayList;
import java.util.Iterator;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.naming.Context;
import weblogic.jms.common.ConfigurationException;
import weblogic.jms.common.DestinationImpl;
import weblogic.management.MBeanHome;
import weblogic.management.WebLogicObjectName;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.JMSDestinationMBean;
import weblogic.management.configuration.JMSDistributedDestinationMBean;
import weblogic.management.configuration.JMSDistributedQueueMBean;
import weblogic.management.configuration.JMSDistributedQueueMemberMBean;
import weblogic.management.configuration.JMSDistributedTopicMBean;
import weblogic.management.configuration.JMSDistributedTopicMemberMBean;
import weblogic.management.configuration.JMSQueueMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSTemplateMBean;
import weblogic.management.configuration.JMSTopicMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;

/** @deprecated */
@Deprecated
public final class JMSHelper extends JMSRuntimeHelper {
   private static TargetMBean getDDTarget(String[] jmsServerNames, MBeanHome configHome, String domainName) throws Exception {
      if (jmsServerNames.length == 1) {
         return getJMSServerTarget(jmsServerNames[0], configHome, domainName, true);
      } else {
         ClusterMBean ret = null;

         for(int inc = 0; inc < jmsServerNames.length; ++inc) {
            ClusterMBean curTarget;
            try {
               curTarget = (ClusterMBean)getJMSServerTarget(jmsServerNames[inc], configHome, domainName, true);
            } catch (ClassCastException var7) {
               throw new JMSException("Distributed destination has multiple members, but JMS server " + jmsServerNames[inc] + " is not targeted to a cluster");
            }

            if (ret == null) {
               ret = curTarget;
            } else if (!ret.getName().equals(curTarget.getName())) {
               throw new JMSException("JMS servers for the distributed destination may not be members  of different clusters");
            }
         }

         return ret;
      }
   }

   private static TargetMBean getJMSServerTarget(String serverName, MBeanHome configHome, String domainName) throws Exception {
      return getJMSServerTarget(serverName, configHome, domainName, false);
   }

   private static TargetMBean getJMSServerTarget(String serverName, MBeanHome configHome, String domainName, boolean isForDD) throws Exception {
      WebLogicObjectName serverNameObj = new WebLogicObjectName(serverName, "JMSServer", domainName);
      JMSServerMBean jmsServerBean = (JMSServerMBean)configHome.getMBean(serverNameObj);
      TargetMBean[] targets = jmsServerBean.getTargets();
      if (targets != null && targets.length != 0) {
         if (targets.length > 1) {
            throw new JMSException("JMS server " + serverName + " has multiple targets");
         } else {
            try {
               TargetMBean ret = null;
               if (targets[0] instanceof MigratableTargetMBean) {
                  ret = ((MigratableTargetMBean)targets[0]).getCluster();
               } else {
                  ret = ((ServerMBean)targets[0]).getCluster();
               }

               if (ret == null && !isForDD) {
                  ret = targets[0];
               }

               return (TargetMBean)ret;
            } catch (ClassCastException var8) {
               var8.printStackTrace();
               throw new JMSException("JMS server " + serverName + " is not targeted");
            }
         }
      } else {
         throw new JMSException("JMS server " + serverName + " has no targets");
      }
   }

   private static JMSDestinationMBean createPermanentDestination(Context ctx, String jmsServerName, String queueName, String jndiName, JMSTemplateMBean template, String destinationType) throws JMSException {
      try {
         MBeanHome configHome = (MBeanHome)ctx.lookup("weblogic.management.adminhome");
         String domainName = configHome.getActiveDomain().getName();
         JMSServerMBean jmsServerMBean = (JMSServerMBean)configHome.getAdminMBean(jmsServerName, "JMSServer", domainName);
         JMSDestinationMBean destMBean = (JMSDestinationMBean)configHome.createAdminMBean(queueName, destinationType, domainName, (ConfigurationMBean)null);
         destMBean.setJNDIName(jndiName);
         if (template != null) {
            destMBean.setTemplate(template);
         }

         destMBean.setParent(jmsServerMBean);
         return destMBean;
      } catch (Exception var10) {
         throw new weblogic.jms.common.JMSException(var10);
      }
   }

   public static void createPermanentQueueAsync(Context ctx, String jmsServerName, String queueName, String jndiName) throws JMSException {
      createPermanentDestination(ctx, jmsServerName, queueName, jndiName, (JMSTemplateMBean)null, "JMSQueue");
   }

   public static void createPermanentTopicAsync(Context ctx, String jmsServerName, String topicName, String jndiName) throws JMSException {
      createPermanentDestination(ctx, jmsServerName, topicName, jndiName, (JMSTemplateMBean)null, "JMSTopic");
   }

   private static void deletePermanentDestination(Context ctx, String jmsServerName, String destinationName, String destinationType) throws ConfigurationException {
      try {
         MBeanHome configHome = (MBeanHome)ctx.lookup("weblogic.management.adminhome");
         String domainName = configHome.getActiveDomain().getName();
         WebLogicObjectName parent = new WebLogicObjectName(jmsServerName, "JMSServer", domainName);
         WebLogicObjectName jmsDestinationObj = new WebLogicObjectName(destinationName, destinationType, domainName, parent);
         JMSDestinationMBean jmsDestination = (JMSDestinationMBean)configHome.getMBean(jmsDestinationObj);
         if (jmsDestination == null) {
            throw new weblogic.jms.common.JMSException("Destination " + destinationName + " not found");
         } else {
            configHome.deleteMBean(jmsDestination);
         }
      } catch (Exception var9) {
         if (var9 instanceof ConfigurationException) {
            throw (ConfigurationException)var9;
         } else {
            ConfigurationException j = new ConfigurationException(var9.toString());
            j.setLinkedException(var9);
            throw j;
         }
      }
   }

   public static void deletePermanentQueue(Context ctx, String jmsServerName, String queueName) throws ConfigurationException {
      deletePermanentDestination(ctx, jmsServerName, queueName, "JMSQueue");
   }

   public static void deletePermanentTopic(Context ctx, String jmsServerName, String topicName) throws ConfigurationException {
      deletePermanentDestination(ctx, jmsServerName, topicName, "JMSTopic");
   }

   private static JMSDistributedDestinationMBean createDistributedDest(String destName, String destType, String jndiName, MBeanHome configHome, String domainName) throws Exception {
      JMSTemplateMBean template = (JMSTemplateMBean)configHome.createAdminMBean(destName, "JMSTemplate", domainName, (ConfigurationMBean)null);
      JMSDistributedDestinationMBean distDest = (JMSDistributedDestinationMBean)configHome.createAdminMBean(destName, destType, domainName, (ConfigurationMBean)null);
      distDest.setJNDIName(jndiName);
      distDest.setTemplate(template);
      return distDest;
   }

   public static void createDistributedQueueAsync(Context ctx, String distributedQName, String jndiName, String[] jmsServerNames) throws JMSException {
      if (jmsServerNames != null && jmsServerNames.length != 0) {
         try {
            MBeanHome configHome = (MBeanHome)ctx.lookup("weblogic.management.adminhome");
            String domainName = configHome.getActiveDomain().getName();
            TargetMBean targetBean = getDDTarget(jmsServerNames, configHome, domainName);
            JMSDistributedQueueMBean distQueue = (JMSDistributedQueueMBean)createDistributedDest(distributedQName, "JMSDistributedQueue", jndiName, configHome, domainName);

            for(int inc = 0; inc < jmsServerNames.length; ++inc) {
               JMSQueueMBean queueBean = (JMSQueueMBean)createPermanentDestination(ctx, jmsServerNames[inc], distributedQName + "@" + jmsServerNames[inc], jndiName + "@" + jmsServerNames[inc], distQueue.getTemplate(), "JMSQueue");
               JMSDistributedQueueMemberMBean member = (JMSDistributedQueueMemberMBean)configHome.createAdminMBean(distributedQName + "@" + jmsServerNames[inc], "JMSDistributedQueueMember", domainName, distQueue);
               member.setJMSQueue(queueBean);
               distQueue.addMember(member);
            }

            TargetMBean[] targets = new TargetMBean[]{targetBean};
            distQueue.setTargets(targets);
         } catch (Exception var11) {
            JMSException j = new weblogic.jms.common.JMSException(var11.toString());
            j.setLinkedException(var11);
            throw j;
         }
      } else {
         throw new JMSException("jmsServerNames parameter may not be null");
      }
   }

   public static void createDistributedTopicAsync(Context ctx, String distributedTName, String jndiName, String[] jmsServerNames) throws JMSException {
      if (jmsServerNames == null) {
         throw new JMSException("jmsServerNames parameter may not be null");
      } else {
         try {
            MBeanHome configHome = (MBeanHome)ctx.lookup("weblogic.management.adminhome");
            String domainName = configHome.getActiveDomain().getName();
            TargetMBean targetBean = getDDTarget(jmsServerNames, configHome, domainName);
            JMSDistributedTopicMBean distTopic = (JMSDistributedTopicMBean)createDistributedDest(distributedTName, "JMSDistributedTopic", jndiName, configHome, domainName);

            for(int inc = 0; inc < jmsServerNames.length; ++inc) {
               JMSTopicMBean topicBean = (JMSTopicMBean)createPermanentDestination(ctx, jmsServerNames[inc], distributedTName + "@" + jmsServerNames[inc], jndiName + "@" + jmsServerNames[inc], distTopic.getTemplate(), "JMSTopic");
               JMSDistributedTopicMemberMBean member = (JMSDistributedTopicMemberMBean)configHome.createAdminMBean(distributedTName + "@" + jmsServerNames[inc], "JMSDistributedTopicMember", domainName, distTopic);
               member.setJMSTopic(topicBean);
               distTopic.addMember(member);
            }

            TargetMBean[] targets = new TargetMBean[]{targetBean};
            distTopic.setTargets(targets);
         } catch (Exception var11) {
            JMSException j = new weblogic.jms.common.JMSException(var11.toString());
            j.setLinkedException(var11);
            throw j;
         }
      }
   }

   public static void deleteDistributedQueue(Context ctx, String distributedQName) throws JMSException {
      deleteDistributedDestination(ctx, distributedQName, "JMSDistributedQueue");
   }

   public static void deleteDistributedTopic(Context ctx, String distributedQName) throws JMSException {
      deleteDistributedDestination(ctx, distributedQName, "JMSDistributedTopic");
   }

   private static void deleteDistributedDestination(Context ctx, String destName, String destType) throws JMSException {
      try {
         MBeanHome configHome = (MBeanHome)ctx.lookup("weblogic.management.adminhome");
         String domainName = configHome.getActiveDomain().getName();
         WebLogicObjectName destObj = new WebLogicObjectName(destName, destType, domainName);
         JMSDistributedDestinationMBean destMBean = (JMSDistributedDestinationMBean)configHome.getMBean(destObj);
         ArrayList memberDests = new ArrayList();
         int inc;
         if (destMBean instanceof JMSDistributedQueueMBean) {
            JMSDistributedQueueMemberMBean[] members = ((JMSDistributedQueueMBean)destMBean).getMembers();

            for(inc = 0; inc < members.length; ++inc) {
               JMSQueueMBean queueBean = members[inc].getJMSQueue();
               if (queueBean != null) {
                  memberDests.add(queueBean);
               }

               configHome.deleteMBean(members[inc]);
            }
         } else {
            if (!(destMBean instanceof JMSDistributedTopicMBean)) {
               throw new JMSException("Invalid destination type");
            }

            JMSDistributedTopicMemberMBean[] members = ((JMSDistributedTopicMBean)destMBean).getMembers();

            for(inc = 0; inc < members.length; ++inc) {
               JMSTopicMBean topicBean = members[inc].getJMSTopic();
               if (topicBean != null) {
                  memberDests.add(topicBean);
               }

               configHome.deleteMBean(members[inc]);
            }
         }

         Iterator members = memberDests.iterator();

         while(members.hasNext()) {
            configHome.deleteMBean((JMSDestinationMBean)members.next());
         }

         JMSTemplateMBean template = destMBean.getTemplate();
         configHome.deleteMBean(destMBean);
         if (template != null && template.getName().equals(destName)) {
            configHome.deleteMBean(template);
         }

      } catch (Exception var11) {
         JMSException j = new weblogic.jms.common.JMSException(var11.toString());
         j.setLinkedException(var11);
         throw j;
      }
   }

   public static JMSTemplateMBean getJMSTemplateConfigMBean(Context ctx, String template) throws JMSException {
      if (template != null && template.length() != 0) {
         try {
            MBeanHome configHome = (MBeanHome)ctx.lookup("weblogic.management.adminhome");
            String domainName = configHome.getActiveDomain().getName();
            WebLogicObjectName jmsTemplateObj = new WebLogicObjectName(template, "JMSTemplate", domainName);
            JMSTemplateMBean templateMBean = (JMSTemplateMBean)configHome.getMBean(jmsTemplateObj);
            if (templateMBean == null) {
               throw new JMSException("JMS template config mbean not found");
            } else {
               return templateMBean;
            }
         } catch (Exception var6) {
            if (var6 instanceof JMSException) {
               throw (JMSException)var6;
            } else {
               JMSException j = new weblogic.jms.common.JMSException(var6.toString());
               j.setLinkedException(var6);
               throw j;
            }
         }
      } else {
         throw new JMSException("Null or empty template name");
      }
   }

   public static JMSTopicMBean getJMSTopicConfigMBean(Context ctx, Topic jmsTopic) throws JMSException {
      if (!(jmsTopic instanceof DestinationImpl)) {
         throw new JMSException("Unknown foreign destination");
      } else {
         DestinationImpl destination = (DestinationImpl)jmsTopic;
         return (JMSTopicMBean)getDestinationMBean(ctx, destination.getJMSServerConfigName(), destination.getName(), "JMSTopic");
      }
   }

   public static JMSTopicMBean getJMSTopicConfigMBean(Context ctx, String jmsServerName, String topicName) throws JMSException {
      return (JMSTopicMBean)getDestinationMBean(ctx, jmsServerName, topicName, "JMSTopic");
   }

   public static JMSQueueMBean getJMSQueueConfigMBean(Context ctx, Queue jmsQueue) throws JMSException {
      if (!(jmsQueue instanceof DestinationImpl)) {
         throw new JMSException("Unknown foreign destination");
      } else {
         DestinationImpl destination = (DestinationImpl)jmsQueue;
         return (JMSQueueMBean)getDestinationMBean(ctx, destination.getJMSServerConfigName(), destination.getName(), "JMSQueue");
      }
   }

   public static JMSQueueMBean getJMSQueueConfigMBean(Context ctx, String jmsServerName, String queueName) throws JMSException {
      return (JMSQueueMBean)getDestinationMBean(ctx, jmsServerName, queueName, "JMSQueue");
   }

   public static JMSServerMBean getJMSServerConfigMBean(Context ctx, String jmsServerName) throws JMSException {
      try {
         MBeanHome configHome = (MBeanHome)ctx.lookup("weblogic.management.adminhome");
         String domainName = configHome.getActiveDomain().getName();
         JMSServerMBean jmsServerMBean = (JMSServerMBean)configHome.getAdminMBean(jmsServerName, "JMSServer", domainName);
         if (jmsServerMBean == null) {
            throw new JMSException("JMS server config mbean not found");
         } else {
            return jmsServerMBean;
         }
      } catch (Exception var5) {
         if (var5 instanceof JMSException) {
            throw (JMSException)var5;
         } else {
            JMSException j = new weblogic.jms.common.JMSException(var5.toString());
            throw j;
         }
      }
   }

   private static JMSDestinationMBean getDestinationMBean(Context ctx, String jmsServerName, String destName, String destinationType) throws JMSException {
      try {
         MBeanHome configHome = (MBeanHome)ctx.lookup("weblogic.management.adminhome");
         String domainName = configHome.getActiveDomain().getName();
         WebLogicObjectName parent = new WebLogicObjectName(jmsServerName, "JMSServer", domainName);
         WebLogicObjectName jmsDestinationObj = new WebLogicObjectName(destName, destinationType, domainName, parent);
         JMSDestinationMBean jmsDestination = (JMSDestinationMBean)configHome.getMBean(jmsDestinationObj);
         if (jmsDestination == null) {
            throw new weblogic.jms.common.JMSException("Destination " + destName + " not found");
         } else {
            return jmsDestination;
         }
      } catch (Exception var9) {
         if (var9 instanceof JMSException) {
            throw (JMSException)var9;
         } else {
            JMSException j = new weblogic.jms.common.JMSException(var9.toString());
            j.setLinkedException(var9);
            throw j;
         }
      }
   }
}
