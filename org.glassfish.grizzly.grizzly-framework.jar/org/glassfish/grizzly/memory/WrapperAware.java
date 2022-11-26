package org.glassfish.grizzly.memory;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.glassfish.grizzly.Buffer;

public interface WrapperAware {
   Buffer wrap(byte[] var1);

   Buffer wrap(byte[] var1, int var2, int var3);

   Buffer wrap(String var1);

   Buffer wrap(String var1, Charset var2);

   Buffer wrap(ByteBuffer var1);
}
