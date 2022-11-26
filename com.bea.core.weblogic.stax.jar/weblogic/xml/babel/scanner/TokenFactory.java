package weblogic.xml.babel.scanner;

import weblogic.xml.util.TernarySearchTree;

final class TokenFactory {
   private TernarySearchTree namedTokens = new TernarySearchTree();
   private TernarySearchTree prefixTokens = new TernarySearchTree();
   private TernarySearchTree spaceTokens = new TernarySearchTree();
   private final int maxTrieSize = 1024;
   private Token[] delimeterTokens = new Token[60];

   public TokenFactory() {
   }

   public void init() {
      this.prefixTokens = new TernarySearchTree();
      this.namedTokens = new TernarySearchTree();
      this.spaceTokens = new TernarySearchTree();
   }

   public Token createEOF() {
      return new Token(-1);
   }

   public Token createToken(int tokenType) {
      Token token = this.delimeterTokens[tokenType];
      if (token == null) {
         token = new Token(tokenType);
         if (tokenType >= 0) {
            this.delimeterTokens[tokenType] = token;
         }
      }

      return token;
   }

   public Token createToken(int tokenType, String text) {
      Token token;
      switch (tokenType) {
         case 0:
            token = (Token)this.namedTokens.get(text);
            if (token == null) {
               token = new Token(tokenType, text);
               this.namedTokens.put(text, token);
            }
            break;
         default:
            token = new Token(tokenType, text);
      }

      return token;
   }

   public Token createStoredToken(int tokenType, char[] ch, int start, int length) {
      Token token;
      String text;
      switch (tokenType) {
         case 0:
            token = (Token)this.namedTokens.get(ch, start, length);
            if (token == null) {
               text = new String(ch, start, length);
               token = new Token(tokenType, text);
               this.namedTokens.put(text, token);
            }
            break;
         case 18:
            token = (Token)this.prefixTokens.get(ch, start, length);
            if (token == null) {
               text = new String(ch, start, length);
               token = new Token(tokenType, text);
               this.prefixTokens.put(text, token);
            }
            break;
         case 19:
            token = (Token)this.spaceTokens.get(ch, start, length);
            if (token == null) {
               text = new String(ch, start, length);
               token = new Token(tokenType, ch, start, length);
               this.spaceTokens.put(text, token);
            }
            break;
         default:
            text = new String(ch, start, length);
            token = new Token(tokenType, text);
      }

      return token;
   }

   public Token createToken(int tokenType, int subType, String text) {
      Token token = this.createToken(tokenType, text);
      token.subType = subType;
      return token;
   }

   public Token createToken(int tokenType, int subType, char c) {
      char[] buf = new char[]{c};
      String text = new String(buf);
      Token token = this.createToken(tokenType, text);
      token.subType = subType;
      return token;
   }

   public Token createToken(int tokenType, char[] ch, int start, int length) {
      return new Token(tokenType, ch, start, length);
   }
}
