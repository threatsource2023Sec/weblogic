package weblogic.management.internal.mbean;

import weblogic.descriptor.DescriptorBean;
import weblogic.utils.codegen.AttributeBinder;

public class SecurityReadOnlyMBeanBinder extends ReadOnlyMBeanBinder {
   protected SecurityReadOnlyMBeanBinder(DescriptorBean bean) {
      super(bean);
   }

   public AttributeBinder bindAttribute(String name, Object value) {
      return super.bindAttribute(name, value);
   }
}
