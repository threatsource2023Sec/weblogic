package com.sun.faces.util;

import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;

public class RequestStateManager {
   public static final String AFTER_VIEW_CONTENT = "com.sun.faces.AFTER_VIEW_CONTENT";
   public static final String EL_RESOLVER_CHAIN_TYPE_NAME = "com.sun.faces.ELResolverChainType";
   public static final String TARGET_COMPONENT_ATTRIBUTE_NAME = "com.sun.faces.ComponentForValue";
   public static final String EXTERNALCONTEXT_IMPL_ATTR_NAME = "com.sun.faces.ExternalContextImpl";
   public static final String FACESCONTEXT_IMPL_ATTR_NAME = "com.sun.faces.FacesContextImpl";
   public static final String RENDER_KIT_IMPL_REQ = "com.sun.faces.renderKitImplForRequest";
   public static final String CLIENT_ID_MESSAGES_NOT_DISPLAYED = "com.sun.faces.clientIdMessagesNotDisplayed";
   public static final String LOGICAL_VIEW_MAP = "com.sun.faces.logicalViewMap";
   public static final String ACTUAL_VIEW_MAP = "com.sun.faces.actualViewMap";
   public static final String VIEWTAG_STACK_ATTR_NAME = "com.sun.faces.taglib.jsf_core.VIEWTAG_STACK";
   public static final String INVOCATION_PATH = "com.sun.faces.INVOCATION_PATH";
   public static final String REENTRANT_GUARD = "com.sun.faces.LegacyVariableResolver";
   public static final String FACES_VIEW_STATE = "com.sun.faces.FACES_VIEW_STATE";
   public static final String FACES_VIEW_STRUCTURE = "com.sun.faces.FACES_VIEW_STRUCTURE";
   private static final String KEY = RequestStateManager.class.getName();

   public static Object get(FacesContext ctx, String key) {
      if (ctx != null && key != null) {
         Map reqState = getStateMap(ctx);
         return reqState.get(key);
      } else {
         return null;
      }
   }

   public static void set(FacesContext ctx, String key, Object value) {
      if (ctx != null && key != null) {
         if (value == null) {
            remove(ctx, key);
         }

         Map reqState = getStateMap(ctx);
         reqState.put(key, value);
      }
   }

   public static Object remove(FacesContext ctx, String key) {
      if (ctx != null && key != null) {
         Map reqState = getStateMap(ctx);
         return reqState.remove(key);
      } else {
         return null;
      }
   }

   public static boolean containsKey(FacesContext ctx, String key) {
      if (ctx != null && key != null) {
         Map reqState = getStateMap(ctx);
         return reqState.containsKey(key);
      } else {
         return false;
      }
   }

   public static Map getStateMap(FacesContext ctx) {
      assert ctx != null;

      Map requestMap = ctx.getExternalContext().getRequestMap();
      Map reqState = (Map)requestMap.get(KEY);
      if (reqState == null) {
         reqState = new HashMap();
         requestMap.put(KEY, reqState);
      }

      return (Map)reqState;
   }
}
