package weblogic.xml.schema.types;

import javax.xml.namespace.QName;

public class IllegalLexicalValueException extends IllegalArgumentException {
   private final String lexicalValue;
   private final QName type;

   public IllegalLexicalValueException(String msg, String lexicalValue, QName type) {
      super(msg);
      this.lexicalValue = lexicalValue;
      this.type = type;
   }

   public IllegalLexicalValueException(String msg, String lexicalValue, QName type, Throwable cause) {
      this(msg, lexicalValue, type);
      this.initCause(cause);
   }

   public String getLexicalValue() {
      return this.lexicalValue;
   }

   public QName getType() {
      return this.type;
   }
}
