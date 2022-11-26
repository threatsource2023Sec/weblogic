package org.hibernate.validator.internal.engine.valueextraction;

import java.util.OptionalLong;
import javax.validation.valueextraction.UnwrapByDefault;
import javax.validation.valueextraction.ValueExtractor;

@UnwrapByDefault
class OptionalLongValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new OptionalLongValueExtractor());

   public void extractValues(OptionalLong originalValue, ValueExtractor.ValueReceiver receiver) {
      receiver.value((String)null, originalValue.isPresent() ? originalValue.getAsLong() : null);
   }
}
