package weblogic.sca.binding.ejb.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.rmi.RemoteException;
import javax.ejb.EJBException;

abstract class BaseServiceHandler implements InvocationHandler {
   protected abstract Object invoke(Method var1, Object[] var2) throws EjbServiceException, RemoteException;

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (method.getDeclaringClass().equals(Object.class)) {
         if (method.getName().equals("equals")) {
            if (args.length != 1) {
               throw new AssertionError("Equals method has multiple args!");
            }

            if (args[0] == null) {
               return false;
            }

            if (!Proxy.isProxyClass(args[0].getClass())) {
               return false;
            }

            return this.equals(Proxy.getInvocationHandler(args[0]));
         }

         if (method.getName().equals("hashCode")) {
            return this.hashCode();
         }

         if (method.getName().equals("toString")) {
            return this.toString();
         }
      }

      try {
         return this.invoke(method, args);
      } catch (EjbServiceException var6) {
         EjbServiceException ese;
         for(ese = var6; ese.getCause() instanceof EjbServiceException; ese = (EjbServiceException)ese.getCause()) {
         }

         Throwable cause = ese.getCause();
         if (cause == null) {
            throw new EJBException(ese.getMessage());
         } else {
            throw cause;
         }
      } catch (RemoteException var7) {
         throw new EJBException(var7);
      }
   }
}
