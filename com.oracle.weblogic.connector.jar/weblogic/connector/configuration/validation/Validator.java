package weblogic.connector.configuration.validation;

public interface Validator {
   void validate();

   int order();
}
