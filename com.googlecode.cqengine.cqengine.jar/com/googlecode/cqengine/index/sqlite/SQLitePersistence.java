package com.googlecode.cqengine.index.sqlite;

import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.persistence.Persistence;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.sql.Connection;

public interface SQLitePersistence extends Persistence {
   Connection getConnection(Index var1, QueryOptions var2);

   long getBytesUsed();

   void compact();

   void expand(long var1);

   SQLiteIdentityIndex createIdentityIndex();
}
