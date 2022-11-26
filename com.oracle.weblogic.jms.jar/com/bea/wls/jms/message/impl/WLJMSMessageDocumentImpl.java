package com.bea.wls.jms.message.impl;

import com.bea.wls.jms.message.BytesType;
import com.bea.wls.jms.message.DestinationType;
import com.bea.wls.jms.message.MapBodyType;
import com.bea.wls.jms.message.PropertyType;
import com.bea.wls.jms.message.StreamBodyType;
import com.bea.wls.jms.message.StringType;
import com.bea.wls.jms.message.WLJMSMessageDocument;
import com.bea.xbean.values.JavaIntHolderEx;
import com.bea.xbean.values.JavaLongHolderEx;
import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class WLJMSMessageDocumentImpl extends XmlComplexContentImpl implements WLJMSMessageDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WLJMSMESSAGE$0 = new QName("http://www.bea.com/WLS/JMS/Message", "WLJMSMessage");

   public WLJMSMessageDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WLJMSMessageDocument.WLJMSMessage getWLJMSMessage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WLJMSMessageDocument.WLJMSMessage target = null;
         target = (WLJMSMessageDocument.WLJMSMessage)this.get_store().find_element_user(WLJMSMESSAGE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWLJMSMessage(WLJMSMessageDocument.WLJMSMessage wljmsMessage) {
      this.generatedSetterHelperImpl(wljmsMessage, WLJMSMESSAGE$0, 0, (short)1);
   }

   public WLJMSMessageDocument.WLJMSMessage addNewWLJMSMessage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WLJMSMessageDocument.WLJMSMessage target = null;
         target = (WLJMSMessageDocument.WLJMSMessage)this.get_store().add_element_user(WLJMSMESSAGE$0);
         return target;
      }
   }

   public static class WLJMSMessageImpl extends XmlComplexContentImpl implements WLJMSMessageDocument.WLJMSMessage {
      private static final long serialVersionUID = 1L;
      private static final QName HEADER$0 = new QName("http://www.bea.com/WLS/JMS/Message", "Header");
      private static final QName BODY$2 = new QName("http://www.bea.com/WLS/JMS/Message", "Body");

      public WLJMSMessageImpl(SchemaType sType) {
         super(sType);
      }

      public WLJMSMessageDocument.WLJMSMessage.Header getHeader() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WLJMSMessageDocument.WLJMSMessage.Header target = null;
            target = (WLJMSMessageDocument.WLJMSMessage.Header)this.get_store().find_element_user(HEADER$0, 0);
            return target == null ? null : target;
         }
      }

      public void setHeader(WLJMSMessageDocument.WLJMSMessage.Header header) {
         this.generatedSetterHelperImpl(header, HEADER$0, 0, (short)1);
      }

      public WLJMSMessageDocument.WLJMSMessage.Header addNewHeader() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WLJMSMessageDocument.WLJMSMessage.Header target = null;
            target = (WLJMSMessageDocument.WLJMSMessage.Header)this.get_store().add_element_user(HEADER$0);
            return target;
         }
      }

      public WLJMSMessageDocument.WLJMSMessage.Body getBody() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WLJMSMessageDocument.WLJMSMessage.Body target = null;
            target = (WLJMSMessageDocument.WLJMSMessage.Body)this.get_store().find_element_user(BODY$2, 0);
            return target == null ? null : target;
         }
      }

      public void setBody(WLJMSMessageDocument.WLJMSMessage.Body body) {
         this.generatedSetterHelperImpl(body, BODY$2, 0, (short)1);
      }

      public WLJMSMessageDocument.WLJMSMessage.Body addNewBody() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            WLJMSMessageDocument.WLJMSMessage.Body target = null;
            target = (WLJMSMessageDocument.WLJMSMessage.Body)this.get_store().add_element_user(BODY$2);
            return target;
         }
      }

      public static class BodyImpl extends XmlComplexContentImpl implements WLJMSMessageDocument.WLJMSMessage.Body {
         private static final long serialVersionUID = 1L;
         private static final QName TEXT$0 = new QName("http://www.bea.com/WLS/JMS/Message", "Text");
         private static final QName OBJECT$2 = new QName("http://www.bea.com/WLS/JMS/Message", "Object");
         private static final QName BYTES$4 = new QName("http://www.bea.com/WLS/JMS/Message", "Bytes");
         private static final QName STREAM$6 = new QName("http://www.bea.com/WLS/JMS/Message", "Stream");
         private static final QName MAP$8 = new QName("http://www.bea.com/WLS/JMS/Message", "Map");
         private static final QName XML$10 = new QName("http://www.bea.com/WLS/JMS/Message", "XML");

         public BodyImpl(SchemaType sType) {
            super(sType);
         }

         public String getText() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(TEXT$0, 0);
               return target == null ? null : target.getStringValue();
            }
         }

         public StringType xgetText() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               StringType target = null;
               target = (StringType)this.get_store().find_element_user(TEXT$0, 0);
               return target;
            }
         }

         public boolean isNilText() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               StringType target = null;
               target = (StringType)this.get_store().find_element_user(TEXT$0, 0);
               return target == null ? false : target.isNil();
            }
         }

         public boolean isSetText() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(TEXT$0) != 0;
            }
         }

         public void setText(String text) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(TEXT$0, 0);
               if (target == null) {
                  target = (SimpleValue)this.get_store().add_element_user(TEXT$0);
               }

               target.setStringValue(text);
            }
         }

         public void xsetText(StringType text) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               StringType target = null;
               target = (StringType)this.get_store().find_element_user(TEXT$0, 0);
               if (target == null) {
                  target = (StringType)this.get_store().add_element_user(TEXT$0);
               }

               target.set(text);
            }
         }

         public void setNilText() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               StringType target = null;
               target = (StringType)this.get_store().find_element_user(TEXT$0, 0);
               if (target == null) {
                  target = (StringType)this.get_store().add_element_user(TEXT$0);
               }

               target.setNil();
            }
         }

         public void unsetText() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(TEXT$0, 0);
            }
         }

         public byte[] getObject() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(OBJECT$2, 0);
               return target == null ? null : target.getByteArrayValue();
            }
         }

         public BytesType xgetObject() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               BytesType target = null;
               target = (BytesType)this.get_store().find_element_user(OBJECT$2, 0);
               return target;
            }
         }

         public boolean isSetObject() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(OBJECT$2) != 0;
            }
         }

         public void setObject(byte[] object) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(OBJECT$2, 0);
               if (target == null) {
                  target = (SimpleValue)this.get_store().add_element_user(OBJECT$2);
               }

               target.setByteArrayValue(object);
            }
         }

         public void xsetObject(BytesType object) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               BytesType target = null;
               target = (BytesType)this.get_store().find_element_user(OBJECT$2, 0);
               if (target == null) {
                  target = (BytesType)this.get_store().add_element_user(OBJECT$2);
               }

               target.set(object);
            }
         }

         public void unsetObject() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(OBJECT$2, 0);
            }
         }

         public byte[] getBytes() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(BYTES$4, 0);
               return target == null ? null : target.getByteArrayValue();
            }
         }

         public BytesType xgetBytes() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               BytesType target = null;
               target = (BytesType)this.get_store().find_element_user(BYTES$4, 0);
               return target;
            }
         }

         public boolean isSetBytes() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(BYTES$4) != 0;
            }
         }

         public void setBytes(byte[] bytes) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(BYTES$4, 0);
               if (target == null) {
                  target = (SimpleValue)this.get_store().add_element_user(BYTES$4);
               }

               target.setByteArrayValue(bytes);
            }
         }

         public void xsetBytes(BytesType bytes) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               BytesType target = null;
               target = (BytesType)this.get_store().find_element_user(BYTES$4, 0);
               if (target == null) {
                  target = (BytesType)this.get_store().add_element_user(BYTES$4);
               }

               target.set(bytes);
            }
         }

         public void unsetBytes() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(BYTES$4, 0);
            }
         }

         public StreamBodyType getStream() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               StreamBodyType target = null;
               target = (StreamBodyType)this.get_store().find_element_user(STREAM$6, 0);
               return target == null ? null : target;
            }
         }

         public boolean isSetStream() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(STREAM$6) != 0;
            }
         }

         public void setStream(StreamBodyType stream) {
            this.generatedSetterHelperImpl(stream, STREAM$6, 0, (short)1);
         }

         public StreamBodyType addNewStream() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               StreamBodyType target = null;
               target = (StreamBodyType)this.get_store().add_element_user(STREAM$6);
               return target;
            }
         }

         public void unsetStream() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(STREAM$6, 0);
            }
         }

         public MapBodyType getMap() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               MapBodyType target = null;
               target = (MapBodyType)this.get_store().find_element_user(MAP$8, 0);
               return target == null ? null : target;
            }
         }

         public boolean isSetMap() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(MAP$8) != 0;
            }
         }

         public void setMap(MapBodyType map) {
            this.generatedSetterHelperImpl(map, MAP$8, 0, (short)1);
         }

         public MapBodyType addNewMap() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               MapBodyType target = null;
               target = (MapBodyType)this.get_store().add_element_user(MAP$8);
               return target;
            }
         }

         public void unsetMap() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(MAP$8, 0);
            }
         }

         public WLJMSMessageDocument.WLJMSMessage.Body.XML getXML() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               WLJMSMessageDocument.WLJMSMessage.Body.XML target = null;
               target = (WLJMSMessageDocument.WLJMSMessage.Body.XML)this.get_store().find_element_user(XML$10, 0);
               return target == null ? null : target;
            }
         }

         public boolean isSetXML() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(XML$10) != 0;
            }
         }

         public void setXML(WLJMSMessageDocument.WLJMSMessage.Body.XML xml) {
            this.generatedSetterHelperImpl(xml, XML$10, 0, (short)1);
         }

         public WLJMSMessageDocument.WLJMSMessage.Body.XML addNewXML() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               WLJMSMessageDocument.WLJMSMessage.Body.XML target = null;
               target = (WLJMSMessageDocument.WLJMSMessage.Body.XML)this.get_store().add_element_user(XML$10);
               return target;
            }
         }

         public void unsetXML() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(XML$10, 0);
            }
         }

         public static class XMLImpl extends XmlComplexContentImpl implements WLJMSMessageDocument.WLJMSMessage.Body.XML {
            private static final long serialVersionUID = 1L;

            public XMLImpl(SchemaType sType) {
               super(sType);
            }
         }
      }

      public static class HeaderImpl extends XmlComplexContentImpl implements WLJMSMessageDocument.WLJMSMessage.Header {
         private static final long serialVersionUID = 1L;
         private static final QName JMSMESSAGEID$0 = new QName("http://www.bea.com/WLS/JMS/Message", "JMSMessageID");
         private static final QName JMSCORRELATIONID$2 = new QName("http://www.bea.com/WLS/JMS/Message", "JMSCorrelationID");
         private static final QName JMSDELIVERYMODE$4 = new QName("http://www.bea.com/WLS/JMS/Message", "JMSDeliveryMode");
         private static final QName JMSDESTINATION$6 = new QName("http://www.bea.com/WLS/JMS/Message", "JMSDestination");
         private static final QName JMSEXPIRATION$8 = new QName("http://www.bea.com/WLS/JMS/Message", "JMSExpiration");
         private static final QName JMSPRIORITY$10 = new QName("http://www.bea.com/WLS/JMS/Message", "JMSPriority");
         private static final QName JMSREDELIVERED$12 = new QName("http://www.bea.com/WLS/JMS/Message", "JMSRedelivered");
         private static final QName JMSTIMESTAMP$14 = new QName("http://www.bea.com/WLS/JMS/Message", "JMSTimestamp");
         private static final QName JMSREPLYTO$16 = new QName("http://www.bea.com/WLS/JMS/Message", "JMSReplyTo");
         private static final QName JMSTYPE$18 = new QName("http://www.bea.com/WLS/JMS/Message", "JMSType");
         private static final QName PROPERTIES$20 = new QName("http://www.bea.com/WLS/JMS/Message", "Properties");

         public HeaderImpl(SchemaType sType) {
            super(sType);
         }

         public String getJMSMessageID() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(JMSMESSAGEID$0, 0);
               return target == null ? null : target.getStringValue();
            }
         }

         public XmlString xgetJMSMessageID() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               XmlString target = null;
               target = (XmlString)this.get_store().find_element_user(JMSMESSAGEID$0, 0);
               return target;
            }
         }

         public boolean isSetJMSMessageID() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(JMSMESSAGEID$0) != 0;
            }
         }

         public void setJMSMessageID(String jmsMessageID) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(JMSMESSAGEID$0, 0);
               if (target == null) {
                  target = (SimpleValue)this.get_store().add_element_user(JMSMESSAGEID$0);
               }

               target.setStringValue(jmsMessageID);
            }
         }

         public void xsetJMSMessageID(XmlString jmsMessageID) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               XmlString target = null;
               target = (XmlString)this.get_store().find_element_user(JMSMESSAGEID$0, 0);
               if (target == null) {
                  target = (XmlString)this.get_store().add_element_user(JMSMESSAGEID$0);
               }

               target.set(jmsMessageID);
            }
         }

         public void unsetJMSMessageID() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(JMSMESSAGEID$0, 0);
            }
         }

         public String getJMSCorrelationID() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(JMSCORRELATIONID$2, 0);
               return target == null ? null : target.getStringValue();
            }
         }

         public XmlString xgetJMSCorrelationID() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               XmlString target = null;
               target = (XmlString)this.get_store().find_element_user(JMSCORRELATIONID$2, 0);
               return target;
            }
         }

         public boolean isSetJMSCorrelationID() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(JMSCORRELATIONID$2) != 0;
            }
         }

         public void setJMSCorrelationID(String jmsCorrelationID) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(JMSCORRELATIONID$2, 0);
               if (target == null) {
                  target = (SimpleValue)this.get_store().add_element_user(JMSCORRELATIONID$2);
               }

               target.setStringValue(jmsCorrelationID);
            }
         }

         public void xsetJMSCorrelationID(XmlString jmsCorrelationID) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               XmlString target = null;
               target = (XmlString)this.get_store().find_element_user(JMSCORRELATIONID$2, 0);
               if (target == null) {
                  target = (XmlString)this.get_store().add_element_user(JMSCORRELATIONID$2);
               }

               target.set(jmsCorrelationID);
            }
         }

         public void unsetJMSCorrelationID() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(JMSCORRELATIONID$2, 0);
            }
         }

         public WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode.Enum getJMSDeliveryMode() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(JMSDELIVERYMODE$4, 0);
               return target == null ? null : (WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode.Enum)target.getEnumValue();
            }
         }

         public WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode xgetJMSDeliveryMode() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode target = null;
               target = (WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode)this.get_store().find_element_user(JMSDELIVERYMODE$4, 0);
               return target;
            }
         }

         public boolean isSetJMSDeliveryMode() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(JMSDELIVERYMODE$4) != 0;
            }
         }

         public void setJMSDeliveryMode(WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode.Enum jmsDeliveryMode) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(JMSDELIVERYMODE$4, 0);
               if (target == null) {
                  target = (SimpleValue)this.get_store().add_element_user(JMSDELIVERYMODE$4);
               }

               target.setEnumValue(jmsDeliveryMode);
            }
         }

         public void xsetJMSDeliveryMode(WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode jmsDeliveryMode) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode target = null;
               target = (WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode)this.get_store().find_element_user(JMSDELIVERYMODE$4, 0);
               if (target == null) {
                  target = (WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode)this.get_store().add_element_user(JMSDELIVERYMODE$4);
               }

               target.set(jmsDeliveryMode);
            }
         }

         public void unsetJMSDeliveryMode() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(JMSDELIVERYMODE$4, 0);
            }
         }

         public DestinationType getJMSDestination() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               DestinationType target = null;
               target = (DestinationType)this.get_store().find_element_user(JMSDESTINATION$6, 0);
               return target == null ? null : target;
            }
         }

         public boolean isSetJMSDestination() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(JMSDESTINATION$6) != 0;
            }
         }

         public void setJMSDestination(DestinationType jmsDestination) {
            this.generatedSetterHelperImpl(jmsDestination, JMSDESTINATION$6, 0, (short)1);
         }

         public DestinationType addNewJMSDestination() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               DestinationType target = null;
               target = (DestinationType)this.get_store().add_element_user(JMSDESTINATION$6);
               return target;
            }
         }

         public void unsetJMSDestination() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(JMSDESTINATION$6, 0);
            }
         }

         public long getJMSExpiration() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(JMSEXPIRATION$8, 0);
               return target == null ? 0L : target.getLongValue();
            }
         }

         public WLJMSMessageDocument.WLJMSMessage.Header.JMSExpiration xgetJMSExpiration() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               WLJMSMessageDocument.WLJMSMessage.Header.JMSExpiration target = null;
               target = (WLJMSMessageDocument.WLJMSMessage.Header.JMSExpiration)this.get_store().find_element_user(JMSEXPIRATION$8, 0);
               return target;
            }
         }

         public boolean isSetJMSExpiration() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(JMSEXPIRATION$8) != 0;
            }
         }

         public void setJMSExpiration(long jmsExpiration) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(JMSEXPIRATION$8, 0);
               if (target == null) {
                  target = (SimpleValue)this.get_store().add_element_user(JMSEXPIRATION$8);
               }

               target.setLongValue(jmsExpiration);
            }
         }

         public void xsetJMSExpiration(WLJMSMessageDocument.WLJMSMessage.Header.JMSExpiration jmsExpiration) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               WLJMSMessageDocument.WLJMSMessage.Header.JMSExpiration target = null;
               target = (WLJMSMessageDocument.WLJMSMessage.Header.JMSExpiration)this.get_store().find_element_user(JMSEXPIRATION$8, 0);
               if (target == null) {
                  target = (WLJMSMessageDocument.WLJMSMessage.Header.JMSExpiration)this.get_store().add_element_user(JMSEXPIRATION$8);
               }

               target.set(jmsExpiration);
            }
         }

         public void unsetJMSExpiration() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(JMSEXPIRATION$8, 0);
            }
         }

         public int getJMSPriority() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(JMSPRIORITY$10, 0);
               return target == null ? 0 : target.getIntValue();
            }
         }

         public WLJMSMessageDocument.WLJMSMessage.Header.JMSPriority xgetJMSPriority() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               WLJMSMessageDocument.WLJMSMessage.Header.JMSPriority target = null;
               target = (WLJMSMessageDocument.WLJMSMessage.Header.JMSPriority)this.get_store().find_element_user(JMSPRIORITY$10, 0);
               return target;
            }
         }

         public boolean isSetJMSPriority() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(JMSPRIORITY$10) != 0;
            }
         }

         public void setJMSPriority(int jmsPriority) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(JMSPRIORITY$10, 0);
               if (target == null) {
                  target = (SimpleValue)this.get_store().add_element_user(JMSPRIORITY$10);
               }

               target.setIntValue(jmsPriority);
            }
         }

         public void xsetJMSPriority(WLJMSMessageDocument.WLJMSMessage.Header.JMSPriority jmsPriority) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               WLJMSMessageDocument.WLJMSMessage.Header.JMSPriority target = null;
               target = (WLJMSMessageDocument.WLJMSMessage.Header.JMSPriority)this.get_store().find_element_user(JMSPRIORITY$10, 0);
               if (target == null) {
                  target = (WLJMSMessageDocument.WLJMSMessage.Header.JMSPriority)this.get_store().add_element_user(JMSPRIORITY$10);
               }

               target.set(jmsPriority);
            }
         }

         public void unsetJMSPriority() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(JMSPRIORITY$10, 0);
            }
         }

         public boolean getJMSRedelivered() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(JMSREDELIVERED$12, 0);
               return target == null ? false : target.getBooleanValue();
            }
         }

         public XmlBoolean xgetJMSRedelivered() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               XmlBoolean target = null;
               target = (XmlBoolean)this.get_store().find_element_user(JMSREDELIVERED$12, 0);
               return target;
            }
         }

         public boolean isSetJMSRedelivered() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(JMSREDELIVERED$12) != 0;
            }
         }

         public void setJMSRedelivered(boolean jmsRedelivered) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(JMSREDELIVERED$12, 0);
               if (target == null) {
                  target = (SimpleValue)this.get_store().add_element_user(JMSREDELIVERED$12);
               }

               target.setBooleanValue(jmsRedelivered);
            }
         }

         public void xsetJMSRedelivered(XmlBoolean jmsRedelivered) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               XmlBoolean target = null;
               target = (XmlBoolean)this.get_store().find_element_user(JMSREDELIVERED$12, 0);
               if (target == null) {
                  target = (XmlBoolean)this.get_store().add_element_user(JMSREDELIVERED$12);
               }

               target.set(jmsRedelivered);
            }
         }

         public void unsetJMSRedelivered() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(JMSREDELIVERED$12, 0);
            }
         }

         public long getJMSTimestamp() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(JMSTIMESTAMP$14, 0);
               return target == null ? 0L : target.getLongValue();
            }
         }

         public WLJMSMessageDocument.WLJMSMessage.Header.JMSTimestamp xgetJMSTimestamp() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               WLJMSMessageDocument.WLJMSMessage.Header.JMSTimestamp target = null;
               target = (WLJMSMessageDocument.WLJMSMessage.Header.JMSTimestamp)this.get_store().find_element_user(JMSTIMESTAMP$14, 0);
               return target;
            }
         }

         public boolean isSetJMSTimestamp() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(JMSTIMESTAMP$14) != 0;
            }
         }

         public void setJMSTimestamp(long jmsTimestamp) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(JMSTIMESTAMP$14, 0);
               if (target == null) {
                  target = (SimpleValue)this.get_store().add_element_user(JMSTIMESTAMP$14);
               }

               target.setLongValue(jmsTimestamp);
            }
         }

         public void xsetJMSTimestamp(WLJMSMessageDocument.WLJMSMessage.Header.JMSTimestamp jmsTimestamp) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               WLJMSMessageDocument.WLJMSMessage.Header.JMSTimestamp target = null;
               target = (WLJMSMessageDocument.WLJMSMessage.Header.JMSTimestamp)this.get_store().find_element_user(JMSTIMESTAMP$14, 0);
               if (target == null) {
                  target = (WLJMSMessageDocument.WLJMSMessage.Header.JMSTimestamp)this.get_store().add_element_user(JMSTIMESTAMP$14);
               }

               target.set(jmsTimestamp);
            }
         }

         public void unsetJMSTimestamp() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(JMSTIMESTAMP$14, 0);
            }
         }

         public DestinationType getJMSReplyTo() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               DestinationType target = null;
               target = (DestinationType)this.get_store().find_element_user(JMSREPLYTO$16, 0);
               return target == null ? null : target;
            }
         }

         public boolean isSetJMSReplyTo() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(JMSREPLYTO$16) != 0;
            }
         }

         public void setJMSReplyTo(DestinationType jmsReplyTo) {
            this.generatedSetterHelperImpl(jmsReplyTo, JMSREPLYTO$16, 0, (short)1);
         }

         public DestinationType addNewJMSReplyTo() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               DestinationType target = null;
               target = (DestinationType)this.get_store().add_element_user(JMSREPLYTO$16);
               return target;
            }
         }

         public void unsetJMSReplyTo() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(JMSREPLYTO$16, 0);
            }
         }

         public String getJMSType() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(JMSTYPE$18, 0);
               return target == null ? null : target.getStringValue();
            }
         }

         public XmlString xgetJMSType() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               XmlString target = null;
               target = (XmlString)this.get_store().find_element_user(JMSTYPE$18, 0);
               return target;
            }
         }

         public boolean isSetJMSType() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(JMSTYPE$18) != 0;
            }
         }

         public void setJMSType(String jmsType) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               SimpleValue target = null;
               target = (SimpleValue)this.get_store().find_element_user(JMSTYPE$18, 0);
               if (target == null) {
                  target = (SimpleValue)this.get_store().add_element_user(JMSTYPE$18);
               }

               target.setStringValue(jmsType);
            }
         }

         public void xsetJMSType(XmlString jmsType) {
            synchronized(this.monitor()) {
               this.check_orphaned();
               XmlString target = null;
               target = (XmlString)this.get_store().find_element_user(JMSTYPE$18, 0);
               if (target == null) {
                  target = (XmlString)this.get_store().add_element_user(JMSTYPE$18);
               }

               target.set(jmsType);
            }
         }

         public void unsetJMSType() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(JMSTYPE$18, 0);
            }
         }

         public PropertyType getProperties() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               PropertyType target = null;
               target = (PropertyType)this.get_store().find_element_user(PROPERTIES$20, 0);
               return target == null ? null : target;
            }
         }

         public boolean isSetProperties() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               return this.get_store().count_elements(PROPERTIES$20) != 0;
            }
         }

         public void setProperties(PropertyType properties) {
            this.generatedSetterHelperImpl(properties, PROPERTIES$20, 0, (short)1);
         }

         public PropertyType addNewProperties() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               PropertyType target = null;
               target = (PropertyType)this.get_store().add_element_user(PROPERTIES$20);
               return target;
            }
         }

         public void unsetProperties() {
            synchronized(this.monitor()) {
               this.check_orphaned();
               this.get_store().remove_element(PROPERTIES$20, 0);
            }
         }

         public static class JMSTimestampImpl extends JavaLongHolderEx implements WLJMSMessageDocument.WLJMSMessage.Header.JMSTimestamp {
            private static final long serialVersionUID = 1L;

            public JMSTimestampImpl(SchemaType sType) {
               super(sType, false);
            }

            protected JMSTimestampImpl(SchemaType sType, boolean b) {
               super(sType, b);
            }
         }

         public static class JMSPriorityImpl extends JavaIntHolderEx implements WLJMSMessageDocument.WLJMSMessage.Header.JMSPriority {
            private static final long serialVersionUID = 1L;

            public JMSPriorityImpl(SchemaType sType) {
               super(sType, false);
            }

            protected JMSPriorityImpl(SchemaType sType, boolean b) {
               super(sType, b);
            }
         }

         public static class JMSExpirationImpl extends JavaLongHolderEx implements WLJMSMessageDocument.WLJMSMessage.Header.JMSExpiration {
            private static final long serialVersionUID = 1L;

            public JMSExpirationImpl(SchemaType sType) {
               super(sType, false);
            }

            protected JMSExpirationImpl(SchemaType sType, boolean b) {
               super(sType, b);
            }
         }

         public static class JMSDeliveryModeImpl extends JavaStringEnumerationHolderEx implements WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode {
            private static final long serialVersionUID = 1L;

            public JMSDeliveryModeImpl(SchemaType sType) {
               super(sType, false);
            }

            protected JMSDeliveryModeImpl(SchemaType sType, boolean b) {
               super(sType, b);
            }
         }
      }
   }
}
