package com.bea.security.xacml.function.standard;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeValue;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.BooleanAttribute;
import com.bea.common.security.xacml.attr.GenericBag;
import com.bea.common.security.xacml.policy.Apply;
import com.bea.common.security.xacml.policy.Expression;
import com.bea.common.security.xacml.policy.Function;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.AttributeEvaluatorWrapper;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.InvalidArgumentsException;
import com.bea.security.xacml.attr.evaluator.BooleanAttributeEvaluatorBase;
import com.bea.security.xacml.function.FunctionFactory;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HigherOrderBagFunctionFactory implements FunctionFactory {
   private static final String ANY_OF = "urn:oasis:names:tc:xacml:1.0:function:any-of";
   private static final String ALL_OF = "urn:oasis:names:tc:xacml:1.0:function:all-of";
   private static final String ANY_OF_ANY = "urn:oasis:names:tc:xacml:1.0:function:any-of-any";
   private static final String ALL_OF_ALL = "urn:oasis:names:tc:xacml:1.0:function:all-of-all";
   private static final String ANY_OF_ALL = "urn:oasis:names:tc:xacml:1.0:function:any-of-all";
   private static final String ALL_OF_ANY = "urn:oasis:names:tc:xacml:1.0:function:all-of-any";
   private static final String MAP = "urn:oasis:names:tc:xacml:1.0:function:map";
   private static final String ATTR_VALUE_DATA_KEY = "attributeValueKey";
   private static final String BAG_VALUE_DATA_KEY = "bagKey";
   private static final String ITERATOR_DATA_KEY = "iteratorKey";
   private URI anyOf;
   private URI allOf;
   private URI anyOfAny;
   private URI allOfAny;
   private URI anyOfAll;
   private URI allOfAll;
   private URI map;
   private Set bagFunctions = new HashSet();

   public HigherOrderBagFunctionFactory() throws URISyntaxException {
      try {
         this.anyOf = new URI("urn:oasis:names:tc:xacml:1.0:function:any-of");
         this.allOf = new URI("urn:oasis:names:tc:xacml:1.0:function:all-of");
         this.anyOfAny = new URI("urn:oasis:names:tc:xacml:1.0:function:any-of-any");
         this.allOfAny = new URI("urn:oasis:names:tc:xacml:1.0:function:all-of-any");
         this.anyOfAll = new URI("urn:oasis:names:tc:xacml:1.0:function:any-of-all");
         this.allOfAll = new URI("urn:oasis:names:tc:xacml:1.0:function:all-of-all");
         this.map = new URI("urn:oasis:names:tc:xacml:1.0:function:map");
      } catch (java.net.URISyntaxException var2) {
         throw new URISyntaxException(var2);
      }

      this.bagFunctions.add(this.anyOf);
      this.bagFunctions.add(this.allOf);
      this.bagFunctions.add(this.anyOfAll);
      this.bagFunctions.add(this.allOfAll);
      this.bagFunctions.add(this.anyOfAny);
      this.bagFunctions.add(this.allOfAny);
      this.bagFunctions.add(this.map);
   }

   public AttributeEvaluator createFunction(Apply expression, Map variables, Configuration config, Iterator otherFactories) throws InvalidArgumentsException, EvaluationPlanException, URISyntaxException {
      URI identifier = expression.getFunctionId();
      if (this.bagFunctions.contains(identifier)) {
         List es;
         URI fId;
         ArrayList exprs;
         AttributeEvaluator ae1;
         AttributeEvaluator ae2;
         final ExpressionWrapper wrap1;
         final ExpressionBagWrapper wrap2;
         Apply apply;
         final AttributeEvaluator ae;
         if (this.anyOf.equals(identifier)) {
            es = expression.getExpressions();
            if (es.size() != 3) {
               throw new EvaluationPlanException("Bag function requires three parameters");
            } else if (!(es.get(0) instanceof Function)) {
               throw new EvaluationPlanException("First parameter of bag function must be Function element");
            } else {
               fId = ((Function)es.get(0)).getFunctionId();
               exprs = new ArrayList();
               ae1 = config.getExpressionRegistry().parse((Expression)es.get(1), variables, config);
               wrap1 = new ExpressionWrapper(ae1);
               exprs.add(wrap1);
               ae2 = config.getExpressionRegistry().parse((Expression)es.get(2), variables, config);
               if (!ae2.getType().isBag()) {
                  throw new EvaluationPlanException("Last parameter of anyOf bag function must be bag type");
               } else {
                  wrap2 = new ExpressionBagWrapper(ae2);
                  exprs.add(wrap2);
                  apply = new Apply(fId, exprs);
                  ae = config.getExpressionRegistry().parse(apply, variables, config);
                  if (!Type.BOOLEAN.equals(ae.getType())) {
                     throw new EvaluationPlanException("Bag function must evaluate to Boolean return type");
                  } else {
                     return new BooleanAttributeEvaluatorBase() {
                        public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           wrap2.prime(context);
                           if (!wrap2.hasNext(context)) {
                              if (context.isDebugEnabled()) {
                                 HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.anyOf, (Bag)BooleanAttribute.FALSE);
                              }

                              return BooleanAttribute.FALSE;
                           } else {
                              wrap1.prime(context);

                              do {
                                 wrap2.shiftNext(context);
                                 if (BooleanAttribute.TRUE.equals(ae.evaluate(context))) {
                                    if (context.isDebugEnabled()) {
                                       HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.anyOf, (Bag)BooleanAttribute.TRUE);
                                    }

                                    return BooleanAttribute.TRUE;
                                 }
                              } while(wrap2.hasNext(context));

                              if (context.isDebugEnabled()) {
                                 HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.anyOf, (Bag)BooleanAttribute.FALSE);
                              }

                              return BooleanAttribute.FALSE;
                           }
                        }
                     };
                  }
               }
            }
         } else if (this.allOf.equals(identifier)) {
            es = expression.getExpressions();
            if (es.size() != 3) {
               throw new EvaluationPlanException("Bag function requires three parameters");
            } else if (!(es.get(0) instanceof Function)) {
               throw new EvaluationPlanException("First parameter of bag function must be Function element");
            } else {
               fId = ((Function)es.get(0)).getFunctionId();
               exprs = new ArrayList();
               ae1 = config.getExpressionRegistry().parse((Expression)es.get(1), variables, config);
               wrap1 = new ExpressionWrapper(ae1);
               exprs.add(wrap1);
               ae2 = config.getExpressionRegistry().parse((Expression)es.get(2), variables, config);
               if (!ae2.getType().isBag()) {
                  throw new EvaluationPlanException("Last parameter of allOf bag function must be bag type");
               } else {
                  wrap2 = new ExpressionBagWrapper(ae2);
                  exprs.add(wrap2);
                  apply = new Apply(fId, exprs);
                  ae = config.getExpressionRegistry().parse(apply, variables, config);
                  if (!Type.BOOLEAN.equals(ae.getType())) {
                     throw new EvaluationPlanException("Bag function must evaluate to Boolean return type");
                  } else {
                     return new BooleanAttributeEvaluatorBase() {
                        public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           wrap2.prime(context);
                           if (!wrap2.hasNext(context)) {
                              if (context.isDebugEnabled()) {
                                 HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.allOf, (Bag)BooleanAttribute.FALSE);
                              }

                              return BooleanAttribute.FALSE;
                           } else {
                              wrap1.prime(context);

                              do {
                                 wrap2.shiftNext(context);
                                 if (BooleanAttribute.FALSE.equals(ae.evaluate(context))) {
                                    if (context.isDebugEnabled()) {
                                       HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.allOf, (Bag)BooleanAttribute.FALSE);
                                    }

                                    return BooleanAttribute.FALSE;
                                 }
                              } while(wrap2.hasNext(context));

                              if (context.isDebugEnabled()) {
                                 HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.allOf, (Bag)BooleanAttribute.TRUE);
                              }

                              return BooleanAttribute.TRUE;
                           }
                        }
                     };
                  }
               }
            }
         } else {
            final ExpressionBagWrapper wrap1;
            if (this.anyOfAny.equals(identifier)) {
               es = expression.getExpressions();
               if (es.size() != 3) {
                  throw new EvaluationPlanException("Bag function requires three parameters");
               } else if (!(es.get(0) instanceof Function)) {
                  throw new EvaluationPlanException("First parameter of bag function must be Function element");
               } else {
                  fId = ((Function)es.get(0)).getFunctionId();
                  exprs = new ArrayList();
                  ae1 = config.getExpressionRegistry().parse((Expression)es.get(1), variables, config);
                  if (!ae1.getType().isBag()) {
                     throw new EvaluationPlanException("Second parameter of anyOfAny bag function must be bag type");
                  } else {
                     wrap1 = new ExpressionBagWrapper(ae1);
                     exprs.add(wrap1);
                     ae2 = config.getExpressionRegistry().parse((Expression)es.get(2), variables, config);
                     if (!ae2.getType().isBag()) {
                        throw new EvaluationPlanException("Last parameter of anyOfAny bag function must be bag type");
                     } else {
                        wrap2 = new ExpressionBagWrapper(ae2);
                        exprs.add(wrap2);
                        apply = new Apply(fId, exprs);
                        ae = config.getExpressionRegistry().parse(apply, variables, config);
                        if (!Type.BOOLEAN.equals(ae.getType())) {
                           throw new EvaluationPlanException("Bag function must evaluate to Boolean return type");
                        } else {
                           return new BooleanAttributeEvaluatorBase() {
                              public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                                 wrap1.prime(context);
                                 if (!wrap1.hasNext(context)) {
                                    if (context.isDebugEnabled()) {
                                       HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.anyOfAny, (Bag)BooleanAttribute.FALSE);
                                    }

                                    return BooleanAttribute.FALSE;
                                 } else {
                                    wrap2.prime(context);
                                    if (!wrap2.hasNext(context)) {
                                       if (context.isDebugEnabled()) {
                                          HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.anyOfAny, (Bag)BooleanAttribute.FALSE);
                                       }

                                       return BooleanAttribute.FALSE;
                                    } else {
                                       do {
                                          wrap1.shiftNext(context);

                                          do {
                                             wrap2.shiftNext(context);
                                             if (BooleanAttribute.TRUE.equals(ae.evaluate(context))) {
                                                if (context.isDebugEnabled()) {
                                                   HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.anyOfAny, (Bag)BooleanAttribute.TRUE);
                                                }

                                                return BooleanAttribute.TRUE;
                                             }
                                          } while(wrap2.hasNext(context));

                                          wrap2.reset(context);
                                       } while(wrap1.hasNext(context));

                                       if (context.isDebugEnabled()) {
                                          HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.anyOfAny, (Bag)BooleanAttribute.FALSE);
                                       }

                                       return BooleanAttribute.FALSE;
                                    }
                                 }
                              }
                           };
                        }
                     }
                  }
               }
            } else if (this.allOfAny.equals(identifier)) {
               es = expression.getExpressions();
               if (es.size() != 3) {
                  throw new EvaluationPlanException("Bag function requires three parameters");
               } else if (!(es.get(0) instanceof Function)) {
                  throw new EvaluationPlanException("First parameter of bag function must be Function element");
               } else {
                  fId = ((Function)es.get(0)).getFunctionId();
                  exprs = new ArrayList();
                  ae1 = config.getExpressionRegistry().parse((Expression)es.get(1), variables, config);
                  if (!ae1.getType().isBag()) {
                     throw new EvaluationPlanException("Second parameter of allOfAny bag function must be bag type");
                  } else {
                     wrap1 = new ExpressionBagWrapper(ae1);
                     exprs.add(wrap1);
                     ae2 = config.getExpressionRegistry().parse((Expression)es.get(2), variables, config);
                     if (!ae2.getType().isBag()) {
                        throw new EvaluationPlanException("Last parameter of allOfAny bag function must be bag type");
                     } else {
                        wrap2 = new ExpressionBagWrapper(ae2);
                        exprs.add(wrap2);
                        apply = new Apply(fId, exprs);
                        ae = config.getExpressionRegistry().parse(apply, variables, config);
                        if (!Type.BOOLEAN.equals(ae.getType())) {
                           throw new EvaluationPlanException("Bag function must evaluate to Boolean return type");
                        } else {
                           return new BooleanAttributeEvaluatorBase() {
                              public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                                 wrap1.prime(context);
                                 if (!wrap1.hasNext(context)) {
                                    if (context.isDebugEnabled()) {
                                       HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.allOfAny, (Bag)BooleanAttribute.FALSE);
                                    }

                                    return BooleanAttribute.FALSE;
                                 } else {
                                    wrap2.prime(context);
                                    if (!wrap2.hasNext(context)) {
                                       if (context.isDebugEnabled()) {
                                          HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.allOfAny, (Bag)BooleanAttribute.FALSE);
                                       }

                                       return BooleanAttribute.FALSE;
                                    } else {
                                       label42:
                                       do {
                                          wrap1.shiftNext(context);

                                          do {
                                             wrap2.shiftNext(context);
                                             if (BooleanAttribute.TRUE.equals(ae.evaluate(context))) {
                                                wrap2.reset(context);
                                                continue label42;
                                             }
                                          } while(wrap2.hasNext(context));

                                          if (context.isDebugEnabled()) {
                                             HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.allOfAny, (Bag)BooleanAttribute.FALSE);
                                          }

                                          return BooleanAttribute.FALSE;
                                       } while(wrap1.hasNext(context));

                                       if (context.isDebugEnabled()) {
                                          HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.allOfAny, (Bag)BooleanAttribute.TRUE);
                                       }

                                       return BooleanAttribute.TRUE;
                                    }
                                 }
                              }
                           };
                        }
                     }
                  }
               }
            } else if (this.anyOfAll.equals(identifier)) {
               es = expression.getExpressions();
               if (es.size() != 3) {
                  throw new EvaluationPlanException("Bag function requires three parameters");
               } else if (!(es.get(0) instanceof Function)) {
                  throw new EvaluationPlanException("First parameter of bag function must be Function element");
               } else {
                  fId = ((Function)es.get(0)).getFunctionId();
                  exprs = new ArrayList();
                  ae1 = config.getExpressionRegistry().parse((Expression)es.get(1), variables, config);
                  if (!ae1.getType().isBag()) {
                     throw new EvaluationPlanException("Second parameter of anyOfAll bag function must be bag type");
                  } else {
                     wrap1 = new ExpressionBagWrapper(ae1);
                     exprs.add(wrap1);
                     ae2 = config.getExpressionRegistry().parse((Expression)es.get(2), variables, config);
                     if (!ae2.getType().isBag()) {
                        throw new EvaluationPlanException("Last parameter of anyOfAll bag function must be bag type");
                     } else {
                        wrap2 = new ExpressionBagWrapper(ae2);
                        exprs.add(wrap2);
                        apply = new Apply(fId, exprs);
                        ae = config.getExpressionRegistry().parse(apply, variables, config);
                        if (!Type.BOOLEAN.equals(ae.getType())) {
                           throw new EvaluationPlanException("Bag function must evaluate to Boolean return type");
                        } else {
                           return new BooleanAttributeEvaluatorBase() {
                              public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                                 wrap1.prime(context);
                                 if (!wrap1.hasNext(context)) {
                                    if (context.isDebugEnabled()) {
                                       HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.anyOfAll, (Bag)BooleanAttribute.FALSE);
                                    }

                                    return BooleanAttribute.FALSE;
                                 } else {
                                    wrap2.prime(context);
                                    if (!wrap2.hasNext(context)) {
                                       if (context.isDebugEnabled()) {
                                          HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.anyOfAll, (Bag)BooleanAttribute.FALSE);
                                       }

                                       return BooleanAttribute.FALSE;
                                    } else {
                                       label42:
                                       do {
                                          wrap1.shiftNext(context);

                                          do {
                                             wrap2.shiftNext(context);
                                             if (BooleanAttribute.FALSE.equals(ae.evaluate(context))) {
                                                wrap2.reset(context);
                                                continue label42;
                                             }
                                          } while(wrap2.hasNext(context));

                                          if (context.isDebugEnabled()) {
                                             HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.anyOfAll, (Bag)BooleanAttribute.TRUE);
                                          }

                                          return BooleanAttribute.TRUE;
                                       } while(wrap1.hasNext(context));

                                       if (context.isDebugEnabled()) {
                                          HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.anyOfAll, (Bag)BooleanAttribute.FALSE);
                                       }

                                       return BooleanAttribute.FALSE;
                                    }
                                 }
                              }
                           };
                        }
                     }
                  }
               }
            } else if (this.allOfAll.equals(identifier)) {
               es = expression.getExpressions();
               if (es.size() != 3) {
                  throw new EvaluationPlanException("Bag function requires three parameters");
               } else if (!(es.get(0) instanceof Function)) {
                  throw new EvaluationPlanException("First parameter of bag function must be Function element");
               } else {
                  fId = ((Function)es.get(0)).getFunctionId();
                  exprs = new ArrayList();
                  ae1 = config.getExpressionRegistry().parse((Expression)es.get(1), variables, config);
                  if (!ae1.getType().isBag()) {
                     throw new EvaluationPlanException("Second parameter of allOfAll bag function must be bag type");
                  } else {
                     wrap1 = new ExpressionBagWrapper(ae1);
                     exprs.add(wrap1);
                     ae2 = config.getExpressionRegistry().parse((Expression)es.get(2), variables, config);
                     if (!ae2.getType().isBag()) {
                        throw new EvaluationPlanException("Last parameter of allOfAll bag function must be bag type");
                     } else {
                        wrap2 = new ExpressionBagWrapper(ae2);
                        exprs.add(wrap2);
                        apply = new Apply(fId, exprs);
                        ae = config.getExpressionRegistry().parse(apply, variables, config);
                        if (!Type.BOOLEAN.equals(ae.getType())) {
                           throw new EvaluationPlanException("Bag function must evaluate to Boolean return type");
                        } else {
                           return new BooleanAttributeEvaluatorBase() {
                              public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                                 wrap1.prime(context);
                                 if (!wrap1.hasNext(context)) {
                                    if (context.isDebugEnabled()) {
                                       HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.allOfAll, (Bag)BooleanAttribute.FALSE);
                                    }

                                    return BooleanAttribute.FALSE;
                                 } else {
                                    wrap2.prime(context);
                                    if (!wrap2.hasNext(context)) {
                                       if (context.isDebugEnabled()) {
                                          HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.allOfAll, (Bag)BooleanAttribute.FALSE);
                                       }

                                       return BooleanAttribute.FALSE;
                                    } else {
                                       do {
                                          wrap1.shiftNext(context);

                                          do {
                                             wrap2.shiftNext(context);
                                             if (BooleanAttribute.FALSE.equals(ae.evaluate(context))) {
                                                if (context.isDebugEnabled()) {
                                                   HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.allOfAll, (Bag)BooleanAttribute.FALSE);
                                                }

                                                return BooleanAttribute.FALSE;
                                             }
                                          } while(wrap2.hasNext(context));

                                          wrap2.reset(context);
                                       } while(wrap1.hasNext(context));

                                       if (context.isDebugEnabled()) {
                                          HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.allOfAll, (Bag)BooleanAttribute.TRUE);
                                       }

                                       return BooleanAttribute.TRUE;
                                    }
                                 }
                              }
                           };
                        }
                     }
                  }
               }
            } else {
               es = expression.getExpressions();
               if (es.size() != 2) {
                  throw new EvaluationPlanException("Map function requires two parameters");
               } else if (!(es.get(0) instanceof Function)) {
                  throw new EvaluationPlanException("First parameter of bag function must be Function element");
               } else {
                  fId = ((Function)es.get(0)).getFunctionId();
                  exprs = new ArrayList();
                  ae1 = config.getExpressionRegistry().parse((Expression)es.get(1), variables, config);
                  wrap1 = new ExpressionBagWrapper(ae1);
                  exprs.add(wrap1);
                  if (!ae1.getType().isBag()) {
                     throw new EvaluationPlanException("Last parameter of map bag function must be bag type");
                  } else {
                     Apply apply = new Apply(fId, exprs);
                     final AttributeEvaluator ae = config.getExpressionRegistry().parse(apply, variables, config);
                     final Type t = ae.getType();
                     if (t.isBag()) {
                        throw new EvaluationPlanException("Bag function must evaluate to scalar return type");
                     } else {
                        Type bagt = new Type(t, true);
                        return new AttributeEvaluatorWrapper(bagt) {
                           public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                              wrap1.prime(context);
                              Bag result = new GenericBag(t);

                              while(wrap1.hasNext(context)) {
                                 wrap1.shiftNext(context);
                                 result.add(ae.evaluate(context));
                              }

                              if (context.isDebugEnabled()) {
                                 HigherOrderBagFunctionFactory.this.debugEval(context, HigherOrderBagFunctionFactory.this.map, (Bag)result);
                              }

                              return result;
                           }
                        };
                     }
                  }
               }
            }
         }
      } else {
         return null;
      }
   }

   private void debugEval(EvaluationCtx context, URI id, Bag result, Bag... params) {
      StringBuffer sb = new StringBuffer();
      sb.append("Evaluate ");
      sb.append(id);
      sb.append('(');
      if (params != null) {
         int l = params.length;

         for(int i = 0; i < l; ++i) {
            sb.append(params[i]);
            if (i + 1 < l) {
               sb.append(',');
            }
         }
      }

      sb.append(") -> ");
      sb.append(result);
      context.debug(sb.toString());
   }

   private void debugEval(EvaluationCtx context, URI id, IndeterminateEvaluationException result, Bag... params) {
      StringBuffer sb = new StringBuffer();
      sb.append("Evaluate ");
      sb.append(id);
      sb.append('(');
      if (params != null) {
         int l = params.length;

         for(int i = 0; i < l; ++i) {
            sb.append(params[i]);
            if (i + 1 < l) {
               sb.append(',');
            }
         }
      }

      sb.append(") -> indeterminate: ");
      sb.append(result.getMessage());
      context.debug(sb.toString());
   }

   private static class ExpressionBagWrapper extends AttributeEvaluatorWrapper implements Expression {
      private AttributeEvaluator inner;

      public ExpressionBagWrapper(AttributeEvaluator inner) throws URISyntaxException {
         super(new Type(inner.getType(), false));
         this.inner = inner;
      }

      public AttributeValue evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
         Map evaluationData = context.getEvaluationData(this);
         return (AttributeValue)evaluationData.get("attributeValueKey");
      }

      public boolean hasNext(EvaluationCtx context) throws IndeterminateEvaluationException {
         return this.getIterator(context).hasNext();
      }

      public void shiftNext(EvaluationCtx context) throws IndeterminateEvaluationException {
         Map evaluationData = context.getEvaluationData(this);
         AttributeValue av = (AttributeValue)this.getIterator(context).next();
         evaluationData.put("attributeValueKey", av);
      }

      private Iterator getIterator(EvaluationCtx context) throws IndeterminateEvaluationException {
         Map evaluationData = context.getEvaluationData(this);
         return (Iterator)evaluationData.get("iteratorKey");
      }

      public void prime(EvaluationCtx context) throws IndeterminateEvaluationException {
         Bag bag = this.inner.evaluateToBag(context);
         Iterator itr = bag.iterator();
         Map evaluationData = context.getEvaluationData(this);
         evaluationData.put("bagKey", bag);
         evaluationData.put("iteratorKey", itr);
      }

      public void reset(EvaluationCtx context) throws IndeterminateEvaluationException {
         Map evaluationData = context.getEvaluationData(this);
         Bag bag = (Bag)evaluationData.get("bagKey");
         Iterator itr = bag.iterator();
         evaluationData.put("iteratorKey", itr);
      }
   }

   private static class ExpressionWrapper extends AttributeEvaluatorWrapper implements Expression {
      private AttributeEvaluator inner;

      public ExpressionWrapper(AttributeEvaluator inner) throws URISyntaxException {
         super(inner.getType());
         this.inner = inner;
      }

      public AttributeValue evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
         Map evaluationData = context.getEvaluationData(this);
         return (AttributeValue)evaluationData.get("attributeValueKey");
      }

      public void prime(EvaluationCtx context) throws IndeterminateEvaluationException {
         Map evaluationData = context.getEvaluationData(this);
         AttributeValue av = this.inner.evaluate(context);
         evaluationData.put("attributeValueKey", av);
      }
   }
}
