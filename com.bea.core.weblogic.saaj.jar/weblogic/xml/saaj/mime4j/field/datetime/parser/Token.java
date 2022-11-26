package weblogic.xml.saaj.mime4j.field.datetime.parser;

public class Token {
   public int kind;
   public int beginLine;
   public int beginColumn;
   public int endLine;
   public int endColumn;
   public String image;
   public Token next;
   public Token specialToken;

   public String toString() {
      return this.image;
   }

   public static final Token newToken(int ofKind) {
      switch (ofKind) {
         default:
            return new Token();
      }
   }
}
