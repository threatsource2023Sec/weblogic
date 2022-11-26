package weblogic.jms.common;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;

public interface Payload {
   int getLength();

   BufferInputStream getInputStream() throws IOException;

   void writeLengthAndData(DataOutput var1) throws IOException;

   void writeTo(OutputStream var1) throws IOException;
}
