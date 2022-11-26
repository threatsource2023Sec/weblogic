package weblogic.rmi.internal;

import java.io.IOException;
import java.io.OutputStream;
import weblogic.rmi.extensions.server.CBVOutputStream;
import weblogic.utils.io.Replacer;

public interface CBVOutput {
   void writeObject(Object var1) throws IOException;

   void writeInt(int var1) throws IOException;

   void setDelegate(CBVOutputStream var1, OutputStream var2) throws IOException;

   void setReplacer(Replacer var1);

   void flush() throws IOException;

   void close() throws IOException;

   void reset() throws IOException;
}
