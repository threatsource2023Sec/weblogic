package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface DefaultPersistentStoreBean extends SettableBean {
   String getNotes();

   void setNotes(String var1);

   String getDirectoryPath();

   void setDirectoryPath(String var1);

   String getSynchronousWritePolicy();

   void setSynchronousWritePolicy(String var1);
}
