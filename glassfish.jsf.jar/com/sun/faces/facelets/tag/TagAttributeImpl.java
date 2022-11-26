package com.sun.faces.facelets.tag;

import com.sun.faces.el.ELUtils;
import com.sun.faces.facelets.el.ContextualCompositeMethodExpression;
import com.sun.faces.facelets.el.ContextualCompositeValueExpression;
import com.sun.faces.facelets.el.ELText;
import com.sun.faces.facelets.el.TagMethodExpression;
import com.sun.faces.facelets.el.TagValueExpression;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.view.Location;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributeException;

public class TagAttributeImpl extends TagAttribute {
   private final boolean literal;
   private final String localName;
   private final Location location;
   private final String namespace;
   private final String qName;
   private final String value;
   private String string;
   private Tag tag;

   public TagAttributeImpl() {
      this.literal = false;
      this.localName = null;
      this.location = null;
      this.namespace = null;
      this.qName = null;
      this.value = null;
      this.string = null;
      this.tag = null;
   }

   public TagAttributeImpl(Location location, String ns, String localName, String qName, String value) {
      this.location = location;
      this.namespace = ns;
      this.localName = null != localName && 0 != localName.length() ? localName : qName;
      this.qName = qName;
      this.value = value;

      try {
         this.literal = ELText.isLiteral(this.value);
      } catch (ELException var7) {
         throw new TagAttributeException(this, var7);
      }
   }

   public boolean getBoolean(FaceletContext ctx) {
      if (this.literal) {
         return Boolean.valueOf(this.value);
      } else {
         Boolean bool = (Boolean)this.getObject(ctx, Boolean.class);
         if (bool == null) {
            bool = false;
         }

         return bool;
      }
   }

   public int getInt(FaceletContext ctx) {
      return this.literal ? Integer.parseInt(this.value) : ((Number)this.getObject(ctx, Integer.class)).intValue();
   }

   public String getLocalName() {
      return this.localName;
   }

   public Location getLocation() {
      return this.location;
   }

   public MethodExpression getMethodExpression(FaceletContext ctx, Class type, Class[] paramTypes) {
      try {
         ExpressionFactory f = ctx.getExpressionFactory();
         if (ELUtils.isCompositeComponentLookupWithArgs(this.value)) {
            String message = MessageUtils.getExceptionMessageString("com.sun.faces.ARGUMENTS_NOT_LEGAL_WITH_CC_ATTRS_EXPR");
            throw new TagAttributeException(this, message);
         } else {
            Object result;
            if (ELUtils.isCompositeComponentMethodExprLookup(this.value)) {
               result = new AttributeLookupMethodExpression(this.getValueExpression(ctx, MethodExpression.class));
            } else if (ELUtils.isCompositeComponentExpr(this.value)) {
               MethodExpression delegate = new TagMethodExpression(this, f.createMethodExpression(ctx, this.value, type, paramTypes));
               result = new ContextualCompositeMethodExpression(this.getLocation(), delegate);
            } else {
               result = new TagMethodExpression(this, f.createMethodExpression(ctx, this.value, type, paramTypes));
            }

            return (MethodExpression)result;
         }
      } catch (Exception var7) {
         if (var7 instanceof TagAttributeException) {
            throw (TagAttributeException)var7;
         } else {
            throw new TagAttributeException(this, var7);
         }
      }
   }

   public String getNamespace() {
      return this.namespace;
   }

   public Object getObject(FaceletContext ctx) {
      return this.getObject(ctx, Object.class);
   }

   public String getQName() {
      return this.qName;
   }

   public Tag getTag() {
      return this.tag;
   }

   public void setTag(Tag tag) {
      this.tag = tag;
   }

   public String getValue() {
      return this.value;
   }

   public String getValue(FaceletContext ctx) {
      return this.literal ? this.value : (String)this.getObject(ctx, String.class);
   }

   public Object getObject(FaceletContext ctx, Class type) {
      if (this.literal) {
         if (String.class.equals(type)) {
            return this.value;
         } else {
            try {
               return ctx.getExpressionFactory().coerceToType(this.value, type);
            } catch (Exception var5) {
               throw new TagAttributeException(this, var5);
            }
         }
      } else {
         ValueExpression ve = this.getValueExpression(ctx, type);

         try {
            return ve.getValue(ctx);
         } catch (Exception var6) {
            throw new TagAttributeException(this, var6);
         }
      }
   }

   public ValueExpression getValueExpression(FaceletContext ctx, Class type) {
      return this.getValueExpression(ctx, this.value, type);
   }

   public boolean isLiteral() {
      return this.literal;
   }

   public String toString() {
      if (this.string == null) {
         this.string = this.location + " " + this.qName + "=\"" + this.value + "\"";
      }

      return this.string;
   }

   public ValueExpression getValueExpression(FaceletContext ctx, String expr, Class type) {
      try {
         ExpressionFactory f = ctx.getExpressionFactory();
         ValueExpression delegate = f.createValueExpression(ctx, expr, type);
         if (ELUtils.isCompositeComponentExpr(expr)) {
            if (ELUtils.isCompositeComponentLookupWithArgs(expr)) {
               String message = MessageUtils.getExceptionMessageString("com.sun.faces.ARGUMENTS_NOT_LEGAL_WITH_CC_ATTRS_EXPR");
               throw new TagAttributeException(this, message);
            } else {
               return new TagValueExpression(this, new ContextualCompositeValueExpression(this.getLocation(), delegate));
            }
         } else {
            return new TagValueExpression(this, delegate);
         }
      } catch (Exception var7) {
         throw new TagAttributeException(this, var7);
      }
   }

   private static class AttributeLookupMethodExpression extends MethodExpression {
      private static final long serialVersionUID = -8983924930720420664L;
      private ValueExpression lookupExpression;

      public AttributeLookupMethodExpression(ValueExpression lookupExpression) {
         Util.notNull("lookupExpression", lookupExpression);
         this.lookupExpression = lookupExpression;
      }

      public AttributeLookupMethodExpression() {
      }

      public MethodInfo getMethodInfo(ELContext elContext) {
         Util.notNull("elContext", elContext);
         Object result = this.lookupExpression.getValue(elContext);
         return result != null && result instanceof MethodExpression ? ((MethodExpression)result).getMethodInfo(elContext) : null;
      }

      public Object invoke(ELContext elContext, Object[] args) {
         Util.notNull("elContext", elContext);
         Object result = this.lookupExpression.getValue(elContext);
         if (result == null) {
            throw new FacesException("Unable to resolve composite component from using page using EL expression '" + this.lookupExpression.getExpressionString() + '\'');
         } else if (!(result instanceof MethodExpression)) {
            throw new FacesException("Successfully resolved expression '" + this.lookupExpression.getExpressionString() + "', but the value is not a MethodExpression");
         } else {
            return ((MethodExpression)result).invoke(elContext, args);
         }
      }

      public String getExpressionString() {
         return this.lookupExpression.getExpressionString();
      }

      public boolean equals(Object otherObj) {
         boolean result = false;
         if (otherObj instanceof AttributeLookupMethodExpression) {
            AttributeLookupMethodExpression other = (AttributeLookupMethodExpression)otherObj;
            result = this.lookupExpression.getExpressionString().equals(other.lookupExpression.getExpressionString());
         }

         return result;
      }

      public boolean isLiteralText() {
         return this.lookupExpression.isLiteralText();
      }

      public int hashCode() {
         return this.lookupExpression.hashCode();
      }
   }
}
