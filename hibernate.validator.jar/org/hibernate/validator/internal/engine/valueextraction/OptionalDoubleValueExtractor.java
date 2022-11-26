package org.hibernate.validator.internal.engine.valueextraction;

import java.util.OptionalDouble;
import javax.validation.valueextraction.UnwrapByDefault;
import javax.validation.valueextraction.ValueExtractor;

@UnwrapByDefault
class OptionalDoubleValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new OptionalDoubleValueExtractor());

   public void extractValues(OptionalDouble originalValue, ValueExtractor.ValueReceiver receiver) {
      receiver.value((String)null, originalValue.isPresent() ? originalValue.getAsDouble() : null);
   }
}
