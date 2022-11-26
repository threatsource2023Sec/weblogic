package org.jcp.xmlns.xml.ns.javaee;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
import com.bea.xml.XmlOptions;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ProtocolBindingListType extends XmlAnySimpleType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ProtocolBindingListType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("protocolbindinglisttypefe3dtype");

   List getListValue();

   List xgetListValue();

   void setListValue(List var1);

   /** @deprecated */
   List listValue();

   /** @deprecated */
   List xlistValue();

   /** @deprecated */
   void set(List var1);

   public static final class Factory {
      public static ProtocolBindingListType newValue(Object obj) {
         return (ProtocolBindingListType)ProtocolBindingListType.type.newValue(obj);
      }

      public static ProtocolBindingListType newInstance() {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().newInstance(ProtocolBindingListType.type, (XmlOptions)null);
      }

      public static ProtocolBindingListType newInstance(XmlOptions options) {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().newInstance(ProtocolBindingListType.type, options);
      }

      public static ProtocolBindingListType parse(java.lang.String xmlAsString) throws XmlException {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProtocolBindingListType.type, (XmlOptions)null);
      }

      public static ProtocolBindingListType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ProtocolBindingListType.type, options);
      }

      public static ProtocolBindingListType parse(File file) throws XmlException, IOException {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().parse(file, ProtocolBindingListType.type, (XmlOptions)null);
      }

      public static ProtocolBindingListType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().parse(file, ProtocolBindingListType.type, options);
      }

      public static ProtocolBindingListType parse(URL u) throws XmlException, IOException {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().parse(u, ProtocolBindingListType.type, (XmlOptions)null);
      }

      public static ProtocolBindingListType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().parse(u, ProtocolBindingListType.type, options);
      }

      public static ProtocolBindingListType parse(InputStream is) throws XmlException, IOException {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().parse(is, ProtocolBindingListType.type, (XmlOptions)null);
      }

      public static ProtocolBindingListType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().parse(is, ProtocolBindingListType.type, options);
      }

      public static ProtocolBindingListType parse(Reader r) throws XmlException, IOException {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().parse(r, ProtocolBindingListType.type, (XmlOptions)null);
      }

      public static ProtocolBindingListType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().parse(r, ProtocolBindingListType.type, options);
      }

      public static ProtocolBindingListType parse(XMLStreamReader sr) throws XmlException {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().parse(sr, ProtocolBindingListType.type, (XmlOptions)null);
      }

      public static ProtocolBindingListType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().parse(sr, ProtocolBindingListType.type, options);
      }

      public static ProtocolBindingListType parse(Node node) throws XmlException {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().parse(node, ProtocolBindingListType.type, (XmlOptions)null);
      }

      public static ProtocolBindingListType parse(Node node, XmlOptions options) throws XmlException {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().parse(node, ProtocolBindingListType.type, options);
      }

      /** @deprecated */
      public static ProtocolBindingListType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().parse(xis, ProtocolBindingListType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ProtocolBindingListType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ProtocolBindingListType)XmlBeans.getContextTypeLoader().parse(xis, ProtocolBindingListType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProtocolBindingListType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ProtocolBindingListType.type, options);
      }

      private Factory() {
      }
   }
}
