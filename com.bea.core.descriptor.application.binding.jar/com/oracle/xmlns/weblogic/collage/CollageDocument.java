package com.oracle.xmlns.weblogic.collage;

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

public interface CollageDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CollageDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("collaged22edoctype");

   CollageType getCollage();

   void setCollage(CollageType var1);

   CollageType addNewCollage();

   public static final class Factory {
      public static CollageDocument newInstance() {
         return (CollageDocument)XmlBeans.getContextTypeLoader().newInstance(CollageDocument.type, (XmlOptions)null);
      }

      public static CollageDocument newInstance(XmlOptions options) {
         return (CollageDocument)XmlBeans.getContextTypeLoader().newInstance(CollageDocument.type, options);
      }

      public static CollageDocument parse(String xmlAsString) throws XmlException {
         return (CollageDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, CollageDocument.type, (XmlOptions)null);
      }

      public static CollageDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CollageDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, CollageDocument.type, options);
      }

      public static CollageDocument parse(File file) throws XmlException, IOException {
         return (CollageDocument)XmlBeans.getContextTypeLoader().parse(file, CollageDocument.type, (XmlOptions)null);
      }

      public static CollageDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CollageDocument)XmlBeans.getContextTypeLoader().parse(file, CollageDocument.type, options);
      }

      public static CollageDocument parse(URL u) throws XmlException, IOException {
         return (CollageDocument)XmlBeans.getContextTypeLoader().parse(u, CollageDocument.type, (XmlOptions)null);
      }

      public static CollageDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CollageDocument)XmlBeans.getContextTypeLoader().parse(u, CollageDocument.type, options);
      }

      public static CollageDocument parse(InputStream is) throws XmlException, IOException {
         return (CollageDocument)XmlBeans.getContextTypeLoader().parse(is, CollageDocument.type, (XmlOptions)null);
      }

      public static CollageDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CollageDocument)XmlBeans.getContextTypeLoader().parse(is, CollageDocument.type, options);
      }

      public static CollageDocument parse(Reader r) throws XmlException, IOException {
         return (CollageDocument)XmlBeans.getContextTypeLoader().parse(r, CollageDocument.type, (XmlOptions)null);
      }

      public static CollageDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CollageDocument)XmlBeans.getContextTypeLoader().parse(r, CollageDocument.type, options);
      }

      public static CollageDocument parse(XMLStreamReader sr) throws XmlException {
         return (CollageDocument)XmlBeans.getContextTypeLoader().parse(sr, CollageDocument.type, (XmlOptions)null);
      }

      public static CollageDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CollageDocument)XmlBeans.getContextTypeLoader().parse(sr, CollageDocument.type, options);
      }

      public static CollageDocument parse(Node node) throws XmlException {
         return (CollageDocument)XmlBeans.getContextTypeLoader().parse(node, CollageDocument.type, (XmlOptions)null);
      }

      public static CollageDocument parse(Node node, XmlOptions options) throws XmlException {
         return (CollageDocument)XmlBeans.getContextTypeLoader().parse(node, CollageDocument.type, options);
      }

      /** @deprecated */
      public static CollageDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CollageDocument)XmlBeans.getContextTypeLoader().parse(xis, CollageDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CollageDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CollageDocument)XmlBeans.getContextTypeLoader().parse(xis, CollageDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CollageDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CollageDocument.type, options);
      }

      private Factory() {
      }
   }
}
