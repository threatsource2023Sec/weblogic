package org.hibernate.validator.internal.engine.valueextraction;

import javafx.beans.property.ReadOnlyListProperty;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;

@IgnoreForbiddenApisErrors(
   reason = "Usage of JavaFX classes"
)
class ReadOnlyListPropertyValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new ReadOnlyListPropertyValueExtractor());

   private ReadOnlyListPropertyValueExtractor() {
   }

   public void extractValues(ReadOnlyListProperty originalValue, ValueExtractor.ValueReceiver receiver) {
      for(int i = 0; i < originalValue.size(); ++i) {
         receiver.indexedValue("<list element>", i, originalValue.get(i));
      }

   }
}
