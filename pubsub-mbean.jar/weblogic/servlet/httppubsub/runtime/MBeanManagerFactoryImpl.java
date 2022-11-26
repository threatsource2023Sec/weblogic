package weblogic.servlet.httppubsub.runtime;

import com.bea.httppubsub.descriptor.WeblogicPubsubBean;
import com.bea.httppubsub.runtime.MBeanManager;
import com.bea.httppubsub.runtime.MBeanManagerFactory;
import javax.servlet.ServletContext;
import weblogic.servlet.internal.WebAppServletContext;

public class MBeanManagerFactoryImpl implements MBeanManagerFactory {
   public MBeanManager createMBeanManager(WeblogicPubsubBean bean, ServletContext servletContext) {
      return new MBeanManagerImpl(bean, (WebAppServletContext)servletContext);
   }
}
