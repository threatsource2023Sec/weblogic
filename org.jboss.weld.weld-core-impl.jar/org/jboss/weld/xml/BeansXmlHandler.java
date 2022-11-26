package org.jboss.weld.xml;

import java.net.URL;
import org.xml.sax.helpers.DefaultHandler;

public class BeansXmlHandler extends DefaultHandler {
   protected final URL file;

   public BeansXmlHandler(URL file) {
      this.file = file;
   }

   protected String interpolate(String text) {
      return text;
   }
}
