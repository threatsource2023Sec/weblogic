package weblogic.xml.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import weblogic.xml.babel.stream.ExclusiveCanonicalWriter;
import weblogic.xml.babel.stream.XMLOutputStreamBase;
import weblogic.xml.babel.stream.XMLWriter;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLInputStreamFactory;

public final class ExclusiveCanonicalizer {
   public static void main(String[] args) throws Exception {
      String file = args[0];
      String localName = args[1];
      XMLInputStream s = XMLInputStreamFactory.newInstance().newInputStream(new File(file));
      s.skip(new weblogic.xml.stream.events.Name(localName), 2);
      XMLWriter writer = new ExclusiveCanonicalWriter(new OutputStreamWriter(new FileOutputStream("out.xml"), "utf-8"));
      XMLOutputStreamBase o = new XMLOutputStreamBase(writer);
      o.add(s.getSubStream());
      o.flush();
   }
}
