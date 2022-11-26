package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingType;
import com.bea.xbeanmarshal.buildtime.internal.bts.BindingTypeName;
import com.bea.xbeanmarshal.buildtime.internal.bts.WrappedArrayType;
import com.bea.xbeanmarshal.buildtime.internal.bts.XmlBeansBuiltinBindingLoader;
import com.bea.xbeanmarshal.buildtime.internal.util.XmlBeanUtil;
import com.bea.xbeanmarshal.runtime.internal.util.ArrayUtils;
import com.bea.xbeanmarshal.runtime.internal.util.collections.Accumulator;
import com.bea.xbeanmarshal.runtime.internal.util.collections.AccumulatorFactory;
import com.bea.xml.XmlException;
import java.lang.reflect.Array;
import java.util.Iterator;
import javax.xml.namespace.QName;

final class WrappedArrayRuntimeBindingType extends RuntimeBindingType {
   private final WrappedArrayType wrappedArrayType;
   private ItemProperty elementProperty;
   private final boolean canUseDefaultNS;
   private static final int NS_PREDEFINE_THRESHOLD = 2;

   WrappedArrayRuntimeBindingType(WrappedArrayType binding_type) throws XmlException {
      super(binding_type);
      this.wrappedArrayType = binding_type;
      this.canUseDefaultNS = !"".equals(binding_type.getItemName().getNamespaceURI());
   }

   void accept(RuntimeTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }

   protected void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) throws XmlException {
      BindingTypeName item_type_name = this.wrappedArrayType.getItemType();

      assert item_type_name != null;

      BindingType item_type = XmlBeanUtil.getAnyBindingType(this.wrappedArrayType.getItemName(), item_type_name.getJavaName().getClassName());
      if (item_type == null) {
         item_type = XmlBeansBuiltinBindingLoader.getInstance().getBindingType(item_type_name);
      }

      if (item_type == null) {
         item_type = bindingLoader.getBindingType(item_type_name);
         if (item_type == null) {
            String msg = "unable to lookup item type='" + item_type_name + "',  for WrappedArrayType='" + this.wrappedArrayType + "'";
            throw new XmlException(msg);
         }
      }

      RuntimeBindingType item_rtt = typeTable.createRuntimeType(item_type, bindingLoader);
      this.elementProperty = new ItemProperty(this, this.wrappedArrayType.getItemName(), item_rtt, this.wrappedArrayType.isItemNillable());
   }

   boolean isJavaCollection() {
      return false;
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

      String getLexicalDefault() {
         return null;
      }
   }
}
