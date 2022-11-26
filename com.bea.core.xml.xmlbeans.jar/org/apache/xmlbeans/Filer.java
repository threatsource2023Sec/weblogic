package org.apache.xmlbeans;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public interface Filer {
   OutputStream createBinaryFile(String var1) throws IOException;

   Writer createSourceFile(String var1) throws IOException;
}
