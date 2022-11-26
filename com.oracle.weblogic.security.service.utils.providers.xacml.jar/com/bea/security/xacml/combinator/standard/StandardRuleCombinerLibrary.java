package com.bea.security.xacml.combinator.standard;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.RuleCombinerEvaluator;
import com.bea.security.xacml.RuleDecision;
import com.bea.security.xacml.RuleEvaluator;
import com.bea.security.xacml.combinator.NoMatchRuleCombinerEvaluator;
import com.bea.security.xacml.combinator.SimpleRuleCombinerEvaluatorFactory;
import com.bea.security.xacml.combinator.SimpleRuleCombinerLibraryBase;
import com.bea.security.xacml.rule.NoMatchRuleEvaluator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StandardRuleCombinerLibrary extends SimpleRuleCombinerLibraryBase {
   public StandardRuleCombinerLibrary() throws URISyntaxException {
      try {
         this.register(new URI("urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:deny-overrides"), new SimpleRuleCombinerEvaluatorFactory() {
            public RuleCombinerEvaluator createCombiner(final List rules) {
               Iterator scanIt = rules.iterator();

               while(scanIt.hasNext()) {
                  if (scanIt.next() instanceof NoMatchRuleEvaluator) {
                     scanIt.remove();
                  }
               }

               if (rules.size() == 0) {
                  return NoMatchRuleCombinerEvaluator.getInstance();
               } else if (rules.size() == 1) {
                  return (RuleCombinerEvaluator)rules.get(0);
               } else {
                  return new RuleCombinerEvaluator() {
                     public RuleDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        List indeterminates = null;
                        boolean potentialDeny = false;
                        boolean atLeastOnePermit = false;
                        Iterator it = rules.iterator();

                        while(it.hasNext()) {
                           RuleEvaluator re = (RuleEvaluator)it.next();

                           try {
                              RuleDecision rd = re.evaluate(context);
                              switch (rd.getDecisionValue()) {
                                 case 0:
                                    atLeastOnePermit = true;
                                    break;
                                 case 1:
                                    return rd;
                              }
                           } catch (IndeterminateEvaluationException var8) {
                              if (indeterminates == null) {
                                 indeterminates = new ArrayList();
                              }

                              indeterminates.add(var8);
                              if (!re.hasPermitEffect()) {
                                 potentialDeny = true;
                              }
                           }
                        }

                        if (potentialDeny) {
                           if (indeterminates.size() == 1) {
                              throw (IndeterminateEvaluationException)indeterminates.get(0);
                           } else {
                              throw new IndeterminateEvaluationException(indeterminates);
                           }
                        } else if (atLeastOnePermit) {
                           return RuleDecision.getPermitDecision();
                        } else if (indeterminates != null) {
                           if (indeterminates.size() == 1) {
                              throw (IndeterminateEvaluationException)indeterminates.get(0);
                           } else {
                              throw new IndeterminateEvaluationException(indeterminates);
                           }
                        } else {
                           return RuleDecision.getNotApplicableDecision();
                        }
                     }
                  };
               }
            }
         });
         this.register(new URI("urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides"), new SimpleRuleCombinerEvaluatorFactory() {
            public RuleCombinerEvaluator createCombiner(final List rules) {
               Iterator scanIt = rules.iterator();

               while(scanIt.hasNext()) {
                  if (scanIt.next() instanceof NoMatchRuleEvaluator) {
                     scanIt.remove();
                  }
               }

               if (rules.size() == 0) {
                  return NoMatchRuleCombinerEvaluator.getInstance();
               } else if (rules.size() == 1) {
                  return (RuleCombinerEvaluator)rules.get(0);
               } else {
                  return new RuleCombinerEvaluator() {
                     public RuleDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        List indeterminates = null;
                        boolean potentialPermit = false;
                        boolean atLeastOneDeny = false;
                        Iterator it = rules.iterator();

                        while(it.hasNext()) {
                           RuleEvaluator re = (RuleEvaluator)it.next();

                           try {
                              RuleDecision rd = re.evaluate(context);
                              switch (rd.getDecisionValue()) {
                                 case 0:
                                    return rd;
                                 case 1:
                                    atLeastOneDeny = true;
                              }
                           } catch (IndeterminateEvaluationException var8) {
                              if (indeterminates == null) {
                                 indeterminates = new ArrayList();
                              }

                              indeterminates.add(var8);
                              if (re.hasPermitEffect()) {
                                 potentialPermit = true;
                              }
                           }
                        }

                        if (potentialPermit) {
                           if (indeterminates.size() == 1) {
                              throw (IndeterminateEvaluationException)indeterminates.get(0);
                           } else {
                              throw new IndeterminateEvaluationException(indeterminates);
                           }
                        } else if (atLeastOneDeny) {
                           return RuleDecision.getDenyDecision();
                        } else if (indeterminates != null) {
                           if (indeterminates.size() == 1) {
                              throw (IndeterminateEvaluationException)indeterminates.get(0);
                           } else {
                              throw new IndeterminateEvaluationException(indeterminates);
                           }
                        } else {
                           return RuleDecision.getNotApplicableDecision();
                        }
                     }
                  };
               }
            }
         });
         this.register(new URI("urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:first-applicable"), new SimpleRuleCombinerEvaluatorFactory() {
            public RuleCombinerEvaluator createCombiner(final List rules) {
               Iterator scanIt = rules.iterator();

               while(scanIt.hasNext()) {
                  if (scanIt.next() instanceof NoMatchRuleEvaluator) {
                     scanIt.remove();
                  }
               }

               if (rules.size() == 0) {
                  return NoMatchRuleCombinerEvaluator.getInstance();
               } else if (rules.size() == 1) {
                  return (RuleCombinerEvaluator)rules.get(0);
               } else {
                  return new RuleCombinerEvaluator() {
                     public RuleDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        Iterator it = rules.iterator();

                        RuleDecision rd;
                        do {
                           if (!it.hasNext()) {
                              return RuleDecision.getNotApplicableDecision();
                           }

                           rd = ((RuleEvaluator)it.next()).evaluate(context);
                        } while(rd.getDecisionValue() == 2);

                        return rd;
                     }
                  };
               }
            }
         });
         this.register(new URI("urn:oasis:names:tc:xacml:1.1:rule-combining-algorithm:ordered-deny-overrides"), new SimpleRuleCombinerEvaluatorFactory() {
            public RuleCombinerEvaluator createCombiner(final List rules) {
               Iterator scanIt = rules.iterator();

               while(scanIt.hasNext()) {
                  if (scanIt.next() instanceof NoMatchRuleEvaluator) {
                     scanIt.remove();
                  }
               }

               if (rules.size() == 0) {
                  return NoMatchRuleCombinerEvaluator.getInstance();
               } else if (rules.size() == 1) {
                  return (RuleCombinerEvaluator)rules.get(0);
               } else {
                  return new RuleCombinerEvaluator() {
                     public RuleDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        List indeterminates = null;
                        boolean potentialDeny = false;
                        boolean atLeastOnePermit = false;
                        Iterator it = rules.iterator();

                        while(it.hasNext()) {
                           RuleEvaluator re = (RuleEvaluator)it.next();

                           try {
                              RuleDecision rd = re.evaluate(context);
                              switch (rd.getDecisionValue()) {
                                 case 0:
                                    atLeastOnePermit = true;
                                    break;
                                 case 1:
                                    return rd;
                              }
                           } catch (IndeterminateEvaluationException var8) {
                              if (indeterminates == null) {
                                 indeterminates = new ArrayList();
                              }

                              indeterminates.add(var8);
                              if (!re.hasPermitEffect()) {
                                 potentialDeny = true;
                              }
                           }
                        }

                        if (potentialDeny) {
                           if (indeterminates.size() == 1) {
                              throw (IndeterminateEvaluationException)indeterminates.get(0);
                           } else {
                              throw new IndeterminateEvaluationException(indeterminates);
                           }
                        } else if (atLeastOnePermit) {
                           return RuleDecision.getPermitDecision();
                        } else if (indeterminates != null) {
                           if (indeterminates.size() == 1) {
                              throw (IndeterminateEvaluationException)indeterminates.get(0);
                           } else {
                              throw new IndeterminateEvaluationException(indeterminates);
                           }
                        } else {
                           return RuleDecision.getNotApplicableDecision();
                        }
                     }
                  };
               }
            }
         });
         this.register(new URI("urn:oasis:names:tc:xacml:1.1:rule-combining-algorithm:ordered-permit-overrides"), new SimpleRuleCombinerEvaluatorFactory() {
            public RuleCombinerEvaluator createCombiner(final List rules) {
               Iterator scanIt = rules.iterator();

               while(scanIt.hasNext()) {
                  if (scanIt.next() instanceof NoMatchRuleEvaluator) {
                     scanIt.remove();
                  }
               }

               if (rules.size() == 0) {
                  return NoMatchRuleCombinerEvaluator.getInstance();
               } else if (rules.size() == 1) {
                  return (RuleCombinerEvaluator)rules.get(0);
               } else {
                  return new RuleCombinerEvaluator() {
                     public RuleDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        List indeterminates = null;
                        boolean potentialPermit = false;
                        boolean atLeastOneDeny = false;
                        Iterator it = rules.iterator();

                        while(it.hasNext()) {
                           RuleEvaluator re = (RuleEvaluator)it.next();

                           try {
                              RuleDecision rd = re.evaluate(context);
                              switch (rd.getDecisionValue()) {
                                 case 0:
                                    return rd;
                                 case 1:
                                    atLeastOneDeny = true;
                              }
                           } catch (IndeterminateEvaluationException var8) {
                              if (indeterminates == null) {
                                 indeterminates = new ArrayList();
                              }

                              indeterminates.add(var8);
                              if (re.hasPermitEffect()) {
                                 potentialPermit = true;
                              }
                           }
                        }

                        if (potentialPermit) {
                           if (indeterminates.size() == 1) {
                              throw (IndeterminateEvaluationException)indeterminates.get(0);
                           } else {
                              throw new IndeterminateEvaluationException(indeterminates);
                           }
                        } else if (atLeastOneDeny) {
                           return RuleDecision.getDenyDecision();
                        } else if (indeterminates != null) {
                           throw new IndeterminateEvaluationException(indeterminates);
                        } else {
                           return RuleDecision.getNotApplicableDecision();
                        }
                     }
                  };
               }
            }
         });
      } catch (java.net.URISyntaxException var2) {
         throw new URISyntaxException(var2);
      }
   }
}
