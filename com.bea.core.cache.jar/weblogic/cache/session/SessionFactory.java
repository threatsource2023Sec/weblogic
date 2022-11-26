package weblogic.cache.session;

import weblogic.cache.CacheMap;

public final class SessionFactory {
   public static SessionFactory getInstance() {
      return SessionFactory.Factory.THE_ONE;
   }

   public Session create(CacheMap map) {
      return new SessionImpl(map);
   }

   private static final class Factory {
      static final SessionFactory THE_ONE = new SessionFactory();
   }
}
