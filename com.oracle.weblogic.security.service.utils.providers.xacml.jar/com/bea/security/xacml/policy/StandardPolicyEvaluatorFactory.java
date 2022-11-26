package com.bea.security.xacml.policy;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeValue;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.policy.Obligation;
import com.bea.common.security.xacml.policy.Obligations;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.common.security.xacml.policy.Target;
import com.bea.common.security.xacml.policy.VariableDefinition;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.PolicyDecision;
import com.bea.security.xacml.PolicyEvaluator;
import com.bea.security.xacml.RuleCombinerEvaluator;
import com.bea.security.xacml.TargetMatchEvaluator;
import com.bea.security.xacml.target.NoOpTargetMatchEvaluator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StandardPolicyEvaluatorFactory implements PolicyEvaluatorFactory {
   public PolicyEvaluator createPolicy(Policy policy, Collection designatorMatches, Configuration config, Iterator otherFactories) throws URISyntaxException, EvaluationPlanException {
      if (policy == null) {
         return null;
      } else {
         List cparams = policy.getCombinerParameters();
         List rcparams = policy.getRuleCombinerParameters();
         Collection vdefs = policy.getVariableDefinitions();
         Target target = policy.getTarget();
         List rules = policy.getRules();
         Obligations obs = policy.getObligations();
         URI ruleCombiningAlgId = policy.getCombiningAlgId();
         final URI policyId = policy.getId();
         final String policyVersion = policy.getVersion();
         final Map variables = vdefs == null ? null : new HashMap();
         if (vdefs != null) {
            Iterator var15 = vdefs.iterator();

            while(var15.hasNext()) {
               VariableDefinition vd = (VariableDefinition)var15.next();
               variables.put(vd.getId(), config.getExpressionRegistry().parse(vd.getExpression(), variables, config));
            }
         }

         TargetMatchEvaluator tmx = target != null ? config.getTargetEvaluatorRegistry().getEvaluator(target, designatorMatches, config) : null;
         final TargetMatchEvaluator tmEval = tmx != NoOpTargetMatchEvaluator.getInstance() ? tmx : null;
         final RuleCombinerEvaluator rcEval = config.getRuleCombinerEvaluatorRegistry().getEvaluator(ruleCombiningAlgId, rules, cparams, rcparams, designatorMatches, variables, config);
         List permitObligations = new ArrayList();
         List denyObligations = new ArrayList();
         if (obs != null) {
            List os = obs.getObligations();
            if (os != null) {
               Iterator it = os.iterator();

               while(it.hasNext()) {
                  Obligation o = (Obligation)it.next();
                  if (o.isFulfillOnPermit()) {
                     permitObligations.add(o);
                  } else {
                     denyObligations.add(o);
                  }
               }
            }
         }

         final PolicyDecision permitDecision = permitObligations.isEmpty() ? PolicyDecision.getPermitDecision() : PolicyDecision.getPermitDecision(permitObligations);
         final PolicyDecision denyDecision = denyObligations.isEmpty() ? PolicyDecision.getDenyDecision() : PolicyDecision.getDenyDecision(denyObligations);
         return tmEval != null ? new PolicyEvaluator() {
            public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
               if (!context.isDebugEnabled()) {
                  if (tmEval.evaluate(context)) {
                     context.setVariableContext(StandardPolicyEvaluatorFactory.this.generateVariableContext(context, variables));
                     switch (rcEval.evaluate(context).getDecisionValue()) {
                        case 0:
                           return permitDecision;
                        case 1:
                           return denyDecision;
                        default:
                           return PolicyDecision.getNotApplicableDecision();
                     }
                  } else {
                     return PolicyDecision.getTargetNotApplicableDecision();
                  }
               } else {
                  String resultx;
                  try {
                     if (tmEval.evaluate(context)) {
                        context.setVariableContext(StandardPolicyEvaluatorFactory.this.generateVariableContext(context, variables));
                        StringBuffer msg = new StringBuffer();
                        msg.append(policyId);
                        msg.append(", ");
                        msg.append(policyVersion);
                        resultx = null;
                        PolicyDecision result;
                        switch (rcEval.evaluate(context).getDecisionValue()) {
                           case 0:
                              msg.append(" evaluates to Permit");
                              result = permitDecision;
                              break;
                           case 1:
                              msg.append(" evaluates to Deny");
                              result = denyDecision;
                              break;
                           default:
                              msg.append(" evaluates to NotApplicable");
                              context.debug(msg.toString());
                              return PolicyDecision.getNotApplicableDecision();
                        }

                        if (result != null) {
                           if (result.hasObligations()) {
                              msg.append(" with obligations: ");
                              Iterator var4 = permitDecision.getObligations().iterator();

                              while(var4.hasNext()) {
                                 Obligation o = (Obligation)var4.next();
                                 msg.append('\n');
                                 msg.append(o);
                              }
                           }

                           context.debug(msg.toString());
                           return result;
                        }
                     }
                  } catch (IndeterminateEvaluationException var6) {
                     resultx = var6.getMessage();
                     context.debug(policyId + ", " + policyVersion + " evaluates to Indeterminate with message: " + (resultx != null ? resultx : var6.getClass().getName()));
                     throw var6;
                  }

                  context.debug(policyId + ", " + policyVersion + " evaluates to NotApplicable because of Target");
                  return PolicyDecision.getTargetNotApplicableDecision();
               }
            }
         } : new PolicyEvaluator() {
            public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
               if (context.isDebugEnabled()) {
                  try {
                     context.setVariableContext(StandardPolicyEvaluatorFactory.this.generateVariableContext(context, variables));
                     StringBuffer msg = new StringBuffer();
                     msg.append(policyId);
                     msg.append(", ");
                     msg.append(policyVersion);
                     PolicyDecision result = null;
                     switch (rcEval.evaluate(context).getDecisionValue()) {
                        case 0:
                           msg.append(" evaluates to Permit");
                           result = permitDecision;
                           break;
                        case 1:
                           msg.append(" evaluates to Deny");
                           result = denyDecision;
                     }

                     if (result != null) {
                        if (result.hasObligations()) {
                           msg.append(" with obligations: ");
                           Iterator var4 = result.getObligations().iterator();

                           while(var4.hasNext()) {
                              Obligation o = (Obligation)var4.next();
                              msg.append('\n');
                              msg.append(o);
                           }
                        }

                        context.debug(msg.toString());
                        return result;
                     }
                  } catch (IndeterminateEvaluationException var6) {
                     String im = var6.getMessage();
                     context.debug(policyId + ", " + policyVersion + " evaluates to Indeterminate with message: " + (im != null ? im : var6.getClass().getName()));
                     throw var6;
                  }

                  context.debug(policyId + ", " + policyVersion + " evaluates to NotApplicable");
                  return PolicyDecision.getNotApplicableDecision();
               } else {
                  context.setVariableContext(StandardPolicyEvaluatorFactory.this.generateVariableContext(context, variables));
                  switch (rcEval.evaluate(context).getDecisionValue()) {
                     case 0:
                        return permitDecision;
                     case 1:
                        return denyDecision;
                     default:
                        return PolicyDecision.getNotApplicableDecision();
                  }
               }
            }
         };
      }
   }

   private VariableContext generateVariableContext(final EvaluationCtx context, final Map variables) {
      return variables == null ? null : new VariableContext() {
         private Map vals = new HashMap();

         public AttributeValue getVariable(String identifier) throws IndeterminateEvaluationException {
            Bag b = (Bag)this.vals.get(identifier);
            if (b == null) {
               AttributeEvaluator ae = (AttributeEvaluator)variables.get(identifier);
               if (ae == null) {
                  throw new IndeterminateEvaluationException("No corresponding variable defintion for: " + identifier);
               } else {
                  AttributeValue av = ae.evaluate(context);
                  this.vals.put(identifier, av);
                  return av;
               }
            } else if (b instanceof AttributeValue) {
               return (AttributeValue)b;
            } else if (b.size() != 1) {
               throw new IndeterminateEvaluationException("Bags must be size 1 to be treated as scalars");
            } else {
               return (AttributeValue)b.iterator().next();
            }
         }

         public Bag getVariableAsBag(String identifier) throws IndeterminateEvaluationException {
            Bag b = (Bag)this.vals.get(identifier);
            if (b == null) {
               AttributeEvaluator ae = (AttributeEvaluator)variables.get(identifier);
               if (ae == null) {
                  throw new IndeterminateEvaluationException("No corresponding variable defintion for: " + identifier);
               }

               b = ae.evaluateToBag(context);
               this.vals.put(identifier, b);
            }

            return b;
         }
      };
   }

   public PolicyEvaluator createPolicySet(PolicySet policySet, Collection designatorMatches, Configuration config, Iterator otherFactories) throws EvaluationPlanException, URISyntaxException {
      List cparams = policySet.getCombinerParameters();
      List pcparams = policySet.getPolicyCombinerParameters();
      List psparams = policySet.getPolicySetCombinerParameters();
      Target target = policySet.getTarget();
      List policiesPolicySetsAndReferences = policySet.getPoliciesPolicySetsAndReferences();
      Obligations obs = policySet.getObligations();
      URI policyCombiningAlgId = policySet.getCombiningAlgId();
      final URI policySetId = policySet.getId();
      final String policySetVersion = policySet.getVersion();
      TargetMatchEvaluator tmx = target != null ? config.getTargetEvaluatorRegistry().getEvaluator(target, designatorMatches, config) : null;
      final TargetMatchEvaluator tmEval = tmx != NoOpTargetMatchEvaluator.getInstance() ? tmx : null;
      final PolicyEvaluator pcEval = config.getPolicyCombinerEvaluatorRegistry().getEvaluator(policyCombiningAlgId, policiesPolicySetsAndReferences, cparams, pcparams, psparams, designatorMatches, config);
      final List permitObligations = new ArrayList();
      final List denyObligations = new ArrayList();
      if (obs != null) {
         List os = obs.getObligations();
         if (os != null) {
            Iterator it = os.iterator();

            while(it.hasNext()) {
               Obligation o = (Obligation)it.next();
               if (o.isFulfillOnPermit()) {
                  permitObligations.add(o);
               } else {
                  denyObligations.add(o);
               }
            }
         }
      }

      final PolicyDecision permitDecision = permitObligations.isEmpty() ? PolicyDecision.getPermitDecision() : PolicyDecision.getPermitDecision(permitObligations);
      final PolicyDecision denyDecision = denyObligations.isEmpty() ? PolicyDecision.getDenyDecision() : PolicyDecision.getDenyDecision(denyObligations);
      if (tmEval != null) {
         if (permitObligations.isEmpty()) {
            return denyObligations.isEmpty() ? new PolicyEvaluator() {
               public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                  if (!context.isDebugEnabled()) {
                     return tmEval.evaluate(context) ? pcEval.evaluate(context) : PolicyDecision.getTargetNotApplicableDecision();
                  } else {
                     try {
                        if (tmEval.evaluate(context)) {
                           PolicyDecision result = pcEval.evaluate(context);
                           StringBuffer msg = new StringBuffer();
                           msg.append(policySetId);
                           msg.append(", ");
                           msg.append(policySetVersion);
                           msg.append(" evaluates to ");
                           switch (result.getDecisionValue()) {
                              case 0:
                                 msg.append("Permit");
                                 break;
                              case 1:
                                 msg.append("Deny");
                                 break;
                              default:
                                 msg.append("NotApplicable");
                           }

                           context.debug(msg.toString());
                           return result;
                        }
                     } catch (IndeterminateEvaluationException var4) {
                        String im = var4.getMessage();
                        context.debug(policySetId + ", " + policySetVersion + " evaluates to Indeterminate with message: " + (im != null ? im : var4.getClass().getName()));
                        throw var4;
                     }

                     context.debug(policySetId + ", " + policySetVersion + " evaluates to NotApplicable because of Target");
                     return PolicyDecision.getTargetNotApplicableDecision();
                  }
               }
            } : new PolicyEvaluator() {
               public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                  PolicyDecision pd;
                  if (!context.isDebugEnabled()) {
                     if (tmEval.evaluate(context)) {
                        pd = pcEval.evaluate(context);
                        switch (pd.getDecisionValue()) {
                           case 0:
                              return pd;
                           case 1:
                              List obsx = pd.getObligations();
                              return obsx == null ? denyDecision : PolicyDecision.getDenyDecision(StandardPolicyEvaluatorFactory.this.combineObligations(denyObligations, obsx));
                           default:
                              return pd;
                        }
                     } else {
                        return PolicyDecision.getTargetNotApplicableDecision();
                     }
                  } else {
                     try {
                        if (tmEval.evaluate(context)) {
                           pd = pcEval.evaluate(context);
                           StringBuffer msg = new StringBuffer();
                           msg.append(policySetId);
                           msg.append(", ");
                           msg.append(policySetVersion);
                           msg.append(" evaluates to ");
                           switch (pd.getDecisionValue()) {
                              case 0:
                                 msg.append("Permit");
                                 context.debug(msg.toString());
                                 return pd;
                              case 1:
                                 msg.append("Deny");
                                 List obs = pd.getObligations();
                                 PolicyDecision result = obs == null ? denyDecision : PolicyDecision.getDenyDecision(StandardPolicyEvaluatorFactory.this.combineObligations(denyObligations, obs));
                                 msg.append(" with obligations: ");
                                 Iterator var6 = result.getObligations().iterator();

                                 while(var6.hasNext()) {
                                    Obligation o = (Obligation)var6.next();
                                    msg.append('\n');
                                    msg.append(o);
                                 }

                                 context.debug(msg.toString());
                                 return result;
                              default:
                                 msg.append("NotApplicable");
                                 context.debug(msg.toString());
                                 return PolicyDecision.getNotApplicableDecision();
                           }
                        }
                     } catch (IndeterminateEvaluationException var8) {
                        String im = var8.getMessage();
                        context.debug(policySetId + ", " + policySetVersion + " evaluates to Indeterminate with message: " + (im != null ? im : var8.getClass().getName()));
                        throw var8;
                     }

                     context.debug(policySetId + ", " + policySetVersion + " evaluates to NotApplicable because of Target");
                     return PolicyDecision.getTargetNotApplicableDecision();
                  }
               }
            };
         } else {
            return denyObligations.isEmpty() ? new PolicyEvaluator() {
               public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                  PolicyDecision pd;
                  if (!context.isDebugEnabled()) {
                     if (tmEval.evaluate(context)) {
                        pd = pcEval.evaluate(context);
                        switch (pd.getDecisionValue()) {
                           case 0:
                              List obsx = pd.getObligations();
                              return obsx == null ? permitDecision : PolicyDecision.getPermitDecision(StandardPolicyEvaluatorFactory.this.combineObligations(permitObligations, obsx));
                           case 1:
                              return pd;
                        }
                     }

                     return PolicyDecision.getTargetNotApplicableDecision();
                  } else {
                     try {
                        if (tmEval.evaluate(context)) {
                           pd = pcEval.evaluate(context);
                           StringBuffer msg = new StringBuffer();
                           msg.append(policySetId);
                           msg.append(", ");
                           msg.append(policySetVersion);
                           msg.append(" evaluates to ");
                           switch (pd.getDecisionValue()) {
                              case 0:
                                 msg.append("Permit");
                                 List obs = pd.getObligations();
                                 PolicyDecision result = obs == null ? permitDecision : PolicyDecision.getPermitDecision(StandardPolicyEvaluatorFactory.this.combineObligations(permitObligations, obs));
                                 msg.append(" with obligations: ");
                                 Iterator var6 = result.getObligations().iterator();

                                 while(var6.hasNext()) {
                                    Obligation o = (Obligation)var6.next();
                                    msg.append('\n');
                                    msg.append(o);
                                 }

                                 context.debug(msg.toString());
                                 return result;
                              case 1:
                                 msg.append("Deny");
                                 context.debug(msg.toString());
                                 return pd;
                              default:
                                 msg.append("NotApplicable");
                                 context.debug(msg.toString());
                                 return PolicyDecision.getNotApplicableDecision();
                           }
                        }
                     } catch (IndeterminateEvaluationException var8) {
                        String im = var8.getMessage();
                        context.debug(policySetId + ", " + policySetVersion + " evaluates to Indeterminate with message: " + (im != null ? im : var8.getClass().getName()));
                        throw var8;
                     }

                     context.debug(policySetId + ", " + policySetVersion + " evaluates to NotApplicable because of Target");
                     return PolicyDecision.getTargetNotApplicableDecision();
                  }
               }
            } : new PolicyEvaluator() {
               public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                  PolicyDecision pd;
                  List obs;
                  if (!context.isDebugEnabled()) {
                     if (tmEval.evaluate(context)) {
                        pd = pcEval.evaluate(context);
                        obs = pd.getObligations();
                        switch (pd.getDecisionValue()) {
                           case 0:
                              return obs == null ? permitDecision : PolicyDecision.getPermitDecision(StandardPolicyEvaluatorFactory.this.combineObligations(permitObligations, obs));
                           case 1:
                              return obs == null ? denyDecision : PolicyDecision.getDenyDecision(StandardPolicyEvaluatorFactory.this.combineObligations(denyObligations, obs));
                           default:
                              return pd;
                        }
                     } else {
                        return PolicyDecision.getTargetNotApplicableDecision();
                     }
                  } else {
                     try {
                        if (tmEval.evaluate(context)) {
                           pd = pcEval.evaluate(context);
                           obs = pd.getObligations();
                           StringBuffer msg = new StringBuffer();
                           msg.append(policySetId);
                           msg.append(", ");
                           msg.append(policySetVersion);
                           msg.append(" evaluates to ");
                           switch (pd.getDecisionValue()) {
                              case 0:
                                 msg.append("Permit");
                                 PolicyDecision result = obs == null ? permitDecision : PolicyDecision.getPermitDecision(StandardPolicyEvaluatorFactory.this.combineObligations(permitObligations, obs));
                                 msg.append(" with obligations: ");
                                 Iterator var11 = result.getObligations().iterator();

                                 while(var11.hasNext()) {
                                    Obligation o = (Obligation)var11.next();
                                    msg.append('\n');
                                    msg.append(o);
                                 }

                                 context.debug(msg.toString());
                                 return result;
                              case 1:
                                 msg.append("Deny");
                                 PolicyDecision result2 = obs == null ? denyDecision : PolicyDecision.getDenyDecision(StandardPolicyEvaluatorFactory.this.combineObligations(denyObligations, obs));
                                 msg.append(" with obligations: ");
                                 Iterator var7 = result2.getObligations().iterator();

                                 while(var7.hasNext()) {
                                    Obligation ox = (Obligation)var7.next();
                                    msg.append('\n');
                                    msg.append(ox);
                                 }

                                 context.debug(msg.toString());
                                 return result2;
                              default:
                                 msg.append("NotApplicable");
                                 context.debug(msg.toString());
                                 return PolicyDecision.getNotApplicableDecision();
                           }
                        }
                     } catch (IndeterminateEvaluationException var9) {
                        String im = var9.getMessage();
                        context.debug(policySetId + ", " + policySetVersion + " evaluates to Indeterminate with message: " + (im != null ? im : var9.getClass().getName()));
                        throw var9;
                     }

                     context.debug(policySetId + ", " + policySetVersion + " evaluates to NotApplicable because of Target");
                     return PolicyDecision.getTargetNotApplicableDecision();
                  }
               }
            };
         }
      } else if (permitObligations.isEmpty()) {
         return denyObligations.isEmpty() ? new PolicyEvaluator() {
            public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
               if (context.isDebugEnabled()) {
                  try {
                     PolicyDecision result = pcEval.evaluate(context);
                     StringBuffer msg = new StringBuffer();
                     msg.append(policySetId);
                     msg.append(", ");
                     msg.append(policySetVersion);
                     msg.append(" evaluates to ");
                     switch (result.getDecisionValue()) {
                        case 0:
                           msg.append("Permit");
                           break;
                        case 1:
                           msg.append("Deny");
                           break;
                        default:
                           msg.append("NotApplicable");
                     }

                     context.debug(msg.toString());
                     return result;
                  } catch (IndeterminateEvaluationException var4) {
                     String im = var4.getMessage();
                     context.debug(policySetId + ", " + policySetVersion + " evaluates to Indeterminate with message: " + (im != null ? im : var4.getClass().getName()));
                     throw var4;
                  }
               } else {
                  return pcEval.evaluate(context);
               }
            }
         } : new PolicyEvaluator() {
            public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
               PolicyDecision pd;
               if (!context.isDebugEnabled()) {
                  pd = pcEval.evaluate(context);
                  switch (pd.getDecisionValue()) {
                     case 0:
                        return pd;
                     case 1:
                        List obs = pd.getObligations();
                        return obs == null ? denyDecision : PolicyDecision.getDenyDecision(StandardPolicyEvaluatorFactory.this.combineObligations(denyObligations, obs));
                     default:
                        return pd;
                  }
               } else {
                  try {
                     StringBuffer msg;
                     pd = pcEval.evaluate(context);
                     msg = new StringBuffer();
                     msg.append(policySetId);
                     msg.append(", ");
                     msg.append(policySetVersion);
                     msg.append(" evaluates to ");
                     label45:
                     switch (pd.getDecisionValue()) {
                        case 0:
                           msg.append("Permit");
                           break;
                        case 1:
                           msg.append("Deny");
                           List obsx = pd.getObligations();
                           pd = obsx == null ? denyDecision : PolicyDecision.getDenyDecision(StandardPolicyEvaluatorFactory.this.combineObligations(denyObligations, obsx));
                           msg.append(" with obligations: ");
                           Iterator var5 = pd.getObligations().iterator();

                           while(true) {
                              if (!var5.hasNext()) {
                                 break label45;
                              }

                              Obligation o = (Obligation)var5.next();
                              msg.append('\n');
                              msg.append(o);
                           }
                        default:
                           msg.append("NotApplicable");
                     }

                     context.debug(msg.toString());
                     return pd;
                  } catch (IndeterminateEvaluationException var7) {
                     String im = var7.getMessage();
                     context.debug(policySetId + ", " + policySetVersion + " evaluates to Indeterminate with message: " + (im != null ? im : var7.getClass().getName()));
                     throw var7;
                  }
               }
            }
         };
      } else {
         return denyObligations.isEmpty() ? new PolicyEvaluator() {
            public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
               PolicyDecision pd;
               if (!context.isDebugEnabled()) {
                  pd = pcEval.evaluate(context);
                  switch (pd.getDecisionValue()) {
                     case 0:
                        List obs = pd.getObligations();
                        return obs == null ? permitDecision : PolicyDecision.getPermitDecision(StandardPolicyEvaluatorFactory.this.combineObligations(permitObligations, obs));
                     case 1:
                        return pd;
                     default:
                        return pd;
                  }
               } else {
                  try {
                     StringBuffer msg;
                     pd = pcEval.evaluate(context);
                     msg = new StringBuffer();
                     msg.append(policySetId);
                     msg.append(", ");
                     msg.append(policySetVersion);
                     msg.append(" evaluates to ");
                     label45:
                     switch (pd.getDecisionValue()) {
                        case 0:
                           msg.append("Permit");
                           List obsx = pd.getObligations();
                           pd = obsx == null ? permitDecision : PolicyDecision.getPermitDecision(StandardPolicyEvaluatorFactory.this.combineObligations(permitObligations, obsx));
                           msg.append(" with obligations: ");
                           Iterator var5 = pd.getObligations().iterator();

                           while(true) {
                              if (!var5.hasNext()) {
                                 break label45;
                              }

                              Obligation o = (Obligation)var5.next();
                              msg.append('\n');
                              msg.append(o);
                           }
                        case 1:
                           msg.append("Deny");
                           break;
                        default:
                           msg.append("NotApplicable");
                     }

                     context.debug(msg.toString());
                     return pd;
                  } catch (IndeterminateEvaluationException var7) {
                     String im = var7.getMessage();
                     context.debug(policySetId + ", " + policySetVersion + " evaluates to Indeterminate with message: " + (im != null ? im : var7.getClass().getName()));
                     throw var7;
                  }
               }
            }
         } : new PolicyEvaluator() {
            public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
               PolicyDecision pd;
               List obs;
               if (!context.isDebugEnabled()) {
                  pd = pcEval.evaluate(context);
                  obs = pd.getObligations();
                  switch (pd.getDecisionValue()) {
                     case 0:
                        return obs == null ? permitDecision : PolicyDecision.getPermitDecision(StandardPolicyEvaluatorFactory.this.combineObligations(permitObligations, obs));
                     case 1:
                        return obs == null ? denyDecision : PolicyDecision.getDenyDecision(StandardPolicyEvaluatorFactory.this.combineObligations(denyObligations, obs));
                     default:
                        return pd;
                  }
               } else {
                  try {
                     StringBuffer msg;
                     pd = pcEval.evaluate(context);
                     obs = pd.getObligations();
                     msg = new StringBuffer();
                     msg.append(policySetId);
                     msg.append(", ");
                     msg.append(policySetVersion);
                     msg.append(" evaluates to ");
                     Iterator var5;
                     Obligation o;
                     label63:
                     switch (pd.getDecisionValue()) {
                        case 0:
                           msg.append("Permit");
                           pd = obs == null ? permitDecision : PolicyDecision.getPermitDecision(StandardPolicyEvaluatorFactory.this.combineObligations(permitObligations, obs));
                           msg.append(" with obligations: ");
                           var5 = pd.getObligations().iterator();

                           while(true) {
                              if (!var5.hasNext()) {
                                 break label63;
                              }

                              o = (Obligation)var5.next();
                              msg.append('\n');
                              msg.append(o);
                           }
                        case 1:
                           msg.append("Deny");
                           pd = obs == null ? denyDecision : PolicyDecision.getDenyDecision(StandardPolicyEvaluatorFactory.this.combineObligations(denyObligations, obs));
                           msg.append(" with obligations: ");
                           var5 = pd.getObligations().iterator();

                           while(true) {
                              if (!var5.hasNext()) {
                                 break label63;
                              }

                              o = (Obligation)var5.next();
                              msg.append('\n');
                              msg.append(o);
                           }
                        default:
                           msg.append("NotApplicable");
                     }

                     context.debug(msg.toString());
                     return pd;
                  } catch (IndeterminateEvaluationException var7) {
                     String im = var7.getMessage();
                     context.debug(policySetId + ", " + policySetVersion + " evaluates to Indeterminate with message: " + (im != null ? im : var7.getClass().getName()));
                     throw var7;
                  }
               }
            }
         };
      }
   }

   private List combineObligations(List local, List obs) {
      List combined = new ArrayList();
      combined.addAll(obs);
      combined.addAll(local);
      return combined;
   }
}
