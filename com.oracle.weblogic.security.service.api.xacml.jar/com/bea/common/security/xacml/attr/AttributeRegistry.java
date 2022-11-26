package com.bea.common.security.xacml.attr;

import com.bea.common.security.xacml.CollectionUtil;
import com.bea.common.security.xacml.InvalidAttributeException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import org.w3c.dom.Node;

public class AttributeRegistry {
   private List factories = Collections.synchronizedList(new ArrayList());

   public AttributeRegistry() throws URISyntaxException {
      this.register(new StandardAttributes());
   }

   public void register(AttributeFactory factory) {
      this.factories.add(factory);
   }

   public AttributeValue getAttribute(Node attribute) throws URISyntaxException, InvalidAttributeException {
      AttributeValue av = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new InvalidAttributeException("No configured factory for attribute value node: " + attribute.toString());
         }

         av = ((AttributeFactory)facIt.next()).createAttribute(attribute, this.factories.listIterator(facIt.nextIndex()));
      } while(av == null);

      return av;
   }

   public AttributeValue getAttribute(URI dataType, Node attribute) throws URISyntaxException, InvalidAttributeException {
      AttributeValue av = null;
      ListIterator facIt = this.factories.listIterator();

      do {
         if (!facIt.hasNext()) {
            throw new InvalidAttributeException("No configured factory for attribute value node: " + attribute.toString());
         }

         av = ((AttributeFactory)facIt.next()).createAttribute(dataType, attribute, this.factories.listIterator(facIt.nextIndex()));
      } while(av == null);

      return av;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         return !(o instanceof AttributeRegistry) ? false : CollectionUtil.equals(this.factories, ((AttributeRegistry)o).factories);
      }
   }

   public int hashCode() {
      return this.factories.hashCode();
   }
}
