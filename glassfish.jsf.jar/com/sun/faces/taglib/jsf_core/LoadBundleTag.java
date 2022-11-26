package com.sun.faces.taglib.jsf_core;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.el.ELUtils;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.ReflectionUtils;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentClassicTagBase;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class LoadBundleTag extends TagSupport {
   private static final long serialVersionUID = -584139192758868254L;
   static final String PRE_VIEW_LOADBUNDLES_LIST_ATTR_NAME = "com.sun.faces.taglib.jsf_core.PRE_VIEW_LOADBUNDLES_LIST";
   private static final Logger LOGGER;
   private ValueExpression basenameExpression;
   private String var;

   public void setBasename(ValueExpression basename) {
      this.basenameExpression = basename;
   }

   public void setVar(String var) {
      this.var = var;
   }

   public int doStartTag() throws JspException {
      FacesContext context = FacesContext.getCurrentInstance();
      String basename = (String)ELUtils.evaluateValueExpression(this.basenameExpression, context.getELContext());
      String message;
      if (null == basename) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "baseName");
         throw new NullPointerException(message);
      } else if (null == this.var) {
         message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "baseName");
         throw new NullPointerException(message);
      } else {
         final ResourceBundle bundle = ResourceBundle.getBundle(basename, context.getViewRoot().getLocale(), Util.getCurrentLoader(this));
         if (null == bundle) {
            throw new JspException("null ResourceBundle for " + basename);
         } else {
            Map toStore = new Map() {
               public String toString() {
                  StringBuffer sb = new StringBuffer();
                  Iterator entries = this.entrySet().iterator();

                  while(entries.hasNext()) {
                     Map.Entry cur = (Map.Entry)entries.next();
                     sb.append((String)cur.getKey()).append(": ").append(cur.getValue()).append('\n');
                  }

                  return sb.toString();
               }

               public void clear() {
                  throw new UnsupportedOperationException();
               }

               public boolean containsKey(Object key) {
                  boolean result = false;
                  if (null != key) {
                     result = null != bundle.getObject(key.toString());
                  }

                  return result;
               }

               public boolean containsValue(Object value) {
                  Enumeration keys = bundle.getKeys();
                  boolean result = false;

                  while(keys.hasMoreElements()) {
                     Object curObj = bundle.getObject((String)keys.nextElement());
                     if (curObj == value || null != curObj && curObj.equals(value)) {
                        result = true;
                        break;
                     }
                  }

                  return result;
               }

               public Set entrySet() {
                  HashMap mappings = new HashMap();
                  Enumeration keys = bundle.getKeys();

                  while(keys.hasMoreElements()) {
                     String key = (String)keys.nextElement();
                     Object value = bundle.getObject(key);
                     mappings.put(key, value);
                  }

                  return mappings.entrySet();
               }

               public boolean equals(Object obj) {
                  return obj != null && obj instanceof Map && this.entrySet().equals(((Map)obj).entrySet());
               }

               public Object get(Object key) {
                  if (null == key) {
                     return null;
                  } else {
                     try {
                        return bundle.getObject(key.toString());
                     } catch (MissingResourceException var3) {
                        return "???" + key + "???";
                     }
                  }
               }

               public int hashCode() {
                  return bundle.hashCode();
               }

               public boolean isEmpty() {
                  Enumeration keys = bundle.getKeys();
                  return !keys.hasMoreElements();
               }

               public Set keySet() {
                  Set keySet = new HashSet();
                  Enumeration keys = bundle.getKeys();

                  while(keys.hasMoreElements()) {
                     keySet.add(keys.nextElement());
                  }

                  return keySet;
               }

               public Object put(Object k, Object v) {
                  throw new UnsupportedOperationException();
               }

               public void putAll(Map t) {
                  throw new UnsupportedOperationException();
               }

               public Object remove(Object k) {
                  throw new UnsupportedOperationException();
               }

               public int size() {
                  int result = 0;

                  for(Enumeration keys = bundle.getKeys(); keys.hasMoreElements(); ++result) {
                     keys.nextElement();
                  }

                  return result;
               }

               public Collection values() {
                  ArrayList result = new ArrayList();
                  Enumeration keys = bundle.getKeys();

                  while(keys.hasMoreElements()) {
                     result.add(bundle.getObject((String)keys.nextElement()));
                  }

                  return result;
               }
            };
            ExternalContext extContext = context.getExternalContext();
            extContext.getRequestMap().put(this.var, toStore);
            if (WebConfiguration.getInstance(extContext).isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableLoadBundle11Compatibility)) {
               UIComponent bundleComponent = this.createNewLoadBundleComponent(this.var, toStore);
               UIComponentClassicTagBase parentTag = this.getParentUIComponentTag();
               if (null == parentTag) {
                  List preViewBundleComponents = getPreViewLoadBundleComponentList();
                  preViewBundleComponents.add(bundleComponent);
               } else {
                  addChildToParentTagAndParentComponent(bundleComponent, parentTag);
               }
            }

            return 1;
         }
      }
   }

   static void addChildToParentTagAndParentComponent(UIComponent child, UIComponentClassicTagBase parentTag) {
      Method addChildToComponentAndTag;
      if (null != (addChildToComponentAndTag = ReflectionUtils.lookupMethod(UIComponentClassicTagBase.class, "addChildToComponentAndTag", UIComponent.class))) {
         try {
            addChildToComponentAndTag.setAccessible(true);
            addChildToComponentAndTag.invoke(parentTag, child);
         } catch (IllegalArgumentException | IllegalAccessException var5) {
            if (LOGGER.isLoggable(Level.WARNING)) {
               LOGGER.log(Level.WARNING, "Unable to add " + child + " to tree:", var5);
            }
         } catch (InvocationTargetException var6) {
            Throwable cause = var6.getCause();
            if (cause instanceof RuntimeException) {
               throw (RuntimeException)cause;
            }
         }
      }

   }

   static List getPreViewLoadBundleComponentList() {
      FacesContext ctx = FacesContext.getCurrentInstance();
      Map stateMap = RequestStateManager.getStateMap(ctx);
      List result = (List)stateMap.get("com.sun.faces.taglib.jsf_core.PRE_VIEW_LOADBUNDLES_LIST");
      if (result == null) {
         result = new ArrayList();
         stateMap.put("com.sun.faces.taglib.jsf_core.PRE_VIEW_LOADBUNDLES_LIST", result);
      }

      return (List)result;
   }

   private UIComponent createNewLoadBundleComponent(String var, Map toStore) {
      UIComponent result = new LoadBundleComponent(var, toStore);
      result.setTransient(true);
      return result;
   }

   private UIComponentClassicTagBase getParentUIComponentTag() {
      Tag parent;
      for(parent = this.getParent(); null != parent && !(parent instanceof UIComponentClassicTagBase); parent = this.getParent()) {
      }

      UIComponentClassicTagBase result = (UIComponentClassicTagBase)parent;
      Stack viewTagStack = SubviewTag.getViewTagStack();
      if (!viewTagStack.empty()) {
         result = (UIComponentClassicTagBase)viewTagStack.peek();
      }

      return result;
   }

   public void release() {
      this.basenameExpression = null;
      this.var = null;
   }

   static {
      LOGGER = FacesLogger.TAGLIB.getLogger();
   }

   private static class LoadBundleComponent extends UIComponentBase {
      private String var;
      private Map toStore;

      public LoadBundleComponent(String var, Map toStore) {
         this.var = var;
         this.toStore = toStore;
      }

      public String getFamily() {
         return null;
      }

      public void encodeBegin(FacesContext context) throws IOException {
         Map requestMap = context.getExternalContext().getRequestMap();
         requestMap.put(this.var, this.toStore);
      }

      public void encodeEnd(FacesContext context) throws IOException {
      }

      public void encodeChildren(FacesContext context) throws IOException {
      }

      public String toString() {
         return "LoadBundleComponent: var: " + this.var + " keys: " + this.toStore.toString();
      }
   }
}
