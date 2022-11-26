package org.apache.velocity.runtime.directive;

import java.util.Stack;

public class ParseDirectiveException extends Exception {
   private Stack filenameStack = new Stack();
   private String msg = "";
   private int depthCount = 0;

   ParseDirectiveException(String m, int i) {
      this.msg = m;
      this.depthCount = i;
   }

   public String getMessage() {
      String returnStr = "#parse() exception : depth = " + this.depthCount + " -> " + this.msg;
      returnStr = returnStr + " File stack : ";

      try {
         while(!this.filenameStack.empty()) {
            returnStr = returnStr + (String)this.filenameStack.pop();
            returnStr = returnStr + " -> ";
         }
      } catch (Exception var3) {
      }

      return returnStr;
   }

   public void addFile(String s) {
      this.filenameStack.push(s);
   }
}
