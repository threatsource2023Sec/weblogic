package com.bea.xml_.impl.jam.mutable;

import com.bea.xml_.impl.jam.JSourcePosition;
import java.net.URI;

public interface MSourcePosition extends JSourcePosition {
   void setColumn(int var1);

   void setLine(int var1);

   void setSourceURI(URI var1);
}
