package weblogic.management.provider.internal;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.management.InvalidAttributeValueException;
import javax.xml.stream.XMLStreamException;
import org.glassfish.hk2.api.IterableProvider;
import org.jvnet.hk2.annotations.Service;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;
import weblogic.management.ManagementLogger;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSBridgeDestinationMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.ManagedExecutorServiceMBean;
import weblogic.management.configuration.ManagedScheduledExecutorServiceMBean;
import weblogic.management.configuration.ManagedThreadFactoryMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PropertyValueVBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SAFAgentMBean;
import weblogic.management.configuration.SimplePropertyValueVBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.SystemResourceMBean;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.internal.InternalApplicationProcessor;
import weblogic.management.utils.ActiveBeanUtil;
import weblogic.management.utils.PartitionUtils;
import weblogic.management.utils.ResourceUtil;
import weblogic.server.GlobalServiceLocator;

@Service
public class PartitionProcessor {
   @Inject
   private IterableProvider componentProcessors;
   @Inject
   private IterableProvider componentPartitionResourceProcessors;
   @Inject
   private Provider planProvider;
   @Inject
   private InternalApplicationProcessor internalAppProcessor;
   @Inject
   private ActiveBeanUtil activeBeanUtil;
   private static PartitionProcessor instance = null;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   private static final String SIMPLE_PROPERTY_VALUE_VBEAN_IMPL_CLASS_NAME = "weblogic.management.mbeans.custom.SimplePropertyValueVBeanImpl";
   private static final List beanClasses = ResourceUtil.getResourceClasses();
   private static final Set beanClassesAbsentFromComponentPartitionProcessorAPI = new HashSet(Arrays.asList(ManagedExecutorServiceMBean.class, ManagedScheduledExecutorServiceMBean.class, ManagedThreadFactoryMBean.class));

   public static void updateConfiguration(DomainMBean root) throws ManagementException {
      updateConfiguration(root, false);
   }

   public static void updateConfiguration(DomainMBean root, boolean processInternalApps) throws ManagementException {
      PartitionProcessor proc = (PartitionProcessor)GlobalServiceLocator.getServiceLocator().getService(PartitionProcessor.class, new Annotation[0]);
      proc.processPrepare(root, processInternalApps);
   }

   public static void updateCloneConfiguration(ConfigurationMBean clone) throws ManagementException {
      updateCloneConfiguration(clone, (Object)null);
   }

   public static void updateCloneConfiguration(ConfigurationMBean clone, Object loadedResource) throws ManagementException {
      PartitionProcessor proc = (PartitionProcessor)GlobalServiceLocator.getServiceLocator().getService(PartitionProcessor.class, new Annotation[0]);
      proc.updateClone(clone, loadedResource);
   }

   private static synchronized PartitionProcessor getInstance() {
      if (instance == null) {
         instance = (PartitionProcessor)GlobalServiceLocator.getServiceLocator().getService(PartitionProcessor.class, new Annotation[0]);
      }

      return instance;
   }

   private void updateClone(ConfigurationMBean clone, Object loadedResource) throws ManagementException {
      DomainMBean domain = findRoot(clone);
      ConfigurationMBean orig = this.activeBeanUtil.toOriginalBean(clone);
      if (orig != null) {
         PartitionMBean partition = this.activeBeanUtil.findContainingPartition(orig);
         OrderedOrganizer organizer = new OrderedOrganizer();
         organizer.add(partition, new BeanPair(orig, clone, loadedResource));

         try {
            this.updateConfiguration(domain, partition, (OrderedOrganizer)organizer, new ExistingCloneConfigUpdateHelper());
         } catch (InvalidAttributeValueException var8) {
            throw new ManagementException(var8);
         }
      }
   }

   public void updateClones(DomainMBean root) throws ManagementException {
      ConfigUpdateHelper configUpdateHelper = new ExistingCloneConfigUpdateHelper();
      this.updateResourceConfiguration(root, configUpdateHelper, true);
   }

   private void processPrepare(DomainMBean root, boolean processInternalApps) throws ManagementException {
      ConfigUpdateHelper configUpdateHelper = new NewOrExistingCloneConfigUpdateHelper();
      this.updateResourceConfiguration(root, configUpdateHelper, processInternalApps);
   }

   private void updateResourceConfiguration(DomainMBean root, ConfigUpdateHelper configUpdateHelper, boolean processInternalApps) throws ManagementException {
   }

   private static AbstractDescriptorBean abstractify(ConfigurationMBean bean) {
      return (AbstractDescriptorBean)AbstractDescriptorBean.class.cast(bean);
   }

   private List filter(List beans, Class tClass) {
      List result = new ArrayList();
      Iterator var4 = beans.iterator();

      while(var4.hasNext()) {
         ConfigurationMBean bean = (ConfigurationMBean)var4.next();
         if (tClass.isAssignableFrom(bean.getClass())) {
            result.add(bean);
         }
      }

      return result;
   }

   private void updateConfiguration(DomainMBean root, ConfigurationMBean parent, ResourceGroupMBean[] resourceGroups, ConfigUpdateHelper configUpdateHelper) throws ManagementException, InvalidAttributeValueException {
      OrderedOrganizer cloneOrganizer = new OrderedOrganizer();
      ResourceGroupMBean[] var6 = resourceGroups;
      int var7 = resourceGroups.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         ResourceGroupMBean group = var6[var8];
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Processing resource group " + group.getName());
         }

         PartitionMBean partition = parent instanceof PartitionMBean ? (PartitionMBean)parent : null;
         Iterator var11 = this.cloneableConfigBeans(group).iterator();

         while(var11.hasNext()) {
            ConfigurationMBean cloneableAttribute = (ConfigurationMBean)var11.next();
            cloneOrganizer.add(partition, new BeanPair(cloneableAttribute, configUpdateHelper.locateClone(root, partition, group, cloneableAttribute, cloneableAttribute.getClass())));
         }
      }

      this.updateConfiguration(root, parent, cloneOrganizer, configUpdateHelper);
   }

   private void updateConfiguration(DomainMBean root, ConfigurationMBean parent, OrderedOrganizer cloneOrganizer, ConfigUpdateHelper configUpdateHelper) throws InvalidAttributeValueException, ManagementException {
      if (!(parent instanceof DomainMBean) && parent instanceof PartitionMBean) {
         try {
            this.updatePartitionDeploymentPlan((PartitionMBean)parent, cloneOrganizer);
         } catch (Exception var9) {
            ManagementLogger.logDeploymentFailed(var9.getLocalizedMessage(), var9);
            throw new ManagementException(var9);
         }
      }

      Iterator var5 = cloneOrganizer.groupIdentifiers().iterator();

      while(var5.hasNext()) {
         PartitionMBean partition = (PartitionMBean)var5.next();
         Iterator var7 = cloneOrganizer.elements(partition).iterator();

         while(var7.hasNext()) {
            BeanPair beans = (BeanPair)var7.next();
            if (beans.clone != null) {
               configUpdateHelper.applyComponentProcessors(root, parent instanceof PartitionMBean ? (PartitionMBean)parent : null, groupForBeanPair(beans), beans.orig, beans.clone, beans.resource);
            }
         }
      }

   }

   private Iterable cloneableConfigBeans(final ResourceGroupMBean rg) {
      return new Iterable() {
         public Iterator iterator() {
            final AttributeAggregator DESCRIPTOR_AGGREGATOR = new AttributeAggregator(ResourceGroupMBean.class.getName(), DescriptorBean.class);
            return new Iterator() {
               private final DeploymentMBean[] deployments = rg.getDeployments();
               private final BasicDeploymentMBean[] basicDeployments = rg.getBasicDeployments();
               private final Object[] descriptorBeans = DESCRIPTOR_AGGREGATOR.getAll(rg);
               private int deploymentsSlot = 0;
               private int basicDeploymentsSlot = 0;
               private int descriptorBeansSlot = 0;
               private boolean isDebug;
               private ConfigurationMBean nextDescriptorBean;

               {
                  this.isDebug = PartitionProcessor.debugLogger.isDebugEnabled();
                  this.nextDescriptorBean = this.getNextDescriptorBean();
                  if (this.isDebug) {
                     StringBuilder sb = (new StringBuilder("PartitionProcessor.cloneableConfigBeans called for resource group ")).append(rg.getName());
                     sb.append(System.lineSeparator()).append("  Deployments: ").append(Arrays.toString(this.deployments)).append(System.lineSeparator()).append("  BasicDeployments: ").append(Arrays.toString(this.basicDeployments)).append(System.lineSeparator()).append("  Unfiltered descriptor beans: ").append(Arrays.toString(this.descriptorBeans));
                     PartitionProcessor.debugLogger.debug(sb.toString());
                  }

               }

               private boolean isMoreDeployments() {
                  return this.deploymentsSlot < this.deployments.length;
               }

               private boolean isMoreBasicDeployments() {
                  return this.basicDeploymentsSlot < this.basicDeployments.length;
               }

               private boolean isMoreDescriptors() {
                  return this.nextDescriptorBean != null;
               }

               private ConfigurationMBean getNextDescriptorBean() {
                  Object next = null;

                  while(this.descriptorBeansSlot < this.descriptorBeans.length) {
                     next = this.descriptorBeans[this.descriptorBeansSlot++];
                     if (next instanceof DescriptorBean && !(next instanceof DeploymentMBean) && !(next instanceof BasicDeploymentMBean)) {
                        ConfigurationMBean result = (ConfigurationMBean)next;
                        if (ResourceUtil.beanToClass(result) != null) {
                           return result;
                        }
                     }
                  }

                  return null;
               }

               public boolean hasNext() {
                  if (this.isDebug) {
                     PartitionProcessor.debugLogger.debug("PartitionProcessor.cloneableConfigBeans.hasNext() finds isMoreDeployments = " + this.isMoreDeployments() + ", isMoreBasicDeployments = " + this.isMoreBasicDeployments() + ", isMoreDescriptors = " + this.isMoreDescriptors());
                  }

                  return this.isMoreDeployments() || this.isMoreBasicDeployments() || this.isMoreDescriptors();
               }

               public ConfigurationMBean next() {
                  if (this.isMoreDeployments()) {
                     if (this.isDebug) {
                        PartitionProcessor.debugLogger.debug("PartitionProcessor.cloneableConfigBeans.next returning deployment " + this.deployments[this.deploymentsSlot].toString());
                     }

                     return this.deployments[this.deploymentsSlot++];
                  } else if (this.isMoreBasicDeployments()) {
                     if (this.isDebug) {
                        PartitionProcessor.debugLogger.debug("PartitionProcessor.cloneableConfigBeans.next returning basic deployment " + this.basicDeployments[this.basicDeploymentsSlot].toString());
                     }

                     return this.basicDeployments[this.basicDeploymentsSlot++];
                  } else if (this.isMoreDescriptors()) {
                     ConfigurationMBean result = this.nextDescriptorBean;
                     this.nextDescriptorBean = this.getNextDescriptorBean();
                     if (this.isDebug) {
                        PartitionProcessor.debugLogger.debug("PartitionProcessor.cloneableConfigBeans.next returning descriptor " + result.toString());
                     }

                     return result;
                  } else {
                     throw new NoSuchElementException();
                  }
               }

               public void remove() {
                  throw new UnsupportedOperationException();
               }
            };
         }
      };
   }

   static ResourceGroupMBean groupForBeanPair(BeanPair pair) {
      WebLogicMBean parent = pair.getOrig().getParent();
      return parent instanceof ResourceGroupMBean ? (ResourceGroupMBean)parent : null;
   }

   public static ConfigurationMBean clone(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, ConfigurationMBean bean) throws InvalidAttributeValueException, ManagementException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("PP.clone (part=" + (partition == null ? "none" : partition.getName()) + ", rg=" + resourceGroup.getName() + ", bean=" + bean.getType() + ":" + bean.getName());
      }

      ConfigurationMBean clone = clone(domain, partition, resourceGroup, bean, true, domain);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("PP.clone returning clone " + clone.getName() + " with parent " + clone.getParent().getType() + ":" + clone.getParent().getName());
      }

      return clone;
   }

   public static ConfigurationMBean findOrCreateCloneWithOverridesApplied(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, ConfigurationMBean bean, ConfigurationMBean parent) throws InvalidAttributeValueException, ManagementException {
      ConfigurationMBean clone = findExistingClone(parent, partition, bean);
      if (clone != null) {
         return clone;
      } else {
         clone = clone(domain, partition, resourceGroup, bean, false, parent);
         updateCloneConfiguration(clone);
         return clone;
      }
   }

   public PropertyValueVBean[] getPropertyValues(ConfigurationMBean bean, String[] propertyNames) throws Exception {
      PropertyValueHelper helper = new PropertyValueHelper(bean, propertyNames, this);
      return helper.run();
   }

   public PropertyValueVBean[] getPropertyValues(ConfigurationMBean bean, String[] navigationAttributeNames, SettableBean[] beans, String[] propertyNames) throws Exception {
      PropertyValueHelper helper = new PropertyValueHelper(bean, navigationAttributeNames, beans, propertyNames, this);
      return helper.run();
   }

   public SimplePropertyValueVBean[] getEffectiveValues(ConfigurationMBean bean, String[] propertyNames) throws Exception {
      return this.createSimplePropertyValueVBeans(this.getPropertyValues(bean, propertyNames));
   }

   public SimplePropertyValueVBean[] getEffectiveValues(ConfigurationMBean bean, String[] navigationAttributeNames, SettableBean[] beans, String[] propertyNames) throws Exception {
      return this.createSimplePropertyValueVBeans(this.getPropertyValues(bean, navigationAttributeNames, beans, propertyNames));
   }

   private SimplePropertyValueVBean[] createSimplePropertyValueVBeans(PropertyValueVBean[] properties) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
      SimplePropertyValueVBean[] result = new SimplePropertyValueVBean[properties.length];
      Class implClass = this.getClass().getClassLoader().loadClass("weblogic.management.mbeans.custom.SimplePropertyValueVBeanImpl");
      Constructor ctor = implClass.getConstructor(PropertyValueVBean.class);

      for(int i = 0; i < result.length; ++i) {
         result[i] = (SimplePropertyValueVBean)ctor.newInstance(properties[i]);
      }

      return result;
   }

   public static Object processIfClone(SystemResourceMBean bean, Object dataSource) {
      SystemResourceMBean orig = (SystemResourceMBean)getInstance().activeBeanUtil.toOriginalBean(bean);
      if (bean != orig && orig != null) {
         try {
            updateCloneConfiguration(bean, dataSource);
            return dataSource;
         } catch (Exception var4) {
            throw new RuntimeException(var4);
         }
      } else {
         return dataSource;
      }
   }

   static ConfigurationMBean simpleClone(DomainMBean domain, PartitionMBean partition, ConfigurationMBean bean) throws InvalidAttributeValueException, ManagementException {
      return simpleCloneWithParent(partition, bean, domain);
   }

   static ConfigurationMBean simpleCloneOrphan(PartitionMBean partition, ConfigurationMBean bean) throws InvalidAttributeValueException, ManagementException {
      return simpleCloneWithParent(partition, bean, (ConfigurationMBean)null);
   }

   static ConfigurationMBean simpleCloneWithParent(PartitionMBean partition, ConfigurationMBean bean, ConfigurationMBean parent) throws InvalidAttributeValueException, ManagementException {
      return simpleCloneWithParent(partition, bean, parent, true);
   }

   private static ConfigurationMBean simpleCloneWithParent(PartitionMBean partition, ConfigurationMBean bean, ConfigurationMBean parent, boolean adjustName) throws InvalidAttributeValueException, ManagementException {
      if (!(bean instanceof AbstractDescriptorBean)) {
         throw new IllegalArgumentException("bean " + bean + " is not an AbstractDescriptorBean");
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("PP.simpleCloneWithParent(part=" + (partition == null ? "none" : partition.getName()) + ", bean=" + bean.getType() + ":" + bean.getName() + ", parent=" + (parent == null ? "null" : parent.getType() + ":" + parent.getName()));
         }

         AbstractDescriptorBean parentADB = (AbstractDescriptorBean)AbstractDescriptorBean.class.cast(parent);
         AbstractDescriptorBean beanADB = (AbstractDescriptorBean)AbstractDescriptorBean.class.cast(bean);
         ConfigurationMBean clone = parent != null ? (ConfigurationMBean)bean.getClass().cast(beanADB.clone(parentADB)) : (ConfigurationMBean)bean.getClass().cast(beanADB.clone());
         if (adjustName) {
            String name = addSuffix(partition, clone.getName());
            clone.unSet("Name");
            clone.setName(name);
         }

         ((AbstractDescriptorBean)clone)._setTransient(true);
         return clone;
      }
   }

   public static ConfigurationMBean clone(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, ConfigurationMBean bean, boolean autoAddToDomain, ConfigurationMBean parent) throws InvalidAttributeValueException, ManagementException {
      ConfigurationMBean clone = findExistingClone(parent, partition, bean);
      if (clone != null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Found existing clone for bean");
         }

         return clone;
      } else {
         return newClone(domain, partition, resourceGroup, bean, autoAddToDomain, parent);
      }
   }

   public static ConfigurationMBean newClone(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, ConfigurationMBean bean) throws InvalidAttributeValueException, ManagementException {
      return newClone(domain, partition, resourceGroup, bean, false);
   }

   public static ConfigurationMBean newClone(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, ConfigurationMBean bean, boolean autoAddToDomain) throws InvalidAttributeValueException, ManagementException {
      return newClone(domain, partition, resourceGroup, bean, autoAddToDomain, (ConfigurationMBean)null);
   }

   public static ConfigurationMBean newClone(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, ConfigurationMBean bean, boolean autoAddToDomain, ConfigurationMBean parent) throws InvalidAttributeValueException, ManagementException {
      ConfigurationMBean clone = simpleCloneWithParent(partition, bean, parent);
      if (autoAddToDomain) {
         addToTypedDomainCollectionIfPossible(domain, clone);
      }

      ((DescriptorImpl)bean.getDescriptor()).resolveReferences();
      cloneDelegate(bean, clone);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Updating clone name to " + clone.getName());
      }

      setEffectiveTargetsOnClonedBean(domain, partition, resourceGroup, bean, clone);
      return clone;
   }

   static void applyComponentProcessors(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, ConfigurationMBean bean, ConfigurationMBean clone) throws InvalidAttributeValueException, ManagementException {
      Class beanClass = ResourceUtil.beanToClass(bean);
      if (beanClass != null && !beanClassesAbsentFromComponentPartitionProcessorAPI.contains(beanClass)) {
         String attributeName = ResourceUtil.beanClassToAttributeName(beanClass);
         String methodName = "process" + attributeName;

         Method m;
         try {
            m = ComponentPartitionProcessor.class.getDeclaredMethod(methodName, DomainMBean.class, PartitionMBean.class, ResourceGroupMBean.class, beanClass, beanClass);
         } catch (NoSuchMethodException var13) {
            throw new ManagementException(var13);
         }

         Iterator var9 = GlobalServiceLocator.getServiceLocator().getAllServices(ComponentPartitionProcessor.class, new Annotation[0]).iterator();

         while(var9.hasNext()) {
            ComponentPartitionProcessor proc = (ComponentPartitionProcessor)var9.next();

            try {
               m.invoke(proc, domain, partition, resourceGroup, bean, clone);
            } catch (InvocationTargetException | IllegalAccessException var12) {
               throw new ManagementException(var12);
            }
         }

      }
   }

   private static void addToTypedDomainCollectionIfPossible(DomainMBean domain, ConfigurationMBean clone) throws ManagementException {
      Class beanClass = ResourceUtil.beanToClass(clone);
      if (beanClass == null) {
         throw new IllegalArgumentException(clone.getClass().getName());
      } else {
         String attributeName = ResourceUtil.beanClassToAttributeName(beanClass);

         try {
            Method m = domain.getClass().getDeclaredMethod("add" + attributeName, beanClass);
            m.invoke(domain, clone);
         } catch (NoSuchMethodException var6) {
         } catch (IllegalAccessException | InvocationTargetException var7) {
            throw new ManagementException(var7);
         }

      }
   }

   private void updatePartitionDeploymentPlan(PartitionMBean pMBean, OrderedOrganizer cloneOrganizer) throws Exception {
      this.createPlanProcessor(pMBean);
   }

   void updateResourceUsingDeploymentPlan(PartitionMBean partition, ConfigurationMBean bean, String resourceName) throws Exception {
      OverridePartitionDepPlan service = this.createPlanProcessor(partition);
      if (service != null) {
         service.applyResourceOverride(bean, resourceName);
      }

   }

   OverridePartitionDepPlan createPlanProcessor(PartitionMBean pMBean) throws IOException, XMLStreamException {
      OverridePartitionDepPlan service = null;
      if (pMBean == null) {
         return null;
      } else {
         String pOverridePlanPath = pMBean.getResourceDeploymentPlanPath();
         if (pOverridePlanPath != null && !pOverridePlanPath.isEmpty()) {
            service = (OverridePartitionDepPlan)this.planProvider.get();
            service.setPartition(pMBean);
         }

         return service;
      }
   }

   public static ConfigurationMBean setEffectiveTargetsOnClonedBean(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, ConfigurationMBean bean, ConfigurationMBean clone) throws InvalidAttributeValueException, ManagementException {
      Object parent = bean.getParent();
      boolean isPartitionChild = parent instanceof PartitionMBean;
      boolean isResourceGroupChild = parent instanceof ResourceGroupTemplateMBean;
      if (!isResourceGroupChild && !isPartitionChild && !(parent instanceof AppDeploymentMBean)) {
         return clone;
      } else {
         TargetMBean[] effectiveTargets;
         if (isPartitionChild) {
            effectiveTargets = partition.findEffectiveTargets();
         } else {
            effectiveTargets = resourceGroup.findEffectiveTargets();
         }

         if (effectiveTargets.length == 0) {
            return clone;
         } else if (!isTargetable(clone)) {
            return clone;
         } else {
            TargetMBean[] var10;
            int var11;
            int var12;
            TargetMBean t;
            if (clone instanceof TargetInfoMBean) {
               TargetInfoMBean target = (TargetInfoMBean)clone;
               if (!isUntargeted(target)) {
                  target.setTargets(effectiveTargets);
                  if (debugLogger.isDebugEnabled()) {
                     var10 = target.getTargets();
                     var11 = var10.length;

                     for(var12 = 0; var12 < var11; ++var12) {
                        t = var10[var12];
                        debugLogger.debug("Set clone target to " + t);
                     }
                  }
               } else if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Keeping clone untargeted " + target);
               }

               if (clone instanceof BasicDeploymentMBean) {
                  BasicDeploymentMBean basicDeployment = (BasicDeploymentMBean)clone;
                  SubDeploymentMBean[] subDeployments = basicDeployment.getSubDeployments();
                  SubDeploymentMBean[] var19 = subDeployments;
                  int var20 = subDeployments.length;

                  for(int var14 = 0; var14 < var20; ++var14) {
                     SubDeploymentMBean subDeployment = var19[var14];
                     fixupSubDeploymentTargeting(domain, partition, effectiveTargets, subDeployment);
                  }
               }

               return clone;
            } else if (!(clone instanceof DeploymentMBean)) {
               throw new RuntimeException("unsupported type for targets" + clone.getClass());
            } else {
               DeploymentMBean target = (DeploymentMBean)clone;
               target.setTargets(effectiveTargets(clone, effectiveTargets));
               if (debugLogger.isDebugEnabled()) {
                  var10 = target.getTargets();
                  var11 = var10.length;

                  for(var12 = 0; var12 < var11; ++var12) {
                     t = var10[var12];
                     debugLogger.debug("Set clone target to " + t);
                  }
               }

               return clone;
            }
         }
      }
   }

   private static void fixupSubDeploymentTargeting(DomainMBean domain, PartitionMBean partition, TargetMBean[] effectiveTargets, SubDeploymentMBean subDeployment) throws ManagementException, InvalidAttributeValueException {
      boolean updatedTarget = false;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Processing sub deployment " + subDeployment);
      }

      TargetMBean[] targets = subDeployment.getTargets();
      TargetMBean[] newTargets;
      if (targets != null && targets.length > 0) {
         newTargets = new TargetMBean[targets.length];

         for(int i = 0; i < targets.length; ++i) {
            TargetMBean subDepTarget = targets[i];
            newTargets[i] = subDepTarget;
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Processing sub deployment target " + subDepTarget);
            }

            if (subDepTarget instanceof JMSServerMBean) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Found jms server target " + subDepTarget);
               }

               JMSServerMBean jmsServer = domain.lookupJMSServer(addSuffix(partition, subDepTarget.getName()));
               if (jmsServer != null) {
                  newTargets[i] = jmsServer;
                  updatedTarget = true;
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Replacing JMS Server target with " + newTargets[i]);
                  }
               }
            } else if (subDepTarget instanceof SAFAgentMBean) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Found saf agent target " + subDepTarget);
               }

               SAFAgentMBean safAgent = domain.lookupSAFAgent(addSuffix(partition, subDepTarget.getName()));
               if (safAgent != null) {
                  newTargets[i] = safAgent;
                  updatedTarget = true;
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Replacing SAF Agent target with " + newTargets[i]);
                  }
               }
            }
         }
      } else if (!isUntargeted(subDeployment)) {
         newTargets = effectiveTargets;
         updatedTarget = true;
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Adding deployment's effective target to sub deployment target " + subDeployment);
         }
      } else {
         newTargets = null;
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Keeping sub deployment untargeted " + subDeployment);
         }
      }

      if (updatedTarget) {
         subDeployment.setTargets(newTargets);
         SubDeploymentMBean[] var11 = subDeployment.getSubDeployments();
         int var12 = var11.length;

         for(int var14 = 0; var14 < var12; ++var14) {
            SubDeploymentMBean subSub = var11[var14];
            fixupSubDeploymentTargeting(findRoot(subDeployment), partition, effectiveTargets, subSub);
         }
      }

   }

   private static boolean isUntargeted(TargetInfoMBean bean) {
      if (bean instanceof AppDeploymentMBean) {
         return ((AppDeploymentMBean)bean).isUntargeted();
      } else {
         return bean instanceof SubDeploymentMBean ? ((SubDeploymentMBean)bean).isUntargeted() : false;
      }
   }

   private static boolean isTargetable(ConfigurationMBean bean) {
      return !(bean instanceof JMSBridgeDestinationMBean);
   }

   public static ConfigurationMBean findExistingClone(ConfigurationMBean parent, PartitionMBean partition, ConfigurationMBean bean) {
      return findExistingClone(parent, partition, bean, bean.getClass());
   }

   public static ConfigurationMBean findExistingClone(ConfigurationMBean parent, PartitionMBean partition, ConfigurationMBean bean, Class beanClass) {
      String name = addSuffix(partition, bean.getName());
      Class bClass = ResourceUtil.beanToClass(bean);
      String methodName = "lookup" + ResourceUtil.beanClassToAttributeName(bClass);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("PP.findExistingClone(parent=" + parent.getType() + ":" + parent.getName() + ", bean=" + bean.getType() + ":" + bean.getName() + ", beanClass=" + beanClass.getName() + " will invoke method " + methodName + " on parent");
      }

      try {
         Method lookupMethod = parent.getClass().getMethod(methodName, String.class);
         Object lookedUpClone = lookupMethod.invoke(parent, name);
         if (lookedUpClone == null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Did not find clone; returning null");
            }

            return null;
         } else if (!beanClass.isAssignableFrom(lookedUpClone.getClass())) {
            throw new IllegalArgumentException("Expected type of result from calling " + lookupMethod.getName() + " is " + beanClass.getName() + " but existing resource " + name + " is of type " + lookedUpClone.getClass().getName());
         } else {
            ConfigurationMBean returnedClone = (ConfigurationMBean)lookedUpClone;
            if (debugLogger.isDebugEnabled()) {
               WebLogicMBean parentOfReturnedClone = returnedClone.getParent();
               debugLogger.debug("Found existing clone; returning " + returnedClone.getType() + ":" + returnedClone.getName() + " with parent " + parentOfReturnedClone.getType() + ":" + parentOfReturnedClone.getName());
            }

            return (ConfigurationMBean)lookedUpClone;
         }
      } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException var11) {
         throw new RuntimeException(var11);
      }
   }

   private static TargetMBean[] effectiveTargets(ConfigurationMBean clone, TargetMBean[] effectiveTargets) {
      List deploymentTargets = new ArrayList();
      boolean canUseVH = false;

      ComponentPartitionProcessor cpp;
      for(Iterator var4 = getInstance().componentProcessors.iterator(); var4.hasNext(); canUseVH |= cpp.isTargetableVirtually(clone)) {
         cpp = (ComponentPartitionProcessor)var4.next();
      }

      TargetMBean[] var8 = effectiveTargets;
      int var9 = effectiveTargets.length;

      for(int var6 = 0; var6 < var9; ++var6) {
         TargetMBean t = var8[var6];
         if (canUseVH || !(t instanceof VirtualHostMBean) && !(t instanceof VirtualTargetMBean)) {
            deploymentTargets.add(t);
         } else if (t instanceof VirtualHostMBean) {
            deploymentTargets.addAll(Arrays.asList(((VirtualHostMBean)t).getTargets()));
         } else if (t instanceof VirtualTargetMBean) {
            deploymentTargets.addAll(Arrays.asList(((VirtualTargetMBean)t).getTargets()));
         }
      }

      return (TargetMBean[])deploymentTargets.toArray(new TargetMBean[deploymentTargets.size()]);
   }

   public static String addSuffix(PartitionMBean partition, String name) {
      return name + PartitionUtils.getSuffix(partition);
   }

   public static void cloneDelegate(ConfigurationMBean orig, ConfigurationMBean clone) {
      try {
         Class clz = orig.getClass();
         Method mthd = clz.getMethod("_getDelegateBean");
         Object delegate = mthd.invoke(orig);
         if (delegate != null) {
            clz = clone.getClass();
            Class[] params = new Class[]{clz};
            mthd = clz.getMethod("_setDelegateBean", params);
            Object[] args1 = new Object[]{delegate};
            mthd.invoke(clone, args1);
         }
      } catch (NoSuchMethodException var7) {
         throw new RuntimeException("Could not find the method to get or set the delegate MBean", var7);
      } catch (IllegalAccessException var8) {
         throw new RuntimeException("Illegal Access while getting or setting the delegate MBean", var8);
      } catch (InvocationTargetException var9) {
         throw new RuntimeException("Invocation Target exception while getting or setting the delegate MBean", var9);
      }
   }

   private static DomainMBean findRoot(ConfigurationMBean bean) {
      for(WebLogicMBean parent = bean.getParent(); parent != null; parent = parent.getParent()) {
         if (parent instanceof DomainMBean) {
            return (DomainMBean)parent;
         }
      }

      return null;
   }

   public static class BeanPair {
      private final ConfigurationMBean orig;
      private final ConfigurationMBean clone;
      private final Object resource;

      public BeanPair(ConfigurationMBean orig, ConfigurationMBean clone, Object resource) {
         this.orig = orig;
         this.clone = clone;
         this.resource = resource;
      }

      public BeanPair(ConfigurationMBean orig, ConfigurationMBean clone) {
         this(orig, clone, (Object)null);
      }

      public ConfigurationMBean getOrig() {
         return this.orig;
      }

      public ConfigurationMBean getClone() {
         return this.clone;
      }

      public Object getResource() {
         return this.resource;
      }
   }

   private class NewOrExistingCloneConfigUpdateHelper implements ConfigUpdateHelper {
      private NewOrExistingCloneConfigUpdateHelper() {
      }

      public ConfigurationMBean locateClone(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, ConfigurationMBean bean, Class beanClass) throws ManagementException {
         try {
            return PartitionProcessor.clone(domain, partition, resourceGroup, bean);
         } catch (InvalidAttributeValueException var7) {
            throw new ManagementException(var7);
         }
      }

      public void applyComponentProcessors(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, ConfigurationMBean bean, ConfigurationMBean clone, Object resource) throws InvalidAttributeValueException, ManagementException {
         PartitionProcessor.applyComponentProcessors(domain, partition, resourceGroup, bean, clone);
      }

      // $FF: synthetic method
      NewOrExistingCloneConfigUpdateHelper(Object x1) {
         this();
      }
   }

   private class ExistingCloneConfigUpdateHelper implements ConfigUpdateHelper {
      private ExistingCloneConfigUpdateHelper() {
      }

      public ConfigurationMBean locateClone(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, ConfigurationMBean bean, Class beanClass) throws ManagementException {
         ConfigurationMBean clone = PartitionProcessor.findExistingClone(domain, partition, bean, beanClass);
         if (clone == null && !(bean instanceof AppDeploymentMBean)) {
            throw new ManagementException(new IllegalArgumentException("Missing clone for config bean " + (partition == null ? "" : partition.getName()) + "/" + bean.getName() + " (" + beanClass.getName() + ")"));
         } else {
            return clone;
         }
      }

      public void applyComponentProcessors(DomainMBean domain, PartitionMBean partition, ResourceGroupMBean resourceGroup, ConfigurationMBean bean, ConfigurationMBean clone, Object resource) throws InvalidAttributeValueException, ManagementException {
         Iterator var7 = PartitionProcessor.this.componentPartitionResourceProcessors.iterator();

         while(var7.hasNext()) {
            ComponentPartitionSystemResourceProcessor cprp = (ComponentPartitionSystemResourceProcessor)var7.next();
            if (cprp.handles(bean.getClass())) {
               Class resourceClass = cprp.descriptorBeanType();
               if (resourceClass == null) {
                  throw new ManagementException(cprp.getClass().getName() + " reports handling type " + bean.getClass().getName() + " but provided a null descriptor bean type");
               }

               try {
                  cprp.processResource(partition, resourceGroup, (SystemResourceMBean)SystemResourceMBean.class.cast(bean), (SystemResourceMBean)SystemResourceMBean.class.cast(clone), resourceClass.cast(resource));
               } catch (ClassCastException var11) {
                  throw new ManagementException(var11);
               }
            }
         }

      }

      // $FF: synthetic method
      ExistingCloneConfigUpdateHelper(Object x1) {
         this();
      }
   }

   private interface ConfigUpdateHelper {
      ConfigurationMBean locateClone(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, ConfigurationMBean var4, Class var5) throws ManagementException;

      void applyComponentProcessors(DomainMBean var1, PartitionMBean var2, ResourceGroupMBean var3, ConfigurationMBean var4, ConfigurationMBean var5, Object var6) throws InvalidAttributeValueException, ManagementException;
   }
}
