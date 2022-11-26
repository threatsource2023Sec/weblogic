package com.bea.security.xacml.function.standard;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.StringAttributeEvaluatorBase;
import com.bea.security.xacml.function.SimpleFunctionFactoryBase;
import com.bea.security.xacml.function.SimpleFunctionLibraryBase;
import java.util.List;

public class StringConversionFunctionLibrary extends SimpleFunctionLibraryBase {
   public StringConversionFunctionLibrary() throws URISyntaxException {
      try {
         final URI ns = new URI("urn:oasis:names:tc:xacml:1.0:function:string-normalize-space");
         final URI lc = new URI("urn:oasis:names:tc:xacml:1.0:function:string-normalize-to-lower-case");
         this.register(ns, new SimpleFunctionFactoryBase(Type.STRING, new Type[]{Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new StringAttributeEvaluatorBase() {
                  public StringAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                     StringAttribute result = new StringAttribute(p1.getValue().trim());
                     if (context.isDebugEnabled()) {
                        StringConversionFunctionLibrary.this.debugEval(context, ns, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(lc, new SimpleFunctionFactoryBase(Type.STRING, new Type[]{Type.STRING}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new StringAttributeEvaluatorBase() {
                  public StringAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     StringAttribute p1 = (StringAttribute)param1.evaluate(context);
                     StringAttribute result = new StringAttribute(p1.getValue().toLowerCase());
                     if (context.isDebugEnabled()) {
                        StringConversionFunctionLibrary.this.debugEval(context, lc, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
      } catch (java.net.URISyntaxException var3) {
         throw new URISyntaxException(var3);
      }
   }
}
