package com.googlecode.cqengine.index.compound.support;

import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompoundAttribute implements Attribute {
   private final List attributes;

   public CompoundAttribute(List attributes) {
      if (attributes.size() < 2) {
         throw new IllegalStateException("Cannot create a compound index on fewer than two attributes: " + attributes.size());
      } else {
         this.attributes = attributes;
      }
   }

   public int size() {
      return this.attributes.size();
   }

   public Class getObjectType() {
      throw new UnsupportedOperationException("Method not supported by CompoundAttribute");
   }

   public Class getAttributeType() {
      throw new UnsupportedOperationException("Method not supported by CompoundAttribute");
   }

   public String getAttributeName() {
      throw new UnsupportedOperationException("Method not supported by CompoundAttribute");
   }

   public Iterable getValues(Object object, QueryOptions queryOptions) {
      List attributeValueLists = new ArrayList(this.attributes.size());
      Iterator var4 = this.attributes.iterator();

      while(var4.hasNext()) {
         Attribute attribute = (Attribute)var4.next();
         Iterable values = attribute.getValues(object, queryOptions);
         attributeValueLists.add(values);
      }

      List listsOfValueCombinations = TupleCombinationGenerator.generateCombinations(attributeValueLists);
      List tuples = new ArrayList(listsOfValueCombinations.size());
      Iterator var10 = listsOfValueCombinations.iterator();

      while(var10.hasNext()) {
         List valueCombination = (List)var10.next();
         tuples.add(new CompoundValueTuple(valueCombination));
      }

      return tuples;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof CompoundAttribute)) {
         return false;
      } else {
         CompoundAttribute that = (CompoundAttribute)o;
         return this.attributes.equals(that.attributes);
      }
   }

   public int hashCode() {
      return this.attributes.hashCode();
   }

   public String toString() {
      return "CompoundAttribute{attributes=" + this.attributes + '}';
   }
}
