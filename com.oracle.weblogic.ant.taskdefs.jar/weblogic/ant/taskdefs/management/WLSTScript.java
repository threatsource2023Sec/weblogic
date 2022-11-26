package weblogic.ant.taskdefs.management;

import org.apache.tools.ant.Task;

public class WLSTScript extends Task {
   private String line = null;
   private boolean replaceProperties = false;

   public void addText(String theLine) {
      this.line = theLine;
   }

   public void setReplaceProperties(boolean value) {
      this.replaceProperties = value;
   }

   public String getScript() {
      return this.replaceProperties ? this.getProject().replaceProperties(this.line) : this.line;
   }
}
