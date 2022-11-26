package serp.bytecode;

import serp.bytecode.lowlevel.ConstantPool;

public interface BCEntity {
   Project getProject();

   ConstantPool getPool();

   ClassLoader getClassLoader();

   boolean isValid();
}
