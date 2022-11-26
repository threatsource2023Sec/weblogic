package javax.faces.application;

import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.el.ELContextListener;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.el.MethodBinding;
import javax.faces.el.PropertyResolver;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.ValueBinding;
import javax.faces.el.VariableResolver;
import javax.faces.event.ActionListener;
import javax.faces.validator.Validator;

public abstract class Application {
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

   /** @deprecated */
   public abstract PropertyResolver getPropertyResolver();

   /** @deprecated */
   public abstract void setPropertyResolver(PropertyResolver var1);

   public ResourceBundle getResourceBundle(FacesContext ctx, String name) {
      Application app = getRIApplicationImpl(ctx);
      if (app != null) {
         return app.getResourceBundle(ctx, name);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   /** @deprecated */
   public abstract VariableResolver getVariableResolver();

   /** @deprecated */
   public abstract void setVariableResolver(VariableResolver var1);

   public void addELResolver(ELResolver resolver) {
      Application app = getRIApplicationImpl();
      if (app != null) {
         app.addELResolver(resolver);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public ELResolver getELResolver() {
      Application app = getRIApplicationImpl();
      if (app != null) {
         return app.getELResolver();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract ViewHandler getViewHandler();

   public abstract void setViewHandler(ViewHandler var1);

   public abstract StateManager getStateManager();

   public abstract void setStateManager(StateManager var1);

   public abstract void addComponent(String var1, String var2);

   public abstract UIComponent createComponent(String var1) throws FacesException;

   /** @deprecated */
   public abstract UIComponent createComponent(ValueBinding var1, FacesContext var2, String var3) throws FacesException;

   public UIComponent createComponent(ValueExpression componentExpression, FacesContext context, String componentType) throws FacesException {
      if (null != componentExpression && null != context && null != componentType) {
         boolean createOne = false;

         Object result;
         try {
            if (null != (result = componentExpression.getValue(context.getELContext()))) {
               createOne = !(result instanceof UIComponent);
            }

            if (null == result || createOne) {
               result = this.createComponent(componentType);
               componentExpression.setValue(context.getELContext(), result);
            }
         } catch (ELException var7) {
            throw new FacesException(var7);
         }

         return (UIComponent)result;
      } else {
         StringBuilder builder = new StringBuilder(64);
         builder.append("null parameters - ");
         builder.append("componentExpression: ").append(componentExpression);
         builder.append(", context: ").append(context);
         builder.append(", componentType: ").append(componentType);
         throw new NullPointerException(builder.toString());
      }
   }

   public abstract Iterator getComponentTypes();

   public abstract void addConverter(String var1, String var2);

   public abstract void addConverter(Class var1, String var2);

   public abstract Converter createConverter(String var1);

   public abstract Converter createConverter(Class var1);

   public abstract Iterator getConverterIds();

   public abstract Iterator getConverterTypes();

   public ExpressionFactory getExpressionFactory() {
      Application app = getRIApplicationImpl();
      if (app != null) {
         return app.getExpressionFactory();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public Object evaluateExpressionGet(FacesContext context, String expression, Class expectedType) throws ELException {
      Application app = getRIApplicationImpl(context);
      if (app != null) {
         return app.evaluateExpressionGet(context, expression, expectedType);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   /** @deprecated */
   public abstract MethodBinding createMethodBinding(String var1, Class[] var2) throws ReferenceSyntaxException;

   public abstract Iterator getSupportedLocales();

   public abstract void setSupportedLocales(Collection var1);

   public void addELContextListener(ELContextListener listener) {
      Application app = getRIApplicationImpl();
      if (app != null) {
         app.addELContextListener(listener);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void removeELContextListener(ELContextListener listener) {
      Application app = getRIApplicationImpl();
      if (app != null) {
         app.removeELContextListener(listener);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public ELContextListener[] getELContextListeners() {
      Application app = getRIApplicationImpl();
      if (app != null) {
         return app.getELContextListeners();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract void addValidator(String var1, String var2);

   public abstract Validator createValidator(String var1) throws FacesException;

   public abstract Iterator getValidatorIds();

   /** @deprecated */
   public abstract ValueBinding createValueBinding(String var1) throws ReferenceSyntaxException;

   private static Application getRIApplicationImpl(FacesContext context) {
      ExternalContext extContext;
      if (context != null) {
         extContext = context.getExternalContext();
      } else {
         extContext = FacesContext.getCurrentInstance().getExternalContext();
      }

      return extContext != null ? (Application)extContext.getApplicationMap().get("com.sun.faces.ApplicationImpl") : null;
   }

   private static Application getRIApplicationImpl() {
      return getRIApplicationImpl((FacesContext)null);
   }
}
