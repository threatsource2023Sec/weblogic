package weblogic.management.provider;

public interface ResourceGroupMigrationTask {
   int getState();

   Exception getError();
}
