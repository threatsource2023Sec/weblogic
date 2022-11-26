package com.bea.staxb.runtime.internal;

import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;

final class FloatTypeConverter extends BaseSimpleTypeConverter {
   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      float val = context.getAttributeFloatValue();
      return new Float(val);
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      try {
         float f = XsTypeConverter.lexFloat(lexical_value);
         return new Float(f);
      } catch (NumberFormatException var4) {
         throw new InvalidLexicalValueException(var4, result.getLocation());
      }
   }

   public CharSequence print(Object value, MarshalResult result) {
      Float val = (Float)value;
      return XsTypeConverter.printFloat(val);
   }

   protected Object getObject(UnmarshalResult context) throws XmlException {
      float val = context.getFloatValue();
      return new Float(val);
   }
}
