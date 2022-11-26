package weblogic.application.archive.collage;

import java.util.Collection;

public interface Directory extends Node {
   Node get(String var1);

   Collection getAll();
}
