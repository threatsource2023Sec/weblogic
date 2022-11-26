package weblogic.management.rest.lib.utils;

import weblogic.descriptor.DescriptorBean;

public interface ConfigurationManagerWrapper {
   void startEdit(int var1, int var2) throws Exception;

   void startEdit(int var1, int var2, boolean var3) throws Exception;

   void removeReferencesToBean(DescriptorBean var1) throws Exception;

   boolean haveUnactivatedChanges() throws Exception;

   void undoUnactivatedChanges() throws Exception;

   void save() throws Exception;

   ActivationTaskWrapper resolve(boolean var1, long var2) throws Exception;

   ActivationTaskWrapper activate(long var1) throws Exception;

   void stopEdit() throws Exception;

   void cancelEdit() throws Exception;

   String getCurrentEditor() throws Exception;

   boolean isCurrentEditorExpired() throws Exception;

   boolean isCurrentEditorExclusive() throws Exception;

   boolean isEditor() throws Exception;
}
