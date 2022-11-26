package com.bea.ns.weblogic.x60;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface CmpFieldType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(CmpFieldType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("cmpfieldtype5f20type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static CmpFieldType newInstance() {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().newInstance(CmpFieldType.type, (XmlOptions)null);
      }

      public static CmpFieldType newInstance(XmlOptions options) {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().newInstance(CmpFieldType.type, options);
      }

      public static CmpFieldType parse(String xmlAsString) throws XmlException {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CmpFieldType.type, (XmlOptions)null);
      }

      public static CmpFieldType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().parse(xmlAsString, CmpFieldType.type, options);
      }

      public static CmpFieldType parse(File file) throws XmlException, IOException {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().parse(file, CmpFieldType.type, (XmlOptions)null);
      }

      public static CmpFieldType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().parse(file, CmpFieldType.type, options);
      }

      public static CmpFieldType parse(URL u) throws XmlException, IOException {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().parse(u, CmpFieldType.type, (XmlOptions)null);
      }

      public static CmpFieldType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().parse(u, CmpFieldType.type, options);
      }

      public static CmpFieldType parse(InputStream is) throws XmlException, IOException {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().parse(is, CmpFieldType.type, (XmlOptions)null);
      }

      public static CmpFieldType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().parse(is, CmpFieldType.type, options);
      }

      public static CmpFieldType parse(Reader r) throws XmlException, IOException {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().parse(r, CmpFieldType.type, (XmlOptions)null);
      }

      public static CmpFieldType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().parse(r, CmpFieldType.type, options);
      }

      public static CmpFieldType parse(XMLStreamReader sr) throws XmlException {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().parse(sr, CmpFieldType.type, (XmlOptions)null);
      }

      public static CmpFieldType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().parse(sr, CmpFieldType.type, options);
      }

      public static CmpFieldType parse(Node node) throws XmlException {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().parse(node, CmpFieldType.type, (XmlOptions)null);
      }

      public static CmpFieldType parse(Node node, XmlOptions options) throws XmlException {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().parse(node, CmpFieldType.type, options);
      }

      /** @deprecated */
      public static CmpFieldType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().parse(xis, CmpFieldType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static CmpFieldType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (CmpFieldType)XmlBeans.getContextTypeLoader().parse(xis, CmpFieldType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CmpFieldType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, CmpFieldType.type, options);
      }

      private Factory() {
      }
   }
}
