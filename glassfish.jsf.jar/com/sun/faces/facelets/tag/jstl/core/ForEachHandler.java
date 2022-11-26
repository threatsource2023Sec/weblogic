package com.sun.faces.facelets.tag.jstl.core;

import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.facelets.tag.jsf.IterationIdManager;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.el.ValueExpression;
import javax.el.VariableMapper;
import javax.faces.component.UIComponent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;
import javax.faces.view.facelets.TagConfig;

public final class ForEachHandler extends TagHandlerImpl {
   private final TagAttribute begin = this.getAttribute("begin");
   private final TagAttribute end = this.getAttribute("end");
   private final TagAttribute items = this.getAttribute("items");
   private final TagAttribute step = this.getAttribute("step");
   private final TagAttribute tranzient = this.getAttribute("transient");
   private final TagAttribute var = this.getAttribute("var");
   private final TagAttribute varStatus = this.getAttribute("varStatus");

   public ForEachHandler(TagConfig config) {
      super(config);
      if (this.items == null && this.begin != null && this.end == null) {
         throw new TagAttributeException(this.tag, this.begin, "If the 'items' attribute is not specified, but the 'begin' attribute is, then the 'end' attribute is required");
      }
   }

   public void apply(FaceletContext ctx, UIComponent parent) throws IOException {
      int s = this.getBegin(ctx);
      int e = this.getEnd(ctx);
      int m = this.getStep(ctx);
      Integer sO = this.begin != null ? s : null;
      Integer eO = this.end != null ? e : null;
      Integer mO = this.step != null ? m : null;
      boolean t = this.getTransient(ctx);
      Object src = null;
      ValueExpression srcVE = null;
      int i;
      if (this.items != null) {
         srcVE = this.items.getValueExpression(ctx, Object.class);
         src = srcVE.getValue(ctx);
      } else {
         byte[] b = new byte[e + 1];

         for(i = 0; i < b.length; ++i) {
            b[i] = (byte)i;
         }

         src = b;
      }

      if (src != null) {
         Iterator itr = this.toIterator(src);
         if (itr != null) {
            for(i = 0; i < s && itr.hasNext(); ++i) {
               itr.next();
            }

            String v = this.getVarName(ctx);
            String vs = this.getVarStatusName(ctx);
            VariableMapper vars = ctx.getVariableMapper();
            ValueExpression ve = null;
            ValueExpression vO = this.capture(v, vars);
            ValueExpression vsO = this.capture(vs, vars);
            int mi = false;
            Object value = null;
            int count = 0;
            IterationIdManager.startIteration(ctx);

            try {
               for(boolean first = true; i <= e && itr.hasNext(); first = false) {
                  ++count;
                  value = itr.next();
                  if (v != null) {
                     if (!t && srcVE != null) {
                        ve = this.getVarExpr(srcVE, src, value, i, s);
                        vars.setVariable(v, ve);
                     } else {
                        ctx.setAttribute(v, value);
                     }
                  }

                  if (vs != null) {
                     JstlIterationStatus itrS = new JstlIterationStatus(first, !itr.hasNext(), i, sO, eO, mO, value, count);
                     if (!t && srcVE != null) {
                        ValueExpression ve = new IterationStatusExpression(itrS);
                        vars.setVariable(vs, ve);
                     } else {
                        ctx.setAttribute(vs, itrS);
                     }
                  }

                  this.nextHandler.apply(ctx, parent);

                  for(int mi = 1; mi < m && itr.hasNext(); ++i) {
                     itr.next();
                     ++mi;
                  }

                  ++i;
               }
            } finally {
               if (v != null) {
                  vars.setVariable(v, vO);
               }

               if (vs != null) {
                  vars.setVariable(vs, vsO);
               }

               IterationIdManager.stopIteration(ctx);
            }
         }
      }

   }

   private ValueExpression capture(String name, VariableMapper vars) {
      return name != null ? vars.setVariable(name, (ValueExpression)null) : null;
   }

   private int getBegin(FaceletContext ctx) {
      return this.begin != null ? this.begin.getInt(ctx) : 0;
   }

   private int getEnd(FaceletContext ctx) {
      return this.end != null ? this.end.getInt(ctx) : 2147483646;
   }

   private int getStep(FaceletContext ctx) {
      return this.step != null ? this.step.getInt(ctx) : 1;
   }

   private boolean getTransient(FaceletContext ctx) {
      return this.tranzient != null ? this.tranzient.getBoolean(ctx) : false;
   }

   private ValueExpression getVarExpr(ValueExpression ve, Object src, Object value, int i, int start) {
      if (!(src instanceof List) && !src.getClass().isArray()) {
         if (src instanceof Map && value instanceof Map.Entry) {
            return new MappedValueExpression(ve, (Map.Entry)value);
         } else if (src instanceof Collection) {
            return new IteratedValueExpression(ve, start, i);
         } else {
            throw new IllegalStateException("Cannot create VE for: " + src);
         }
      } else {
         return new IndexedValueExpression(ve, i);
      }
   }

   private String getVarName(FaceletContext ctx) {
      return this.var != null ? this.var.getValue(ctx) : null;
   }

   private String getVarStatusName(FaceletContext ctx) {
      return this.varStatus != null ? this.varStatus.getValue(ctx) : null;
   }

   private Iterator toIterator(Object src) {
      if (src == null) {
         return null;
      } else if (src instanceof Collection) {
         return ((Collection)src).iterator();
      } else if (src instanceof Map) {
         return ((Map)src).entrySet().iterator();
      } else if (src.getClass().isArray()) {
         return new ArrayIterator(src);
      } else {
         throw new TagAttributeException(this.tag, this.items, "Must evaluate to a Collection, Map, Array, or null.");
      }
   }

   private static class ArrayIterator implements Iterator {
      protected final Object array;
      protected int i = 0;
      protected final int len;

      public ArrayIterator(Object src) {
         this.array = src;
         this.len = Array.getLength(src);
      }

      public boolean hasNext() {
         return this.i < this.len;
      }

      public Object next() {
         try {
            return Array.get(this.array, this.i++);
         } catch (ArrayIndexOutOfBoundsException var2) {
            throw new NoSuchElementException();
         }
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
}
