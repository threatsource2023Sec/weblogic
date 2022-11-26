package weblogic.xml.saaj.mime4j.util;

import java.io.IOException;

public interface TempPath {
   TempPath createTempPath() throws IOException;

   TempPath createTempPath(String var1) throws IOException;

   TempFile createTempFile() throws IOException;

   TempFile createTempFile(String var1, String var2) throws IOException;

   TempFile createTempFile(String var1, String var2, boolean var3) throws IOException;

   String getAbsolutePath();

   void delete();
}
