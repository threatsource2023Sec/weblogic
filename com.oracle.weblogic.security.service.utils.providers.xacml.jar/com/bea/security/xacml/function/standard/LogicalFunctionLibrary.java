package com.bea.security.xacml.function.standard;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.BooleanAttribute;
import com.bea.common.security.xacml.attr.BooleanAttributeBag;
import com.bea.common.security.xacml.attr.IntegerAttribute;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.BooleanAttributeEvaluatorBase;
import com.bea.security.xacml.function.MultipleArgumentType;
import com.bea.security.xacml.function.SimpleFunctionFactoryBase;
import com.bea.security.xacml.function.SimpleFunctionLibraryBase;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LogicalFunctionLibrary extends SimpleFunctionLibraryBase {
   public LogicalFunctionLibrary() throws URISyntaxException {
      try {
         final URI or = new URI("urn:oasis:names:tc:xacml:1.0:function:or");
         final URI and = new URI("urn:oasis:names:tc:xacml:1.0:function:and");
         final URI nof = new URI("urn:oasis:names:tc:xacml:1.0:function:n-of");
         final URI not = new URI("urn:oasis:names:tc:xacml:1.0:function:not");
         this.register(or, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{new MultipleArgumentType(Type.BOOLEAN, 0)}) {
            protected AttributeEvaluator getFunctionImplementation(final List arguments) {
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     List indeterminates = null;
                     Bag a1 = new BooleanAttributeBag();
                     BooleanAttribute result = BooleanAttribute.FALSE;

                     for(int i = 0; i < arguments.size(); ++i) {
                        AttributeEvaluator a = (AttributeEvaluator)arguments.get(i);

                        try {
                           BooleanAttribute b = (BooleanAttribute)a.evaluate(context);
                           a1.add(b);
                           if (b.getBooleanValue()) {
                              result = BooleanAttribute.TRUE;
                              break;
                           }
                        } catch (IndeterminateEvaluationException var8) {
                           if (indeterminates == null) {
                              indeterminates = new ArrayList();
                           }

                           indeterminates.add(var8);
                           if (context.isDebugEnabled()) {
                              LogicalFunctionLibrary.this.debugEval(context, or, var8, new Bag[]{a1});
                              if (i + 1 < arguments.size()) {
                                 context.debug("Evaluating the next operand despite the IndeterminateEvaluationException.");
                              }
                           }
                        }
                     }

                     if (!result.getBooleanValue() && indeterminates != null && indeterminates.size() > 0) {
                        if (context.isDebugEnabled()) {
                           context.debug("The conditional \"|\"(OR) evaluation resulted in one or more exceptions of type IndeterminateEvaluationException.  Rethrowing the first one.");
                        }

                        throw (IndeterminateEvaluationException)indeterminates.get(0);
                     } else {
                        if (context.isDebugEnabled()) {
                           LogicalFunctionLibrary.this.debugEval(context, or, result, new Bag[]{a1});
                        }

                        return result;
                     }
                  }
               };
            }
         });
         this.register(and, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{new MultipleArgumentType(Type.BOOLEAN, 0)}) {
            protected AttributeEvaluator getFunctionImplementation(final List arguments) {
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     if (context.isDebugEnabled()) {
                        Bag a1 = new BooleanAttributeBag();
                        Iterator var7 = arguments.iterator();

                        BooleanAttribute b;
                        do {
                           if (!var7.hasNext()) {
                              LogicalFunctionLibrary.this.debugEval(context, and, BooleanAttribute.TRUE, new Bag[]{a1});
                              return BooleanAttribute.TRUE;
                           }

                           AttributeEvaluator ax = (AttributeEvaluator)var7.next();
                           b = (BooleanAttribute)ax.evaluate(context);
                           a1.add(b);
                        } while(b.getBooleanValue());

                        LogicalFunctionLibrary.this.debugEval(context, and, BooleanAttribute.FALSE, new Bag[]{a1});
                        return BooleanAttribute.FALSE;
                     } else {
                        Iterator var2 = arguments.iterator();

                        AttributeEvaluator a;
                        do {
                           if (!var2.hasNext()) {
                              return BooleanAttribute.TRUE;
                           }

                           a = (AttributeEvaluator)var2.next();
                        } while(((BooleanAttribute)a.evaluate(context)).getBooleanValue());

                        return BooleanAttribute.FALSE;
                     }
                  }
               };
            }
         });
         this.register(nof, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.INTEGER, new MultipleArgumentType(Type.BOOLEAN, 0)}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final List args = arguments.subList(1, arguments.size());
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     IntegerAttribute p1 = (IntegerAttribute)param1.evaluate(context);
                     int n = p1.getIntValue();
                     if (n < 0) {
                        IndeterminateEvaluationException th = new IndeterminateEvaluationException("N-of count must not be negative");
                        if (context.isDebugEnabled()) {
                           LogicalFunctionLibrary.this.debugEval(context, nof, th, new Bag[]{p1});
                        }

                        throw th;
                     } else if (context.isDebugEnabled()) {
                        Bag a1 = new BooleanAttributeBag();

                        for(int ix = 0; n != 0 && ix < args.size(); ++ix) {
                           if (args.size() - ix < n) {
                              LogicalFunctionLibrary.this.debugEval(context, nof, BooleanAttribute.FALSE, new Bag[]{p1, a1});
                              return BooleanAttribute.FALSE;
                           }

                           BooleanAttribute b = (BooleanAttribute)((AttributeEvaluator)args.get(ix)).evaluate(context);
                           a1.add(b);
                           if (b.getBooleanValue()) {
                              --n;
                           }
                        }

                        BooleanAttribute result = n == 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                        LogicalFunctionLibrary.this.debugEval(context, nof, result, new Bag[]{p1, a1});
                        return result;
                     } else {
                        for(int i = 0; n != 0 && i < args.size(); ++i) {
                           if (args.size() - i < n) {
                              return BooleanAttribute.FALSE;
                           }

                           if (((BooleanAttribute)((AttributeEvaluator)args.get(i)).evaluate(context)).getBooleanValue()) {
                              --n;
                           }
                        }

                        return n == 0 ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                     }
                  }
               };
            }
         });
         this.register(not, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.BOOLEAN}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     BooleanAttribute p1 = (BooleanAttribute)param1.evaluate(context);
                     BooleanAttribute result = p1.getBooleanValue() ? BooleanAttribute.FALSE : BooleanAttribute.TRUE;
                     if (context.isDebugEnabled()) {
                        LogicalFunctionLibrary.this.debugEval(context, not, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
      } catch (java.net.URISyntaxException var5) {
         throw new URISyntaxException(var5);
      }
   }
}
