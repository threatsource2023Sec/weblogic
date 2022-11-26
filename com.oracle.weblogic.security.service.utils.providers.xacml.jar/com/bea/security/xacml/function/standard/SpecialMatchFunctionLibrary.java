package com.bea.security.xacml.function.standard;

import com.bea.common.security.utils.Pair;
import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.BooleanAttribute;
import com.bea.common.security.xacml.attr.RFC822NameAttribute;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.common.security.xacml.attr.X500NameAttribute;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.BooleanAttributeEvaluatorBase;
import com.bea.security.xacml.function.SimpleFunctionFactoryBase;
import com.bea.security.xacml.function.SimpleFunctionLibraryBase;
import java.util.List;
import java.util.ListIterator;

public class SpecialMatchFunctionLibrary extends SimpleFunctionLibraryBase {
   public SpecialMatchFunctionLibrary() throws URISyntaxException {
      try {
         final URI xm = new URI("urn:oasis:names:tc:xacml:1.0:function:x500Name-match");
         final URI rm = new URI("urn:oasis:names:tc:xacml:1.0:function:rfc822Name-match");
         this.register(xm, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.X500_NAME, Type.X500_NAME}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     X500NameAttribute p1 = (X500NameAttribute)param1.evaluate(context);
                     X500NameAttribute p2 = (X500NameAttribute)param2.evaluate(context);
                     List c1 = p1.getComponents();
                     List c2 = p2.getComponents();
                     ListIterator l1 = c1.listIterator(c1.size());
                     ListIterator l2 = c2.listIterator(c2.size());

                     do {
                        if (!l1.hasPrevious()) {
                           if (context.isDebugEnabled()) {
                              SpecialMatchFunctionLibrary.this.debugEval(context, xm, BooleanAttribute.TRUE, new Bag[]{p1, p2});
                           }

                           return BooleanAttribute.TRUE;
                        }

                        if (!l2.hasPrevious()) {
                           if (context.isDebugEnabled()) {
                              SpecialMatchFunctionLibrary.this.debugEval(context, xm, BooleanAttribute.FALSE, new Bag[]{p1, p2});
                           }

                           return BooleanAttribute.FALSE;
                        }
                     } while(((Pair)l1.previous()).equals(l2.previous()));

                     if (context.isDebugEnabled()) {
                        SpecialMatchFunctionLibrary.this.debugEval(context, xm, BooleanAttribute.FALSE, new Bag[]{p1, p2});
                     }

                     return BooleanAttribute.FALSE;
                  }
               };
            }
         });
         this.register(rm, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{Type.STRING, Type.RFC822_NAME}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
               return new BooleanAttributeEvaluatorBase() {
                  public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                     String pattern = p1.getValue();
                     RFC822NameAttribute r = (RFC822NameAttribute)param2.evaluate(context);
                     int idx = pattern.indexOf(64);
                     BooleanAttribute result;
                     if (idx >= 0) {
                        result = r.getLocalPart().equals(pattern.substring(0, idx)) && r.getDomainPart().equalsIgnoreCase(pattern.substring(idx + 1)) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                        if (context.isDebugEnabled()) {
                           SpecialMatchFunctionLibrary.this.debugEval(context, rm, result, new Bag[]{p1, r});
                        }

                        return result;
                     } else if (pattern.startsWith(".")) {
                        String domPart = r.getDomainPart();
                        if (domPart.length() + 1 < pattern.length()) {
                           if (context.isDebugEnabled()) {
                              SpecialMatchFunctionLibrary.this.debugEval(context, rm, BooleanAttribute.FALSE, new Bag[]{p1, r});
                           }

                           return BooleanAttribute.FALSE;
                        } else {
                           BooleanAttribute resultx = !domPart.substring(domPart.length() - pattern.length() + 1).equalsIgnoreCase(pattern.substring(1)) || domPart.length() >= pattern.length() && domPart.charAt(domPart.length() - pattern.length()) != '.' ? BooleanAttribute.FALSE : BooleanAttribute.TRUE;
                           if (context.isDebugEnabled()) {
                              SpecialMatchFunctionLibrary.this.debugEval(context, rm, resultx, new Bag[]{p1, r});
                           }

                           return resultx;
                        }
                     } else {
                        result = r.getDomainPart().equalsIgnoreCase(pattern) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                        if (context.isDebugEnabled()) {
                           SpecialMatchFunctionLibrary.this.debugEval(context, rm, result, new Bag[]{p1, r});
                        }

                        return result;
                     }
                  }
               };
            }
         });
      } catch (java.net.URISyntaxException var3) {
         throw new URISyntaxException(var3);
      }
   }
}
