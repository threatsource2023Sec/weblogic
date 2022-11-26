package org.hibernate.validator.internal.engine.valueextraction;

import javafx.beans.property.ListProperty;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;

@IgnoreForbiddenApisErrors(
   reason = "Usage of JavaFX classes"
)
class ListPropertyValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new ListPropertyValueExtractor());

   private ListPropertyValueExtractor() {
   }

   public void extractValues(ListProperty originalValue, ValueExtractor.ValueReceiver receiver) {
      for(int i = 0; i < originalValue.size(); ++i) {
         receiver.indexedValue("<list element>", i, originalValue.get(i));
      }

   }
}
