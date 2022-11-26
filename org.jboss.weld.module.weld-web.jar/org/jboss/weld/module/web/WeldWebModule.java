package org.jboss.weld.module.web;

import java.util.Set;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import org.jboss.weld.bootstrap.ContextHolder;
import org.jboss.weld.context.http.HttpConversationContext;
import org.jboss.weld.context.http.HttpLiteral;
import org.jboss.weld.context.http.HttpRequestContext;
import org.jboss.weld.context.http.HttpSessionContext;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.module.ExpressionLanguageSupport;
import org.jboss.weld.module.WeldModule;
import org.jboss.weld.module.web.context.http.HttpRequestContextImpl;
import org.jboss.weld.module.web.context.http.HttpSessionContextImpl;
import org.jboss.weld.module.web.context.http.HttpSessionDestructionContext;
import org.jboss.weld.module.web.context.http.LazyHttpConversationContextImpl;
import org.jboss.weld.module.web.el.WeldELResolver;
import org.jboss.weld.module.web.el.WeldExpressionFactory;
import org.jboss.weld.module.web.servlet.ServletApiAbstraction;
import org.jboss.weld.module.web.servlet.ServletContextService;
import org.jboss.weld.resources.WeldClassLoaderResourceLoader;
import org.jboss.weld.serialization.BeanIdentifierIndex;
import org.jboss.weld.util.Bindings;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public class WeldWebModule implements WeldModule {
   public static final ExpressionLanguageSupport EL_SUPPORT = new ExpressionLanguageSupport() {
      public void cleanup() {
      }

      public ExpressionFactory wrapExpressionFactory(ExpressionFactory expressionFactory) {
         return new WeldExpressionFactory(expressionFactory);
      }

      public ELResolver createElResolver(BeanManagerImpl manager) {
         return new WeldELResolver(manager);
      }
   };

   public String getName() {
      return "weld-web";
   }

   public void postServiceRegistration(WeldModule.PostServiceRegistrationContext ctx) {
      ctx.getServices().add(ExpressionLanguageSupport.class, EL_SUPPORT);
      ctx.getServices().add(ServletContextService.class, new ServletContextService());
      ctx.getServices().add(ServletApiAbstraction.class, new ServletApiAbstraction(WeldClassLoaderResourceLoader.INSTANCE));
   }

   public void postContextRegistration(WeldModule.PostContextRegistrationContext ctx) {
      BeanIdentifierIndex index = (BeanIdentifierIndex)ctx.getServices().get(BeanIdentifierIndex.class);
      String contextId = ctx.getContextId();
      if (Reflections.isClassLoadable("javax.servlet.ServletContext", WeldClassLoaderResourceLoader.INSTANCE)) {
         Set httpQualifiers = ImmutableSet.builder().addAll(Bindings.DEFAULT_QUALIFIERS).add(HttpLiteral.INSTANCE).build();
         ctx.addContext(new ContextHolder(new HttpSessionContextImpl(contextId, index), HttpSessionContext.class, httpQualifiers));
         ctx.addContext(new ContextHolder(new HttpSessionDestructionContext(contextId, index), HttpSessionDestructionContext.class, httpQualifiers));
         ctx.addContext(new ContextHolder(new LazyHttpConversationContextImpl(contextId, ctx.getServices()), HttpConversationContext.class, httpQualifiers));
         ctx.addContext(new ContextHolder(new HttpRequestContextImpl(contextId), HttpRequestContext.class, httpQualifiers));
      }

   }

   public void preBeanRegistration(WeldModule.PreBeanRegistrationContext ctx) {
      if (Reflections.isClassLoadable("javax.servlet.ServletContext", WeldClassLoaderResourceLoader.INSTANCE)) {
         ctx.registerBean(new HttpServletRequestBean(ctx.getBeanManager()));
         ctx.registerBean(new HttpSessionBean(ctx.getBeanManager()));
         ctx.registerBean(new ServletContextBean(ctx.getBeanManager()));
      }

   }
}
