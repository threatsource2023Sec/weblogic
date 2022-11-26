package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.instrumentation.ValueRenderer;
import weblogic.ejb.container.internal.InvocationWrapper;
import weblogic.ejb.container.internal.MethodDescriptor;

public class EJBInvocationWrapperRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      if (inputObject != null && inputObject instanceof InvocationWrapper) {
         InvocationWrapper wrapper = (InvocationWrapper)inputObject;
         MethodDescriptor md = wrapper.getMethodDescriptor();
         return md == null ? null : new EJBEventInfoImpl(md.getApplicationName(), md.getModuleId(), md.getEjbName(), md.getMethodName());
      } else {
         return null;
      }
   }
}
