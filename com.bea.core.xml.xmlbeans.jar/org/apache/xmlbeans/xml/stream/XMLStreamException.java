package org.apache.xmlbeans.xml.stream;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import org.apache.xmlbeans.xml.stream.utils.NestedThrowable;

public class XMLStreamException extends IOException implements NestedThrowable {
   protected Throwable th;

   public XMLStreamException() {
   }

   public XMLStreamException(String msg) {
      super(msg);
   }

   public XMLStreamException(Throwable th) {
      this.th = th;
   }

   public XMLStreamException(String msg, Throwable th) {
      super(msg);
      this.th = th;
   }

   public Throwable getNestedException() {
      return this.getNested();
   }

   public String getMessage() {
      String msg = super.getMessage();
      return msg == null && this.th != null ? this.th.getMessage() : msg;
   }

   public Throwable getNested() {
      return this.th;
   }

   public String superToString() {
      return super.toString();
   }

   public void superPrintStackTrace(PrintStream ps) {
      super.printStackTrace(ps);
   }

   public void superPrintStackTrace(PrintWriter pw) {
      super.printStackTrace(pw);
   }

   public String toString() {
      return NestedThrowable.Util.toString(this);
   }

   public void printStackTrace(PrintStream s) {
      NestedThrowable.Util.printStackTrace(this, (PrintStream)s);
   }

   public void printStackTrace(PrintWriter w) {
      NestedThrowable.Util.printStackTrace(this, (PrintWriter)w);
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }
}
