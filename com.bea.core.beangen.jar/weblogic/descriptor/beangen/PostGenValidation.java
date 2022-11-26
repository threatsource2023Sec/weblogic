package weblogic.descriptor.beangen;

import java.util.ArrayList;

public abstract class PostGenValidation {
   protected String propName;
   private String message;
   protected ArrayList code = new ArrayList();

   public PostGenValidation(String propName, String message) {
      this.propName = propName;
      this.message = message;
   }

   public String getMessage() {
      return this.message;
   }

   public String[] getValidationCode() {
      return (String[])((String[])this.code.toArray(new String[0]));
   }

   public static class NonZeroLengthCheck extends PostGenValidation {
      private String defaultValue;

      public NonZeroLengthCheck(String propName, String message, String defaultValue) {
         super(propName, message);
         this.defaultValue = defaultValue;
      }

      public String[] getValidationCode() {
         this.code.add("LegalChecks.checkNonEmptyString(\"" + this.propName + "\", " + this.defaultValue + ");");
         return super.getValidationCode();
      }
   }

   public static class NonNullCheck extends PostGenValidation {
      private String defaultValue;

      public NonNullCheck(String propName, String message, String defaultValue) {
         super(propName, message);
         this.defaultValue = defaultValue;
      }

      public String[] getValidationCode() {
         this.code.add("LegalChecks.checkNonNull(\"" + this.propName + "\", " + this.defaultValue + ");");
         return super.getValidationCode();
      }
   }

   public static class EnumCheck extends PostGenValidation {
      String type;
      String legalValues;
      String defaultValue;

      public EnumCheck(String propName, String message, String type, String legalValues, String defaultValue) {
         super(propName, message);
         this.type = type;
         this.legalValues = legalValues;
         this.defaultValue = defaultValue;
      }

      public String[] getValidationCode() {
         this.code.add(this.type + "[] _set = { " + this.legalValues + " };");
         this.code.add("LegalChecks.checkInEnum(\"" + this.propName + "\", " + this.defaultValue + ", _set);");
         return super.getValidationCode();
      }
   }
}
