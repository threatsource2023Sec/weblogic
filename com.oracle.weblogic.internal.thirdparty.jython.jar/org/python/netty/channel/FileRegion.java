package org.python.netty.channel;

import java.io.IOException;
import java.nio.channels.WritableByteChannel;
import org.python.netty.util.ReferenceCounted;

public interface FileRegion extends ReferenceCounted {
   long position();

   /** @deprecated */
   @Deprecated
   long transfered();

   long transferred();

   long count();

   long transferTo(WritableByteChannel var1, long var2) throws IOException;

   FileRegion retain();

   FileRegion retain(int var1);

   FileRegion touch();

   FileRegion touch(Object var1);
}
