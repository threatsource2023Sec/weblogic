package org.python.modules;

import org.python.modules.posix.PosixModule;

public class Setup {
   public static String[] builtinModules = new String[]{"_ast:org.python.antlr.ast.AstModule", "_bytecodetools", "_codecs", "_collections:org.python.modules._collections.Collections", "_csv:org.python.modules._csv._csv", "_functools:org.python.modules._functools._functools", "_hashlib", "_jyio:org.python.modules._io._jyio", "_json:org.python.modules._json._json", "_jythonlib:org.python.modules._jythonlib._jythonlib", "_marshal", "_py_compile", "_random:org.python.modules.random.RandomModule", "_sre", "_systemrestart", "_threading:org.python.modules._threading._threading", "_weakref:org.python.modules._weakref.WeakrefModule", "array:org.python.modules.ArrayModule", "binascii", "bz2:org.python.modules.bz2.bz2", "cPickle", "cStringIO", "cmath", "errno", "exceptions:org.python.core.exceptions", "gc", "_imp:org.python.modules._imp", "itertools:org.python.modules.itertools.itertools", "jarray", "jffi:org.python.modules.jffi.jffi", "math", "operator", "struct", "synchronize", "thread:org.python.modules.thread.thread", "time:org.python.modules.time.Time", "ucnhash", "zipimport:org.python.modules.zipimport.zipimport", PosixModule.getOSName() + ":org.python.modules.posix.PosixModule"};
}
