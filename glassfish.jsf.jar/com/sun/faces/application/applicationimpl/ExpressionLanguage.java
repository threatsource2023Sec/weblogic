package com.sun.faces.application.applicationimpl;

import com.sun.faces.RIConstants;
import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.application.MethodBindingMethodExpressionAdapter;
import com.sun.faces.application.ValueBindingValueExpressionAdapter;
import com.sun.faces.el.ELUtils;
import com.sun.faces.el.FacesCompositeELResolver;
import com.sun.faces.el.PropertyResolverImpl;
import com.sun.faces.el.VariableResolverImpl;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.CompositeELResolver;
import javax.el.ELContextListener;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.PropertyResolver;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.ValueBinding;
import javax.faces.el.VariableResolver;

public class ExpressionLanguage {
   private static final Logger LOGGER;
   private static final ELContextListener[] EMPTY_EL_CTX_LIST_ARRAY;
   private final ApplicationAssociate associate;
   private volatile PropertyResolverImpl propertyResolver;
   private volatile VariableResolverImpl variableResolver;
   private List elContextListeners;
   private CompositeELResolver elResolvers;
   private volatile FacesCompositeELResolver compositeELResolver;
   private Version version = new Version();

   public ExpressionLanguage(ApplicationAssociate applicationAssociate) {
      this.associate = applicationAssociate;
      this.propertyResolver = new PropertyResolverImpl();
      this.variableResolver = new VariableResolverImpl();
      this.elContextListeners = new CopyOnWriteArrayList();
      this.elResolvers = new CompositeELResolver();
   }

   public void addELContextListener(ELContextListener listener) {
      if (listener != null) {
         this.elContextListeners.add(listener);
      }

   }

   public void removeELContextListener(ELContextListener listener) {
      if (listener != null) {
         this.elContextListeners.remove(listener);
      }

   }

   public ELContextListener[] getELContextListeners() {
      return !this.elContextListeners.isEmpty() ? (ELContextListener[])this.elContextListeners.toArray(new ELContextListener[this.elContextListeners.size()]) : EMPTY_EL_CTX_LIST_ARRAY;
   }

   public ELResolver getELResolver() {
      if (this.compositeELResolver == null) {
         synchronized(this) {
            if (this.compositeELResolver == null) {
               this.performOneTimeELInitialization();
            }
         }
      }

      return this.compositeELResolver;
   }

   public void addELResolver(ELResolver resolver) {
      if (this.associate.hasRequestBeenServiced()) {
         throw new IllegalStateException(MessageUtils.getExceptionMessageString("com.sun.faces.ILLEGAL_ATTEMPT_SETTING_APPLICATION_ARTIFACT", "ELResolver"));
      } else {
         FacesContext facesContext = FacesContext.getCurrentInstance();
         if (this.version.isJsf23()) {
            BeanManager cdiBeanManager = Util.getCdiBeanManager(facesContext);
            if (cdiBeanManager != null && !resolver.equals(cdiBeanManager.getELResolver())) {
               this.elResolvers.add(resolver);
            }
         } else {
            this.elResolvers.add(resolver);
         }

      }
   }

   public ExpressionFactory getExpressionFactory() {
      return this.associate.getExpressionFactory();
   }

   public Object evaluateExpressionGet(FacesContext context, String expression, Class expectedType) throws ELException {
      return this.getExpressionFactory().createValueExpression(context.getELContext(), expression, expectedType).getValue(context.getELContext());
   }

   public CompositeELResolver getApplicationELResolvers() {
      return this.elResolvers;
   }

   public FacesCompositeELResolver getCompositeELResolver() {
      return this.compositeELResolver;
   }

   public void setCompositeELResolver(FacesCompositeELResolver compositeELResolver) {
      this.compositeELResolver = compositeELResolver;
   }

   private void performOneTimeELInitialization() {
      if (this.compositeELResolver != null) {
         throw new IllegalStateException("Class invariant invalidated: The Application instance's ELResolver is not null and it should be.");
      } else {
         this.associate.initializeELResolverChains();
      }
   }

   /** @deprecated */
   @Deprecated
   public PropertyResolver getPropertyResolver() {
      if (this.compositeELResolver == null) {
         this.performOneTimeELInitialization();
      }

      return this.propertyResolver;
   }

   /** @deprecated */
   @Deprecated
   public void setPropertyResolver(PropertyResolver resolver) {
      if (this.associate.hasRequestBeenServiced()) {
         throw new IllegalStateException(MessageUtils.getExceptionMessageString("com.sun.faces.ILLEGAL_ATTEMPT_SETTING_APPLICATION_ARTIFACT", "PropertyResolver"));
      } else if (resolver == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "resolver");
         throw new NullPointerException(message);
      } else {
         this.propertyResolver.setDelegate(ELUtils.getDelegatePR(this.associate, true));
         this.associate.setLegacyPropertyResolver(resolver);
         this.propertyResolver = new PropertyResolverImpl();
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine(MessageFormat.format("set PropertyResolver Instance to ''{0}''", resolver.getClass().getName()));
         }

      }
   }

   /** @deprecated */
   @Deprecated
   public MethodBinding createMethodBinding(String ref, Class[] params) {
      Util.notNull("ref", ref);
      if (ref.startsWith("#{") && ref.endsWith("}")) {
         FacesContext context = FacesContext.getCurrentInstance();

         MethodExpression result;
         try {
            if (null == params) {
               params = RIConstants.EMPTY_CLASS_ARGS;
            }

            result = this.getExpressionFactory().createMethodExpression(context.getELContext(), ref, (Class)null, params);
         } catch (ELException var6) {
            throw new ReferenceSyntaxException(var6);
         }

         return new MethodBindingMethodExpressionAdapter(result);
      } else {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine(MessageFormat.format("Expression ''{0}'' does not follow the syntax #{...}", ref));
         }

         throw new ReferenceSyntaxException(ref);
      }
   }

   /** @deprecated */
   @Deprecated
   public ValueBinding createValueBinding(String ref) throws ReferenceSyntaxException {
      Util.notNull("ref", ref);
      FacesContext context = FacesContext.getCurrentInstance();

      ValueExpression result;
      try {
         result = this.getExpressionFactory().createValueExpression(context.getELContext(), ref, Object.class);
      } catch (ELException var5) {
         throw new ReferenceSyntaxException(var5);
      }

      return new ValueBindingValueExpressionAdapter(result);
   }

   /** @deprecated */
   @Deprecated
   public VariableResolver getVariableResolver() {
      if (this.compositeELResolver == null) {
         this.performOneTimeELInitialization();
      }

      return this.variableResolver;
   }

   /** @deprecated */
   @Deprecated
   public void setVariableResolver(VariableResolver resolver) {
      Util.notNull("variableResolver", resolver);
      Util.canSetAppArtifact(this.associate, "VariableResolver");
      this.variableResolver.setDelegate(ELUtils.getDelegateVR(this.associate, true));
      this.associate.setLegacyVariableResolver(resolver);
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine(MessageFormat.format("set VariableResolver Instance to ''{0}''", this.variableResolver.getClass().getName()));
      }

   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      EMPTY_EL_CTX_LIST_ARRAY = new ELContextListener[0];
   }
}
