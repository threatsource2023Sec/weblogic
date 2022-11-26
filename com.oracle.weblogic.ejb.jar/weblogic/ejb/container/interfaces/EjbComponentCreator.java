package weblogic.ejb.container.interfaces;

import java.lang.reflect.Method;
import javax.ejb.Timer;

public interface EjbComponentCreator {
   void initialize(DeploymentInfo var1, ClassLoader var2);

   Object getBean(String var1, Class var2, boolean var3) throws IllegalAccessException, InstantiationException;

   void invokePostConstruct(Object var1, String var2);

   void invokePreDestroy(Object var1, String var2);

   void invokePostActivate(Object var1, String var2);

   void invokePrePassivate(Object var1, String var2);

   void invokeTimer(Object var1, Method var2, Timer var3, String var4);

   Object assembleEJB3Proxy(Object var1, String var2);

   void destroyBean(Object var1);
}
