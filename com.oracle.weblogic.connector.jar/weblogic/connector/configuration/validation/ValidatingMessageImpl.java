package weblogic.connector.configuration.validation;

import java.util.Map;
import weblogic.connector.utils.BaseValidationMessageImpl;
import weblogic.connector.utils.ValidationMessage;

public class ValidatingMessageImpl extends BaseValidationMessageImpl {
   public ValidatingMessageImpl(ValidationMessage baseMessage, String[] criticalSubComponents, Map child2ParentMap) {
      super(baseMessage, criticalSubComponents, child2ParentMap);
   }

   public ValidatingMessageImpl(ValidationMessage baseMessage) {
      super(baseMessage);
   }

   public void warning(String message, int order) {
      super.warning(message, order);
   }
}
