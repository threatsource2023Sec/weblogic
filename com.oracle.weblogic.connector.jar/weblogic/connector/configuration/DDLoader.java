package weblogic.connector.configuration;

import weblogic.connector.common.Debug;
import weblogic.xml.process.SAXValidationException;

public abstract class DDLoader {
   protected void validateInitialCapacity(int initialCapacity) throws SAXValidationException {
      if (initialCapacity < 0) {
         String exMsg = Debug.getExceptionInitialCapacityMustBePositive();
         throw new SAXValidationException(exMsg);
      }
   }

   protected void validateShrinkPeriodMinutes(int shrinkPeriodMinutes) throws SAXValidationException {
      this.validateShrinkFrequencySeconds(shrinkPeriodMinutes * 60);
   }

   protected void validateShrinkFrequencySeconds(int shrinkFrequencySeconds) throws SAXValidationException {
      if (shrinkFrequencySeconds <= 0) {
         String exMsg = Debug.getExceptionShrinkFrequencySecondsMustBePositive();
         throw new SAXValidationException(exMsg);
      }
   }

   protected void validateMaxIdleTime(int maxIdleTime) throws SAXValidationException {
      this.validateInactiveConnectionTimeoutSeconds(maxIdleTime);
   }

   protected void validateInactiveConnectionTimeoutSeconds(int inactiveTimeoutSeconds) throws SAXValidationException {
      if (inactiveTimeoutSeconds < 0) {
         String exMsg = Debug.getExceptionInactiveConnectionTimeoutSecondsNegative();
         throw new SAXValidationException(exMsg);
      }
   }

   protected void printDeprecationWarning(String oldElement, String newElement) {
      if (newElement != null) {
         Debug.logDeprecationReplacedWarning(oldElement, newElement);
      } else {
         Debug.logDeprecationNotUsedWarning(oldElement);
      }

   }

   protected void printDeletionWarning(String oldElement) {
      Debug.logDeprecationNotUsedWarning(oldElement);
   }
}
