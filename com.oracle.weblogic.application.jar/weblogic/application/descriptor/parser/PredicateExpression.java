package weblogic.application.descriptor.parser;

public class PredicateExpression {
   private String keyName;
   private String indexValue;
   private String literalValue;

   public String getKeyName() {
      return this.keyName;
   }

   public void setKeyName(String keyName) {
      this.keyName = keyName;
   }

   public String getIndexValue() {
      return this.indexValue;
   }

   public void setIndexValue(String idx) {
      this.indexValue = idx;
   }

   public String getLiteralValue() {
      return this.literalValue;
   }

   public void setLiteralValue(String literal) {
      this.literalValue = literal;
   }
}
