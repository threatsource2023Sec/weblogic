package weblogic.jms.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import weblogic.jms.JMSLogger;
import weblogic.utils.StringUtils;

public final class JMSMessageExpirationHelper {
   public static final void logExpiredMessage(MessageImpl message, List jmsHeaders, List jmsProperties) {
      StringBuffer logHeaderBuffer = new StringBuffer(256);
      StringBuffer logPropertyBuffer = new StringBuffer(256);
      boolean hasHeader = buildLogHeaderString(jmsHeaders, message, logHeaderBuffer);
      boolean hasProperty = buildLogPropertyString(jmsProperties, message, logPropertyBuffer);
      if (hasHeader & hasProperty) {
         JMSLogger.logExpiredMessageHeaderProperty("'" + message.getJMSMessageID() + "'", logHeaderBuffer.toString(), logPropertyBuffer.toString());
      } else if (hasHeader) {
         JMSLogger.logExpiredMessageHeader("'" + message.getJMSMessageID() + "'", logHeaderBuffer.toString());
      } else if (hasProperty) {
         JMSLogger.logExpiredMessageProperty("'" + message.getJMSMessageID() + "'", logPropertyBuffer.toString());
      } else {
         JMSLogger.logExpiredMessageNoHeaderProperty("'" + message.getJMSMessageID() + "'");
      }

   }

   public static final void logExpiredSAFMessage(MessageImpl message, List jmsHeaders, List jmsProperties) {
      StringBuffer logHeaderBuffer = new StringBuffer(256);
      StringBuffer logPropertyBuffer = new StringBuffer(256);
      boolean hasHeader = buildLogHeaderString(jmsHeaders, message, logHeaderBuffer);
      boolean hasProperty = buildLogPropertyString(jmsProperties, message, logPropertyBuffer);
      if (hasHeader & hasProperty) {
         JMSLogger.logExpiredSAFMessageHeaderProperty("'" + message.getJMSMessageID() + "'", logHeaderBuffer.toString(), logPropertyBuffer.toString());
      } else if (hasHeader) {
         JMSLogger.logExpiredSAFMessageHeader("'" + message.getJMSMessageID() + "'", logHeaderBuffer.toString());
      } else if (hasProperty) {
         JMSLogger.logExpiredSAFMessageProperty("'" + message.getJMSMessageID() + "'", logPropertyBuffer.toString());
      } else {
         JMSLogger.logExpiredSAFMessageNoHeaderProperty("'" + message.getJMSMessageID() + "'");
      }

   }

   public static final boolean buildLogHeaderString(List jmsHeaders, MessageImpl message, StringBuffer logHeaderBuffer) {
      boolean notEmptyHeader = false;
      if (jmsHeaders != null && jmsHeaders.size() != 0) {
         ListIterator itr = jmsHeaders.listIterator(0);
         String firstHeaderItem = new String();
         String headerName = null;
         Object result = null;

         while(itr.hasNext()) {
            if (!notEmptyHeader) {
               notEmptyHeader = true;
               logHeaderBuffer.insert(0, "<HeaderFields ");
            }

            try {
               headerName = (String)itr.next();
               if (headerName.equalsIgnoreCase("JMSDestination")) {
                  result = message.getJMSDestination();
               } else if (headerName.equalsIgnoreCase("JMSReplyTo")) {
                  result = message.getJMSReplyTo();
               } else {
                  result = message.get(headerName);
               }

               if (result != null) {
                  if (headerName.equals("JMSExpiration")) {
                     result = new Date((Long)result);
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

                  logHeaderBuffer.append(firstHeaderItem + headerName + "='" + result + "'");
               } else {
                  logHeaderBuffer.append(firstHeaderItem + headerName + "=" + result);
               }

               if (firstHeaderItem.length() == 0) {
                  firstHeaderItem = "\n    ";
               }
            } catch (Exception var10) {
            }
         }

         logHeaderBuffer.append(" />");
         return notEmptyHeader;
      } else {
         return notEmptyHeader;
      }
   }

   private static final boolean buildLogPropertyString(List userProperties, MessageImpl message, StringBuffer logPropertyBuffer) {
      boolean notEmptyProperty = false;
      if (userProperties != null && userProperties.size() != 0 && message.hasProperties()) {
         ListIterator itr = userProperties.listIterator(0);
         String firstPropertyItem = new String();
         String logPropertyName = null;
         Object result = null;
         HashMap knownPropertyNames = new HashMap();

         while(itr.hasNext()) {
            try {
               logPropertyName = (String)itr.next();
               if (logPropertyName.equalsIgnoreCase("%Properties%")) {
                  if (!notEmptyProperty) {
                     notEmptyProperty = true;
                  }

                  Iterator propertyItr = null;
                  boolean idcHold = message.includeJMSXDeliveryCount(true);

                  try {
                     propertyItr = message.getPropertyNameCollection().iterator();
                  } finally {
                     message.includeJMSXDeliveryCount(idcHold);
                  }

                  while(propertyItr.hasNext()) {
                     String propertyName = (String)propertyItr.next();
                     if (knownPropertyNames.get(propertyName) == null) {
                        knownPropertyNames.put(propertyName, propertyName);
                        result = message.getStringProperty(propertyName);
                        appendTrucatedStringToLogBuffer(firstPropertyItem, propertyName, result, logPropertyBuffer);
                        if (firstPropertyItem.length() == 0) {
                           firstPropertyItem = "\n    ";
                        }
                     }
                  }
               } else if (message.propertyExists(logPropertyName) && knownPropertyNames.get(logPropertyName) == null) {
                  knownPropertyNames.put(logPropertyName, logPropertyName);
                  if (!notEmptyProperty) {
                     notEmptyProperty = true;
                  }

                  result = message.getStringProperty(logPropertyName);
                  appendTrucatedStringToLogBuffer(firstPropertyItem, logPropertyName, result, logPropertyBuffer);
                  if (firstPropertyItem.length() == 0) {
                     firstPropertyItem = "\n    ";
                  }
               }
            } catch (Exception var16) {
            }
         }

         logPropertyBuffer.insert(0, "<UserProperties ");
         logPropertyBuffer.append(" />");
         return notEmptyProperty;
      } else {
         return notEmptyProperty;
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
      if (inputString == null) {
         return null;
      } else {
         List list = new LinkedList();
         String[] propertyToken = StringUtils.splitCompletely(inputString, ",");
         if ((propertyToken == null || propertyToken.length == 0) && inputString.indexOf(",") == -1) {
            propertyToken = new String[]{inputString};
         }

         for(int i = 0; i < propertyToken.length; ++i) {
            if (!list.contains(propertyToken[i].trim())) {
               list.add(propertyToken[i].trim());
            }
         }

         return list;
      }
   }

   public static final List extractJMSHeaderAndProperty(String inputString, StringBuffer userProperties) {
      List list = new LinkedList();
      boolean foundPropertiesKey = false;
      boolean foundHeaderKey = false;
      if (inputString == null) {
         return null;
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

                  if (!list.contains("JMSDestination")) {
                     list.add("JMSDestination");
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
            } else if (headerPropertyToken[i].trim().equalsIgnoreCase("%Properties%")) {
               if (!foundPropertiesKey) {
                  foundPropertiesKey = true;
                  userProperties.append("%Properties%, ");
               }
            } else if (!foundPropertiesKey) {
               userProperties.append(headerPropertyToken[i].trim() + ", ");
            }
         }

         return list;
      }
   }
}
