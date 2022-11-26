package weblogic.management.provider.internal;

import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import javax.inject.Named;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.kernel.ThreadLocalInitialValue;
import weblogic.management.EditSessionTool;
import weblogic.management.internal.SecurityHelper;
import weblogic.management.provider.EditAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;

@Service
@Named
@Singleton
public class EditSessionToolService implements EditSessionTool {
   private static final AuditableThreadLocal EA_CONTEXT = AuditableThreadLocalFactory.createThreadLocal(new ThreadLocalInitialValue(true) {
      protected Object initialValue() {
         return new ArrayDeque(1);
      }

      protected Object childValue(Object parentValue) {
         return ((ArrayDeque)parentValue).clone();
      }
   });

   public void pushEditContext(AuthenticatedSubject sub, EditAccess ea) {
      SecurityHelper.assertIfNotKernel(sub);
      ArrayDeque dq = (ArrayDeque)EA_CONTEXT.get();
      if (ea == null) {
         dq.addFirst((Object)null);
      } else {
         dq.addFirst(new WeakReference(ea));
      }

   }

   public void popEditContext(AuthenticatedSubject sub) {
      SecurityHelper.assertIfNotKernel(sub);
      ArrayDeque dq = (ArrayDeque)EA_CONTEXT.get();
      dq.removeFirst();
   }

   public EditAccess getEditContext() {
      ArrayDeque dq = (ArrayDeque)EA_CONTEXT.get();
      WeakReference result = (WeakReference)dq.peekFirst();
      return result == null ? null : (EditAccess)result.get();
   }
}
