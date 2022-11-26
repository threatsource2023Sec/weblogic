package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.xml.XmlException;

final class NullUnmarshaller implements TypeUnmarshaller {
   private static final TypeUnmarshaller INSTANCE = new NullUnmarshaller();

   private NullUnmarshaller() {
   }

   public Object unmarshal(UnmarshalResult context) throws XmlException {
      context.skipElement();
      return null;
   }

   public void unmarshalIntoIntermediary(Object intermediary, UnmarshalResult result) throws XmlException {
      result.skipElement();
   }

   public Object unmarshalAttribute(UnmarshalResult context) {
      throw new UnsupportedOperationException("not supported: this=" + this);
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      throw new UnsupportedOperationException("not supported: this=" + this);
   }

   public void unmarshalAttribute(Object object, UnmarshalResult result) throws XmlException {
      throw new UnsupportedOperationException("not supported: this=" + this);
   }

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) {
   }

   public static TypeUnmarshaller getInstance() {
      return INSTANCE;
   }
}
