package weblogic.security.securityapi;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import org.glassfish.soteria.SecurityContextImpl;
import org.glassfish.soteria.cdi.CdiUtils;

public class WlsCdiExtension implements Extension {
   public void register(@Observes BeforeBeanDiscovery beforeBean, BeanManager beanManager) {
      CdiUtils.addAnnotatedTypes(beforeBean, beanManager, new Class[]{WlsSecurityContextImpl.class});
   }

   void disableSoteriaSC(@Observes ProcessAnnotatedType pat) {
      if (SecurityContextImpl.class.equals(pat.getAnnotatedType().getJavaClass())) {
         pat.veto();
      }

   }
}
