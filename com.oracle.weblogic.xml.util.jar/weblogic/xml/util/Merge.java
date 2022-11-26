package weblogic.xml.util;

import java.io.File;
import java.io.FileOutputStream;
import weblogic.xml.stream.ElementFactory;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;
import weblogic.xml.stream.XMLOutputStream;
import weblogic.xml.stream.XMLOutputStreamFactory;

public class Merge {
   public static void main(String[] args) throws Exception {
      if (args.length != 3) {
         System.out.println("Usage: java weblogic.xml.util.Merge");
         System.out.println("[filename1] [filename2] [outputfile]");
         System.exit(0);
      }

      XMLInputStream input1 = XMLInputStreamFactory.newInstance().newInputStream(new File(args[0]));
      input1.skip(2);
      StartElement se = (StartElement)input1.next();
      XMLInputStream input2 = XMLInputStreamFactory.newInstance().newInputStream(new File(args[1]));
      input2.skip(2);
      input2.next();
      XMLOutputStream output = XMLOutputStreamFactory.newInstance().newDebugOutputStream(new FileOutputStream(new File(args[2])));
      output.add(se);
      output.add(input1.getSubStream());
      output.add(input2.getSubStream());
      output.add(ElementFactory.createEndElement(se.getName()));
      output.flush();
   }
}
