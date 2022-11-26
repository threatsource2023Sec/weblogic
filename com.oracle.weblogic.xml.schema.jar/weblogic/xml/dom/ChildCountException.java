package weblogic.xml.dom;

import java.io.PrintWriter;

public class ChildCountException extends DOMProcessingException {
   public static final int MISSING_CHILD = 1;
   public static final int EXTRA_CHILD = 2;
   private static final String[] errorMsgs = new String[]{"missing child", "extra child"};
   private int errorCode;
   private String parentTagName;
   private String childTagName;
   private int childCount;

   public ChildCountException(int errorCode, String parentTagName, String childTagName, int childCount) {
      this.errorCode = errorCode;
      this.parentTagName = parentTagName;
      this.childTagName = childTagName;
      this.childCount = childCount;
   }

   public void writeErrorCondition(PrintWriter pw) {
      pw.println("[Begin ChildCountException:");
      pw.println("\terrorCode: " + this.errorCode);
      pw.println("\tparentTagName: " + this.parentTagName);
      pw.println("\tchildTagName: " + this.childTagName);
      pw.println("\tchildCount: " + this.childCount);
      pw.println("End ChildCountException]");
   }

   public String toString() {
      return super.toString() + ": " + errorMsgs[this.errorCode - 1] + " " + this.childTagName + " in " + this.parentTagName;
   }
}
