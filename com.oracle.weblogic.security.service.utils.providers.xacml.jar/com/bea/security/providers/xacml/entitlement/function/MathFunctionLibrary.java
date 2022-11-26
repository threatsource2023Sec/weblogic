package com.bea.security.providers.xacml.entitlement.function;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.DoubleAttribute;
import com.bea.security.utils.random.SecureRandomData;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.DoubleAttributeEvaluatorBase;
import com.bea.security.xacml.function.SimpleFunctionFactoryBase;
import com.bea.security.xacml.function.SimpleFunctionLibraryBase;
import java.util.List;

public class MathFunctionLibrary extends SimpleFunctionLibraryBase {
   public MathFunctionLibrary() throws URISyntaxException {
      try {
         final URI acos = new URI("urn:bea:xacml:2.0:function:acos");
         final URI asin = new URI("urn:bea:xacml:2.0:function:asin");
         final URI atan = new URI("urn:bea:xacml:2.0:function:atan");
         final URI atan2 = new URI("urn:bea:xacml:2.0:function:atan2");
         final URI ceil = new URI("urn:bea:xacml:2.0:function:ceil");
         final URI cos = new URI("urn:bea:xacml:2.0:function:cos");
         final URI exp = new URI("urn:bea:xacml:2.0:function:exp");
         final URI ieeeremainder = new URI("urn:bea:xacml:2.0:function:ieee-remainder");
         final URI log = new URI("urn:bea:xacml:2.0:function:log");
         final URI maximum = new URI("urn:bea:xacml:2.0:function:maximum");
         final URI minimum = new URI("urn:bea:xacml:2.0:function:minimum");
         final URI pow = new URI("urn:bea:xacml:2.0:function:pow");
         final URI randomnumber = new URI("urn:bea:xacml:2.0:function:random-number");
         final URI rint = new URI("urn:bea:xacml:2.0:function:rint");
         final URI sqrt = new URI("urn:bea:xacml:2.0:function:sqrt");
         final URI tan = new URI("urn:bea:xacml:2.0:function:tan");
         final URI todegrees = new URI("urn:bea:xacml:2.0:function:to-degrees");
         final URI toradians = new URI("urn:bea:xacml:2.0:function:to-radians");
         this.register(acos, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.acos(p1.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, acos, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(asin, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.asin(p1.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, asin, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(atan, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.atan(p1.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, atan, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(atan2, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE, Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute p2 = (DoubleAttribute)param2.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.atan2(p1.getDoubleValue(), p2.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, atan2, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(ceil, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.ceil(p1.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, ceil, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(cos, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.cos(p1.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, cos, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(exp, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.exp(p1.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, exp, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(ieeeremainder, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE, Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute p2 = (DoubleAttribute)param2.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.IEEEremainder(p1.getDoubleValue(), p2.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, ieeeremainder, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(log, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.log(p1.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, log, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(maximum, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE, Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute p2 = (DoubleAttribute)param2.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.max(p1.getDoubleValue(), p2.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, maximum, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(minimum, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE, Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute p2 = (DoubleAttribute)param2.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.min(p1.getDoubleValue(), p2.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, minimum, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(pow, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE, Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute p2 = (DoubleAttribute)param2.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.pow(p1.getDoubleValue(), p2.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, pow, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(randomnumber, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE, Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute p2 = (DoubleAttribute)param2.evaluate(context);
                     double d1 = p1.getDoubleValue();
                     double d2 = p2.getDoubleValue();
                     DoubleAttribute result = new DoubleAttribute(SecureRandomData.getInstance().getRandomDouble() * (d2 - d1) + d1);
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, randomnumber, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(rint, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.rint(p1.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, rint, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(sqrt, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.sqrt(p1.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, sqrt, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(tan, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.tan(p1.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, tan, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(todegrees, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.toDegrees(p1.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, todegrees, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(toradians, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.toRadians(p1.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        MathFunctionLibrary.this.debugEval(context, toradians, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
      } catch (java.net.URISyntaxException var19) {
         throw new URISyntaxException(var19);
      }
   }
}
