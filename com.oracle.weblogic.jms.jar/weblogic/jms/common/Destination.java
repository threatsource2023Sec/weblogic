package weblogic.jms.common;

import java.io.IOException;
import java.io.ObjectInput;
import weblogic.jms.JMSClientExceptionLogger;

public abstract class Destination implements javax.jms.Destination {
   static final byte NULLDESTINATIONIMPL = 0;
   static final byte DESTINATIONIMPL = 1;
   static final byte DISTRIBUTEDDESTINATIONIMPL = 2;
   static final byte FUTUREDESTINATIONIMPL1 = 3;
   static final byte FUTUREDESTINATIONIMPL2 = 4;
   static final byte FUTUREDESTINATIONIMPL3 = 5;
   static final byte FUTUREDESTINATIONIMPL4 = 6;
   static final byte FUTUREDESTINATIONIMPL5 = 7;
   public static final byte _IFDESTCANNOTBENULL = 1;
   public static final byte _IFMUSTBEQUEUE = 2;
   public static final byte _IFMUSTBETOPIC = 4;

   public static int getDestinationType(Destination destination, int destinationShift) {
      if (destination == null) {
         return 0;
      } else {
         byte dtype = destination.getDestinationInstanceType();
         return dtype << destinationShift;
      }
   }

   protected abstract byte getDestinationInstanceType();

   public static boolean equalsForDS(Destination d1, Destination d2) {
      byte d1Type = d1.getDestinationInstanceType();
      byte d2Type = d2.getDestinationInstanceType();
      if (d1Type != d2Type) {
         return false;
      } else {
         switch (d1Type) {
            case 1:
               return d1.equals(d2);
            case 2:
               return ((DistributedDestinationImpl)d1).same(((DistributedDestinationImpl)d2).getName());
            default:
               return false;
         }
      }
   }

   public static DestinationImpl createDestination(byte type, ObjectInput in) throws IOException, ClassNotFoundException {
      DestinationImpl retDestinationImpl = null;
      switch (type) {
         case 0:
            return retDestinationImpl;
         case 1:
            DestinationImpl retDestinationImpl = new DestinationImpl();
            retDestinationImpl.readExternal(in);
            return retDestinationImpl;
         case 2:
            retDestinationImpl = new DistributedDestinationImpl();
            retDestinationImpl.readExternal(in);
            return retDestinationImpl;
         default:
            throw new IOException(JMSClientExceptionLogger.logInternalMarshallingErrorLoggable(type).getMessage());
      }
   }

   public static final void checkDestinationType(javax.jms.Destination destination, byte flags) throws javax.jms.JMSException {
      if (destination == null) {
         if ((flags & 1) != 0) {
            throw new javax.jms.InvalidDestinationException(JMSClientExceptionLogger.logDestinationNullLoggable().getMessage());
         }
      } else {
         if (!(destination instanceof DestinationImpl)) {
            throw new javax.jms.InvalidDestinationException(JMSClientExceptionLogger.logForeignDestination3Loggable(destination.toString()).getMessage());
         }

         if (flags != 0) {
            if ((flags & 2) != 0) {
               if (!((DestinationImpl)destination).isQueue()) {
                  throw new javax.jms.InvalidDestinationException(JMSClientExceptionLogger.logDestinationMustBeQueueLoggable(destination.toString()).getMessage());
               }
            } else if ((flags & 4) != 0 && !((DestinationImpl)destination).isTopic()) {
               throw new javax.jms.InvalidDestinationException(JMSClientExceptionLogger.logDestinationMustBeTopicLoggable(destination.toString()).getMessage());
            }
         }
      }

   }
}
