package com.ziclix.python.sql;

import org.python.core.PyInteger;
import org.python.core.Untraversable;

@Untraversable
public final class DBApiType extends PyInteger {
   public DBApiType(int type) {
      super(type);
   }

   public DBApiType(Integer type) {
      super(type);
   }
}
