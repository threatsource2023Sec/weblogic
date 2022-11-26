package javax.resource.cci;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Streamable {
   void read(InputStream var1) throws IOException;

   void write(OutputStream var1) throws IOException;
}
