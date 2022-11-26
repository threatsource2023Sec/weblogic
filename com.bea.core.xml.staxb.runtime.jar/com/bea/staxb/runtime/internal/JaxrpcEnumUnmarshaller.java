package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xml.XmlException;

final class JaxrpcEnumUnmarshaller implements TypeUnmarshaller {
   private final JaxrpcEnumRuntimeBindingType runtimeType;

   public JaxrpcEnumUnmarshaller(JaxrpcEnumRuntimeBindingType rtt) {
      this.runtimeType = rtt;
   }

   public Object unmarshal(UnmarshalResult result) throws XmlException {
      TypeUnmarshaller item_um = this.runtimeType.getItemUnmarshaller();
      Object itemValue = item_um.unmarshal(result);

      try {
         return this.runtimeType.fromValue(itemValue, result);
      } catch (InvalidLexicalValueException var5) {
         result.addError(var5.getMessage(), var5.getLocation());
         throw var5;
      }
   }

   public void unmarshalIntoIntermediary(Object intermediary, UnmarshalResult result) throws XmlException {
      throw new UnsupportedOperationException("not supported: this=" + this);
   }

   public Object unmarshalAttribute(UnmarshalResult result) throws XmlException {
      TypeUnmarshaller item_um = this.runtimeType.getItemUnmarshaller();
      Object itemValue = item_um.unmarshalAttribute(result);
      return this.runtimeType.fromValue(itemValue, result);
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      TypeUnmarshaller item_um = this.runtimeType.getItemUnmarshaller();
      Object itemValue = item_um.unmarshalAttribute(lexical_value, result);
      return this.runtimeType.fromValue(itemValue, result);
   }

   public void unmarshalAttribute(Object object, UnmarshalResult result) throws XmlException {
      throw new UnsupportedOperationException("not supported: this=" + this);
   }

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) {
   }
}
