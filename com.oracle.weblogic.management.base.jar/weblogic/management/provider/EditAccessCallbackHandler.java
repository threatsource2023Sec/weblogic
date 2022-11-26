package weblogic.management.provider;

import java.io.IOException;
import java.util.Iterator;

public interface EditAccessCallbackHandler {
   void saveChanges();

   void undoUnsavedChanges();

   void undoUnactivatedChanges();

   void activateChanges() throws IOException;

   Iterator getChanges();

   Iterator getUnactivatedChanges();

   boolean isModified();

   void updateApplication();
}
