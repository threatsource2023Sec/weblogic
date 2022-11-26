package com.googlecode.cqengine.persistence.support.serialization;

public interface PojoSerializer {
   byte[] serialize(Object var1);

   Object deserialize(byte[] var1);
}
