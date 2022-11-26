package weblogic.jms.common;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import org.w3c.dom.Document;
import weblogic.apache.xml.serialize.OutputFormat;
import weblogic.apache.xml.serialize.XMLSerializer;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.jms.backend.BEConnectionConsumerImpl;
import weblogic.jms.backend.BEConsumerImpl;
import weblogic.jms.backend.MessageConsumerCreationEvent;
import weblogic.jms.backend.MessageConsumerDestroyEvent;
import weblogic.logging.jms.JMSMessageAddLogRecord;
import weblogic.logging.jms.JMSMessageConsumerCreationLogRecord;
import weblogic.logging.jms.JMSMessageConsumerDestroyLogRecord;
import weblogic.logging.jms.JMSMessageExceededLogRecord;
import weblogic.logging.jms.JMSMessageExpireLogRecord;
import weblogic.logging.jms.JMSMessageForwardLogRecord;
import weblogic.logging.jms.JMSMessageLogRecord;
import weblogic.logging.jms.JMSMessageLogger;
import weblogic.logging.jms.JMSMessageReceiveLogRecord;
import weblogic.logging.jms.JMSMessageRemoveLogRecord;
import weblogic.logging.jms.JMSMessageSendLogRecord;
import weblogic.logging.jms.JMSMessageStoreLogRecord;
import weblogic.logging.jms.JMSSAFMessageLogger;
import weblogic.messaging.kernel.Event;
import weblogic.messaging.kernel.MessageAddEvent;
import weblogic.messaging.kernel.MessageConsumerEvent;
import weblogic.messaging.kernel.MessageEvent;
import weblogic.messaging.kernel.MessageExpirationEvent;
import weblogic.messaging.kernel.MessageReceiveEvent;
import weblogic.messaging.kernel.MessageRedeliveryLimitEvent;
import weblogic.messaging.kernel.MessageRemoveEvent;
import weblogic.messaging.kernel.MessageSendEvent;
import weblogic.utils.StringUtils;

public final class JMSMessageLogHelper {
   private static final DebugLogger debugSAFLogger = DebugLogger.getDebugLogger("DebugSAFLifeCycle");
   private static final String DELIMITER = " ";

   public static final String createLogMessage(MessageImpl message, List jmsHeaders, List jmsProperties) {
      StringBuffer logHeaderBuffer = new StringBuffer(256);
      StringBuffer logPropertyBuffer = new StringBuffer(256);
      buildLogHeaderString(jmsHeaders, message, logHeaderBuffer);
      buildLogPropertyString(jmsProperties, message, logPropertyBuffer);
      return logHeaderBuffer.toString() != null && logHeaderBuffer.toString().length() != 0 && logPropertyBuffer.toString() != null && logPropertyBuffer.toString().length() != 0 ? logHeaderBuffer.toString() + " " + logPropertyBuffer.toString() : logPropertyBuffer.toString();
   }

   public static final void buildLogHeaderString(List jmsHeaders, MessageImpl message, StringBuffer logHeaderBuffer) {
      String delimiter = "";
      if (jmsHeaders != null && jmsHeaders.size() != 0) {
         ListIterator itr = jmsHeaders.listIterator(0);
         String headerName = null;
         Object result = null;

         while(itr.hasNext()) {
            try {
               headerName = (String)itr.next();
               if (!headerName.equalsIgnoreCase("JMSDestination")) {
                  if (headerName.equalsIgnoreCase("JMSReplyTo")) {
                     result = message.getJMSReplyTo();
                  } else {
                     result = message.get(headerName);
                  }

                  if (result == null) {
                     logHeaderBuffer.append(delimiter + headerName + "=" + result);
                     delimiter = " ";
                  } else {
                     if (headerName.equals("JMSExpiration")) {
                        if ((Long)result == 0L) {
                           result = "Never";
                        } else {
                           result = new Date((Long)result);
                        }
                     } else if (headerName.equals("JMSTimestamp")) {
                        result = new Date((Long)result);
                     } else if (!headerName.equals("JMSCorrelationID") && !headerName.equals("JMSType")) {
                        if (headerName.equals("JMSDeliveryTime")) {
                           if ((Long)result > 0L) {
                              result = new Date((Long)result);
                           } else {
                              result = new Date(message.getJMSTimestamp());
                           }
                        }
                     } else {
                        result = truncatedIt(result);
                     }

                     logHeaderBuffer.append(delimiter + headerName + "='" + result + "'");
                     delimiter = " ";
                  }
               }
            } catch (Exception var9) {
            }
         }

      }
   }

   private static final void buildLogPropertyString(List userProperties, MessageImpl message, StringBuffer logPropertyBuffer) {
      String delimiter = "";
      Enumeration propertyEnum = null;
      boolean idcHold = message.includeJMSXDeliveryCount(true);

      try {
         propertyEnum = message.getPropertyNames();
      } catch (javax.jms.JMSException var15) {
      } finally {
         message.includeJMSXDeliveryCount(idcHold);
      }

      if (userProperties != null && userProperties.size() != 0 && propertyEnum != null && propertyEnum.hasMoreElements()) {
         ListIterator itr = userProperties.listIterator(0);
         String logPropertyName = null;
         Object result = null;
         HashMap knownPropertyNames = new HashMap();

         while(itr.hasNext()) {
            try {
               logPropertyName = (String)itr.next();
               if (logPropertyName.equalsIgnoreCase("%properties%")) {
                  while(propertyEnum.hasMoreElements()) {
                     String propertyName = (String)propertyEnum.nextElement();
                     if (knownPropertyNames.get(propertyName) == null) {
                        knownPropertyNames.put(propertyName, propertyName);
                        result = message.getStringProperty(propertyName);
                        appendTrucatedStringToLogBuffer(delimiter, propertyName, result, logPropertyBuffer);
                        delimiter = " ";
                     }
                  }
               } else if (message.propertyExists(logPropertyName) && knownPropertyNames.get(logPropertyName) == null) {
                  knownPropertyNames.put(logPropertyName, logPropertyName);
                  result = message.getStringProperty(logPropertyName);
                  appendTrucatedStringToLogBuffer(delimiter, logPropertyName, result, logPropertyBuffer);
                  delimiter = " ";
               }
            } catch (Exception var17) {
            }
         }

      }
   }

   private static final void appendTrucatedStringToLogBuffer(String delimiter, String fieldName, Object value, StringBuffer logBuffer) {
      String truncatedString;
      if (value != null) {
         truncatedString = truncatedIt(value);
      } else {
         truncatedString = null;
      }

      if (truncatedString != null) {
         logBuffer.append(delimiter + fieldName + "='" + truncatedString + "'");
      } else {
         logBuffer.append(delimiter + fieldName + "=" + truncatedString);
      }

   }

   private static final String truncatedIt(Object obj) {
      String str = obj instanceof String ? (String)obj : obj.toString();
      if (str == null) {
         return null;
      } else {
         return str.length() > 32 ? str.substring(0, 32) + "..." : str;
      }
   }

   public static final List convertStringToLinkedList(String inputString) {
      List list = new LinkedList();
      if (inputString == null) {
         return list;
      } else {
         inputString = inputString.trim();
         if (inputString.length() == 0) {
            return list;
         } else {
            String[] propertyToken = StringUtils.splitCompletely(inputString, ",");
            if ((propertyToken == null || propertyToken.length == 0) && inputString.indexOf(",") == -1) {
               propertyToken = new String[]{inputString};
            }

            for(int i = 0; i < propertyToken.length; ++i) {
               String name = propertyToken[i].trim();
               if (!list.contains(name)) {
                  list.add(name);
               }
            }

            return list;
         }
      }
   }

   public static final List extractJMSHeaderAndProperty(String inputString, StringBuffer userProperties) {
      List list = new LinkedList();
      boolean foundPropertiesKey = false;
      boolean foundBodyKey = false;
      boolean foundHeaderKey = false;
      if (inputString == null) {
         return list;
      } else {
         String[] headerPropertyToken = StringUtils.splitCompletely(inputString, ",");
         if ((headerPropertyToken == null || headerPropertyToken.length == 0) && inputString.indexOf(",") == -1) {
            headerPropertyToken = new String[]{inputString};
         }

         for(int i = 0; i < headerPropertyToken.length; ++i) {
            if (MessageImpl.isHeaderField(headerPropertyToken[i].trim())) {
               if (!foundHeaderKey && !list.contains(headerPropertyToken[i].trim())) {
                  list.add(headerPropertyToken[i].trim());
               }
            } else if (headerPropertyToken[i].trim().startsWith("getJMS")) {
               if (MessageImpl.isHeaderField(headerPropertyToken[i].trim().substring(3))) {
                  if (!foundHeaderKey && !list.contains(headerPropertyToken[i].trim().substring(3))) {
                     list.add(headerPropertyToken[i].trim().substring(3));
                  }
               } else if (!foundPropertiesKey) {
                  userProperties.append(headerPropertyToken[i].trim() + ", ");
               }
            } else if (headerPropertyToken[i].trim().equalsIgnoreCase("%Header%")) {
               if (!foundHeaderKey) {
                  foundHeaderKey = true;
                  if (!list.contains("JMSCorrelationID")) {
                     list.add("JMSCorrelationID");
                  }

                  if (!list.contains("JMSDeliveryMode")) {
                     list.add("JMSDeliveryMode");
                  }

                  if (!list.contains("JMSDeliveryTime")) {
                     list.add("JMSDeliveryTime");
                  }

                  if (!list.contains("JMSExpiration")) {
                     list.add("JMSExpiration");
                  }

                  if (!list.contains("JMSPriority")) {
                     list.add("JMSPriority");
                  }

                  if (!list.contains("JMSRedelivered")) {
                     list.add("JMSRedelivered");
                  }

                  if (!list.contains("JMSRedeliveryLimit")) {
                     list.add("JMSRedeliveryLimit");
                  }

                  if (!list.contains("JMSReplyTo")) {
                     list.add("JMSReplyTo");
                  }

                  if (!list.contains("JMSTimestamp")) {
                     list.add("JMSTimestamp");
                  }

                  if (!list.contains("JMSType")) {
                     list.add("JMSType");
                  }
               }
            } else if (headerPropertyToken[i].trim().equalsIgnoreCase("%properties%")) {
               if (!foundPropertiesKey) {
                  foundPropertiesKey = true;
                  userProperties.append("%properties%, ");
               }
            } else if (headerPropertyToken[i].trim().equalsIgnoreCase("%body%")) {
               if (!foundBodyKey) {
                  foundBodyKey = true;
                  userProperties.append("%body%, ");
               }
            } else if (!foundPropertiesKey) {
               userProperties.append(headerPropertyToken[i].trim() + ", ");
            }
         }

         return list;
      }
   }

   public static String addSubscriberInfo(BEConsumerImpl consumer) {
      if (consumer instanceof BEConnectionConsumerImpl) {
         return "CC";
      } else if (consumer.getSession() != null && consumer.getSession().getConnection() != null) {
         String addr = consumer.getSession().getConnection().getAddress();
         if (addr == null) {
            return null;
         } else {
            int index = addr.indexOf("|");
            return index < 0 ? null : "MC:CA(" + addr.substring(0, index) + "):OAMI(" + addr.substring(index + 1) + ".connection" + ((JMSID)consumer.getSession().getConnection().getId()).getCounter() + ".session" + ((JMSID)consumer.getSession().getId()).getCounter() + ".consumer" + ((JMSID)consumer.getId()).getCounter() + ")";
         }
      } else {
         return null;
      }
   }

   public static void logMessageEvent(JMSMessageEventLogListener listener, Event event) {
      JMSMessageLogger logger = listener.getJMSMessageLogger();
      JMSMessageLogRecord record = null;
      String subscriptionName = null;
      String subject = null;
      String subscriberInfo = null;
      String selector = null;
      if (event instanceof MessageConsumerEvent) {
         String tempSubscriberInfo = ((MessageConsumerEvent)event).getUserBlob();
         if (tempSubscriberInfo != null) {
            int idx = tempSubscriberInfo.indexOf("#");
            if (idx != -1) {
               subscriberInfo = tempSubscriberInfo.substring(0, idx);
               subject = tempSubscriberInfo.substring(idx + 1);
            }
         }
      }

      if (listener != null && listener instanceof BEConsumerImpl) {
         BEConsumerImpl consumer = (BEConsumerImpl)listener;
         if (event instanceof MessageConsumerCreationEvent) {
            selector = ((MessageConsumerEvent)event).getSelector();
         }

         if (consumer.isDurable()) {
            subscriptionName = "DS:" + consumer.getClientID() + "." + consumer.getSubscriptionName();
         }

         if (event instanceof MessageSendEvent || subscriptionName == null) {
            subscriberInfo = addSubscriberInfo(consumer);
         }
      }

      if (subject == null) {
         subject = event.getSubjectName();
      }

      if (event instanceof MessageSendEvent) {
         if (logger instanceof JMSSAFMessageLogger) {
            record = createStoreLogRecord(logger, listener, (MessageSendEvent)event, subject, subscriptionName != null ? subscriptionName : subscriberInfo);
         } else {
            record = createSendLogRecord(logger, listener, (MessageSendEvent)event, subject, subscriptionName != null ? subscriptionName : subscriberInfo);
         }
      } else if (event instanceof MessageAddEvent) {
         record = createAddLogRecord(logger, listener, (MessageAddEvent)event, subject, subscriptionName);
      } else if (event instanceof MessageReceiveEvent) {
         if (logger instanceof JMSSAFMessageLogger) {
            record = createForwardLogRecord(logger, listener, (MessageReceiveEvent)event, subject, subscriptionName != null ? subscriptionName + "[" + subscriberInfo + "]" : subscriberInfo);
         } else {
            record = createReceiveLogRecord(logger, listener, (MessageReceiveEvent)event, subject, subscriptionName != null ? subscriptionName + "[" + subscriberInfo + "]" : subscriberInfo);
         }
      } else if (event instanceof MessageExpirationEvent) {
         record = createExpireLogRecord(logger, listener, (MessageExpirationEvent)event, subject, subscriptionName);
      } else if (event instanceof MessageRedeliveryLimitEvent) {
         record = createRedeliveryLimitLogRecord(logger, listener, (MessageRedeliveryLimitEvent)event, subject, subscriptionName);
      } else if (event instanceof MessageRemoveEvent) {
         record = createRemoveLogRecord(logger, listener, (MessageRemoveEvent)event, subject, subscriptionName);
      } else if (event instanceof MessageConsumerCreationEvent) {
         record = createConsumerCreationLogRecord(logger, listener, (MessageConsumerCreationEvent)event, subject, subscriptionName != null ? subscriptionName + "[" + subscriberInfo + "]" : subscriberInfo, selector);
      } else if (event instanceof MessageConsumerDestroyEvent) {
         record = createConsumerDestroyLogRecord(logger, listener, (MessageConsumerDestroyEvent)event, subject, subscriptionName != null ? subscriptionName + "[" + subscriberInfo + "]" : subscriberInfo);
      }

      if (record != null) {
         logger.log(record);
         if (logger instanceof JMSSAFMessageLogger && debugSAFLogger.isDebugEnabled()) {
            debugSAFLogger.debug("State: '" + record.getJMSMessageState() + "', EventTimeStamp: " + record.getEventTimeMillisStamp() + " JMSMessageID: " + record.getJMSMessageId() + " JMSCorrelationID: " + record.getJMSCorrelationId() + " DestinationName: " + record.getJMSDestinationName());
         }
      }

   }

   private static final String createMessageLogMessage(JMSMessageEventLogListener listener, MessageImpl message) {
      try {
         return getMessageDocument(message, listener.getMessageLoggingJMSHeaders(), listener.getMessageLoggingUserProperties());
      } catch (javax.jms.JMSException var3) {
         return null;
      }
   }

   private static final JMSMessageLogRecord createSendLogRecord(JMSMessageLogger logger, JMSMessageEventLogListener listener, MessageSendEvent event, String subject, String subscriptionName) {
      return new JMSMessageSendLogRecord(getMillis(logger, event), getNano(logger, event), getJMSMessageContent(listener, event), listener.getListenerName(), ((MessageImpl)event.getMessage()).getJMSMessageID(), ((MessageImpl)event.getMessage()).getJMSCorrelationID(), subject, subscriptionName, event.getXid());
   }

   private static final JMSMessageLogRecord createStoreLogRecord(JMSMessageLogger logger, JMSMessageEventLogListener listener, MessageSendEvent event, String subject, String subscriptionName) {
      return new JMSMessageStoreLogRecord(getMillis(logger, event), getNano(logger, event), getJMSMessageContent(listener, event), listener.getListenerName(), ((MessageImpl)event.getMessage()).getJMSMessageID(), ((MessageImpl)event.getMessage()).getJMSCorrelationID(), subject, subscriptionName, event.getXid());
   }

   private static final JMSMessageLogRecord createAddLogRecord(JMSMessageLogger logger, JMSMessageEventLogListener listener, MessageAddEvent event, String subject, String subscriptionName) {
      return new JMSMessageAddLogRecord(getMillis(logger, event), getNano(logger, event), getJMSMessageContent(listener, event), listener.getListenerName(), ((MessageImpl)event.getMessage()).getJMSMessageID(), ((MessageImpl)event.getMessage()).getJMSCorrelationID(), subject, subscriptionName, event.getXid());
   }

   private static final JMSMessageLogRecord createReceiveLogRecord(JMSMessageLogger logger, JMSMessageEventLogListener listener, MessageReceiveEvent event, String subject, String subscriptionName) {
      return new JMSMessageReceiveLogRecord(getMillis(logger, event), getNano(logger, event), getJMSMessageContent(listener, event), listener.getListenerName(), ((MessageImpl)event.getMessage()).getJMSMessageID(), ((MessageImpl)event.getMessage()).getJMSCorrelationID(), subject, subscriptionName, event.getXid());
   }

   private static final JMSMessageLogRecord createForwardLogRecord(JMSMessageLogger logger, JMSMessageEventLogListener listener, MessageReceiveEvent event, String subject, String subscriptionName) {
      return new JMSMessageForwardLogRecord(getMillis(logger, event), getNano(logger, event), getJMSMessageContent(listener, event), listener.getListenerName(), ((MessageImpl)event.getMessage()).getJMSMessageID(), ((MessageImpl)event.getMessage()).getJMSCorrelationID(), subject, subscriptionName, event.getXid());
   }

   private static final JMSMessageLogRecord createExpireLogRecord(JMSMessageLogger logger, JMSMessageEventLogListener listener, MessageExpirationEvent event, String subject, String subscriptionName) {
      return new JMSMessageExpireLogRecord(getMillis(logger, event), getNano(logger, event), getJMSMessageContent(listener, event), listener.getListenerName(), ((MessageImpl)event.getMessage()).getJMSMessageID(), ((MessageImpl)event.getMessage()).getJMSCorrelationID(), subject, subscriptionName, event.getXid());
   }

   private static final JMSMessageLogRecord createRemoveLogRecord(JMSMessageLogger logger, JMSMessageEventLogListener listener, MessageRemoveEvent event, String subject, String subscriptionName) {
      return new JMSMessageRemoveLogRecord(getMillis(logger, event), getNano(logger, event), getJMSMessageContent(listener, event), listener.getListenerName(), ((MessageImpl)event.getMessage()).getJMSMessageID(), ((MessageImpl)event.getMessage()).getJMSCorrelationID(), subject, subscriptionName, event.getXid());
   }

   private static final JMSMessageLogRecord createRedeliveryLimitLogRecord(JMSMessageLogger logger, JMSMessageEventLogListener listener, MessageRedeliveryLimitEvent event, String subject, String subscriptionName) {
      return new JMSMessageExceededLogRecord(getMillis(logger, event), getNano(logger, event), getJMSMessageContent(listener, event), listener.getListenerName(), ((MessageImpl)event.getMessage()).getJMSMessageID(), ((MessageImpl)event.getMessage()).getJMSCorrelationID(), subject, subscriptionName, event.getXid());
   }

   private static final JMSMessageLogRecord createConsumerCreationLogRecord(JMSMessageLogger logger, JMSMessageEventLogListener listener, MessageConsumerCreationEvent event, String subject, String subscriptionName, String selector) {
      return new JMSMessageConsumerCreationLogRecord(getMillis(logger, event), getNano(logger, event), listener.getListenerName(), subject, subscriptionName, selector);
   }

   private static final JMSMessageLogRecord createConsumerDestroyLogRecord(JMSMessageLogger logger, JMSMessageEventLogListener listener, MessageConsumerDestroyEvent event, String subject, String subscriptionName) {
      return new JMSMessageConsumerDestroyLogRecord(getMillis(logger, event), getNano(logger, event), listener.getListenerName(), subject, subscriptionName);
   }

   private static boolean isMessageBodyNeeded(List properties) {
      if (properties == null) {
         return false;
      } else {
         Iterator itr = properties.iterator();

         String property;
         do {
            if (!itr.hasNext()) {
               return false;
            }

            property = (String)itr.next();
         } while(!"%body%".equalsIgnoreCase(property));

         return true;
      }
   }

   private static String getMessageDocument(MessageImpl message, List headers, List properties) throws javax.jms.JMSException {
      if ((headers == null || headers.size() == 0) && (properties == null || properties.size() == 0)) {
         return null;
      } else {
         Document doc = XMLHelper.getDocument(message, headers, properties, isMessageBodyNeeded(properties));

         try {
            StringWriter writer = new StringWriter();
            OutputFormat format = new OutputFormat(doc);
            XMLSerializer serializer = new XMLSerializer(writer, format);
            serializer.serialize(doc);
            return writer.toString();
         } catch (IOException var7) {
            throw new JMSException("Wrap IOException inside", var7);
         }
      }
   }

   private static long getMillis(JMSMessageLogger logger, Event event) {
      return logger.doNano() ? logger.getStartMillisTime() + (event.getNanoseconds() - logger.getStartNanoTime()) / 1000000L : event.getMilliseconds();
   }

   private static long getNano(JMSMessageLogger logger, Event event) {
      return logger.doNano() ? (event.getNanoseconds() - logger.getStartNanoTime()) % 1000000L : 0L;
   }

   private static final String getJMSMessageContent(JMSMessageEventLogListener listener, MessageEvent event) {
      MessageImpl message = (MessageImpl)event.getMessage();
      message = message.cloneit();
      message.setDeliveryCount(event.getDeliveryCount());
      return createMessageLogMessage(listener, message);
   }
}
