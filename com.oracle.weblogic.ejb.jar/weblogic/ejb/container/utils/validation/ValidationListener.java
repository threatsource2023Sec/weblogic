package weblogic.ejb.container.utils.validation;

import java.util.EventListener;

public interface ValidationListener extends EventListener {
   void validationPerformed(ValidationEvent var1);
}
