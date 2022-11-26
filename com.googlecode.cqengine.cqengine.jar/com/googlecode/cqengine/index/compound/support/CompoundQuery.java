package com.googlecode.cqengine.index.compound.support;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.logical.And;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.query.simple.Equal;
import com.googlecode.cqengine.query.simple.SimpleQuery;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CompoundQuery implements Query {
   private final And andQuery;
   private final CompoundAttribute compoundAttribute;

   public CompoundQuery(And andQuery, CompoundAttribute compoundAttribute) {
      this.andQuery = andQuery;
      this.compoundAttribute = compoundAttribute;
   }

   public boolean matches(Object object, QueryOptions queryOptions) {
      Iterator var3 = this.andQuery.getSimpleQueries().iterator();

      Equal equal;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         SimpleQuery simpleQuery = (SimpleQuery)var3.next();
         equal = (Equal)simpleQuery;
      } while(equal.matches(object, queryOptions));

      return false;
   }

   public And getAndQuery() {
      return this.andQuery;
   }

   public CompoundAttribute getCompoundAttribute() {
      return this.compoundAttribute;
   }

   public CompoundValueTuple getCompoundValueTuple() {
      List attributeValues = new ArrayList(this.andQuery.getSimpleQueries().size());
      Iterator var2 = this.andQuery.getSimpleQueries().iterator();

      while(var2.hasNext()) {
         SimpleQuery simpleQuery = (SimpleQuery)var2.next();
         Equal equal = (Equal)simpleQuery;
         attributeValues.add(equal.getValue());
      }

      return new CompoundValueTuple(attributeValues);
   }

   public static CompoundQuery fromAndQueryIfSuitable(And andQuery) {
      if (andQuery.hasLogicalQueries()) {
         return null;
      } else {
         List attributeList = new ArrayList(andQuery.getSimpleQueries().size());
         Iterator var2 = andQuery.getSimpleQueries().iterator();

         while(var2.hasNext()) {
            SimpleQuery simpleQuery = (SimpleQuery)var2.next();
            if (!(simpleQuery instanceof Equal)) {
               return null;
            }

            attributeList.add(simpleQuery.getAttribute());
         }

         CompoundAttribute compoundAttribute = new CompoundAttribute(attributeList);
         return new CompoundQuery(andQuery, compoundAttribute);
      }
   }
}
