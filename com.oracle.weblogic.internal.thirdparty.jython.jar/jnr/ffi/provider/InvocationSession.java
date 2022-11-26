package jnr.ffi.provider;

import java.util.ArrayList;
import java.util.Iterator;

public class InvocationSession {
   private ArrayList list;
   private ArrayList liveObjects;

   public void finish() {
      if (this.list != null) {
         Iterator var1 = this.list.iterator();

         while(var1.hasNext()) {
            PostInvoke p = (PostInvoke)var1.next();

            try {
               p.postInvoke();
            } catch (Throwable var4) {
            }
         }
      }

   }

   public void addPostInvoke(PostInvoke postInvoke) {
      if (this.list == null) {
         this.list = new ArrayList();
      }

      this.list.add(postInvoke);
   }

   public void keepAlive(Object obj) {
      if (this.liveObjects == null) {
         this.liveObjects = new ArrayList();
      }

      this.liveObjects.add(obj);
   }

   public interface PostInvoke {
      void postInvoke();
   }
}
