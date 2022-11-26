package com.bea.xml_.impl.jam.internal.elements;

import com.bea.xml_.impl.jam.mutable.MSourcePosition;
import java.net.URI;

public final class SourcePositionImpl implements MSourcePosition {
   private int mColumn = -1;
   private int mLine = -1;
   private URI mURI = null;

   SourcePositionImpl() {
   }

   public void setColumn(int col) {
      this.mColumn = col;
   }

   public void setLine(int line) {
      this.mLine = line;
   }

   public void setSourceURI(URI uri) {
      this.mURI = uri;
   }

   public int getColumn() {
      return this.mColumn;
   }

   public int getLine() {
      return this.mLine;
   }

   public URI getSourceURI() {
      return this.mURI;
   }
}
