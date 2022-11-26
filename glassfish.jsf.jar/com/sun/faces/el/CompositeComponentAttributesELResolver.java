package com.sun.faces.el;

import com.sun.faces.component.CompositeComponentStackManager;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.CompositeComponentExpressionHolder;

public class CompositeComponentAttributesELResolver extends ELResolver {
   private static final Logger LOGGER;
   private static final String COMPOSITE_COMPONENT_ATTRIBUTES_NAME = "attrs";
   private static final String COMPOSITE_COMPONENT_PARENT_NAME = "parent";
   private static final String EVAL_MAP_KEY;

   public Object getValue(ELContext context, Object base, Object property) {
      Util.notNull("context", context);
      if (base != null && base instanceof UIComponent && UIComponent.isCompositeComponent((UIComponent)base) && property != null) {
         String propertyName = property.toString();
         UIComponent c;
         FacesContext ctx;
         if ("attrs".equals(propertyName)) {
            c = (UIComponent)base;
            context.setPropertyResolved(true);
            ctx = (FacesContext)context.getContext(FacesContext.class);
            return this.getEvalMapFor(c, ctx);
         }

         if ("parent".equals(propertyName)) {
            c = (UIComponent)base;
            context.setPropertyResolved(true);
            ctx = (FacesContext)context.getContext(FacesContext.class);
            CompositeComponentStackManager m = CompositeComponentStackManager.getManager(ctx);
            UIComponent ccp = m.getParentCompositeComponent(CompositeComponentStackManager.StackType.TreeCreation, ctx, c);
            if (ccp == null) {
               ccp = m.getParentCompositeComponent(CompositeComponentStackManager.StackType.Evaluation, ctx, c);
            }

            return ccp;
         }
      }

      return null;
   }

   public Class getType(ELContext context, Object base, Object property) {
      Util.notNull("context", context);
      if (base instanceof ExpressionEvalMap && property instanceof String) {
         Class exprType = null;
         Class metaType = null;
         ExpressionEvalMap evalMap = (ExpressionEvalMap)base;
         ValueExpression ve = evalMap.getExpression((String)property);
         if (ve != null) {
            exprType = ve.getType(context);
         }

         if (!"".equals(property)) {
            FacesContext facesContext = (FacesContext)context.getContext(FacesContext.class);
            UIComponent cc = UIComponent.getCurrentCompositeComponent(facesContext);
            BeanInfo metadata = (BeanInfo)cc.getAttributes().get("javax.faces.component.BEANINFO_KEY");

            assert null != metadata;

            PropertyDescriptor[] attributes = metadata.getPropertyDescriptors();
            if (null != attributes) {
               PropertyDescriptor[] var12 = attributes;
               int var13 = attributes.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  PropertyDescriptor cur = var12[var14];
                  if (property.equals(cur.getName())) {
                     Object type = cur.getValue("type");
                     if (null != type) {
                        assert type instanceof Class;

                        metaType = (Class)type;
                        break;
                     }
                  }
               }
            }
         }

         if (metaType == null || exprType != null && !exprType.isAssignableFrom(metaType)) {
            return exprType;
         } else {
            context.setPropertyResolved(true);
            return metaType;
         }
      } else {
         return null;
      }
   }

   public void setValue(ELContext context, Object base, Object property, Object value) {
      Util.notNull("context", context);
   }

   public boolean isReadOnly(ELContext context, Object base, Object property) {
      Util.notNull("context", context);
      return true;
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      Util.notNull("context", context);
      return null;
   }

   public Class getCommonPropertyType(ELContext context, Object base) {
      Util.notNull("context", context);
      return String.class;
   }

   public Map getEvalMapFor(UIComponent c, FacesContext ctx) {
      Map ctxAttributes = ctx.getAttributes();
      Map topMap = (Map)ctxAttributes.get(EVAL_MAP_KEY);
      Map evalMap = null;
      if (topMap == null) {
         Map topMap = new HashMap();
         ctxAttributes.put(EVAL_MAP_KEY, topMap);
         evalMap = new ExpressionEvalMap(ctx, c);
         topMap.put(c, evalMap);
      } else {
         evalMap = (Map)topMap.get(c);
         if (evalMap == null) {
            evalMap = new ExpressionEvalMap(ctx, c);
            topMap.put(c, evalMap);
         } else {
            ((ExpressionEvalMap)evalMap).updateFacesContext(ctx);
         }
      }

      return (Map)evalMap;
   }

   static {
      LOGGER = FacesLogger.CONTEXT.getLogger();
      EVAL_MAP_KEY = CompositeComponentAttributesELResolver.class.getName() + "_EVAL_MAP";
   }

   private static final class ExpressionEvalMap implements Map, CompositeComponentExpressionHolder {
      private Map attributesMap;
      private PropertyDescriptor[] declaredAttributes;
      private Map declaredDefaultValues;
      private FacesContext ctx;
      private UIComponent cc;

      ExpressionEvalMap(FacesContext ctx, UIComponent cc) {
         this.cc = cc;
         this.attributesMap = cc.getAttributes();
         BeanInfo metadata = (BeanInfo)this.attributesMap.get("javax.faces.component.BEANINFO_KEY");
         if (null != metadata) {
            this.declaredAttributes = metadata.getPropertyDescriptors();
            this.declaredDefaultValues = new HashMap(5);
         }

         this.ctx = ctx;
      }

      public ValueExpression getExpression(String name) {
         Object ve = this.cc.getValueExpression(name);
         return ve instanceof ValueExpression ? (ValueExpression)ve : null;
      }

      public int size() {
         throw new UnsupportedOperationException();
      }

      public boolean isEmpty() {
         throw new UnsupportedOperationException();
      }

      public boolean containsKey(Object key) {
         boolean result = this.attributesMap.containsKey(key);
         if (!result) {
            result = null != this.getDeclaredDefaultValue(key);
         }

         return result;
      }

      public boolean containsValue(Object value) {
         throw new UnsupportedOperationException();
      }

      public Object get(Object key) {
         Object v = this.attributesMap.get(key);
         if (v == null) {
            v = this.getDeclaredDefaultValue(key);
            if (v != null) {
               return ((ValueExpression)v).getValue(this.ctx.getELContext());
            }
         }

         return v != null && v instanceof MethodExpression ? v : v;
      }

      public Object put(String key, Object value) {
         ValueExpression ve = this.cc.getValueExpression(key);
         if (ve != null) {
            ve.setValue(this.ctx.getELContext(), value);
         } else {
            this.attributesMap.put(key, value);
         }

         return null;
      }

      public Object remove(Object key) {
         throw new UnsupportedOperationException();
      }

      public void putAll(Map t) {
         throw new UnsupportedOperationException();
      }

      public void clear() {
         throw new UnsupportedOperationException();
      }

      public Set keySet() {
         throw new UnsupportedOperationException();
      }

      public Collection values() {
         throw new UnsupportedOperationException();
      }

      public Set entrySet() {
         throw new UnsupportedOperationException();
      }

      private Object getDeclaredDefaultValue(Object key) {
         Object result = null;
         if (!this.declaredDefaultValues.containsKey(key)) {
            boolean found = false;
            PropertyDescriptor[] var4 = this.declaredAttributes;
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               PropertyDescriptor cur = var4[var6];
               if (cur.getName().equals(key)) {
                  found = true;
                  this.declaredDefaultValues.put(key, result = cur.getValue("default"));
                  break;
               }
            }

            if (!found) {
               this.declaredDefaultValues.put(key, (Object)null);
            }
         } else {
            result = this.declaredDefaultValues.get(key);
         }

         return result;
      }

      public void updateFacesContext(FacesContext ctx) {
         if (this.ctx != ctx) {
            this.ctx = ctx;
         }

      }
   }
}
