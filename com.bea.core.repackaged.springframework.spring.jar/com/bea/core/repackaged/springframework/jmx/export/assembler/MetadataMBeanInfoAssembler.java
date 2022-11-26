package com.bea.core.repackaged.springframework.jmx.export.assembler;

import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.beans.BeanUtils;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.jmx.export.metadata.InvalidMetadataException;
import com.bea.core.repackaged.springframework.jmx.export.metadata.JmxAttributeSource;
import com.bea.core.repackaged.springframework.jmx.export.metadata.JmxMetadataUtils;
import com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedAttribute;
import com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedMetric;
import com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedNotification;
import com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedOperation;
import com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedOperationParameter;
import com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedResource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import javax.management.Descriptor;
import javax.management.MBeanParameterInfo;
import javax.management.modelmbean.ModelMBeanNotificationInfo;

public class MetadataMBeanInfoAssembler extends AbstractReflectiveMBeanInfoAssembler implements AutodetectCapableMBeanInfoAssembler, InitializingBean {
   @Nullable
   private JmxAttributeSource attributeSource;

   public MetadataMBeanInfoAssembler() {
   }

   public MetadataMBeanInfoAssembler(JmxAttributeSource attributeSource) {
      Assert.notNull(attributeSource, (String)"JmxAttributeSource must not be null");
      this.attributeSource = attributeSource;
   }

   public void setAttributeSource(JmxAttributeSource attributeSource) {
      Assert.notNull(attributeSource, (String)"JmxAttributeSource must not be null");
      this.attributeSource = attributeSource;
   }

   public void afterPropertiesSet() {
      if (this.attributeSource == null) {
         throw new IllegalArgumentException("Property 'attributeSource' is required");
      }
   }

   private JmxAttributeSource obtainAttributeSource() {
      Assert.state(this.attributeSource != null, "No JmxAttributeSource set");
      return this.attributeSource;
   }

   protected void checkManagedBean(Object managedBean) throws IllegalArgumentException {
      if (AopUtils.isJdkDynamicProxy(managedBean)) {
         throw new IllegalArgumentException("MetadataMBeanInfoAssembler does not support JDK dynamic proxies - export the target beans directly or use CGLIB proxies instead");
      }
   }

   public boolean includeBean(Class beanClass, String beanName) {
      return this.obtainAttributeSource().getManagedResource(this.getClassToExpose(beanClass)) != null;
   }

   protected boolean includeReadAttribute(Method method, String beanKey) {
      return this.hasManagedAttribute(method) || this.hasManagedMetric(method);
   }

   protected boolean includeWriteAttribute(Method method, String beanKey) {
      return this.hasManagedAttribute(method);
   }

   protected boolean includeOperation(Method method, String beanKey) {
      PropertyDescriptor pd = BeanUtils.findPropertyForMethod(method);
      return pd != null && this.hasManagedAttribute(method) || this.hasManagedOperation(method);
   }

   private boolean hasManagedAttribute(Method method) {
      return this.obtainAttributeSource().getManagedAttribute(method) != null;
   }

   private boolean hasManagedMetric(Method method) {
      return this.obtainAttributeSource().getManagedMetric(method) != null;
   }

   private boolean hasManagedOperation(Method method) {
      return this.obtainAttributeSource().getManagedOperation(method) != null;
   }

   protected String getDescription(Object managedBean, String beanKey) {
      ManagedResource mr = this.obtainAttributeSource().getManagedResource(this.getClassToExpose(managedBean));
      return mr != null ? mr.getDescription() : "";
   }

   protected String getAttributeDescription(PropertyDescriptor propertyDescriptor, String beanKey) {
      Method readMethod = propertyDescriptor.getReadMethod();
      Method writeMethod = propertyDescriptor.getWriteMethod();
      ManagedAttribute getter = readMethod != null ? this.obtainAttributeSource().getManagedAttribute(readMethod) : null;
      ManagedAttribute setter = writeMethod != null ? this.obtainAttributeSource().getManagedAttribute(writeMethod) : null;
      if (getter != null && StringUtils.hasText(getter.getDescription())) {
         return getter.getDescription();
      } else if (setter != null && StringUtils.hasText(setter.getDescription())) {
         return setter.getDescription();
      } else {
         ManagedMetric metric = readMethod != null ? this.obtainAttributeSource().getManagedMetric(readMethod) : null;
         return metric != null && StringUtils.hasText(metric.getDescription()) ? metric.getDescription() : propertyDescriptor.getDisplayName();
      }
   }

   protected String getOperationDescription(Method method, String beanKey) {
      PropertyDescriptor pd = BeanUtils.findPropertyForMethod(method);
      if (pd != null) {
         ManagedAttribute ma = this.obtainAttributeSource().getManagedAttribute(method);
         if (ma != null && StringUtils.hasText(ma.getDescription())) {
            return ma.getDescription();
         } else {
            ManagedMetric metric = this.obtainAttributeSource().getManagedMetric(method);
            return metric != null && StringUtils.hasText(metric.getDescription()) ? metric.getDescription() : method.getName();
         }
      } else {
         ManagedOperation mo = this.obtainAttributeSource().getManagedOperation(method);
         return mo != null && StringUtils.hasText(mo.getDescription()) ? mo.getDescription() : method.getName();
      }
   }

   protected MBeanParameterInfo[] getOperationParameters(Method method, String beanKey) {
      ManagedOperationParameter[] params = this.obtainAttributeSource().getManagedOperationParameters(method);
      if (ObjectUtils.isEmpty((Object[])params)) {
         return super.getOperationParameters(method, beanKey);
      } else {
         MBeanParameterInfo[] parameterInfo = new MBeanParameterInfo[params.length];
         Class[] methodParameters = method.getParameterTypes();

         for(int i = 0; i < params.length; ++i) {
            ManagedOperationParameter param = params[i];
            parameterInfo[i] = new MBeanParameterInfo(param.getName(), methodParameters[i].getName(), param.getDescription());
         }

         return parameterInfo;
      }
   }

   protected ModelMBeanNotificationInfo[] getNotificationInfo(Object managedBean, String beanKey) {
      ManagedNotification[] notificationAttributes = this.obtainAttributeSource().getManagedNotifications(this.getClassToExpose(managedBean));
      ModelMBeanNotificationInfo[] notificationInfos = new ModelMBeanNotificationInfo[notificationAttributes.length];

      for(int i = 0; i < notificationAttributes.length; ++i) {
         ManagedNotification attribute = notificationAttributes[i];
         notificationInfos[i] = JmxMetadataUtils.convertToModelMBeanNotificationInfo(attribute);
      }

      return notificationInfos;
   }

   protected void populateMBeanDescriptor(Descriptor desc, Object managedBean, String beanKey) {
      ManagedResource mr = this.obtainAttributeSource().getManagedResource(this.getClassToExpose(managedBean));
      if (mr == null) {
         throw new InvalidMetadataException("No ManagedResource attribute found for class: " + this.getClassToExpose(managedBean));
      } else {
         this.applyCurrencyTimeLimit(desc, mr.getCurrencyTimeLimit());
         if (mr.isLog()) {
            desc.setField("log", "true");
         }

         if (StringUtils.hasLength(mr.getLogFile())) {
            desc.setField("logFile", mr.getLogFile());
         }

         if (StringUtils.hasLength(mr.getPersistPolicy())) {
            desc.setField("persistPolicy", mr.getPersistPolicy());
         }

         if (mr.getPersistPeriod() >= 0) {
            desc.setField("persistPeriod", Integer.toString(mr.getPersistPeriod()));
         }

         if (StringUtils.hasLength(mr.getPersistName())) {
            desc.setField("persistName", mr.getPersistName());
         }

         if (StringUtils.hasLength(mr.getPersistLocation())) {
            desc.setField("persistLocation", mr.getPersistLocation());
         }

      }
   }

   protected void populateAttributeDescriptor(Descriptor desc, @Nullable Method getter, @Nullable Method setter, String beanKey) {
      if (getter != null) {
         ManagedMetric metric = this.obtainAttributeSource().getManagedMetric(getter);
         if (metric != null) {
            this.populateMetricDescriptor(desc, metric);
            return;
         }
      }

      ManagedAttribute gma = getter != null ? this.obtainAttributeSource().getManagedAttribute(getter) : null;
      ManagedAttribute sma = setter != null ? this.obtainAttributeSource().getManagedAttribute(setter) : null;
      this.populateAttributeDescriptor(desc, gma != null ? gma : ManagedAttribute.EMPTY, sma != null ? sma : ManagedAttribute.EMPTY);
   }

   private void populateAttributeDescriptor(Descriptor desc, ManagedAttribute gma, ManagedAttribute sma) {
      this.applyCurrencyTimeLimit(desc, this.resolveIntDescriptor(gma.getCurrencyTimeLimit(), sma.getCurrencyTimeLimit()));
      Object defaultValue = this.resolveObjectDescriptor(gma.getDefaultValue(), sma.getDefaultValue());
      desc.setField("default", defaultValue);
      String persistPolicy = this.resolveStringDescriptor(gma.getPersistPolicy(), sma.getPersistPolicy());
      if (StringUtils.hasLength(persistPolicy)) {
         desc.setField("persistPolicy", persistPolicy);
      }

      int persistPeriod = this.resolveIntDescriptor(gma.getPersistPeriod(), sma.getPersistPeriod());
      if (persistPeriod >= 0) {
         desc.setField("persistPeriod", Integer.toString(persistPeriod));
      }

   }

   private void populateMetricDescriptor(Descriptor desc, ManagedMetric metric) {
      this.applyCurrencyTimeLimit(desc, metric.getCurrencyTimeLimit());
      if (StringUtils.hasLength(metric.getPersistPolicy())) {
         desc.setField("persistPolicy", metric.getPersistPolicy());
      }

      if (metric.getPersistPeriod() >= 0) {
         desc.setField("persistPeriod", Integer.toString(metric.getPersistPeriod()));
      }

      if (StringUtils.hasLength(metric.getDisplayName())) {
         desc.setField("displayName", metric.getDisplayName());
      }

      if (StringUtils.hasLength(metric.getUnit())) {
         desc.setField("units", metric.getUnit());
      }

      if (StringUtils.hasLength(metric.getCategory())) {
         desc.setField("metricCategory", metric.getCategory());
      }

      desc.setField("metricType", metric.getMetricType().toString());
   }

   protected void populateOperationDescriptor(Descriptor desc, Method method, String beanKey) {
      ManagedOperation mo = this.obtainAttributeSource().getManagedOperation(method);
      if (mo != null) {
         this.applyCurrencyTimeLimit(desc, mo.getCurrencyTimeLimit());
      }

   }

   private int resolveIntDescriptor(int getter, int setter) {
      return getter >= setter ? getter : setter;
   }

   @Nullable
   private Object resolveObjectDescriptor(@Nullable Object getter, @Nullable Object setter) {
      return getter != null ? getter : setter;
   }

   @Nullable
   private String resolveStringDescriptor(@Nullable String getter, @Nullable String setter) {
      return StringUtils.hasLength(getter) ? getter : setter;
   }
}
