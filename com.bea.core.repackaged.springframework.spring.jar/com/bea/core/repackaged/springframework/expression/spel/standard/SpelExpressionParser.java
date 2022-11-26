package com.bea.core.repackaged.springframework.expression.spel.standard;

import com.bea.core.repackaged.springframework.expression.ParseException;
import com.bea.core.repackaged.springframework.expression.ParserContext;
import com.bea.core.repackaged.springframework.expression.common.TemplateAwareExpressionParser;
import com.bea.core.repackaged.springframework.expression.spel.SpelParserConfiguration;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class SpelExpressionParser extends TemplateAwareExpressionParser {
   private final SpelParserConfiguration configuration;

   public SpelExpressionParser() {
      this.configuration = new SpelParserConfiguration();
   }

   public SpelExpressionParser(SpelParserConfiguration configuration) {
      Assert.notNull(configuration, (String)"SpelParserConfiguration must not be null");
      this.configuration = configuration;
   }

   public SpelExpression parseRaw(String expressionString) throws ParseException {
      return this.doParseExpression(expressionString, (ParserContext)null);
   }

   protected SpelExpression doParseExpression(String expressionString, @Nullable ParserContext context) throws ParseException {
      return (new InternalSpelExpressionParser(this.configuration)).doParseExpression(expressionString, context);
   }
}
