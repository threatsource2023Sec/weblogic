package weblogic.management.rest.lib.bean.utils;

import java.beans.MethodDescriptor;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;

class MethodReference {
   private BeanType beanType;
   private String methodName;
   private Class[] signature;
   private MethodType methodType;
   private boolean initializedMethodType = false;

   MethodReference(BeanType beanType, String methodName, Class... signature) {
      this.beanType = beanType;
      this.methodName = methodName;
      this.signature = signature;
   }

   MethodType getMethodType(HttpServletRequest request) throws Exception {
      if (this.methodName == null) {
         return null;
      } else {
         if (!this.initializedMethodType) {
            boolean ambiguous = false;
            MethodDescriptor[] var3 = this.beanType.getBeanInfo().getMethodDescriptors();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               MethodDescriptor md = var3[var5];
               if (DescriptorUtils.isPublicMethod(md) && md.getName().equals(this.methodName) && InvokeUtils.invokable(request, md) && this.matchesSignature(md)) {
                  if (this.methodType == null) {
                     this.methodType = new MethodTypeImpl(this.beanType, md);
                  } else {
                     if (this.signature == null) {
                        this.beanType.addMethodProblem(md, "multiple methods named " + md.getMethod().getName());
                     } else {
                        this.beanType.addMethodProblem(md, "multiple matching methods for signature " + Arrays.toString(this.signature));
                     }

                     ambiguous = true;
                  }
               }
            }

            if (ambiguous) {
               this.methodType = null;
            }

            if (this.methodType == null) {
            }

            this.initializedMethodType = true;
         }

         return this.methodType;
      }
   }

   private boolean matchesSignature(MethodDescriptor md) {
      if (this.signature == null) {
         return true;
      } else {
         Class[] sig = md.getMethod().getParameterTypes();
         int sigLength = sig != null ? sig.length : 0;
         if (this.signature.length != sigLength) {
            return false;
         } else {
            for(int i = 0; i < sigLength; ++i) {
               Class want = this.signature[i];
               Class have = sig[i];
               if (!want.equals(have)) {
                  return false;
               }
            }

            return true;
         }
      }
   }
}
