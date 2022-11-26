package com.bea.security.xacml.function.standard;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeValue;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.BooleanAttribute;
import com.bea.common.security.xacml.attr.GenericBag;
import com.bea.common.security.xacml.attr.IntegerAttribute;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.AttributeEvaluatorWrapper;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.BooleanAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.Constant;
import com.bea.security.xacml.attr.evaluator.ConstantBag;
import com.bea.security.xacml.attr.evaluator.IntegerAttributeEvaluatorBase;
import com.bea.security.xacml.function.MultipleArgumentType;
import com.bea.security.xacml.function.SimpleFunctionFactoryBase;
import com.bea.security.xacml.function.SimpleFunctionLibraryBase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BagFunctionLibrary extends SimpleFunctionLibraryBase {
   public BagFunctionLibrary() throws URISyntaxException {
      try {
         Collection types = Type.getScalarTypes();
         Iterator var2 = types.iterator();

         while(var2.hasNext()) {
            final Type t = (Type)var2.next();
            final URI oao = new URI((t.isCustom() ? "urn:bea:xacml:2.0:function:" : "urn:oasis:names:tc:xacml:1.0:function:") + t.getShortName() + "-one-and-only");
            final URI bs = new URI((t.isCustom() ? "urn:bea:xacml:2.0:function:" : "urn:oasis:names:tc:xacml:1.0:function:") + t.getShortName() + "-bag-size");
            final URI ii = new URI((t.isCustom() ? "urn:bea:xacml:2.0:function:" : "urn:oasis:names:tc:xacml:1.0:function:") + t.getShortName() + "-is-in");
            final URI b = new URI((t.isCustom() ? "urn:bea:xacml:2.0:function:" : "urn:oasis:names:tc:xacml:1.0:function:") + t.getShortName() + "-bag");
            this.register(oao, new SimpleFunctionFactoryBase(t, new Type[]{new Type(t, true)}) {
               protected AttributeEvaluator getFunctionImplementation(List arguments) {
                  final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
                  return new AttributeEvaluatorWrapper(t) {
                     public AttributeValue evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        Bag b = param1.evaluateToBag(context);
                        if (b.size() != 1) {
                           IndeterminateEvaluationException th = new IndeterminateEvaluationException("Bags must be size 1 to be treated as scalars");
                           if (context.isDebugEnabled()) {
                              BagFunctionLibrary.this.debugEval(context, oao, th, new Bag[]{b});
                           }

                           throw th;
                        } else {
                           AttributeValue result = (AttributeValue)b.iterator().next();
                           if (context.isDebugEnabled()) {
                              BagFunctionLibrary.this.debugEval(context, oao, result, new Bag[]{b});
                           }

                           return result;
                        }
                     }
                  };
               }
            });
            this.register(bs, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{new Type(t, true)}) {
               protected AttributeEvaluator getFunctionImplementation(List arguments) {
                  final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
                  return new IntegerAttributeEvaluatorBase() {
                     public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        Bag b = param1.evaluateToBag(context);
                        IntegerAttribute result = new IntegerAttribute(b.size());
                        if (context.isDebugEnabled()) {
                           BagFunctionLibrary.this.debugEval(context, bs, result, new Bag[]{b});
                        }

                        return result;
                     }
                  };
               }
            });
            this.register(ii, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{t, new Type(t, true)}) {
               protected AttributeEvaluator getFunctionImplementation(List arguments) {
                  final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
                  final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
                  return new BooleanAttributeEvaluatorBase() {
                     public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                        AttributeValue p1 = param1.evaluate(context);
                        Bag p2 = param2.evaluateToBag(context);
                        BooleanAttribute result = p2.contains(p1) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                        if (context.isDebugEnabled()) {
                           BagFunctionLibrary.this.debugEval(context, ii, result, new Bag[]{p1, p2});
                        }

                        return result;
                     }
                  };
               }
            });
            this.register(b, new SimpleFunctionFactoryBase(new Type(t, true), new Type[]{new MultipleArgumentType(t, 0)}) {
               protected AttributeEvaluator getFunctionImplementation(final List arguments) {
                  boolean isConstantBag = true;
                  List argsC = new ArrayList();
                  Iterator var4 = arguments.iterator();

                  while(var4.hasNext()) {
                     AttributeEvaluator ae = (AttributeEvaluator)var4.next();
                     if (!(ae instanceof Constant)) {
                        isConstantBag = false;
                        break;
                     }

                     argsC.add(((Constant)ae).getValue());
                  }

                  return (AttributeEvaluator)(isConstantBag ? new ConstantBag(new GenericBag(t, argsC)) : new AttributeEvaluatorWrapper(new Type(t, true)) {
                     public Bag evaluateToBag(EvaluationCtx context) throws IndeterminateEvaluationException {
                        Bag result = new GenericBag(t);
                        Iterator var3 = arguments.iterator();

                        while(var3.hasNext()) {
                           AttributeEvaluator ae = (AttributeEvaluator)var3.next();
                           result.add(ae.evaluate(context));
                        }

                        if (context.isDebugEnabled()) {
                           BagFunctionLibrary.this.debugEval(context, b, result, new Bag[0]);
                        }

                        return result;
                     }
                  });
               }
            });
         }

      } catch (java.net.URISyntaxException var8) {
         throw new URISyntaxException(var8);
      }
   }
}
