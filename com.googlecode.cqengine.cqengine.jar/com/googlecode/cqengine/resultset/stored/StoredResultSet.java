package com.googlecode.cqengine.resultset.stored;

import com.googlecode.cqengine.resultset.ResultSet;

public abstract class StoredResultSet extends ResultSet {
   public abstract boolean add(Object var1);

   public abstract boolean remove(Object var1);

   public abstract void clear();

   public abstract boolean isEmpty();

   public abstract boolean isNotEmpty();
}
