package com.oracle.xmlns.weblogic.weblogicApplication;

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

public interface GroupNameType extends XmlString {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(GroupNameType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("groupnametype61f4type");

   String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static GroupNameType newInstance() {
         return (GroupNameType)XmlBeans.getContextTypeLoader().newInstance(GroupNameType.type, (XmlOptions)null);
      }

      public static GroupNameType newInstance(XmlOptions options) {
         return (GroupNameType)XmlBeans.getContextTypeLoader().newInstance(GroupNameType.type, options);
      }

      public static GroupNameType parse(String xmlAsString) throws XmlException {
         return (GroupNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GroupNameType.type, (XmlOptions)null);
      }

      public static GroupNameType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (GroupNameType)XmlBeans.getContextTypeLoader().parse(xmlAsString, GroupNameType.type, options);
      }

      public static GroupNameType parse(File file) throws XmlException, IOException {
         return (GroupNameType)XmlBeans.getContextTypeLoader().parse(file, GroupNameType.type, (XmlOptions)null);
      }

      public static GroupNameType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (GroupNameType)XmlBeans.getContextTypeLoader().parse(file, GroupNameType.type, options);
      }

      public static GroupNameType parse(URL u) throws XmlException, IOException {
         return (GroupNameType)XmlBeans.getContextTypeLoader().parse(u, GroupNameType.type, (XmlOptions)null);
      }

      public static GroupNameType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (GroupNameType)XmlBeans.getContextTypeLoader().parse(u, GroupNameType.type, options);
      }

      public static GroupNameType parse(InputStream is) throws XmlException, IOException {
         return (GroupNameType)XmlBeans.getContextTypeLoader().parse(is, GroupNameType.type, (XmlOptions)null);
      }

      public static GroupNameType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (GroupNameType)XmlBeans.getContextTypeLoader().parse(is, GroupNameType.type, options);
      }

      public static GroupNameType parse(Reader r) throws XmlException, IOException {
         return (GroupNameType)XmlBeans.getContextTypeLoader().parse(r, GroupNameType.type, (XmlOptions)null);
      }

      public static GroupNameType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (GroupNameType)XmlBeans.getContextTypeLoader().parse(r, GroupNameType.type, options);
      }

      public static GroupNameType parse(XMLStreamReader sr) throws XmlException {
         return (GroupNameType)XmlBeans.getContextTypeLoader().parse(sr, GroupNameType.type, (XmlOptions)null);
      }

      public static GroupNameType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (GroupNameType)XmlBeans.getContextTypeLoader().parse(sr, GroupNameType.type, options);
      }

      public static GroupNameType parse(Node node) throws XmlException {
         return (GroupNameType)XmlBeans.getContextTypeLoader().parse(node, GroupNameType.type, (XmlOptions)null);
      }

      public static GroupNameType parse(Node node, XmlOptions options) throws XmlException {
         return (GroupNameType)XmlBeans.getContextTypeLoader().parse(node, GroupNameType.type, options);
      }

      /** @deprecated */
      public static GroupNameType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (GroupNameType)XmlBeans.getContextTypeLoader().parse(xis, GroupNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static GroupNameType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (GroupNameType)XmlBeans.getContextTypeLoader().parse(xis, GroupNameType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GroupNameType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, GroupNameType.type, options);
      }

      private Factory() {
      }
   }
}
