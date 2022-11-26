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

public interface RemoveMethodType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(RemoveMethodType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("removemethodtype2973type");

   NamedMethodType getBeanMethod();

   void setBeanMethod(NamedMethodType var1);

   NamedMethodType addNewBeanMethod();

   TrueFalseType getRetainIfException();

   boolean isSetRetainIfException();

   void setRetainIfException(TrueFalseType var1);

   TrueFalseType addNewRetainIfException();

   void unsetRetainIfException();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static RemoveMethodType newInstance() {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().newInstance(RemoveMethodType.type, (XmlOptions)null);
      }

      public static RemoveMethodType newInstance(XmlOptions options) {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().newInstance(RemoveMethodType.type, options);
      }

      public static RemoveMethodType parse(java.lang.String xmlAsString) throws XmlException {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RemoveMethodType.type, (XmlOptions)null);
      }

      public static RemoveMethodType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().parse(xmlAsString, RemoveMethodType.type, options);
      }

      public static RemoveMethodType parse(File file) throws XmlException, IOException {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().parse(file, RemoveMethodType.type, (XmlOptions)null);
      }

      public static RemoveMethodType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().parse(file, RemoveMethodType.type, options);
      }

      public static RemoveMethodType parse(URL u) throws XmlException, IOException {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().parse(u, RemoveMethodType.type, (XmlOptions)null);
      }

      public static RemoveMethodType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().parse(u, RemoveMethodType.type, options);
      }

      public static RemoveMethodType parse(InputStream is) throws XmlException, IOException {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().parse(is, RemoveMethodType.type, (XmlOptions)null);
      }

      public static RemoveMethodType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().parse(is, RemoveMethodType.type, options);
      }

      public static RemoveMethodType parse(Reader r) throws XmlException, IOException {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().parse(r, RemoveMethodType.type, (XmlOptions)null);
      }

      public static RemoveMethodType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().parse(r, RemoveMethodType.type, options);
      }

      public static RemoveMethodType parse(XMLStreamReader sr) throws XmlException {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().parse(sr, RemoveMethodType.type, (XmlOptions)null);
      }

      public static RemoveMethodType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().parse(sr, RemoveMethodType.type, options);
      }

      public static RemoveMethodType parse(Node node) throws XmlException {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().parse(node, RemoveMethodType.type, (XmlOptions)null);
      }

      public static RemoveMethodType parse(Node node, XmlOptions options) throws XmlException {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().parse(node, RemoveMethodType.type, options);
      }

      /** @deprecated */
      public static RemoveMethodType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().parse(xis, RemoveMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static RemoveMethodType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (RemoveMethodType)XmlBeans.getContextTypeLoader().parse(xis, RemoveMethodType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RemoveMethodType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, RemoveMethodType.type, options);
      }

      private Factory() {
      }
   }
}
