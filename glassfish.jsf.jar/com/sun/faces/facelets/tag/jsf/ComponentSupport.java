package com.sun.faces.facelets.tag.jsf;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.context.StateContext;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;

public final class ComponentSupport {
   private static final String MARK_DELETED = "com.sun.faces.facelets.MARK_DELETED";
   public static final String MARK_CREATED = "com.sun.faces.facelets.MARK_ID";
   public static final String MARK_CHILDREN_MODIFIED = "com.sun.faces.facelets.MARK_CHILDREN_MODIFIED";
   public static final String REMOVED_CHILDREN = "com.sun.faces.facelets.REMOVED_CHILDREN";
   public static final String MARK_CREATED_REMOVED = StateContext.class.getName() + "_MARK_CREATED_REMOVED";
   private static final String IMPLICIT_PANEL = "com.sun.faces.facelets.IMPLICIT_PANEL";
   public static final String COMPONENT_TO_TAG_MAP_NAME = "com.sun.faces.facelets.COMPONENT_TO_LOCATION_MAP";

   public static boolean handlerIsResourceRelated(ComponentHandler handler) {
      ComponentConfig config = handler.getComponentConfig();
      if (!"javax.faces.Output".equals(config.getComponentType())) {
         return false;
      } else {
         String rendererType = config.getRendererType();
         return "javax.faces.resource.Script".equals(rendererType) || "javax.faces.resource.Stylesheet".equals(rendererType);
      }
   }

   public static boolean isBuildingNewComponentTree(FacesContext context) {
      return !context.isPostback() || context.getCurrentPhaseId().equals(PhaseId.RESTORE_VIEW);
   }

   public static boolean isImplicitPanel(UIComponent component) {
      return component.getAttributes().containsKey("com.sun.faces.facelets.IMPLICIT_PANEL");
   }

   public static void finalizeForDeletion(UIComponent c) {
      c.getAttributes().remove("com.sun.faces.facelets.MARK_DELETED");
      int sz = c.getChildCount();
      if (sz > 0) {
         UIComponent cc = null;
         List cl = c.getChildren();

         while(true) {
            --sz;
            if (sz < 0) {
               break;
            }

            cc = (UIComponent)cl.get(sz);
            if (cc.getAttributes().containsKey("com.sun.faces.facelets.MARK_DELETED")) {
               cl.remove(sz);
            }
         }
      }

      Map facets = c.getFacets();
      if (facets.size() > 0) {
         Set col = facets.entrySet();
         Iterator itr = col.iterator();

         while(true) {
            while(itr.hasNext()) {
               Map.Entry curEntry = (Map.Entry)itr.next();
               UIComponent fc = (UIComponent)curEntry.getValue();
               Map attrs = fc.getAttributes();
               if (attrs.containsKey("com.sun.faces.facelets.MARK_DELETED")) {
                  itr.remove();
               } else if ("javax.faces.component.COMPOSITE_FACET_NAME".equals(curEntry.getKey()) || attrs.containsKey("com.sun.faces.facelets.IMPLICIT_PANEL") && !((String)curEntry.getKey()).equals("javax_faces_metadata")) {
                  List implicitPanelChildren = fc.getChildren();
                  Iterator innerItr = implicitPanelChildren.iterator();

                  while(innerItr.hasNext()) {
                     UIComponent innerChild = (UIComponent)innerItr.next();
                     if (innerChild.getAttributes().containsKey("com.sun.faces.facelets.MARK_DELETED")) {
                        innerItr.remove();
                     }
                  }
               }
            }

            return;
         }
      }
   }

   public static Tag setTagForComponent(FacesContext context, UIComponent c, Tag t) {
      Map contextMap = context.getAttributes();
      Map componentToTagMap = (Map)contextMap.get("com.sun.faces.facelets.COMPONENT_TO_LOCATION_MAP");
      if (null == componentToTagMap) {
         componentToTagMap = new HashMap();
         contextMap.put("com.sun.faces.facelets.COMPONENT_TO_LOCATION_MAP", componentToTagMap);
      }

      return (Tag)((Map)componentToTagMap).put(System.identityHashCode(c), t);
   }

   public static Tag getTagForComponent(FacesContext context, UIComponent c) {
      Tag result = null;
      Map contextMap = context.getAttributes();
      Map componentToTagMap = (Map)contextMap.get("com.sun.faces.facelets.COMPONENT_TO_LOCATION_MAP");
      if (null != componentToTagMap) {
         result = (Tag)componentToTagMap.get(System.identityHashCode(c));
      }

      return result;
   }

   public static UIComponent findChild(UIComponent parent, String id) {
      int sz = parent.getChildCount();
      if (sz > 0) {
         UIComponent c = null;
         List cl = parent.getChildren();

         while(true) {
            --sz;
            if (sz < 0) {
               break;
            }

            c = (UIComponent)cl.get(sz);
            if (id.equals(c.getId())) {
               return c;
            }
         }
      }

      return null;
   }

   public static UIComponent findUIInstructionChildByTagId(FacesContext context, UIComponent parent, String id) {
      UIComponent result = null;
      if (isBuildingNewComponentTree(context)) {
         return null;
      } else {
         Map attrs = context.getAttributes();
         if (attrs.containsKey(WebConfiguration.BooleanWebContextInitParameter.PartialStateSaving) && (Boolean)attrs.get(WebConfiguration.BooleanWebContextInitParameter.PartialStateSaving)) {
            result = findChildByTagId(context, parent, id);
         }

         return result;
      }
   }

   public static UIComponent findChildByTagId(FacesContext context, UIComponent parent, String id) {
      UIComponent c = null;
      UIViewRoot root = context.getViewRoot();
      boolean hasDynamicComponents = null != root && root.getAttributes().containsKey("com.sun.faces.TreeHasDynamicComponents");
      String cid = null;
      String facetName = getFacetName(parent);
      if (null != facetName) {
         c = parent.getFacet(facetName);
         if (null != c) {
            cid = (String)c.getAttributes().get("com.sun.faces.facelets.MARK_ID");
            if (id.equals(cid)) {
               return c;
            }
         }
      }

      Object components;
      if (0 < parent.getFacetCount()) {
         components = new ArrayList();
         ((List)components).addAll(parent.getFacets().values());
         ((List)components).addAll(parent.getChildren());
      } else {
         components = parent.getChildren();
      }

      int len = ((List)components).size();

      for(int i = 0; i < len; ++i) {
         c = (UIComponent)((List)components).get(i);
         cid = (String)c.getAttributes().get("com.sun.faces.facelets.MARK_ID");
         if (id.equals(cid)) {
            return c;
         }

         if (c instanceof UIPanel && c.getAttributes().containsKey("com.sun.faces.facelets.IMPLICIT_PANEL")) {
            Iterator var11 = c.getChildren().iterator();

            while(var11.hasNext()) {
               UIComponent c2 = (UIComponent)var11.next();
               cid = (String)c2.getAttributes().get("com.sun.faces.facelets.MARK_ID");
               if (id.equals(cid)) {
                  return c2;
               }
            }
         }

         if (hasDynamicComponents) {
            UIComponent foundChild = findChildByTagId(context, c, id);
            if (foundChild != null) {
               return foundChild;
            }
         }
      }

      return null;
   }

   public static Locale getLocale(FaceletContext ctx, TagAttribute attr) throws TagAttributeException {
      Object obj = attr.getObject(ctx);
      if (obj instanceof Locale) {
         return (Locale)obj;
      } else if (obj instanceof String) {
         String s = (String)obj;

         try {
            return Util.getLocaleFromString(s);
         } catch (IllegalArgumentException var5) {
            throw new TagAttributeException(attr, "Invalid Locale Specified: " + s);
         }
      } else {
         throw new TagAttributeException(attr, "Attribute did not evaluate to a String or Locale: " + obj);
      }
   }

   public static UIViewRoot getViewRoot(FaceletContext ctx, UIComponent parent) {
      UIComponent c = parent;

      while(!(c instanceof UIViewRoot)) {
         c = c.getParent();
         if (c == null) {
            return ctx.getFacesContext().getViewRoot();
         }
      }

      return (UIViewRoot)c;
   }

   public static void markForDeletion(UIComponent c) {
      c.getAttributes().put("com.sun.faces.facelets.MARK_DELETED", Boolean.TRUE);
      int sz = c.getChildCount();
      if (sz > 0) {
         UIComponent cc = null;
         List cl = c.getChildren();

         while(true) {
            --sz;
            if (sz < 0) {
               break;
            }

            cc = (UIComponent)cl.get(sz);
            if (cc.getAttributes().containsKey("com.sun.faces.facelets.MARK_ID")) {
               cc.getAttributes().put("com.sun.faces.facelets.MARK_DELETED", Boolean.TRUE);
            }
         }
      }

      if (c.getFacets().size() > 0) {
         Set col = c.getFacets().entrySet();
         Iterator itr = col.iterator();

         while(true) {
            while(itr.hasNext()) {
               Map.Entry entry = (Map.Entry)itr.next();
               String facet = (String)entry.getKey();
               UIComponent fc = (UIComponent)entry.getValue();
               Map attrs = fc.getAttributes();
               if (attrs.containsKey("com.sun.faces.facelets.MARK_ID")) {
                  attrs.put("com.sun.faces.facelets.MARK_DELETED", Boolean.TRUE);
               } else {
                  List implicitPanelChildren;
                  if ("javax.faces.component.COMPOSITE_FACET_NAME".equals(facet)) {
                     sz = fc.getChildCount();
                     if (sz > 0) {
                        implicitPanelChildren = null;
                        List cl = fc.getChildren();

                        while(true) {
                           --sz;
                           if (sz < 0) {
                              break;
                           }

                           UIComponent cc = (UIComponent)cl.get(sz);
                           cc.getAttributes().put("com.sun.faces.facelets.MARK_DELETED", Boolean.TRUE);
                        }
                     }
                  } else if (attrs.containsKey("com.sun.faces.facelets.IMPLICIT_PANEL")) {
                     implicitPanelChildren = fc.getChildren();
                     Map innerAttrs = null;
                     Iterator var10 = implicitPanelChildren.iterator();

                     while(var10.hasNext()) {
                        UIComponent cur = (UIComponent)var10.next();
                        innerAttrs = cur.getAttributes();
                        if (innerAttrs.containsKey("com.sun.faces.facelets.MARK_ID")) {
                           innerAttrs.put("com.sun.faces.facelets.MARK_DELETED", Boolean.TRUE);
                        }
                     }
                  }
               }
            }

            return;
         }
      }
   }

   public static void encodeRecursive(FacesContext context, UIComponent viewToRender) throws IOException, FacesException {
      if (viewToRender.isRendered()) {
         viewToRender.encodeBegin(context);
         if (viewToRender.getRendersChildren()) {
            viewToRender.encodeChildren(context);
         } else if (viewToRender.getChildCount() > 0) {
            Iterator kids = viewToRender.getChildren().iterator();

            while(kids.hasNext()) {
               UIComponent kid = (UIComponent)kids.next();
               encodeRecursive(context, kid);
            }
         }

         viewToRender.encodeEnd(context);
      }

   }

   public static void removeTransient(UIComponent c) {
      UIComponent d;
      Iterator itr;
      if (c.getChildCount() > 0) {
         itr = c.getChildren().iterator();

         while(itr.hasNext()) {
            d = (UIComponent)itr.next();
            if (d.getFacets().size() > 0) {
               Iterator jtr = d.getFacets().values().iterator();

               while(jtr.hasNext()) {
                  UIComponent e = (UIComponent)jtr.next();
                  if (e.isTransient()) {
                     jtr.remove();
                  } else {
                     removeTransient(e);
                  }
               }
            }

            if (d.isTransient()) {
               itr.remove();
            } else {
               removeTransient(d);
            }
         }
      }

      if (c.getFacets().size() > 0) {
         itr = c.getFacets().values().iterator();

         while(itr.hasNext()) {
            d = (UIComponent)itr.next();
            if (d.isTransient()) {
               itr.remove();
            } else {
               removeTransient(d);
            }
         }
      }

   }

   public static void addComponent(FaceletContext ctx, UIComponent parent, UIComponent child) {
      String facetName = getFacetName(parent);
      if (facetName == null) {
         if (child.getAttributes().containsKey("com.sun.faces.DynamicComponent")) {
            int childIndex = (Integer)child.getAttributes().get("com.sun.faces.DynamicComponent");
            if (childIndex < parent.getChildCount() && childIndex != -1) {
               parent.getChildren().add(childIndex, child);
            } else {
               parent.getChildren().add(child);
            }
         } else {
            parent.getChildren().add(child);
         }
      } else {
         UIComponent existing = (UIComponent)parent.getFacets().get(facetName);
         if (existing != null && existing != child) {
            if (existing.getAttributes().get("com.sun.faces.facelets.IMPLICIT_PANEL") == null) {
               UIComponent panelGroup = ctx.getFacesContext().getApplication().createComponent("javax.faces.Panel");
               parent.getFacets().put(facetName, panelGroup);
               Map attrs = panelGroup.getAttributes();
               attrs.put("com.sun.faces.facelets.IMPLICIT_PANEL", true);
               panelGroup.getChildren().add(existing);
               existing = panelGroup;
            }

            if (existing.getAttributes().get("com.sun.faces.facelets.IMPLICIT_PANEL") != null) {
               existing.getChildren().add(child);
            } else {
               parent.getFacets().put(facetName, child);
            }
         } else {
            parent.getFacets().put(facetName, child);
         }
      }

   }

   public static String getFacetName(UIComponent parent) {
      return (String)parent.getAttributes().get("facelets.FACET_NAME");
   }

   public static boolean suppressViewModificationEvents(FacesContext ctx) {
      UIViewRoot root = ctx.getViewRoot();
      if (root != null) {
         String viewId = root.getViewId();
         if (viewId != null) {
            StateContext stateCtx = StateContext.getStateContext(ctx);
            return stateCtx.isPartialStateSaving(ctx, viewId);
         }
      }

      return false;
   }

   public static void copyPassthroughAttributes(FaceletContext ctx, UIComponent c, Tag t) {
      if (null != c && null != t) {
         TagAttribute[] passthroughAttrs = t.getAttributes().getAll("http://xmlns.jcp.org/jsf/passthrough");
         if (null != passthroughAttrs && 0 < passthroughAttrs.length) {
            Map componentPassthroughAttrs = c.getPassThroughAttributes(true);
            Object attrValue = null;
            TagAttribute[] var6 = passthroughAttrs;
            int var7 = passthroughAttrs.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               TagAttribute cur = var6[var8];
               attrValue = cur.isLiteral() ? cur.getValue(ctx) : cur.getValueExpression(ctx, Object.class);
               componentPassthroughAttrs.put(cur.getLocalName(), attrValue);
            }
         }

      }
   }
}
