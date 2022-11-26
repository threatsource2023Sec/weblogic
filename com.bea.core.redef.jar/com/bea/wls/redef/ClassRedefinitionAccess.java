package com.bea.wls.redef;

import com.bea.wls.redef.agent.ClassRedefiner;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Arrays;
import java.util.List;

public class ClassRedefinitionAccess {
   private final RedefiningClassLoader loader;

   ClassRedefinitionAccess(RedefiningClassLoader cl) {
      this.loader = cl;
   }

   public void redefineClass(Class c, byte[] bytes) throws ClassNotFoundException, UnmodifiableClassException {
      ClassDefinition[] cd = new ClassDefinition[]{new ClassDefinition(c, bytes)};
      this.redefineClasses(Arrays.asList(cd));
   }

   public void redefineClasses(List classDefs) throws ClassNotFoundException, UnmodifiableClassException {
      this.loader.updateMetaData(classDefs);
      ClassRedefiner.redefineClass((ClassDefinition[])classDefs.toArray(new ClassDefinition[classDefs.size()]));
   }
}
