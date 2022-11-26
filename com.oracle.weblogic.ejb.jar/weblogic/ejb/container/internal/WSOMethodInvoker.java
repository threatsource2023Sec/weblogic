package weblogic.ejb.container.internal;

import weblogic.ejb.container.interfaces.Invokable;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;

public final class WSOMethodInvoker {
   private WSOMethodInvoker() {
   }

   public static Object invoke(BaseWSLocalObject lo, MethodDescriptor md, Object[] args, int idx) throws Throwable {
      Object result = null;
      boolean doTxRetry = false;

      do {
         lo.__WL_business(md);
         WLEnterpriseBean bean = (WLEnterpriseBean)lo.__WL_getWrap().getBean();
         int oldState = bean.__WL_getMethodState();
         InvocationContextStack.push(lo.__WL_getWrap());
         EJBContextManager.pushEjbContext(bean.__WL_getEJBContext());
         bean.__WL_setMethodState(131072);

         try {
            result = ((Invokable)lo).__WL_invoke(bean, args, idx);
            lo.__WL_business_success();
         } catch (Throwable var14) {
            lo.__WL_business_fail(var14);
            lo.__WL_setException(var14);
            if (result == null) {
               result = getDefault(md.getMethod().getReturnType());
            }
         } finally {
            EJBContextManager.popEjbContext();
            InvocationContextStack.pop();
            bean.__WL_setMethodState(oldState);
         }

         try {
            doTxRetry = lo.__WL_postInvokeTxRetry();
         } catch (Throwable var13) {
            lo.__WL_setException(var13);
            throw lo.__WL_getException();
         }
      } while(doTxRetry);

      return result;
   }

   private static Object getDefault(Class c) {
      if (c.isPrimitive() && c != Void.TYPE) {
         if (c == Boolean.TYPE) {
            return Boolean.FALSE;
         } else if (c == Byte.TYPE) {
            return 0;
         } else if (c == Character.TYPE) {
            return '\u0000';
         } else if (c == Double.TYPE) {
            return 0.0;
         } else if (c == Float.TYPE) {
            return 0.0F;
         } else if (c == Integer.TYPE) {
            return 0;
         } else if (c == Long.TYPE) {
            return 0L;
         } else if (c == Short.TYPE) {
            return Short.valueOf((short)0);
         } else {
            throw new AssertionError("Unknown primitive type : " + c);
         }
      } else {
         return null;
      }
   }
}
