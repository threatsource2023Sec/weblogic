package com.googlecode.cqengine.query.simple;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;

public abstract class SimpleQuery implements Query {
   protected final boolean attributeIsSimple;
   protected final Attribute attribute;
   protected final SimpleAttribute simpleAttribute;
   private transient int cachedHashCode = 0;

   public SimpleQuery(Attribute attribute) {
      if (attribute == null) {
         throw new IllegalArgumentException("The attribute argument was null.");
      } else {
         this.attribute = attribute;
         if (attribute instanceof SimpleAttribute) {
            this.attributeIsSimple = true;
            this.simpleAttribute = (SimpleAttribute)attribute;
         } else {
            this.attributeIsSimple = false;
            this.simpleAttribute = null;
         }

      }
   }

   public Class getAttributeType() {
      return this.attribute.getAttributeType();
   }

   public String getAttributeName() {
      return this.attribute.getAttributeName();
   }

   public Attribute getAttribute() {
      return this.attribute;
   }

   public final boolean matches(Object object, QueryOptions queryOptions) {
      return this.attributeIsSimple ? this.matchesSimpleAttribute(this.simpleAttribute, object, queryOptions) : this.matchesNonSimpleAttribute(this.attribute, object, queryOptions);
   }

   protected abstract boolean matchesSimpleAttribute(SimpleAttribute var1, Object var2, QueryOptions var3);

   protected abstract boolean matchesNonSimpleAttribute(Attribute var1, Object var2, QueryOptions var3);

   public int hashCode() {
      int h = this.cachedHashCode;
      if (h == 0) {
         h = this.calcHashCode();
         if (h == 0) {
            h = -1838660945;
         }

         this.cachedHashCode = h;
      }

      return h;
   }

   protected abstract int calcHashCode();

   protected static String asLiteral(Object value) {
      return value instanceof String ? "\"" + value + "\"" : String.valueOf(value);
   }

   protected static String asLiteral(String value) {
      return "\"" + value + "\"";
   }
}
