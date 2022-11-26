package com.googlecode.cqengine.persistence.support.sqlite;

import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.sqlite.SQLiteIdentityIndex;
import com.googlecode.cqengine.index.support.indextype.OffHeapTypeIndex;

public class SQLiteOffHeapIdentityIndex extends SQLiteIdentityIndex implements OffHeapTypeIndex {
   public SQLiteOffHeapIdentityIndex(SimpleAttribute primaryKeyAttribute) {
      super(primaryKeyAttribute);
   }

   public Index getEffectiveIndex() {
      return this;
   }

   public static SQLiteOffHeapIdentityIndex onAttribute(SimpleAttribute primaryKeyAttribute) {
      return new SQLiteOffHeapIdentityIndex(primaryKeyAttribute);
   }
}
