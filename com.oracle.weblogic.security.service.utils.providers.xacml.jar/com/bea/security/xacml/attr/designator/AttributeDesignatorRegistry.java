package com.bea.security.xacml.attr.designator;

import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.AttributeDesignator;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.Configuration;
import com.bea.security.xacml.EvaluationPlanException;
import com.bea.security.xacml.MissingFactoryException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class AttributeDesignatorRegistry {
   private List factories = Collections.synchronizedList(new ArrayList());

   public void register(AttributeDesignatorFactory factory) {
      this.factories.add(factory);
   }

   public AttributeEvaluator getDesignator(AttributeDesignator attribute, Configuration config) throws URISyntaxException, EvaluationPlanException {
      AttributeEvaluator av = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new MissingFactoryException("No configured factory for attribute designator: " + attribute);
         }

         av = ((AttributeDesignatorFactory)facIt.next()).createDesignator(attribute, config, this.factories.listIterator(facIt.nextIndex()));
      } while(av == null);

      return av;
   }
}
