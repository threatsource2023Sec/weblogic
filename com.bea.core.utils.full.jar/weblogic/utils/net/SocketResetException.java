package weblogic.utils.net;

import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.SocketException;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.ClosedChannelException;
import weblogic.utils.NestedThrowable;
import weblogic.utils.NestedThrowable.Util;

public class SocketResetException extends SocketException implements NestedThrowable {
   private static final long serialVersionUID = 2608888081621018043L;
   public static final String[] RESET_MESSAGE = new String[]{"by peer", "broken pipe", "network name is no longer available", "closed", "a connection with a remote socket was reset by that socket", "there is no process to read data written to a pipe", "connection timed out", "readfile failed", "connection was aborted", "software caused connection abort", "connection reset", "socket operation on nonsocket: recv failed", "null fd object", "error 64", "bad file number", "error in poll for fd"};
   private IOException nested;

   public static boolean isResetException(IOException ioe) {
      if (ioe instanceof SocketResetException) {
         return true;
      } else if (ioe instanceof AsynchronousCloseException) {
         return true;
      } else if (ioe instanceof ClosedChannelException) {
         return true;
      } else if (ioe instanceof EOFException) {
         return true;
      } else {
         String message = ioe.getMessage();
         if (message == null) {
            return false;
         } else {
            message = message.toLowerCase();

            for(int i = 0; i < RESET_MESSAGE.length; ++i) {
               if (message.indexOf(RESET_MESSAGE[i]) != -1) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public SocketResetException() {
   }

   public SocketResetException(String message) {
      super(message);
   }

   public SocketResetException(IOException ioe) {
      this.nested = ioe;
   }

   public SocketResetException(String message, IOException ioe) {
      super(message);
      this.nested = ioe;
   }

   public Throwable getNested() {
      return this.nested;
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
      return Util.toString(this);
   }

   public void printStackTrace(PrintStream s) {
      Util.printStackTrace(this, s);
   }

   public void printStackTrace(PrintWriter w) {
      Util.printStackTrace(this, w);
   }

   public void printStackTrace() {
      this.printStackTrace(System.err);
   }
}
