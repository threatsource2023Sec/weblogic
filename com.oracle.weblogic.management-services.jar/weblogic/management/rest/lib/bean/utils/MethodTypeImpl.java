package weblogic.management.rest.lib.bean.utils;

import java.beans.MethodDescriptor;
import java.lang.reflect.Method;

class MethodTypeImpl extends MemberTypeImpl implements MethodType {
   private MethodDescriptor md;

   protected MethodTypeImpl(BeanType beanType, MethodDescriptor md) throws Exception {
      super(beanType, VersionVisibility.getVersionVisibility(beanType.getVersionVisibility(), md), md.getName(), DescriptorUtils.getVisibleToPartitions(md), DescriptorUtils.isInternal(md));
      this.md = md;
   }

   public MethodDescriptor getMethodDescriptor() {
      return this.md;
   }

   public Method getMethod() {
      return this.getMethodDescriptor().getMethod();
   }

   public String getDescription() {
      return DescriptorUtils.getDescription(this.getMethodDescriptor());
   }

   public String getImpact() {
      return DescriptorUtils.getStringField(this.getMethodDescriptor(), "impact");
   }
}
