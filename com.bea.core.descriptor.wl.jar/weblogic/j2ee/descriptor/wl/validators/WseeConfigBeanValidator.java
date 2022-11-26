package weblogic.j2ee.descriptor.wl.validators;

import com.bea.core.descriptor.wl.descriptorWlLogger;
import javax.xml.datatype.DatatypeFactory;

public class WseeConfigBeanValidator {
   public static void validateInactivityTimeout(String duration) throws IllegalArgumentException {
      validateDuration("InactivityTimeout", duration);
   }

   public static void validateBaseRetransmissionInterval(String duration) throws IllegalArgumentException {
      validateDuration("BaseRetransmissionInterval", duration);
   }

   public static void validateAcknowledgementInterval(String duration) throws IllegalArgumentException {
      validateDuration("AcknowledgementInterval", duration);
   }

   public static void validateSequenceExpiration(String duration) throws IllegalArgumentException {
      validateDuration("SequenceExpiration", duration);
   }

   public static void validateBufferRetryDelay(String duration) throws IllegalArgumentException {
      validateDuration("BufferRetryDelay", duration);
   }

   public static void validateRetryDelay(String duration) throws IllegalArgumentException {
      validateDuration("RetryDelay", duration);
   }

   public static void validateDefaultLogicalStoreName(String name) throws IllegalArgumentException {
      validateLogicalStoreName("DefaultLogicalStoreName", name);
   }

   public static void validateLogicalStoreName(String name) throws IllegalArgumentException {
      validateLogicalStoreName("Name", name);
   }

   public static void validatePhysicalStoreName(String name) throws IllegalArgumentException {
      validateLogicalStoreName("Name", name);
   }

   public static void validateBufferQueueJndiName(String name) {
      validateJndiName("Name", name);
   }

   public static void validateConnectionFactoryJndiName(String name) throws IllegalArgumentException {
      validateJndiName("ConnectionFactoryJndiName", name);
   }

   public static void validateCleanerInterval(String duration) throws IllegalArgumentException {
      validateDuration("CleanerInterval", duration);
   }

   public static void validateDefaultMaximumObjectLifetime(String duration) throws IllegalArgumentException {
      validateDuration("DefaultMaximumObjectLifetime", duration);
   }

   public static void validateRequestBufferingQueueJndiName(String name) throws IllegalArgumentException {
      validateJndiName("RequestBufferingQueueJndiName", name);
   }

   public static void validateResponseBufferingQueueJndiName(String name) throws IllegalArgumentException {
      validateJndiName("ResponseBufferingQueueJndiName", name);
   }

   private static void validateJndiName(String attr, String name) throws IllegalArgumentException {
   }

   private static void validateLogicalStoreName(String attr, String name) throws IllegalArgumentException {
      boolean valid = !isEmpty(name);
      if (valid) {
         valid = Character.isJavaIdentifierStart(name.charAt(0));

         for(int i = 1; i < name.length(); ++i) {
            valid &= Character.isJavaIdentifierPart(name.charAt(i));
         }
      }

      if (!valid) {
         throw new IllegalArgumentException(descriptorWlLogger.logLogicalStoreNameNotValidLoggable(attr, name).getMessage());
      }
   }

   private static void validateDuration(String attr, String duration) throws IllegalArgumentException {
      try {
         DatatypeFactory.newInstance().newDuration(duration);
      } catch (Exception var3) {
         throw new IllegalArgumentException(descriptorWlLogger.logDurationNotValidLoggable(attr, duration, var3.toString()).getMessage());
      }
   }

   private static boolean isEmpty(String s) {
      return s == null || s.trim().length() == 0;
   }
}
