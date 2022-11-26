package weblogic.jms.common;

import java.io.IOException;

public interface PutBackable {
   void unput() throws IOException;
}
