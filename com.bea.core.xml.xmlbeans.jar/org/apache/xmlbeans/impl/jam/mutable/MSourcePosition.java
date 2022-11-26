package org.apache.xmlbeans.impl.jam.mutable;

import java.net.URI;
import org.apache.xmlbeans.impl.jam.JSourcePosition;

public interface MSourcePosition extends JSourcePosition {
   void setColumn(int var1);

   void setLine(int var1);

   void setSourceURI(URI var1);
}
