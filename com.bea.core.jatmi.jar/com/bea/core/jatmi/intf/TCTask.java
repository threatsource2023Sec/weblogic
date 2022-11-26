package com.bea.core.jatmi.intf;

public interface TCTask {
   int TASK_RC_DONE = 0;
   int TASK_RC_BLOCKED = 1;
   int TASK_RC_CONTINUE = 2;

   int execute();

   void setTaskName(String var1);

   String getTaskName();
}
