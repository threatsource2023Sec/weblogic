package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.staxb.runtime.UnmarshalOptions;
import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.common.PrefixResolver;
import com.bea.xbean.validator.ValidatingXMLStreamReader;
import com.bea.xml.SchemaField;
import com.bea.xml.SchemaGlobalElement;
import com.bea.xml.SchemaType;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import com.bea.xml.soap.SOAPArrayType;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;

final class LiteralUnmarshalResult extends UnmarshalResult implements PrefixResolver {
   private final SchemaTypeLoaderProvider schemaTypeLoaderProvider;

   LiteralUnmarshalResult(BindingLoader bindingLoader, RuntimeBindingTypeTable typeTable, SchemaTypeLoaderProvider provider, UnmarshalOptions options) {
      super(bindingLoader, typeTable, options);
      this.schemaTypeLoaderProvider = provider;
   }

   protected XMLStreamReader getValidatingStream(XMLStreamReader reader) throws XmlException {
      if (this.isValidating()) {
         ValidatingXMLStreamReader vr = new ValidatingXMLStreamReader();
         SchemaTypeLoader schemaTypeLoader = this.schemaTypeLoaderProvider.getSchemaTypeLoader();
         if (schemaTypeLoader == null) {
            String msg = "null schema type loader from schemaTypeLoaderProvider " + this.schemaTypeLoaderProvider;
            throw new XmlException(msg);
         } else {
            XmlOptions xopts = new XmlOptions();
            xopts.setErrorListener(this.errors);
            if (this.isAttributeValidationCompatMode()) {
               xopts.put("OPTION_ATTTRIBUTE_VALIDATION_COMPAT_MODE");
            }

            vr.init(reader, true, (SchemaType)null, schemaTypeLoader, xopts, this.errors);
            return (XMLStreamReader)(reader instanceof PushBackStreamReader ? new StreamAnalyser((PushBackStreamReader)reader, vr) : vr);
         }
      } else {
         return reader;
      }
   }

   protected XMLStreamReader getValidatingStream(XmlTypeName xml_type, XMLStreamReader reader) throws XmlException {
      if (this.isValidating()) {
         if (xml_type.isGlobal() && xml_type.isElement()) {
            return this.getValidatingStream(reader);
         } else {
            SchemaTypeLoader schemaTypeLoader = this.schemaTypeLoaderProvider.getSchemaTypeLoader();
            SchemaType schema_type = xml_type.findTypeIn(schemaTypeLoader);
            if (schema_type == null) {
               String e = "unable to locate definition of type " + xml_type + " in supplied schema type system";
               throw new XmlException(e);
            } else {
               SchemaField field = schema_type.getContainerField();
               if (field != null && field instanceof SchemaGlobalElement) {
                  return this.getValidatingStream(reader);
               } else {
                  ValidatingXMLStreamReader vr = new ValidatingXMLStreamReader();
                  XmlOptions xopts = new XmlOptions();
                  xopts.setErrorListener(this.errors);
                  if (this.isAttributeValidationCompatMode()) {
                     xopts.put("OPTION_ATTTRIBUTE_VALIDATION_COMPAT_MODE");
                  }

                  vr.init(reader, false, schema_type, schemaTypeLoader, xopts, this.errors);
                  return (XMLStreamReader)(reader instanceof PushBackStreamReader ? new StreamAnalyser((PushBackStreamReader)reader, vr) : vr);
               }
            }
         }
      } else {
         return reader;
      }
   }

   private boolean isValidating() {
      return this.options == null ? false : this.options.isValidation();
   }

   private boolean isAttributeValidationCompatMode() {
      return this.options == null ? false : this.options.isAttributeValidationCompatMode();
   }

   void extractAndFillElementProp(RuntimeBindingProperty prop, Object inter) throws XmlException {
      try {
         BindingType customizedBType = this.getPrioritizedBindingType(prop.getRuntimeBindingType().getBindingType().getName());
         RuntimeBindingType actual_rtt = null;
         if (customizedBType != null) {
            actual_rtt = this.getRuntimeType(customizedBType);
         }

         if (actual_rtt == null) {
            actual_rtt = prop.getRuntimeBindingType().determineActualRuntimeType(this);
         }

         Object this_val = this.unmarshalElementProperty(prop, inter, actual_rtt);
         prop.fill(inter, this_val);
      } catch (InvalidLexicalValueException var6) {
      }

   }

   protected Object unmarshalElementProperty(RuntimeBindingProperty prop, Object inter, RuntimeBindingType actual_rtt) throws XmlException {
      String lexical_default = prop.getLexicalDefault();
      if (lexical_default != null) {
         this.setNextElementDefault(lexical_default);
      }

      Object this_val;
      if (this.hasXsiNil()) {
         this_val = NullUnmarshaller.getInstance().unmarshal(this);
      } else if (prop.hasFactory()) {
         Object prop_inter = prop.createIntermediary(inter, actual_rtt, this);
         actual_rtt.getUnmarshaller().unmarshalIntoIntermediary(prop_inter, this);
         this_val = actual_rtt.getFinalObjectFromIntermediary(prop_inter, this);
      } else {
         this_val = actual_rtt.getUnmarshaller().unmarshal(this);
      }

      return this_val;
   }

   public String getNamespaceForPrefix(String prefix) {
      return this.getNamespaceContext().getNamespaceURI(prefix);
   }

   public SOAPArrayType extractSoapArrayType() throws XmlException {
      String soap_array_str = this.baseReader.getAttributeValue(Soap11Constants.ARRAY_TYPE_NAME.getNamespaceURI(), Soap11Constants.ARRAY_TYPE_NAME.getLocalPart());
      if (soap_array_str == null) {
         soap_array_str = this.baseReader.getAttributeValue(Soap11Constants.SOAP12_ARRAY_TYPE_NAME.getNamespaceURI(), Soap11Constants.SOAP12_ARRAY_TYPE_NAME.getLocalPart());
         if (soap_array_str != null && this.baseReader.getAttributeValue(Soap11Constants.SOAP12_ARRAYSIZE.getNamespaceURI(), Soap11Constants.SOAP12_ARRAYSIZE.getLocalPart()) != null) {
            soap_array_str = soap_array_str + "[" + this.baseReader.getAttributeValue(Soap11Constants.SOAP12_ARRAYSIZE.getNamespaceURI(), Soap11Constants.SOAP12_ARRAYSIZE.getLocalPart()) + "]";
         } else {
            soap_array_str = null;
         }
      }

      return soap_array_str == null ? null : new SOAPArrayType(soap_array_str, this);
   }

   class StreamAnalyser extends StreamReaderDelegate {
      boolean latched = false;
      PushBackStreamReader reader;

      StreamAnalyser(PushBackStreamReader areader, ValidatingXMLStreamReader validationreader) {
         super(validationreader);
         this.reader = areader;
      }

      public int next() throws XMLStreamException {
         if (!this.latched) {
            int evType = this.reader.next();
            switch (evType) {
               case 1:
                  this.latched = true;
                  break;
               case 2:
               case 8:
                  return evType;
               default:
                  this.latched = true;
            }

            this.reader.bufferlastevent();
         }

         return super.next();
      }
   }
}
