package com.googlecode.cqengine.query.simple;

import com.googlecode.cqengine.query.Query;
import com.googlecode.cqengine.query.option.QueryOptions;

public interface FilterQuery extends Query {
   boolean matchesValue(Object var1, QueryOptions var2);
}
