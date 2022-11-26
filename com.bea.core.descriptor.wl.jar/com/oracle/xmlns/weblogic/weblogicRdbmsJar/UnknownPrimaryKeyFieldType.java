package com.oracle.xmlns.weblogic.weblogicRdbmsJar;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlID;
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

public interface UnknownPrimaryKeyFieldType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(UnknownPrimaryKeyFieldType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("unknownprimarykeyfieldtypee73btype");

   CmpFieldType getCmpField();

   void setCmpField(CmpFieldType var1);

   CmpFieldType addNewCmpField();

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static UnknownPrimaryKeyFieldType newInstance() {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().newInstance(UnknownPrimaryKeyFieldType.type, (XmlOptions)null);
      }

      public static UnknownPrimaryKeyFieldType newInstance(XmlOptions options) {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().newInstance(UnknownPrimaryKeyFieldType.type, options);
      }

      public static UnknownPrimaryKeyFieldType parse(String xmlAsString) throws XmlException {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UnknownPrimaryKeyFieldType.type, (XmlOptions)null);
      }

      public static UnknownPrimaryKeyFieldType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().parse(xmlAsString, UnknownPrimaryKeyFieldType.type, options);
      }

      public static UnknownPrimaryKeyFieldType parse(File file) throws XmlException, IOException {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().parse(file, UnknownPrimaryKeyFieldType.type, (XmlOptions)null);
      }

      public static UnknownPrimaryKeyFieldType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().parse(file, UnknownPrimaryKeyFieldType.type, options);
      }

      public static UnknownPrimaryKeyFieldType parse(URL u) throws XmlException, IOException {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().parse(u, UnknownPrimaryKeyFieldType.type, (XmlOptions)null);
      }

      public static UnknownPrimaryKeyFieldType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().parse(u, UnknownPrimaryKeyFieldType.type, options);
      }

      public static UnknownPrimaryKeyFieldType parse(InputStream is) throws XmlException, IOException {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().parse(is, UnknownPrimaryKeyFieldType.type, (XmlOptions)null);
      }

      public static UnknownPrimaryKeyFieldType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().parse(is, UnknownPrimaryKeyFieldType.type, options);
      }

      public static UnknownPrimaryKeyFieldType parse(Reader r) throws XmlException, IOException {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().parse(r, UnknownPrimaryKeyFieldType.type, (XmlOptions)null);
      }

      public static UnknownPrimaryKeyFieldType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().parse(r, UnknownPrimaryKeyFieldType.type, options);
      }

      public static UnknownPrimaryKeyFieldType parse(XMLStreamReader sr) throws XmlException {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().parse(sr, UnknownPrimaryKeyFieldType.type, (XmlOptions)null);
      }

      public static UnknownPrimaryKeyFieldType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().parse(sr, UnknownPrimaryKeyFieldType.type, options);
      }

      public static UnknownPrimaryKeyFieldType parse(Node node) throws XmlException {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().parse(node, UnknownPrimaryKeyFieldType.type, (XmlOptions)null);
      }

      public static UnknownPrimaryKeyFieldType parse(Node node, XmlOptions options) throws XmlException {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().parse(node, UnknownPrimaryKeyFieldType.type, options);
      }

      /** @deprecated */
      public static UnknownPrimaryKeyFieldType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().parse(xis, UnknownPrimaryKeyFieldType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static UnknownPrimaryKeyFieldType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (UnknownPrimaryKeyFieldType)XmlBeans.getContextTypeLoader().parse(xis, UnknownPrimaryKeyFieldType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UnknownPrimaryKeyFieldType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, UnknownPrimaryKeyFieldType.type, options);
      }

      private Factory() {
      }
   }
}
