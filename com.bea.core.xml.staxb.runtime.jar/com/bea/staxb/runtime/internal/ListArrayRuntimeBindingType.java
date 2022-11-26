package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.ListArrayType;
import com.bea.staxb.runtime.internal.util.collections.Accumulator;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;

final class ListArrayRuntimeBindingType extends RuntimeBindingType {
   private final ListArrayType listArrayType;
   private LAProperty itemProperty;

   ListArrayRuntimeBindingType(ListArrayType binding_type) throws XmlException {
      super(binding_type);
      this.listArrayType = binding_type;
   }

   void accept(RuntimeTypeVisitor visitor) throws XmlException {
      visitor.visit(this);
   }

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) throws XmlException {
      BindingTypeName item_type_name = this.listArrayType.getItemType();

      assert item_type_name != null;

      BindingType item_type = bindingLoader.getBindingType(item_type_name);
      if (item_type == null) {
         String msg = "unable to lookup " + item_type_name + " from type " + this.listArrayType;
         throw new XmlException(msg);
      } else {
         RuntimeBindingType item_rtt = typeTable.createRuntimeType(item_type, bindingLoader);
         this.itemProperty = new LAProperty(this, item_rtt);
      }
   }

   boolean hasElementChildren() {
      return false;
   }

   RuntimeBindingProperty getItemProperty() {
      assert this.itemProperty != null;

      return this.itemProperty;
   }

   private static final class LAProperty extends RuntimeBindingProperty {
      private final ListArrayRuntimeBindingType containingType;
      private final RuntimeBindingType itemType;

      LAProperty(ListArrayRuntimeBindingType containing_type, RuntimeBindingType item_type) throws XmlException {
         super(containing_type);
         this.containingType = containing_type;
         this.itemType = item_type;

         assert item_type.getMarshaller() != null;

      }

      Class getItemClass() {
         return this.itemType.getJavaType();
      }

      RuntimeBindingType getRuntimeBindingType() {
         return this.itemType;
      }

      QName getName() {
         throw new UnsupportedOperationException("no name");
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

      Object getValue(Object parentObject) throws XmlException {
         throw new AssertionError("not used");
      }

      boolean isSet(Object parentObject) throws XmlException {
         throw new AssertionError("not used");
      }

      boolean isMultiple() {
         return true;
      }

      boolean isNillable() {
         return false;
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
