package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.staxb.runtime.UnmarshalOptions;
import com.bea.staxb.runtime.Unmarshaller;
import com.bea.xml.XmlException;
import java.io.InputStream;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.xml.stax.XMLStreamInputFactory;

final class UnmarshallerImpl implements Unmarshaller {
   private final BindingLoader bindingLoader;
   private final RuntimeBindingTypeTable typeTable;
   private final SchemaTypeLoaderProvider schemaTypeLoaderProvider;
   private static final XMLInputFactory XML_INPUT_FACTORY = new XMLStreamInputFactory();

   public UnmarshallerImpl(BindingLoader loader, RuntimeBindingTypeTable typeTable, SchemaTypeLoaderProvider provider) {
      assert loader != null;

      assert typeTable != null;

      this.bindingLoader = loader;
      this.typeTable = typeTable;
      this.schemaTypeLoaderProvider = provider;
   }

   public Object unmarshal(XMLStreamReader reader) throws XmlException {
      return this.unmarshal(reader, (UnmarshalOptions)null);
   }

   public Object unmarshal(XMLStreamReader reader, UnmarshalOptions options) throws XmlException {
      UnmarshalResult result = new LiteralUnmarshalResult(this.bindingLoader, this.typeTable, this.schemaTypeLoaderProvider, options);
      return result.unmarshalDocument(reader);
   }

   public Object unmarshal(InputStream doc) throws XmlException {
      return this.unmarshal(doc, (UnmarshalOptions)null);
   }

   public Object unmarshal(InputStream doc, UnmarshalOptions options) throws XmlException {
      if (doc == null) {
         throw new IllegalArgumentException("null inputStream");
      } else {
         try {
            XMLStreamReader reader = XML_INPUT_FACTORY.createXMLStreamReader(doc);
            return this.unmarshal(reader, options);
         } catch (XMLStreamException var4) {
            throw new XmlException(var4);
         }
      }
   }

   public Object unmarshalType(XMLStreamReader reader, QName schemaType, String javaType) throws XmlException {
      return this.unmarshalType(reader, schemaType, javaType, (UnmarshalOptions)null);
   }

   public Object unmarshalType(XMLStreamReader reader, QName schemaType, String javaType, UnmarshalOptions options) throws XmlException {
      if (schemaType == null) {
         throw new IllegalArgumentException("null schemaType");
      } else {
         return this.unmarshalType(reader, XmlTypeName.forTypeNamed(schemaType), javaType, options);
      }
   }

   public Object unmarshalType(XMLStreamReader reader, XmlTypeName schemaType, String javaType, UnmarshalOptions options) throws XmlException {
      if (reader == null) {
         throw new IllegalArgumentException("null reader");
      } else if (schemaType == null) {
         throw new IllegalArgumentException("null schemaType");
      } else if (javaType == null) {
         throw new IllegalArgumentException("null javaType");
      } else if (!reader.isStartElement()) {
         throw new IllegalStateException("reader must be positioned on a start element");
      } else {
         UnmarshalResult result = new LiteralUnmarshalResult(this.bindingLoader, this.typeTable, this.schemaTypeLoaderProvider, options);
         return result.unmarshalType(reader, schemaType, javaType);
      }
   }

   public Object unmarshalElement(XMLStreamReader reader, QName globalElement, String javaType, UnmarshalOptions options) throws XmlException {
      if (globalElement == null) {
         throw new IllegalArgumentException("null globalElement");
      } else {
         XmlTypeName elem = XmlTypeName.forGlobalName('e', globalElement);
         return this.unmarshalElement(reader, elem, javaType, options);
      }
   }

   public Object unmarshalElement(XMLStreamReader reader, XmlTypeName globalElement, String javaType, UnmarshalOptions options) throws XmlException {
      if (reader == null) {
         throw new IllegalArgumentException("null reader");
      } else if (globalElement == null) {
         throw new IllegalArgumentException("null globalElement");
      } else if (javaType == null) {
         throw new IllegalArgumentException("null javaType");
      } else if (!reader.isStartElement()) {
         throw new IllegalStateException("reader must be positioned on a start element");
      } else {
         UnmarshalResult result = new LiteralUnmarshalResult(this.bindingLoader, this.typeTable, this.schemaTypeLoaderProvider, options);
         return result.unmarshalElement(reader, globalElement, javaType);
      }
   }

   static XMLInputFactory getXmlInputFactory() {
      return XML_INPUT_FACTORY;
   }
}
