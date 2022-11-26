package weblogic.xml.util;

public interface XMLStreamReader {
   int open(String var1);

   int read(int[] var1, int var2);

   void setEncoding(int var1, int var2);

   void close();
}
