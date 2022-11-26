package com.bea.core.repackaged.aspectj.asm;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public interface IRelationshipMap extends Serializable {
   List get(IProgramElement var1);

   List get(String var1);

   IRelationship get(IProgramElement var1, IRelationship.Kind var2, String var3, boolean var4, boolean var5);

   IRelationship get(IProgramElement var1, IRelationship.Kind var2, String var3);

   IRelationship get(String var1, IRelationship.Kind var2, String var3, boolean var4, boolean var5);

   void put(IProgramElement var1, IRelationship var2);

   void put(String var1, IRelationship var2);

   boolean remove(String var1, IRelationship var2);

   void removeAll(String var1);

   void clear();

   Set getEntries();
}
