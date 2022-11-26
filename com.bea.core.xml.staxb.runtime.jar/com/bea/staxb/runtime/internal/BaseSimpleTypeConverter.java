package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xml.XmlException;

abstract class BaseSimpleTypeConverter implements TypeConverter {
   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) {
   }

   public final Object unmarshal(UnmarshalResult context) throws XmlException {
      try {
         return this.getObject(context);
      } catch (InvalidLexicalValueException var3) {
         context.addError(var3.getMessage(), var3.getLocation());
         throw var3;
      }
   }

   public void unmarshalIntoIntermediary(Object intermediary, UnmarshalResult result) {
      throw new UnsupportedOperationException("not used: " + this);
   }

   public void unmarshalAttribute(Object object, UnmarshalResult result) throws XmlException {
      throw new UnsupportedOperationException("not supported: this=" + this);
   }

   protected abstract Object getObject(UnmarshalResult var1) throws XmlException;
}
