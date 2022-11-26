package weblogic.utils.classes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface ClassFinder {
   void setClasspath(File[] var1);

   ClassBytes find(String var1);

   InputStream getClassAsStream(String var1) throws IOException, ClassNotFoundException;

   InputStream getResourceStream(String var1) throws IOException;

   byte[] getClassAsBytes(String var1) throws IOException, ClassNotFoundException;
}
