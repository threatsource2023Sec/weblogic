package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.WrappedArrayType;
import com.bea.staxb.runtime.internal.util.ArrayUtils;
import com.bea.staxb.runtime.internal.util.collections.Accumulator;
import com.bea.staxb.runtime.internal.util.collections.AccumulatorFactory;
import com.bea.xml.XmlException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import javax.xml.namespace.QName;

final class WrappedArrayRuntimeBindingType extends RuntimeBindingType {
   private final WrappedArrayType wrappedArrayType;
   private ItemProperty elementProperty;
   private final boolean canUseDefaultNS;
   private final boolean isCollection;
   private static final int NS_PREDEFINE_THRESHOLD = 2;

   WrappedArrayRuntimeBindingType(WrappedArrayType binding_type) throws XmlException {
      super(binding_type);
      this.wrappedArrayType = binding_type;
      this.canUseDefaultNS = !"".equals(binding_type.getItemName().getNamespaceURI());
      this.isCollection = Collection.class.isAssignableFrom(this.getJavaType());
   }

   void accept(RuntimeTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) throws XmlException {
      BindingTypeName item_type_name = this.wrappedArrayType.getItemType();

      assert item_type_name != null;

      BindingType item_type = bindingLoader.getBindingType(item_type_name);
      if (item_type == null) {
         String msg = "unable to lookup " + item_type_name + " from type " + this.wrappedArrayType;
         throw new XmlException(msg);
      } else {
         RuntimeBindingType item_rtt = typeTable.createRuntimeType(item_type, bindingLoader);
         this.elementProperty = new ItemProperty(this, this.wrappedArrayType.getItemName(), item_rtt, this.wrappedArrayType.isItemNillable());
      }
   }

   boolean isJavaCollection() {
      return this.isCollection;
   }

   protected boolean needsXsiType(RuntimeBindingType expected) {
      if (this == expected) {
         return false;
      } else {
         return expected.isJavaCollection() && this.isJavaCollection() ? false : super.needsXsiType(expected);
      }
   }

   protected boolean canUseDefaultNamespace(Object obj) throws XmlException {
      return this.canUseDefaultNS;
   }

   boolean hasElementChildren() {
      return true;
   }

   ItemProperty getElementProperty() {
      assert this.elementProperty != null;

      return this.elementProperty;
   }

   protected Object createIntermediary(UnmarshalResult context) {
      return AccumulatorFactory.createAccumulator(this.getJavaType(), this.elementProperty.getElementClass());
   }

   protected Object createIntermediary(UnmarshalResult context, Object actual_object) {
      String e = "factories not supported for array types: " + this.getBindingType();
      throw new UnsupportedOperationException(e);
   }

   protected Object getFinalObjectFromIntermediary(Object inter, UnmarshalResult context) throws XmlException {
      Accumulator acc = (Accumulator)inter;
      return acc.getFinalArray();
   }

   boolean isObjectFromIntermediateIdempotent() {
      return false;
   }

   void predefineNamespaces(Object instance, MarshalResult result) throws XmlException {
      String uri = this.elementProperty.getName().getNamespaceURI();
      if (!"".equals(uri)) {
         if (hasSignificantContent(instance)) {
            result.ensureElementPrefix(uri);
         }
      }
   }

   static boolean hasSignificantContent(Object instance) {
      Iterator itr = ArrayUtils.getCollectionIterator(instance);
      int cnt = 0;

      while(itr.hasNext()) {
         ++cnt;
         if (cnt >= 2) {
            return true;
         }

         itr.next();
      }

      return false;
   }

   static final class ItemProperty extends RuntimeBindingProperty {
      private final WrappedArrayRuntimeBindingType containingType;
      private final QName itemName;
      private final RuntimeBindingType itemType;
      private final boolean nillable;

      ItemProperty(WrappedArrayRuntimeBindingType containing_type, QName item_name, RuntimeBindingType item_type, boolean nillable) throws XmlException {
         super(containing_type);
         this.containingType = containing_type;
         this.itemName = item_name;
         this.itemType = item_type;
         this.nillable = nillable;
      }

      Class getElementClass() {
         return this.itemType.getJavaType();
      }

      RuntimeBindingType getRuntimeBindingType() {
         return this.itemType;
      }

      QName getName() {
         return this.itemName;
      }

      RuntimeBindingType getContainingType() {
         return this.containingType;
      }

      void setValue(Object target, Object prop_obj) throws XmlException {
         throw new AssertionError("not used");
      }

      public void fill(Object inter, Object prop_obj) throws XmlException {
         Accumulator acc = (Accumulator)inter;
         acc.append(prop_obj);
      }

      protected void fillPlaceholder(Object inter) {
         Accumulator acc = (Accumulator)inter;
         acc.appendDefault();
      }

      protected int getSize(Object inter) {
         Accumulator acc = (Accumulator)inter;
         return acc.size();
      }

      protected void fill(Object final_obj, int index, Object prop_val) {
         assert final_obj != null;

         assert !(final_obj instanceof Accumulator);

         assert final_obj.getClass().isArray();

         Array.set(final_obj, index, prop_val);
      }

      Object getValue(Object parentObject) throws XmlException {
         throw new UnsupportedOperationException("use 3 arg getValue");
      }

      Object getValue(Object parentObject, MarshalResult result, int item_index) throws XmlException {
         return Array.get(parentObject, item_index);
      }

      boolean isSet(Object parentObject) throws XmlException {
         throw new UnsupportedOperationException("use 3 arg isSet");
      }

      boolean isSet(Object parentObject, MarshalResult result, int item_index) throws XmlException {
         if (this.nillable) {
            return true;
         } else if (this.itemType.isJavaPrimitive()) {
            return true;
         } else {
            return this.getValue(parentObject, result, item_index) != null;
         }
      }

      boolean isMultiple() {
         return true;
      }

      boolean isNillable() {
         return this.nillable;
      }

      boolean isOptional() {
         return false;
      }

      String getLexicalDefault() {
         return null;
      }

      boolean isTransient(Object currObject) {
         return false;
      }
   }
}
