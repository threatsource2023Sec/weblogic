package weblogic.ant.taskdefs.management;

public interface MBeanCommand {
   int MBEAN_CREATE_COMMAND = 1;
   int MBEAN_DELETE_COMMAND = 2;
   int MBEAN_SET_COMMAND = 3;
   int MBEAN_GET_COMMAND = 4;
   int MBEAN_QUERY_COMMAND = 5;
   int MBEAN_DEPLOY_COMMAND = 6;
   int MBEAN_INVOKE_COMMAND = 7;

   int getCommandType();
}
