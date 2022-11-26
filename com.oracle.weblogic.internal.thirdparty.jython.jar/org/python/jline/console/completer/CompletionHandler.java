package org.python.jline.console.completer;

import java.io.IOException;
import java.util.List;
import org.python.jline.console.ConsoleReader;

public interface CompletionHandler {
   boolean complete(ConsoleReader var1, List var2, int var3) throws IOException;
}
