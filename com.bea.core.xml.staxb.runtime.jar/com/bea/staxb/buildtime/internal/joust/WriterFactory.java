package com.bea.staxb.buildtime.internal.joust;

import java.io.IOException;
import java.io.Writer;

public interface WriterFactory {
   Writer createWriter(String var1, String var2) throws IOException;
}
