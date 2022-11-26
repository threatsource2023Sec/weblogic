package org.apache.openjpa.xmlstore;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.openjpa.meta.ClassMetaData;

public class XMLStore {
   private final XMLConfiguration _conf;
   private final Map _metaOidMaps = new HashMap();
   private boolean _locked;

   public XMLStore(XMLConfiguration conf) {
      this._conf = conf;
   }

   public synchronized ObjectData getData(ClassMetaData meta, Object oid) {
      meta = getLeastDerived(meta);
      return (ObjectData)this.getMap(meta).get(oid);
   }

   public synchronized ObjectData[] getData(ClassMetaData meta) {
      meta = getLeastDerived(meta);
      Collection vals = this.getMap(meta).values();
      return (ObjectData[])((ObjectData[])vals.toArray(new ObjectData[vals.size()]));
   }

   private Map getMap(ClassMetaData meta) {
      Map m = (Map)this._metaOidMaps.get(meta);
      if (m != null) {
         return m;
      } else {
         Collection datas = this._conf.getFileHandler().load(meta);
         Map m = new HashMap(datas.size());
         Iterator itr = datas.iterator();

         while(itr.hasNext()) {
            ObjectData data = (ObjectData)itr.next();
            m.put(data.getId(), data);
         }

         this._metaOidMaps.put(meta, m);
         return m;
      }
   }

   private static ClassMetaData getLeastDerived(ClassMetaData meta) {
      while(meta.getPCSuperclass() != null) {
         meta = meta.getPCSuperclassMetaData();
      }

      return meta;
   }

   public synchronized void beginTransaction() {
      while(this._locked) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

      this._locked = true;
   }

   public synchronized void endTransaction(Collection updates, Collection deletes) {
      Set dirty = new HashSet();

      try {
         Iterator itr;
         ObjectData data;
         ClassMetaData meta;
         if (updates != null) {
            itr = updates.iterator();

            while(itr.hasNext()) {
               data = (ObjectData)itr.next();
               meta = getLeastDerived(data.getMetaData());
               this.getMap(meta).put(data.getId(), data);
               dirty.add(meta);
            }
         }

         if (deletes != null) {
            itr = deletes.iterator();

            while(itr.hasNext()) {
               data = (ObjectData)itr.next();
               meta = getLeastDerived(data.getMetaData());
               this.getMap(meta).remove(data.getId());
               dirty.add(meta);
            }
         }

         XMLFileHandler fh = this._conf.getFileHandler();
         Iterator itr = dirty.iterator();

         while(itr.hasNext()) {
            meta = (ClassMetaData)itr.next();
            fh.store(meta, this.getMap(meta).values());
         }
      } finally {
         this.notify();
         this._locked = false;
      }

   }
}
