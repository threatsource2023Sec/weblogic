package org.jboss.weld.exceptions;

import java.io.ObjectStreamException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

public class WeldExceptionListMessage implements WeldExceptionMessage, Serializable {
   private static final long serialVersionUID = 3445187707771082346L;
   private List causes;
   private String message;

   public WeldExceptionListMessage(List throwables) {
      this.causes = throwables;
   }

   public String getAsString() {
      if (this.message == null) {
         this.generateMessage();
      }

      return this.message;
   }

   private void generateMessage() {
      StringWriter writer = new StringWriter();
      PrintWriter messageBuffer = new PrintWriter(writer);
      messageBuffer.print("Exception List with ");
      messageBuffer.print(this.causes.size());
      messageBuffer.print(" exceptions:\n");
      int i = 0;
      Iterator var4 = this.causes.iterator();

      while(var4.hasNext()) {
         Throwable throwable = (Throwable)var4.next();
         messageBuffer.print("Exception ");
         messageBuffer.print(i++);
         messageBuffer.print(" :\n");
         throwable.printStackTrace(messageBuffer);
      }

      messageBuffer.flush();
      this.message = writer.toString();
   }

   private Object writeReplace() throws ObjectStreamException {
      return new WeldExceptionStringMessage(this.getAsString());
   }
}
