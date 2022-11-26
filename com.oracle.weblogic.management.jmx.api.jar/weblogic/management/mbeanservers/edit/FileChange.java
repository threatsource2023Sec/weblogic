package weblogic.management.mbeanservers.edit;

import java.io.Serializable;

public interface FileChange extends Serializable {
   String EDIT = "edit";
   String ADD = "add";
   String REMOVE = "remove";

   String getPath();

   String getOperation();

   long getCurrentLastModifiedTime();

   long getProposedLastModifiedTime();
}
