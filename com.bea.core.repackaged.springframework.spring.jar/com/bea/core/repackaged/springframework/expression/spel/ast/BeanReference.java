package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.expression.AccessException;
import com.bea.core.repackaged.springframework.expression.BeanResolver;
import com.bea.core.repackaged.springframework.expression.EvaluationException;
import com.bea.core.repackaged.springframework.expression.TypedValue;
import com.bea.core.repackaged.springframework.expression.spel.ExpressionState;
import com.bea.core.repackaged.springframework.expression.spel.SpelEvaluationException;
import com.bea.core.repackaged.springframework.expression.spel.SpelMessage;

public class BeanReference extends SpelNodeImpl {
   private static final String FACTORY_BEAN_PREFIX = "&";
   private final String beanName;

   public BeanReference(int pos, String beanName) {
      super(pos);
      this.beanName = beanName;
   }

   public TypedValue getValueInternal(ExpressionState state) throws EvaluationException {
      BeanResolver beanResolver = state.getEvaluationContext().getBeanResolver();
      if (beanResolver == null) {
         throw new SpelEvaluationException(this.getStartPosition(), SpelMessage.NO_BEAN_RESOLVER_REGISTERED, new Object[]{this.beanName});
      } else {
         try {
            return new TypedValue(beanResolver.resolve(state.getEvaluationContext(), this.beanName));
         } catch (AccessException var4) {
            throw new SpelEvaluationException(this.getStartPosition(), var4, SpelMessage.EXCEPTION_DURING_BEAN_RESOLUTION, new Object[]{this.beanName, var4.getMessage()});
         }
      }
   }

   public String toStringAST() {
      StringBuilder sb = new StringBuilder();
      if (!this.beanName.startsWith("&")) {
         sb.append("@");
      }

      if (!this.beanName.contains(".")) {
         sb.append(this.beanName);
      } else {
         sb.append("'").append(this.beanName).append("'");
      }

      return sb.toString();
   }
}
