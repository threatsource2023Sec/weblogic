package weblogic.ejb.container.interfaces;

import java.util.concurrent.TimeUnit;

public interface ConcurrencyInfo {
   String getConcurrentLockType();

   long getTimeout();

   TimeUnit getTimeoutUnit();
}
