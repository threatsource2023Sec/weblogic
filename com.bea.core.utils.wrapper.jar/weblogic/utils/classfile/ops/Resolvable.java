package weblogic.utils.classfile.ops;

import weblogic.utils.classfile.BadBytecodesException;
import weblogic.utils.classfile.Bytecodes;

public interface Resolvable {
   boolean resolve(Bytecodes var1) throws BadBytecodesException;
}
