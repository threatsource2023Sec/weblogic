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

public class SpringTransactionAnnotationParser implements TransactionAnnotationParser, Serializable {
   @Nullable
   public TransactionAttribute parseTransactionAnnotation(AnnotatedElement element) {
      AnnotationAttributes attributes = AnnotatedElementUtils.findMergedAnnotationAttributes(element, Transactional.class, false, false);
      return attributes != null ? this.parseTransactionAnnotation(attributes) : null;
   }

   public TransactionAttribute parseTransactionAnnotation(Transactional ann) {
      return this.parseTransactionAnnotation(AnnotationUtils.getAnnotationAttributes(ann, false, false));
   }

   protected TransactionAttribute parseTransactionAnnotation(AnnotationAttributes attributes) {
      RuleBasedTransactionAttribute rbta = new RuleBasedTransactionAttribute();
      Propagation propagation = (Propagation)attributes.getEnum("propagation");
      rbta.setPropagationBehavior(propagation.value());
      Isolation isolation = (Isolation)attributes.getEnum("isolation");
      rbta.setIsolationLevel(isolation.value());
      rbta.setTimeout(attributes.getNumber("timeout").intValue());
      rbta.setReadOnly(attributes.getBoolean("readOnly"));
      rbta.setQualifier(attributes.getString("value"));
      List rollbackRules = new ArrayList();
      Class[] var6 = attributes.getClassArray("rollbackFor");
      int var7 = var6.length;

      int var8;
      Class rbRule;
      for(var8 = 0; var8 < var7; ++var8) {
         rbRule = var6[var8];
         rollbackRules.add(new RollbackRuleAttribute(rbRule));
      }

      String[] var10 = attributes.getStringArray("rollbackForClassName");
      var7 = var10.length;

      String rbRule;
      for(var8 = 0; var8 < var7; ++var8) {
         rbRule = var10[var8];
         rollbackRules.add(new RollbackRuleAttribute(rbRule));
      }

      var6 = attributes.getClassArray("noRollbackFor");
      var7 = var6.length;

      for(var8 = 0; var8 < var7; ++var8) {
         rbRule = var6[var8];
         rollbackRules.add(new NoRollbackRuleAttribute(rbRule));
      }

      var10 = attributes.getStringArray("noRollbackForClassName");
      var7 = var10.length;

      for(var8 = 0; var8 < var7; ++var8) {
         rbRule = var10[var8];
         rollbackRules.add(new NoRollbackRuleAttribute(rbRule));
      }

      rbta.setRollbackRules(rollbackRules);
      return rbta;
   }

   public boolean equals(Object other) {
      return this == other || other instanceof SpringTransactionAnnotationParser;
   }

   public int hashCode() {
      return SpringTransactionAnnotationParser.class.hashCode();
   }
}
