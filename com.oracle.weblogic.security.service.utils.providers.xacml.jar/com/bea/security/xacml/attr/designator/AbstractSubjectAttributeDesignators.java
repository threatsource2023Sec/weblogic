package com.bea.security.xacml.attr.designator;

import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.policy.SubjectAttributeDesignator;
import com.bea.security.xacml.AttributeEvaluator;
import com.bea.security.xacml.attr.SubjectAttributeEvaluatableFactory;
import java.util.Iterator;

public abstract class AbstractSubjectAttributeDesignators extends AbstractAttributeDesignators implements SubjectAttributeDesignatorFactory {
   public void register(SubjectAttributeEvaluatableFactory factory) {
      super.register(factory);
   }

   public AbstractSubjectAttributeDesignators() throws URISyntaxException {
   }

   public AttributeEvaluator createDesignator(SubjectAttributeDesignator attribute, Iterator otherFactories) throws URISyntaxException, InvalidAttributeException {
      URI type = attribute.getDataType();
      URI id = attribute.getAttributeId();
      String issuer = attribute.getIssuer();
      URI category = attribute.getSubjectCategory();
      Iterator it = this.getIterator();

      AttributeEvaluator ae;
      do {
         if (!it.hasNext()) {
            return null;
         }

         SubjectAttributeEvaluatableFactory saf = (SubjectAttributeEvaluatableFactory)it.next();
         ae = saf.getEvaluatable(id, type, issuer, category);
      } while(ae == null);

      return ae;
   }
}
