package com.googlecode.cqengine.index.sqlite;

import com.googlecode.cqengine.attribute.SimpleAttribute;
import com.googlecode.cqengine.index.AttributeIndex;

public interface IdentityAttributeIndex extends AttributeIndex {
   SimpleAttribute getForeignKeyAttribute();
}
