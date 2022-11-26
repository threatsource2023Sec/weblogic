package com.googlecode.cqengine.persistence;

import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.index.Index;
import com.googlecode.cqengine.persistence.support.ObjectStore;
import com.googlecode.cqengine.query.option.QueryOptions;

public interface Persistence {
   ObjectStore createObjectStore();

   boolean supportsIndex(Index var1);

   void openRequestScopeResources(QueryOptions var1);

   void closeRequestScopeResources(QueryOptions var1);

   SimpleAttribute getPrimaryKeyAttribute();
}
