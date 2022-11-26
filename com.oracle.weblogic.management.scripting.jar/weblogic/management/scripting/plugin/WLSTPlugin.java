package weblogic.management.scripting.plugin;

import org.python.util.InteractiveInterpreter;

public abstract class WLSTPlugin {
   public static InteractiveInterpreter interpreter = null;

   public static void setInterpreter(InteractiveInterpreter interp) {
      interpreter = interp;
   }

   public static void initialize() {
   }
}
