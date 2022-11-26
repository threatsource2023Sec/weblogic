package weblogic.tools.ui;

import java.awt.Component;

public class ValidationFeedback implements IValidationFeedback {
   private String errmsg;
   private Component comp;

   public ValidationFeedback(String errmsg, Component comp) {
      this.errmsg = errmsg;
      this.comp = comp;
   }

   public String getMessage() {
      return this.errmsg;
   }

   public void setMessage(String s) {
      this.errmsg = s;
   }

   public Component getFocusComponent() {
      return this.comp;
   }

   public void setFocusComponent(Component c) {
      this.comp = c;
   }
}
