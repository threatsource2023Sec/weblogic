package weblogic.management.mbeanservers.edit;

public interface Change {
   String MODIFY = "modify";
   String CREATE = "create";
   String DESTROY = "destroy";
   String ADD = "add";
   String REMOVE = "remove";
   String UNSET = "unset";
   String SERVER = "server";
   String PARTITION = "partition";
   String NONE = "none";

   Object getBean();

   String getAttributeName();

   String getOperation();

   Object getOldValue();

   Object getNewValue();

   boolean isRestartRequired();

   Object getAffectedBean();

   String getEntityToRestart();
}
