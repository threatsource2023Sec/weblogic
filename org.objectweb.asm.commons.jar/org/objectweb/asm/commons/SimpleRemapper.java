package org.objectweb.asm.commons;

import java.util.Collections;
import java.util.Map;

public class SimpleRemapper extends Remapper {
   private final Map mapping;

   public SimpleRemapper(Map mapping) {
      this.mapping = mapping;
   }

   public SimpleRemapper(String oldName, String newName) {
      this.mapping = Collections.singletonMap(oldName, newName);
   }

   public String mapMethodName(String owner, String name, String descriptor) {
      String remappedName = this.map(owner + '.' + name + descriptor);
      return remappedName == null ? name : remappedName;
   }

   public String mapInvokeDynamicMethodName(String name, String descriptor) {
      String remappedName = this.map('.' + name + descriptor);
      return remappedName == null ? name : remappedName;
   }

   public String mapFieldName(String owner, String name, String descriptor) {
      String remappedName = this.map(owner + '.' + name);
      return remappedName == null ? name : remappedName;
   }

   public String map(String key) {
      return (String)this.mapping.get(key);
   }
}
