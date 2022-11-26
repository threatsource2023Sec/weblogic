package com.bea.core.repackaged.springframework.expression.common;

import com.bea.core.repackaged.springframework.expression.Expression;
import com.bea.core.repackaged.springframework.expression.ExpressionParser;
import com.bea.core.repackaged.springframework.expression.ParseException;
import com.bea.core.repackaged.springframework.expression.ParserContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public abstract class TemplateAwareExpressionParser implements ExpressionParser {
   public Expression parseExpression(String expressionString) throws ParseException {
      return this.parseExpression(expressionString, (ParserContext)null);
   }

   public Expression parseExpression(String expressionString, @Nullable ParserContext context) throws ParseException {
      return context != null && context.isTemplate() ? this.parseTemplate(expressionString, context) : this.doParseExpression(expressionString, context);
   }

   private Expression parseTemplate(String expressionString, ParserContext context) throws ParseException {
      if (expressionString.isEmpty()) {
         return new LiteralExpression("");
      } else {
         Expression[] expressions = this.parseExpressions(expressionString, context);
         return (Expression)(expressions.length == 1 ? expressions[0] : new CompositeStringExpression(expressionString, expressions));
      }
   }

   private Expression[] parseExpressions(String expressionString, ParserContext context) throws ParseException {
      List expressions = new ArrayList();
      String prefix = context.getExpressionPrefix();
      String suffix = context.getExpressionSuffix();
      int startIdx = 0;

      while(startIdx < expressionString.length()) {
         int prefixIndex = expressionString.indexOf(prefix, startIdx);
         if (prefixIndex >= startIdx) {
            if (prefixIndex > startIdx) {
               expressions.add(new LiteralExpression(expressionString.substring(startIdx, prefixIndex)));
            }

            int afterPrefixIndex = prefixIndex + prefix.length();
            int suffixIndex = this.skipToCorrectEndSuffix(suffix, expressionString, afterPrefixIndex);
            if (suffixIndex == -1) {
               throw new ParseException(expressionString, prefixIndex, "No ending suffix '" + suffix + "' for expression starting at character " + prefixIndex + ": " + expressionString.substring(prefixIndex));
            }

            if (suffixIndex == afterPrefixIndex) {
               throw new ParseException(expressionString, prefixIndex, "No expression defined within delimiter '" + prefix + suffix + "' at character " + prefixIndex);
            }

            String expr = expressionString.substring(prefixIndex + prefix.length(), suffixIndex);
            expr = expr.trim();
            if (expr.isEmpty()) {
               throw new ParseException(expressionString, prefixIndex, "No expression defined within delimiter '" + prefix + suffix + "' at character " + prefixIndex);
            }

            expressions.add(this.doParseExpression(expr, context));
            startIdx = suffixIndex + suffix.length();
         } else {
            expressions.add(new LiteralExpression(expressionString.substring(startIdx)));
            startIdx = expressionString.length();
         }
      }

      return (Expression[])expressions.toArray(new Expression[0]);
   }

   private boolean isSuffixHere(String expressionString, int pos, String suffix) {
      int suffixPosition = 0;

      for(int i = 0; i < suffix.length() && pos < expressionString.length(); ++i) {
         if (expressionString.charAt(pos++) != suffix.charAt(suffixPosition++)) {
            return false;
         }
      }

      return suffixPosition == suffix.length();
   }

   private int skipToCorrectEndSuffix(String suffix, String expressionString, int afterPrefixIndex) throws ParseException {
      int pos = afterPrefixIndex;
      int maxlen = expressionString.length();
      int nextSuffix = expressionString.indexOf(suffix, afterPrefixIndex);
      if (nextSuffix == -1) {
         return -1;
      } else {
         ArrayDeque stack;
         for(stack = new ArrayDeque(); pos < maxlen && (!this.isSuffixHere(expressionString, pos, suffix) || !stack.isEmpty()); ++pos) {
            char ch = expressionString.charAt(pos);
            switch (ch) {
               case '"':
               case '\'':
                  int endLiteral = expressionString.indexOf(ch, pos + 1);
                  if (endLiteral == -1) {
                     throw new ParseException(expressionString, pos, "Found non terminating string literal starting at position " + pos);
                  }

                  pos = endLiteral;
                  break;
               case '(':
               case '[':
               case '{':
                  stack.push(new Bracket(ch, pos));
                  break;
               case ')':
               case ']':
               case '}':
                  if (stack.isEmpty()) {
                     throw new ParseException(expressionString, pos, "Found closing '" + ch + "' at position " + pos + " without an opening '" + TemplateAwareExpressionParser.Bracket.theOpenBracketFor(ch) + "'");
                  }

                  Bracket p = (Bracket)stack.pop();
                  if (!p.compatibleWithCloseBracket(ch)) {
                     throw new ParseException(expressionString, pos, "Found closing '" + ch + "' at position " + pos + " but most recent opening is '" + p.bracket + "' at position " + p.pos);
                  }
            }
         }

         if (!stack.isEmpty()) {
            Bracket p = (Bracket)stack.pop();
            throw new ParseException(expressionString, p.pos, "Missing closing '" + TemplateAwareExpressionParser.Bracket.theCloseBracketFor(p.bracket) + "' for '" + p.bracket + "' at position " + p.pos);
         } else {
            return !this.isSuffixHere(expressionString, pos, suffix) ? -1 : pos;
         }
      }
   }

   protected abstract Expression doParseExpression(String var1, @Nullable ParserContext var2) throws ParseException;

   private static class Bracket {
      char bracket;
      int pos;

      Bracket(char bracket, int pos) {
         this.bracket = bracket;
         this.pos = pos;
      }

      boolean compatibleWithCloseBracket(char closeBracket) {
         if (this.bracket == '{') {
            return closeBracket == '}';
         } else if (this.bracket == '[') {
            return closeBracket == ']';
         } else {
            return closeBracket == ')';
         }
      }

      static char theOpenBracketFor(char closeBracket) {
         if (closeBracket == '}') {
            return '{';
         } else {
            return (char)(closeBracket == ']' ? '[' : '(');
         }
      }

      static char theCloseBracketFor(char openBracket) {
         if (openBracket == '{') {
            return '}';
         } else {
            return (char)(openBracket == '[' ? ']' : ')');
         }
      }
   }
}
