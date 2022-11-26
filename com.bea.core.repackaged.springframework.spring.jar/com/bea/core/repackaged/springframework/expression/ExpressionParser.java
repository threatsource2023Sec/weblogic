package com.bea.core.repackaged.springframework.expression;

public interface ExpressionParser {
   Expression parseExpression(String var1) throws ParseException;

   Expression parseExpression(String var1, ParserContext var2) throws ParseException;
}
