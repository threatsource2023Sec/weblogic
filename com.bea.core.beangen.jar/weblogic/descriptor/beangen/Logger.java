package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JElement;
import com.bea.util.jam.JSourcePosition;
import java.util.HashSet;
import java.util.Set;

public class Logger {
   private Set locations = new HashSet();
   private int errorCount = 0;
   private int warningCount = 0;
   private boolean silent = false;

   public int getErrorCount() {
      return this.errorCount;
   }

   public int getWarningCount() {
      return this.warningCount;
   }

   public void error(String msg, JElement element, Exception e) {
      this.error(msg, element);
   }

   public void error(String msg, JElement element) {
      if (!(element instanceof JClass) && this.locations.add(element)) {
         System.err.println(this.constructMessage("Error", msg, element));
         ++this.errorCount;
      }

   }

   public void warning(String msg, JElement element, Exception e) {
      this.warning(msg, element);
   }

   public void warning(String msg, JElement element) {
      if (!(element instanceof JClass) && this.locations.add(element)) {
         if (!this.silent) {
            System.err.println(this.constructMessage("Warning", msg, element));
         }

         ++this.warningCount;
      }

   }

   public String constructMessage(String prefix, String msg, JElement element) {
      if (element == null) {
         return prefix + ": " + msg;
      } else {
         JSourcePosition pos = element.getSourcePosition();
         String loc = "(Unknown)";
         if (pos != null) {
            loc = pos.getSourceURI().getPath() + "(" + pos.getLine() + ")";
         }

         return prefix + ": " + loc + ": " + msg;
      }
   }
}
