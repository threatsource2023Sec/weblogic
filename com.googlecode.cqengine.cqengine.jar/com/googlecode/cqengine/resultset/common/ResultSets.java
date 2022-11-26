package com.googlecode.cqengine.resultset.common;

import com.googlecode.cqengine.resultset.ResultSet;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ResultSets {
   public static Collection asCollection(final ResultSet resultSet) {
      return new AbstractCollection() {
         public Iterator iterator() {
            return resultSet.iterator();
         }

         public int size() {
            return resultSet.size();
         }

         public boolean contains(Object o) {
            return resultSet.contains(o);
         }

         public boolean isEmpty() {
            return resultSet.isEmpty();
         }
      };
   }

   public static List wrapWithCostCachingIfNecessary(Iterable resultSets) {
      List result = new LinkedList();
      Iterator var2 = resultSets.iterator();

      while(var2.hasNext()) {
         ResultSet candidate = (ResultSet)var2.next();
         result.add(wrapWithCostCachingIfNecessary(candidate));
      }

      return result;
   }

   public static ResultSet wrapWithCostCachingIfNecessary(ResultSet resultSet) {
      return (ResultSet)(resultSet instanceof CostCachingResultSet ? resultSet : new CostCachingResultSet(resultSet));
   }

   ResultSets() {
   }
}
