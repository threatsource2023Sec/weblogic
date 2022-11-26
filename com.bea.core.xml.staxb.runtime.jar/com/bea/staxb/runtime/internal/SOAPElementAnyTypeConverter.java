package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.xml.XmlException;
import javax.xml.soap.SOAPElement;
import org.w3c.dom.CharacterData;

public final class SOAPElementAnyTypeConverter implements TypeConverter, GenericXmlMarshaller {
   public Object unmarshal(UnmarshalResult result) throws XmlException {
      SOAPElement se = result.getCurrentSOAPElementNode();
      result.skipElement();
      return se;
   }

   public void unmarshalIntoIntermediary(Object intermediary, UnmarshalResult result) {
      throw new UnsupportedOperationException("not used: " + this);
   }

   public Object unmarshalAttribute(UnmarshalResult result) throws XmlException {
      throw new AssertionError("unused");
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      throw new AssertionError("unused");
   }

   public void unmarshalAttribute(Object object, UnmarshalResult result) throws XmlException {
      throw new UnsupportedOperationException("not supported: this=" + this);
   }

   public void initialize(RuntimeBindingTypeTable typeTable, BindingLoader bindingLoader) {
   }

   public CharSequence print(Object obj, MarshalResult result) throws XmlException {
      if (!(obj instanceof SOAPElement)) {
         throw new AssertionError("expecting SOAPElement, not " + obj.getClass());
      } else {
         SOAPElement el = (SOAPElement)obj;
         short nodeType = el.getNodeType();
         if (nodeType == 3) {
            CharacterData cd = (CharacterData)el;
            return cd.getData();
         } else {
            throw new XmlException("expected text node not " + el.getNodeName());
         }
      }
   }

   public void marshalGenericXml(MarshalResult result, Object obj) {
      if (!(obj instanceof SOAPElement)) {
         throw new AssertionError("expecting SOAPElement, not " + obj.getClass());
      } else {
         SOAPElement el = (SOAPElement)obj;
         result.appendDomNode(el);
      }
   }
}
