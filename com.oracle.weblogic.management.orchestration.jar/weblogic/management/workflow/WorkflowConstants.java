package weblogic.management.workflow;

import java.io.File;

public class WorkflowConstants {
   public static final String GLOBAL_ID = "0";
   public static final String GLOBAL_NAME = "DOMAIN";
   public static final String ORCHESTRATION_DIR_NAME = "orchestration";
   public static final String WORKFLOW_DIR_NAME = "workflow";
   public static final String sep;

   static {
      sep = File.separator;
   }
}
