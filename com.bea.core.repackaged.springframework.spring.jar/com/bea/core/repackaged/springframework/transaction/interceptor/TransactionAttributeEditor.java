package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyEditorSupport;

public class TransactionAttributeEditor extends PropertyEditorSupport {
   public void setAsText(String text) throws IllegalArgumentException {
      if (StringUtils.hasLength(text)) {
         String[] tokens = StringUtils.commaDelimitedListToStringArray(text);
         RuleBasedTransactionAttribute attr = new RuleBasedTransactionAttribute();
         String[] var4 = tokens;
         int var5 = tokens.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String token = var4[var6];
            String trimmedToken = StringUtils.trimWhitespace(token.trim());
            if (StringUtils.containsWhitespace(trimmedToken)) {
               throw new IllegalArgumentException("Transaction attribute token contains illegal whitespace: [" + trimmedToken + "]");
            }

            if (trimmedToken.startsWith("PROPAGATION_")) {
               attr.setPropagationBehaviorName(trimmedToken);
            } else if (trimmedToken.startsWith("ISOLATION_")) {
               attr.setIsolationLevelName(trimmedToken);
            } else if (trimmedToken.startsWith("timeout_")) {
               String value = trimmedToken.substring("timeout_".length());
               attr.setTimeout(Integer.parseInt(value));
            } else if (trimmedToken.equals("readOnly")) {
               attr.setReadOnly(true);
            } else if (trimmedToken.startsWith("+")) {
               attr.getRollbackRules().add(new NoRollbackRuleAttribute(trimmedToken.substring(1)));
            } else {
               if (!trimmedToken.startsWith("-")) {
                  throw new IllegalArgumentException("Invalid transaction attribute token: [" + trimmedToken + "]");
               }

               attr.getRollbackRules().add(new RollbackRuleAttribute(trimmedToken.substring(1)));
            }
         }

         this.setValue(attr);
      } else {
         this.setValue((Object)null);
      }

   }
}
