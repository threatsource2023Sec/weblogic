package org.opensaml.core.xml.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;

@NotThreadSafe
public class MapLoadSaveManager implements XMLObjectLoadSaveManager {
   private Map backingMap;

   public MapLoadSaveManager() {
      this(new HashMap());
   }

   public MapLoadSaveManager(@Nonnull Map map) {
      this.backingMap = (Map)Constraint.isNotNull(map, "Backing map was null");
   }

   public Set listKeys() throws IOException {
      return this.backingMap.keySet();
   }

   public Iterable listAll() throws IOException {
      ArrayList list = new ArrayList();
      Iterator var2 = this.listKeys().iterator();

      while(var2.hasNext()) {
         String key = (String)var2.next();
         list.add(new Pair(key, this.load(key)));
      }

      return list;
   }

   public boolean exists(String key) throws IOException {
      return this.backingMap.containsKey(key);
   }

   public XMLObject load(String key) throws IOException {
      return (XMLObject)this.backingMap.get(key);
   }

   public void save(String key, XMLObject xmlObject) throws IOException {
      this.save(key, xmlObject, false);
   }

   public void save(String key, XMLObject xmlObject, boolean overwrite) throws IOException {
      if (!overwrite && this.exists(key)) {
         throw new IOException(String.format("Value already exists for key '%s'", key));
      } else {
         this.backingMap.put(key, xmlObject);
      }
   }

   public boolean remove(String key) throws IOException {
      return this.backingMap.remove(key) != null;
   }

   public boolean updateKey(String currentKey, String newKey) throws IOException {
      XMLObject value = this.load(currentKey);
      if (value != null) {
         this.save(newKey, value, false);
         this.remove(currentKey);
         return true;
      } else {
         return false;
      }
   }
}
