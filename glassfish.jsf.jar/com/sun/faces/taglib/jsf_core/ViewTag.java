package com.sun.faces.taglib.jsf_core;

import com.sun.faces.RIConstants;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.ReflectionUtils;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.jstl.core.Config;
import javax.servlet.jsp.tagext.BodyContent;

public class ViewTag extends UIComponentELTag {
   private static final Logger LOGGER;
   protected ValueExpression renderKitId = null;
   protected ValueExpression locale = null;
   protected MethodExpression beforePhase = null;
   protected MethodExpression afterPhase = null;

   public void setRenderKitId(ValueExpression renderKitId) {
      this.renderKitId = renderKitId;
   }

   public void setLocale(ValueExpression newLocale) {
      this.locale = newLocale;
   }

   public void setBeforePhase(MethodExpression newBeforePhase) {
      this.beforePhase = newBeforePhase;
   }

   public void setAfterPhase(MethodExpression newAfterPhase) {
      this.afterPhase = newAfterPhase;
   }

   protected int getDoStartValue() throws JspException {
      return 2;
   }

   public int doStartTag() throws JspException {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      if (facesContext == null) {
         throw new IllegalStateException(MessageUtils.getExceptionMessageString("com.sun.faces.FACES_CONTEXT_NOT_FOUND"));
      } else {
         Object response = facesContext.getExternalContext().getResponse();
         Method customFlush = ReflectionUtils.lookupMethod(response.getClass(), "flushContentToWrappedResponse", RIConstants.EMPTY_CLASS_ARGS);
         if (customFlush != null) {
            try {
               this.pageContext.getOut().flush();
               customFlush.invoke(response, RIConstants.EMPTY_METH_ARGS);
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException var8) {
               throw new JspException("Exception attemtping to write content above the <f:view> tag.", var8);
            }
         } else if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "jsf.core.taglib.viewtag.interweaving_failed");
         }

         int rc;
         try {
            rc = super.doStartTag();
         } catch (JspException var9) {
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, "Can't leverage base class", var9);
            }

            throw var9;
         } catch (Throwable var10) {
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, "Can't leverage base class", var10);
            }

            throw new JspException(var10);
         }

         this.pageContext.getResponse().setLocale(facesContext.getViewRoot().getLocale());
         List preViewLoadBundleComponents = LoadBundleTag.getPreViewLoadBundleComponentList();
         if (!preViewLoadBundleComponents.isEmpty()) {
            Iterator iter = preViewLoadBundleComponents.iterator();

            while(iter.hasNext()) {
               UIComponent cur = (UIComponent)iter.next();
               LoadBundleTag.addChildToParentTagAndParentComponent(cur, this);
            }

            preViewLoadBundleComponents.clear();
         }

         Stack viewTagStack = SubviewTag.getViewTagStack();
         viewTagStack.push(this);
         return rc;
      }
   }

   public int doAfterBody() throws JspException {
      int result = 6;
      UIViewRoot root = FacesContext.getCurrentInstance().getViewRoot();
      Stack viewTagStack = SubviewTag.getViewTagStack();
      viewTagStack.pop();
      BodyContent bodyContent;
      String content;
      String trimContent;
      if (null != (bodyContent = this.getBodyContent()) && null != (content = bodyContent.getString()) && 0 != (trimContent = content.trim()).length() && (!trimContent.startsWith("<!--") || !trimContent.endsWith("-->"))) {
         bodyContent.clearBody();
         UIOutput verbatim = this.createVerbatimComponent();
         verbatim.setValue(content);
         root.getChildren().add(verbatim);
         return result;
      } else {
         return result;
      }
   }

   public int doEndTag() throws JspException {
      int rc = super.doEndTag();
      HttpSession session;
      if (null != (session = this.pageContext.getSession())) {
         session.setAttribute("javax.faces.request.charset", this.pageContext.getResponse().getCharacterEncoding());
      }

      return rc;
   }

   public String getComponentType() {
      return "javax.faces.ViewRoot";
   }

   public String getRendererType() {
      return null;
   }

   protected int getDoEndValue() throws JspException {
      return 6;
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);
      Locale viewLocale = null;
      UIViewRoot viewRoot = (UIViewRoot)component;
      FacesContext context = FacesContext.getCurrentInstance();
      ELContext elContext = context.getELContext();

      try {
         if (null != this.renderKitId) {
            if (this.renderKitId.isLiteralText()) {
               viewRoot.setRenderKitId(this.renderKitId.getValue(elContext).toString());
            } else {
               viewRoot.setRenderKitId((String)null);
               viewRoot.setValueExpression("renderKitId", this.renderKitId);
            }
         } else if (viewRoot.getRenderKitId() == null) {
            String renderKitIdString = context.getApplication().getDefaultRenderKitId();
            if (null == renderKitIdString) {
               renderKitIdString = "HTML_BASIC";
            }

            viewRoot.setRenderKitId(renderKitIdString);
         }

         if (null != this.locale) {
            if (this.locale.isLiteralText()) {
               viewLocale = this.getLocaleFromString(this.locale.getValue(elContext).toString());
            } else {
               component.setValueExpression("locale", this.locale);
               Object result = this.locale.getValue(context.getELContext());
               if (result instanceof Locale) {
                  viewLocale = (Locale)result;
               } else if (result instanceof String) {
                  viewLocale = this.getLocaleFromString((String)result);
               }
            }
         }

         if (null != viewLocale) {
            ((UIViewRoot)component).setLocale(viewLocale);
            Config.set(this.pageContext.getRequest(), "javax.servlet.jsp.jstl.fmt.locale", viewLocale);
         }

         Object[] params;
         if (null != this.beforePhase) {
            if (this.beforePhase.isLiteralText()) {
               params = new Object[]{this.beforePhase};
               throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.INVALID_EXPRESSION", params));
            }

            viewRoot.setBeforePhaseListener(this.beforePhase);
         }

         if (null != this.afterPhase) {
            if (this.afterPhase.isLiteralText()) {
               params = new Object[]{this.afterPhase};
               throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.INVALID_EXPRESSION", params));
            }

            viewRoot.setAfterPhaseListener(this.afterPhase);
         }

      } catch (ELException var7) {
         throw new FacesException(var7);
      }
   }

   protected Locale getLocaleFromString(String localeExpr) {
      Locale result = Locale.getDefault();
      if (localeExpr.indexOf("_") == -1 && localeExpr.indexOf("-") == -1) {
         if (localeExpr.length() == 2) {
            result = new Locale(localeExpr, "");
         }
      } else if (localeExpr.length() == 5) {
         String language = localeExpr.substring(0, 2);
         String country = localeExpr.substring(3, localeExpr.length());
         result = new Locale(language, country);
      }

      return result;
   }

   static {
      LOGGER = FacesLogger.TAGLIB.getLogger();
   }
}
