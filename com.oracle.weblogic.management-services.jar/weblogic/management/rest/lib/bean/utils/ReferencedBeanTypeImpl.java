package weblogic.management.rest.lib.bean.utils;

import java.beans.PropertyDescriptor;

class ReferencedBeanTypeImpl extends BeanAttributeTypeImpl implements ReferencedBeanType {
   ReferencedBeanTypeImpl(BeanType beanType, PropertyDescriptor pd) throws Exception {
      super(beanType, pd);
   }
}
