package weblogic.descriptor.internal;

import java.util.Stack;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;

public class DescriptorFactory {
   private static final ThreadLocal THREAD_LOCAL = new ThreadLocal() {
      protected Object initialValue() {
         return new Stack();
      }
   };

   static Descriptor getThreadLocal() {
      Stack descStack = (Stack)THREAD_LOCAL.get();
      return descStack.empty() ? null : (Descriptor)descStack.peek();
   }

   public static Descriptor endConstruction(DescriptorBean root) {
      Stack descStack = (Stack)THREAD_LOCAL.get();
      DescriptorImpl desc = (DescriptorImpl)descStack.pop();
      desc.setModified(false);
      if (root != null) {
         desc.initializeRootBean(root);
      }

      return desc;
   }

   public static DescriptorBean createRootBean(Class implClass, String elementName) {
      return createBean(implClass, (DescriptorBean)null, elementName);
   }

   public static final DescriptorBean createBean(Class implClass, DescriptorBean parent, String elementName) {
      try {
         AbstractDescriptorBean bean = (AbstractDescriptorBean)implClass.newInstance();
         bean._setElementName(elementName);
         return (DescriptorBean)implClass.newInstance();
      } catch (IllegalAccessException var4) {
         throw new AssertionError(var4);
      } catch (InstantiationException var5) {
         throw new AssertionError(var5);
      }
   }
}
