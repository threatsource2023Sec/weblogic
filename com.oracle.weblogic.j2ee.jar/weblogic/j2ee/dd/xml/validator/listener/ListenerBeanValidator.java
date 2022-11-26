package weblogic.j2ee.dd.xml.validator.listener;

import java.util.EventListener;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.dd.xml.validator.AnnotationValidator;
import weblogic.j2ee.descriptor.ListenerBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.management.DeploymentException;
import weblogic.servlet.WebLogicServletContextListener;
import weblogic.utils.ErrorCollectionException;

public class ListenerBeanValidator implements AnnotationValidator {
   private static final Class[] LISTENER_CLASSES = new Class[]{ServletContextListener.class, ServletContextAttributeListener.class, ServletRequestListener.class, ServletRequestAttributeListener.class, HttpSessionListener.class, HttpSessionIdListener.class, HttpSessionBindingListener.class, HttpSessionAttributeListener.class, HttpSessionActivationListener.class, WebLogicServletContextListener.class};

   public void validate(DescriptorBean bean, ClassLoader cl) throws ErrorCollectionException {
      if (bean instanceof WebAppBean) {
         ListenerBean[] listeners = ((WebAppBean)bean).getListeners();
         if (listeners != null && listeners.length >= 1) {
            ErrorCollectionException errors = new ErrorCollectionException();
            ListenerBean[] var5 = listeners;
            int var6 = listeners.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               ListenerBean listener = var5[var7];

               try {
                  Class listenerClass = cl.loadClass(listener.getListenerClass());
                  if (!isListener(listenerClass)) {
                     errors.add(new DeploymentException("User defined class " + listener.getListenerClass() + " is not a Listener, as it does not implement the correct interface(s)."));
                  }
               } catch (ClassNotFoundException var10) {
                  errors.add(var10);
               }
            }

            if (errors.size() != 0) {
               throw errors;
            }
         }
      }
   }

   private static boolean isListener(Class cls) {
      if (!EventListener.class.isAssignableFrom(cls)) {
         return false;
      } else {
         Class[] var1 = LISTENER_CLASSES;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Class listener = var1[var3];
            if (listener.isAssignableFrom(cls)) {
               return true;
            }
         }

         return false;
      }
   }
}
