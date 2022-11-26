package com.bea.core.repackaged.springframework.core.serializer;

import java.io.IOException;
import java.io.OutputStream;

@FunctionalInterface
public interface Serializer {
   void serialize(Object var1, OutputStream var2) throws IOException;
}
