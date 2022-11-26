package weblogic.tools.ui;

import java.awt.Component;

public interface IValidationFeedback {
   String getMessage();

   void setMessage(String var1);

   Component getFocusComponent();

   void setFocusComponent(Component var1);
}
