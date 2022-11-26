package weblogic.jms.backend;

import weblogic.application.ModuleException;
import weblogic.j2ee.descriptor.wl.DestinationKeyBean;
import weblogic.jms.common.MessageImpl;

public class BEDestinationKey {
   private static final int KEY_TYPE_JMS_MESSAGEID = 0;
   private static final int KEY_TYPE_JMS_TIMESTAMP = 1;
   private static final int KEY_TYPE_JMS_CORRELATIONID = 2;
   private static final int KEY_TYPE_JMS_PRIORITY = 3;
   private static final int KEY_TYPE_JMS_EXPIRATION = 4;
   private static final int KEY_TYPE_JMS_TYPE = 5;
   private static final int KEY_TYPE_JMS_REDELIVERED = 6;
   private static final int KEY_TYPE_JMS_DELIVERY_TIME = 7;
   private static final int KEY_TYPE_JMS_BEA_SIZE = 8;
   private static final int KEY_TYPE_JMS_BEA_UNITOFORDER = 9;
   private static final int KEY_TYPE_BOOLEAN = 15;
   private static final int KEY_TYPE_BYTE = 16;
   private static final int KEY_TYPE_SHORT = 17;
   private static final int KEY_TYPE_INT = 18;
   private static final int KEY_TYPE_LONG = 19;
   private static final int KEY_TYPE_FLOAT = 20;
   private static final int KEY_TYPE_DOUBLE = 21;
   private static final int KEY_TYPE_STRING = 22;
   static final String JMS_MESSAGE_ID = "JMSMessageID";
   private static final Integer DEFAULT_INTEGER = new Integer(Integer.MIN_VALUE);
   private static final Short DEFAULT_SHORT = new Short((short)Short.MIN_VALUE);
   private static final Long DEFAULT_LONG = new Long(Long.MIN_VALUE);
   private static final Float DEFAULT_FLOAT = new Float(Float.MIN_VALUE);
   private static final Byte DEFAULT_BYTE = new Byte((byte)-128);
   private static final Double DEFAULT_DOUBLE = new Double(Double.MIN_VALUE);
   private static final String DEFAULT_STRING = new String();
   private static final Boolean DEFAULT_BOOLEAN = new Boolean(true);
   protected static final int KEY_DIRECTION_ASCENDING = 0;
   private static final int KEY_DIRECTION_DESCENDING = 1;
   private static final int HEADER_PROPERTY = 0;
   private static final int USER_PROPERTY = 1;
   private BEDestinationImpl destination;
   private final String name;
   protected final String property;
   private int propType = 0;
   protected int keyType = -2;
   protected int direction = 0;

   public BEDestinationKey(BEDestinationImpl destination, DestinationKeyBean destinationKey) throws ModuleException {
      this.property = destinationKey.getProperty();
      this.name = destinationKey.getName();
      this.destination = destination;
      String stringDirection = destinationKey.getSortOrder();
      if (stringDirection.equalsIgnoreCase("Descending")) {
         this.direction = 1;
      }

      String stringKeyType = destinationKey.getKeyType();
      if (this.property.equalsIgnoreCase("JMSMessageID")) {
         this.keyType = 0;
      } else if (this.property.equalsIgnoreCase("JMSTimestamp")) {
         this.keyType = 1;
      } else if (this.property.equalsIgnoreCase("JMSCorrelationID")) {
         this.keyType = 2;
      } else if (this.property.equalsIgnoreCase("JMSPriority")) {
         this.keyType = 3;
      } else if (this.property.equalsIgnoreCase("JMSExpiration")) {
         this.keyType = 4;
      } else if (this.property.equalsIgnoreCase("JMSType")) {
         this.keyType = 5;
      } else if (this.property.equalsIgnoreCase("JMSRedelivered")) {
         this.keyType = 6;
      } else if (this.property.equalsIgnoreCase("JMSDeliveryTime")) {
         this.keyType = 7;
      } else if (this.property.equalsIgnoreCase("JMS_BEA_Size")) {
         this.keyType = 8;
      } else if (this.property.equalsIgnoreCase("JMS_BEA_UnitOfOrder")) {
         this.keyType = 9;
      } else if (stringKeyType.equalsIgnoreCase("Boolean")) {
         this.keyType = 15;
         this.propType = 1;
      } else if (stringKeyType.equalsIgnoreCase("Byte")) {
         this.keyType = 16;
         this.propType = 1;
      } else if (stringKeyType.equalsIgnoreCase("Short")) {
         this.keyType = 17;
         this.propType = 1;
      } else if (stringKeyType.equalsIgnoreCase("Int")) {
         this.keyType = 18;
         this.propType = 1;
      } else if (stringKeyType.equalsIgnoreCase("Long")) {
         this.keyType = 19;
         this.propType = 1;
      } else if (stringKeyType.equalsIgnoreCase("Float")) {
         this.keyType = 20;
         this.propType = 1;
      } else if (stringKeyType.equalsIgnoreCase("Double")) {
         this.keyType = 21;
         this.propType = 1;
      } else {
         if (!stringKeyType.equalsIgnoreCase("String")) {
            throw new ModuleException("JMS: One or more missing attributes for destination key '" + this.name + "'");
         }

         this.keyType = 22;
         this.propType = 1;
      }

   }

   public BEDestinationKey(BEDestinationImpl destination) {
      this.property = "JMSMessageID";
      this.name = "JMSMessageID";
      this.direction = 0;
      this.keyType = 0;
      this.destination = destination;
   }

   boolean isDefault() {
      return this.direction == 0 && this.keyType == 0 && (this.property == null || this.property == "JMSMessageID" || this.property != null && this.property.equalsIgnoreCase("JMSMessageID")) && (this.name == null || this.name == "JMSMessageID" || this.name != null && this.name.equalsIgnoreCase("JMSMessageID"));
   }

   long compareKey(MessageImpl m1, MessageImpl m2, boolean destIsLifo) {
      long ret = 0L;
      if (this.propType == 0) {
         switch (this.keyType) {
            case 0:
               ret = (long)m1.getId().compare(m2.getId());
               break;
            case 1:
               ret = m1.getJMSTimestamp() - m2.getJMSTimestamp();
               break;
            case 2:
               if (m1.getJMSCorrelationID() != null && m2.getJMSCorrelationID() != null) {
                  ret = (long)m1.getJMSCorrelationID().compareTo(m2.getJMSCorrelationID());
               } else if (m1.getJMSCorrelationID() != null) {
                  ret = 1L;
               } else if (m2.getJMSCorrelationID() != null) {
                  ret = -1L;
               } else {
                  ret = 0L;
               }
               break;
            case 3:
               ret = (long)(m1.getJMSPriority() - m2.getJMSPriority());
               break;
            case 4:
               ret = m1.getJMSExpiration() - m2.getJMSExpiration();
               break;
            case 5:
               if (m1.getJMSType() != null && m2.getJMSType() != null) {
                  ret = (long)m1.getJMSType().compareTo(m2.getJMSType());
               } else if (m1.getJMSType() != null) {
                  ret = 1L;
               } else if (m2.getJMSType() != null) {
                  ret = -1L;
               } else {
                  ret = 0L;
               }
               break;
            case 6:
               if (m1.getJMSRedelivered() && !m2.getJMSRedelivered()) {
                  ret = 1L;
               } else if (!m1.getJMSRedelivered() && m2.getJMSRedelivered()) {
                  ret = -1L;
               }
               break;
            case 7:
               ret = m1.getDeliveryTime() - m2.getDeliveryTime();
               break;
            case 8:
               ret = m1.size() - m2.size();
               break;
            case 9:
               if (m1.getUnitOfOrder() != null && m2.getUnitOfOrder() != null) {
                  ret = (long)m1.getUnitOfOrder().compareTo(m2.getUnitOfOrder());
               } else if (m1.getUnitOfOrder() != null) {
                  ret = 1L;
               } else if (m2.getUnitOfOrder() != null) {
                  ret = -1L;
               } else {
                  ret = 0L;
               }
         }
      } else {
         Object obj1;
         Object obj2;
         try {
            obj1 = m1.getObjectProperty(this.property);
            obj2 = m2.getObjectProperty(this.property);
            if (obj1 == null && obj2 == null) {
               return ret;
            }
         } catch (Throwable var9) {
            return ret;
         }

         boolean keyTypeBoolean = false;
         switch (this.keyType) {
            case 15:
               if (!(obj1 instanceof Boolean)) {
                  obj1 = DEFAULT_BOOLEAN;
               }

               if (!(obj2 instanceof Boolean)) {
                  obj2 = DEFAULT_BOOLEAN;
               }

               if ((Boolean)obj1 && !(Boolean)obj2) {
                  ret = 1L;
               } else if (!(Boolean)obj1 && (Boolean)obj2) {
                  ret = -1L;
               }

               keyTypeBoolean = true;
               break;
            case 16:
               if (!(obj1 instanceof Byte)) {
                  obj1 = DEFAULT_BYTE;
               }

               if (!(obj2 instanceof Byte)) {
                  obj2 = DEFAULT_BYTE;
               }
               break;
            case 17:
               if (!(obj1 instanceof Short)) {
                  obj1 = DEFAULT_SHORT;
               }

               if (!(obj2 instanceof Short)) {
                  obj2 = DEFAULT_SHORT;
               }
               break;
            case 18:
               if (!(obj1 instanceof Integer)) {
                  obj1 = DEFAULT_INTEGER;
               }

               if (!(obj2 instanceof Integer)) {
                  obj2 = DEFAULT_INTEGER;
               }
               break;
            case 19:
               if (!(obj1 instanceof Long)) {
                  obj1 = DEFAULT_LONG;
               }

               if (!(obj2 instanceof Long)) {
                  obj2 = DEFAULT_LONG;
               }
               break;
            case 20:
               if (!(obj1 instanceof Float)) {
                  obj1 = DEFAULT_FLOAT;
               }

               if (!(obj2 instanceof Float)) {
                  obj2 = DEFAULT_FLOAT;
               }
               break;
            case 21:
               if (!(obj1 instanceof Double)) {
                  obj1 = DEFAULT_DOUBLE;
               }

               if (!(obj2 instanceof Double)) {
                  obj2 = DEFAULT_DOUBLE;
               }
               break;
            case 22:
               if (!(obj1 instanceof String)) {
                  obj1 = DEFAULT_STRING;
               }

               if (!(obj2 instanceof String)) {
                  obj2 = DEFAULT_STRING;
               }
         }

         if (!keyTypeBoolean) {
            ret = (long)((Comparable)obj1).compareTo(obj2);
         }
      }

      if (destIsLifo) {
         return this.direction == 0 ? -ret : ret;
      } else {
         return this.direction == 0 ? ret : -ret;
      }
   }

   public String toString() {
      return "BEDestinationKey  propType=" + this.propType + ", keyType=" + this.keyType + ", direction=" + this.direction + ", name=" + this.name + ", property=" + this.property + " Dest=" + this.destination;
   }
}
