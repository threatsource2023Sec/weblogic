package weblogic.jms.module;

import java.security.AccessController;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.DeliveryFailureParamsBean;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.DestinationKeyBean;
import weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean;
import weblogic.j2ee.descriptor.wl.GroupParamsBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.j2ee.descriptor.wl.QuotaBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.jms.extensions.JMSModuleHelper;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.utils.GenericBeanListener;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public abstract class JMSBeanHelper extends JMSModuleHelper {
   public static final String INTEROP_APPLICATION_NAME = "interop-jms";
   private static final String DECORATION_SEPARATOR = "!";
   public static final Map destinationBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("DestinationKeys", String[].class);
         this.put("AttachSender", String.class);
         this.put("ProductionPausedAtStartup", Boolean.TYPE);
         this.put("InsertionPausedAtStartup", Boolean.TYPE);
         this.put("ConsumptionPausedAtStartup", Boolean.TYPE);
         this.put("MaximumMessageSize", Integer.TYPE);
         this.put("MessagingPerformancePreference", Integer.TYPE);
         this.put("JNDIName", String.class);
         this.put("LocalJNDIName", String.class);
         this.put("JMSCreateDestinationIdentifier", String.class);
         this.put("DefaultUnitOfOrder", Boolean.TYPE);
         this.put("SAFExportPolicy", String.class);
         this.put("UnitOfWorkHandlingPolicy", String.class);
         this.put("IncompleteWorkExpirationTime", Integer.TYPE);
         this.put("DefaultTargetingEnabled", Boolean.TYPE);
      }
   });
   public static final Map destinationKeyBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("Property", String.class);
         this.put("KeyType", String.class);
         this.put("SortOrder", String.class);
      }
   });
   public static final Map thresholdBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("BytesHigh", Long.TYPE);
         this.put("BytesLow", Long.TYPE);
         this.put("MessagesHigh", Long.TYPE);
         this.put("MessagesLow", Long.TYPE);
      }
   });
   public static final Map deliveryOverridesSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("DeliveryMode", String.class);
         this.put("TimeToDeliver", String.class);
         this.put("TimeToLive", Long.TYPE);
         this.put("Priority", Integer.TYPE);
         this.put("RedeliveryDelay", Long.TYPE);
      }
   });
   public static final Map deliveryFailureSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("RedeliveryLimit", Integer.TYPE);
         this.put("ExpirationPolicy", String.class);
         this.put("ExpirationLoggingPolicy", String.class);
      }
   });
   public static final Map messageLoggingSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("MessageLoggingFormat", String.class);
         this.put("MessageLoggingEnabled", Boolean.TYPE);
      }
   });
   public static final Map templateBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("DestinationKeys", String[].class);
         this.put("AttachSender", String.class);
         this.put("ProductionPausedAtStartup", Boolean.TYPE);
         this.put("InsertionPausedAtStartup", Boolean.TYPE);
         this.put("ConsumptionPausedAtStartup", Boolean.TYPE);
         this.put("MaximumMessageSize", Integer.TYPE);
         this.put("MessagingPerformancePreference", Integer.TYPE);
      }
   });
   public static final Map multicastBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("MulticastAddress", String.class);
         this.put("MulticastPort", Integer.TYPE);
         this.put("MulticastTimeToLive", Integer.TYPE);
      }
   });
   public static final Map subscriptionSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("MessagesLimitOverride", Long.TYPE);
      }
   });
   public static final Map distributedTopicBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("JNDIName", String.class);
         this.put("LoadBalancingPolicy", String.class);
         this.put("UnitOfOrderRouting", String.class);
         this.put("SAFExportPolicy", String.class);
      }
   });
   public static final Map distributedTopicAdditionSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("DistributedTopicMembers", DistributedDestinationMemberBean.class);
      }
   });
   public static final Map distributedQueueBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("JNDIName", String.class);
         this.put("LoadBalancingPolicy", String.class);
         this.put("UnitOfOrderRouting", String.class);
         this.put("ForwardDelay", Integer.TYPE);
         this.put("SAFExportPolicy", String.class);
      }
   });
   public static final Map distributedQueueAdditionSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("DistributedQueueMembers", DistributedDestinationMemberBean.class);
      }
   });
   public static final Map uniformDistributedTopicBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("JNDIName", String.class);
         this.put("LoadBalancingPolicy", String.class);
         this.put("ForwardingPolicy", String.class);
         this.put("UnitOfOrderRouting", String.class);
         this.put("SAFExportPolicy", String.class);
      }
   });
   public static final Map uniformDistributedQueueBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("JNDIName", String.class);
         this.put("LoadBalancingPolicy", String.class);
         this.put("UnitOfOrderRouting", String.class);
         this.put("ForwardDelay", Integer.TYPE);
         this.put("SAFExportPolicy", String.class);
      }
   });
   public static final Map localDestinationBeanSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("Quota", QuotaBean.class);
      }
   });
   public static final Map localDeliveryFailureSignatures = Collections.unmodifiableMap(new HashMap() {
      {
         this.put("ErrorDestination", DestinationBean.class);
      }
   });
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String DEFAULT_APPENDIX = "-jms.xml";

   public static void copyTemplateBean(TemplateBean to, JMSBean toRoot, TemplateBean from) throws ManagementException {
      GenericBeanListener gbl = new GenericBeanListener((DescriptorBean)from, to, templateBeanSignatures, false);
      gbl.initialize(false);
      gbl = new GenericBeanListener((DescriptorBean)from.getThresholds(), to.getThresholds(), thresholdBeanSignatures, false);
      gbl.initialize(false);
      gbl = new GenericBeanListener((DescriptorBean)from.getDeliveryParamsOverrides(), to.getDeliveryParamsOverrides(), deliveryOverridesSignatures, false);
      gbl.initialize(false);
      gbl = new GenericBeanListener((DescriptorBean)from.getDeliveryFailureParams(), to.getDeliveryFailureParams(), deliveryFailureSignatures, false);
      gbl.initialize(false);
      gbl = new GenericBeanListener((DescriptorBean)from.getMulticast(), to.getMulticast(), multicastBeanSignatures, false);
      gbl.initialize(false);
      gbl = new GenericBeanListener((DescriptorBean)from.getTopicSubscriptionParams(), to.getTopicSubscriptionParams(), subscriptionSignatures, false);
      gbl.initialize(false);
      gbl = new GenericBeanListener((DescriptorBean)from.getMessageLoggingParams(), to.getMessageLoggingParams(), messageLoggingSignatures, false);
      gbl.initialize(false);
      if (from.isSet("Quota")) {
         QuotaBean fromQuota = from.getQuota();
         if (fromQuota == null) {
            to.setQuota((QuotaBean)null);
         } else {
            to.setQuota(toRoot.lookupQuota(fromQuota.getName()));
         }
      }

      DeliveryFailureParamsBean fromFailure = from.getDeliveryFailureParams();
      if (fromFailure.isSet("ErrorDestination")) {
         DeliveryFailureParamsBean toFailure = to.getDeliveryFailureParams();
         DestinationBean fromErrorDestination = fromFailure.getErrorDestination();
         if (fromErrorDestination == null) {
            toFailure.setErrorDestination((DestinationBean)null);
         } else if (fromErrorDestination instanceof QueueBean) {
            toFailure.setErrorDestination(toRoot.lookupQueue(fromErrorDestination.getName()));
         } else {
            toFailure.setErrorDestination(toRoot.lookupTopic(fromErrorDestination.getName()));
         }
      }

      GroupParamsBean[] groups = from.getGroupParams();
      if (groups != null) {
         for(int lcv = 0; lcv < groups.length; ++lcv) {
            GroupParamsBean fromGroup = groups[lcv];
            GroupParamsBean toGroup = to.createGroupParams(fromGroup.getSubDeploymentName());
            toGroup.setErrorDestination(fromGroup.getErrorDestination());
         }
      }

   }

   public static void copyDestinationKeyBean(JMSBean toRoot, JMSBean fromRoot, String destinationKeyName) throws ManagementException {
      toRoot.createDestinationKey(destinationKeyName);
      DestinationKeyBean from = fromRoot.lookupDestinationKey(destinationKeyName);
      DestinationKeyBean to = toRoot.lookupDestinationKey(destinationKeyName);
      GenericBeanListener gbl = new GenericBeanListener((DescriptorBean)from, to, destinationKeyBeanSignatures, false);
      gbl.initialize(false);
   }

   public static SubDeploymentMBean findSubDeployment(String name, BasicDeploymentMBean deployment) {
      if (deployment != null && name != null) {
         SubDeploymentMBean[] subs = deployment.getSubDeployments();
         if (subs == null) {
            return null;
         } else {
            for(int lcv = 0; lcv < subs.length; ++lcv) {
               SubDeploymentMBean sub = subs[lcv];
               if (name.equals(sub.getName())) {
                  return sub;
               }
            }

            return null;
         }
      } else {
         return null;
      }
   }

   public static String getDecoratedName(String prefixName, String postfixName) {
      return prefixName != null && !"interop-jms".equals(prefixName) ? prefixName + "!" + postfixName : postfixName;
   }

   public static TargetMBean[] getSubDeploymentTargets(String subDeploymentName, BasicDeploymentMBean basicDeploymentMBean) {
      SubDeploymentMBean[] sdmb = basicDeploymentMBean.getSubDeployments();

      for(int lcv = 0; lcv < sdmb.length; ++lcv) {
         if (subDeploymentName.equals(sdmb[lcv].getName())) {
            return sdmb[lcv].getTargets();
         }
      }

      return new TargetMBean[0];
   }

   public static DomainMBean getDomain(WebLogicMBean basic) throws IllegalArgumentException {
      DescriptorBean db = (DescriptorBean)basic;
      DescriptorBean parent = db.getDescriptor().getRootBean();
      if (!(parent instanceof DomainMBean)) {
         throw new IllegalArgumentException("could not get DomainMbean from " + basic.getName() + ".  My root has type " + parent.getClass().getName());
      } else {
         return (DomainMBean)parent;
      }
   }

   public static String constructDefaultJMSSystemFilename(String name) {
      return JMSModuleBeanHelper.constructDefaultJMSSystemFilename(name);
   }

   public static void destroyDestination(JMSBean parent, DestinationBean destination) {
      if (destination instanceof QueueBean) {
         parent.destroyQueue((QueueBean)destination);
      } else {
         parent.destroyTopic((TopicBean)destination);
      }

   }
}
