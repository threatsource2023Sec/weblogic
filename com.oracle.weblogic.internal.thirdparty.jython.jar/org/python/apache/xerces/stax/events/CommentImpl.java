package org.python.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Comment;

public final class CommentImpl extends XMLEventImpl implements Comment {
   private final String fText;

   public CommentImpl(String var1, Location var2) {
      super(5, var2);
      this.fText = var1 != null ? var1 : "";
   }

   public String getText() {
      return this.fText;
   }

   public void writeAsEncodedUnicode(Writer var1) throws XMLStreamException {
      try {
         var1.write("<!--");
         var1.write(this.fText);
         var1.write("-->");
      } catch (IOException var3) {
         throw new XMLStreamException(var3);
      }
   }
}
