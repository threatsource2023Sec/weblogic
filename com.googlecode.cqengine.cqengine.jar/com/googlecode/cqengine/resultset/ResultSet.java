package com.googlecode.cqengine.resultset;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;
import com.googlecode.cqengine.resultset.common.NoSuchObjectException;
import com.googlecode.cqengine.resultset.common.NonUniqueObjectException;
import java.io.Closeable;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class ResultSet implements Iterable, Closeable {
   public abstract Iterator iterator();

   public abstract boolean contains(Object var1);

   public abstract boolean matches(Object var1);

   public abstract Query getQuery();

   public abstract QueryOptions getQueryOptions();

   public Object uniqueResult() {
      Iterator iterator = this.iterator();
      if (!iterator.hasNext()) {
         throw new NoSuchObjectException("ResultSet is empty");
      } else {
         Object result = iterator.next();
         if (iterator.hasNext()) {
            throw new NonUniqueObjectException("ResultSet contains more than one object");
         } else {
            return result;
         }
      }
   }

   public abstract int getRetrievalCost();

   public abstract int getMergeCost();

   public abstract int size();

   public boolean isEmpty() {
      return !this.iterator().hasNext();
   }

   public boolean isNotEmpty() {
      return this.iterator().hasNext();
   }

   public abstract void close();

   public Spliterator spliterator() {
      return Spliterators.spliteratorUnknownSize(this.iterator(), 16);
   }

   public Stream stream() {
      return (Stream)StreamSupport.stream(this.spliterator(), false).onClose(this::close);
   }
}
