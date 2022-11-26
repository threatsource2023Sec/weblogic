package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlException;
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

public interface DistributedTopicType extends DistributedDestinationType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DistributedTopicType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("distributedtopictype3f83type");

   DistributedDestinationMemberType[] getDistributedTopicMemberArray();

   DistributedDestinationMemberType getDistributedTopicMemberArray(int var1);

   int sizeOfDistributedTopicMemberArray();

   void setDistributedTopicMemberArray(DistributedDestinationMemberType[] var1);

   void setDistributedTopicMemberArray(int var1, DistributedDestinationMemberType var2);

   DistributedDestinationMemberType insertNewDistributedTopicMember(int var1);

   DistributedDestinationMemberType addNewDistributedTopicMember();

   void removeDistributedTopicMember(int var1);

   public static final class Factory {
      public static DistributedTopicType newInstance() {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().newInstance(DistributedTopicType.type, (XmlOptions)null);
      }

      public static DistributedTopicType newInstance(XmlOptions options) {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().newInstance(DistributedTopicType.type, options);
      }

      public static DistributedTopicType parse(String xmlAsString) throws XmlException {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DistributedTopicType.type, (XmlOptions)null);
      }

      public static DistributedTopicType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DistributedTopicType.type, options);
      }

      public static DistributedTopicType parse(File file) throws XmlException, IOException {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().parse(file, DistributedTopicType.type, (XmlOptions)null);
      }

      public static DistributedTopicType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().parse(file, DistributedTopicType.type, options);
      }

      public static DistributedTopicType parse(URL u) throws XmlException, IOException {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().parse(u, DistributedTopicType.type, (XmlOptions)null);
      }

      public static DistributedTopicType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().parse(u, DistributedTopicType.type, options);
      }

      public static DistributedTopicType parse(InputStream is) throws XmlException, IOException {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().parse(is, DistributedTopicType.type, (XmlOptions)null);
      }

      public static DistributedTopicType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().parse(is, DistributedTopicType.type, options);
      }

      public static DistributedTopicType parse(Reader r) throws XmlException, IOException {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().parse(r, DistributedTopicType.type, (XmlOptions)null);
      }

      public static DistributedTopicType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().parse(r, DistributedTopicType.type, options);
      }

      public static DistributedTopicType parse(XMLStreamReader sr) throws XmlException {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().parse(sr, DistributedTopicType.type, (XmlOptions)null);
      }

      public static DistributedTopicType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().parse(sr, DistributedTopicType.type, options);
      }

      public static DistributedTopicType parse(Node node) throws XmlException {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().parse(node, DistributedTopicType.type, (XmlOptions)null);
      }

      public static DistributedTopicType parse(Node node, XmlOptions options) throws XmlException {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().parse(node, DistributedTopicType.type, options);
      }

      /** @deprecated */
      public static DistributedTopicType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().parse(xis, DistributedTopicType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DistributedTopicType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DistributedTopicType)XmlBeans.getContextTypeLoader().parse(xis, DistributedTopicType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DistributedTopicType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DistributedTopicType.type, options);
      }

      private Factory() {
      }
   }
}
