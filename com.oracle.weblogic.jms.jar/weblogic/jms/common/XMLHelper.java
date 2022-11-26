package weblogic.jms.common;

import com.bea.wls.jms.message.BooleanType;
import com.bea.wls.jms.message.ByteType;
import com.bea.wls.jms.message.BytesType;
import com.bea.wls.jms.message.CharType;
import com.bea.wls.jms.message.DestinationType;
import com.bea.wls.jms.message.DoubleType;
import com.bea.wls.jms.message.FloatType;
import com.bea.wls.jms.message.IntType;
import com.bea.wls.jms.message.LongType;
import com.bea.wls.jms.message.MapBodyType;
import com.bea.wls.jms.message.PropertyType;
import com.bea.wls.jms.message.ShortType;
import com.bea.wls.jms.message.StreamBodyType;
import com.bea.wls.jms.message.StringType;
import com.bea.wls.jms.message.WLJMSMessageDocument;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlCursor;
import com.bea.xml.XmlException;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlOptions;
import com.bea.xml.XmlObject.Factory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.jms.extensions.WLMessage;
import weblogic.xml.jaxp.WebLogicDocumentBuilderFactory;

public class XMLHelper {
   public static Document getDocument(WLMessage wlMessage) throws javax.jms.JMSException {
      return getDocument(wlMessage, (List)null, (List)null, true);
   }

   public static String getXMLText(WLMessage wlMessage, boolean withBody) throws javax.jms.JMSException {
      WLJMSMessageDocument msgDoc = getWLJMSMessageDocument(wlMessage, (List)null, (List)null, withBody);
      if (msgDoc == null) {
         return null;
      } else {
         XmlOptions options = new XmlOptions();
         options.setSavePrettyPrint();
         return msgDoc.xmlText(options);
      }
   }

   public static String getXMLText(WLMessage wlMessage) throws javax.jms.JMSException {
      return getXMLText(wlMessage, true);
   }

   static Document getDocument(WLMessage wlMessage, List headers, List properties, boolean withBody) throws javax.jms.JMSException {
      WLJMSMessageDocument msgDoc = getWLJMSMessageDocument(wlMessage, headers, properties, withBody);
      return msgDoc == null ? null : (Document)msgDoc.newDomNode();
   }

   public static WLMessage createMessage(Document doc) throws javax.jms.JMSException, IOException, ClassNotFoundException {
      WLMessage wlMessage = null;

      try {
         WLJMSMessageDocument jmsDoc = WLJMSMessageDocument.Factory.parse((Node)doc.getDocumentElement());
         return createMessage(jmsDoc);
      } catch (XmlException var3) {
         throw new JMSException(var3);
      }
   }

   public static WLMessage createMessage(String xmlText) throws javax.jms.JMSException, IOException, ClassNotFoundException {
      try {
         WLJMSMessageDocument jmsDoc = WLJMSMessageDocument.Factory.parse(xmlText);
         return createMessage(jmsDoc);
      } catch (XmlException var2) {
         throw new JMSException(var2);
      }
   }

   private static WLMessage createMessage(WLJMSMessageDocument doc) throws javax.jms.JMSException, IOException, ClassNotFoundException {
      WLJMSMessageDocument.WLJMSMessage wlJMSMessage = doc.getWLJMSMessage();
      WLJMSMessageDocument.WLJMSMessage.Header header = wlJMSMessage.getHeader();
      WLJMSMessageDocument.WLJMSMessage.Body body = wlJMSMessage.getBody();
      WLMessage wlMessage = null;
      if (body == null) {
         wlMessage = new HdrMessageImpl();
         processHeader(header, (WLMessage)wlMessage);
      } else if (body.isSetText()) {
         wlMessage = new TextMessageImpl();
         processHeader(header, (WLMessage)wlMessage);
         processTextBody(body, (TextMessageImpl)wlMessage);
      } else if (body.isSetObject()) {
         wlMessage = new ObjectMessageImpl();
         processHeader(header, (WLMessage)wlMessage);
         processObjectBody(body, (ObjectMessageImpl)wlMessage);
      } else if (body.isSetBytes()) {
         wlMessage = new BytesMessageImpl();
         processHeader(header, (WLMessage)wlMessage);
         processBytesBody(body, (BytesMessageImpl)wlMessage);
      } else if (body.isSetStream()) {
         wlMessage = new StreamMessageImpl();
         processHeader(header, (WLMessage)wlMessage);
         processStreamBody(body, (StreamMessageImpl)wlMessage);
      } else if (body.isSetMap()) {
         wlMessage = new MapMessageImpl();
         processHeader(header, (WLMessage)wlMessage);
         processMapBody(body, (MapMessageImpl)wlMessage);
      } else if (body.isSetXML()) {
         wlMessage = new XMLMessageImpl();
         processHeader(header, (WLMessage)wlMessage);
         processXMLBody(body, (XMLMessageImpl)wlMessage);
      } else {
         wlMessage = new HdrMessageImpl();
         processHeader(header, (WLMessage)wlMessage);
      }

      return (WLMessage)wlMessage;
   }

   public static Document parse(String xml) throws ParserConfigurationException, SAXException, IOException {
      WebLogicDocumentBuilderFactory docBuilderFactory;
      try {
         docBuilderFactory = new WebLogicDocumentBuilderFactory();
      } catch (FactoryConfigurationError var7) {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("XML Factory Configuration Error: " + var7.getMessage());
         }

         throw var7;
      }

      DocumentBuilder docBuilder;
      try {
         docBuilderFactory.setNamespaceAware(true);
         docBuilder = docBuilderFactory.newDocumentBuilder();
      } catch (ParserConfigurationException var8) {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("Can not create XML Document Builder: " + var8.getMessage());
         }

         throw var8;
      }

      InputSource inSource = new InputSource();
      inSource.setCharacterStream(new StringReader(xml));
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("XMLMessageImpl.parse(): message :");
         JMSDebug.JMSCommon.debug(xml);
      }

      Document doc = null;

      try {
         doc = docBuilder.parse(inSource);
      } catch (SAXException var6) {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("Input message is not a well-formed XML message: " + var6.getMessage());
         }

         throw var6;
      }

      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("Parsed DOM document is -" + doc + "-");
      }

      return doc;
   }

   private static WLJMSMessageDocument getWLJMSMessageDocument(WLMessage wlMessage, List headers, List properties, boolean withBody) throws javax.jms.JMSException {
      MessageImpl message = (MessageImpl)wlMessage;
      WLJMSMessageDocument msgDoc = WLJMSMessageDocument.Factory.newInstance();
      WLJMSMessageDocument.WLJMSMessage wlJMSMessage = msgDoc.addNewWLJMSMessage();
      createHeader(wlJMSMessage, message, headers, properties);
      if (withBody) {
         WLJMSMessageDocument.WLJMSMessage.Body body = wlJMSMessage.addNewBody();
         switch (message.getType()) {
            case 1:
               createBytesBody(body, (BytesMessageImpl)message);
            case 2:
            default:
               break;
            case 3:
               createMapBody(body, (MapMessageImpl)message);
               break;
            case 4:
               createObjectBody(body, (ObjectMessageImpl)message);
               break;
            case 5:
               createStreamBody(body, (StreamMessageImpl)message);
               break;
            case 6:
               createTextBody(body, (TextMessageImpl)message);
               break;
            case 7:
               try {
                  createXMLBody(body, (XMLMessageImpl)message);
               } catch (XmlException var9) {
                  throw new JMSException(var9);
               }
         }
      }

      return msgDoc;
   }

   private static JMSMessageId getMessageId(String id) {
      StringTokenizer tok = new StringTokenizer(id, "<.>");
      if (!tok.hasMoreElements()) {
         return null;
      } else {
         String jmsPrefix = tok.nextToken();
         if (!tok.hasMoreElements()) {
            return null;
         } else {
            String seed = tok.nextToken();
            String timestamp = tok.nextToken();
            String counter = tok.nextToken();
            return new JMSMessageId(Integer.parseInt(seed), Long.parseLong(timestamp), Integer.parseInt(counter));
         }
      }
   }

   private static void processHeader(WLJMSMessageDocument.WLJMSMessage.Header header, WLMessage wlMessage) throws javax.jms.JMSException {
      if (header.isSetJMSMessageID()) {
         MessageImpl messageImpl = (MessageImpl)wlMessage;
         String messageIdString = header.getJMSMessageID();
         if (messageIdString.contains("ID:N") || messageIdString.contains("ID:P")) {
            messageImpl.setOldMessage(true);
         }

         messageImpl.setId(getMessageId(messageIdString));
      }

      if (header.isSetJMSCorrelationID()) {
         wlMessage.setJMSCorrelationID(header.getJMSCorrelationID());
      }

      if (header.isSetJMSDeliveryMode()) {
         WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode.Enum deliveryModeEnum = header.getJMSDeliveryMode();
         wlMessage.setJMSDeliveryMode(deliveryModeEnum.intValue() == 1 ? 2 : 1);
      }

      String destName;
      String destServerName;
      String destServerURL;
      String destApplicationName;
      String destModuleName;
      DestinationType destType;
      DestinationType.Destination dest;
      if (header.isSetJMSDestination()) {
         destType = header.getJMSDestination();
         dest = destType.getDestination();
         destName = dest.getName();
         destServerName = dest.getServerName();
         destServerURL = dest.getServerURL();
         destApplicationName = dest.getApplicationName();
         destModuleName = dest.getModuleName();
         wlMessage.setJMSDestination(new DestinationImpl((byte)0, destServerName, destName, destApplicationName, destModuleName));
      }

      if (header.isSetJMSExpiration()) {
         wlMessage.setJMSExpiration(header.getJMSExpiration());
      }

      if (header.isSetJMSPriority()) {
         wlMessage.setJMSPriority(header.getJMSPriority());
      }

      if (header.isSetJMSRedelivered()) {
         wlMessage.setJMSRedelivered(header.getJMSRedelivered());
      }

      if (header.isSetJMSTimestamp()) {
         wlMessage.setJMSTimestamp(header.getJMSTimestamp());
      }

      if (header.isSetJMSReplyTo()) {
         destType = header.getJMSReplyTo();
         dest = destType.getDestination();
         destName = dest.getName();
         destServerName = dest.getServerName();
         destServerURL = dest.getServerURL();
         destApplicationName = dest.getApplicationName();
         destModuleName = dest.getModuleName();
         wlMessage.setJMSReplyTo(new DestinationImpl((byte)0, destServerName, destName, destApplicationName, destModuleName));
      }

      if (header.isSetJMSType()) {
         wlMessage.setJMSType(header.getJMSType());
      }

      if (header.isSetProperties()) {
         processHeaderProperties(header, wlMessage);
      }

      ((MessageImpl)wlMessage).includeJMSXDeliveryCount(true);
   }

   private static Element getBodyTypeElement(Node bodyNode) {
      Node bodyText = bodyNode.getFirstChild();
      return bodyText == null ? null : (Element)bodyText.getNextSibling();
   }

   private static void processXMLBody(WLJMSMessageDocument.WLJMSMessage.Body body, XMLMessageImpl xmlMessage) throws javax.jms.JMSException {
      WLJMSMessageDocument.WLJMSMessage.Body.XML xml = body.getXML();
      XmlOptions o = new XmlOptions();
      o.setUseDefaultNamespace();
      XmlCursor c = xml.newCursor();
      boolean exists = c.toFirstChild();
      String xmlText = c.xmlText(o);
      xmlMessage.setText(xmlText);
   }

   private static void processMapBody(WLJMSMessageDocument.WLJMSMessage.Body body, MapMessageImpl mapMessage) throws javax.jms.JMSException {
      MapBodyType mapBodyType = body.getMap();
      MapBodyType.NameValue[] nameValueArray = mapBodyType.getNameValueArray();
      if (nameValueArray != null) {
         for(int i = 0; i < nameValueArray.length; ++i) {
            MapBodyType.NameValue nv = nameValueArray[i];
            String name = nv.getName();
            if (nv.isSetBoolean()) {
               mapMessage.setBoolean(name, nv.getBoolean());
            } else if (nv.isSetByte()) {
               mapMessage.setByte(name, nv.getByte());
            } else if (nv.isSetBytes()) {
               mapMessage.setBytes(name, nv.getBytes());
            } else if (nv.isSetChar()) {
               mapMessage.setChar(name, nv.getChar().charAt(0));
            } else if (nv.isSetDouble()) {
               mapMessage.setDouble(name, nv.getDouble());
            } else if (nv.isSetFloat()) {
               mapMessage.setFloat(name, nv.getFloat());
            } else if (nv.isSetInt()) {
               mapMessage.setInt(name, nv.getInt().intValue());
            } else if (nv.isSetLong()) {
               mapMessage.setLong(name, nv.getLong());
            } else if (nv.isSetShort()) {
               mapMessage.setShort(name, nv.getShort());
            } else if (nv.isSetString()) {
               mapMessage.setString(name, nv.getString());
            } else {
               mapMessage.setObject(name, (Object)null);
            }
         }

      }
   }

   private static void processStreamBody(WLJMSMessageDocument.WLJMSMessage.Body body, StreamMessageImpl streamMessage) throws javax.jms.JMSException {
      StreamBodyType streamBodyType = body.getStream();
      XmlCursor cursor = streamBodyType.newCursor();

      while(cursor.hasNextToken()) {
         XmlCursor.TokenType tokenType = cursor.toNextToken();
         if (tokenType.isStart()) {
            XmlObject object = cursor.getObject();
            SchemaType type = object.schemaType();
            if (type.equals(BooleanType.type)) {
               streamMessage.writeBoolean(((BooleanType)object).getBooleanValue());
            } else if (type.equals(ByteType.type)) {
               streamMessage.writeByte(((ByteType)object).getByteValue());
            } else if (type.equals(BytesType.type)) {
               streamMessage.writeBytes(((BytesType)object).getByteArrayValue());
            } else if (type.equals(CharType.type)) {
               streamMessage.writeChar(((CharType)object).getStringValue().charAt(0));
            } else if (type.equals(DoubleType.type)) {
               streamMessage.writeDouble(((DoubleType)object).getDoubleValue());
            } else if (type.equals(FloatType.type)) {
               streamMessage.writeFloat(((FloatType)object).getFloatValue());
            } else if (type.equals(IntType.type)) {
               streamMessage.writeInt(((IntType)object).getBigDecimalValue().intValue());
            } else if (type.equals(LongType.type)) {
               streamMessage.writeLong(((LongType)object).getLongValue());
            } else if (type.equals(ShortType.type)) {
               streamMessage.writeShort(((ShortType)object).getShortValue());
            } else {
               if (!type.equals(StringType.type)) {
                  throw new javax.jms.JMSException("Invalid stream body type " + type);
               }

               streamMessage.writeString(((StringType)object).getStringValue());
            }
         }
      }

   }

   private static void processBytesBody(WLJMSMessageDocument.WLJMSMessage.Body body, BytesMessageImpl bytesMessage) throws javax.jms.JMSException {
      byte[] bytes = body.getBytes();
      if (bytes != null) {
         bytesMessage.writeBytes(bytes);
      }

   }

   private static void processObjectBody(WLJMSMessageDocument.WLJMSMessage.Body body, ObjectMessageImpl objectMessage) throws javax.jms.JMSException, IOException, ClassNotFoundException {
      byte[] data = body.getObject();
      ByteArrayInputStream bais = new ByteArrayInputStream(data);
      ObjectMessageImpl.ObjectInputStream2 ois = objectMessage.new ObjectInputStream2(bais);
      objectMessage.setObject((Serializable)ois.readObject());
   }

   private static void processTextBody(WLJMSMessageDocument.WLJMSMessage.Body body, TextMessageImpl textMessage) throws javax.jms.JMSException {
      textMessage.setText(body.getText());
   }

   private static void processHeaderProperties(WLJMSMessageDocument.WLJMSMessage.Header header, WLMessage wlMessage) throws javax.jms.JMSException {
      PropertyType propertyType = header.getProperties();
      PropertyType.Property[] propertyArray = propertyType.getPropertyArray();
      if (propertyArray != null) {
         for(int i = 0; i < propertyArray.length; ++i) {
            PropertyType.Property p = propertyArray[i];
            String name = p.getName();
            if (p.isSetString()) {
               wlMessage.setStringProperty(name, p.getString());
            } else if (p.isSetLong()) {
               wlMessage.setLongProperty(name, p.getLong());
            } else if (p.isSetShort()) {
               wlMessage.setShortProperty(name, p.getShort());
            } else if (p.isSetInt()) {
               wlMessage.setIntProperty(name, p.getInt().intValue());
            } else if (p.isSetFloat()) {
               wlMessage.setFloatProperty(name, p.getFloat());
            } else if (p.isSetDouble()) {
               wlMessage.setDoubleProperty(name, p.getDouble());
            } else if (p.isSetByte()) {
               wlMessage.setByteProperty(name, p.getByte());
            } else if (p.isSetBoolean()) {
               wlMessage.setBooleanProperty(name, p.getBoolean());
            } else {
               wlMessage.setObjectProperty(name, (Object)null);
            }
         }

      }
   }

   private static void createHeader(WLJMSMessageDocument.WLJMSMessage wlJMSMessage, MessageImpl wlmsg, List headers, List properties) throws javax.jms.JMSException {
      WLJMSMessageDocument.WLJMSMessage.Header header = wlJMSMessage.addNewHeader();
      String jmsType;
      if (headers == null || headers.contains("JMSMessageID")) {
         jmsType = wlmsg.getJMSMessageID();
         if (jmsType != null) {
            header.setJMSMessageID(jmsType);
         }
      }

      if (headers == null || headers.contains("JMSCorrelationID")) {
         jmsType = wlmsg.getJMSCorrelationID();
         if (jmsType != null) {
            header.setJMSCorrelationID(jmsType);
         }
      }

      if (headers == null || headers.contains("JMSType")) {
         jmsType = wlmsg.getJMSType();
         if (jmsType != null) {
            header.setJMSType(jmsType);
         }
      }

      if (headers == null || headers.contains("JMSDeliveryMode")) {
         header.setJMSDeliveryMode(wlmsg.getJMSDeliveryMode() == 2 ? WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode.PERSISTENT : WLJMSMessageDocument.WLJMSMessage.Header.JMSDeliveryMode.NON_PERSISTENT);
      }

      DestinationType replyToType;
      DestinationType.Destination replyToDestination;
      DestinationImpl replyTo;
      if (headers == null || headers.contains("JMSDestination")) {
         replyTo = wlmsg.getDestination();
         if (replyTo != null) {
            replyToType = header.addNewJMSDestination();
            replyToDestination = replyToType.addNewDestination();
            replyToDestination.setName(replyTo.getDestinationName());
            replyToDestination.setServerName(replyTo.getServerName());
            replyToDestination.setApplicationName(replyTo.getApplicationName());
            replyToDestination.setModuleName(replyTo.getModuleName());
         }
      }

      if (headers == null || headers.contains("JMSExpiration")) {
         header.setJMSExpiration(wlmsg.getJMSExpiration());
      }

      if (headers == null || headers.contains("JMSPriority")) {
         header.setJMSPriority(wlmsg.getJMSPriority());
      }

      if (headers == null || headers.contains("JMSRedelivered")) {
         header.setJMSRedelivered(wlmsg.getJMSRedelivered());
      }

      if (headers == null || headers.contains("JMSTimestamp")) {
         header.setJMSTimestamp(wlmsg.getJMSTimestamp());
      }

      if (headers == null || headers.contains("JMSReplyTo")) {
         replyTo = (DestinationImpl)wlmsg.getJMSReplyTo();
         if (replyTo != null) {
            replyToType = header.addNewJMSReplyTo();
            replyToDestination = replyToType.addNewDestination();
            replyToDestination.setName(replyTo.getDestinationName());
            replyToDestination.setServerName(replyTo.getServerName());
            replyToDestination.setApplicationName(replyTo.getApplicationName());
            replyToDestination.setModuleName(replyTo.getModuleName());
         }
      }

      createHeaderProperties(header, wlmsg, properties);
   }

   private static void createHeaderProperties(WLJMSMessageDocument.WLJMSMessage.Header header, MessageImpl wlmsg, List properties) throws javax.jms.JMSException {
      Enumeration propNames = null;
      boolean idcHold = wlmsg.includeJMSXDeliveryCount(true);

      try {
         propNames = wlmsg.getPropertyNames();
      } finally {
         wlmsg.includeJMSXDeliveryCount(idcHold);
      }

      if (propNames != null && propNames.hasMoreElements()) {
         PropertyType propertyType = header.addNewProperties();

         while(true) {
            String name;
            do {
               if (!propNames.hasMoreElements()) {
                  return;
               }

               name = (String)propNames.nextElement();
            } while(properties != null && !properties.contains("%properties%") && !properties.contains(name));

            PropertyType.Property property = propertyType.addNewProperty();
            property.setName(name);
            Object value = wlmsg.getObjectProperty(name);
            if (value instanceof String) {
               property.setString((String)value);
            } else if (value instanceof Long) {
               property.setLong((Long)value);
            } else if (value instanceof Short) {
               property.setShort((Short)value);
            } else if (value instanceof Integer) {
               property.setInt(BigInteger.valueOf(((Integer)value).longValue()));
            } else if (value instanceof Float) {
               property.setFloat((Float)value);
            } else if (value instanceof Double) {
               property.setDouble((Double)value);
            } else if (value instanceof Byte) {
               property.setByte((Byte)value);
            } else if (value instanceof Boolean) {
               property.setBoolean((Boolean)value);
            }
         }
      }
   }

   private static void createTextBody(WLJMSMessageDocument.WLJMSMessage.Body body, TextMessageImpl textMessage) throws javax.jms.JMSException {
      body.setText(textMessage.getText());
   }

   private static void createStreamBody(WLJMSMessageDocument.WLJMSMessage.Body body, StreamMessageImpl streamMessage) throws javax.jms.JMSException {
      StreamBodyType streamBody = body.addNewStream();
      StreamMessageImpl streamMessageCopy = (StreamMessageImpl)streamMessage.copy();

      while(true) {
         Object o;
         try {
            o = streamMessageCopy.readObject();
         } catch (javax.jms.MessageEOFException var6) {
            return;
         }

         if (o != null) {
            if (o instanceof byte[]) {
               streamBody.addBytes((byte[])((byte[])o));
            } else if (o instanceof Number) {
               if (o instanceof Byte) {
                  streamBody.addByte((Byte)o);
               } else if (o instanceof Short) {
                  streamBody.addShort((Short)o);
               } else if (o instanceof Integer) {
                  streamBody.addInt(BigInteger.valueOf(((Integer)o).longValue()));
               } else if (o instanceof Long) {
                  streamBody.addLong((Long)o);
               } else if (o instanceof Float) {
                  streamBody.addFloat((Float)o);
               } else {
                  if (!(o instanceof Double)) {
                     throw new javax.jms.JMSException("Invalid Stream value type " + o.getClass().getName());
                  }

                  streamBody.addDouble((Double)o);
               }
            } else if (o instanceof Boolean) {
               streamBody.addBoolean((Boolean)o);
            } else if (o instanceof String) {
               streamBody.addString((String)o);
            } else {
               if (!(o instanceof Character)) {
                  throw new javax.jms.JMSException("Invalid Stream value type " + o.getClass().getName());
               }

               streamBody.addChar(o.toString());
            }
         }
      }
   }

   private static void createBytesBody(WLJMSMessageDocument.WLJMSMessage.Body body, BytesMessageImpl bytesMessage) throws javax.jms.JMSException {
      BytesMessageImpl bytesMessageCopy = (BytesMessageImpl)bytesMessage.copy();
      bytesMessageCopy.decompressMessageBody();
      byte[] data = bytesMessageCopy.getBodyBytes();
      body.setBytes(data);
   }

   private static void createMapBody(WLJMSMessageDocument.WLJMSMessage.Body body, MapMessageImpl mapMessage) throws javax.jms.JMSException {
      MapBodyType mapBody = body.addNewMap();
      Enumeration mapNames = mapMessage.getMapNames();

      while(mapNames.hasMoreElements()) {
         String key = (String)mapNames.nextElement();
         MapBodyType.NameValue nameValue = mapBody.addNewNameValue();
         nameValue.setName(key);
         Object object = mapMessage.getObject(key);
         if (object != null) {
            if (object instanceof byte[]) {
               nameValue.setBytes((byte[])((byte[])object));
            } else if (object instanceof Number) {
               if (object instanceof Byte) {
                  nameValue.setByte((Byte)object);
               } else if (object instanceof Short) {
                  nameValue.setShort((Short)object);
               } else if (object instanceof Integer) {
                  nameValue.setInt(BigInteger.valueOf(((Integer)object).longValue()));
               } else if (object instanceof Long) {
                  nameValue.setLong((Long)object);
               } else if (object instanceof Float) {
                  nameValue.setFloat((Float)object);
               } else {
                  if (!(object instanceof Double)) {
                     throw new javax.jms.JMSException("Invalid Map value type " + object.getClass().getName());
                  }

                  nameValue.setDouble((Double)object);
               }
            } else if (object instanceof Boolean) {
               nameValue.setBoolean((Boolean)object);
            } else if (object instanceof String) {
               nameValue.setString((String)object);
            } else {
               if (!(object instanceof Character)) {
                  throw new javax.jms.JMSException("Invalid Map value type " + object.getClass().getName());
               }

               nameValue.setChar(((Character)object).toString());
            }
         }
      }

   }

   private static void createObjectBody(WLJMSMessageDocument.WLJMSMessage.Body body, ObjectMessageImpl objectMessage) throws javax.jms.JMSException {
      objectMessage.decompressMessageBody();
      body.setObject(objectMessage.getBodyBytes());
   }

   private static void createXMLBody(WLJMSMessageDocument.WLJMSMessage.Body body, XMLMessageImpl xmlMessage) throws XmlException, javax.jms.JMSException {
      WLJMSMessageDocument.WLJMSMessage.Body.XML xml = body.addNewXML();
      xml.set(Factory.parse(xmlMessage.getDocument()));
   }
}
