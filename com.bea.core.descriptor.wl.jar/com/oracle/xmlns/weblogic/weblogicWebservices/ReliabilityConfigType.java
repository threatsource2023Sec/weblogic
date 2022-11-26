package com.oracle.xmlns.weblogic.weblogicWebservices;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.sun.java.xml.ns.j2Ee.String;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface ReliabilityConfigType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(ReliabilityConfigType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("reliabilityconfigtypedf72type");

   boolean getCustomized();

   XmlBoolean xgetCustomized();

   boolean isSetCustomized();

   void setCustomized(boolean var1);

   void xsetCustomized(XmlBoolean var1);

   void unsetCustomized();

   String getInactivityTimeout();

   boolean isSetInactivityTimeout();

   void setInactivityTimeout(String var1);

   String addNewInactivityTimeout();

   void unsetInactivityTimeout();

   String getBaseRetransmissionInterval();

   boolean isSetBaseRetransmissionInterval();

   void setBaseRetransmissionInterval(String var1);

   String addNewBaseRetransmissionInterval();

   void unsetBaseRetransmissionInterval();

   boolean getRetransmissionExponentialBackoff();

   XmlBoolean xgetRetransmissionExponentialBackoff();

   boolean isSetRetransmissionExponentialBackoff();

   void setRetransmissionExponentialBackoff(boolean var1);

   void xsetRetransmissionExponentialBackoff(XmlBoolean var1);

   void unsetRetransmissionExponentialBackoff();

   boolean getNonBufferedSource();

   XmlBoolean xgetNonBufferedSource();

   boolean isSetNonBufferedSource();

   void setNonBufferedSource(boolean var1);

   void xsetNonBufferedSource(XmlBoolean var1);

   void unsetNonBufferedSource();

   String getAcknowledgementInterval();

   boolean isSetAcknowledgementInterval();

   void setAcknowledgementInterval(String var1);

   String addNewAcknowledgementInterval();

   void unsetAcknowledgementInterval();

   String getSequenceExpiration();

   boolean isSetSequenceExpiration();

   void setSequenceExpiration(String var1);

   String addNewSequenceExpiration();

   void unsetSequenceExpiration();

   int getBufferRetryCount();

   XmlInt xgetBufferRetryCount();

   boolean isSetBufferRetryCount();

   void setBufferRetryCount(int var1);

   void xsetBufferRetryCount(XmlInt var1);

   void unsetBufferRetryCount();

   String getBufferRetryDelay();

   boolean isSetBufferRetryDelay();

   void setBufferRetryDelay(String var1);

   String addNewBufferRetryDelay();

   void unsetBufferRetryDelay();

   boolean getNonBufferedDestination();

   XmlBoolean xgetNonBufferedDestination();

   boolean isSetNonBufferedDestination();

   void setNonBufferedDestination(boolean var1);

   void xsetNonBufferedDestination(XmlBoolean var1);

   void unsetNonBufferedDestination();

   String getMessagingQueueJndiName();

   boolean isSetMessagingQueueJndiName();

   void setMessagingQueueJndiName(String var1);

   String addNewMessagingQueueJndiName();

   void unsetMessagingQueueJndiName();

   String getMessagingQueueMdbRunAsPrincipalName();

   boolean isSetMessagingQueueMdbRunAsPrincipalName();

   void setMessagingQueueMdbRunAsPrincipalName(String var1);

   String addNewMessagingQueueMdbRunAsPrincipalName();

   void unsetMessagingQueueMdbRunAsPrincipalName();

   public static final class Factory {
      public static ReliabilityConfigType newInstance() {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().newInstance(ReliabilityConfigType.type, (XmlOptions)null);
      }

      public static ReliabilityConfigType newInstance(XmlOptions options) {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().newInstance(ReliabilityConfigType.type, options);
      }

      public static ReliabilityConfigType parse(java.lang.String xmlAsString) throws XmlException {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ReliabilityConfigType.type, (XmlOptions)null);
      }

      public static ReliabilityConfigType parse(java.lang.String xmlAsString, XmlOptions options) throws XmlException {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().parse(xmlAsString, ReliabilityConfigType.type, options);
      }

      public static ReliabilityConfigType parse(File file) throws XmlException, IOException {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().parse(file, ReliabilityConfigType.type, (XmlOptions)null);
      }

      public static ReliabilityConfigType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().parse(file, ReliabilityConfigType.type, options);
      }

      public static ReliabilityConfigType parse(URL u) throws XmlException, IOException {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().parse(u, ReliabilityConfigType.type, (XmlOptions)null);
      }

      public static ReliabilityConfigType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().parse(u, ReliabilityConfigType.type, options);
      }

      public static ReliabilityConfigType parse(InputStream is) throws XmlException, IOException {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().parse(is, ReliabilityConfigType.type, (XmlOptions)null);
      }

      public static ReliabilityConfigType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().parse(is, ReliabilityConfigType.type, options);
      }

      public static ReliabilityConfigType parse(Reader r) throws XmlException, IOException {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().parse(r, ReliabilityConfigType.type, (XmlOptions)null);
      }

      public static ReliabilityConfigType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().parse(r, ReliabilityConfigType.type, options);
      }

      public static ReliabilityConfigType parse(XMLStreamReader sr) throws XmlException {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().parse(sr, ReliabilityConfigType.type, (XmlOptions)null);
      }

      public static ReliabilityConfigType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().parse(sr, ReliabilityConfigType.type, options);
      }

      public static ReliabilityConfigType parse(Node node) throws XmlException {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().parse(node, ReliabilityConfigType.type, (XmlOptions)null);
      }

      public static ReliabilityConfigType parse(Node node, XmlOptions options) throws XmlException {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().parse(node, ReliabilityConfigType.type, options);
      }

      /** @deprecated */
      public static ReliabilityConfigType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().parse(xis, ReliabilityConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static ReliabilityConfigType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (ReliabilityConfigType)XmlBeans.getContextTypeLoader().parse(xis, ReliabilityConfigType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ReliabilityConfigType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, ReliabilityConfigType.type, options);
      }

      private Factory() {
      }
   }
}
