package com.bea.security.xacml.function;

import com.bea.common.security.xacml.Type;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Apply;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.InvalidArgumentsException;
import com.bea.security.xacml.MissingFactoryException;
import com.bea.security.xacml.function.standard.HigherOrderBagFunctionFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class FunctionRegistry {
   private List factories = Collections.synchronizedList(new ArrayList());

   public FunctionRegistry() throws URISyntaxException {
      this.register(new StandardFunctions());
      this.register(new HigherOrderBagFunctionFactory());
   }

   public void register(FunctionFactory factory) {
      this.factories.add(factory);
   }

   public AttributeEvaluator getEvaluator(Apply expression, Map variables, Configuration config) throws EvaluationPlanException, URISyntaxException {
      AttributeEvaluator ae = null;
      ListIterator facIt = this.factories.listIterator();

      while(facIt.hasNext()) {
         FunctionFactory ff = (FunctionFactory)facIt.next();

         try {
            ae = ff.createFunction(expression, variables, config, this.factories.listIterator(facIt.nextIndex()));
            if (ae != null) {
               return ae;
            }
         } catch (InvalidArgumentsException var13) {
            StringBuffer msg = new StringBuffer();
            msg.append("Failed to generate evaluator for function: ");
            msg.append(expression);
            msg.append(". Expected return type: ");
            Type rt = var13.getExpectedReturnType();
            if (rt.isBag()) {
               msg.append("Bag of ");
            }

            msg.append(rt.getDataType());
            List args = var13.getExpectedArguments();
            if (args != null && !args.isEmpty()) {
               msg.append("\tExpected arguments: ");
               Iterator it = args.iterator();

               while(it.hasNext()) {
                  Type n = (Type)it.next();
                  if (n.isBag()) {
                     msg.append("Bag of ");
                  }

                  msg.append(n.getDataType());
                  if (it.hasNext()) {
                     msg.append(", ");
                  }
               }
            }

            String message = msg.toString();
            throw new EvaluationPlanException(message);
         }
      }

      String message = "No evaluator found for apply expression with function identifier: " + expression.getFunctionId().toString();
      throw new MissingFactoryException(message);
   }
}
