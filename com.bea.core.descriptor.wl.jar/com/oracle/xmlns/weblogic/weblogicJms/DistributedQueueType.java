package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
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

public interface DistributedQueueType extends DistributedDestinationType {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DistributedQueueType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("distributedqueuetype5c61type");

   DistributedDestinationMemberType[] getDistributedQueueMemberArray();

   DistributedDestinationMemberType getDistributedQueueMemberArray(int var1);

   int sizeOfDistributedQueueMemberArray();

   void setDistributedQueueMemberArray(DistributedDestinationMemberType[] var1);

   void setDistributedQueueMemberArray(int var1, DistributedDestinationMemberType var2);

   DistributedDestinationMemberType insertNewDistributedQueueMember(int var1);

   DistributedDestinationMemberType addNewDistributedQueueMember();

   void removeDistributedQueueMember(int var1);

   int getForwardDelay();

   XmlInt xgetForwardDelay();

   boolean isSetForwardDelay();

   void setForwardDelay(int var1);

   void xsetForwardDelay(XmlInt var1);

   void unsetForwardDelay();

   boolean getResetDeliveryCountOnForward();

   XmlBoolean xgetResetDeliveryCountOnForward();

   boolean isSetResetDeliveryCountOnForward();

   void setResetDeliveryCountOnForward(boolean var1);

   void xsetResetDeliveryCountOnForward(XmlBoolean var1);

   void unsetResetDeliveryCountOnForward();

   public static final class Factory {
      public static DistributedQueueType newInstance() {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().newInstance(DistributedQueueType.type, (XmlOptions)null);
      }

      public static DistributedQueueType newInstance(XmlOptions options) {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().newInstance(DistributedQueueType.type, options);
      }

      public static DistributedQueueType parse(String xmlAsString) throws XmlException {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DistributedQueueType.type, (XmlOptions)null);
      }

      public static DistributedQueueType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DistributedQueueType.type, options);
      }

      public static DistributedQueueType parse(File file) throws XmlException, IOException {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().parse(file, DistributedQueueType.type, (XmlOptions)null);
      }

      public static DistributedQueueType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().parse(file, DistributedQueueType.type, options);
      }

      public static DistributedQueueType parse(URL u) throws XmlException, IOException {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().parse(u, DistributedQueueType.type, (XmlOptions)null);
      }

      public static DistributedQueueType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().parse(u, DistributedQueueType.type, options);
      }

      public static DistributedQueueType parse(InputStream is) throws XmlException, IOException {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().parse(is, DistributedQueueType.type, (XmlOptions)null);
      }

      public static DistributedQueueType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().parse(is, DistributedQueueType.type, options);
      }

      public static DistributedQueueType parse(Reader r) throws XmlException, IOException {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().parse(r, DistributedQueueType.type, (XmlOptions)null);
      }

      public static DistributedQueueType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().parse(r, DistributedQueueType.type, options);
      }

      public static DistributedQueueType parse(XMLStreamReader sr) throws XmlException {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().parse(sr, DistributedQueueType.type, (XmlOptions)null);
      }

      public static DistributedQueueType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().parse(sr, DistributedQueueType.type, options);
      }

      public static DistributedQueueType parse(Node node) throws XmlException {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().parse(node, DistributedQueueType.type, (XmlOptions)null);
      }

      public static DistributedQueueType parse(Node node, XmlOptions options) throws XmlException {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().parse(node, DistributedQueueType.type, options);
      }

      /** @deprecated */
      public static DistributedQueueType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().parse(xis, DistributedQueueType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DistributedQueueType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DistributedQueueType)XmlBeans.getContextTypeLoader().parse(xis, DistributedQueueType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DistributedQueueType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DistributedQueueType.type, options);
      }

      private Factory() {
      }
   }
}
