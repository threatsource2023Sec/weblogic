package org.hibernate.validator.internal.engine.messageinterpolation.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Token {
   private static final Pattern ESCAPED_OPENING_CURLY_BRACE = Pattern.compile("\\\\\\{");
   private static final Pattern ESCAPED_CLOSING_CURLY_BRACE = Pattern.compile("\\\\\\}");
   private boolean isParameter;
   private boolean isEL;
   private boolean terminated;
   private String value;
   private StringBuilder builder;

   public Token(String tokenStart) {
      this.builder = new StringBuilder();
      this.builder.append(tokenStart);
   }

   public Token(char tokenStart) {
      this(String.valueOf(tokenStart));
   }

   public void append(char character) {
      this.builder.append(character);
   }

   public void makeParameterToken() {
      this.isParameter = true;
   }

   public void makeELToken() {
      this.makeParameterToken();
      this.isEL = true;
   }

   public void terminate() {
      this.value = this.builder.toString();
      if (this.isEL) {
         Matcher matcher = ESCAPED_OPENING_CURLY_BRACE.matcher(this.value);
         this.value = matcher.replaceAll("{");
         matcher = ESCAPED_CLOSING_CURLY_BRACE.matcher(this.value);
         this.value = matcher.replaceAll("}");
      }

      this.builder = null;
      this.terminated = true;
   }

   public boolean isParameter() {
      return this.isParameter;
   }

   public String getTokenValue() {
      if (!this.terminated) {
         throw new IllegalStateException("Trying to retrieve token value for unterminated token");
      } else {
         return this.value;
      }
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("Token{");
      sb.append("value='").append(this.value).append('\'');
      sb.append(", terminated=").append(this.terminated);
      sb.append(", isEL=").append(this.isEL);
      sb.append(", isParameter=").append(this.isParameter);
      sb.append('}');
      return sb.toString();
   }
}
