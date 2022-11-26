package javax.faces.application;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.el.ELContextListener;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.component.search.SearchExpressionHandler;
import javax.faces.component.search.SearchKeywordResolver;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.el.PropertyResolver;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.ValueBinding;
import javax.faces.el.VariableResolver;
import javax.faces.event.ActionListener;
import javax.faces.event.SystemEventListener;
import javax.faces.flow.FlowHandler;
import javax.faces.validator.Validator;

public abstract class Application {
   private Application defaultApplication;

   public abstract ActionListener getActionListener();

   public abstract void setActionListener(ActionListener var1);

   public abstract Locale getDefaultLocale();

   public abstract void setDefaultLocale(Locale var1);

   public abstract String getDefaultRenderKitId();

   public abstract void setDefaultRenderKitId(String var1);

   public abstract String getMessageBundle();

   public abstract void setMessageBundle(String var1);

   public abstract NavigationHandler getNavigationHandler();

   public abstract void setNavigationHandler(NavigationHandler var1);

   public ResourceHandler getResourceHandler() {
      if (this.defaultApplication != null) {
         return this.defaultApplication.getResourceHandler();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void setResourceHandler(ResourceHandler resourceHandler) {
      if (this.defaultApplication != null) {
         this.defaultApplication.setResourceHandler(resourceHandler);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   /** @deprecated */
   public abstract PropertyResolver getPropertyResolver();

   /** @deprecated */
   public abstract void setPropertyResolver(PropertyResolver var1);

   public ResourceBundle getResourceBundle(FacesContext ctx, String name) {
      if (this.defaultApplication != null) {
         return this.defaultApplication.getResourceBundle(ctx, name);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public ProjectStage getProjectStage() {
      return this.defaultApplication != null ? this.defaultApplication.getProjectStage() : ProjectStage.Production;
   }

   /** @deprecated */
   public abstract VariableResolver getVariableResolver();

   /** @deprecated */
   public abstract void setVariableResolver(VariableResolver var1);

   public void addELResolver(ELResolver resolver) {
      if (this.defaultApplication != null) {
         this.defaultApplication.addELResolver(resolver);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public ELResolver getELResolver() {
      if (this.defaultApplication != null) {
         return this.defaultApplication.getELResolver();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public FlowHandler getFlowHandler() {
      return this.defaultApplication != null ? this.defaultApplication.getFlowHandler() : null;
   }

   public void setFlowHandler(FlowHandler newHandler) {
      if (this.defaultApplication != null) {
         this.defaultApplication.setFlowHandler(newHandler);
      }

   }

   public abstract ViewHandler getViewHandler();

   public abstract void setViewHandler(ViewHandler var1);

   public abstract StateManager getStateManager();

   public abstract void setStateManager(StateManager var1);

   public void addBehavior(String behaviorId, String behaviorClass) {
      if (this.defaultApplication != null) {
         this.defaultApplication.addBehavior(behaviorId, behaviorClass);
      }

   }

   public Behavior createBehavior(String behaviorId) throws FacesException {
      return this.defaultApplication != null ? this.defaultApplication.createBehavior(behaviorId) : null;
   }

   public Iterator getBehaviorIds() {
      return this.defaultApplication != null ? this.defaultApplication.getBehaviorIds() : Collections.EMPTY_LIST.iterator();
   }

   public abstract void addComponent(String var1, String var2);

   public abstract UIComponent createComponent(String var1) throws FacesException;

   /** @deprecated */
   public abstract UIComponent createComponent(ValueBinding var1, FacesContext var2, String var3) throws FacesException;

   public UIComponent createComponent(ValueExpression componentExpression, FacesContext context, String componentType) throws FacesException {
      if (this.defaultApplication != null) {
         return this.defaultApplication.createComponent(componentExpression, context, componentType);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public UIComponent createComponent(ValueExpression componentExpression, FacesContext context, String componentType, String rendererType) {
      if (this.defaultApplication != null) {
         return this.defaultApplication.createComponent(componentExpression, context, componentType, rendererType);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public UIComponent createComponent(FacesContext context, String componentType, String rendererType) {
      if (this.defaultApplication != null) {
         return this.defaultApplication.createComponent(context, componentType, rendererType);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public UIComponent createComponent(FacesContext context, Resource componentResource) {
      if (this.defaultApplication != null) {
         return this.defaultApplication.createComponent(context, componentResource);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract Iterator getComponentTypes();

   public abstract void addConverter(String var1, String var2);

   public abstract void addConverter(Class var1, String var2);

   public abstract Converter createConverter(String var1);

   public abstract Converter createConverter(Class var1);

   public abstract Iterator getConverterIds();

   public abstract Iterator getConverterTypes();

   public void addDefaultValidatorId(String validatorId) {
      if (this.defaultApplication != null) {
         this.defaultApplication.addDefaultValidatorId(validatorId);
      }

   }

   public Map getDefaultValidatorInfo() {
      return this.defaultApplication != null ? this.defaultApplication.getDefaultValidatorInfo() : Collections.emptyMap();
   }

   public ExpressionFactory getExpressionFactory() {
      if (this.defaultApplication != null) {
         return this.defaultApplication.getExpressionFactory();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public Object evaluateExpressionGet(FacesContext context, String expression, Class expectedType) throws ELException {
      if (this.defaultApplication != null) {
         return this.defaultApplication.evaluateExpressionGet(context, expression, expectedType);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   /** @deprecated */
   public abstract MethodBinding createMethodBinding(String var1, Class[] var2) throws ReferenceSyntaxException;

   public abstract Iterator getSupportedLocales();

   public abstract void setSupportedLocales(Collection var1);

   public void addELContextListener(ELContextListener listener) {
      if (this.defaultApplication != null) {
         this.defaultApplication.addELContextListener(listener);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void removeELContextListener(ELContextListener listener) {
      if (this.defaultApplication != null) {
         this.defaultApplication.removeELContextListener(listener);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public ELContextListener[] getELContextListeners() {
      if (this.defaultApplication != null) {
         return this.defaultApplication.getELContextListeners();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract void addValidator(String var1, String var2);

   public abstract Validator createValidator(String var1) throws FacesException;

   public abstract Iterator getValidatorIds();

   /** @deprecated */
   public abstract ValueBinding createValueBinding(String var1) throws ReferenceSyntaxException;

   public void publishEvent(FacesContext context, Class systemEventClass, Object source) {
      if (this.defaultApplication != null) {
         this.defaultApplication.publishEvent(context, systemEventClass, source);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void publishEvent(FacesContext context, Class systemEventClass, Class sourceBaseType, Object source) {
      if (this.defaultApplication != null) {
         this.defaultApplication.publishEvent(context, systemEventClass, sourceBaseType, source);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void subscribeToEvent(Class systemEventClass, Class sourceClass, SystemEventListener listener) {
      if (this.defaultApplication != null) {
         this.defaultApplication.subscribeToEvent(systemEventClass, sourceClass, listener);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void subscribeToEvent(Class systemEventClass, SystemEventListener listener) {
      if (this.defaultApplication != null) {
         this.defaultApplication.subscribeToEvent(systemEventClass, listener);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void unsubscribeFromEvent(Class systemEventClass, Class sourceClass, SystemEventListener listener) {
      if (this.defaultApplication != null) {
         this.defaultApplication.unsubscribeFromEvent(systemEventClass, sourceClass, listener);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void unsubscribeFromEvent(Class systemEventClass, SystemEventListener listener) {
      if (this.defaultApplication != null) {
         this.defaultApplication.unsubscribeFromEvent(systemEventClass, listener);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public SearchExpressionHandler getSearchExpressionHandler() {
      if (this.defaultApplication != null) {
         return this.defaultApplication.getSearchExpressionHandler();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void setSearchExpressionHandler(SearchExpressionHandler searchExpressionHandler) {
      if (this.defaultApplication != null) {
         this.defaultApplication.setSearchExpressionHandler(searchExpressionHandler);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void addSearchKeywordResolver(SearchKeywordResolver resolver) {
      if (this.defaultApplication != null) {
         this.defaultApplication.addSearchKeywordResolver(resolver);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public SearchKeywordResolver getSearchKeywordResolver() {
      if (this.defaultApplication != null) {
         return this.defaultApplication.getSearchKeywordResolver();
      } else {
         throw new UnsupportedOperationException();
      }
   }
}
