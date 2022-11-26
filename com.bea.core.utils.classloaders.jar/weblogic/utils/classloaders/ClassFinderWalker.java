package weblogic.utils.classloaders;

import java.util.Iterator;

public interface ClassFinderWalker {
   Iterator iterator(String var1);

   void handlePostFreezeAddition(int var1, ClassFinder var2);
}
