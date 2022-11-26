package weblogic.xml.domimpl.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.w3c.dom.Document;
import weblogic.xml.domimpl.Loader;
import weblogic.xml.domimpl.Saver;

public class LoadSave {
   private static final String USAGE = "Usage: " + LoadSave.class.getName() + " inputfile outputfile?";

   public static void main(String[] args) throws Exception {
      switch (args.length) {
         case 1:
            loadSave(new FileInputStream(args[0]), System.out);
            break;
         case 2:
            FileOutputStream os = new FileOutputStream(args[1]);
            loadSave(new FileInputStream(args[0]), os);
            os.close();
            break;
         default:
            System.err.println(USAGE);
      }

   }

   private static void loadSave(InputStream inputStream, OutputStream outputStream) throws IOException {
      Document document = Loader.load(inputStream);

      assert document != null;

      Saver.save(outputStream, document);
   }
}
