package weblogic.persistence;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import javax.persistence.EntityManagerFactory;

public final class EntityManagerFactoryProxyImpl implements InterceptingInvocationHandler {
   private final EntityManagerFactory emf;
   private final Method closeMethod;
   private final Method equalsMethod;
   private final Method openMethod;
   private final String unitName;
   private String appName;
   private String moduleName;
   private InvocationHandlerInterceptor iceptor;
   private volatile boolean isClosed;

   public EntityManagerFactoryProxyImpl(EntityManagerFactory emf, String unitName) {
      this.emf = emf;
      this.unitName = unitName;

      try {
         this.closeMethod = EntityManagerFactory.class.getMethod("close", (Class[])null);
         this.equalsMethod = Object.class.getMethod("equals", Object.class);
         this.openMethod = EntityManagerFactory.class.getMethod("isOpen", (Class[])null);
      } catch (NoSuchMethodException var4) {
         throw new AssertionError("Couldn't get expected method: " + var4);
      }
   }

   public void setInterceptor(InvocationHandlerInterceptor ihi) {
      this.iceptor = ihi;
   }

   EntityManagerFactory getEntityManagerFactory() {
      return this.emf;
   }

   public void setAppName(String appName) {
      this.appName = appName;
   }

   public String getAppName() {
      return this.appName;
   }

   public void setModuleName(String moduleName) {
      this.moduleName = moduleName;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   public String getUnitName() {
      return this.unitName;
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (this.iceptor != null) {
         this.iceptor.preInvoke(method, args);
      }

      if (this.isClosed) {
         if (method.equals(this.openMethod)) {
            return false;
         } else {
            throw new IllegalStateException("The EntityManagerFactory has been closed");
         }
      } else if (method.equals(this.closeMethod)) {
         this.isClosed = true;
         return null;
      } else {
         EntityManagerFactoryProxyImpl ih;
         if (method.equals(this.equalsMethod) && args.length == 1 && args[0] instanceof Proxy) {
            try {
               ih = (EntityManagerFactoryProxyImpl)Proxy.getInvocationHandler(args[0]);
               args = new Object[]{ih.getEntityManagerFactory()};
            } catch (ClassCastException var7) {
            }
         }

         ih = null;

         Object result;
         try {
            result = method.invoke(this.emf, args);
         } catch (InvocationTargetException var6) {
            throw var6.getCause();
         }

         return this.iceptor != null ? this.iceptor.postInvoke(method, args, result) : result;
      }
   }
}
