package weblogic.management.provider.internal;

import java.util.ArrayDeque;
import java.util.Deque;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.kernel.ThreadLocalInitialValue;
import weblogic.management.internal.SecurityHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class CallerPartitionContext {
   public static void updateContext(AuthenticatedSubject sub) {
      SecurityHelper.assertIfNotKernel(sub);
      Deque dq = (Deque)CallerPartitionContext.SingletonHolder.CONTEXT.get();
      dq.addFirst(ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName());
   }

   public static void updateContext(AuthenticatedSubject sub, String partitionName) {
      SecurityHelper.assertIfNotKernel(sub);
      Deque dq = (Deque)CallerPartitionContext.SingletonHolder.CONTEXT.get();
      if (partitionName == null) {
         partitionName = "DOMAIN";
      }

      dq.addFirst(partitionName);
   }

   public static void pollContext(AuthenticatedSubject sub) {
      SecurityHelper.assertIfNotKernel(sub);
      Deque dq = (Deque)CallerPartitionContext.SingletonHolder.CONTEXT.get();
      dq.pollFirst();
   }

   static String getPartitionName(AuthenticatedSubject sub) {
      SecurityHelper.assertIfNotKernel(sub);
      Deque dq = (Deque)CallerPartitionContext.SingletonHolder.CONTEXT.get();
      return (String)dq.peek();
   }

   private static final class SingletonHolder {
      private static final AuditableThreadLocal CONTEXT = AuditableThreadLocalFactory.createThreadLocal(new ThreadLocalInitialValue(true) {
         protected Object initialValue() {
            return new ArrayDeque(1);
         }

         protected Object childValue(Object parentValue) {
            return ((ArrayDeque)parentValue).clone();
         }
      });
   }
}
