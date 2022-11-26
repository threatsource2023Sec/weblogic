package com.bea.security.xacml.function.standard;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeValue;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.BooleanAttribute;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.BooleanAttributeEvaluatorBase;
import com.bea.security.xacml.function.SimpleFunctionFactoryBase;
import com.bea.security.xacml.function.SimpleFunctionLibraryBase;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class EqualityFunctionLibrary extends SimpleFunctionLibraryBase {
   public EqualityFunctionLibrary() throws URISyntaxException {
      try {
         Collection types = Type.getScalarTypes();
         Iterator var2 = types.iterator();

         while(var2.hasNext()) {
            Type t = (Type)var2.next();
            if (!Type.OBJECT.equals(t)) {
               final URI equal = new URI((t.isCustom() ? "urn:bea:xacml:2.0:function:" : "urn:oasis:names:tc:xacml:1.0:function:") + t.getShortName() + "-equal");
               this.register(equal, new SimpleFunctionFactoryBase(Type.BOOLEAN, new Type[]{t, t}) {
                  protected AttributeEvaluator getFunctionImplementation(List arguments) {
                     final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
                     final AttributeEvaluator param2 = (AttributeEvaluator)arguments.get(1);
                     return new BooleanAttributeEvaluatorBase() {
                        public BooleanAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                           AttributeValue p1 = param1.evaluate(context);
                           AttributeValue p2 = param2.evaluate(context);
                           BooleanAttribute result = p1.equals(p2) ? BooleanAttribute.TRUE : BooleanAttribute.FALSE;
                           if (context.isDebugEnabled()) {
                              EqualityFunctionLibrary.this.debugEval(context, equal, result, new Bag[]{p1, p2});
                           }

                           return result;
                        }
                     };
                  }
               });
            }
         }

      } catch (java.net.URISyntaxException var5) {
         throw new URISyntaxException(var5);
      }
   }
}
