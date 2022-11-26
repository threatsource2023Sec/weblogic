package weblogic.servlet.jsp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.el.ArrayELResolver;
import javax.el.BeanELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELContextEvent;
import javax.el.ELContextListener;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.FunctionMapper;
import javax.el.ListELResolver;
import javax.el.MapELResolver;
import javax.el.ResourceBundleELResolver;
import javax.el.StaticFieldELResolver;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.el.ImplicitObjectELResolver;
import javax.servlet.jsp.el.ScopedAttributeELResolver;

public class JspApplicationContextImpl implements JspApplicationContext {
   private ExpressionFactory exprFactory;
   private ServletContext context;
   private List listeners;
   private CompositeELResolver resolver;
   private CompositeELResolver appResolver;
   private boolean contextStarted;

   public JspApplicationContextImpl(ServletContext ctx, boolean el22BackwardCompatible) {
      this.exprFactory = JspConfig.getExpressionFactory(el22BackwardCompatible);
      this.context = ctx;
      this.listeners = new ArrayList();
      this.appResolver = new CompositeELResolver();
      this.resolver = new CompositeELResolver();
      this.initELResolver();
   }

   public void addELContextListener(ELContextListener listener) {
      if (listener != null) {
         this.listeners.add(listener);
      }

   }

   public void addELResolver(ELResolver resolver) {
      if (this.contextStarted) {
         throw new IllegalStateException("It is illegal to register an ELResolver after all ServletContextListeners have had their contextInitialized methods invoked.");
      } else {
         if (resolver != null) {
            this.appResolver.add(resolver);
         }

      }
   }

   public ExpressionFactory getExpressionFactory() {
      return this.exprFactory;
   }

   public void setContextStarted(boolean started) {
      this.contextStarted = started;
   }

   public ELContextImpl createELContext(JspContext ctx) {
      ELContextImpl context = new ELContextImpl(this.resolver, JspConfig.getVariableMapper(), (FunctionMapper)null);
      context.putContext(JspContext.class, ctx);
      context.putContext(ExpressionFactory.class, this.exprFactory);
      Iterator it = this.listeners.iterator();

      while(it.hasNext()) {
         ELContextListener listener = (ELContextListener)it.next();
         listener.contextCreated(new ELContextEvent(context));
      }

      return context;
   }

   private void initELResolver() {
      this.resolver.add(new ImplicitObjectELResolver());
      if (this.appResolver != null) {
         this.resolver.add(this.appResolver);
      }

      this.resolver.add(this.exprFactory.getStreamELResolver());
      this.resolver.add(new StaticFieldELResolver());
      this.resolver.add(new MapELResolver());
      this.resolver.add(new ResourceBundleELResolver());
      this.resolver.add(new ListELResolver());
      this.resolver.add(new ArrayELResolver());
      this.resolver.add(new BeanELResolver());
      this.resolver.add(new ScopedAttributeELResolver());
   }
}
