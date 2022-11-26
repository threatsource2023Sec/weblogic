package com.bea.security.xacml.function;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.Apply;
import com.bea.common.security.xacml.policy.Expression;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.InvalidArgumentsException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SimpleFunctionFactoryContainer implements FunctionFactory {
   private List libraries = Collections.synchronizedList(new ArrayList());

   public void register(SimpleFunctionLibrary library) {
      this.libraries.add(library);
   }

   public AttributeEvaluator createFunction(Apply expression, Map variables, Configuration config, Iterator otherFactories) throws InvalidArgumentsException, EvaluationPlanException, URISyntaxException {
      URI identifier = expression.getFunctionId();
      List arguments = new ArrayList();
      List es = expression.getExpressions();
      if (es != null) {
         Iterator it = es.iterator();

         while(it.hasNext()) {
            arguments.add(config.getExpressionRegistry().parse((Expression)it.next(), variables, config));
         }
      }

      AttributeEvaluator result = null;
      Iterator it = this.libraries.iterator();

      while(it.hasNext()) {
         result = ((SimpleFunctionLibrary)it.next()).createFunction(identifier, arguments, config.getLog());
         if (result != null) {
            break;
         }
      }

      return result;
   }
}
