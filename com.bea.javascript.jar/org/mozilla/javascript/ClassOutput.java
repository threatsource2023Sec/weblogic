package org.mozilla.javascript;

import java.io.IOException;
import java.io.OutputStream;

public interface ClassOutput {
   OutputStream getOutputStream(String var1, boolean var2) throws IOException;
}
