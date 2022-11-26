package org.apache.openjpa.jdbc.kernel.exps;

import org.apache.openjpa.kernel.exps.Path;

interface JDBCPath extends Path, Val {
   void getKey();
}
