package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;

public class EmptyFileFilter extends AbstractFileFilter implements Serializable {
   private static final long serialVersionUID = 3631422087512832211L;
   public static final IOFileFilter EMPTY = new EmptyFileFilter();
   public static final IOFileFilter NOT_EMPTY;

   protected EmptyFileFilter() {
   }

   public boolean accept(File file) {
      if (!file.isDirectory()) {
         return file.length() == 0L;
      } else {
         File[] files = file.listFiles();
         return files == null || files.length == 0;
      }
   }

   static {
      NOT_EMPTY = new NotFileFilter(EMPTY);
   }
}
