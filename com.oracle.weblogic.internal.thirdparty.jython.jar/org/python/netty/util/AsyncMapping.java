package org.python.netty.util;

import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.Promise;

public interface AsyncMapping {
   Future map(Object var1, Promise var2);
}
