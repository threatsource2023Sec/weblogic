package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.instrumentation.ValueRenderer;
import weblogic.ejb.container.internal.MethodDescriptor;

public class EJBMethodDescriptorRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      if (inputObject != null && inputObject instanceof MethodDescriptor) {
         MethodDescriptor md = (MethodDescriptor)inputObject;
         return new EJBEventInfoImpl(md.getApplicationName(), md.getModuleId(), md.getEjbName(), md.getMethodName());
      } else {
         return null;
      }
   }
}
