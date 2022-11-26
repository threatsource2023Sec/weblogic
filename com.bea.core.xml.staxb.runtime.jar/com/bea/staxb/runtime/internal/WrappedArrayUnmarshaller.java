package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;

public class WrappedArrayUnmarshaller implements TypeUnmarshaller {
   private final WrappedArrayRuntimeBindingType type;

   public WrappedArrayUnmarshaller(WrappedArrayRuntimeBindingType rtt) {
      this.type = rtt;
   }

   public Object unmarshal(UnmarshalResult result) throws XmlException {
      Object inter = this.type.createIntermediary(result);
      this.unmarshalIntoIntermediary(inter, result);
      return this.type.getFinalObjectFromIntermediary(inter, result);
   }

   public void unmarshalIntoIntermediary(Object intermediary, UnmarshalResult result) throws XmlException {
      this.deserializeContents(intermediary, result);
   }

   private void deserializeContents(Object inter, UnmarshalResult context) throws XmlException {
      assert context.isStartElement();

      String ourStartUri = context.getNamespaceURI();
      String ourStartLocalName = context.getLocalName();
      context.next();
      WrappedArrayRuntimeBindingType.ItemProperty elem_prop = this.type.getElementProperty();

      while(context.advanceToNextStartElement()) {
         assert context.isStartElement();

         if (this.matchesItemElement(context)) {
            context.extractAndFillElementProp(elem_prop, inter);
         } else {
            context.skipElement();
         }

         assert context.isEndElement();

         if (context.hasNext()) {
            context.next();
         }
      }

      assert context.isEndElement();

      String ourEndUri = context.getNamespaceURI();
      String ourEndLocalName = context.getLocalName();

      assert ourStartUri.equals(ourEndUri) : "expected=" + ourStartUri + " got=" + ourEndUri;

      assert ourStartLocalName.equals(ourEndLocalName) : "expected=" + ourStartLocalName + " got=" + ourEndLocalName;

   }

   private boolean matchesItemElement(UnmarshalResult context) {
      QName el_name = this.type.getElementProperty().getName();
      return UnmarshalResult.doesElementMatch(el_name, context.getLocalName(), context.getNamespaceURI(), context.getNamespaceMapping());
   }

   public Object unmarshalAttribute(UnmarshalResult result) throws XmlException {
      throw new AssertionError("not used");
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      throw new AssertionError("not used");
   }

   public void unmarshalAttribute(Object object, UnmarshalResult result) throws XmlException {
      throw new AssertionError("not used");
   }

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) {
   }
}
