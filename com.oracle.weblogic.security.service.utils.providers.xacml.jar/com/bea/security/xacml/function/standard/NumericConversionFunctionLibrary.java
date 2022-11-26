package com.bea.security.xacml.function.standard;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.Bag;
import com.bea.common.security.xacml.attr.DoubleAttribute;
import com.bea.common.security.xacml.attr.IntegerAttribute;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.EvaluationCtx;
import com.bea.security.xacml.IndeterminateEvaluationException;
import com.bea.security.xacml.attr.evaluator.DoubleAttributeEvaluatorBase;
import com.bea.security.xacml.attr.evaluator.IntegerAttributeEvaluatorBase;
import com.bea.security.xacml.function.SimpleFunctionFactoryBase;
import com.bea.security.xacml.function.SimpleFunctionLibraryBase;
import java.util.List;

public class NumericConversionFunctionLibrary extends SimpleFunctionLibraryBase {
   public NumericConversionFunctionLibrary() throws URISyntaxException {
      try {
         final URI dit = new URI("urn:oasis:names:tc:xacml:1.0:function:double-to-integer");
         final URI itd = new URI("urn:oasis:names:tc:xacml:1.0:function:integer-to-double");
         this.register(dit, new SimpleFunctionFactoryBase(Type.INTEGER, new Type[]{Type.DOUBLE}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new IntegerAttributeEvaluatorBase() {
                  public IntegerAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     DoubleAttribute p1 = (DoubleAttribute)param1.evaluate(context);
                     IntegerAttribute result = new IntegerAttribute((int)p1.getDoubleValue());
                     if (context.isDebugEnabled()) {
                        NumericConversionFunctionLibrary.this.debugEval(context, dit, result, new Bag[]{p1});
                     }

                     return result;
                  }
               };
            }
         });
         this.register(itd, new SimpleFunctionFactoryBase(Type.DOUBLE, new Type[]{Type.INTEGER}) {
            protected AttributeEvaluator getFunctionImplementation(List arguments) {
               final AttributeEvaluator param1 = (AttributeEvaluator)arguments.get(0);
               return new DoubleAttributeEvaluatorBase() {
                  public DoubleAttribute evaluate(EvaluationCtx context) throws IndeterminateEvaluationException {
                     IntegerAttribute p1 = (IntegerAttribute)param1.evaluate(context);
                     DoubleAttribute result = new DoubleAttribute((double)p1.getIntValue());
                     if (context.isDebugEnabled()) {
                        NumericConversionFunctionLibrary.this.debugEval(context, itd, result, new Bag[]{p1});
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
