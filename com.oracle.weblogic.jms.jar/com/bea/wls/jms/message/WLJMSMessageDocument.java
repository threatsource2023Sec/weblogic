package com.bea.wls.jms.message;

import com.bea.xml.SchemaType;
import com.bea.xml.StringEnumAbstractBase;
import com.bea.xml.XmlBeans;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlException;
import com.bea.xml.XmlInteger;
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

public interface WLJMSMessageDocument extends XmlObject {
   SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WLJMSMessageDocument.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("wljmsmessage954bdoctype");

   WLJMSMessage getWLJMSMessage();

   void setWLJMSMessage(WLJMSMessage var1);

   WLJMSMessage addNewWLJMSMessage();

   public static final class Factory {
      public static WLJMSMessageDocument newInstance() {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.type, (XmlOptions)null);
      }

      public static WLJMSMessageDocument newInstance(XmlOptions options) {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.type, options);
      }

      public static WLJMSMessageDocument parse(String xmlAsString) throws XmlException {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WLJMSMessageDocument.type, (XmlOptions)null);
      }

      public static WLJMSMessageDocument parse(String xmlAsString, XmlOptions options) throws XmlException {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().parse(xmlAsString, WLJMSMessageDocument.type, options);
      }

      public static WLJMSMessageDocument parse(File file) throws XmlException, IOException {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().parse(file, WLJMSMessageDocument.type, (XmlOptions)null);
      }

      public static WLJMSMessageDocument parse(File file, XmlOptions options) throws XmlException, IOException {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().parse(file, WLJMSMessageDocument.type, options);
      }

      public static WLJMSMessageDocument parse(URL u) throws XmlException, IOException {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().parse(u, WLJMSMessageDocument.type, (XmlOptions)null);
      }

      public static WLJMSMessageDocument parse(URL u, XmlOptions options) throws XmlException, IOException {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().parse(u, WLJMSMessageDocument.type, options);
      }

      public static WLJMSMessageDocument parse(InputStream is) throws XmlException, IOException {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().parse(is, WLJMSMessageDocument.type, (XmlOptions)null);
      }

      public static WLJMSMessageDocument parse(InputStream is, XmlOptions options) throws XmlException, IOException {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().parse(is, WLJMSMessageDocument.type, options);
      }

      public static WLJMSMessageDocument parse(Reader r) throws XmlException, IOException {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().parse(r, WLJMSMessageDocument.type, (XmlOptions)null);
      }

      public static WLJMSMessageDocument parse(Reader r, XmlOptions options) throws XmlException, IOException {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().parse(r, WLJMSMessageDocument.type, options);
      }

      public static WLJMSMessageDocument parse(XMLStreamReader sr) throws XmlException {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().parse(sr, WLJMSMessageDocument.type, (XmlOptions)null);
      }

      public static WLJMSMessageDocument parse(XMLStreamReader sr, XmlOptions options) throws XmlException {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().parse(sr, WLJMSMessageDocument.type, options);
      }

      public static WLJMSMessageDocument parse(Node node) throws XmlException {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().parse(node, WLJMSMessageDocument.type, (XmlOptions)null);
      }

      public static WLJMSMessageDocument parse(Node node, XmlOptions options) throws XmlException {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().parse(node, WLJMSMessageDocument.type, options);
      }

      /** @deprecated */
      public static WLJMSMessageDocument parse(XMLInputStream xis) throws XmlException, XMLStreamException {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().parse(xis, WLJMSMessageDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static WLJMSMessageDocument parse(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return (WLJMSMessageDocument)XmlBeans.getContextTypeLoader().parse(xis, WLJMSMessageDocument.type, options);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WLJMSMessageDocument.type, (XmlOptions)null);
      }

      /** @deprecated */
      public static XMLInputStream newValidatingXMLInputStream(XMLInputStream xis, XmlOptions options) throws XmlException, XMLStreamException {
         return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream(xis, WLJMSMessageDocument.type, options);
      }

      private Factory() {
      }
   }

   public interface WLJMSMessage extends XmlObject {
      SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(WLJMSMessage.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("wljmsmessagebef3elemtype");

      Header getHeader();

      void setHeader(Header var1);

      Header addNewHeader();

      Body getBody();

      void setBody(Body var1);

      Body addNewBody();

      public static final class Factory {
         public static WLJMSMessage newInstance() {
            return (WLJMSMessage)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.WLJMSMessage.type, (XmlOptions)null);
         }

         public static WLJMSMessage newInstance(XmlOptions options) {
            return (WLJMSMessage)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.WLJMSMessage.type, options);
         }

         private Factory() {
         }
      }

      public interface Body extends XmlObject {
         SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Body.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("body5055elemtype");

         String getText();

         StringType xgetText();

         boolean isNilText();

         boolean isSetText();

         void setText(String var1);

         void xsetText(StringType var1);

         void setNilText();

         void unsetText();

         byte[] getObject();

         BytesType xgetObject();

         boolean isSetObject();

         void setObject(byte[] var1);

         void xsetObject(BytesType var1);

         void unsetObject();

         byte[] getBytes();

         BytesType xgetBytes();

         boolean isSetBytes();

         void setBytes(byte[] var1);

         void xsetBytes(BytesType var1);

         void unsetBytes();

         StreamBodyType getStream();

         boolean isSetStream();

         void setStream(StreamBodyType var1);

         StreamBodyType addNewStream();

         void unsetStream();

         MapBodyType getMap();

         boolean isSetMap();

         void setMap(MapBodyType var1);

         MapBodyType addNewMap();

         void unsetMap();

         XML getXML();

         boolean isSetXML();

         void setXML(XML var1);

         XML addNewXML();

         void unsetXML();

         public static final class Factory {
            public static Body newInstance() {
               return (Body)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.WLJMSMessage.Body.type, (XmlOptions)null);
            }

            public static Body newInstance(XmlOptions options) {
               return (Body)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.WLJMSMessage.Body.type, options);
            }

            private Factory() {
            }
         }

         public interface XML extends XmlObject {
            SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(XML.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("xml09b8elemtype");

            public static final class Factory {
               public static XML newInstance() {
                  return (XML)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.WLJMSMessage.Body.XML.type, (XmlOptions)null);
               }

               public static XML newInstance(XmlOptions options) {
                  return (XML)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.WLJMSMessage.Body.XML.type, options);
               }

               private Factory() {
               }
            }
         }
      }

      public interface Header extends XmlObject {
         SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(Header.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("header2acaelemtype");

         String getJMSMessageID();

         XmlString xgetJMSMessageID();

         boolean isSetJMSMessageID();

         void setJMSMessageID(String var1);

         void xsetJMSMessageID(XmlString var1);

         void unsetJMSMessageID();

         String getJMSCorrelationID();

         XmlString xgetJMSCorrelationID();

         boolean isSetJMSCorrelationID();

         void setJMSCorrelationID(String var1);

         void xsetJMSCorrelationID(XmlString var1);

         void unsetJMSCorrelationID();

         JMSDeliveryMode.Enum getJMSDeliveryMode();

         JMSDeliveryMode xgetJMSDeliveryMode();

         boolean isSetJMSDeliveryMode();

         void setJMSDeliveryMode(JMSDeliveryMode.Enum var1);

         void xsetJMSDeliveryMode(JMSDeliveryMode var1);

         void unsetJMSDeliveryMode();

         DestinationType getJMSDestination();

         boolean isSetJMSDestination();

         void setJMSDestination(DestinationType var1);

         DestinationType addNewJMSDestination();

         void unsetJMSDestination();

         long getJMSExpiration();

         JMSExpiration xgetJMSExpiration();

         boolean isSetJMSExpiration();

         void setJMSExpiration(long var1);

         void xsetJMSExpiration(JMSExpiration var1);

         void unsetJMSExpiration();

         int getJMSPriority();

         JMSPriority xgetJMSPriority();

         boolean isSetJMSPriority();

         void setJMSPriority(int var1);

         void xsetJMSPriority(JMSPriority var1);

         void unsetJMSPriority();

         boolean getJMSRedelivered();

         XmlBoolean xgetJMSRedelivered();

         boolean isSetJMSRedelivered();

         void setJMSRedelivered(boolean var1);

         void xsetJMSRedelivered(XmlBoolean var1);

         void unsetJMSRedelivered();

         long getJMSTimestamp();

         JMSTimestamp xgetJMSTimestamp();

         boolean isSetJMSTimestamp();

         void setJMSTimestamp(long var1);

         void xsetJMSTimestamp(JMSTimestamp var1);

         void unsetJMSTimestamp();

         DestinationType getJMSReplyTo();

         boolean isSetJMSReplyTo();

         void setJMSReplyTo(DestinationType var1);

         DestinationType addNewJMSReplyTo();

         void unsetJMSReplyTo();

         String getJMSType();

         XmlString xgetJMSType();

         boolean isSetJMSType();

         void setJMSType(String var1);

         void xsetJMSType(XmlString var1);

         void unsetJMSType();

         PropertyType getProperties();

         boolean isSetProperties();

         void setProperties(PropertyType var1);

         PropertyType addNewProperties();

         void unsetProperties();

         public static final class Factory {
            public static Header newInstance() {
               return (Header)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.WLJMSMessage.Header.type, (XmlOptions)null);
            }

            public static Header newInstance(XmlOptions options) {
               return (Header)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.WLJMSMessage.Header.type, options);
            }

            private Factory() {
            }
         }

         public interface JMSTimestamp extends XmlLong {
            SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JMSTimestamp.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("jmstimestamp446celemtype");

            public static final class Factory {
               public static JMSTimestamp newValue(Object obj) {
                  return (JMSTimestamp)WLJMSMessageDocument.WLJMSMessage.Header.JMSTimestamp.type.newValue(obj);
               }

               public static JMSTimestamp newInstance() {
                  return (JMSTimestamp)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.WLJMSMessage.Header.JMSTimestamp.type, (XmlOptions)null);
               }

               public static JMSTimestamp newInstance(XmlOptions options) {
                  return (JMSTimestamp)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.WLJMSMessage.Header.JMSTimestamp.type, options);
               }

               private Factory() {
               }
            }
         }

         public interface JMSPriority extends XmlInteger {
            SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JMSPriority.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("jmspriority586aelemtype");

            int getIntValue();

            void setIntValue(int var1);

            /** @deprecated */
            int intValue();

            /** @deprecated */
            void set(int var1);

            public static final class Factory {
               public static JMSPriority newValue(Object obj) {
                  return (JMSPriority)WLJMSMessageDocument.WLJMSMessage.Header.JMSPriority.type.newValue(obj);
               }

               public static JMSPriority newInstance() {
                  return (JMSPriority)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.WLJMSMessage.Header.JMSPriority.type, (XmlOptions)null);
               }

               public static JMSPriority newInstance(XmlOptions options) {
                  return (JMSPriority)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.WLJMSMessage.Header.JMSPriority.type, options);
               }

               private Factory() {
               }
            }
         }

         public interface JMSExpiration extends XmlLong {
            SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JMSExpiration.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("jmsexpiration21b5elemtype");

            public static final class Factory {
               public static JMSExpiration newValue(Object obj) {
                  return (JMSExpiration)WLJMSMessageDocument.WLJMSMessage.Header.JMSExpiration.type.newValue(obj);
               }

               public static JMSExpiration newInstance() {
                  return (JMSExpiration)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.WLJMSMessage.Header.JMSExpiration.type, (XmlOptions)null);
               }

               public static JMSExpiration newInstance(XmlOptions options) {
                  return (JMSExpiration)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.WLJMSMessage.Header.JMSExpiration.type, options);
               }

               private Factory() {
               }
            }
         }

         public interface JMSDeliveryMode extends XmlString {
            SchemaType type = (SchemaType)XmlBeans.typeSystemForClassLoader(JMSDeliveryMode.class.getClassLoader(), "schemacom_bea_xml.system.wls_jms").resolveHandle("jmsdeliverymode7ebdelemtype");
            Enum PERSISTENT = WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode.Enum.forString("PERSISTENT");
            Enum NON_PERSISTENT = WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode.Enum.forString("NON_PERSISTENT");
            int INT_PERSISTENT = 1;
            int INT_NON_PERSISTENT = 2;

            StringEnumAbstractBase enumValue();

            void set(StringEnumAbstractBase var1);

            public static final class Factory {
               public static JMSDeliveryMode newValue(Object obj) {
                  return (JMSDeliveryMode)WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode.type.newValue(obj);
               }

               public static JMSDeliveryMode newInstance() {
                  return (JMSDeliveryMode)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode.type, (XmlOptions)null);
               }

               public static JMSDeliveryMode newInstance(XmlOptions options) {
                  return (JMSDeliveryMode)XmlBeans.getContextTypeLoader().newInstance(WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode.type, options);
               }

               private Factory() {
               }
            }

            public static final class Enum extends StringEnumAbstractBase {
               static final int INT_PERSISTENT = 1;
               static final int INT_NON_PERSISTENT = 2;
               public static final StringEnumAbstractBase.Table table = new StringEnumAbstractBase.Table(new Enum[]{new Enum("PERSISTENT", 1), new Enum("NON_PERSISTENT", 2)});
               private static final long serialVersionUID = 1L;

               public static Enum forString(String s) {
                  return (Enum)table.forString(s);
               }

               public static Enum forInt(int i) {
                  return (Enum)table.forInt(i);
               }

               private Enum(String s, int i) {
                  super(s, i);
               }

               private Object readResolve() {
                  return forInt(this.intValue());
               }
            }
         }
      }
   }
}
