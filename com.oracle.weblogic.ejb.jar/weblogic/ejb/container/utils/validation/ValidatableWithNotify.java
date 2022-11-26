package weblogic.ejb.container.utils.validation;

public interface ValidatableWithNotify extends Validatable {
   void addValidationListener(ValidationListener var1);

   void removeValidationListener(ValidationListener var1);
}
