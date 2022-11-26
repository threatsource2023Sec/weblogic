package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Optional;
import javax.validation.valueextraction.ValueExtractor;

class OptionalValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new OptionalValueExtractor());

   private OptionalValueExtractor() {
   }

   public void extractValues(Optional originalValue, ValueExtractor.ValueReceiver receiver) {
      receiver.value((String)null, originalValue.isPresent() ? originalValue.get() : null);
   }
}
