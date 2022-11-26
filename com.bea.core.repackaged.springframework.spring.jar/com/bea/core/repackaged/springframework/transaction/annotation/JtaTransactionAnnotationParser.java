package com.bea.core.repackaged.springframework.transaction.annotation;

import com.bea.core.repackaged.springframework.core.annotation.AnnotatedElementUtils;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.transaction.interceptor.NoRollbackRuleAttribute;
import com.bea.core.repackaged.springframework.transaction.interceptor.RollbackRuleAttribute;
import com.bea.core.repackaged.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import com.bea.core.repackaged.springframework.transaction.interceptor.TransactionAttribute;
import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

public class JtaTransactionAnnotationParser implements TransactionAnnotationParser, Serializable {
   @Nullable
   public TransactionAttribute parseTransactionAnnotation(AnnotatedElement element) {
      AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(element, javax.transaction.Transactional.class);
      return attributes != null ? this.parseTransactionAnnotation(attributes) : null;
   }

   public TransactionAttribute parseTransactionAnnotation(javax.transaction.Transactional ann) {
      return this.parseTransactionAnnotation(AnnotationUtils.getAnnotationAttributes(ann, false, false));
   }

   protected TransactionAttribute parseTransactionAnnotation(AnnotationAttributes attributes) {
      RuleBasedTransactionAttribute rbta = new RuleBasedTransactionAttribute();
      rbta.setPropagationBehaviorName("PROPAGATION_" + attributes.getEnum("value").toString());
      List rollbackRules = new ArrayList();
      Class[] var4 = attributes.getClassArray("rollbackOn");
      int var5 = var4.length;

      int var6;
      Class rbRule;
      for(var6 = 0; var6 < var5; ++var6) {
         rbRule = var4[var6];
         rollbackRules.add(new RollbackRuleAttribute(rbRule));
      }

      var4 = attributes.getClassArray("dontRollbackOn");
      var5 = var4.length;

      for(var6 = 0; var6 < var5; ++var6) {
         rbRule = var4[var6];
         rollbackRules.add(new NoRollbackRuleAttribute(rbRule));
      }

      rbta.setRollbackRules(rollbackRules);
      return rbta;
   }

   public boolean equals(Object other) {
      return this == other || other instanceof JtaTransactionAnnotationParser;
   }

   public int hashCode() {
      return JtaTransactionAnnotationParser.class.hashCode();
   }
}
