package weblogic.ejb.container.injection;

import com.oracle.injection.BeanManager;
import com.oracle.injection.InjectionContainer;
import com.oracle.injection.InjectionDeployment;
import com.oracle.injection.InjectionException;
import com.oracle.pitchfork.interfaces.TargetWrapper;
import com.oracle.pitchfork.interfaces.intercept.InterceptionMetadataI;
import com.oracle.pitchfork.interfaces.intercept.InterceptorMetadataI;
import com.oracle.pitchfork.interfaces.intercept.__ProxyControl;
import com.oracle.pitchfork.spi.TargetWrapperImpl;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Timer;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.EjbComponentCreator;

public class InjectionBasedEjbComponentCreator implements EjbComponentCreator {
   private final InjectionContainer injectionContainer;
   private final EjbComponentCreatorImpl pitchforkEjbComponentCreator;
   private final String moduleId;
   private Map proxiesToCdiBeanInstances;

   public InjectionBasedEjbComponentCreator(InjectionContainer ic, String moduleId, EjbComponentCreatorImpl pitchforkECC) {
      this(ic, moduleId, pitchforkECC, new ProxyMapFactoryImpl());
   }

   protected InjectionBasedEjbComponentCreator(InjectionContainer ic, String moduleId, EjbComponentCreatorImpl pitchforkECC, ProxyMapFactory proxyMapFactory) {
      this.injectionContainer = ic;
      this.moduleId = moduleId;
      this.pitchforkEjbComponentCreator = pitchforkECC;
      this.proxiesToCdiBeanInstances = proxyMapFactory.createProxiesToCdiBeanInstancesMap();
   }

   public void initialize(DeploymentInfo dinfo, ClassLoader cl) {
      this.pitchforkEjbComponentCreator.initialize(dinfo, cl);
   }

   public Object getBean(String ejbName, Class beanClass, boolean createProxy) throws IllegalAccessException, InstantiationException {
      this.injectionContainer.getIntegrationService().setCurrentEjbName(ejbName);

      BeanManager beanManager;
      try {
         InjectionDeployment injectionDeployment = this.injectionContainer.getDeployment();
         if (injectionDeployment != null) {
            beanManager = injectionDeployment.getBeanManager(this.moduleId);
            Object instance;
            if (beanManager == null) {
               instance = null;
               return instance;
            }

            instance = beanManager.createEjb(ejbName);
            if (instance != null) {
               beanManager.injectEjbInstance(instance);
            }

            Object injectIntoMe;
            if (instance == null) {
               instance = this.pitchforkEjbComponentCreator.getBean(ejbName, beanClass, createProxy);
               injectIntoMe = instance;
               if (instance instanceof __ProxyControl) {
                  TargetWrapper tw = ((__ProxyControl)instance).__getTarget();
                  if (tw != null && tw.getBeanTarget() != null) {
                     injectIntoMe = tw.getBeanTarget();
                  }
               }

               try {
                  beanManager.injectOnExternalInstance(injectIntoMe);
               } catch (InjectionException var16) {
                  throw new InstantiationException(var16.getMessage());
               }

               Object var20 = instance;
               return var20;
            }

            if (!createProxy) {
               injectIntoMe = instance;
               return injectIntoMe;
            }

            List cdiBeanInstances = new ArrayList();
            cdiBeanInstances.add(instance);
            Map instantiatedInstances = new HashMap();

            InterceptorMetadataI im;
            Object interceptorInstance;
            for(Iterator var9 = this.getInterceptorMetadatas(ejbName).iterator(); var9.hasNext(); im.setExternallyCreatedInterceptor(instance, interceptorInstance)) {
               im = (InterceptorMetadataI)var9.next();
               Class interceptorClass = im.getComponentClass();
               interceptorInstance = instantiatedInstances.get(interceptorClass);
               if (interceptorInstance == null) {
                  interceptorInstance = beanManager.newInterceptorInstance(interceptorClass);
                  cdiBeanInstances.add(interceptorInstance);
                  if (!im.isDefaultInterceptor()) {
                     instantiatedInstances.put(interceptorClass, interceptorInstance);
                  }
               }
            }

            TargetWrapperImpl targetWrapper = new TargetWrapperImpl(instance);
            Object proxy = this.pitchforkEjbComponentCreator.assembleEJB3Proxy(targetWrapper, ejbName);
            this.proxiesToCdiBeanInstances.put(proxy, cdiBeanInstances);
            Object var23 = proxy;
            return var23;
         }

         beanManager = null;
      } finally {
         this.injectionContainer.getIntegrationService().setCurrentEjbName((String)null);
      }

      return beanManager;
   }

   private List getInterceptorMetadatas(String ejbName) {
      InterceptionMetadataI im = (InterceptionMetadataI)this.pitchforkEjbComponentCreator.getComponentContributor().getMetadata(ejbName);
      return im.getInterceptorMetadata();
   }

   private void doDestroyBean(Object ejb) {
      if (this.injectionContainer.getDeployment() != null) {
         BeanManager beanManager = this.injectionContainer.getDeployment().getBeanManager(this.moduleId);
         if (beanManager != null) {
            List beans = (List)this.proxiesToCdiBeanInstances.remove(ejb);
            if (beans != null) {
               Iterator var4 = beans.iterator();

               while(var4.hasNext()) {
                  Object bean = var4.next();
                  beanManager.destroyBean(bean);
               }
            }
         }
      }

   }

   public void destroyBean(Object proxy) {
      this.doDestroyBean(proxy);
   }

   public void invokePostConstruct(Object bean, String ejbName) {
      this.pitchforkEjbComponentCreator.invokePostConstruct(bean, ejbName);
   }

   public void invokePreDestroy(Object bean, String ejbName) {
      this.pitchforkEjbComponentCreator.invokePreDestroy(bean, ejbName);
      this.doDestroyBean(bean);
   }

   public void invokePostActivate(Object bean, String ejbName) {
      this.pitchforkEjbComponentCreator.invokePostActivate(bean, ejbName);
   }

   public void invokePrePassivate(Object bean, String ejbName) {
      this.pitchforkEjbComponentCreator.invokePrePassivate(bean, ejbName);
   }

   public void invokeTimer(Object bean, Method timeoutMethod, Timer timer, String ejbName) {
      this.pitchforkEjbComponentCreator.invokeTimer(bean, timeoutMethod, timer, ejbName);
   }

   public Object assembleEJB3Proxy(Object bean, String ejbName) {
      return this.pitchforkEjbComponentCreator.assembleEJB3Proxy(bean, ejbName);
   }

   public EjbComponentCreator getDelegateComponentCreator() {
      return this.pitchforkEjbComponentCreator;
   }
}
