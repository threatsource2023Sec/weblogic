package org.glassfish.grizzly.attributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultAttributeBuilder implements AttributeBuilder {
   protected final List attributes = new ArrayList();
   protected final Map name2Attribute = new HashMap();

   public synchronized Attribute createAttribute(String name) {
      return this.createAttribute(name, (Object)null);
   }

   public synchronized Attribute createAttribute(String name, Object defaultValue) {
      Attribute attribute = (Attribute)this.name2Attribute.get(name);
      if (attribute == null) {
         attribute = new Attribute(this, name, this.attributes.size(), defaultValue);
         this.attributes.add(attribute);
         this.name2Attribute.put(name, attribute);
      }

      return attribute;
   }

   public synchronized Attribute createAttribute(String name, org.glassfish.grizzly.utils.NullaryFunction initializer) {
      Attribute attribute = (Attribute)this.name2Attribute.get(name);
      if (attribute == null) {
         attribute = new Attribute(this, name, this.attributes.size(), initializer);
         this.attributes.add(attribute);
         this.name2Attribute.put(name, attribute);
      }

      return attribute;
   }

   public Attribute createAttribute(String name, final NullaryFunction initializer) {
      return this.createAttribute(name, initializer == null ? null : new org.glassfish.grizzly.utils.NullaryFunction() {
         public Object evaluate() {
            return initializer.evaluate();
         }
      });
   }

   public AttributeHolder createSafeAttributeHolder() {
      return new IndexedAttributeHolder(this);
   }

   public AttributeHolder createUnsafeAttributeHolder() {
      return new UnsafeAttributeHolder(this);
   }

   protected Attribute getAttributeByName(String name) {
      return (Attribute)this.name2Attribute.get(name);
   }

   protected Attribute getAttributeByIndex(int index) {
      return (Attribute)this.attributes.get(index);
   }
}
