package weblogic.ejb.container.utils.validation;

import java.util.Collection;

public interface Validatable {
   boolean isValid();

   Collection getErrors();

   void validate();
}
