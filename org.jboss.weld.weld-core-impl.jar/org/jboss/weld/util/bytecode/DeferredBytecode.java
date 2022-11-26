package org.jboss.weld.util.bytecode;

import org.jboss.classfilewriter.code.CodeAttribute;

public interface DeferredBytecode {
   void apply(CodeAttribute var1);
}
