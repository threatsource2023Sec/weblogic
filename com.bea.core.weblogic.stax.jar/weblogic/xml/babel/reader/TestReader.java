package weblogic.xml.babel.reader;

import java.io.Reader;

public class TestReader {
   public static void main(String[] args) {
      try {
         Reader reader = XmlReader.createReader(System.in);

         int c;
         do {
            c = reader.read();
            System.out.println((char)c);
         } while(c != -1);
      } catch (Exception var3) {
         System.out.println(var3);
      }

   }
}
