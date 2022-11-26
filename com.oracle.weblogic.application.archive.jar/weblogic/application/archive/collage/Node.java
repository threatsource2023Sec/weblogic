package weblogic.application.archive.collage;

import java.io.File;

public interface Node {
   String getName();

   File getFile();

   String getSource();

   long getTime();
}
