package com.oracle.xmlns.weblogic.multiVersionState;

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

public interface MultiVersionStateDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MultiVersionStateDocument.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("multiversionstate2b4edoctype");

   MultiVersionStateType getMultiVersionState();

   void setMultiVersionState(MultiVersionStateType var1);

   MultiVersionStateType addNewMultiVersionState();

   public static final class Factory {
      public static MultiVersionStateDocument newInstance() {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().newInstance(MultiVersionStateDocument.type, (XmlOptions)null);
      }

      public static MultiVersionStateDocument newInstance(XmlOptions options) {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().newInstance(MultiVersionStateDocument.type, options);
      }

      public static MultiVersionStateDocument parse(String xmlAsString) throws XmlException {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, MultiVersionStateDocument.type, (XmlOptions)null);
      }

      public static MultiVersionStateDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, MultiVersionStateDocument.type, options);
      }

      public static MultiVersionStateDocument parse(File file) throws XmlException, IOException {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().parse(file, MultiVersionStateDocument.type, (XmlOptions)null);
      }

      public static MultiVersionStateDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().parse(file, MultiVersionStateDocument.type, options);
      }

      public static MultiVersionStateDocument parse(URL u) throws XmlException, IOException {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().parse(u, MultiVersionStateDocument.type, (XmlOptions)null);
      }

      public static MultiVersionStateDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().parse(u, MultiVersionStateDocument.type, options);
      }

      public static MultiVersionStateDocument parse(InputStream is) throws XmlException, IOException {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().parse(is, MultiVersionStateDocument.type, (XmlOptions)null);
      }

      public static MultiVersionStateDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().parse(is, MultiVersionStateDocument.type, options);
      }

      public static MultiVersionStateDocument parse(Reader r) throws XmlException, IOException {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().parse(r, MultiVersionStateDocument.type, (XmlOptions)null);
      }

      public static MultiVersionStateDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().parse(r, MultiVersionStateDocument.type, options);
      }

      public static MultiVersionStateDocument parse(XMLStreamReader sr) throws XmlException {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().parse(sr, MultiVersionStateDocument.type, (XmlOptions)null);
      }

      public static MultiVersionStateDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().parse(sr, MultiVersionStateDocument.type, options);
      }

      public static MultiVersionStateDocument parse(Node node) throws XmlException {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().parse(node, MultiVersionStateDocument.type, (XmlOptions)null);
      }

      public static MultiVersionStateDocument parse(Node node, XmlOptions options) throws XmlException {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().parse(node, MultiVersionStateDocument.type, options);
      }

      /** @deprecated */
      public static MultiVersionStateDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().parse(xis, MultiVersionStateDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MultiVersionStateDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MultiVersionStateDocument)XmlBeans.getContextTypeLoader().parse(xis, MultiVersionStateDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MultiVersionStateDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MultiVersionStateDocument.type, options);
      }

      private Factory() {
      }
   }
}
