package org.jboss.classfilewriter;

import java.io.IOException;
import org.jboss.classfilewriter.util.ByteArrayDataOutputStream;

public interface WritableEntry {
   void write(ByteArrayDataOutputStream var1) throws IOException;
}
