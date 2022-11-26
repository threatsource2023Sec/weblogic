package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RuleBasedTransactionAttribute extends DefaultTransactionAttribute implements Serializable {
   public static final String PREFIX_ROLLBACK_RULE = "-";
   public static final String PREFIX_COMMIT_RULE = "+";
   private static final Log logger = LogFactory.getLog(RuleBasedTransactionAttribute.class);
   @Nullable
   private List rollbackRules;

   public RuleBasedTransactionAttribute() {
   }

   public RuleBasedTransactionAttribute(RuleBasedTransactionAttribute other) {
      super(other);
      this.rollbackRules = other.rollbackRules != null ? new ArrayList(other.rollbackRules) : null;
   }

   public RuleBasedTransactionAttribute(int propagationBehavior, List rollbackRules) {
      super(propagationBehavior);
      this.rollbackRules = rollbackRules;
   }

   public void setRollbackRules(List rollbackRules) {
      this.rollbackRules = rollbackRules;
   }

   public List getRollbackRules() {
      if (this.rollbackRules == null) {
         this.rollbackRules = new LinkedList();
      }

      return this.rollbackRules;
   }

   public boolean rollbackOn(Throwable ex) {
      if (logger.isTraceEnabled()) {
         logger.trace("Applying rules to determine whether transaction should rollback on " + ex);
      }

      RollbackRuleAttribute winner = null;
      int deepest = Integer.MAX_VALUE;
      if (this.rollbackRules != null) {
         Iterator var4 = this.rollbackRules.iterator();

         while(var4.hasNext()) {
            RollbackRuleAttribute rule = (RollbackRuleAttribute)var4.next();
            int depth = rule.getDepth(ex);
            if (depth >= 0 && depth < deepest) {
               deepest = depth;
               winner = rule;
            }
         }
      }

      if (logger.isTraceEnabled()) {
         logger.trace("Winning rollback rule is: " + winner);
      }

      if (winner == null) {
         logger.trace("No relevant rollback rule found: applying default rules");
         return super.rollbackOn(ex);
      } else {
         return !(winner instanceof NoRollbackRuleAttribute);
      }
   }

   public String toString() {
      StringBuilder result = this.getAttributeDescription();
      if (this.rollbackRules != null) {
         Iterator var2 = this.rollbackRules.iterator();

         while(var2.hasNext()) {
            RollbackRuleAttribute rule = (RollbackRuleAttribute)var2.next();
            String sign = rule instanceof NoRollbackRuleAttribute ? "+" : "-";
            result.append(',').append(sign).append(rule.getExceptionName());
         }
      }

      return result.toString();
   }
}
