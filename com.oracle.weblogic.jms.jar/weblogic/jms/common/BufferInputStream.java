package weblogic.jms.common;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;

public abstract class BufferInputStream extends InputStream implements DataInput, PutBackable {
   abstract int pos();

   abstract void gotoPos(int var1) throws IOException;

   abstract String readUTF32() throws IOException;
}
