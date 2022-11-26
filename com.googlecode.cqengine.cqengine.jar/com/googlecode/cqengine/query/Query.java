package com.googlecode.cqengine.query;

import com.googlecode.cqengine.query.option.QueryOptions;

public interface Query {
   boolean matches(Object var1, QueryOptions var2);
}
