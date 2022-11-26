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
import java.util.List;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface HandlerChainType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(HandlerChainType.class.getClassLoader(), "schemacom_bea_xml.system.com_oracle_core_coherence_descriptor_binding_3_0_0_0").resolveHandle("handlerchaintypebef9type");

   java.lang.String getServiceNamePattern();

   QnamePattern xgetServiceNamePattern();

   boolean isSetServiceNamePattern();

   void setServiceNamePattern(java.lang.String var1);

   void xsetServiceNamePattern(QnamePattern var1);

   void unsetServiceNamePattern();

   java.lang.String getPortNamePattern();

   QnamePattern xgetPortNamePattern();

   boolean isSetPortNamePattern();

   void setPortNamePattern(java.lang.String var1);

   void xsetPortNamePattern(QnamePattern var1);

   void unsetPortNamePattern();

   List getProtocolBindings();

   ProtocolBindingListType xgetProtocolBindings();

   boolean isSetProtocolBindings();

   void setProtocolBindings(List var1);

   void xsetProtocolBindings(ProtocolBindingListType var1);

   void unsetProtocolBindings();

   HandlerType[] getHandlerArray();

   HandlerType getHandlerArray(int var1);

   int sizeOfHandlerArray();

   void setHandlerArray(HandlerType[] var1);

   void setHandlerArray(int var1, HandlerType var2);

   HandlerType insertNewHandler(int var1);

   HandlerType addNewHandler();

   void removeHandler(int var1);

   java.lang.String getId();

   XmlID xgetId();

   boolean isSetId();

   void setId(java.lang.String var1);

   void xsetId(XmlID var1);

   void unsetId();

   public static final class Factory {
      public static HandlerChainType newInstance() {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().newInstance(HandlerChainType.type, (XmlOptions)null);
      }

      public static HandlerChainType newInstance(XmlOptions options) {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().newInstance(HandlerChainType.type, options);
      }

      public static HandlerChainType parse(java.lang.String xmlAsString) throws XmlException {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HandlerChainType.type, (XmlOptions)null);
      }

      public static HandlerChainType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().parse(xmlAsString, HandlerChainType.type, options);
      }

      public static HandlerChainType parse(File file) throws XmlException, IOException {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().parse(file, HandlerChainType.type, (XmlOptions)null);
      }

      public static HandlerChainType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().parse(file, HandlerChainType.type, options);
      }

      public static HandlerChainType parse(URL u) throws XmlException, IOException {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().parse(u, HandlerChainType.type, (XmlOptions)null);
      }

      public static HandlerChainType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().parse(u, HandlerChainType.type, options);
      }

      public static HandlerChainType parse(InputStream is) throws XmlException, IOException {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().parse(is, HandlerChainType.type, (XmlOptions)null);
      }

      public static HandlerChainType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().parse(is, HandlerChainType.type, options);
      }

      public static HandlerChainType parse(Reader r) throws XmlException, IOException {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().parse(r, HandlerChainType.type, (XmlOptions)null);
      }

      public static HandlerChainType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().parse(r, HandlerChainType.type, options);
      }

      public static HandlerChainType parse(XMLStreamReader sr) throws XmlException {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().parse(sr, HandlerChainType.type, (XmlOptions)null);
      }

      public static HandlerChainType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().parse(sr, HandlerChainType.type, options);
      }

      public static HandlerChainType parse(Node node) throws XmlException {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().parse(node, HandlerChainType.type, (XmlOptions)null);
      }

      public static HandlerChainType parse(Node node, XmlOptions options) throws XmlException {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().parse(node, HandlerChainType.type, options);
      }

      /** @deprecated */
      public static HandlerChainType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().parse(xis, HandlerChainType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static HandlerChainType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (HandlerChainType)XmlBeans.getContextTypeLoader().parse(xis, HandlerChainType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HandlerChainType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, HandlerChainType.type, options);
      }

      private Factory() {
      }
   }
}
