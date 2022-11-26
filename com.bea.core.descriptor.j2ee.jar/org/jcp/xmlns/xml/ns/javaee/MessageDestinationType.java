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

public interface MessageDestinationType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MessageDestinationType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("messagedestinationtype37f5type");

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

   String getMessageDestinationName();

   void setMessageDestinationName(String var1);

   String addNewMessageDestinationName();

   XsdStringType getMappedName();

   boolean isSetMappedName();

   void setMappedName(XsdStringType var1);

   XsdStringType addNewMappedName();

   void unsetMappedName();

   XsdStringType getLookupName();

   boolean isSetLookupName();

   void setLookupName(XsdStringType var1);

   XsdStringType addNewLookupName();

   void unsetLookupName();

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static MessageDestinationType newInstance() {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().newInstance(MessageDestinationType.type, (XmlOptions)null);
      }

      public static MessageDestinationType newInstance(XmlOptions options) {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().newInstance(MessageDestinationType.type, options);
      }

      public static MessageDestinationType parse(java.lang.String xmlAsString) throws XmlException {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageDestinationType.type, (XmlOptions)null);
      }

      public static MessageDestinationType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageDestinationType.type, options);
      }

      public static MessageDestinationType parse(File file) throws XmlException, IOException {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().parse(file, MessageDestinationType.type, (XmlOptions)null);
      }

      public static MessageDestinationType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().parse(file, MessageDestinationType.type, options);
      }

      public static MessageDestinationType parse(URL u) throws XmlException, IOException {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().parse(u, MessageDestinationType.type, (XmlOptions)null);
      }

      public static MessageDestinationType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().parse(u, MessageDestinationType.type, options);
      }

      public static MessageDestinationType parse(InputStream is) throws XmlException, IOException {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().parse(is, MessageDestinationType.type, (XmlOptions)null);
      }

      public static MessageDestinationType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().parse(is, MessageDestinationType.type, options);
      }

      public static MessageDestinationType parse(Reader r) throws XmlException, IOException {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().parse(r, MessageDestinationType.type, (XmlOptions)null);
      }

      public static MessageDestinationType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().parse(r, MessageDestinationType.type, options);
      }

      public static MessageDestinationType parse(XMLStreamReader sr) throws XmlException {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().parse(sr, MessageDestinationType.type, (XmlOptions)null);
      }

      public static MessageDestinationType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().parse(sr, MessageDestinationType.type, options);
      }

      public static MessageDestinationType parse(Node node) throws XmlException {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().parse(node, MessageDestinationType.type, (XmlOptions)null);
      }

      public static MessageDestinationType parse(Node node, XmlOptions options) throws XmlException {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().parse(node, MessageDestinationType.type, options);
      }

      /** @deprecated */
      public static MessageDestinationType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().parse(xis, MessageDestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MessageDestinationType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MessageDestinationType)XmlBeans.getContextTypeLoader().parse(xis, MessageDestinationType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageDestinationType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageDestinationType.type, options);
      }

      private Factory() {
      }
   }
}
