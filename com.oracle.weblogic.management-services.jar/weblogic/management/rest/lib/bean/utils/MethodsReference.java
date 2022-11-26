package weblogic.management.rest.lib.bean.utils;

import java.beans.MethodDescriptor;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

class MethodsReference {
   private BeanType beanType;
   private String methodName;
   private List methodTypes = null;

   MethodsReference(BeanType beanType, String methodName) {
      this.beanType = beanType;
      this.methodName = methodName;
   }

   List getMethodTypes(HttpServletRequest request) throws Exception {
      if (this.methodName == null) {
         return null;
      } else {
         if (this.methodTypes == null) {
            MethodDescriptor[] var2 = this.beanType.getBeanInfo().getMethodDescriptors();
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               MethodDescriptor md = var2[var4];
               if (DescriptorUtils.isPublicMethod(md) && md.getName().equals(this.methodName) && InvokeUtils.invokable(request, md)) {
                  if (this.methodTypes == null) {
                     this.methodTypes = new ArrayList();
                  }

                  this.methodTypes.add(new MethodTypeImpl(this.beanType, md));
               }
            }
         }

         return this.methodTypes;
      }
   }
}
