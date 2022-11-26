package com.sun.faces.facelets.impl;

import com.sun.faces.util.Cache;
import com.sun.faces.util.Util;
import java.util.concurrent.atomic.AtomicInteger;
import javax.faces.context.FacesContext;

public class IdMapper {
   private static final String KEY = IdMapper.class.getName();
   private Cache idCache = new Cache(new IdGen());

   IdMapper() {
   }

   public String getAliasedId(String id) {
      return (String)this.idCache.get(id);
   }

   public static void setMapper(FacesContext ctx, IdMapper mapper) {
      Util.notNull("ctx", ctx);
      if (mapper == null) {
         ctx.getAttributes().remove(KEY);
      } else {
         ctx.getAttributes().put(KEY, mapper);
      }

   }

   public static IdMapper getMapper(FacesContext ctx) {
      Util.notNull("ctx", ctx);
      return (IdMapper)ctx.getAttributes().get(KEY);
   }

   private static final class IdGen implements Cache.Factory {
      private AtomicInteger counter;

      private IdGen() {
         this.counter = new AtomicInteger(0);
      }

      public String newInstance(String arg) throws InterruptedException {
         return 't' + Integer.toString(this.counter.incrementAndGet());
      }

      // $FF: synthetic method
      IdGen(Object x0) {
         this();
      }
   }
}
