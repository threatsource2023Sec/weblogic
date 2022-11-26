package weblogic.management.rest.lib.bean.utils;

import java.beans.PropertyDescriptor;
import javax.servlet.http.HttpServletRequest;

abstract class BeanAttributeTypeImpl extends AttributeTypeImpl implements BeanAttributeType {
   private String typeName;
   private BeanType type;

   protected BeanAttributeTypeImpl(BeanType beanType, PropertyDescriptor pd) throws Exception {
      super(beanType, pd);
      this.typeName = DescriptorUtils.getBeanTypeName(pd);
   }

   public String getTypeName() {
      return this.typeName;
   }

   public BeanType getType(HttpServletRequest request) throws Exception {
      if (this.type == null) {
         this.type = BeanType.getBeanType(request, this.getTypeName());
         if (this.type == null) {
            throw new AssertionError("Type " + this.getTypeName() + " not found");
         }
      }

      return this.type;
   }
}
