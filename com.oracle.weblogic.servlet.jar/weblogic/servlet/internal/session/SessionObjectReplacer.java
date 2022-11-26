package weblogic.servlet.internal.session;

import java.io.IOException;
import weblogic.utils.io.Replacer;

public class SessionObjectReplacer implements Replacer {
   private Replacer nextReplacer;

   public static SessionObjectReplacer getInstance() {
      return SessionObjectReplacer.SingletonMaker.singleton;
   }

   private SessionObjectReplacer() {
   }

   public void insertReplacer(Replacer replacer) {
      this.nextReplacer = replacer;
   }

   public Object replaceObject(Object o) throws IOException {
      return this.nextReplacer == null ? o : this.nextReplacer.replaceObject(o);
   }

   public Object resolveObject(Object o) throws IOException {
      return this.nextReplacer == null ? o : this.nextReplacer.resolveObject(o);
   }

   // $FF: synthetic method
   SessionObjectReplacer(Object x0) {
      this();
   }

   private static class SingletonMaker {
      static final SessionObjectReplacer singleton = new SessionObjectReplacer();
   }
}
