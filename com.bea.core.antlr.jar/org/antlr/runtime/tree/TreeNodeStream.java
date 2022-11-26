package org.antlr.runtime.tree;

import org.antlr.runtime.IntStream;
import org.antlr.runtime.TokenStream;

public interface TreeNodeStream extends IntStream {
   Object get(int var1);

   Object LT(int var1);

   Object getTreeSource();

   TokenStream getTokenStream();

   TreeAdaptor getTreeAdaptor();

   void setUniqueNavigationNodes(boolean var1);

   void reset();

   String toString(Object var1, Object var2);

   void replaceChildren(Object var1, int var2, int var3, Object var4);
}
