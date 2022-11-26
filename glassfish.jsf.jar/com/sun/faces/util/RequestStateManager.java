package com.sun.faces.util;

import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;

public class RequestStateManager {
   public static final String AFTER_VIEW_CONTENT = "com.sun.faces.AFTER_VIEW_CONTENT";
   public static final String EL_RESOLVER_CHAIN_TYPE_NAME = "com.sun.faces.ELResolverChainType";
   public static final String TARGET_COMPONENT_ATTRIBUTE_NAME = "com.sun.faces.ComponentForValue";
   public static final String RENDER_KIT_IMPL_REQ = "com.sun.faces.renderKitImplForRequest";
   public static final String LOGICAL_VIEW_MAP = "com.sun.faces.logicalViewMap";
   public static final String ACTUAL_VIEW_MAP = "com.sun.faces.actualViewMap";
   public static final String VIEWTAG_STACK_ATTR_NAME = "com.sun.faces.taglib.jsf_core.VIEWTAG_STACK";
   public static final String INVOCATION_PATH = "com.sun.faces.INVOCATION_PATH";
   public static final String REENTRANT_GUARD = "com.sun.faces.LegacyVariableResolver";
   public static final String FACES_VIEW_STATE = "com.sun.faces.FACES_VIEW_STATE";
   public static final String RESOURCE_REQUEST = "com.sun.faces.RESOURCE_REQUEST";
   public static final String FACELET_FACTORY = "com.sun.faces.FACELET_FACTORY";
   public static final String SCRIPT_STATE = "com.sun.faces.SCRIPT_STATE";
   public static final String DISABLED_VALIDATORS = "com.sun.faces.DISABLED_VALIDATORS";
   public static final String PROCESSED_RESOURCE_DEPENDENCIES = "com.sun.faces.PROCESSED_RESOURCE_DEPENDENCIES";
   public static final String PROCESSED_RADIO_BUTTON_GROUPS = "com.sun.faces.PROCESSED_RADIO_BUTTON_GROUPS";
   public static final String RENDERED_RESOURCE_DEPENDENCIES = "/javax.faces.resource";
   private static final String[] ATTRIBUTES_TO_CLEAR_ON_CHANGE_OF_VIEW = new String[]{"com.sun.faces.SCRIPT_STATE", "com.sun.faces.PROCESSED_RESOURCE_DEPENDENCIES", "com.sun.faces.PROCESSED_RADIO_BUTTON_GROUPS"};
   private static final String KEY = RequestStateManager.class.getName();

   public static Object get(FacesContext ctx, String key) {
      return ctx != null && key != null ? ctx.getAttributes().get(key) : null;
   }

   public static void set(FacesContext ctx, String key, Object value) {
      if (ctx != null && key != null) {
         if (value == null) {
            remove(ctx, key);
         }

         ctx.getAttributes().put(key, value);
      }
   }

   public static Object remove(FacesContext ctx, String key) {
      return ctx != null && key != null ? ctx.getAttributes().remove(key) : null;
   }

   public static void clearAttributesOnChangeOfView(FacesContext ctx) {
      if (ctx != null) {
         Map attrs = ctx.getAttributes();
         String[] var2 = ATTRIBUTES_TO_CLEAR_ON_CHANGE_OF_VIEW;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String key = var2[var4];
            attrs.remove(key);
         }

         PartialViewContext pvc = ctx.getPartialViewContext();
         if (!pvc.isAjaxRequest() || pvc.isRenderAll()) {
            attrs.remove("/javax.faces.resource");
         }

      }
   }

   public static boolean containsKey(FacesContext ctx, String key) {
      return ctx != null && key != null && ctx.getAttributes().containsKey(key);
   }

   public static Map getStateMap(FacesContext ctx) {
      assert ctx != null;

      Map contextMap = ctx.getAttributes();
      Map reqState = (Map)contextMap.get(KEY);
      if (reqState == null) {
         reqState = new HashMap();
         contextMap.put(KEY, reqState);
      }

      return (Map)reqState;
   }
}
