package org.python.core.util;

import java.io.File;
import org.python.core.PySystemState;

public class RelativeFile extends File {
   public RelativeFile(String pathname) {
      super(PySystemState.getPathLazy(pathname));
   }

   public RelativeFile(String parent, String child) {
      super(PySystemState.getPathLazy(parent), child);
   }
}
