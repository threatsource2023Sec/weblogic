package org.hibernate.validator.internal.engine.valueextraction;

import javafx.beans.value.ObservableValue;
import javax.validation.valueextraction.UnwrapByDefault;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;

@UnwrapByDefault
@IgnoreForbiddenApisErrors(
   reason = "Usage of JavaFX classes"
)
class ObservableValueValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new ObservableValueValueExtractor());

   private ObservableValueValueExtractor() {
   }

   public void extractValues(ObservableValue originalValue, ValueExtractor.ValueReceiver receiver) {
      receiver.value((String)null, originalValue.getValue());
   }
}
