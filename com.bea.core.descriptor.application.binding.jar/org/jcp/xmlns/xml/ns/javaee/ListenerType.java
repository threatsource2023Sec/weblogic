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

public interface ListenerType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ListenerType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_application_binding_2_0_0_0").resolveHandle("listenertype0a57type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   DisplayNameType[] getDisplayNameArray();

   DisplayNameType getDisplayNameArray(int var1);

   int sizeOfDisplayNameArray();

   void setDisplayNameArray(DisplayNameType[] var1);

   void setDisplayNameArray(int var1, DisplayNameType var2);

   DisplayNameType insertNewDisplayName(int var1);

   DisplayNameType addNewDisplayName();

   void removeDisplayName(int var1);

   IconType[] getIconArray();

   IconType getIconArray(int var1);

   int sizeOfIconArray();

   void setIconArray(IconType[] var1);

   void setIconArray(int var1, IconType var2);

   IconType insertNewIcon(int var1);

   IconType addNewIcon();

   void removeIcon(int var1);

   FullyQualifiedClassType getListenerClass();

   void setListenerClass(FullyQualifiedClassType var1);

   FullyQualifiedClassType addNewListenerClass();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static ListenerType newInstance() {
         return (ListenerType)XmlBeans.getContextTypeLoader().newInstance(ListenerType.type, (XmlOptions)null);
      }

      public static ListenerType newInstance(XmlOptions options) {
         return (ListenerType)XmlBeans.getContextTypeLoader().newInstance(ListenerType.type, options);
      }

      public static ListenerType parse(java.lang.String xmlAsString) throws XmlException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ListenerType.type, (XmlOptions)null);
      }

      public static ListenerType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ListenerType.type, options);
      }

      public static ListenerType parse(File file) throws XmlException, IOException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(file, ListenerType.type, (XmlOptions)null);
      }

      public static ListenerType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(file, ListenerType.type, options);
      }

      public static ListenerType parse(URL u) throws XmlException, IOException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(u, ListenerType.type, (XmlOptions)null);
      }

      public static ListenerType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(u, ListenerType.type, options);
      }

      public static ListenerType parse(InputStream is) throws XmlException, IOException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(is, ListenerType.type, (XmlOptions)null);
      }

      public static ListenerType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(is, ListenerType.type, options);
      }

      public static ListenerType parse(Reader r) throws XmlException, IOException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(r, ListenerType.type, (XmlOptions)null);
      }

      public static ListenerType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(r, ListenerType.type, options);
      }

      public static ListenerType parse(XMLStreamReader sr) throws XmlException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(sr, ListenerType.type, (XmlOptions)null);
      }

      public static ListenerType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(sr, ListenerType.type, options);
      }

      public static ListenerType parse(Node node) throws XmlException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(node, ListenerType.type, (XmlOptions)null);
      }

      public static ListenerType parse(Node node, XmlOptions options) throws XmlException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(node, ListenerType.type, options);
      }

      /** @deprecated */
      public static ListenerType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(xis, ListenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ListenerType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ListenerType)XmlBeans.getContextTypeLoader().parse(xis, ListenerType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ListenerType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ListenerType.type, options);
      }

      private Factory() {
      }
   }
}
