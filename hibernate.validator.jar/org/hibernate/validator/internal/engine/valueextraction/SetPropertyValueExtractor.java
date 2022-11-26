package org.hibernate.validator.internal.engine.valueextraction;

import java.util.Iterator;
import javafx.beans.property.SetProperty;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;

@IgnoreForbiddenApisErrors(
   reason = "Usage of JavaFX classes"
)
class SetPropertyValueExtractor implements ValueExtractor {
   static final ValueExtractorDescriptor DESCRIPTOR = new ValueExtractorDescriptor(new SetPropertyValueExtractor());

   private SetPropertyValueExtractor() {
   }

   public void extractValues(SetProperty originalValue, ValueExtractor.ValueReceiver receiver) {
      Iterator var3 = originalValue.iterator();

      while(var3.hasNext()) {
         Object object = var3.next();
         receiver.iterableValue("<iterable element>", object);
      }

   }
}
