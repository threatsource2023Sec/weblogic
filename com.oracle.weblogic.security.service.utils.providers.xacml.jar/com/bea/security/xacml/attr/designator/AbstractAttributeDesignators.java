package com.bea.security.xacml.attr.designator;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.AttributeDesignator;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.attr.AttributeEvaluatableFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractAttributeDesignators implements AttributeDesignatorFactory {
   private List factories = Collections.synchronizedList(new ArrayList());

   public void register(AttributeEvaluatableFactory factory) {
      this.factories.add(factory);
   }

   public AbstractAttributeDesignators() throws URISyntaxException {
   }

   protected Iterator getIterator() {
      return this.factories.iterator();
   }

   public AttributeEvaluator createDesignator(AttributeDesignator attribute, Iterator otherFactories) throws URISyntaxException, InvalidAttributeException {
      URI type = attribute.getDataType();
      URI id = attribute.getAttributeId();
      String issuer = attribute.getIssuer();
      Iterator it = this.factories.iterator();

      AttributeEvaluator ae;
      do {
         if (!it.hasNext()) {
            return null;
         }

         AttributeEvaluatableFactory saf = (AttributeEvaluatableFactory)it.next();
         ae = saf.getEvaluatable(id, type, issuer);
      } while(ae == null);

      return ae;
   }
}
