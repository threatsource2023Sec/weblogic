package com.googlecode.cqengine.engine;

public interface QueryEngineInternal extends QueryEngine, ModificationListener {
   boolean isMutable();
}
