package com.bea.security.xacml.function.standard;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AnyURIAttribute;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.common.security.xacml.attr.StringAttributeBag;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.AnyURIAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.StringAttributeEvaluatorBase;
import com.bea.security.xacml.function.MultipleArgumentType;
import com.bea.security.xacml.function.SimpleFunctionFactoryBase;
import com.bea.security.xacml.function.SimpleFunctionLibraryBase;
import java.util.Iterator;
import java.util.List;

public class StringFunctionLibrary extends SimpleFunctionLibraryBase {
   public StringFunctionLibrary() throws URISyntaxException {
      try {
         final URI sc = new URI("urn:oasis:names:tc:xacml:2.0:function:string-concatenate");
         final URI usc = new URI("urn:oasis:names:tc:xacml:2.0:function:url-string-concatenate");
         this.register(sc, new SimpleFunctionFactoryBase(Type.STRING, new Type[]{new MultipleArgumentType(Type.STRING, 2)}) {
            protected AttributeEvaluator getFunctionImplementation(final List arguments) {
               return new StringAttributeEvaluatorBase() {
                  public StringAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     StringBuffer sb = new StringBuffer();
                     if (context.isDebugEnabled()) {
                        Bag a1 = new StringAttributeBag();
                        Iterator var8 = arguments.iterator();

                        while(var8.hasNext()) {
                           AttributeEvaluator aex = (AttributeEvaluator)var8.next();
                           StringAttribute s = (StringAttribute)aex.evaluate(context);
                           a1.add(s);
                           sb.append(s.getValue());
                        }

                        StringAttribute result = new StringAttribute(sb.toString());
                        if (context.isDebugEnabled()) {
                           StringFunctionLibrary.this.debugEval(context, sc, result, new Bag[]{a1});
                        }

                        return result;
                     } else {
                        Iterator var3 = arguments.iterator();

                        while(var3.hasNext()) {
                           AttributeEvaluator ae = (AttributeEvaluator)var3.next();
                           sb.append(((StringAttribute)ae.evaluate(context)).getValue());
                        }

                        return new StringAttribute(sb.toString());
                     }
                  }
               };
            }
         });
         this.register(usc, new SimpleFunctionFactoryBase(Type.ANY_URI, new Type[]{Type.ANY_URI, new MultipleArgumentType(Type.STRING, 1)}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               final List args = arguments.subList(1, arguments.size());
               return new AnyURIAttributeEvaluatorBase() {
                  public AnyURIAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     try {
                        AnyURIAttribute p1 = (AnyURIAttribute)param1.evaluate(context);
                        StringBuffer sb = new StringBuffer(p1.getValue().toString());
                        if (context.isDebugEnabled()) {
                           Bag a2 = new StringAttributeBag();
                           Iterator var10 = args.iterator();

                           while(var10.hasNext()) {
                              AttributeEvaluator aex = (AttributeEvaluator)var10.next();
                              StringAttribute s = (StringAttribute)aex.evaluate(context);
                              a2.add(s);
                              sb.append(s.getValue());
                           }

                           AnyURIAttribute result = new AnyURIAttribute(new URI(sb.toString()));
                           if (context.isDebugEnabled()) {
                              StringFunctionLibrary.this.debugEval(context, usc, result, new Bag[]{p1, a2});
                           }

                           return result;
                        } else {
                           Iterator var4 = args.iterator();

                           while(var4.hasNext()) {
                              AttributeEvaluator ae = (AttributeEvaluator)var4.next();
                              sb.append(((StringAttribute)ae.evaluate(context)).getValue());
                           }

                           return new AnyURIAttribute(new URI(sb.toString()));
                        }
                     } catch (java.net.URISyntaxException var8) {
                        throw new IndeterminateEvaluationException(var8);
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
