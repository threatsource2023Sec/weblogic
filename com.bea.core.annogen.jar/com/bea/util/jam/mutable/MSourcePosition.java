package com.bea.util.jam.mutable;

import com.bea.util.jam.JSourcePosition;
import java.net.URI;

public interface MSourcePosition extends JSourcePosition {
   void setColumn(int var1);

   void setLine(int var1);

   void setOffset(int var1);

   void setSourceURI(URI var1);
}
