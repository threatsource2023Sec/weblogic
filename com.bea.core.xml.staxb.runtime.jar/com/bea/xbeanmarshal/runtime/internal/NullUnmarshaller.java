package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xml.XmlException;
import org.w3c.dom.Node;

final class NullUnmarshaller implements TypeUnmarshaller {
   private static final TypeUnmarshaller INSTANCE = new NullUnmarshaller();

   private NullUnmarshaller() {
   }

   public Object unmarshal(UnmarshalResult context, Node node, Class javaClass) throws XmlException {
      return null;
   }

   public void unmarshalIntoIntermediary(Object intermediary, UnmarshalResult result, Node node, Class javaClass) throws XmlException {
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
