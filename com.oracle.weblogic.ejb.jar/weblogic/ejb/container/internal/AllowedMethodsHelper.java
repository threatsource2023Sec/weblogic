package weblogic.ejb.container.internal;

import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.FastThreadLocalMarker;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;
import weblogic.kernel.ThreadLocalStack;

@Service
public final class AllowedMethodsHelper implements FastThreadLocalMarker {
   private static final ThreadLocalStack threadStorage = new ThreadLocalStack(true);
   private static final ThreadLocalStack threadStorageForMethodInvocationState = new ThreadLocalStack(true);

   public static void pushBean(WLEnterpriseBean bean) {
      threadStorage.push(bean);
   }

   public static void popBean() {
      threadStorage.pop();
   }

   public static WLEnterpriseBean getBean() {
      return (WLEnterpriseBean)threadStorage.peek();
   }

   public static void pushMethodInvocationState(int state) {
      threadStorageForMethodInvocationState.push(state);
   }

   public static void popMethodInvocationState() {
      threadStorageForMethodInvocationState.pop();
   }

   public static Object getMethodInvocationState() {
      return threadStorageForMethodInvocationState.peek();
   }

   public String getFastThreadLocalClassName() {
      return this.getClass().getCanonicalName();
   }
}
