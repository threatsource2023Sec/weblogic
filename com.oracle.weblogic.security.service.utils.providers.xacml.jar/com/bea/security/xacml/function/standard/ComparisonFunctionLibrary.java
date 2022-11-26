package com.bea.security.xacml.function.standard;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeValue;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.BooleanAttribute;
import com.bea.common.security.xacml.attr.TimeAttribute;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.BooleanAttributeEvaluatorBase;
import com.bea.security.xacml.function.SimpleFunctionFactoryBase;
import com.bea.security.xacml.function.SimpleFunctionLibraryBase;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ComparisonFunctionLibrary extends SimpleFunctionLibraryBase {
   public ComparisonFunctionLibrary() throws URISyntaxException {
      try {
         final URI tir = new URI("urn:oasis:names:tc:xacml:1.0:function:time-in-range");
         this.register(tir, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.TIME, Type.TIME, Type.TIME}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               final AttributeEvaluator param3 = (AttributeEvaluator)arguments.get(2);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     TimeAttribute ta1 = (TimeAttribute)param1.evaluate(context);
                     TimeAttribute ta2 = (TimeAttribute)param2.evaluate(context);
                     TimeAttribute ta3 = (TimeAttribute)param3.evaluate(context);
                     BooleanAttribute result;
                     if (ta1.compareToNormalize(ta2) < 0) {
                        result = ta1.compareToNormalize(ta3) <= 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                        if (context.isDebugEnabled()) {
                           ComparisonFunctionLibrary.this.debugEval(context, tir, result, new Bag[]{ta1, ta2, ta3});
                        }

                        return result;
                     } else {
                        result = ta1.compareToNormalize(ta3) <= 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                        if (context.isDebugEnabled()) {
                           ComparisonFunctionLibrary.this.debugEval(context, tir, result, new Bag[]{ta1, ta2, ta3});
                        }

                        return result;
                     }
                  }
               };
            }
         });
         Collection types = Type.getScalarTypes();
         Iterator var3 = types.iterator();

         while(var3.hasNext()) {
            Type t = (Type)var3.next();
            if (!Type.OBJECT.equals(t)) {
               final URI gt = new URI((t.isCustom() ? "urn:bea:xacml:2.0:function:" : "urn:oasis:names:tc:xacml:1.0:function:") + t.getShortName() + "-greater-than");
               final URI gtoe = new URI((t.isCustom() ? "urn:bea:xacml:2.0:function:" : "urn:oasis:names:tc:xacml:1.0:function:") + t.getShortName() + "-greater-than-or-equal");
               final URI lt = new URI((t.isCustom() ? "urn:bea:xacml:2.0:function:" : "urn:oasis:names:tc:xacml:1.0:function:") + t.getShortName() + "-less-than");
               final URI ltoe = new URI((t.isCustom() ? "urn:bea:xacml:2.0:function:" : "urn:oasis:names:tc:xacml:1.0:function:") + t.getShortName() + "-less-than-or-equal");
               this.register(gt, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{t, t}) {
                  protected AttributeEvaluator getFunctionImplementation(List arguments) {
                     final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
                     final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
                     return new BooleanAttributeEvaluatorBase() {
                        public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           AttributeValue p1 = param1.evaluate(context);
                           AttributeValue p2 = param2.evaluate(context);

                           try {
                              BooleanAttribute result = p1.compareTo(p2) > 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                              if (context.isDebugEnabled()) {
                                 ComparisonFunctionLibrary.this.debugEval(context, gt, result, new Bag[]{p1, p2});
                              }

                              return result;
                           } catch (RuntimeException var6) {
                              IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
                              if (context.isDebugEnabled()) {
                                 ComparisonFunctionLibrary.this.debugEval(context, gt, th, new Bag[]{p1, p2});
                              }

                              throw th;
                           }
                        }
                     };
                  }
               });
               this.register(gtoe, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{t, t}) {
                  protected AttributeEvaluator getFunctionImplementation(List arguments) {
                     final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
                     final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
                     return new BooleanAttributeEvaluatorBase() {
                        public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           AttributeValue p1 = param1.evaluate(context);
                           AttributeValue p2 = param2.evaluate(context);

                           try {
                              BooleanAttribute result = p1.compareTo(p2) >= 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                              if (context.isDebugEnabled()) {
                                 ComparisonFunctionLibrary.this.debugEval(context, gtoe, result, new Bag[]{p1, p2});
                              }

                              return result;
                           } catch (RuntimeException var6) {
                              IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
                              if (context.isDebugEnabled()) {
                                 ComparisonFunctionLibrary.this.debugEval(context, gtoe, th, new Bag[]{p1, p2});
                              }

                              throw th;
                           }
                        }
                     };
                  }
               });
               this.register(lt, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{t, t}) {
                  protected AttributeEvaluator getFunctionImplementation(List arguments) {
                     final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
                     final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
                     return new BooleanAttributeEvaluatorBase() {
                        public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           AttributeValue p1 = param1.evaluate(context);
                           AttributeValue p2 = param2.evaluate(context);

                           try {
                              BooleanAttribute result = p1.compareTo(p2) < 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                              if (context.isDebugEnabled()) {
                                 ComparisonFunctionLibrary.this.debugEval(context, lt, result, new Bag[]{p1, p2});
                              }

                              return result;
                           } catch (RuntimeException var6) {
                              IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
                              if (context.isDebugEnabled()) {
                                 ComparisonFunctionLibrary.this.debugEval(context, lt, th, new Bag[]{p1, p2});
                              }

                              throw th;
                           }
                        }
                     };
                  }
               });
               this.register(ltoe, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{t, t}) {
                  protected AttributeEvaluator getFunctionImplementation(List arguments) {
                     final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
                     final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
                     return new BooleanAttributeEvaluatorBase() {
                        public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           AttributeValue p1 = param1.evaluate(context);
                           AttributeValue p2 = param2.evaluate(context);

                           try {
                              BooleanAttribute result = p1.compareTo(p2) <= 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                              if (context.isDebugEnabled()) {
                                 ComparisonFunctionLibrary.this.debugEval(context, ltoe, result, new Bag[]{p1, p2});
                              }

                              return result;
                           } catch (RuntimeException var6) {
                              IndeterminateEvaluationException th = new IndeterminateEvaluationException(var6);
                              if (context.isDebugEnabled()) {
                                 ComparisonFunctionLibrary.this.debugEval(context, ltoe, th, new Bag[]{p1, p2});
                              }

                              throw th;
                           }
                        }
                     };
                  }
               });
            }
         }

      } catch (java.net.URISyntaxException var9) {
         throw new URISyntaxException(var9);
      }
   }
}
