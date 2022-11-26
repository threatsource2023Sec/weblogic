package weblogic.rmi.provider;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.workarea.NoWorkContextException;
import weblogic.workarea.PropertyReadOnlyException;
import weblogic.workarea.WorkContext;
import weblogic.workarea.WorkContextMap;

public final class PriviledgedWorkContextMap implements WorkContextMap {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String[] PRIVILEDGED_KEYS = new String[]{"oracle.dms.context.internal.wls.WLSExecutionContext", "oracle.dms.context.internal.wls.WLSContextFamily"};
   private final WorkContextMap delegate;

   public PriviledgedWorkContextMap(WorkContextMap delegate) {
      this.delegate = delegate;
   }

   public WorkContext put(final String key, final WorkContext ctx) throws PropertyReadOnlyException {
      if (this.isPriviledgedKey(key)) {
         Object obj = SecurityManager.runAs(kernelId, kernelId, new PrivilegedAction() {
            public Object run() {
               WorkContext context = null;

               try {
                  context = PriviledgedWorkContextMap.this.delegate.put(key, ctx);
               } catch (PropertyReadOnlyException var3) {
               }

               return context;
            }
         });
         return (WorkContext)obj;
      } else {
         return this.delegate.put(key, ctx);
      }
   }

   public WorkContext put(final String key, final WorkContext ctx, final int propagationMode) throws PropertyReadOnlyException {
      if (this.isPriviledgedKey(key)) {
         Object obj = SecurityManager.runAs(kernelId, kernelId, new PrivilegedAction() {
            public Object run() {
               WorkContext context = null;

               try {
                  context = PriviledgedWorkContextMap.this.delegate.put(key, ctx, propagationMode);
               } catch (PropertyReadOnlyException var3) {
               }

               return context;
            }
         });
         return (WorkContext)obj;
      } else {
         return this.delegate.put(key, ctx, propagationMode);
      }
   }

   public WorkContext get(final String key) {
      if (this.isPriviledgedKey(key)) {
         Object obj = SecurityManager.runAs(kernelId, kernelId, new PrivilegedAction() {
            public Object run() {
               return PriviledgedWorkContextMap.this.delegate.get(key);
            }
         });
         return (WorkContext)obj;
      } else {
         return this.delegate.get(key);
      }
   }

   private boolean isPriviledgedKey(String key) {
      int length = PRIVILEDGED_KEYS.length;

      for(int i = 0; i < length; ++i) {
         if (key.equals(PRIVILEDGED_KEYS[i])) {
            return true;
         }
      }

      return false;
   }

   public int getPropagationMode(String key) {
      return this.delegate.getPropagationMode(key);
   }

   public boolean isPropagationModePresent(int propMode) {
      return this.delegate.isPropagationModePresent(propMode);
   }

   public WorkContext remove(String key) throws NoWorkContextException, PropertyReadOnlyException {
      return this.delegate.remove(key);
   }

   public boolean isEmpty() {
      return this.delegate.isEmpty();
   }

   public Iterator iterator() {
      return this.delegate.iterator();
   }

   public Iterator keys() {
      return this.delegate.keys();
   }
}
