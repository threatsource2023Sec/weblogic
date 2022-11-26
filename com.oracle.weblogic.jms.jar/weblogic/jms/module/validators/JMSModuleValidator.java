package weblogic.jms.module.validators;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.ClientParamsBean;
import weblogic.j2ee.descriptor.wl.DefaultDeliveryParamsBean;
import weblogic.j2ee.descriptor.wl.DeliveryFailureParamsBean;
import weblogic.j2ee.descriptor.wl.DeliveryParamsOverridesBean;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.DestinationKeyBean;
import weblogic.j2ee.descriptor.wl.DistributedDestinationBean;
import weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean;
import weblogic.j2ee.descriptor.wl.DistributedQueueBean;
import weblogic.j2ee.descriptor.wl.DistributedTopicBean;
import weblogic.j2ee.descriptor.wl.FlowControlParamsBean;
import weblogic.j2ee.descriptor.wl.ForeignConnectionFactoryBean;
import weblogic.j2ee.descriptor.wl.ForeignDestinationBean;
import weblogic.j2ee.descriptor.wl.ForeignServerBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean;
import weblogic.j2ee.descriptor.wl.NamedEntityBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.j2ee.descriptor.wl.QuotaBean;
import weblogic.j2ee.descriptor.wl.SAFDestinationBean;
import weblogic.j2ee.descriptor.wl.SAFErrorHandlingBean;
import weblogic.j2ee.descriptor.wl.SAFImportedDestinationsBean;
import weblogic.j2ee.descriptor.wl.SAFQueueBean;
import weblogic.j2ee.descriptor.wl.SAFTopicBean;
import weblogic.j2ee.descriptor.wl.TargetableBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.j2ee.descriptor.wl.ThresholdParamsBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedDestinationBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedQueueBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedTopicBean;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSLogger;
import weblogic.jms.deployer.DeployerConstants;
import weblogic.jms.extensions.JMSModuleHelper;
import weblogic.jms.extensions.Schedule;
import weblogic.jms.module.JMSBeanHelper;
import weblogic.jms.saf.IDBeanHandler;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.DomainMBeanValidator;
import weblogic.management.configuration.JMSConstants;
import weblogic.management.configuration.JMSLegalHelper;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.MigratableTargetMBean;
import weblogic.management.configuration.PathServiceMBean;
import weblogic.management.configuration.PersistentStoreMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.utils.AppDeploymentHelper;

@Service
@ContractsProvided({DomainMBeanValidator.class})
public class JMSModuleValidator implements DomainMBeanValidator {
   private static final String JMS_INITIALCONTEXT_FATORY = "weblogic.jms.WLInitialContextFactory";
   private static final String PHY_PROP = "PhysicalDestinationName";
   private static final int DQ_TYPE = 0;
   private static final int DT_TYPE = 1;
   private static final int DD_MAX_TYPE = 2;
   private static final int QUEUE_TYPE = 0;
   private static final int TOPIC_TYPE = 1;
   private static final int MAX_TYPE = 2;
   private static final String INTEROP_FILE_NAME = "interop-jms.xml";

   public static void validateJMSModule(JMSBean module) throws IllegalArgumentException {
      validateTemplates(module);
      validateDestinations(module);
      validateConnectionFactories(module);
      validateForeignServers(module);
      validateSAFErrorHandlings(module);
   }

   public static void validateTemplates(JMSBean bean) throws IllegalArgumentException {
      TemplateBean[] templateBeans = bean.getTemplates();

      for(int i = 0; i < templateBeans.length; ++i) {
         validateAMEPolicy(bean, (DescriptorBean)templateBeans[i]);
      }

   }

   private static void validateDistributedDestinations(TargetInfoMBean targetingInfo, BasicDeploymentMBean basic, JMSBean bean) throws IllegalArgumentException {
      validateDistributedQueues(targetingInfo, basic, bean);
      validateDistributedTopics(targetingInfo, basic, bean);
   }

   private static SubDeploymentMBean mySub(SubDeploymentMBean[] subs, TargetableBean target) {
      if (target.getSubDeploymentName() != null) {
         for(int i = 0; i < subs.length; ++i) {
            if (subs[i].getName().equals(target.getSubDeploymentName())) {
               return subs[i];
            }
         }
      }

      return null;
   }

   private static void sameCluster(DomainMBean domain, String name, Collection allTargets) {
      ClusterMBean cluster = null;
      Iterator iter = allTargets.iterator();

      while(iter.hasNext()) {
         String targetName = (String)iter.next();
         String debug = targetName;
         TargetMBean[] targets = null;
         Object target;
         if (targets == null) {
            if (domain.lookupJMSServer(targetName) != null) {
               targets = domain.lookupJMSServer(targetName).getTargets();
               debug = targetName + "; 140 did domain.lookupJMSServer " + (targets == null);
            }

            if (targets == null) {
               target = domain.lookupServer(targetName);
               if (target == null) {
                  target = domain.lookupMigratableTarget(targetName);
               }

               targets = new TargetMBean[]{(TargetMBean)target};
               debug = debug + "; 151 replace lookupServer " + (target == null);
            }
         }

         target = null;

         for(int i = 0; i < targets.length; ++i) {
            ClusterMBean myCluster;
            if (targets[i] instanceof ServerMBean) {
               myCluster = ((ServerMBean)targets[i]).getCluster();
            } else if (targets[i] instanceof MigratableTargetMBean) {
               myCluster = ((MigratableTargetMBean)targets[i]).getCluster();
            } else {
               if (!(targets[i] instanceof ClusterMBean)) {
                  throw new AssertionError("Nonsensical target " + targets[i] + " details " + debug + (targets[i] == null ? "; null " + i : "; [" + i + "] type " + targets[i].getClass().getName()));
               }

               myCluster = (ClusterMBean)targets[i];
            }

            if (cluster == null) {
               cluster = myCluster;
            } else if (cluster != myCluster) {
               throw new IllegalArgumentException(name + " may only be targeted to one cluster");
            }
         }
      }

   }

   private static void sameClusterAsSAFAgent(DomainMBean domain, String name, Collection allTargets) {
      ClusterMBean cluster = null;
      Iterator iter = allTargets.iterator();

      while(iter.hasNext()) {
         String targetName = (String)iter.next();
         String debug = targetName;
         TargetMBean[] targets = null;
         if (domain.lookupSAFAgent(targetName) != null) {
            targets = domain.lookupSAFAgent(targetName).getTargets();
            debug = targetName + "; 134 domain.lookupSAFAgent " + (targets == null);
         }

         ClusterMBean myCluster = null;

         for(int i = 0; i < targets.length; ++i) {
            if (targets[i] instanceof ServerMBean) {
               myCluster = ((ServerMBean)targets[i]).getCluster();
            } else if (targets[i] instanceof MigratableTargetMBean) {
               myCluster = ((MigratableTargetMBean)targets[i]).getCluster();
            } else {
               if (!(targets[i] instanceof ClusterMBean)) {
                  throw new AssertionError("Nonsensical target " + targets[i] + " details " + debug + (targets[i] == null ? "; null " + i : "; [" + i + "] type " + targets[i].getClass().getName()));
               }

               myCluster = (ClusterMBean)targets[i];
            }

            if (cluster == null) {
               cluster = myCluster;
            } else if (cluster != myCluster) {
               throw new IllegalArgumentException(name + " may only be targeted to one cluster");
            }
         }
      }

   }

   private static void validateRDTMembers(DomainMBean domain, String rdtName, String subDeploymentName, String moduleName, Collection allTargets) throws IllegalArgumentException {
      Iterator iter = allTargets.iterator();

      while(iter.hasNext()) {
         String targetName = (String)iter.next();
         TargetMBean[] targets = null;
         if (domain.lookupJMSServer(targetName) != null) {
            targets = domain.lookupJMSServer(targetName).getTargets();
            (new StringBuilder()).append(targetName).append("; 160 did domain.lookupJMSServer ").append(targets == null).toString();
         }

         for(int i = 0; i < targets.length; ++i) {
            if (targets[i] instanceof ClusterMBean) {
               throw new IllegalArgumentException(JMSExceptionLogger.logRDTNotSupportedOnClusteredJMSServerLoggable(rdtName, subDeploymentName, moduleName, targetName).getMessage());
            }
         }
      }

   }

   private static void validateImportedDestinationGroups(SubDeploymentMBean[] subs, BasicDeploymentMBean basic, JMSBean bean) throws IllegalArgumentException {
      SAFImportedDestinationsBean[] idgroups = bean.getSAFImportedDestinations();
      DomainMBean domain = JMSLegalHelper.getDomain(basic);
      WebLogicMBean deploymentScope = JMSModuleHelper.getDeploymentScope(basic);

      for(int i = 0; i < idgroups.length; ++i) {
         validateTargetingOptions(idgroups[i]);
         SubDeploymentMBean sub = mySub(subs, idgroups[i]);
         if (deploymentScope instanceof ResourceGroupTemplateMBean && idgroups[i].isDefaultTargetingEnabled()) {
            HashMap result = new HashMap();
            IDBeanHandler.fillWithMyTargets(idgroups[i].getName(), result, domain, basic.getTargets(), basic);
         }

         if (sub != null) {
            TargetMBean[] targets = sub.getTargets();
            if (targets != null && targets.length != 0) {
               for(int j = 0; j < targets.length; ++j) {
                  if (!(targets[j] instanceof ServerMBean) && !(targets[j] instanceof ClusterMBean) && !(targets[j] instanceof SAFAgentMBean)) {
                     throw new IllegalArgumentException(JMSExceptionLogger.logIllegalTargetTypeLoggable(idgroups[j].getName(), targets[j].getName(), sub.getName()).getMessage());
                  }

                  if (targets[j] instanceof SAFAgentMBean && "Receiving-only".equals(((SAFAgentMBean)targets[j]).getServiceType())) {
                     throw new IllegalArgumentException(JMSExceptionLogger.logIllegalAgentTypeLoggable(idgroups[j].getName(), targets[j].getName()).getMessage());
                  }
               }
            }

            HashMap result = new HashMap();
            String name = idgroups[i].getName();
            IDBeanHandler.fillWithMyTargets(name, result, domain, sub.getTargets(), basic);
            sameClusterAsSAFAgent(domain, name, result.values());
            validateImportedDestinations(idgroups[i], sub, bean);
         }
      }

   }

   private static void validateUniformDistributedDestinations(SubDeploymentMBean[] subs, BasicDeploymentMBean basic, JMSBean bean, boolean isJMSResDefn) throws IllegalArgumentException {
      DomainMBean domain = JMSLegalHelper.getDomain(basic);
      WebLogicMBean deploymentScope = JMSModuleHelper.getDeploymentScope(basic);
      UniformDistributedQueueBean[] udqs = bean.getUniformDistributedQueues();

      for(int i = 0; i < udqs.length; ++i) {
         validateTargetingOptions(udqs[i]);
         Map result = new HashMap();
         String entityName = isJMSResDefn ? udqs[i].getJNDIName() : udqs[i].getName();
         if (deploymentScope instanceof ResourceGroupTemplateMBean && udqs[i].isDefaultTargetingEnabled()) {
            JMSModuleHelper.uddFillWithMyTargets(result, domain, (TargetMBean[])basic.getTargets(), basic, "Uniform Distributed Queue", entityName, isJMSResDefn);
         }

         SubDeploymentMBean sub = mySub(subs, udqs[i]);
         if (sub != null) {
            validateSubdTargetDistPolicy(udqs[i], sub, basic.getName(), "Distributed", deploymentScope);
            result = new HashMap();
            JMSModuleHelper.uddFillWithMyTargets(result, domain, (SubDeploymentMBean)sub, basic, "Uniform Distributed Queue", entityName, isJMSResDefn);
            sameCluster(domain, udqs[i].getName(), result.values());
            validateUDDAgainstClusteredJMSServer(domain, udqs[i].getName(), sub.getName(), basic.getName(), result.values());
            validateErrorDestination(udqs[i], (TargetInfoMBean)null, true);
         }
      }

      UniformDistributedTopicBean[] udts = bean.getUniformDistributedTopics();

      for(int i = 0; i < udts.length; ++i) {
         validateTargetingOptions(udts[i]);
         Map result = new HashMap();
         String entityName = isJMSResDefn ? udts[i].getJNDIName() : udts[i].getName();
         if (deploymentScope instanceof ResourceGroupTemplateMBean && udts[i].isDefaultTargetingEnabled()) {
            JMSModuleHelper.uddFillWithMyTargets(result, domain, (TargetMBean[])basic.getTargets(), basic, "Uniform Distributed Topic", entityName, isJMSResDefn);
         }

         SubDeploymentMBean sub = mySub(subs, udts[i]);
         if (sub != null) {
            validateSubdTargetDistPolicy(udts[i], sub, basic.getName(), "Distributed", deploymentScope);
            result = new HashMap();
            JMSModuleHelper.uddFillWithMyTargets(result, domain, (SubDeploymentMBean)sub, basic, "Uniform Distributed Topic", entityName, isJMSResDefn);
            sameCluster(domain, udts[i].getName(), result.values());
            if (udts[i].getForwardingPolicy().equals(JMSConstants.FORWARDING_POLICY_REPLICATED)) {
               if (deploymentScope != null && deploymentScope instanceof ResourceGroupTemplateMBean) {
                  String rgGroupInfo = " Resource Group Template " + deploymentScope.getName();
                  if (deploymentScope instanceof ResourceGroupMBean) {
                     rgGroupInfo = " Resource Group " + deploymentScope.getName();
                  }

                  throw new IllegalArgumentException(JMSExceptionLogger.logRDTopicNotSupportedLoggable(udts[i].getName(), basic.getName(), rgGroupInfo).getMessage());
               }

               validateRDTMembers(domain, udts[i].getName(), sub.getName(), basic.getName(), result.values());
            }

            validateUDDAgainstClusteredJMSServer(domain, udts[i].getName(), sub.getName(), basic.getName(), result.values());
            validateErrorDestination(udts[i], (TargetInfoMBean)null, true);
         }
      }

   }

   private static void validateUDDAgainstClusteredJMSServer(DomainMBean domain, String uddName, String subDeploymentName, String moduleName, Collection allTargets) throws IllegalArgumentException {
      Iterator iter = allTargets.iterator();

      while(iter.hasNext()) {
         String targetName = (String)iter.next();
         TargetMBean[] targets = null;
         JMSServerMBean jmsServer = domain.lookupJMSServer(targetName);
         if (jmsServer != null) {
            targets = jmsServer.getTargets();
            (new StringBuilder()).append(targetName).append("; 170 did domain.lookupJMSServer ").append(targets == null).toString();
         }

         for(int i = 0; i < targets.length; ++i) {
            if (targets[i] instanceof ClusterMBean) {
               PersistentStoreMBean store = jmsServer.getPersistentStore();
               if (store != null && !store.getDistributionPolicy().equalsIgnoreCase("Distributed")) {
                  throw new IllegalArgumentException(JMSExceptionLogger.logUDDNotSupportedOnClusteredJMSServerWithoutDistributedPolicyLoggable(uddName, subDeploymentName, moduleName, targetName).getMessage());
               }
            }
         }
      }

   }

   private static String getDestinationTargetName(DestinationBean dbean, TargetInfoMBean targetingInfo) {
      if (targetingInfo == null) {
         return null;
      } else {
         String destDeploymentName = dbean.getSubDeploymentName();
         SubDeploymentMBean destSubDeployment = null;
         if (targetingInfo instanceof BasicDeploymentMBean) {
            destSubDeployment = ((BasicDeploymentMBean)targetingInfo).lookupSubDeployment(destDeploymentName);
         } else {
            destSubDeployment = ((SubDeploymentMBean)targetingInfo).lookupSubDeployment(destDeploymentName);
         }

         if (destSubDeployment == null) {
            return null;
         } else {
            TargetMBean[] destTargets = destSubDeployment.getTargets();
            return destTargets.length != 1 ? null : destTargets[0].getName();
         }
      }
   }

   static void validateDistributedDestinationUnderRG(DistributedDestinationBean[] dest, WebLogicMBean deploymentScope, BasicDeploymentMBean basic) {
      String rgGroupInfo = "";
      if (deploymentScope != null && deploymentScope instanceof ResourceGroupTemplateMBean) {
         rgGroupInfo = " Resource Group Template " + deploymentScope.getName();
         if (deploymentScope instanceof ResourceGroupMBean) {
            rgGroupInfo = " Resource Group " + deploymentScope.getName();
         }

         if (dest != null && dest.length > 0) {
            throw new IllegalArgumentException(JMSExceptionLogger.logDistributedDestNotSupportedLoggable(basic.getName(), rgGroupInfo).getMessage());
         }
      }

   }

   private static void validateDistributedQueues(TargetInfoMBean targetingInfo, BasicDeploymentMBean basic, JMSBean bean) throws IllegalArgumentException {
      boolean isInterop = false;
      boolean sawUOWHandlingPolicy = false;
      String valueUOWHandlingPolicy = null;
      WebLogicMBean deploymentScope = JMSModuleHelper.getDeploymentScope(basic);
      DistributedQueueBean[] dqs = bean.getDistributedQueues();
      validateDistributedDestinationUnderRG(dqs, deploymentScope, basic);
      Map allMembers = new HashMap();

      for(int outer = 0; outer < dqs.length; ++outer) {
         DistributedQueueBean dq = dqs[outer];
         DistributedDestinationMemberBean[] dqms = dq.getDistributedQueueMembers();
         List allTargets = new LinkedList();

         for(int lcv = 0; lcv < dqms.length; ++lcv) {
            DistributedDestinationMemberBean dqm = dqms[lcv];
            if (!isInterop && dqm.isSet("PhysicalDestinationName")) {
               throw new IllegalArgumentException(JMSExceptionLogger.logUseOfInteropFieldLoggable(basic.getName(), dq.getName(), dqm.getName(), dqm.getPhysicalDestinationName()).getMessage());
            }

            String queueName = dqm.getPhysicalDestinationName();
            QueueBean queue = bean.lookupQueue(queueName);
            if (!isInterop) {
               if (queue == null) {
                  throw new IllegalArgumentException(JMSExceptionLogger.logPhysicalDestinationNotPresentLoggable(basic.getName(), dq.getName(), dqm.getName(), dqm.getPhysicalDestinationName()).getMessage());
               }
            } else if (dqm.isSet("PhysicalDestinationName") && queue == null) {
               throw new IllegalArgumentException(JMSExceptionLogger.logPhysicalDestinationNotPresentLoggable(basic.getName(), dq.getName(), dqm.getName(), dqm.getPhysicalDestinationName()).getMessage());
            }

            String myUOWHandlingPolicy;
            if (queue != null) {
               myUOWHandlingPolicy = queue.getUnitOfWorkHandlingPolicy();
               if (sawUOWHandlingPolicy) {
                  if (!valueUOWHandlingPolicy.equals(myUOWHandlingPolicy)) {
                     throw new IllegalArgumentException("DQ Member " + dqm.getName() + " has a Unit Of Work Handling Policy which is inconsistent with other members");
                  }
               } else {
                  valueUOWHandlingPolicy = myUOWHandlingPolicy;
                  sawUOWHandlingPolicy = true;
               }
            }

            myUOWHandlingPolicy = (String)allMembers.put(dqm.getName(), dq.getName());
            if (myUOWHandlingPolicy != null) {
               throw new IllegalArgumentException("DQ Member " + dqm.getName() + " is part of both " + myUOWHandlingPolicy + " and " + dq.getName());
            }

            String destTargetName = getDestinationTargetName(queue, targetingInfo);
            if (destTargetName != null) {
               allTargets.add(destTargetName);
            }
         }

         DomainMBean domain = JMSLegalHelper.getDomain(basic);
         sameCluster(domain, dq.getName(), allTargets);
      }

   }

   private static void validateDistributedTopics(TargetInfoMBean targetingInfo, BasicDeploymentMBean basic, JMSBean bean) throws IllegalArgumentException {
      boolean isInterop = false;
      boolean sawUOWHandlingPolicy = false;
      String valueUOWHandlingPolicy = null;
      DistributedTopicBean[] dts = bean.getDistributedTopics();
      WebLogicMBean deploymentScope = JMSModuleHelper.getDeploymentScope(basic);
      validateDistributedDestinationUnderRG(dts, deploymentScope, basic);
      Map allMembers = new HashMap();

      for(int outer = 0; outer < dts.length; ++outer) {
         DistributedTopicBean dt = dts[outer];
         DistributedDestinationMemberBean[] dtms = dt.getDistributedTopicMembers();
         List allTargets = new LinkedList();

         for(int lcv = 0; lcv < dtms.length; ++lcv) {
            DistributedDestinationMemberBean dtm = dtms[lcv];
            if (!isInterop && dtm.isSet("PhysicalDestinationName")) {
               throw new IllegalArgumentException(JMSExceptionLogger.logUseOfInteropFieldLoggable(basic.getName(), dt.getName(), dtm.getName(), dtm.getPhysicalDestinationName()).getMessage());
            }

            String topicName = dtm.getPhysicalDestinationName();
            TopicBean topic = bean.lookupTopic(topicName);
            if (!isInterop) {
               if (topic == null) {
                  throw new IllegalArgumentException(JMSExceptionLogger.logPhysicalDestinationNotPresentLoggable(basic.getName(), dt.getName(), dtm.getName(), dtm.getPhysicalDestinationName()).getMessage());
               }
            } else if (dtm.isSet("PhysicalDestinationName") && topic == null) {
               throw new IllegalArgumentException(JMSExceptionLogger.logPhysicalDestinationNotPresentLoggable(basic.getName(), dt.getName(), dtm.getName(), dtm.getPhysicalDestinationName()).getMessage());
            }

            String myUOWHandlingPolicy;
            if (topic != null) {
               myUOWHandlingPolicy = topic.getUnitOfWorkHandlingPolicy();
               if (sawUOWHandlingPolicy) {
                  if (!valueUOWHandlingPolicy.equals(myUOWHandlingPolicy)) {
                     throw new IllegalArgumentException("DQ Member " + dtm.getName() + " has a Unit Of Work Handling Policy which is inconsistent with other members");
                  }
               } else {
                  valueUOWHandlingPolicy = myUOWHandlingPolicy;
                  sawUOWHandlingPolicy = true;
               }
            }

            myUOWHandlingPolicy = (String)allMembers.put(dtm.getName(), dt.getName());
            if (myUOWHandlingPolicy != null) {
               throw new IllegalArgumentException("DT Member " + dtm.getName() + " is part of both " + myUOWHandlingPolicy + " and " + dt.getName());
            }

            String destTargetName = getDestinationTargetName(topic, targetingInfo);
            if (destTargetName != null) {
               allTargets.add(destTargetName);
            }
         }

         DomainMBean domain = JMSLegalHelper.getDomain(basic);
         sameCluster(domain, dt.getName(), allTargets);
      }

   }

   private static void validateDestinations(JMSBean bean) throws IllegalArgumentException {
      HashMap uniqueNames = new HashMap();

      int lcv;
      for(lcv = 0; lcv < bean.getQueues().length; ++lcv) {
         validateQueue(bean, bean.getQueues()[lcv], uniqueNames);
      }

      for(lcv = 0; lcv < bean.getTopics().length; ++lcv) {
         validateTopic(bean, bean.getTopics()[lcv], uniqueNames);
      }

      for(lcv = 0; lcv < bean.getUniformDistributedQueues().length; ++lcv) {
         validateQueue(bean, bean.getUniformDistributedQueues()[lcv], uniqueNames);
      }

      for(lcv = 0; lcv < bean.getUniformDistributedTopics().length; ++lcv) {
         validateTopic(bean, bean.getUniformDistributedTopics()[lcv], uniqueNames);
      }

      for(lcv = 0; lcv < 2; ++lcv) {
         Object namedTypes;
         String typeString;
         switch (lcv) {
            case 0:
               namedTypes = bean.getDistributedQueues();
               typeString = "DistributedQueue";
               break;
            case 1:
               namedTypes = bean.getDistributedTopics();
               typeString = "DistributedTopic";
               break;
            default:
               throw new IllegalArgumentException("Unknown type: " + lcv);
         }

         for(int inner = 0; inner < ((Object[])namedTypes).length; ++inner) {
            NamedEntityBean namedType = ((Object[])namedTypes)[inner];
            if (uniqueNames.containsKey(((NamedEntityBean)namedType).getName())) {
               String key = ((NamedEntityBean)namedType).getName();
               String value = (String)uniqueNames.get(key);
               throw new IllegalArgumentException("The destination " + key + " has a duplicate name with an entity of type " + value);
            }

            uniqueNames.put(((NamedEntityBean)namedType).getName(), typeString);
         }
      }

   }

   private static void validateQueue(JMSBean bean, QueueBean queueBean, HashMap uniqueNames) throws IllegalArgumentException {
      if (uniqueNames.containsKey(queueBean.getName())) {
         String key = queueBean.getName();
         String value = (String)uniqueNames.get(key);
         throw new IllegalArgumentException("The destination " + key + " has a duplicate name with an entity of type " + value);
      } else {
         uniqueNames.put(queueBean.getName(), "Queue");
         validateDestCommon(bean, queueBean);
      }
   }

   private static void validateTopic(JMSBean bean, TopicBean topicBean, HashMap uniqueNames) throws IllegalArgumentException {
      if (uniqueNames.containsKey(topicBean.getName())) {
         String key = topicBean.getName();
         String value = (String)uniqueNames.get(key);
         throw new IllegalArgumentException("The destination " + key + " has a duplicate name with an entity of type " + value);
      } else {
         uniqueNames.put(topicBean.getName(), "Topic");
         validateDestCommon(bean, topicBean);
         validateMulticastAddress(topicBean.getMulticast().getMulticastAddress());
      }
   }

   private static void validateDestCommon(JMSBean bean, DestinationBean dbean) throws IllegalArgumentException {
      validateLegalOrderOfDestinationKeys(bean, dbean);
      validateThresholds(dbean);
      validateQuota(dbean);
      validateDeliveryParamsOverrides(dbean);
      validateDeliveryFailureParams(bean, dbean);
   }

   private static void validateThresholds(DestinationBean dbean) throws IllegalArgumentException {
      validBytes(dbean);
      validMessages(dbean);
   }

   private static void validateQuota(DestinationBean dbean) throws IllegalArgumentException {
      validBytesMaximum(dbean);
      validMessagesMaximum(dbean);
   }

   private static void validBytes(DestinationBean dbean) throws IllegalArgumentException {
      ThresholdParamsBean bean = dbean.getThresholds();
      long bytesLow = bean.getBytesLow();
      long bytesHigh = bean.getBytesHigh();
      long bytesMaximum = Long.MAX_VALUE;
      QuotaBean quotaBean = dbean.getQuota();
      if (quotaBean != null) {
         bytesMaximum = quotaBean.getBytesMaximum();
      }

      if (bytesHigh != Long.MAX_VALUE && bytesLow >= bytesHigh && bytesMaximum < bytesHigh) {
         throw new IllegalArgumentException("Invalid bytes threshold parameter for destination " + dbean.getName() + ", bytes low=" + bytesLow + ", bytes high=" + bytesHigh + ", bytes maximum=" + bytesMaximum);
      }
   }

   private static void validMessages(DestinationBean dbean) throws IllegalArgumentException {
      ThresholdParamsBean bean = dbean.getThresholds();
      long messagesLow = bean.getMessagesLow();
      long messagesHigh = bean.getMessagesHigh();
      long messagesMaximum = Long.MAX_VALUE;
      QuotaBean quotaBean = dbean.getQuota();
      if (quotaBean != null) {
         messagesMaximum = quotaBean.getMessagesMaximum();
      }

      if (messagesHigh != Long.MAX_VALUE && messagesLow >= messagesHigh && messagesMaximum < messagesHigh) {
         throw new IllegalArgumentException("Invalid messages threshold for destination " + dbean.getName() + ", messages low=" + messagesLow + ", messages high=" + messagesHigh + ", messages maximum=" + messagesMaximum);
      }
   }

   private static void validBytesMaximum(DestinationBean dbean) throws IllegalArgumentException {
      long bytesMaximum = Long.MAX_VALUE;
      QuotaBean quotaBean = dbean.getQuota();
      if (quotaBean != null) {
         bytesMaximum = quotaBean.getBytesMaximum();
      }

      if (bytesMaximum != Long.MAX_VALUE && (bytesMaximum < 0L || bytesMaximum > Long.MAX_VALUE)) {
         throw new IllegalArgumentException("Invalid bytes maximum for destination " + dbean.getName() + ", bytes maximum=" + bytesMaximum);
      }
   }

   private static void validMessagesMaximum(DestinationBean dbean) throws IllegalArgumentException {
      long messagesMaximum = Long.MAX_VALUE;
      QuotaBean quotaBean = dbean.getQuota();
      if (quotaBean != null) {
         messagesMaximum = quotaBean.getMessagesMaximum();
      }

      if (messagesMaximum != Long.MAX_VALUE && (messagesMaximum < 0L || messagesMaximum > Long.MAX_VALUE)) {
         throw new IllegalArgumentException("Invalid messages maximum for destination " + dbean.getName() + ", messages maximum=" + messagesMaximum);
      }
   }

   private static void validateLegalOrderOfDestinationKeys(JMSBean bean, DestinationBean dbean) {
      String[] destinationKeyNames = dbean.getDestinationKeys();
      DestinationKeyBean[] destinationKeys = new DestinationKeyBean[destinationKeyNames.length];

      int i;
      for(i = 0; i < destinationKeyNames.length; ++i) {
         DestinationKeyBean dkBean = bean.lookupDestinationKey(destinationKeyNames[i]);
         if (dkBean == null) {
            throw new IllegalArgumentException("Destination key " + destinationKeyNames[i] + " used by destination " + dbean.getName() + " not found");
         }

         destinationKeys[i] = dkBean;
      }

      if (destinationKeys.length > 1) {
         for(i = 0; i < destinationKeys.length - 1; ++i) {
            if (destinationKeys[i].getProperty().equals("JMSMessageID")) {
               throw new IllegalArgumentException("Invalid destination key order for destination " + dbean.getName() + ", JMSMessageID must be specified as last key ");
            }
         }
      }

   }

   public static void validateDestinationKeyProperty(DestinationKeyBean dkb) throws IllegalArgumentException {
      String property = dkb.getProperty();
      if (property == null) {
         throw new IllegalArgumentException("Invalid property value " + property + " found for destination key " + dkb.getName());
      } else if (property.trim().length() == 0) {
         throw new IllegalArgumentException("Invalid property value \"" + property + "\" found for destination key " + dkb.getName());
      } else if (property.startsWith("JMS") && !property.equals("JMSMessageID") && !property.equals("JMSTimestamp") && !property.equals("JMSCorrelationID") && !property.equals("JMSPriority") && !property.equals("JMSExpiration") && !property.equals("JMSType") && !property.equals("JMSRedelivered") && !property.equals("JMSDeliveryTime") && !property.equals("JMS_BEA_Size") && !property.equals("JMS_BEA_UnitOfOrder")) {
         throw new IllegalArgumentException("Invalid JMS Header property value " + property + " found for destination key " + dkb.getName());
      }
   }

   public static void validateDeliveryParamsOverrides(DestinationBean dbean) throws IllegalArgumentException {
      validateTimeToDeliverOverride(dbean);
   }

   private static boolean isTimeToDeliverOverrideValid(String ttlOverride) throws ParseException {
      if (ttlOverride == null) {
         return true;
      } else {
         try {
            long l = Long.parseLong(ttlOverride);
            return l >= -1L;
         } catch (NumberFormatException var3) {
            Schedule.parseSchedule(ttlOverride);
            return true;
         }
      }
   }

   public static void validateTimeToDeliverOverride(DestinationBean dbean) {
      DeliveryParamsOverridesBean bean = dbean.getDeliveryParamsOverrides();
      String ttlOverride = bean.getTimeToDeliver();

      try {
         if (!isTimeToDeliverOverrideValid(ttlOverride)) {
            throw new IllegalArgumentException(JMSTextTextFormatter.getInstance().getIllegalTimeToDeliverOverride());
         }
      } catch (ParseException var4) {
         throw new IllegalArgumentException(JMSTextTextFormatter.getInstance().getIllegalTimeToDeliverOverrideWithException(var4.toString()));
      }
   }

   public static void validateTimeToDeliverOverride(String ttlOverride) {
      try {
         if (!isTimeToDeliverOverrideValid(ttlOverride)) {
            throw new IllegalArgumentException(JMSTextTextFormatter.getInstance().getIllegalTimeToDeliverOverride());
         }
      } catch (ParseException var2) {
         throw new IllegalArgumentException(JMSTextTextFormatter.getInstance().getIllegalTimeToDeliverOverrideWithException(var2.toString()));
      }
   }

   private static void validateDeliveryFailureParams(JMSBean bean, DestinationBean dbean) throws IllegalArgumentException {
      validateAMEPolicy(bean, (DescriptorBean)dbean);
      DeliveryFailureParamsBean dfpb = dbean.getDeliveryFailureParams();
      if (dfpb.getExpirationPolicy().equals("Redirect") && dfpb.getErrorDestination() == null) {
         throw new IllegalArgumentException("Can not use \"Redirect\" as message expiration policy if error destination is not defined for destination " + dbean.getName());
      }
   }

   private static void validateErrorDestination(DestinationBean dbean, TargetInfoMBean targetingInfo, boolean uniform) throws IllegalArgumentException {
      if (targetingInfo != null || uniform) {
         String destDeploymentName = dbean.getSubDeploymentName();
         String edDeploymentName = null;
         DestinationBean edBean = dbean.getDeliveryFailureParams().getErrorDestination();
         if (edBean != null) {
            if (edBean instanceof UniformDistributedDestinationBean) {
               if (!uniform) {
                  throw new IllegalArgumentException("Invalid Error destination for destination " + dbean.getName() + ", a normal destination may not have a uniform distributed destination as an error destination");
               }
            } else if (uniform) {
               throw new IllegalArgumentException("Invalid Error destination for destination " + dbean.getName() + ", a uniform distributed destination must have a uniform distributed destination as an error destination");
            }

            String edName = edBean.getName();
            if (edName.equals(dbean.getName())) {
               throw new IllegalArgumentException("Invalid Error destination for destination " + dbean.getName() + ", a destination cannot set itself as its error destination");
            } else {
               edDeploymentName = edBean.getSubDeploymentName();
               if (!destDeploymentName.equals(edDeploymentName) && (!edBean.isDefaultTargetingEnabled() || !dbean.isDefaultTargetingEnabled())) {
                  if (uniform) {
                     throw new IllegalArgumentException("Invalid Error destination for destination " + dbean.getName() + ", a uniform distributed destination must have an error destination which is targeted identically to itself (same subdeployment name or default targeting on both");
                  } else {
                     SubDeploymentMBean edSubDeployment = null;
                     SubDeploymentMBean destSubDeployment = null;
                     if (targetingInfo instanceof BasicDeploymentMBean) {
                        edSubDeployment = ((BasicDeploymentMBean)targetingInfo).lookupSubDeployment(edDeploymentName);
                        destSubDeployment = ((BasicDeploymentMBean)targetingInfo).lookupSubDeployment(destDeploymentName);
                     } else {
                        edSubDeployment = ((SubDeploymentMBean)targetingInfo).lookupSubDeployment(edDeploymentName);
                        destSubDeployment = ((SubDeploymentMBean)targetingInfo).lookupSubDeployment(destDeploymentName);
                     }

                     if (edSubDeployment != null || destSubDeployment != null) {
                        if (edSubDeployment == null && destSubDeployment != null || edSubDeployment != null && destSubDeployment == null) {
                           throw new IllegalArgumentException("Sub Deploymet does not exist for either error destination " + edName + " or the destination " + dbean.getName());
                        } else {
                           TargetMBean[] edTargets = edSubDeployment.getTargets();
                           TargetMBean[] destTargets = destSubDeployment.getTargets();
                           if (edTargets.length > 0 || destTargets.length > 0) {
                              if ((edTargets.length != 1 || destTargets.length > 0) && (edTargets.length > 0 || destTargets.length != 1)) {
                                 if (!edTargets[0].getName().equals(destTargets[0].getName())) {
                                    throw new IllegalArgumentException("Error destination " + edName + " must be targeted to the same JMSServer as the destination " + dbean.getName());
                                 }
                              } else {
                                 throw new IllegalArgumentException("The SubDeployment of either destination " + dbean.getName() + " or the error destination " + dbean.getName() + " is not currently targeted");
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static void validateAMEPolicy(JMSBean bean, DescriptorBean dbean) throws IllegalArgumentException {
      DeliveryFailureParamsBean dfb = null;
      if (!(dbean instanceof DestinationBean) && !(dbean instanceof TemplateBean)) {
         if (dbean instanceof DestinationBean) {
            dfb = ((DestinationBean)dbean).getDeliveryFailureParams();
         } else if (dbean instanceof TemplateBean) {
            dfb = ((TemplateBean)dbean).getDeliveryFailureParams();
         }

         String amePolicy = dfb.getExpirationPolicy();
         if (amePolicy != null && (!amePolicy.equalsIgnoreCase("Discard") || !amePolicy.equalsIgnoreCase("Redirect") || !amePolicy.equalsIgnoreCase("Log"))) {
            throw new IllegalArgumentException("Invalid active message expiration policy for destination " + ((NamedEntityBean)dbean).getName() + ", expiration policy=" + amePolicy);
         } else {
            if (dbean instanceof DestinationBean) {
               if (amePolicy != null && amePolicy.equalsIgnoreCase("Redirect")) {
                  DestinationBean edBean = ((DestinationBean)dbean).getDeliveryFailureParams().getErrorDestination();
                  if (edBean == null) {
                     throw new IllegalArgumentException("Invalid active message expiration policy for destination " + ((NamedEntityBean)dbean).getName() + ", expiration policy=" + amePolicy + ", no error destination found");
                  }
               }
            } else if (dbean instanceof TemplateBean) {
               TemplateBean template = (TemplateBean)dbean;
               if (amePolicy != null && amePolicy.equalsIgnoreCase("Redirect")) {
                  DestinationBean edBean = template.getDeliveryFailureParams().getErrorDestination();
                  if (edBean == null) {
                     DestinationBean[] dbeans = JMSBeanHelper.findAllInheritedDestinations(template.getName(), bean);
                     if (dbeans != null) {
                        for(int i = 0; i < dbeans.length; ++i) {
                           edBean = dbeans[i].getDeliveryFailureParams().getErrorDestination();
                           if (edBean == null) {
                              throw new IllegalArgumentException("Invalid active message expiration policy for destination " + ((NamedEntityBean)dbean).getName() + ", expiration policy=" + amePolicy + ", no error destination found");
                           }
                        }
                     }
                  }
               }
            }

         }
      }
   }

   public static void validateMulticastAddress(String address) throws IllegalArgumentException {
      if (address != null && !address.equals("")) {
         for(int j = 0; j < 4; ++j) {
            int index = address.indexOf(".");
            if (index == -1 && j < 3) {
               throw new IllegalArgumentException(JMSTextTextFormatter.getInstance().InvalidMulticastAddress(address));
            }

            if (j == 3) {
               index = address.length();
            }

            for(int i = 0; i < index; ++i) {
               if (!Character.isDigit(address.charAt(i))) {
                  throw new IllegalArgumentException(JMSTextTextFormatter.getInstance().InvalidMulticastAddress(address));
               }
            }

            if (j < 3) {
               address = address.substring(index + 1);
            }
         }
      }

   }

   public static void validateCFJNDIName(String jndiName) throws IllegalArgumentException {
      if (jndiName != null && jndiName.length() != 0) {
         DeployerConstants.DefaultNames[] var1 = DeployerConstants.DefaultNames.values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            DeployerConstants.DefaultNames name = var1[var3];
            if (jndiName.equalsIgnoreCase(name.getJndiName())) {
               throw new IllegalArgumentException(JMSTextTextFormatter.getInstance().getJMSCFJNDIConflictWithDefaultsException(jndiName));
            }
         }

      }
   }

   public static void validateCFName(String name) throws IllegalArgumentException {
      if (name != null && name.length() != 0) {
         DeployerConstants.DefaultNames[] var1 = DeployerConstants.DefaultNames.values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            DeployerConstants.DefaultNames dcname = var1[var3];
            if (name.equalsIgnoreCase(dcname.getCFName())) {
               throw new IllegalArgumentException(JMSTextTextFormatter.getInstance().getJMSCFConflictWithDefaultsException(name));
            }
         }

      }
   }

   public static void validateTopicSubscriptionMessagesLimit(Long limit) {
      long messagesLimit = limit;
      if (messagesLimit < -1L || messagesLimit == 0L) {
         throw new IllegalArgumentException(JMSTextTextFormatter.getInstance().getInvalidTopicSubscriptionMessagesLimit());
      }
   }

   private static SubDeploymentMBean[] getSubDeploymentsFromTargetInfo(TargetInfoMBean targetingInfo) {
      if (targetingInfo == null) {
         return new SubDeploymentMBean[0];
      } else if (targetingInfo instanceof BasicDeploymentMBean) {
         return ((BasicDeploymentMBean)targetingInfo).getSubDeployments();
      } else {
         return targetingInfo instanceof SubDeploymentMBean ? ((SubDeploymentMBean)targetingInfo).getSubDeployments() : new SubDeploymentMBean[0];
      }
   }

   private static boolean isTargetASubTarget(TargetMBean superTarget, TargetMBean subTarget) {
      boolean isSuperCluster = superTarget instanceof ClusterMBean;
      ServerMBean serverTarget = null;
      TargetMBean[] jmsServerTargets;
      if (subTarget instanceof SAFAgentMBean) {
         SAFAgentMBean safAgent = (SAFAgentMBean)subTarget;
         jmsServerTargets = safAgent.getTargets();
         if (jmsServerTargets.length <= 0) {
            return true;
         } else {
            for(int i = 0; i < jmsServerTargets.length; ++i) {
               TargetMBean safAgentTarget = jmsServerTargets[i];
               if (safAgentTarget instanceof ServerMBean) {
                  serverTarget = (ServerMBean)safAgentTarget;
                  if (!isSingleServerTargetASubTarget(isSuperCluster, serverTarget, superTarget, subTarget)) {
                     return false;
                  }
               } else if (safAgentTarget instanceof ClusterMBean) {
                  if (!isSuperCluster) {
                     return false;
                  }

                  if (!superTarget.getName().equals(safAgentTarget.getName())) {
                     return false;
                  }

                  return true;
               }
            }

            return true;
         }
      } else {
         if (subTarget instanceof JMSServerMBean) {
            JMSServerMBean jmsServer = (JMSServerMBean)subTarget;
            jmsServerTargets = jmsServer.getTargets();
            if (jmsServerTargets.length <= 0) {
               return true;
            }

            TargetMBean jmsServerTarget = jmsServerTargets[0];
            if (jmsServerTarget instanceof MigratableTargetMBean) {
               if (!isSuperCluster) {
                  return false;
               }

               MigratableTargetMBean migratableTarget = (MigratableTargetMBean)jmsServerTarget;
               TargetMBean jmsServerTarget = migratableTarget.getCluster();
               if (jmsServerTarget == null) {
                  return false;
               }

               if (!superTarget.getName().equals(jmsServerTarget.getName())) {
                  return false;
               }

               return true;
            }

            if (jmsServerTarget instanceof ClusterMBean) {
               if (!isSuperCluster) {
                  return false;
               }

               if (!superTarget.getName().equals(jmsServerTarget.getName())) {
                  return false;
               }

               return true;
            }

            if (!(jmsServerTarget instanceof ServerMBean)) {
               return false;
            }

            serverTarget = (ServerMBean)jmsServerTarget;
         }

         return isSingleServerTargetASubTarget(isSuperCluster, serverTarget, superTarget, subTarget);
      }
   }

   private static boolean isSingleServerTargetASubTarget(boolean isSuperCluster, ServerMBean serverTarget, TargetMBean superTarget, TargetMBean subTarget) {
      if (serverTarget == null && subTarget instanceof ServerMBean) {
         serverTarget = (ServerMBean)subTarget;
      }

      if (serverTarget != null) {
         if (isSuperCluster) {
            return isMatchingCluster(superTarget, serverTarget);
         } else {
            return superTarget.getName().equals(serverTarget.getName());
         }
      } else {
         return !isSuperCluster ? false : superTarget.getName().equals(subTarget.getName());
      }
   }

   private static boolean isMatchingCluster(TargetMBean superTarget, ServerMBean serverTarget) {
      ClusterMBean serverCluster = null;
      if (serverTarget != null) {
         serverCluster = serverTarget.getCluster();
      }

      if (serverCluster == null) {
         return false;
      } else {
         return superTarget.getName().equals(serverCluster.getName());
      }
   }

   private static void validateTargetingHierarchy(BasicDeploymentMBean basic, TargetInfoMBean targetingInfo, JMSBean jmsBean, boolean isJMSResourceDefn) throws IllegalArgumentException {
      if (targetingInfo != null) {
         TargetMBean[] superset = basic.getTargets();
         List basicDeploymentTargets = new ArrayList();
         TargetMBean[] var6 = superset;
         int var7 = superset.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            TargetMBean t = var6[var8];
            if (t instanceof VirtualHostMBean) {
               basicDeploymentTargets.addAll(Arrays.asList(((VirtualHostMBean)t).getTargets()));
            } else if (t instanceof VirtualTargetMBean) {
               basicDeploymentTargets.addAll(Arrays.asList(((VirtualTargetMBean)t).getTargets()));
            } else {
               basicDeploymentTargets.add(t);
            }
         }

         superset = (TargetMBean[])basicDeploymentTargets.toArray(new TargetMBean[0]);
         if (superset.length > 0) {
            SubDeploymentMBean[] subDeployments = getSubDeploymentsFromTargetInfo(targetingInfo);
            HashSet jmsServerMap = new HashSet();
            HashSet wlsServerMap = new HashSet();
            HashSet clusterMap = new HashSet();
            HashSet safAgentMap = new HashSet();

            for(int lcv = 0; lcv < subDeployments.length; ++lcv) {
               SubDeploymentMBean subDeployment = subDeployments[lcv];
               TargetMBean[] subset = subDeployment.getTargets();
               List subDeploymentTargets = new ArrayList();
               TargetMBean[] var15 = subset;
               int var16 = subset.length;

               TargetMBean exampleFailedSuperTarget;
               for(int var17 = 0; var17 < var16; ++var17) {
                  exampleFailedSuperTarget = var15[var17];
                  if (exampleFailedSuperTarget instanceof VirtualHostMBean) {
                     subDeploymentTargets.addAll(Arrays.asList(((VirtualHostMBean)exampleFailedSuperTarget).getTargets()));
                  } else if (exampleFailedSuperTarget instanceof VirtualTargetMBean) {
                     subDeploymentTargets.addAll(Arrays.asList(((VirtualTargetMBean)exampleFailedSuperTarget).getTargets()));
                  } else {
                     subDeploymentTargets.add(exampleFailedSuperTarget);
                  }
               }

               subset = (TargetMBean[])subDeploymentTargets.toArray(new TargetMBean[0]);

               for(int inner = 0; inner < subset.length; ++inner) {
                  TargetMBean subTarget = subset[inner];
                  if (subTarget instanceof JMSServerMBean) {
                     if (jmsServerMap.contains(subTarget.getName())) {
                        continue;
                     }

                     jmsServerMap.add(subTarget.getName());
                  } else if (subTarget instanceof ServerMBean) {
                     if (wlsServerMap.contains(subTarget.getName())) {
                        continue;
                     }

                     wlsServerMap.add(subTarget.getName());
                  } else if (subTarget instanceof ClusterMBean) {
                     if (clusterMap.contains(subTarget.getName())) {
                        continue;
                     }

                     clusterMap.add(subTarget.getName());
                  } else {
                     if (!(subTarget instanceof SAFAgentMBean)) {
                        throw new IllegalArgumentException(JMSExceptionLogger.logInvalidSubDeploymentTargetLoggable(basic.getName(), subTarget.getName(), subTarget.getClass().getName(), subDeployment.getName()).getMessage());
                     }

                     if (safAgentMap.contains(subTarget.getName())) {
                        continue;
                     }

                     safAgentMap.add(subTarget.getName());
                  }

                  boolean isSubTarget = false;
                  exampleFailedSuperTarget = null;

                  for(int tri = 0; tri < superset.length; ++tri) {
                     TargetMBean superTarget = superset[tri];
                     if (isTargetASubTarget(superTarget, subTarget)) {
                        isSubTarget = true;
                        break;
                     }

                     if (exampleFailedSuperTarget == null) {
                        exampleFailedSuperTarget = superTarget;
                     }
                  }

                  if (!isSubTarget) {
                     String moduleName;
                     if (targetingInfo instanceof SubDeploymentMBean) {
                        moduleName = JMSBeanHelper.getDecoratedName(basic.getName(), targetingInfo.getName());
                     } else {
                        moduleName = basic.getName();
                     }

                     String exampleFailedSuperTargetName = exampleFailedSuperTarget == null ? "<no-targets>" : exampleFailedSuperTarget.getName();
                     if (isJMSResourceDefn) {
                        String jndiName = null;
                        boolean isDestinationDefinition = true;
                        if (jmsBean.getConnectionFactories().length > 0) {
                           jndiName = jmsBean.getConnectionFactories()[0].getJNDIName();
                           isDestinationDefinition = false;
                        } else if (jmsBean.getUniformDistributedQueues().length > 0) {
                           jndiName = jmsBean.getUniformDistributedQueues()[0].getJNDIName();
                        } else {
                           jndiName = jmsBean.getUniformDistributedTopics()[0].getJNDIName();
                        }

                        String jmsResourceDefinitionType = isDestinationDefinition ? "JMS Destination Definition" : "JMS Connection Factory Definition";
                        throw new IllegalArgumentException(JMSExceptionLogger.logInvalidHierarchyForJMSServerLoggable(jmsResourceDefinitionType, basic.getName(), jndiName, subDeployment.getName(), exampleFailedSuperTargetName).getMessage());
                     }

                     throw new IllegalArgumentException(JMSExceptionLogger.logInvalidSubTargetingLoggable(basic.getName(), moduleName, subDeployment.getName(), subTarget.getName(), exampleFailedSuperTargetName).getMessage());
                  }
               }
            }

         }
      }
   }

   private static void validateDeliveryMode(DestinationBean destination, JMSServerMBean jmsServer) {
      if (destination.getDeliveryParamsOverrides().getDeliveryMode().equals("Persistent")) {
         throw new IllegalArgumentException(JMSExceptionLogger.logDeliveryModeMismatch2Loggable(destination.getName(), jmsServer.getName()).getMessage());
      }
   }

   public static void validateTargeting(JMSBean bean, BasicDeploymentMBean basic, TargetInfoMBean targetingInfo, boolean isJMSResourceDefn) throws IllegalArgumentException {
      WebLogicMBean deploymentScope = JMSModuleHelper.getDeploymentScope(basic);
      validateJMSModuleName(basic);
      validateAppDeploymentFileName(basic);
      int lcv;
      if (targetingInfo != null) {
         validateTargetingHierarchy(basic, targetingInfo, bean, isJMSResourceDefn);
         TargetMBean[] infoTargets = targetingInfo.getTargets();

         for(lcv = 0; lcv < infoTargets.length; ++lcv) {
            TargetMBean infoTarget = infoTargets[lcv];
            if (!(infoTarget instanceof ServerMBean) && !(infoTarget instanceof ClusterMBean) && !(infoTarget instanceof VirtualTargetMBean)) {
               throw new IllegalArgumentException(JMSExceptionLogger.logInvalidModuleTargetLoggable(basic.getName(), infoTarget.getName(), infoTarget.getClass().getName()).getMessage());
            }
         }
      }

      HashSet jmsServerGroups = new HashSet();

      Object entities;
      for(lcv = 0; lcv < 2; ++lcv) {
         Object entities;
         switch (lcv) {
            case 0:
               entities = bean.getQueues();
               break;
            case 1:
               entities = bean.getTopics();
               break;
            default:
               throw new AssertionError("Unknown JMSServer type: " + lcv);
         }

         if (entities != null) {
            for(int inner = 0; inner < ((Object[])entities).length; ++inner) {
               entities = ((Object[])entities)[inner];
               String deploymentName = ((TargetableBean)entities).getSubDeploymentName();
               jmsServerGroups.add(deploymentName);
            }
         }
      }

      DomainMBean domain = null;
      Map pathServiceMap = null;
      DistributedDestinationBean[] topics = bean.getDistributedTopics();
      entities = bean.getDistributedQueues();

      while(true) {
         int lcv;
         for(lcv = 0; lcv < ((Object[])entities).length; ++lcv) {
            if (!hashRouting((DistributedDestinationBean)((Object[])entities)[lcv])) {
               if (domain == null) {
                  domain = JMSLegalHelper.getDomain(basic);
               }

               if (pathServiceMap == null) {
                  pathServiceMap = validatePathServicesInternal(domain);
               }

               validatePathServiceDistributedDestination(domain, pathServiceMap, bean, (DistributedDestinationBean)((Object[])entities)[lcv], basic);
            }
         }

         if (entities == topics) {
            if (targetingInfo == null) {
               return;
            }

            SubDeploymentMBean[] subs = getSubDeploymentsFromTargetInfo(targetingInfo);

            for(lcv = 0; lcv < subs.length; ++lcv) {
               SubDeploymentMBean sub = subs[lcv];
               String subDeploymentName = sub.getName();
               if (jmsServerGroups.contains(subDeploymentName)) {
                  TargetMBean[] targets = sub.getTargets();
                  if (targets != null && targets.length > 0) {
                     if (targets.length > 1) {
                        throw new IllegalArgumentException(JMSExceptionLogger.logInvalidDeploymentTargetLoggable(subDeploymentName, basic.getName()).getMessage());
                     }

                     TargetMBean target = targets[0];
                     if (!(target instanceof JMSServerMBean)) {
                        if (!(basic instanceof JMSSystemResourceMBean) || !(deploymentScope instanceof ResourceGroupTemplateMBean)) {
                           throw new IllegalArgumentException(JMSExceptionLogger.logInvalidDeploymentTargetLoggable(subDeploymentName, basic.getName()).getMessage());
                        }
                     } else {
                        JMSServerMBean jmsServer = (JMSServerMBean)target;
                        TargetMBean[] jmsServertargets = jmsServer.getTargets();
                        if (jmsServertargets != null && jmsServertargets.length > 0 && jmsServertargets[0] instanceof ClusterMBean) {
                           PersistentStoreMBean store = jmsServer.getPersistentStore();
                           if (store == null || !store.getDistributionPolicy().equalsIgnoreCase("Singleton")) {
                              throw new IllegalArgumentException(JMSExceptionLogger.logInvalidSubDeploymentTargetingLoggable(subDeploymentName, basic.getName()).getMessage());
                           }
                        }

                        if (!jmsServer.getStoreEnabled()) {
                           QueueBean[] queueBeans = bean.getQueues();

                           for(int i = 0; i < queueBeans.length; ++i) {
                              QueueBean queueBean = queueBeans[i];
                              if (queueBean.getSubDeploymentName().equals(subDeploymentName)) {
                                 validateDeliveryMode(queueBean, jmsServer);
                              }
                           }

                           TopicBean[] topicBeans = bean.getTopics();

                           for(int i = 0; i < topicBeans.length; ++i) {
                              TopicBean topicBean = topicBeans[i];
                              if (topicBean.getSubDeploymentName().equals(subDeploymentName)) {
                                 validateDeliveryMode(topicBean, jmsServer);
                              }
                           }
                        }
                     }
                  }
               }
            }

            QueueBean[] queueBeans = bean.getQueues();
            if (queueBeans != null) {
               for(int i = 0; i < queueBeans.length; ++i) {
                  SubDeploymentMBean sub = mySub(subs, queueBeans[i]);
                  validateSubdTargetDistPolicy(queueBeans[i], sub, basic.getName(), "Singleton", deploymentScope);
                  validateErrorDestination(queueBeans[i], targetingInfo, false);
               }
            }

            TopicBean[] topicBeans = bean.getTopics();
            if (topics != null) {
               for(int i = 0; i < topicBeans.length; ++i) {
                  SubDeploymentMBean sub = mySub(subs, topicBeans[i]);
                  validateSubdTargetDistPolicy(topicBeans[i], sub, basic.getName(), "Singleton", deploymentScope);
                  validateErrorDestination(topicBeans[i], targetingInfo, false);
               }
            }

            validateForeignServerTargeting(basic, subs, bean, deploymentScope);
            validateSingletonDestinationsInRGT(basic, bean);
            validateDistributedDestinations(targetingInfo, basic, bean);
            validateUniformDistributedDestinations(subs, basic, bean, isJMSResourceDefn);
            validateImportedDestinationGroups(subs, basic, bean);
            return;
         }

         entities = topics;
      }
   }

   public static void validateJMSSystemResource(JMSSystemResourceMBean resource) throws IllegalArgumentException {
      if (resource != null) {
         validateJMSModuleName(resource);
         JMSBean bean = resource.getJMSResource();
         if (bean != null) {
            validateJMSSystemResourceModuleDescriptorFileName((HashMap)null, resource);
            if (resource.getParent() instanceof DomainMBean) {
               validateTargeting(bean, resource, resource, false);
            }

            validateDestinationUnderRGScope(resource);
         }
      }
   }

   private static void validateDestinationUnderRGScope(JMSSystemResourceMBean resource) {
      WebLogicMBean deploymentScope = resource.getParent();
      if (deploymentScope instanceof ResourceGroupTemplateMBean) {
         JMSBean jmsBean = resource.getJMSResource();
         DistributedQueueBean[] dqs = jmsBean.getDistributedQueues();
         validateDistributedDestinationUnderRG(dqs, deploymentScope, resource);
         DistributedTopicBean[] dts = jmsBean.getDistributedTopics();
         validateDistributedDestinationUnderRG(dts, deploymentScope, resource);
         UniformDistributedTopicBean[] udts = jmsBean.getUniformDistributedTopics();

         for(int i = 0; i < udts.length; ++i) {
            if (udts[i].getForwardingPolicy().equals(JMSConstants.FORWARDING_POLICY_REPLICATED)) {
               String rgGroupInfo = " Resource Group Template " + deploymentScope.getName();
               if (deploymentScope instanceof ResourceGroupMBean) {
                  rgGroupInfo = " Resource Group " + deploymentScope.getName();
               }

               throw new IllegalArgumentException(JMSExceptionLogger.logRDTopicNotSupportedLoggable(udts[i].getName(), resource.getName(), rgGroupInfo).getMessage());
            }
         }
      }

   }

   public static void validateEntityName(String name) throws IllegalArgumentException {
      if (name != null && name.length() != 0) {
         if (name.indexOf("!") != -1 || name.indexOf("#") != -1 || name.indexOf("%") != -1 || name.indexOf("^") != -1 || name.indexOf("&") != -1 || name.indexOf("*") != -1 || name.indexOf("(") != -1 || name.indexOf(")") != -1 || name.indexOf(",") != -1 || name.indexOf(":") != -1 || name.indexOf("?") != -1 || name.indexOf("+") != -1 || name.indexOf("=") != -1 || name.indexOf("\\") != -1) {
            throw new IllegalArgumentException("Invalid Name " + name + ", any of these characters !#$%^&*(),:?+=\\ are not allowed to be part of the name value");
         }
      } else {
         throw new IllegalArgumentException("Invalid name: JMS resource name cannot be null or empty");
      }
   }

   private static boolean hashRouting(DistributedDestinationBean distDest) {
      return "Hash".equals(distDest.getUnitOfOrderRouting());
   }

   private static void conflictsWithAppDeployment(DomainMBean domain, String resourceName) throws IllegalArgumentException {
      AppDeploymentMBean appd = domain.lookupAppDeployment(resourceName);
      if (appd != null) {
         throw new IllegalArgumentException(JMSExceptionLogger.logDuplicateResourceNameLoggable(resourceName).getMessage());
      }
   }

   private static void validateJMSSystemResource(DomainMBean domain) throws IllegalArgumentException {
      JMSSystemResourceMBean[] systemResources = domain.getJMSSystemResources();
      HashMap descriptorMap = new HashMap();

      for(int i = 0; i < systemResources.length; ++i) {
         conflictsWithAppDeployment(domain, systemResources[i].getName());
         validateJMSModuleName(systemResources[i]);
         validateJMSSystemResourceModuleDescriptorFileName(descriptorMap, systemResources[i]);
      }

   }

   private static void validateJMSModuleName(BasicDeploymentMBean resource) throws IllegalArgumentException {
      if (resource.getName().equals("interop-jms")) {
         throw new IllegalArgumentException(JMSExceptionLogger.logJMSSystemResourceModuleCannotHaveInteropJmsNameLoggable().getMessage());
      }
   }

   private static void validateAppDeploymentFileName(BasicDeploymentMBean deployment) throws IllegalArgumentException {
      String fileName = deployment.getSourcePath();
      File fileVersion = new File(fileName);
      String baseName = fileVersion.getName();
      if ("interop-jms.xml".equalsIgnoreCase(baseName)) {
         throw new IllegalArgumentException(JMSExceptionLogger.logJMSDeploymentModuleCannotHaveInteropJmsDescriptorNameLoggable(deployment.getName(), deployment.getSourcePath()).getMessage());
      }
   }

   private static void validateJMSSystemResourceModuleDescriptorFileName(HashMap descriptorMap, JMSSystemResourceMBean resource) throws IllegalArgumentException {
      if (resource != null) {
         String canonicalName = null;
         String descriptorName = resource.getDescriptorFileName();
         File fileVersion = new File(descriptorName);
         String baseName = fileVersion.getName();
         if (descriptorName != null) {
            canonicalName = descriptorName.replace(File.separatorChar, '/');
            if (!canonicalName.toLowerCase(Locale.ENGLISH).endsWith("-jms.xml")) {
               throw new IllegalArgumentException(JMSExceptionLogger.logInvalidJMSSystemResourceModuleDescriptorFileNameLoggable(resource.getName(), descriptorName).getMessage());
            }

            if ("interop-jms.xml".equalsIgnoreCase(baseName)) {
               throw new IllegalArgumentException(JMSExceptionLogger.logJMSDeploymentModuleCannotHaveInteropJmsDescriptorNameLoggable(resource.getName(), resource.getDescriptorFileName()).getMessage());
            }

            if (descriptorMap != null) {
               if (canonicalName.startsWith("./")) {
                  canonicalName = canonicalName.substring(2, canonicalName.length());
               }

               if (descriptorMap.get(canonicalName) != null) {
                  throw new IllegalArgumentException(JMSExceptionLogger.logInvalidJMSSystemResourceModuleDescriptorFileNameLoggable(resource.getName(), descriptorName).getMessage());
               }

               descriptorMap.put(canonicalName, resource.getName());
            }
         }

      }
   }

   public static void validateJMSDomain(DomainMBean domain) throws IllegalArgumentException {
      validateJMSSystemResource(domain);
      validatePathServicesInternal(domain);
   }

   private static Map validatePathServicesInternal(DomainMBean domain) throws IllegalArgumentException {
      PathServiceMBean[] pathServices = domain.getPathServices();
      HashMap clusters = new HashMap();

      for(int lcv = 0; lcv < pathServices.length; ++lcv) {
         TargetMBean[] targets = pathServices[lcv].getTargets();
         if (targets.length != 0) {
            if (targets.length != 1) {
               throw new IllegalArgumentException("PathService " + pathServices[lcv].getName() + " is not targetted to a single server or Cluster or MigratableTarget");
            }

            ServerMBean serverMBean = null;
            ClusterMBean clusterMBean = null;
            boolean migratable = false;
            if (targets[0] instanceof ServerMBean) {
               serverMBean = (ServerMBean)((ServerMBean)targets[0]);
               clusterMBean = serverMBean.getCluster();
            } else if (targets[0] instanceof MigratableTargetMBean) {
               migratable = true;
               clusterMBean = ((MigratableTargetMBean)targets[0]).getCluster();
            } else {
               if (!(targets[0] instanceof ClusterMBean)) {
                  throw new IllegalArgumentException("PathService " + pathServices[lcv].getName() + " is not targetted to a single server or Cluster or MigratableTarget, it is targetted to " + targets[0]);
               }

               migratable = true;
               clusterMBean = (ClusterMBean)targets[0];
            }

            if (migratable) {
               PersistentStoreMBean storeMBean = pathServices[lcv].getPersistentStore();
               if (storeMBean == null) {
                  throw new IllegalArgumentException("PathService " + pathServices[lcv].getName() + " is targetted to a Migratable Target or to a Cluster " + targets[0].getName() + ", it cannot use the default persistent store. Please configure a custom persistent store, that is also targeted to the same migratable target as PathService");
               }

               TargetMBean[] storeTargets = storeMBean.getTargets();
               if (storeTargets.length != 1 || storeTargets[0] != targets[0]) {
                  throw new IllegalArgumentException("PathService " + pathServices[lcv].getName() + " is targetted to Migratable Target or to a Cluster " + targets[0].getName() + ", but persistent store it uses is not targeted to the migratable target or to the Cluster as PathService");
               }
            }

            if (clusterMBean == null) {
               if (serverMBean != null) {
                  throw new IllegalArgumentException("PathService " + pathServices[lcv].getName() + " is targetted to " + serverMBean.getName() + ", but that sever is not in a cluster");
               }

               throw new IllegalArgumentException("PathService " + pathServices[lcv].getName() + " is not targetted to valid targets");
            }

            PathServiceMBean old = (PathServiceMBean)clusters.put(clusterMBean.getName(), pathServices[lcv]);
            if (old != null) {
               throw new IllegalArgumentException("Both " + old.getName() + " and " + pathServices[lcv].getName() + " are targetted to cluster " + clusterMBean.getName());
            }
         }
      }

      return clusters;
   }

   private static TargetMBean[] getDDTargets(JMSBean bean, DistributedDestinationBean distDest, BasicDeploymentMBean basic) {
      LinkedList destinations = new LinkedList();
      DistributedDestinationMemberBean[] dqmbs;
      int lcv;
      DistributedDestinationMemberBean dqmb;
      String dqMemberName;
      if (distDest instanceof DistributedQueueBean) {
         DistributedQueueBean dqb = (DistributedQueueBean)distDest;
         dqmbs = dqb.getDistributedQueueMembers();

         for(lcv = 0; lcv < dqmbs.length; ++lcv) {
            dqmb = dqmbs[lcv];
            dqMemberName = dqmb.getPhysicalDestinationName();
            DestinationBean db = bean.lookupQueue(dqMemberName);
            if (db == null) {
               throw new IllegalArgumentException("Could not find the member bean \"" + dqMemberName + "\" for distributed queue \"" + distDest.getName() + "\"");
            }

            destinations.add(db);
         }
      } else {
         DistributedTopicBean dtb = (DistributedTopicBean)distDest;
         dqmbs = dtb.getDistributedTopicMembers();

         for(lcv = 0; lcv < dqmbs.length; ++lcv) {
            dqmb = dqmbs[lcv];
            dqMemberName = dqmb.getPhysicalDestinationName();
            DestinationBean db = bean.lookupTopic(dqMemberName);
            if (db == null) {
               throw new IllegalArgumentException("Could not find the member bean \"" + dqMemberName + "\" for distributed topic \"" + distDest.getName() + "\"");
            }

            destinations.add(db);
         }
      }

      LinkedList targetList = new LinkedList();
      Iterator it = destinations.iterator();

      while(it.hasNext()) {
         DestinationBean db = (DestinationBean)it.next();
         TargetMBean[] targets = JMSBeanHelper.getSubDeploymentTargets(db.getSubDeploymentName(), basic);

         for(int lcv = 0; lcv < targets.length; ++lcv) {
            targetList.add(targets[lcv]);
         }
      }

      TargetMBean[] retVal = (TargetMBean[])((TargetMBean[])targetList.toArray(new TargetMBean[targetList.size()]));
      return retVal;
   }

   private static Map validatePathServiceDistributedDestination(DomainMBean domain, Map pathServices, JMSBean bean, DistributedDestinationBean distDest, BasicDeploymentMBean basic) throws IllegalArgumentException {
      if (hashRouting(distDest)) {
         return pathServices;
      } else {
         TargetMBean[] targets = getDDTargets(bean, distDest, basic);
         if (targets.length == 0) {
            return pathServices;
         } else {
            ClusterMBean cumulativeCluster = null;

            for(int lcv = 0; lcv < targets.length; ++lcv) {
               ClusterMBean currentCluster;
               try {
                  currentCluster = getClusterFromTarget(targets[lcv]);
               } catch (IllegalArgumentException var10) {
                  throw new IllegalArgumentException("Could not extract cluster for JMSDistributedDestination " + distDest.getName() + " target " + targets[lcv].getName() + ".\n" + var10.toString());
               }

               if (currentCluster == null) {
                  throw new IllegalArgumentException("JMSDistributedDestination " + distDest.getName() + " is targetted to " + targets[lcv].getName() + ", but is not a cluster member");
               }

               if (cumulativeCluster == null) {
                  cumulativeCluster = currentCluster;
               } else if (cumulativeCluster != currentCluster) {
                  throw new IllegalArgumentException("JMSDistributedDestination " + distDest.getName() + " but is targetted to " + targets[lcv].getName() + ", and that is not in targetted cluster " + cumulativeCluster.getName());
               }
            }

            if (pathServices == null) {
               pathServices = validatePathServicesInternal(domain);
            }

            if (pathServices.get(cumulativeCluster.getName()) == null) {
               throw new IllegalArgumentException("JMSDistributedDestination " + distDest.getName() + " is targetted to cluster " + cumulativeCluster.getName() + ", but that cluster does not have a PathService");
            } else {
               return pathServices;
            }
         }
      }
   }

   private static ClusterMBean getClusterFromTarget(TargetMBean target) throws IllegalArgumentException {
      if (target instanceof ClusterMBean) {
         return (ClusterMBean)target;
      } else if (target instanceof ServerMBean) {
         return ((ServerMBean)((ServerMBean)target)).getCluster();
      } else {
         ServerMBean server;
         if (target instanceof MigratableTargetMBean) {
            server = ((MigratableTargetMBean)target).getHostingServer();
         } else {
            if (target instanceof JMSServerMBean) {
               TargetMBean[] innerTarget = ((JMSServerMBean)target).getTargets();
               if (innerTarget.length == 0) {
                  throw new IllegalArgumentException("no targets for JMSServer " + target.getName());
               }

               ClusterMBean firstClusterMBean = getClusterFromTarget(innerTarget[0]);

               for(int lcv = 1; lcv < innerTarget.length; ++lcv) {
                  ClusterMBean innerClusterMBean = getClusterFromTarget(innerTarget[lcv]);
                  if (firstClusterMBean != innerClusterMBean) {
                     String firstName = firstClusterMBean == null ? null : firstClusterMBean.getName();
                     String innerName = innerTarget[lcv] == null ? null : innerTarget[lcv].getName();
                     String innerClusterName = innerClusterMBean == null ? null : innerClusterMBean.getName();
                     throw new IllegalArgumentException("First target for " + target.getName() + " is in cluster " + firstName + " but target " + innerName + " is in cluster " + innerClusterName);
                  }
               }

               return firstClusterMBean;
            }

            server = null;
         }

         if (server == null) {
            throw new IllegalArgumentException("could not find server for " + target.getName() + " the target class is " + target.getClass().getName());
         } else {
            return server.getCluster();
         }
      }
   }

   private static void validateConnectionFactories(JMSBean module) {
      JMSConnectionFactoryBean[] factories = module.getConnectionFactories();

      for(int lcv = 0; lcv < factories.length; ++lcv) {
         JMSConnectionFactoryBean factory = factories[lcv];
         validateConnectionFactory(factory);
      }

   }

   private static void validateConnectionFactory(JMSConnectionFactoryBean factory) {
      FlowControlParamsBean flowBean = factory.getFlowControlParams();
      validateFlowControl(factory.getName(), flowBean);
      validateTargetingOptions(factory);
      ClientParamsBean clientBean = factory.getClientParams();
      validateClient(clientBean);
      DefaultDeliveryParamsBean defaultDeliveryBean = factory.getDefaultDeliveryParams();
      validateDefaultDeliveryParams(factory.getName(), defaultDeliveryBean);
   }

   private static void validateDefaultDeliveryParams(String name, DefaultDeliveryParamsBean deliveryBean) {
      validateUOOName(name, deliveryBean.getDefaultUnitOfOrder());
   }

   private static void validateUOOName(String cfName, String uooName) {
      if (uooName != null) {
         if (uooName.startsWith(".")) {
            if (!uooName.equals(".System")) {
               if (!uooName.equals(".Standard")) {
                  throw new IllegalArgumentException("Illegal defaultUnitOfOrderName " + uooName + " starts with \".\" in JMSConnectionFactory " + cfName);
               }
            }
         } else {
            validateCFJNDIName(uooName);
         }
      }
   }

   private static void validateFlowControl(String conFacName, FlowControlParamsBean flowBean) {
      if (flowBean.getFlowSteps() > flowBean.getFlowInterval()) {
         throw new IllegalArgumentException(JMSExceptionLogger.logFlowIntervalLoggable(conFacName, flowBean.getFlowSteps(), flowBean.getFlowInterval()).getMessage());
      } else if (flowBean.getFlowMinimum() >= flowBean.getFlowMaximum()) {
         throw new IllegalArgumentException(JMSExceptionLogger.logFlowLimitsLoggable(conFacName, flowBean.getFlowMinimum(), flowBean.getFlowMaximum()).getMessage());
      }
   }

   private static void validateClient(ClientParamsBean clientBean) {
      int max = clientBean.getMessagesMaximum();
      if (max < -1 || max == 0) {
         throw new IllegalArgumentException(JMSTextTextFormatter.getInstance().getInvalidJMSMessagesMaximum());
      }
   }

   public static void validateSessionPoolSessionsMaximum(int maxSessions) {
      if (maxSessions < -1 || maxSessions == 0) {
         throw new IllegalArgumentException(JMSExceptionLogger.logBadSessionsMaxLoggable(maxSessions).getMessage());
      }
   }

   private static void validateSAFErrorDestinationTargeting(String beanName, SAFErrorHandlingBean errorHandling, SubDeploymentMBean sub) {
      if (errorHandling != null) {
         SAFDestinationBean errorDestination = errorHandling.getSAFErrorDestination();
         if (errorDestination != null) {
            SAFImportedDestinationsBean parent = (SAFImportedDestinationsBean)((DescriptorBean)errorDestination).getParentBean();
            if (!sub.getName().equals(parent.getSubDeploymentName())) {
               throw new IllegalArgumentException(JMSExceptionLogger.logBadErrorDestinationLoggable(beanName, errorHandling.getName(), errorDestination.getName()).getMessage());
            }
         }
      }
   }

   private static void validateImportedDestinations(SAFImportedDestinationsBean idgroup, SubDeploymentMBean sub, JMSBean bean) {
      if (idgroup.getSAFErrorHandling() != null) {
         validateSAFErrorDestinationTargeting(idgroup.getName(), getErrorHandlingBean(bean, idgroup.getName(), idgroup.getSAFErrorHandling().getName()), sub);
      }

      SAFDestinationBean[] iqs = idgroup.getSAFQueues();

      int i;
      for(i = 0; i < iqs.length; ++i) {
         SAFDestinationBean id = iqs[i];
         if (id.getSAFErrorHandling() != null) {
            validateSAFErrorDestinationTargeting(id.getName(), getErrorHandlingBean(bean, id.getName(), id.getSAFErrorHandling().getName()), sub);
         }
      }

      SAFDestinationBean[] iqs = idgroup.getSAFTopics();

      for(i = 0; i < iqs.length; ++i) {
         SAFDestinationBean id = iqs[i];
         if (id.getSAFErrorHandling() != null) {
            validateSAFErrorDestinationTargeting(id.getName(), getErrorHandlingBean(bean, id.getName(), id.getSAFErrorHandling().getName()), sub);
         }
      }

   }

   private static SAFErrorHandlingBean getErrorHandlingBean(JMSBean wholeModule, String beanName, String ehName) {
      if (ehName == null) {
         return null;
      } else {
         SAFErrorHandlingBean errorHandling = wholeModule.lookupSAFErrorHandling(ehName);
         if (errorHandling == null) {
            throw new IllegalArgumentException(JMSExceptionLogger.logErrorHandlingNotFoundLoggable(beanName, ehName).getMessage());
         } else {
            return errorHandling;
         }
      }
   }

   private static void validateForeignServers(JMSBean module) {
      ForeignServerBean[] foreignServers = module.getForeignServers();

      for(int lcv = 0; lcv < foreignServers.length; ++lcv) {
         ForeignServerBean foreignServer = foreignServers[lcv];
         validateForeignServer(foreignServer);
      }

   }

   private static void validateForeignServer(ForeignServerBean foreignServer) {
      boolean urlAvailable = false;
      validateTargetingOptions(foreignServer);
      String curl = foreignServer.getConnectionURL();
      if (curl != null && !curl.trim().equals("")) {
         urlAvailable = true;
      }

      ForeignDestinationBean[] fds = foreignServer.getForeignDestinations();

      for(int lcv = 0; lcv < fds.length; ++lcv) {
         if (!urlAvailable && fds[lcv].getLocalJNDIName() != null && fds[lcv].getRemoteJNDIName() != null && fds[lcv].getLocalJNDIName().equals(fds[lcv].getRemoteJNDIName())) {
            throw new IllegalArgumentException(JMSExceptionLogger.logInvalidForeignServerLoggable(foreignServer.getName(), fds[lcv].getName(), "Foreign Destination", fds[lcv].getLocalJNDIName()).getMessage());
         }
      }

      ForeignConnectionFactoryBean[] fcs = foreignServer.getForeignConnectionFactories();

      for(int lcv = 0; lcv < fcs.length; ++lcv) {
         if (!urlAvailable && fcs[lcv].getLocalJNDIName() != null && fcs[lcv].getRemoteJNDIName() != null && fcs[lcv].getLocalJNDIName().equals(fcs[lcv].getRemoteJNDIName())) {
            throw new IllegalArgumentException(JMSExceptionLogger.logInvalidForeignServerLoggable(foreignServer.getName(), fcs[lcv].getName(), "Foreign Connection Factory", fcs[lcv].getLocalJNDIName()).getMessage());
         }
      }

   }

   private static void validateTargetingOptions(TargetableBean targetable) throws IllegalArgumentException {
      if (targetable.isDefaultTargetingEnabled() && targetable.isSet("SubDeploymentName")) {
         throw new IllegalArgumentException(JMSExceptionLogger.logConflictingTargetingInformationLoggable(targetable.getName()).getMessage());
      }
   }

   public void validate(DomainMBean domain) {
      validateJMSDomain(domain);
   }

   private static String getDistributionPolicy(TargetMBean pTargetMbean) {
      PersistentStoreMBean mStoreBean = null;
      if (pTargetMbean == null) {
         return null;
      } else {
         if (pTargetMbean instanceof JMSServerMBean) {
            mStoreBean = ((JMSServerMBean)pTargetMbean).getPersistentStore();
         }

         return mStoreBean == null ? null : mStoreBean.getDistributionPolicy();
      }
   }

   private static void validateSubdTargetDistPolicy(TargetableBean targetablebean, SubDeploymentMBean subdBean, String moduleName, String expectedpolicy, WebLogicMBean deploymentScope) {
      if (targetablebean != null && !targetablebean.isDefaultTargetingEnabled()) {
         if (subdBean != null) {
            TargetMBean[] targets = subdBean.getTargets();
            if (targets != null) {
               for(int i = 0; i < targets.length; ++i) {
                  if (getDistributionPolicy(targets[i]) != null && !expectedpolicy.equals(getDistributionPolicy(targets[i]))) {
                     if (expectedpolicy.equals("Distributed")) {
                        throw new IllegalArgumentException(JMSExceptionLogger.logInvalidTargetClusterPolicyLoggable(subdBean.getName(), moduleName).getMessage());
                     }

                     if (deploymentScope instanceof ResourceGroupTemplateMBean) {
                        throw new IllegalArgumentException(JMSExceptionLogger.logInvalidTargetSingleTonPolicyLoggable(subdBean.getName(), moduleName).getMessage());
                     }

                     if (targets[i] instanceof JMSServerMBean) {
                        TargetMBean[] jmsServertargets = ((JMSServerMBean)targets[i]).getTargets();
                        if (jmsServertargets.length > 0 && jmsServertargets[0] instanceof ClusterMBean) {
                           throw new IllegalArgumentException(JMSExceptionLogger.logInvalidSubDeploymentTargetingLoggable(subdBean.getName(), moduleName).getMessage());
                        }
                     }
                  }
               }
            }
         }

      }
   }

   private static void validateSingletonDestinationsInRGT(BasicDeploymentMBean basic, JMSBean bean) {
      WebLogicMBean deploymentScope = JMSModuleHelper.getDeploymentScope(basic);
      if (!(deploymentScope instanceof DomainMBean)) {
         QueueBean[] var3 = bean.getQueues();
         int var4 = var3.length;

         int var5;
         for(var5 = 0; var5 < var4; ++var5) {
            QueueBean queueBean = var3[var5];
            validateTargetingOptions(queueBean);
            if (queueBean.isDefaultTargetingEnabled()) {
               validateSingletonJMSServersInRGT(basic, deploymentScope, queueBean, "Queue");
            }
         }

         TopicBean[] var7 = bean.getTopics();
         var4 = var7.length;

         for(var5 = 0; var5 < var4; ++var5) {
            TopicBean topicBean = var7[var5];
            validateTargetingOptions(topicBean);
            if (topicBean.isDefaultTargetingEnabled()) {
               validateSingletonJMSServersInRGT(basic, deploymentScope, topicBean, "Topic");
            }
         }

      }
   }

   private static void validateSingletonJMSServersInRGT(BasicDeploymentMBean basic, WebLogicMBean deploymentScope, DestinationBean bean, String entityType) {
      boolean isDeployedToRGT = AppDeploymentHelper.isDeployedThroughRGT(basic);
      JMSServerMBean[] singletonJMSServers = JMSModuleHelper.getCandidateJMSServers(JMSLegalHelper.getDomain(basic), deploymentScope, "Singleton", isDeployedToRGT);
      if (singletonJMSServers.length == 0) {
         JMSLogger.logMatchingJMSServerNotFound("Singleton", entityType, bean.getName(), JMSModuleHelper.getConfigMBeanShortName(deploymentScope, basic), JMSModuleHelper.getDeploymentScopeAsString(basic));
      } else if (singletonJMSServers.length > 1) {
         throw new IllegalArgumentException(JMSExceptionLogger.logMultipleCandidateJMSServersLoggable("Singleton", entityType, bean.getName(), JMSModuleHelper.getConfigMBeanShortName(deploymentScope, basic), JMSModuleHelper.getDeploymentScopeAsString(basic), JMSModuleHelper.getConfigMBeanShortNames(deploymentScope, Arrays.asList(singletonJMSServers))).getMessage());
      }
   }

   private static void validateForeignServerTargeting(BasicDeploymentMBean basic, SubDeploymentMBean[] subdeployments, JMSBean bean, WebLogicMBean deploymentScope) {
      ForeignServerBean[] var4 = bean.getForeignServers();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ForeignServerBean foreignServer = var4[var6];
         if (!foreignServer.isDefaultTargetingEnabled()) {
            SubDeploymentMBean subdeploymentMBean = mySub(subdeployments, foreignServer);
            if (subdeploymentMBean != null) {
               TargetMBean[] targetMBeans = subdeploymentMBean.getTargets();
               if (targetMBeans != null && targetMBeans.length == 1 && targetMBeans[0] instanceof JMSServerMBean) {
                  JMSServerMBean jmsServerMBean = (JMSServerMBean)targetMBeans[0];
                  PersistentStoreMBean storeMBean = jmsServerMBean.getPersistentStore();
                  if (storeMBean != null && "Singleton".equals(storeMBean.getDistributionPolicy())) {
                     throw new IllegalArgumentException(JMSExceptionLogger.logInvalidTargetingForeignServerLoggable(foreignServer.getName(), JMSModuleHelper.getConfigMBeanShortName(deploymentScope, basic), JMSModuleHelper.getDeploymentScopeAsString(basic), subdeploymentMBean.getName()).getMessage());
                  }
               }
            }
         }
      }

   }

   public static void validateForeignServerInitialContextFactory(String initialContextFactory) throws IllegalArgumentException {
      if (initialContextFactory != null && initialContextFactory.length() != 0) {
         if (initialContextFactory.equals("weblogic.jms.WLInitialContextFactory")) {
            throw new IllegalArgumentException(JMSExceptionLogger.logInvalidForeignServerInitialContextFactoryLoggable("weblogic.jms.WLInitialContextFactory").getMessage());
         }
      }
   }

   private static void validateSAFErrorHandlings(JMSBean module) {
      SAFImportedDestinationsBean[] var1 = module.getSAFImportedDestinations();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         SAFImportedDestinationsBean safImportedDestinations = var1[var3];
         SAFQueueBean[] var5 = safImportedDestinations.getSAFQueues();
         int var6 = var5.length;

         int var7;
         for(var7 = 0; var7 < var6; ++var7) {
            SAFQueueBean safQueue = var5[var7];
            validateSAFErrorHandling(safQueue, safImportedDestinations);
         }

         SAFTopicBean[] var9 = safImportedDestinations.getSAFTopics();
         var6 = var9.length;

         for(var7 = 0; var7 < var6; ++var7) {
            SAFTopicBean safTopic = var9[var7];
            validateSAFErrorHandling(safTopic, safImportedDestinations);
         }
      }

   }

   private static void validateSAFErrorHandling(SAFDestinationBean safDestination, SAFImportedDestinationsBean safImportedDestinations) {
      SAFErrorHandlingBean safErrorHandling = safDestination.getSAFErrorHandling();
      if (safErrorHandling == null) {
         safErrorHandling = safImportedDestinations.getSAFErrorHandling();
      }

      if (safErrorHandling != null && "Redirect".equals(safErrorHandling.getPolicy())) {
         SAFDestinationBean safErrorDestination = safErrorHandling.getSAFErrorDestination();
         if (safErrorDestination == null || safDestination == safErrorDestination) {
            throw new IllegalArgumentException(JMSExceptionLogger.logIllegalSAFErrorHandlingLoggable(safErrorHandling.getName()).getMessage());
         }
      }

   }
}
