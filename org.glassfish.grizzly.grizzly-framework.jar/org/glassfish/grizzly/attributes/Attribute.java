package org.glassfish.grizzly.attributes;

public final class Attribute {
   private final AttributeBuilder builder;
   private final String name;
   private final org.glassfish.grizzly.utils.NullaryFunction initializer;
   private final int attributeIndex;

   public String toString() {
      return "Attribute[" + this.name + ':' + this.attributeIndex + ']';
   }

   protected Attribute(AttributeBuilder builder, String name, int index, final Object defaultValue) {
      this(builder, name, index, new org.glassfish.grizzly.utils.NullaryFunction() {
         public Object evaluate() {
            return defaultValue;
         }
      });
   }

   protected Attribute(AttributeBuilder builder, String name, int index, org.glassfish.grizzly.utils.NullaryFunction initializer) {
      this.builder = builder;
      this.name = name;
      this.attributeIndex = index;
      this.initializer = initializer;
   }

   public Object peek(AttributeHolder attributeHolder) {
      return this.get0(attributeHolder, (org.glassfish.grizzly.utils.NullaryFunction)null);
   }

   public Object peek(AttributeStorage storage) {
      AttributeHolder holder = storage.getAttributes();
      return holder != null ? this.peek(holder) : null;
   }

   public Object get(AttributeHolder attributeHolder) {
      return this.get0(attributeHolder, this.initializer);
   }

   public Object get(AttributeStorage storage) {
      return this.get(storage.getAttributes());
   }

   public void set(AttributeHolder attributeHolder, Object value) {
      IndexedAttributeAccessor indexedAccessor = attributeHolder.getIndexedAttributeAccessor();
      if (indexedAccessor != null) {
         indexedAccessor.setAttribute(this.attributeIndex, value);
      } else {
         attributeHolder.setAttribute(this.name, value);
      }

   }

   public void set(AttributeStorage storage, Object value) {
      this.set(storage.getAttributes(), value);
   }

   public Object remove(AttributeHolder attributeHolder) {
      IndexedAttributeAccessor indexedAccessor = attributeHolder.getIndexedAttributeAccessor();
      return indexedAccessor != null ? indexedAccessor.removeAttribute(this.attributeIndex) : attributeHolder.removeAttribute(this.name);
   }

   public Object remove(AttributeStorage storage) {
      AttributeHolder holder = storage.getAttributes();
      return holder != null ? this.remove(holder) : null;
   }

   public boolean isSet(AttributeHolder attributeHolder) {
      return this.get0(attributeHolder, (org.glassfish.grizzly.utils.NullaryFunction)null) != null;
   }

   public boolean isSet(AttributeStorage storage) {
      AttributeHolder holder = storage.getAttributes();
      return holder != null && this.isSet(holder);
   }

   public String name() {
      return this.name;
   }

   public int index() {
      return this.attributeIndex;
   }

   private Object get0(AttributeHolder attributeHolder, org.glassfish.grizzly.utils.NullaryFunction initializer) {
      IndexedAttributeAccessor indexedAccessor = attributeHolder.getIndexedAttributeAccessor();
      return indexedAccessor != null ? indexedAccessor.getAttribute(this.attributeIndex, initializer) : attributeHolder.getAttribute(this.name, initializer);
   }
}
