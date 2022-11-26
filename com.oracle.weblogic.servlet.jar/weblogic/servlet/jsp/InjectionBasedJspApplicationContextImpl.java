package weblogic.servlet.jsp;

import com.oracle.injection.BeanManager;
import com.oracle.injection.InjectionContainer;
import com.oracle.injection.InjectionDeployment;
import java.util.Iterator;
import javax.el.ELContextListener;
import javax.el.ExpressionFactory;
import javax.servlet.ServletContext;
import weblogic.diagnostics.debug.DebugLogger;

public class InjectionBasedJspApplicationContextImpl extends JspApplicationContextImpl {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger(InjectionBasedJspApplicationContextImpl.class.getName());
   private final InjectionContainer m_injectionContainer;
   private final String m_moduleName;

   public InjectionBasedJspApplicationContextImpl(ServletContext servletContext, boolean el22BackwardCompatible, InjectionContainer injectionContainer, ClassLoader classLoader, String moduleName) {
      super(servletContext, el22BackwardCompatible);
      if (servletContext == null) {
         throw new IllegalArgumentException("ServletContext cannot be null");
      } else if (injectionContainer == null) {
         throw new IllegalArgumentException("InjectionContainer cannot be null");
      } else {
         this.m_injectionContainer = injectionContainer;
         this.m_moduleName = moduleName;
         InjectionDeployment injectionDeployment = this.m_injectionContainer.getDeployment();
         if (injectionDeployment != null) {
            BeanManager beanManager = injectionDeployment.getBeanManager(this.m_moduleName);
            if (beanManager != null) {
               this.addELResolver(beanManager.getELResolver());
            }
         } else {
            debugLogger.debug("Error in InjectionBasedJspApplicationContextImpl initialization.  InjectionDeployment is null.");
         }

         Iterator var13 = this.m_injectionContainer.getELContextListenerNames().iterator();

         while(var13.hasNext()) {
            String elContextListenerName = (String)var13.next();

            try {
               Class clazz = classLoader.loadClass(elContextListenerName);
               this.addELContextListener((ELContextListener)clazz.newInstance());
            } catch (ClassNotFoundException var10) {
               debugLogger.debug("Exception occurred while attempting to load an ELContextListener", var10);
            } catch (IllegalAccessException var11) {
               debugLogger.debug("Exception occurred while attempting to load an ELContextListener", var11);
            } catch (InstantiationException var12) {
               debugLogger.debug("Exception occurred while attempting to load an ELContextListener", var12);
            }
         }

      }
   }

   public ExpressionFactory getExpressionFactory() {
      ExpressionFactory expressionFactory = super.getExpressionFactory();
      InjectionDeployment injectionDeployment = this.m_injectionContainer.getDeployment();
      if (injectionDeployment != null) {
         BeanManager beanManager = injectionDeployment.getBeanManager(this.m_moduleName);
         if (beanManager != null) {
            return beanManager.getWrappedExpressionFactory(expressionFactory);
         }
      }

      return expressionFactory;
   }
}
