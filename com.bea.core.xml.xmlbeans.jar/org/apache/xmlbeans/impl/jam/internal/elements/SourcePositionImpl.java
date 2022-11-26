package org.apache.xmlbeans.impl.jam.internal.elements;

import java.net.URI;
import org.apache.xmlbeans.impl.jam.mutable.MSourcePosition;

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
