package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.xml.XmlException;

abstract class AttributeUnmarshaller implements TypeUnmarshaller {
   private final AttributeRuntimeBindingType type;

   public AttributeUnmarshaller(AttributeRuntimeBindingType type) {
      this.type = type;
   }

   public Object unmarshal(UnmarshalResult context) throws XmlException {
      Object inter = this.type.createIntermediary(context);
      this.deserializeAttributes(inter, context);
      this.deserializeContents(inter, context);
      return this.type.getFinalObjectFromIntermediary(inter, context);
   }

   public void unmarshalIntoIntermediary(Object intermediary, UnmarshalResult result) throws XmlException {
      this.deserializeAttributes(intermediary, result);
      this.deserializeContents(intermediary, result);
   }

   public Object unmarshalAttribute(UnmarshalResult context) {
      throw new UnsupportedOperationException("not an attribute: " + this.type.getSchemaTypeName());
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      throw new UnsupportedOperationException("not an attribute: " + this.type.getSchemaTypeName());
   }

   public void unmarshalAttribute(Object object, UnmarshalResult result) throws XmlException {
      throw new UnsupportedOperationException("not an attribute: " + this.type.getSchemaTypeName());
   }

   protected abstract void deserializeContents(Object var1, UnmarshalResult var2) throws XmlException;

   protected void deserializeAttributes(Object inter, UnmarshalResult context) throws XmlException {
      for(; context.hasMoreAttributes(); context.advanceAttribute()) {
         RuntimeBindingProperty prop = this.findMatchingAttributeProperty(context);
         if (prop != null) {
            prop.extractAndFillAttributeProp(context, inter);
         }
      }

      this.type.fillDefaultAttributes(inter, context);
   }

   protected RuntimeBindingProperty findMatchingAttributeProperty(UnmarshalResult context) {
      String uri = context.getCurrentAttributeNamespaceURI();
      String lname = context.getCurrentAttributeLocalName();
      return this.type.getMatchingAttributeProperty(uri, lname, context);
   }

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) {
   }
}
