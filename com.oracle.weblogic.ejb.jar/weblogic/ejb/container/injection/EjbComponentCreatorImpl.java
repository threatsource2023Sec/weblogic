package weblogic.ejb.container.injection;

import com.oracle.pitchfork.interfaces.EjbComponentCreatorBroker;
import com.oracle.pitchfork.interfaces.inject.ComponentContributor;
import com.oracle.pitchfork.interfaces.inject.LifecycleEvent;
import com.oracle.pitchfork.interfaces.intercept.InterceptionMetadataI;
import java.lang.reflect.Method;
import javax.ejb.Timer;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.interfaces.DeploymentInfo;
import weblogic.ejb.container.interfaces.EjbComponentCreator;
import weblogic.j2ee.injection.PitchforkContext;

public class EjbComponentCreatorImpl implements EjbComponentCreator {
   private static final DebugLogger debugLogger;
   private static final String SPRING_EJB_JAR_XML_LOCATION = "/META-INF/spring-ejb-jar.xml";
   private final PitchforkContext pc;
   private final EjbComponentCreatorBroker ejbComponentCreatorBroker;
   private EjbComponentContributor ejbComponentContributor;

   public EjbComponentCreatorImpl(PitchforkContext pc, DeploymentInfo di, ClassLoader cl) {
      this(pc, new EjbComponentContributor(di, cl, pc));
   }

   public EjbComponentCreatorImpl(PitchforkContext pc, EjbComponentContributor ecc) {
      this.pc = pc;
      this.ejbComponentCreatorBroker = pc.getPitchforkUtils().createEjbComponentCreatorBroker();
      this.ejbComponentContributor = ecc;
   }

   public void initialize(DeploymentInfo dinfo, ClassLoader cl) {
      this.ejbComponentCreatorBroker.initialize(cl, "/META-INF/spring-ejb-jar.xml", PitchforkContext.getSynthesizedComponentFactoryClassName(this.pc.getComponentFactoryClassName()), this.pc.isSpringComponentFactoryClassName(), this.ejbComponentContributor);
   }

   public Object getBean(String ejbName, Class beanClass, boolean createProxy) throws IllegalAccessException, InstantiationException {
      return this.ejbComponentCreatorBroker.getBean(ejbName, beanClass, createProxy);
   }

   public void invokePostConstruct(Object bean, String compName) {
      this.getMetadata(compName).invokeLifecycleMethods(bean, LifecycleEvent.POST_CONSTRUCT);
   }

   public void invokePreDestroy(Object bean, String compName) {
      this.getMetadata(compName).invokeLifecycleMethods(bean, LifecycleEvent.PRE_DESTROY);
   }

   public void invokePostActivate(Object bean, String compName) {
      this.getMetadata(compName).invokeLifecycleMethods(bean, LifecycleEvent.POST_ACTIVATE);
   }

   public void invokePrePassivate(Object bean, String compName) {
      this.getMetadata(compName).invokeLifecycleMethods(bean, LifecycleEvent.PRE_PASSIVATE);
   }

   public void invokeTimer(Object bean, Method timeoutMethod, Timer timer, String compName) {
      this.getMetadata(compName).invokeTimeoutMethod(bean, timeoutMethod, timer);
   }

   private InterceptionMetadataI getMetadata(String compName) {
      return this.ejbComponentContributor.getMetadata(compName);
   }

   public Object assembleEJB3Proxy(Object bean, String ejbName) {
      return this.ejbComponentCreatorBroker.assembleEJB3Proxy(bean, ejbName);
   }

   public void destroyBean(Object bean) {
   }

   protected ComponentContributor getComponentContributor() {
      return this.ejbComponentContributor;
   }

   protected void debug(String s) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("[EjbComponentCreatorImpl] " + s);
      }

   }

   static {
      debugLogger = EJBDebugService.deploymentLogger;
   }
}
