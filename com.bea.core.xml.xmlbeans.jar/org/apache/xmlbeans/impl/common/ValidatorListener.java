package org.apache.xmlbeans.impl.common;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import org.apache.xmlbeans.XmlCursor;

public interface ValidatorListener {
   int BEGIN = 1;
   int END = 2;
   int TEXT = 3;
   int ATTR = 4;
   int ENDATTRS = 5;

   void nextEvent(int var1, Event var2);

   public interface Event extends PrefixResolver {
      int PRESERVE = 1;
      int REPLACE = 2;
      int COLLAPSE = 3;

      XmlCursor getLocationAsCursor();

      Location getLocation();

      String getXsiType();

      String getXsiNil();

      String getXsiLoc();

      String getXsiNoLoc();

      QName getName();

      String getText();

      String getText(int var1);

      boolean textIsWhitespace();
   }
}
