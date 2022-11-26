package com.googlecode.cqengine.persistence.support.sqlite;

import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.index.sqlite.SQLiteIdentityIndex;
import com.googlecode.cqengine.index.support.indextype.DiskTypeIndex;

public class SQLiteDiskIdentityIndex extends SQLiteIdentityIndex implements DiskTypeIndex {
   public SQLiteDiskIdentityIndex(SimpleAttribute primaryKeyAttribute) {
      super(primaryKeyAttribute);
   }

   public Index getEffectiveIndex() {
      return this;
   }

   public static SQLiteDiskIdentityIndex onAttribute(SimpleAttribute primaryKeyAttribute) {
      return new SQLiteDiskIdentityIndex(primaryKeyAttribute);
   }
}
