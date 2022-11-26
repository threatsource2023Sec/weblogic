package com.bea.security.xacml.function.standard;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.DoubleAttribute;
import com.bea.common.security.xacml.attr.DoubleAttributeBag;
import com.bea.common.security.xacml.attr.IntegerAttribute;
import com.bea.common.security.xacml.attr.IntegerAttributeBag;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.DoubleAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.IntegerAttributeEvaluatorBase;
import com.bea.security.xacml.function.MultipleArgumentType;
import com.bea.security.xacml.function.SimpleFunctionFactoryBase;
import com.bea.security.xacml.function.SimpleFunctionLibraryBase;
import java.util.Iterator;
import java.util.List;

public class ArithmeticFunctionLibrary extends SimpleFunctionLibraryBase {
   public ArithmeticFunctionLibrary() throws URISyntaxException {
      try {
         final URI ia = new URI("urn:oasis:names:tc:xacml:1.0:function:integer-add");
         final URI da = new URI("urn:oasis:names:tc:xacml:1.0:function:double-add");
         final URI is = new URI("urn:oasis:names:tc:xacml:1.0:function:integer-subtract");
         final URI ds = new URI("urn:oasis:names:tc:xacml:1.0:function:double-subtract");
         final URI im = new URI("urn:oasis:names:tc:xacml:1.0:function:integer-multiply");
         final URI dm = new URI("urn:oasis:names:tc:xacml:1.0:function:double-multiply");
         final URI id = new URI("urn:oasis:names:tc:xacml:1.0:function:integer-divide");
         final URI dd = new URI("urn:oasis:names:tc:xacml:1.0:function:double-divide");
         final URI imod = new URI("urn:oasis:names:tc:xacml:1.0:function:integer-mod");
         final URI iabs = new URI("urn:oasis:names:tc:xacml:1.0:function:integer-abs");
         final URI dabs = new URI("urn:oasis:names:tc:xacml:1.0:function:double-abs");
         final URI round = new URI("urn:oasis:names:tc:xacml:1.0:function:round");
         final URI floor = new URI("urn:oasis:names:tc:xacml:1.0:function:floor");
         this.register(ia, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{new MultipleArgumentType(Type.INTEGER, 2)}) {
            protected AttributeEvaluator getFunctionImplementation(final List arguments) {
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     Bag a1 = null;
                     int runningTotal = 0;
                     Iterator var4;
                     AttributeEvaluator ai;
                     if (context.isDebugEnabled()) {
                        a1 = new IntegerAttributeBag();

                        IntegerAttribute a;
                        for(var4 = arguments.iterator(); var4.hasNext(); runningTotal += a.getIntValue()) {
                           ai = (AttributeEvaluator)var4.next();
                           a = (IntegerAttribute)ai.evaluate(context);
                           a1.add(a);
                        }
                     } else {
                        for(var4 = arguments.iterator(); var4.hasNext(); runningTotal += ((IntegerAttribute)ai.evaluate(context)).getIntValue()) {
                           ai = (AttributeEvaluator)var4.next();
                        }
                     }

                     IntegerAttribute result = new IntegerAttribute(runningTotal);
                     if (context.isDebugEnabled()) {
                        ArithmeticFunctionLibrary.this.debugEval(context, ia, result, new Bag[]{a1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(da, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{new MultipleArgumentType(Type.DOUBLE, 2)}) {
            protected AttributeEvaluator getFunctionImplementation(final List arguments) {
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     Bag a1 = null;
                     double runningTotal = 0.0;
                     Iterator var5;
                     AttributeEvaluator ad;
                     if (context.isDebugEnabled()) {
                        a1 = new DoubleAttributeBag();

                        DoubleAttribute d;
                        for(var5 = arguments.iterator(); var5.hasNext(); runningTotal += d.getDoubleValue()) {
                           ad = (AttributeEvaluator)var5.next();
                           d = (DoubleAttribute)ad.evaluate(context);
                           a1.add(d);
                        }
                     } else {
                        for(var5 = arguments.iterator(); var5.hasNext(); runningTotal += ((DoubleAttribute)ad.evaluate(context)).getDoubleValue()) {
                           ad = (AttributeEvaluator)var5.next();
                        }
                     }

                     DoubleAttribute result = new DoubleAttribute(runningTotal);
                     if (context.isDebugEnabled()) {
                        ArithmeticFunctionLibrary.this.debugEval(context, da, result, new Bag[]{a1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(is, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.INTEGER, Type.INTEGER}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     IntegerAttribute p1 = (IntegerAttribute)param1.evaluate(context);
                     IntegerAttribute p2 = (IntegerAttribute)param2.evaluate(context);
                     IntegerAttribute result = new IntegerAttribute(p1.getIntValue() - p2.getIntValue());
                     if (context.isDebugEnabled()) {
                        ArithmeticFunctionLibrary.this.debugEval(context, is, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(ds, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE, Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute p2 = (DoubleAttribute)param2.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(p1.getDoubleValue() - p2.getDoubleValue());
                     if (context.isDebugEnabled()) {
                        ArithmeticFunctionLibrary.this.debugEval(context, ds, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(im, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.INTEGER, Type.INTEGER}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     IntegerAttribute p1 = (IntegerAttribute)param1.evaluate(context);
                     IntegerAttribute p2 = (IntegerAttribute)param2.evaluate(context);
                     IntegerAttribute result = new IntegerAttribute(p1.getIntValue() * p2.getIntValue());
                     if (context.isDebugEnabled()) {
                        ArithmeticFunctionLibrary.this.debugEval(context, im, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(dm, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE, Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute p2 = (DoubleAttribute)param2.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(p1.getDoubleValue() * p2.getDoubleValue());
                     if (context.isDebugEnabled()) {
                        ArithmeticFunctionLibrary.this.debugEval(context, dm, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(id, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.INTEGER, Type.INTEGER}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     IntegerAttribute p1 = (IntegerAttribute)param1.evaluate(context);
                     IntegerAttribute p2 = (IntegerAttribute)param2.evaluate(context);
                     IntegerAttribute result = new IntegerAttribute(p1.getIntValue() / p2.getIntValue());
                     if (context.isDebugEnabled()) {
                        ArithmeticFunctionLibrary.this.debugEval(context, id, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(dd, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE, Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute p2 = (DoubleAttribute)param2.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(p1.getDoubleValue() / p2.getDoubleValue());
                     if (context.isDebugEnabled()) {
                        ArithmeticFunctionLibrary.this.debugEval(context, dd, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(imod, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.INTEGER, Type.INTEGER}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     IntegerAttribute p1 = (IntegerAttribute)param1.evaluate(context);
                     IntegerAttribute p2 = (IntegerAttribute)param2.evaluate(context);
                     IntegerAttribute result = new IntegerAttribute(p1.getIntValue() % p2.getIntValue());
                     if (context.isDebugEnabled()) {
                        ArithmeticFunctionLibrary.this.debugEval(context, imod, result, new Bag[]{p1, p2});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(iabs, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.INTEGER}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     IntegerAttribute p1 = (IntegerAttribute)param1.evaluate(context);
                     IntegerAttribute result = new IntegerAttribute(Math.abs(p1.getIntValue()));
                     if (context.isDebugEnabled()) {
                        ArithmeticFunctionLibrary.this.debugEval(context, iabs, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(dabs, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.abs(p1.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        ArithmeticFunctionLibrary.this.debugEval(context, dabs, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(round, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute((double)Math.round(p1.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        ArithmeticFunctionLibrary.this.debugEval(context, round, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(floor, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute(Math.floor(p1.getDoubleValue()));
                     if (context.isDebugEnabled()) {
                        ArithmeticFunctionLibrary.this.debugEval(context, floor, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
      } catch (java.net.URISyntaxException var14) {
         throw new URISyntaxException(var14);
      }
   }
}
