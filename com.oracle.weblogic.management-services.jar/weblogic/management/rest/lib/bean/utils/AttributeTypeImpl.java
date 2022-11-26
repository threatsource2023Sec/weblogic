package weblogic.management.rest.lib.bean.utils;

import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import weblogic.management.runtime.RuntimeMBean;

abstract class AttributeTypeImpl extends MemberTypeImpl implements AttributeType {
   private PropertyDescriptor pd;
   private boolean restartRequired;

   protected AttributeTypeImpl(BeanType beanType, PropertyDescriptor pd) throws Exception {
      super(beanType, VersionVisibility.getVersionVisibility(beanType.getVersionVisibility(), pd), DescriptorUtils.getRestName((FeatureDescriptor)pd), DescriptorUtils.getVisibleToPartitions(pd), DescriptorUtils.isInternal(pd));
      this.pd = pd;
      this.restartRequired = this.computeRestartRequired();
   }

   private boolean computeRestartRequired() throws Exception {
      if (!this.isWritable()) {
         return false;
      } else {
         if (!this.getBeanType().isCustomSecurityProvider()) {
            Class clazz = this.getBeanType().getBeanClass();
            if (RuntimeMBean.class.isAssignableFrom(clazz)) {
               return false;
            }

            if (BeanUtils.isVBeanClass(clazz)) {
               return false;
            }
         }

         boolean dynamic = DescriptorUtils.getBooleanField(this.pd, "dynamic");
         return !dynamic;
      }
   }

   public PropertyDescriptor getPropertyDescriptor() {
      return this.pd;
   }

   public Method getReader() {
      return this.pd.getReadMethod();
   }

   public Method getWriter() {
      return this.pd.getWriteMethod();
   }

   public boolean isWritable() {
      return this.getWriter() != null;
   }

   public boolean isRestartRequired() {
      return this.restartRequired;
   }

   public String getDescription() {
      return DescriptorUtils.getDescription(this.getPropertyDescriptor());
   }
}
