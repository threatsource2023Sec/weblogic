package org.hibernate.validator.internal.engine.valueextraction;

import java.util.OptionalInt;
import javax.validation.valueextraction.UnwrapByDefault;
import javax.validation.valueextraction.ValueExtractor;

@UnwrapByDefault
class OptionalIntValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new OptionalIntValueExtractor());

   public void extractValues(OptionalInt originalValue, ValueExtractor.ValueReceiver receiver) {
      receiver.value((String)null, originalValue.isPresent() ? originalValue.getAsInt() : null);
   }
}
