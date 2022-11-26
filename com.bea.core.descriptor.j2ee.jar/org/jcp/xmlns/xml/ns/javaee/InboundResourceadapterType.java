package org.jcp.xmlns.xml.ns.javaee;

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

public interface InboundResourceadapterType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(InboundResourceadapterType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("inboundresourceadaptertypef9f0type");

   MessageadapterType getMessageadapter();

   boolean isSetMessageadapter();

   void setMessageadapter(MessageadapterType var1);

   MessageadapterType addNewMessageadapter();

   void unsetMessageadapter();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static InboundResourceadapterType newInstance() {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().newInstance(InboundResourceadapterType.type, (XmlOptions)null);
      }

      public static InboundResourceadapterType newInstance(XmlOptions options) {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().newInstance(InboundResourceadapterType.type, options);
      }

      public static InboundResourceadapterType parse(java.lang.String xmlAsString) throws XmlException {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InboundResourceadapterType.type, (XmlOptions)null);
      }

      public static InboundResourceadapterType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(xmlAsString, InboundResourceadapterType.type, options);
      }

      public static InboundResourceadapterType parse(File file) throws XmlException, IOException {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(file, InboundResourceadapterType.type, (XmlOptions)null);
      }

      public static InboundResourceadapterType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(file, InboundResourceadapterType.type, options);
      }

      public static InboundResourceadapterType parse(URL u) throws XmlException, IOException {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(u, InboundResourceadapterType.type, (XmlOptions)null);
      }

      public static InboundResourceadapterType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(u, InboundResourceadapterType.type, options);
      }

      public static InboundResourceadapterType parse(InputStream is) throws XmlException, IOException {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(is, InboundResourceadapterType.type, (XmlOptions)null);
      }

      public static InboundResourceadapterType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(is, InboundResourceadapterType.type, options);
      }

      public static InboundResourceadapterType parse(Reader r) throws XmlException, IOException {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(r, InboundResourceadapterType.type, (XmlOptions)null);
      }

      public static InboundResourceadapterType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(r, InboundResourceadapterType.type, options);
      }

      public static InboundResourceadapterType parse(XMLStreamReader sr) throws XmlException {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(sr, InboundResourceadapterType.type, (XmlOptions)null);
      }

      public static InboundResourceadapterType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(sr, InboundResourceadapterType.type, options);
      }

      public static InboundResourceadapterType parse(Node node) throws XmlException {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(node, InboundResourceadapterType.type, (XmlOptions)null);
      }

      public static InboundResourceadapterType parse(Node node, XmlOptions options) throws XmlException {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(node, InboundResourceadapterType.type, options);
      }

      /** @deprecated */
      public static InboundResourceadapterType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(xis, InboundResourceadapterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static InboundResourceadapterType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (InboundResourceadapterType)XmlBeans.getContextTypeLoader().parse(xis, InboundResourceadapterType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InboundResourceadapterType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, InboundResourceadapterType.type, options);
      }

      private Factory() {
      }
   }
}
