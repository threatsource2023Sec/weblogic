package org.python.jline.console.history;

import java.io.IOException;

public interface PersistentHistory extends History {
   void flush() throws IOException;

   void purge() throws IOException;
}
