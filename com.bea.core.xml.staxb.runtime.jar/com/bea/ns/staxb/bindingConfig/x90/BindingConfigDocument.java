package com.bea.ns.staxb.bindingConfig.x90;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface BindingConfigDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(BindingConfigDocument.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("bindingconfig8f77doctype");

   BindingConfig getBindingConfig();

   void setBindingConfig(BindingConfig var1);

   BindingConfig addNewBindingConfig();

   public static final class Factory {
      public static BindingConfigDocument newInstance() {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().newInstance(BindingConfigDocument.type, (XmlOptions)null);
      }

      public static BindingConfigDocument newInstance(XmlOptions options) {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().newInstance(BindingConfigDocument.type, options);
      }

      public static BindingConfigDocument parse(String xmlAsString) throws XmlException {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, BindingConfigDocument.type, (XmlOptions)null);
      }

      public static BindingConfigDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, BindingConfigDocument.type, options);
      }

      public static BindingConfigDocument parse(File file) throws XmlException, IOException {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().parse(file, BindingConfigDocument.type, (XmlOptions)null);
      }

      public static BindingConfigDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().parse(file, BindingConfigDocument.type, options);
      }

      public static BindingConfigDocument parse(URL u) throws XmlException, IOException {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().parse(u, BindingConfigDocument.type, (XmlOptions)null);
      }

      public static BindingConfigDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().parse(u, BindingConfigDocument.type, options);
      }

      public static BindingConfigDocument parse(InputStream is) throws XmlException, IOException {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().parse(is, BindingConfigDocument.type, (XmlOptions)null);
      }

      public static BindingConfigDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().parse(is, BindingConfigDocument.type, options);
      }

      public static BindingConfigDocument parse(Reader r) throws XmlException, IOException {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().parse(r, BindingConfigDocument.type, (XmlOptions)null);
      }

      public static BindingConfigDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().parse(r, BindingConfigDocument.type, options);
      }

      public static BindingConfigDocument parse(XMLStreamReader sr) throws XmlException {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().parse(sr, BindingConfigDocument.type, (XmlOptions)null);
      }

      public static BindingConfigDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().parse(sr, BindingConfigDocument.type, options);
      }

      public static BindingConfigDocument parse(Node node) throws XmlException {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().parse(node, BindingConfigDocument.type, (XmlOptions)null);
      }

      public static BindingConfigDocument parse(Node node, XmlOptions options) throws XmlException {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().parse(node, BindingConfigDocument.type, options);
      }

      /** @deprecated */
      public static BindingConfigDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().parse(xis, BindingConfigDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static BindingConfigDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (BindingConfigDocument)XmlBeans.getContextTypeLoader().parse(xis, BindingConfigDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BindingConfigDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, BindingConfigDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface BindingConfig extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(BindingConfig.class.getClassLoader(), "schemacom_bea_xml.system.bea_staxb_runtime").resolveHandle("bindingconfig7491elemtype");

      BindingTable getBindings();

      void setBindings(BindingTable var1);

      BindingTable addNewBindings();

      MappingTable getXmlToPojo();

      void setXmlToPojo(MappingTable var1);

      MappingTable addNewXmlToPojo();

      MappingTable getXmlToXmlobj();

      void setXmlToXmlobj(MappingTable var1);

      MappingTable addNewXmlToXmlobj();

      MappingTable getJavaToXml();

      void setJavaToXml(MappingTable var1);

      MappingTable addNewJavaToXml();

      MappingTable getJavaToElement();

      void setJavaToElement(MappingTable var1);

      MappingTable addNewJavaToElement();

      public static final class Factory {
         public static BindingConfig newInstance() {
            return (BindingConfig)XmlBeans.getContextTypeLoader().newInstance(BindingConfigDocument.BindingConfig.type, (XmlOptions)null);
         }

         public static BindingConfig newInstance(XmlOptions options) {
            return (BindingConfig)XmlBeans.getContextTypeLoader().newInstance(BindingConfigDocument.BindingConfig.type, options);
         }

         private Factory() {
         }
      }
   }
}
