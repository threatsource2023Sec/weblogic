package weblogic.servlet.jsp;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface ByteWriter {
   void write(byte[] var1, String var2) throws IOException;

   void write(ByteBuffer var1, String var2) throws IOException;

   void setInitCharacterEncoding(String var1, boolean var2);

   boolean writeBytes(byte[] var1, int var2, int var3) throws IOException;
}
