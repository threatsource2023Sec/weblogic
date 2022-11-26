package com.bea.core.repackaged.springframework.context.expression;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanExpressionException;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanExpressionContext;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanExpressionResolver;
import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.expression.EvaluationContext;
import com.bea.core.repackaged.springframework.expression.Expression;
import com.bea.core.repackaged.springframework.expression.ExpressionParser;
import com.bea.core.repackaged.springframework.expression.ParserContext;
import com.bea.core.repackaged.springframework.expression.spel.SpelCompilerMode;
import com.bea.core.repackaged.springframework.expression.spel.SpelParserConfiguration;
import com.bea.core.repackaged.springframework.expression.spel.standard.SpelExpressionParser;
import com.bea.core.repackaged.springframework.expression.spel.support.StandardEvaluationContext;
import com.bea.core.repackaged.springframework.expression.spel.support.StandardTypeConverter;
import com.bea.core.repackaged.springframework.expression.spel.support.StandardTypeLocator;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StandardBeanExpressionResolver implements BeanExpressionResolver {
   public static final String DEFAULT_EXPRESSION_PREFIX = "#{";
   public static final String DEFAULT_EXPRESSION_SUFFIX = "}";
   private String expressionPrefix = "#{";
   private String expressionSuffix = "}";
   private ExpressionParser expressionParser;
   private final Map expressionCache = new ConcurrentHashMap(256);
   private final Map evaluationCache = new ConcurrentHashMap(8);
   private final ParserContext beanExpressionParserContext = new ParserContext() {
      public boolean isTemplate() {
         return true;
      }

      public String getExpressionPrefix() {
         return StandardBeanExpressionResolver.this.expressionPrefix;
      }

      public String getExpressionSuffix() {
         return StandardBeanExpressionResolver.this.expressionSuffix;
      }
   };

   public StandardBeanExpressionResolver() {
      this.expressionParser = new SpelExpressionParser();
   }

   public StandardBeanExpressionResolver(@Nullable ClassLoader beanClassLoader) {
      this.expressionParser = new SpelExpressionParser(new SpelParserConfiguration((SpelCompilerMode)null, beanClassLoader));
   }

   public void setExpressionPrefix(String expressionPrefix) {
      Assert.hasText(expressionPrefix, "Expression prefix must not be empty");
      this.expressionPrefix = expressionPrefix;
   }

   public void setExpressionSuffix(String expressionSuffix) {
      Assert.hasText(expressionSuffix, "Expression suffix must not be empty");
      this.expressionSuffix = expressionSuffix;
   }

   public void setExpressionParser(ExpressionParser expressionParser) {
      Assert.notNull(expressionParser, (String)"ExpressionParser must not be null");
      this.expressionParser = expressionParser;
   }

   @Nullable
   public Object evaluate(@Nullable String value, BeanExpressionContext evalContext) throws BeansException {
      if (!StringUtils.hasLength(value)) {
         return value;
      } else {
         try {
            Expression expr = (Expression)this.expressionCache.get(value);
            if (expr == null) {
               expr = this.expressionParser.parseExpression(value, this.beanExpressionParserContext);
               this.expressionCache.put(value, expr);
            }

            StandardEvaluationContext sec = (StandardEvaluationContext)this.evaluationCache.get(evalContext);
            if (sec == null) {
               sec = new StandardEvaluationContext(evalContext);
               sec.addPropertyAccessor(new BeanExpressionContextAccessor());
               sec.addPropertyAccessor(new BeanFactoryAccessor());
               sec.addPropertyAccessor(new MapAccessor());
               sec.addPropertyAccessor(new EnvironmentAccessor());
               sec.setBeanResolver(new BeanFactoryResolver(evalContext.getBeanFactory()));
               sec.setTypeLocator(new StandardTypeLocator(evalContext.getBeanFactory().getBeanClassLoader()));
               ConversionService conversionService = evalContext.getBeanFactory().getConversionService();
               if (conversionService != null) {
                  sec.setTypeConverter(new StandardTypeConverter(conversionService));
               }

               this.customizeEvaluationContext(sec);
               this.evaluationCache.put(evalContext, sec);
            }

            return expr.getValue((EvaluationContext)sec);
         } catch (Throwable var6) {
            throw new BeanExpressionException("Expression parsing failed", var6);
         }
      }
   }

   protected void customizeEvaluationContext(StandardEvaluationContext evalContext) {
   }
}
