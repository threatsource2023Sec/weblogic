package org.apache.openjpa.kernel;

public interface OpCallbacks {
   int OP_PERSIST = 0;
   int OP_DELETE = 1;
   int OP_REFRESH = 2;
   int OP_RETRIEVE = 3;
   int OP_RELEASE = 4;
   int OP_EVICT = 5;
   int OP_ATTACH = 6;
   int OP_DETACH = 7;
   int OP_NONTRANSACTIONAL = 8;
   int OP_TRANSACTIONAL = 9;
   int OP_LOCK = 10;
   int ACT_NONE = 0;
   int ACT_CASCADE = 2;
   int ACT_RUN = 4;

   int processArgument(int var1, Object var2, OpenJPAStateManager var3);
}
