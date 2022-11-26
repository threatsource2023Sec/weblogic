package com.sun.faces.ext.validator;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.el.ValueExpression;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

class MojarraMessageFactory {
   private MojarraMessageFactory() {
   }

   protected static FacesMessage getMessage(String messageId, FacesMessage.Severity severity, Object... params) {
      FacesMessage message = getMessage(messageId, params);
      message.setSeverity(severity);
      return message;
   }

   protected static FacesMessage getMessage(Locale locale, String messageId, FacesMessage.Severity severity, Object... params) {
      FacesMessage message = getMessage(locale, messageId, params);
      message.setSeverity(severity);
      return message;
   }

   protected static FacesMessage getMessage(FacesContext context, String messageId, FacesMessage.Severity severity, Object... params) {
      FacesMessage message = getMessage(context, messageId, params);
      message.setSeverity(severity);
      return message;
   }

   protected static FacesMessage getMessage(String messageId, Object... params) {
      Locale locale = null;
      FacesContext context = FacesContext.getCurrentInstance();
      if (context != null && context.getViewRoot() != null) {
         locale = context.getViewRoot().getLocale();
         if (locale == null) {
            locale = Locale.getDefault();
         }
      } else {
         locale = Locale.getDefault();
      }

      return getMessage(locale, messageId, params);
   }

   protected static FacesMessage getMessage(Locale locale, String messageId, Object... params) {
      String summary = null;
      String detail = null;
      ResourceBundle bundle;
      String bundleName;
      if (null != (bundleName = getApplication().getMessageBundle()) && null != (bundle = ResourceBundle.getBundle(bundleName, locale, getCurrentLoader(bundleName)))) {
         try {
            summary = bundle.getString(messageId);
            detail = bundle.getString(messageId + "_detail");
         } catch (MissingResourceException var9) {
         }
      }

      if (null == summary) {
         bundle = ResourceBundle.getBundle("com.sun.faces.ext.validator.mojarraMessages", locale, getCurrentLoader(bundleName));
         if (null == bundle) {
            throw new NullPointerException();
         }

         try {
            summary = bundle.getString(messageId);
            if (null == summary) {
               return null;
            }

            detail = bundle.getString(messageId + "_detail");
         } catch (MissingResourceException var8) {
         }
      }

      FacesMessage ret = new BindingFacesMessage(locale, summary, detail, params);
      ret.setSeverity(FacesMessage.SEVERITY_ERROR);
      return ret;
   }

   protected static FacesMessage getMessage(FacesContext context, String messageId, Object... params) {
      if (context != null && messageId != null) {
         Locale locale;
         if (context.getViewRoot() != null) {
            locale = context.getViewRoot().getLocale();
         } else {
            locale = Locale.getDefault();
         }

         if (null == locale) {
            throw new NullPointerException(" locale is null ");
         } else {
            FacesMessage message = getMessage(locale, messageId, params);
            if (message != null) {
               return message;
            } else {
               locale = Locale.getDefault();
               return getMessage(locale, messageId, params);
            }
         }
      } else {
         throw new NullPointerException(" context " + context + " messageId " + messageId);
      }
   }

   protected static Object getLabel(FacesContext context, UIComponent component) {
      Object o = component.getAttributes().get("label");
      if (o == null || o instanceof String && ((String)o).length() == 0) {
         o = component.getValueExpression("label");
      }

      if (o == null) {
         o = component.getClientId(context);
      }

      return o;
   }

   protected static Application getApplication() {
      FacesContext context = FacesContext.getCurrentInstance();
      if (context != null) {
         return FacesContext.getCurrentInstance().getApplication();
      } else {
         ApplicationFactory afactory = (ApplicationFactory)FactoryFinder.getFactory("javax.faces.application.ApplicationFactory");
         return afactory.getApplication();
      }
   }

   protected static ClassLoader getCurrentLoader(Object fallbackClass) {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      if (loader == null) {
         loader = fallbackClass.getClass().getClassLoader();
      }

      return loader;
   }

   static class BindingFacesMessage extends FacesMessage {
      private Locale locale;
      private Object[] parameters;
      private Object[] resolvedParameters;

      BindingFacesMessage(Locale locale, String messageFormat, String detailMessageFormat, Object[] parameters) {
         super(messageFormat, detailMessageFormat);
         this.locale = locale;
         this.parameters = parameters;
         if (parameters != null) {
            this.resolvedParameters = new Object[parameters.length];
         }

      }

      public String getSummary() {
         String pattern = super.getSummary();
         this.resolveBindings();
         return this.getFormattedString(pattern, this.resolvedParameters);
      }

      public String getDetail() {
         String pattern = super.getDetail();
         this.resolveBindings();
         return this.getFormattedString(pattern, this.resolvedParameters);
      }

      private void resolveBindings() {
         FacesContext context = null;
         if (this.parameters != null) {
            for(int i = 0; i < this.parameters.length; ++i) {
               Object o = this.parameters[i];
               if (o instanceof ValueBinding) {
                  if (context == null) {
                     context = FacesContext.getCurrentInstance();
                  }

                  o = ((ValueBinding)o).getValue(context);
               }

               if (o instanceof ValueExpression) {
                  if (context == null) {
                     context = FacesContext.getCurrentInstance();
                  }

                  o = ((ValueExpression)o).getValue(context.getELContext());
               }

               if (o == null) {
                  o = "";
               }

               this.resolvedParameters[i] = o;
            }
         }

      }

      private String getFormattedString(String msgtext, Object[] params) {
         String localizedStr = null;
         if (params != null && msgtext != null) {
            StringBuffer b = new StringBuffer(100);
            MessageFormat mf = new MessageFormat(msgtext);
            if (this.locale != null) {
               mf.setLocale(this.locale);
               b.append(mf.format(params));
               localizedStr = b.toString();
            }

            return localizedStr;
         } else {
            return msgtext;
         }
      }
   }
}
