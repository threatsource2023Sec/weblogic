package weblogic.management.provider.internal;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.ManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PropertyValueVBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.PartitionUtils;

public class PropertyValueHelper {
   private static final String DEFAULT_VALUE_NULL_ATTR_NAME = "defaultValueNull";
   private static final String DEFAULT_VALUE_ATTR_NAME = "default";
   private static final String PROPERTY_VALUE_VBEAN_IMPL_CLASS_NAME = "weblogic.management.mbeans.custom.PropertyValueVBeanImpl";
   private static final String GET_DELEGATE_BEAN_METHOD_NAME = "_getDelegateBean";
   private final PartitionProcessor partitionProcessor;
   private final ConfigurationMBean originalConfigBean;
   private final String[] navigationAttributeNames;
   private final SettableBean[] beans;
   private final String[] propertyNames;
   private DomainMBean domain;
   private PartitionMBean partition;
   private ResourceGroupMBean resourceGroup;
   private Constructor ctor;

   PropertyValueHelper(ConfigurationMBean configBean, String[] propertyNames, PartitionProcessor partitionProcessor) {
      this(configBean, new String[0], new SettableBean[0], propertyNames, partitionProcessor);
   }

   PropertyValueHelper(ConfigurationMBean configBean, String[] navigationAttributeNames, SettableBean[] beans, String[] propertyNames, PartitionProcessor partitionProcessor) {
      this.ctor = null;
      this.navigationAttributeNames = navigationAttributeNames;
      this.beans = beans;

      for(WebLogicMBean parent = configBean.getParent(); parent != null; parent = parent.getParent()) {
         if (parent instanceof ResourceGroupMBean) {
            this.resourceGroup = (ResourceGroupMBean)parent;
         }

         if (parent instanceof PartitionMBean) {
            this.partition = (PartitionMBean)parent;
         } else if (parent instanceof DomainMBean) {
            this.domain = (DomainMBean)parent;
            break;
         }
      }

      this.partitionProcessor = partitionProcessor;
      this.originalConfigBean = configBean;
      if (!(configBean instanceof AbstractDescriptorBean)) {
         throw new IllegalArgumentException(configBean.getClass().getName() + " is not of type AbstractDescriptorBean");
      } else {
         this.propertyNames = propertyNames;
      }
   }

   private SettableBean navigateToBean(ConfigurationMBean clone) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
      NavigationHelper helper = new NavigationHelper();
      BeanNavigator nav = new BeanNavigator(this.navigationAttributeNames, this.beans, helper, SettableBean.class);
      return (SettableBean)nav.navigate(clone);
   }

   PropertyValueVBean[] run() throws Exception {
      PartitionProcessor var10000 = this.partitionProcessor;
      ConfigurationMBean clone = PartitionProcessor.simpleClone(this.domain, this.partition, this.originalConfigBean);
      if (!(clone instanceof AbstractDescriptorBean)) {
         throw new IllegalArgumentException("Clone of type " + clone.getClass().getName() + " is not of type AbstractDescriptorBean");
      } else {
         AbstractDescriptorBean adClone = (AbstractDescriptorBean)clone;
         Object beanToProbe = this.navigateToBean(clone);
         AbstractDescriptorBean adBean = abstractify(beanToProbe);
         BeanInfo beanInfo = ManagementService.getBeanInfoAccess().getBeanInfoForDescriptorBean(adBean);
         String[] propNamesToUse = this.choosePropertyNames(beanInfo, this.propertyNames);
         PropertyValueVBean[] result = this.createPropertyValueVBeans(propNamesToUse);

         for(int i = 0; i < result.length; ++i) {
            PropertyValueVBean prop = result[i];
            if (adBean.isSet(prop.getPropertyName())) {
               this.setOriginalValue(adBean, prop, beanInfo);
            } else {
               this.setOriginalValueFromDelegateIfAvailable(adBean, prop, beanInfo);
            }

            prop.setDefaultValue(this.getDefaultValue(beanInfo, prop.getPropertyName()));
         }

         ClonePropertyChangeListener listener = new ClonePropertyChangeListener();
         adBean.addPropertyChangeListener(listener);
         this.applyResourceDeploymentPlan(result, clone, this.originalConfigBean.getName(), adBean, listener, beanInfo);
         this.applyOverridingConfigBeans(result, clone, adBean, listener, beanInfo);
         return result;
      }
   }

   private static AbstractDescriptorBean abstractify(Object b) {
      if (!(b instanceof AbstractDescriptorBean)) {
         throw new IllegalArgumentException("Bean " + b.toString() + " is not also of type AbstractDescriptorBean");
      } else {
         return (AbstractDescriptorBean)b;
      }
   }

   private PropertyValueVBean[] createPropertyValueVBeans(String[] propertyNames) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
      PropertyValueVBean[] result = new PropertyValueVBean[propertyNames.length];
      Class implClass = this.getClass().getClassLoader().loadClass("weblogic.management.mbeans.custom.PropertyValueVBeanImpl");
      Constructor ctor = implClass.getConstructor();

      for(int i = 0; i < result.length; ++i) {
         result[i] = (PropertyValueVBean)ctor.newInstance();
         result[i].setPropertyName(propertyNames[i]);
      }

      return result;
   }

   private Object getDefaultValue(BeanInfo beanInfo, String propertyName) {
      PropertyDescriptor[] var3 = beanInfo.getPropertyDescriptors();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PropertyDescriptor pd = var3[var5];
         if (pd.getName().equals(propertyName)) {
            Object defaultValueNullObj = pd.getValue("defaultValueNull");
            if (defaultValueNullObj != null && defaultValueNullObj instanceof Boolean) {
               return null;
            }

            return pd.getValue("default");
         }
      }

      throw new IllegalArgumentException(propertyName);
   }

   private String[] choosePropertyNames(BeanInfo beanInfo, String[] requestedPropertyNames) {
      if (requestedPropertyNames != null) {
         return requestedPropertyNames;
      } else {
         PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
         String[] result = new String[propertyDescriptors.length];
         int i = 0;
         PropertyDescriptor[] var6 = propertyDescriptors;
         int var7 = propertyDescriptors.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            PropertyDescriptor pd = var6[var8];
            result[i++] = pd.getName();
         }

         return result;
      }
   }

   private void setOriginalValue(AbstractDescriptorBean adBean, PropertyValueVBean prop, BeanInfo beanInfo) throws ManagementException {
      String propName = prop.getPropertyName();
      PropertyDescriptor pd = this.findPropertyDescriptor(beanInfo, propName);
      prop.setOriginalValue(this.getPropertyValue(adBean, pd));
   }

   private void setOriginalValueFromDelegateIfAvailable(AbstractDescriptorBean adBean, PropertyValueVBean prop, BeanInfo beanInfo) throws InvocationTargetException, IllegalAccessException, ManagementException {
      try {
         Method getDelegateMethod = adBean.getClass().getDeclaredMethod("_getDelegateBean");
         Object delegate = getDelegateMethod.invoke(adBean);
         if (delegate == null) {
            return;
         }

         String propName = prop.getPropertyName();
         PropertyDescriptor pd = this.findPropertyDescriptor(beanInfo, propName);
         Object valueFromDelegate = pd.getReadMethod().invoke(delegate);
         if (valueFromDelegate != null) {
            prop.setOriginalValue(valueFromDelegate);
         }
      } catch (NoSuchMethodException var9) {
      }

   }

   private void applyOverridingConfigBeans(PropertyValueVBean[] result, ConfigurationMBean clone, AbstractDescriptorBean probedBean, ClonePropertyChangeListener listener, BeanInfo beanInfo) throws InvalidAttributeValueException, ManagementException {
      listener.clear();
      PartitionProcessor.applyComponentProcessors(this.domain, this.partition, this.resourceGroup, this.originalConfigBean, clone);
      PropertyValueVBean[] var6 = result;
      int var7 = result.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         PropertyValueVBean propertyValueVBean = var6[var8];
         String propName = propertyValueVBean.getPropertyName();
         if (listener.overriddenPropertyNames().contains(propName)) {
            PropertyDescriptor pd = this.findPropertyDescriptor(beanInfo, propName);
            Object newValue = this.getPropertyValue(probedBean, pd);
            propertyValueVBean.setOverridingConfigBeanValue(newValue);
         }
      }

   }

   private void applyResourceDeploymentPlan(PropertyValueVBean[] result, ConfigurationMBean clone, String resourceName, AbstractDescriptorBean probedBean, ClonePropertyChangeListener listener, BeanInfo beanInfo) throws Exception {
      listener.clear();
      this.partitionProcessor.updateResourceUsingDeploymentPlan(this.partition, clone, resourceName);
      PropertyValueVBean[] var7 = result;
      int var8 = result.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         PropertyValueVBean propertyValueVBean = var7[var9];
         String propName = propertyValueVBean.getPropertyName();
         if (listener.overriddenPropertyNames().contains(propName)) {
            PropertyDescriptor pd = this.findPropertyDescriptor(beanInfo, propName);
            Object newValue = this.getPropertyValue(probedBean, pd);
            propertyValueVBean.setResourceDeploymentPlanValue(newValue);
         }
      }

   }

   private PropertyDescriptor findPropertyDescriptor(BeanInfo beanInfo, String propName) throws ManagementException {
      PropertyDescriptor[] var3 = beanInfo.getPropertyDescriptors();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         PropertyDescriptor pd = var3[var5];
         if (pd.getName().equals(propName)) {
            return pd;
         }
      }

      throw new ManagementException("Property " + propName + " is not defined on " + beanInfo.getBeanDescriptor().getDisplayName());
   }

   private Object getPropertyValue(Object bean, PropertyDescriptor pd) throws ManagementException {
      try {
         Object propValue = pd.getReadMethod().invoke(bean);
         return propValue;
      } catch (IllegalAccessException | InvocationTargetException var5) {
         throw new ManagementException(var5);
      }
   }

   private class ClonePropertyChangeListener implements PropertyChangeListener {
      private final Set overriddenPropertyNames;

      private ClonePropertyChangeListener() {
         this.overriddenPropertyNames = new HashSet();
      }

      public void propertyChange(PropertyChangeEvent evt) {
         this.overriddenPropertyNames.add(evt.getPropertyName());
      }

      private Set overriddenPropertyNames() {
         return this.overriddenPropertyNames;
      }

      private void clear() {
         this.overriddenPropertyNames.clear();
      }

      // $FF: synthetic method
      ClonePropertyChangeListener(Object x1) {
         this();
      }
   }

   private class NavigationHelper implements BeanNavigator.Helper {
      private NavigationHelper() {
      }

      public BeanInfo getBeanInfo(Object o) throws IntrospectionException {
         return DescriptorBean.class.isAssignableFrom(o.getClass()) ? ManagementService.getBeanInfoAccess().getBeanInfoForDescriptorBean((DescriptorBean)o) : Introspector.getBeanInfo(o.getClass());
      }

      public boolean childNameMatches(String originalChildName, String candidateChildName) {
         String matchingCloneName = originalChildName + PartitionUtils.getSuffix(PropertyValueHelper.this.partition);
         return matchingCloneName.equals(candidateChildName) || originalChildName.equals(candidateChildName);
      }

      // $FF: synthetic method
      NavigationHelper(Object x1) {
         this();
      }
   }
}
