package org.python.compiler;

public interface ScopeConstants {
   int BOUND = 1;
   int NGLOBAL = 2;
   int PARAM = 4;
   int FROM_PARAM = 8;
   int CELL = 16;
   int FREE = 32;
   int CLASS_GLOBAL = 64;
   int GLOBAL = 66;
   int TOPSCOPE = 0;
   int FUNCSCOPE = 1;
   int CLASSSCOPE = 2;
}
