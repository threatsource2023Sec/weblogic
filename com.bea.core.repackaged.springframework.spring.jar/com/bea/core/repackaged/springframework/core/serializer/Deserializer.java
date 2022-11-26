package com.bea.core.repackaged.springframework.core.serializer;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
public interface Deserializer {
   Object deserialize(InputStream var1) throws IOException;
}
