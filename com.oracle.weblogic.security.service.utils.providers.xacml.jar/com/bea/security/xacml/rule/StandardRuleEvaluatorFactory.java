package com.bea.security.xacml.rule;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.BooleanAttribute;
import com.bea.common.security.xacml.policy.Condition;
import com.bea.common.security.xacml.policy.Rule;
import com.bea.common.security.xacml.policy.Target;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.RuleDecision;
import com.bea.security.xacml.RuleEvaluator;
import com.bea.security.xacml.TargetMatchEvaluator;
import com.bea.security.xacml.target.NoMatchTargetMatchEvaluator;
import com.bea.security.xacml.target.NoOpTargetMatchEvaluator;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class StandardRuleEvaluatorFactory implements RuleEvaluatorFactory {
   public RuleEvaluator createRule(Rule rule, Collection designatorMatches, Map variables, Configuration config, Iterator otherFactories) throws EvaluationPlanException, URISyntaxException {
      Condition cond = rule.getCondition();
      Target target = rule.getTarget();
      final boolean hasPermitEffect = rule.isEffectPermit();
      final RuleDecision decision = hasPermitEffect ? RuleDecision.getPermitDecision() : RuleDecision.getDenyDecision();
      final String ruleId = rule.getRuleId();
      TargetMatchEvaluator tmx = target != null ? config.getTargetEvaluatorRegistry().getEvaluator(target, designatorMatches, config) : null;
      if (tmx == NoMatchTargetMatchEvaluator.getInstance()) {
         return NoMatchRuleEvaluator.getInstance(hasPermitEffect);
      } else {
         final TargetMatchEvaluator tm = tmx != NoOpTargetMatchEvaluator.getInstance() ? tmx : null;
         final AttributeEvaluator cae = cond != null ? config.getExpressionRegistry().parse(cond.getExpression(), variables, config) : null;
         if (cae != null && !Type.BOOLEAN.equals(cae.getType())) {
            throw new EvaluationPlanException("Condition must identify Boolean expression");
         } else if (tm != null) {
            return cae != null ? new RuleEvaluator() {
               public RuleDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                  if (context.isDebugEnabled()) {
                     try {
                        boolean targetEval = tm.evaluate(context);
                        if (targetEval) {
                           if (((BooleanAttribute)cae.evaluate(context)).getBooleanValue()) {
                              context.debug(ruleId + " evaluates to " + (hasPermitEffect ? "Permit" : "Deny"));
                              return decision;
                           } else {
                              context.debug(ruleId + " evaluates to NotApplicable because of Condition");
                              return RuleDecision.getNotApplicableDecision();
                           }
                        } else {
                           context.debug(ruleId + " evaluates to NotApplicable because of Target");
                           return RuleDecision.getNotApplicableDecision();
                        }
                     } catch (IndeterminateEvaluationException var4) {
                        String im = var4.getMessage();
                        context.debug(ruleId + " evaluates to Indeterminate with message: " + (im != null ? im : var4.getClass().getName()));
                        throw var4;
                     }
                  } else {
                     return tm.evaluate(context) && ((BooleanAttribute)cae.evaluate(context)).getBooleanValue() ? decision : RuleDecision.getNotApplicableDecision();
                  }
               }

               public boolean hasPermitEffect() {
                  return hasPermitEffect;
               }
            } : new RuleEvaluator() {
               public RuleDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                  if (context.isDebugEnabled()) {
                     try {
                        boolean targetEval = tm.evaluate(context);
                        if (targetEval) {
                           context.debug(ruleId + " evaluates to " + (hasPermitEffect ? "Permit" : "Deny"));
                           return decision;
                        } else {
                           context.debug(ruleId + " evaluates to NotApplicable because of Target");
                           return RuleDecision.getNotApplicableDecision();
                        }
                     } catch (IndeterminateEvaluationException var4) {
                        String im = var4.getMessage();
                        context.debug(ruleId + " evaluates to Indeterminate with message: " + (im != null ? im : var4.getClass().getName()));
                        throw var4;
                     }
                  } else {
                     return tm.evaluate(context) ? decision : RuleDecision.getNotApplicableDecision();
                  }
               }

               public boolean hasPermitEffect() {
                  return hasPermitEffect;
               }
            };
         } else {
            return (RuleEvaluator)(cae != null ? new RuleEvaluator() {
               public RuleDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                  if (context.isDebugEnabled()) {
                     try {
                        if (((BooleanAttribute)cae.evaluate(context)).getBooleanValue()) {
                           context.debug(ruleId + " evaluates to " + (hasPermitEffect ? "Permit" : "Deny"));
                           return decision;
                        } else {
                           context.debug(ruleId + " evaluates to NotApplicable because of Condition");
                           return RuleDecision.getNotApplicableDecision();
                        }
                     } catch (IndeterminateEvaluationException var4) {
                        String im = var4.getMessage();
                        context.debug(ruleId + " evaluates to Indeterminate with message: " + (im != null ? im : var4.getClass().getName()));
                        throw var4;
                     }
                  } else {
                     return ((BooleanAttribute)cae.evaluate(context)).getBooleanValue() ? decision : RuleDecision.getNotApplicableDecision();
                  }
               }

               public boolean hasPermitEffect() {
                  return hasPermitEffect;
               }
            } : MatchRuleEvaluator.getInstance(hasPermitEffect));
         }
      }
   }
}
