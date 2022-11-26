package com.googlecode.cqengine.query.simple;

import com.googlecode.cqengine.IndexedCollection;
import com.googlecode.cqengine.attribute.Attribute;
import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.QueryFactory;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.ResultSet;
import java.util.Iterator;

public class ExistsIn extends SimpleQuery {
   final IndexedCollection foreignCollection;
   final Attribute localKeyAttribute;
   final Attribute foreignKeyAttribute;
   final Query foreignRestrictions;

   public ExistsIn(IndexedCollection foreignCollection, Attribute localKeyAttribute, Attribute foreignKeyAttribute) {
      this(foreignCollection, localKeyAttribute, foreignKeyAttribute, (Query)null);
   }

   public ExistsIn(IndexedCollection foreignCollection, Attribute localKeyAttribute, Attribute foreignKeyAttribute, Query foreignRestrictions) {
      super(localKeyAttribute);
      this.foreignCollection = foreignCollection;
      this.localKeyAttribute = localKeyAttribute;
      this.foreignKeyAttribute = foreignKeyAttribute;
      this.foreignRestrictions = foreignRestrictions;
   }

   protected boolean matchesSimpleAttribute(SimpleAttribute attribute, Object object, QueryOptions queryOptions) {
      Object localValue = attribute.getValue(object, queryOptions);
      return this.foreignRestrictions == null ? foreignCollectionContains(this.foreignCollection, QueryFactory.equal(this.foreignKeyAttribute, localValue)) : foreignCollectionContains(this.foreignCollection, QueryFactory.and(QueryFactory.equal(this.foreignKeyAttribute, localValue), this.foreignRestrictions));
   }

   protected boolean matchesNonSimpleAttribute(Attribute attribute, Object object, QueryOptions queryOptions) {
      Iterator var4;
      Object localValue;
      boolean contained;
      if (this.foreignRestrictions == null) {
         var4 = attribute.getValues(object, queryOptions).iterator();

         do {
            if (!var4.hasNext()) {
               return false;
            }

            localValue = var4.next();
            contained = foreignCollectionContains(this.foreignCollection, QueryFactory.equal(this.foreignKeyAttribute, localValue));
         } while(!contained);

         return true;
      } else {
         var4 = attribute.getValues(object, queryOptions).iterator();

         do {
            if (!var4.hasNext()) {
               return false;
            }

            localValue = var4.next();
            contained = foreignCollectionContains(this.foreignCollection, QueryFactory.and(QueryFactory.equal(this.foreignKeyAttribute, localValue), this.foreignRestrictions));
         } while(!contained);

         return true;
      }
   }

   public String toString() {
      return this.foreignRestrictions == null ? "existsIn(IndexedCollection<" + this.foreignKeyAttribute.getObjectType().getSimpleName() + ">, " + asLiteral(this.localKeyAttribute.getAttributeName()) + ", " + asLiteral(this.foreignKeyAttribute.getAttributeName()) + ")" : "existsIn(IndexedCollection<" + this.foreignKeyAttribute.getObjectType().getSimpleName() + ">, " + asLiteral(this.localKeyAttribute.getAttributeName()) + ", " + asLiteral(this.foreignKeyAttribute.getAttributeName()) + ", " + this.foreignRestrictions + ")";
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof ExistsIn)) {
         return false;
      } else {
         ExistsIn existsIn = (ExistsIn)o;
         if (!this.foreignKeyAttribute.equals(existsIn.foreignKeyAttribute)) {
            return false;
         } else {
            label31: {
               if (this.foreignRestrictions != null) {
                  if (this.foreignRestrictions.equals(existsIn.foreignRestrictions)) {
                     break label31;
                  }
               } else if (existsIn.foreignRestrictions == null) {
                  break label31;
               }

               return false;
            }

            if (!this.localKeyAttribute.equals(existsIn.localKeyAttribute)) {
               return false;
            } else {
               return this.foreignCollection.equals(existsIn.foreignCollection);
            }
         }
      }
   }

   protected int calcHashCode() {
      int result = System.identityHashCode(this.foreignCollection);
      result = 31 * result + this.localKeyAttribute.hashCode();
      result = 31 * result + this.foreignKeyAttribute.hashCode();
      result = 31 * result + (this.foreignRestrictions != null ? this.foreignRestrictions.hashCode() : 0);
      return result;
   }

   static boolean foreignCollectionContains(IndexedCollection foreignCollection, Query query) {
      ResultSet resultSet = foreignCollection.retrieve(query);

      boolean var3;
      try {
         var3 = resultSet.isNotEmpty();
      } finally {
         resultSet.close();
      }

      return var3;
   }
}
