package weblogic.rmi.internal;

import java.io.IOException;
import java.io.InputStream;
import weblogic.rmi.extensions.server.CBVInputStream;
import weblogic.utils.io.Replacer;

public interface CBVInput {
   Object readObject() throws IOException, ClassNotFoundException;

   int readInt() throws IOException;

   void setDelegate(CBVInputStream var1, InputStream var2) throws IOException;

   void setReplacer(Replacer var1);

   void close() throws IOException;
}
