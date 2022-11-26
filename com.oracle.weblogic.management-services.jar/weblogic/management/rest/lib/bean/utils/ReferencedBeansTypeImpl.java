package weblogic.management.rest.lib.bean.utils;

import java.beans.PropertyDescriptor;

class ReferencedBeansTypeImpl extends BeanAttributeTypeImpl implements ReferencedBeansType {
   private boolean ordered;

   ReferencedBeansTypeImpl(BeanType beanType, PropertyDescriptor pd) throws Exception {
      super(beanType, pd);
      this.ordered = DescriptorUtils.isOrdered(pd);
   }

   public boolean isOrdered() {
      return this.ordered;
   }
}
