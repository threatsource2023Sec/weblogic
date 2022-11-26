package org.apache.openjpa.lib.util;

import java.io.IOException;
import serp.bytecode.BCClass;

public interface BytecodeWriter {
   void write(BCClass var1) throws IOException;
}
