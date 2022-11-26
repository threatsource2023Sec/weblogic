package com.googlecode.cqengine.attribute;

import com.googlecode.cqengine.query.option.QueryOptions;

public interface Attribute {
   Class getObjectType();

   Class getAttributeType();

   String getAttributeName();

   Iterable getValues(Object var1, QueryOptions var2);
}
