package weblogic.spring.monitoring.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.instrumentation.DiagnosticActionState;
import weblogic.diagnostics.instrumentation.DynamicJoinPointImpl;
import weblogic.diagnostics.instrumentation.JoinPoint;

public class ApplicationContextObtainFreshBeanFactoryAction extends BaseElapsedTimeAction {
   private static final long serialVersionUID = 1L;
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugSpringStatistics");

   public ApplicationContextObtainFreshBeanFactoryAction() {
      super("SpringApplicationContextObtainFreshBeanFactoryAction");
   }

   public void postProcess(JoinPoint jp, DiagnosticActionState actionState) {
      super.postProcess(jp, actionState);
      if (jp instanceof DynamicJoinPointImpl) {
         DynamicJoinPointImpl djp = (DynamicJoinPointImpl)jp;

         try {
            Class applicationContextClass = Thread.currentThread().getContextClassLoader().loadClass("org.springframework.context.ApplicationContext");
            Class beanFactoryClass = Thread.currentThread().getContextClassLoader().loadClass("org.springframework.beans.factory.BeanFactory");
            Class clz = Thread.currentThread().getContextClassLoader().loadClass("weblogic.spring.beans.SpringServerApplicationContextUtils");
            Method method = clz.getMethod("setParentBeanFactoryIfNecessary", applicationContextClass, beanFactoryClass);
            method.invoke((Object)null, ((ElapsedTimeActionState)actionState).getSpringBean(), djp.getReturnValue());
         } catch (Exception var8) {
         }

      }
   }

   protected void updateRuntimeMBean(DiagnosticActionState actionState) {
      ElapsedTimeActionState state = (ElapsedTimeActionState)actionState;
      this.updateBeanFactory(state.getSpringBean());
   }

   private void updateBeanFactory(Object bean) {
      if (bean != null) {
         Method updateBeanFactoryMethod;
         try {
            Class springRuntimeStatisticsMBeanManagerClass = Class.forName("weblogic.spring.monitoring.SpringRuntimeStatisticsMBeanManager", true, bean.getClass().getClassLoader());
            updateBeanFactoryMethod = springRuntimeStatisticsMBeanManagerClass.getDeclaredMethod("updateBeanFactory", Object.class);
         } catch (ClassNotFoundException var4) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("ApplicationContextObtainFreshBeanFactoryAction.updateBeanFactory failed to find class", var4);
            }

            return;
         } catch (SecurityException var5) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("ApplicationContextObtainFreshBeanFactoryAction.updateBeanFactory failed to find method", var5);
            }

            return;
         } catch (NoSuchMethodException var6) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("ApplicationContextObtainFreshBeanFactoryAction.updateBeanFactory failed to find method", var6);
            }

            return;
         }

         try {
            updateBeanFactoryMethod.invoke((Object)null, bean);
         } catch (IllegalArgumentException var7) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("ApplicationContextObtainFreshBeanFactoryAction.updateBeanFactory failed to invoke", var7);
            }
         } catch (IllegalAccessException var8) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("ApplicationContextObtainFreshBeanFactoryAction.updateBeanFactory failed to invoke", var8);
            }
         } catch (InvocationTargetException var9) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("ApplicationContextObtainFreshBeanFactoryAction.updateBeanFactory failed to invoke", var9);
            }
         }

      }
   }
}
