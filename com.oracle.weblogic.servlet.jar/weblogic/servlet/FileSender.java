package weblogic.servlet;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;

public interface FileSender {
   long sendFile(File var1) throws IOException;

   long sendFile(FileChannel var1, long var2, long var4) throws IOException;

   long getBytesSent();

   boolean usesServletOutputStream();
}
