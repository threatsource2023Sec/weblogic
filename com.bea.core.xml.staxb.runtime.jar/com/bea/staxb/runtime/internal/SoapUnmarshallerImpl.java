package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.staxb.runtime.EncodingStyle;
import com.bea.staxb.runtime.SoapUnmarshaller;
import com.bea.staxb.runtime.StreamReaderFromNode;
import com.bea.staxb.runtime.UnmarshalOptions;
import com.bea.staxb.runtime.internal.util.AttrCache;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

final class SoapUnmarshallerImpl implements SoapUnmarshaller, StreamRefNavigator {
   private final BindingLoader bindingLoader;
   private final RuntimeBindingTypeTable typeTable;
   private final EncodingStyle encodingStyle;
   private final RefObjectTable refObjectTable = new RefObjectTable();
   private final Element referenceRoot;
   private AttrCache attrCache;

   SoapUnmarshallerImpl(BindingLoader loader, RuntimeBindingTypeTable typeTable, EncodingStyle encodingStyle, Element reference_root) {
      assert loader != null;

      assert typeTable != null;

      assert encodingStyle != null;

      assert reference_root != null;

      this.bindingLoader = loader;
      this.typeTable = typeTable;
      this.encodingStyle = encodingStyle;
      this.referenceRoot = reference_root;
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
      } else if (options != null && options.getStreamReaderFromNode() != null) {
         SoapUnmarshalResult result = this.createMarshalResult(options);
         return result.unmarshalType(reader, schemaType, javaType);
      } else {
         throw new IllegalArgumentException("soap encoding requires a non null streamReaderFromNode option");
      }
   }

   private SoapUnmarshalResult createMarshalResult(UnmarshalOptions options) {
      if (EncodingStyle.SOAP11 == this.encodingStyle) {
         SoapUnmarshalResult result = new Soap11UnmarshalResult(this.bindingLoader, this.typeTable, this.refObjectTable, this, options);
         return result;
      } else if (EncodingStyle.SOAP12 == this.encodingStyle) {
         throw new AssertionError("soap 12 UNIMP: " + this.encodingStyle);
      } else {
         throw new AssertionError("unknown encoding style: " + this.encodingStyle);
      }
   }

   private QName getIdQName() {
      if (EncodingStyle.SOAP11 == this.encodingStyle) {
         return Soap11Constants.ID_NAME;
      } else if (EncodingStyle.SOAP12 == this.encodingStyle) {
         throw new AssertionError("soap 12 is unimplemented");
      } else {
         throw new AssertionError("unknown encoding style: " + this.encodingStyle);
      }
   }

   public XMLStreamReader lookupRef(String ref, StreamReaderFromNode streamReaderFromNode) throws XmlException {
      assert streamReaderFromNode != null;

      if (this.attrCache == null) {
         this.attrCache = new AttrCache(this.referenceRoot, this.getIdQName());
      }

      Node target_node = this.attrCache.lookup(ref);
      if (target_node == null) {
         return null;
      } else {
         assert target_node.getNodeType() == 1;

         XMLStreamReader target_stream = streamReaderFromNode.getStreamReader((Element)target_node);

         assert target_stream.isStartElement();

         return target_stream;
      }
   }
}
