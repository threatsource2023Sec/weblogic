package org.antlr.tool;

import org.stringtemplate.v4.ST;

public class ToolMessage extends Message {
   public ToolMessage(int msgID) {
      super(msgID, (Object)null, (Object)null);
   }

   public ToolMessage(int msgID, Object arg) {
      super(msgID, arg, (Object)null);
   }

   public ToolMessage(int msgID, Throwable e) {
      super(msgID);
      this.e = e;
   }

   public ToolMessage(int msgID, Object arg, Object arg2) {
      super(msgID, arg, arg2);
   }

   public ToolMessage(int msgID, Object arg, Throwable e) {
      super(msgID, arg, (Object)null);
      this.e = e;
   }

   public String toString() {
      ST st = this.getMessageTemplate();
      if (this.arg != null) {
         st.add("arg", this.arg);
      }

      if (this.arg2 != null) {
         st.add("arg2", this.arg2);
      }

      if (this.e != null) {
         st.add("exception", this.e);
         st.add("stackTrace", this.e.getStackTrace());
      }

      return super.toString(st);
   }
}
