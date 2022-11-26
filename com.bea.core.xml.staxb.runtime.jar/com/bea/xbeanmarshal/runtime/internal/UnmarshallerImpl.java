package com.bea.xbeanmarshal.runtime.internal;

import com.bea.xbeanmarshal.buildtime.internal.bts.BindingLoader;
import com.bea.xbeanmarshal.runtime.Unmarshaller;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.stream.XMLInputFactory;
import weblogic.xml.stax.XMLStreamInputFactory;

final class UnmarshallerImpl implements Unmarshaller {
   private final BindingLoader bindingLoader;
   private final RuntimeBindingTypeTable typeTable;
   private static final XMLInputFactory XML_INPUT_FACTORY = new XMLStreamInputFactory();

   public UnmarshallerImpl(BindingLoader loader, RuntimeBindingTypeTable typeTable) {
      assert loader != null;

      assert typeTable != null;

      this.bindingLoader = loader;
      this.typeTable = typeTable;
   }

   public Object deserializeXmlObjects(boolean isWrapped, SOAPElement sourceSOAPElement, Class targetJavaClass, QName argToDeserializeElementQName) throws XmlException {
      UnmarshalResult result = new LiteralUnmarshalResult(this.bindingLoader, this.typeTable);
      return result.deserializeXmlObjects(isWrapped, sourceSOAPElement, targetJavaClass, argToDeserializeElementQName);
   }
}
