package org.jboss.weld.module.ejb;

import org.jboss.weld.bootstrap.ContextHolder;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.context.ejb.EjbLiteral;
import org.jboss.weld.context.ejb.EjbRequestContext;
import org.jboss.weld.ejb.spi.EjbServices;
import org.jboss.weld.injection.ResourceInjectionFactory;
import org.jboss.weld.module.EjbSupport;
import org.jboss.weld.module.WeldModule;
import org.jboss.weld.module.ejb.context.EjbRequestContextImpl;
import org.jboss.weld.util.Bindings;
import org.jboss.weld.util.collections.ImmutableSet;

public class WeldEjbModule implements WeldModule {
   public String getName() {
      return "weld-ejb";
   }

   public void postServiceRegistration(WeldModule.PostServiceRegistrationContext ctx) {
      ctx.getServices().add(CurrentInvocationInjectionPoint.class, new CurrentInvocationInjectionPoint());
      ctx.registerPlugableValidator(new WeldEjbValidator());
      ((ResourceInjectionFactory)ctx.getServices().get(ResourceInjectionFactory.class)).addResourceInjectionProcessor(new EjbResourceInjectionProcessor());
   }

   public void postContextRegistration(WeldModule.PostContextRegistrationContext ctx) {
      ctx.addContext(new ContextHolder(new EjbRequestContextImpl(ctx.getContextId()), EjbRequestContext.class, ImmutableSet.builder().addAll(Bindings.DEFAULT_QUALIFIERS).add(EjbLiteral.INSTANCE).build()));
   }

   public void postBeanArchiveServiceRegistration(WeldModule.PostBeanArchiveServiceRegistrationContext ctx) {
      ServiceRegistry services = ctx.getServices();
      EjbServices ejbServices = (EjbServices)services.get(EjbServices.class);
      if (ejbServices != null) {
         services.add(EjbSupport.class, new EjbSupportImpl(ejbServices, ctx.getBeanDeploymentArchive().getEjbs()));
      }

   }

   public void preBeanRegistration(WeldModule.PreBeanRegistrationContext ctx) {
      ctx.registerBean(new SessionBeanAwareInjectionPointBean(ctx.getBeanManager()));
   }
}
