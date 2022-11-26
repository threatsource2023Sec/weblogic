package weblogic.logging.jms;

import java.util.logging.Level;

public class JMSMessageLevel extends Level {
   private static final int NON_PERSISTENT_INT = 100;
   private static final int PERSISTENT_INT = 1000;
   private static final String PERSISTENT = "Persistent";
   private static final String NON_PERSISTENT = "NonPersistent";
   public static final JMSMessageLevel NON_PERSISTENT_LEVEL = new JMSMessageLevel("NonPersistent", 100);
   public static final JMSMessageLevel PERSISTENT_LEVEL = new JMSMessageLevel("Persistent", 1000);

   private JMSMessageLevel(String name, int level) {
      super(name, level);
   }
}
