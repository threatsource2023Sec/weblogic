package com.sun.java.xml.ns.javaee;

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

public interface MessageDestinationRefType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(MessageDestinationRefType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_j2ee_3_0_0_0").resolveHandle("messagedestinationreftype5449type");

   DescriptionType[] getDescriptionArray();

   DescriptionType getDescriptionArray(int var1);

   int sizeOfDescriptionArray();

   void setDescriptionArray(DescriptionType[] var1);

   void setDescriptionArray(int var1, DescriptionType var2);

   DescriptionType insertNewDescription(int var1);

   DescriptionType addNewDescription();

   void removeDescription(int var1);

   JndiNameType getMessageDestinationRefName();

   void setMessageDestinationRefName(JndiNameType var1);

   JndiNameType addNewMessageDestinationRefName();

   MessageDestinationTypeType getMessageDestinationType();

   boolean isSetMessageDestinationType();

   void setMessageDestinationType(MessageDestinationTypeType var1);

   MessageDestinationTypeType addNewMessageDestinationType();

   void unsetMessageDestinationType();

   MessageDestinationUsageType getMessageDestinationUsage();

   boolean isSetMessageDestinationUsage();

   void setMessageDestinationUsage(MessageDestinationUsageType var1);

   MessageDestinationUsageType addNewMessageDestinationUsage();

   void unsetMessageDestinationUsage();

   MessageDestinationLinkType getMessageDestinationLink();

   boolean isSetMessageDestinationLink();

   void setMessageDestinationLink(MessageDestinationLinkType var1);

   MessageDestinationLinkType addNewMessageDestinationLink();

   void unsetMessageDestinationLink();

   XsdStringType getMappedName();

   boolean isSetMappedName();

   void setMappedName(XsdStringType var1);

   XsdStringType addNewMappedName();

   void unsetMappedName();

   InjectionTargetType[] getInjectionTargetArray();

   InjectionTargetType getInjectionTargetArray(int var1);

   int sizeOfInjectionTargetArray();

   void setInjectionTargetArray(InjectionTargetType[] var1);

   void setInjectionTargetArray(int var1, InjectionTargetType var2);

   InjectionTargetType insertNewInjectionTarget(int var1);

   InjectionTargetType addNewInjectionTarget();

   void removeInjectionTarget(int var1);

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
      public static MessageDestinationRefType newInstance() {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().newInstance(MessageDestinationRefType.type, (XmlOptions)null);
      }

      public static MessageDestinationRefType newInstance(XmlOptions options) {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().newInstance(MessageDestinationRefType.type, options);
      }

      public static MessageDestinationRefType parse(java.lang.String xmlAsString) throws XmlException {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageDestinationRefType.type, (XmlOptions)null);
      }

      public static MessageDestinationRefType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().parse(xmlAsString, MessageDestinationRefType.type, options);
      }

      public static MessageDestinationRefType parse(File file) throws XmlException, IOException {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().parse(file, MessageDestinationRefType.type, (XmlOptions)null);
      }

      public static MessageDestinationRefType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().parse(file, MessageDestinationRefType.type, options);
      }

      public static MessageDestinationRefType parse(URL u) throws XmlException, IOException {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().parse(u, MessageDestinationRefType.type, (XmlOptions)null);
      }

      public static MessageDestinationRefType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().parse(u, MessageDestinationRefType.type, options);
      }

      public static MessageDestinationRefType parse(InputStream is) throws XmlException, IOException {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().parse(is, MessageDestinationRefType.type, (XmlOptions)null);
      }

      public static MessageDestinationRefType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().parse(is, MessageDestinationRefType.type, options);
      }

      public static MessageDestinationRefType parse(Reader r) throws XmlException, IOException {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().parse(r, MessageDestinationRefType.type, (XmlOptions)null);
      }

      public static MessageDestinationRefType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().parse(r, MessageDestinationRefType.type, options);
      }

      public static MessageDestinationRefType parse(XMLStreamReader sr) throws XmlException {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().parse(sr, MessageDestinationRefType.type, (XmlOptions)null);
      }

      public static MessageDestinationRefType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().parse(sr, MessageDestinationRefType.type, options);
      }

      public static MessageDestinationRefType parse(Node node) throws XmlException {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().parse(node, MessageDestinationRefType.type, (XmlOptions)null);
      }

      public static MessageDestinationRefType parse(Node node, XmlOptions options) throws XmlException {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().parse(node, MessageDestinationRefType.type, options);
      }

      /** @deprecated */
      public static MessageDestinationRefType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().parse(xis, MessageDestinationRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static MessageDestinationRefType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (MessageDestinationRefType)XmlBeans.getContextTypeLoader().parse(xis, MessageDestinationRefType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageDestinationRefType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, MessageDestinationRefType.type, options);
      }

      private Factory() {
      }
   }
}
