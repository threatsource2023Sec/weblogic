package com.bea.security.xacml.combinator.standard;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.PolicyDecision;
import com.bea.security.xacml.PolicyEvaluator;
import com.bea.security.xacml.combinator.SimplePolicyCombinerEvaluatorFactory;
import com.bea.security.xacml.combinator.SimplePolicyCombinerLibraryBase;
import com.bea.security.xacml.policy.NoMatchPolicyEvaluator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StandardPolicyCombinerLibrary extends SimplePolicyCombinerLibraryBase {
   public StandardPolicyCombinerLibrary() throws URISyntaxException {
      try {
         this.register(new URI("urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:deny-overrides"), new SimplePolicyCombinerEvaluatorFactory() {
            public PolicyEvaluator createCombiner(final List policies) {
               Iterator scanIt = policies.iterator();

               while(scanIt.hasNext()) {
                  if (scanIt.next() instanceof NoMatchPolicyEvaluator) {
                     scanIt.remove();
                  }
               }

               if (policies.size() == 0) {
                  return NoMatchPolicyEvaluator.getInstance();
               } else if (policies.size() == 1) {
                  final PolicyEvaluator one = (PolicyEvaluator)policies.get(0);
                  return new PolicyEvaluator() {
                     public PolicyDecision evaluate(EvaluationCtx context) {
                        try {
                           return one.evaluate(context);
                        } catch (IndeterminateEvaluationException var3) {
                           return PolicyDecision.getDenyDecision();
                        }
                     }
                  };
               } else {
                  return new PolicyEvaluator() {
                     public PolicyDecision evaluate(EvaluationCtx context) {
                        try {
                           List permitObligations = null;
                           boolean atLeastOnePermit = false;
                           Iterator it = policies.iterator();

                           while(it.hasNext()) {
                              PolicyEvaluator re = (PolicyEvaluator)it.next();
                              PolicyDecision rd = re.evaluate(context);
                              switch (rd.getDecisionValue()) {
                                 case 0:
                                    List pos = rd.getObligations();
                                    if (pos != null) {
                                       if (permitObligations == null) {
                                          permitObligations = new ArrayList();
                                       }

                                       permitObligations.addAll(pos);
                                    }

                                    atLeastOnePermit = true;
                                    break;
                                 case 1:
                                    return rd;
                              }
                           }

                           if (atLeastOnePermit) {
                              return permitObligations == null ? PolicyDecision.getPermitDecision() : PolicyDecision.getPermitDecision(permitObligations);
                           } else {
                              return PolicyDecision.getNotApplicableDecision();
                           }
                        } catch (IndeterminateEvaluationException var8) {
                           return PolicyDecision.getDenyDecision();
                        }
                     }
                  };
               }
            }
         });
         this.register(new URI("urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:permit-overrides"), new SimplePolicyCombinerEvaluatorFactory() {
            public PolicyEvaluator createCombiner(final List policies) {
               Iterator scanIt = policies.iterator();

               while(scanIt.hasNext()) {
                  if (scanIt.next() instanceof NoMatchPolicyEvaluator) {
                     scanIt.remove();
                  }
               }

               if (policies.size() == 0) {
                  return NoMatchPolicyEvaluator.getInstance();
               } else if (policies.size() == 1) {
                  return (PolicyEvaluator)policies.get(0);
               } else {
                  return new PolicyEvaluator() {
                     public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        List denyObligations = null;
                        boolean atLeastOneDeny = false;
                        Iterator it = policies.iterator();

                        while(it.hasNext()) {
                           PolicyEvaluator re = (PolicyEvaluator)it.next();
                           PolicyDecision rd = re.evaluate(context);
                           switch (rd.getDecisionValue()) {
                              case 0:
                                 return rd;
                              case 1:
                                 List dos = rd.getObligations();
                                 if (dos != null) {
                                    if (denyObligations == null) {
                                       denyObligations = new ArrayList();
                                    }

                                    denyObligations.addAll(dos);
                                 }

                                 atLeastOneDeny = true;
                           }
                        }

                        if (atLeastOneDeny) {
                           return denyObligations == null ? PolicyDecision.getDenyDecision() : PolicyDecision.getDenyDecision(denyObligations);
                        } else {
                           return PolicyDecision.getNotApplicableDecision();
                        }
                     }
                  };
               }
            }
         });
         this.register(new URI("urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:first-applicable"), new SimplePolicyCombinerEvaluatorFactory() {
            public PolicyEvaluator createCombiner(final List policies) {
               Iterator scanIt = policies.iterator();

               while(scanIt.hasNext()) {
                  if (scanIt.next() instanceof NoMatchPolicyEvaluator) {
                     scanIt.remove();
                  }
               }

               if (policies.size() == 0) {
                  return NoMatchPolicyEvaluator.getInstance();
               } else if (policies.size() == 1) {
                  return (PolicyEvaluator)policies.get(0);
               } else {
                  return new PolicyEvaluator() {
                     public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        Iterator it = policies.iterator();

                        PolicyDecision rd;
                        do {
                           if (!it.hasNext()) {
                              return PolicyDecision.getNotApplicableDecision();
                           }

                           rd = ((PolicyEvaluator)it.next()).evaluate(context);
                        } while(rd.getDecisionValue() == 2);

                        return rd;
                     }
                  };
               }
            }
         });
         this.register(new URI("urn:oasis:names:tc:xacml:1.0:policy-combining-algorithm:only-one-applicable"), new SimplePolicyCombinerEvaluatorFactory() {
            public PolicyEvaluator createCombiner(final List policies) {
               Iterator scanIt = policies.iterator();

               while(scanIt.hasNext()) {
                  if (scanIt.next() instanceof NoMatchPolicyEvaluator) {
                     scanIt.remove();
                  }
               }

               if (policies.size() == 0) {
                  return NoMatchPolicyEvaluator.getInstance();
               } else if (policies.size() == 1) {
                  return (PolicyEvaluator)policies.get(0);
               } else {
                  return new PolicyEvaluator() {
                     public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        PolicyDecision applicableDecision = null;
                        Iterator it = policies.iterator();

                        while(it.hasNext()) {
                           PolicyDecision rd = ((PolicyEvaluator)it.next()).evaluate(context);
                           if (rd.isTargetApplicable()) {
                              if (applicableDecision != null) {
                                 throw new IndeterminateEvaluationException("Too many applicable members");
                              }

                              applicableDecision = rd;
                           }
                        }

                        if (applicableDecision != null) {
                           return applicableDecision;
                        } else {
                           return PolicyDecision.getNotApplicableDecision();
                        }
                     }
                  };
               }
            }
         });
         this.register(new URI("urn:oasis:names:tc:xacml:1.1:policy-combining-algorithm:ordered-deny-overrides"), new SimplePolicyCombinerEvaluatorFactory() {
            public PolicyEvaluator createCombiner(final List policies) {
               Iterator scanIt = policies.iterator();

               while(scanIt.hasNext()) {
                  if (scanIt.next() instanceof NoMatchPolicyEvaluator) {
                     scanIt.remove();
                  }
               }

               if (policies.size() == 0) {
                  return NoMatchPolicyEvaluator.getInstance();
               } else if (policies.size() == 1) {
                  final PolicyEvaluator one = (PolicyEvaluator)policies.get(0);
                  return new PolicyEvaluator() {
                     public PolicyDecision evaluate(EvaluationCtx context) {
                        try {
                           return one.evaluate(context);
                        } catch (IndeterminateEvaluationException var3) {
                           return PolicyDecision.getDenyDecision();
                        }
                     }
                  };
               } else {
                  return new PolicyEvaluator() {
                     public PolicyDecision evaluate(EvaluationCtx context) {
                        try {
                           List permitObligations = null;
                           boolean atLeastOnePermit = false;
                           Iterator it = policies.iterator();

                           while(it.hasNext()) {
                              PolicyEvaluator re = (PolicyEvaluator)it.next();
                              PolicyDecision rd = re.evaluate(context);
                              switch (rd.getDecisionValue()) {
                                 case 0:
                                    List pos = rd.getObligations();
                                    if (pos != null) {
                                       if (permitObligations == null) {
                                          permitObligations = new ArrayList();
                                       }

                                       permitObligations.addAll(pos);
                                    }

                                    atLeastOnePermit = true;
                                    break;
                                 case 1:
                                    return rd;
                              }
                           }

                           if (atLeastOnePermit) {
                              return permitObligations == null ? PolicyDecision.getPermitDecision() : PolicyDecision.getPermitDecision(permitObligations);
                           } else {
                              return PolicyDecision.getNotApplicableDecision();
                           }
                        } catch (IndeterminateEvaluationException var8) {
                           return PolicyDecision.getDenyDecision();
                        }
                     }
                  };
               }
            }
         });
         this.register(new URI("urn:oasis:names:tc:xacml:1.1:policy-combining-algorithm:ordered-permit-overrides"), new SimplePolicyCombinerEvaluatorFactory() {
            public PolicyEvaluator createCombiner(final List policies) {
               Iterator scanIt = policies.iterator();

               while(scanIt.hasNext()) {
                  if (scanIt.next() instanceof NoMatchPolicyEvaluator) {
                     scanIt.remove();
                  }
               }

               if (policies.size() == 0) {
                  return NoMatchPolicyEvaluator.getInstance();
               } else if (policies.size() == 1) {
                  return (PolicyEvaluator)policies.get(0);
               } else {
                  return new PolicyEvaluator() {
                     public PolicyDecision evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        List denyObligations = null;
                        boolean atLeastOneDeny = false;
                        Iterator it = policies.iterator();

                        while(it.hasNext()) {
                           PolicyEvaluator re = (PolicyEvaluator)it.next();
                           PolicyDecision rd = re.evaluate(context);
                           switch (rd.getDecisionValue()) {
                              case 0:
                                 return rd;
                              case 1:
                                 List dos = rd.getObligations();
                                 if (dos != null) {
                                    if (denyObligations == null) {
                                       denyObligations = new ArrayList();
                                    }

                                    denyObligations.addAll(dos);
                                 }

                                 atLeastOneDeny = true;
                           }
                        }

                        if (atLeastOneDeny) {
                           return denyObligations == null ? PolicyDecision.getDenyDecision() : PolicyDecision.getDenyDecision(denyObligations);
                        } else {
                           return PolicyDecision.getNotApplicableDecision();
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
