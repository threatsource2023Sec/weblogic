package org.hibernate.validator.internal.engine.valueextraction;

import java.lang.invoke.MethodHandles;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class ValueExtractorHelper {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

   private ValueExtractorHelper() {
   }

   public static Set toValueExtractorClasses(Set valueExtractorDescriptors) {
      return (Set)valueExtractorDescriptors.stream().map((valueExtractorDescriptor) -> {
         return valueExtractorDescriptor.getValueExtractor().getClass();
      }).collect(Collectors.toSet());
   }

   public static void extractValues(ValueExtractorDescriptor valueExtractorDescriptor, Object containerValue, ValueExtractor.ValueReceiver valueReceiver) {
      ValueExtractor valueExtractor = valueExtractorDescriptor.getValueExtractor();

      try {
         valueExtractor.extractValues(containerValue, valueReceiver);
      } catch (ValidationException var5) {
         throw var5;
      } catch (Exception var6) {
         throw LOG.getErrorWhileExtractingValuesInValueExtractorException(valueExtractor.getClass(), var6);
      }
   }
}
