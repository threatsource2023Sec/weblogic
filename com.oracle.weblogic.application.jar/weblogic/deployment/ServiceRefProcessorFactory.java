package weblogic.deployment;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.jws.WebService;
import javax.servlet.ServletContext;
import javax.xml.ws.Service;
import weblogic.j2ee.descriptor.ServiceRefBean;
import weblogic.j2ee.descriptor.wl.ServiceReferenceDescriptionBean;

public class ServiceRefProcessorFactory {
   private static final ServiceRefProcessorFactory instance = new ServiceRefProcessorFactory();
   private static final String JAXRPC_PROCESSOR = "weblogic.wsee.jaxrpc.ServiceRefProcessorImpl";
   private static final String JAXWS_PROCESSOR = "weblogic.wsee.jaxws.ServiceRefProcessorImpl";

   private ServiceRefProcessorFactory() {
   }

   public static ServiceRefProcessorFactory getInstance() {
      return instance;
   }

   public ServiceRefProcessor getProcessor(ServiceRefBean ref, ServiceReferenceDescriptionBean wlref, ServletContext sc) throws ServiceRefProcessorException {
      String className = "weblogic.wsee.jaxrpc.ServiceRefProcessorImpl";
      String siName = ref.getServiceInterface();

      try {
         Class siClass = Thread.currentThread().getContextClassLoader().loadClass(siName);
         if (Service.class.isAssignableFrom(siClass) || siClass.isAnnotationPresent(WebService.class)) {
            className = "weblogic.wsee.jaxws.ServiceRefProcessorImpl";
         }
      } catch (ClassNotFoundException var7) {
         throw new ServiceRefProcessorException("Unable to load specified service-interface: " + siName, var7);
      }

      return this.constructProcessor(className, ref, wlref, sc);
   }

   private ServiceRefProcessor constructProcessor(String className, ServiceRefBean ref, ServiceReferenceDescriptionBean wlref, ServletContext sc) throws ServiceRefProcessorException {
      try {
         Class clazz = Class.forName(className);
         Constructor constructor = clazz.getDeclaredConstructor(ServiceRefBean.class, ServiceReferenceDescriptionBean.class, ServletContext.class);
         return (ServiceRefProcessor)constructor.newInstance(ref, wlref, sc);
      } catch (InvocationTargetException var7) {
         Throwable th = var7.getTargetException();
         throw new ServiceRefProcessorException(th.getMessage(), th);
      } catch (Exception var8) {
         throw new ServiceRefProcessorException("Error constructing ServiceRefProcessor: " + className, var8);
      }
   }
}
