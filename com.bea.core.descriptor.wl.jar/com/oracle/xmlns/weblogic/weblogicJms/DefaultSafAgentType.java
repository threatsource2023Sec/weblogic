package com.oracle.xmlns.weblogic.weblogicJms;

import com.bea.xml.SchemaType;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlDouble;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;

public interface DefaultSafAgentType extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(DefaultSafAgentType.class.getClassLoader(), "schemacom_bea_xml.system.com_bea_core_descriptor_wl_4_0_0_0").resolveHandle("defaultsafagenttype7264type");

   String getNotes();

   XmlString xgetNotes();

   boolean isNilNotes();

   boolean isSetNotes();

   void setNotes(String var1);

   void xsetNotes(XmlString var1);

   void setNilNotes();

   void unsetNotes();

   long getBytesMaximum();

   XmlLong xgetBytesMaximum();

   boolean isSetBytesMaximum();

   void setBytesMaximum(long var1);

   void xsetBytesMaximum(XmlLong var1);

   void unsetBytesMaximum();

   long getMessagesMaximum();

   XmlLong xgetMessagesMaximum();

   boolean isSetMessagesMaximum();

   void setMessagesMaximum(long var1);

   void xsetMessagesMaximum(XmlLong var1);

   void unsetMessagesMaximum();

   int getMaximumMessageSize();

   XmlInt xgetMaximumMessageSize();

   boolean isSetMaximumMessageSize();

   void setMaximumMessageSize(int var1);

   void xsetMaximumMessageSize(XmlInt var1);

   void unsetMaximumMessageSize();

   long getDefaultRetryDelayBase();

   XmlLong xgetDefaultRetryDelayBase();

   boolean isSetDefaultRetryDelayBase();

   void setDefaultRetryDelayBase(long var1);

   void xsetDefaultRetryDelayBase(XmlLong var1);

   void unsetDefaultRetryDelayBase();

   long getDefaultRetryDelayMaximum();

   XmlLong xgetDefaultRetryDelayMaximum();

   boolean isSetDefaultRetryDelayMaximum();

   void setDefaultRetryDelayMaximum(long var1);

   void xsetDefaultRetryDelayMaximum(XmlLong var1);

   void unsetDefaultRetryDelayMaximum();

   double getDefaultRetryDelayMultiplier();

   XmlDouble xgetDefaultRetryDelayMultiplier();

   boolean isSetDefaultRetryDelayMultiplier();

   void setDefaultRetryDelayMultiplier(double var1);

   void xsetDefaultRetryDelayMultiplier(XmlDouble var1);

   void unsetDefaultRetryDelayMultiplier();

   int getWindowSize();

   XmlInt xgetWindowSize();

   boolean isSetWindowSize();

   void setWindowSize(int var1);

   void xsetWindowSize(XmlInt var1);

   void unsetWindowSize();

   boolean getLoggingEnabled();

   XmlBoolean xgetLoggingEnabled();

   boolean isSetLoggingEnabled();

   void setLoggingEnabled(boolean var1);

   void xsetLoggingEnabled(XmlBoolean var1);

   void unsetLoggingEnabled();

   long getDefaultTimeToLive();

   XmlLong xgetDefaultTimeToLive();

   boolean isSetDefaultTimeToLive();

   void setDefaultTimeToLive(long var1);

   void xsetDefaultTimeToLive(XmlLong var1);

   void unsetDefaultTimeToLive();

   long getMessageBufferSize();

   XmlLong xgetMessageBufferSize();

   boolean isSetMessageBufferSize();

   void setMessageBufferSize(long var1);

   void xsetMessageBufferSize(XmlLong var1);

   void unsetMessageBufferSize();

   String getPagingDirectory();

   XmlString xgetPagingDirectory();

   boolean isNilPagingDirectory();

   boolean isSetPagingDirectory();

   void setPagingDirectory(String var1);

   void xsetPagingDirectory(XmlString var1);

   void setNilPagingDirectory();

   void unsetPagingDirectory();

   long getWindowInterval();

   XmlLong xgetWindowInterval();

   boolean isSetWindowInterval();

   void setWindowInterval(long var1);

   void xsetWindowInterval(XmlLong var1);

   void unsetWindowInterval();

   public static final class Factory {
      public static DefaultSafAgentType newInstance() {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().newInstance(DefaultSafAgentType.type, (XmlOptions)null);
      }

      public static DefaultSafAgentType newInstance(XmlOptions options) {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().newInstance(DefaultSafAgentType.type, options);
      }

      public static DefaultSafAgentType parse(String xmlAsString) throws XmlException {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultSafAgentType.type, (XmlOptions)null);
      }

      public static DefaultSafAgentType parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().parse(xmlAsString, DefaultSafAgentType.type, options);
      }

      public static DefaultSafAgentType parse(File file) throws XmlException, IOException {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().parse(file, DefaultSafAgentType.type, (XmlOptions)null);
      }

      public static DefaultSafAgentType parse(File file, XmlOptions options) throws XmlException, IOException {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().parse(file, DefaultSafAgentType.type, options);
      }

      public static DefaultSafAgentType parse(URL u) throws XmlException, IOException {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().parse(u, DefaultSafAgentType.type, (XmlOptions)null);
      }

      public static DefaultSafAgentType parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().parse(u, DefaultSafAgentType.type, options);
      }

      public static DefaultSafAgentType parse(InputStream is) throws XmlException, IOException {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().parse(is, DefaultSafAgentType.type, (XmlOptions)null);
      }

      public static DefaultSafAgentType parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().parse(is, DefaultSafAgentType.type, options);
      }

      public static DefaultSafAgentType parse(Reader r) throws XmlException, IOException {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().parse(r, DefaultSafAgentType.type, (XmlOptions)null);
      }

      public static DefaultSafAgentType parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().parse(r, DefaultSafAgentType.type, options);
      }

      public static DefaultSafAgentType parse(XMLStreamReader sr) throws XmlException {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().parse(sr, DefaultSafAgentType.type, (XmlOptions)null);
      }

      public static DefaultSafAgentType parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().parse(sr, DefaultSafAgentType.type, options);
      }

      public static DefaultSafAgentType parse(Node node) throws XmlException {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().parse(node, DefaultSafAgentType.type, (XmlOptions)null);
      }

      public static DefaultSafAgentType parse(Node node, XmlOptions options) throws XmlException {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().parse(node, DefaultSafAgentType.type, options);
      }

      /** @deprecated */
      public static DefaultSafAgentType parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().parse(xis, DefaultSafAgentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static DefaultSafAgentType parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (DefaultSafAgentType)XmlBeans.getContextTypeLoader().parse(xis, DefaultSafAgentType.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultSafAgentType.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, DefaultSafAgentType.type, options);
      }

      private Factory() {
      }
   }
}
