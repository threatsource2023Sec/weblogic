package com.sun.faces.taglib.jsf_core;

import com.sun.faces.RIConstants;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.ReflectionUtils;
import com.sun.faces.util.RequestStateManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

public class SubviewTag extends UIComponentELTag {
   private static final Logger LOGGER;

   public String getComponentType() {
      return "javax.faces.NamingContainer";
   }

   public String getRendererType() {
      return null;
   }

   protected UIComponent createVerbatimComponentFromBodyContent() {
      UIOutput verbatim = (UIOutput)super.createVerbatimComponentFromBodyContent();
      String value = null;
      FacesContext ctx = this.getFacesContext();
      Object response = ctx.getExternalContext().getResponse();
      Method customFlush = ReflectionUtils.lookupMethod(response.getClass(), "flushContentToWrappedResponse", RIConstants.EMPTY_CLASS_ARGS);
      Method isBytes = ReflectionUtils.lookupMethod(response.getClass(), "isBytes", RIConstants.EMPTY_CLASS_ARGS);
      Method isChars = ReflectionUtils.lookupMethod(response.getClass(), "isChars", RIConstants.EMPTY_CLASS_ARGS);
      Method resetBuffers = ReflectionUtils.lookupMethod(response.getClass(), "resetBuffers", RIConstants.EMPTY_CLASS_ARGS);
      Method getChars = ReflectionUtils.lookupMethod(response.getClass(), "getChars", RIConstants.EMPTY_CLASS_ARGS);
      boolean cont = true;
      if (isBytes == null) {
         cont = false;
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "jsf.core.taglib.subviewtag.interweaving_failed_isbytes");
         }
      }

      if (isChars == null) {
         cont = false;
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "jsf.core.taglib.subviewtag.interweaving_failed_ischars");
         }
      }

      if (resetBuffers == null) {
         cont = false;
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "jsf.core.taglib.subviewtag.interweaving_failed_resetbuffers");
         }
      }

      if (getChars == null) {
         cont = false;
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "jsf.core.taglib.subviewtag.interweaving_failed_getchars");
         }
      }

      if (customFlush == null) {
         cont = false;
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "jsf.core.taglib.viewtag.interweaving_failed");
         }
      }

      if (cont) {
         try {
            if ((Boolean)isBytes.invoke(response)) {
               customFlush.invoke(response);
            } else if ((Boolean)isChars.invoke(response)) {
               char[] chars = (char[])((char[])getChars.invoke(response));
               if (null != chars && 0 < chars.length) {
                  if (null != verbatim) {
                     value = (String)verbatim.getValue();
                  }

                  verbatim = super.createVerbatimComponent();
                  if (null != value) {
                     verbatim.setValue(value + new String(chars));
                  } else {
                     verbatim.setValue(new String(chars));
                  }
               }
            }

            resetBuffers.invoke(response);
         } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var12) {
            throw new FacesException("Response interweaving failed!", var12);
         }
      }

      return verbatim;
   }

   public int doEndTag() throws JspException {
      getViewTagStack().pop();
      int retValue = super.doEndTag();
      return retValue;
   }

   public int doStartTag() throws JspException {
      int retValue = super.doStartTag();
      getViewTagStack().push(this);
      return retValue;
   }

   static Stack getViewTagStack() {
      FacesContext ctx = FacesContext.getCurrentInstance();
      Map stateMap = RequestStateManager.getStateMap(ctx);
      Stack result = (Stack)stateMap.get("com.sun.faces.taglib.jsf_core.VIEWTAG_STACK");
      if (result == null) {
         result = new Stack();
         stateMap.put("com.sun.faces.taglib.jsf_core.VIEWTAG_STACK", result);
      }

      return result;
   }

   static {
      LOGGER = FacesLogger.TAGLIB.getLogger();
   }
}
