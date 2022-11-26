package weblogic.xml.util.cache.entitycache;

import java.util.Enumeration;
import java.util.Vector;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

class Persister extends WorkAdapter {
   static Persister persisterInstance = null;
   Vector persistQueue = new Vector();

   static Persister get() {
      Class var0 = Persister.class;
      synchronized(Persister.class) {
         if (persisterInstance == null) {
            persisterInstance = new Persister();
         }
      }

      return persisterInstance;
   }

   public void run() {
      synchronized(this) {
         try {
            Enumeration e = this.persistQueue.elements();

            while(true) {
               CacheEntry ce;
               do {
                  if (!e.hasMoreElements()) {
                     return;
                  }

                  ce = (CacheEntry)e.nextElement();
               } while(!ce.isPersistent());

               synchronized(ce.cache) {
                  try {
                     ce.saveEntry();
                  } catch (CX.EntryTooLargeDisk var8) {
                     ce.cache.notifyListener(new Event.EntryDiskRejectionEvent(ce.cache, ce));
                     ce.makeTransient(true);
                  } catch (Exception var9) {
                     ce.makeTransient(true);
                     continue;
                  }

                  ce.cache.notifyListener(new Event.EntryPersistEvent(ce.cache, ce));
               }

               this.persistQueue.removeAllElements();
            }
         } catch (Exception var11) {
            Tools.px(var11);
         }
      }
   }

   void add(CacheEntry ce) {
      synchronized(this) {
         if (ce.isPersistent()) {
            this.persistQueue.addElement(ce);
            WorkManagerFactory.getInstance().getSystem().schedule(this);
         }
      }
   }
}
