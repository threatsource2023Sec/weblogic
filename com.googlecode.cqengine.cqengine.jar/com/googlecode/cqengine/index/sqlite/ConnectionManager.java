package com.googlecode.cqengine.index.sqlite;

import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.query.option.QueryOptions;
import java.sql.Connection;

public interface ConnectionManager {
   Connection getConnection(Index var1, QueryOptions var2);

   boolean isApplyUpdateForIndexEnabled(Index var1);
}
