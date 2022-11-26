package org.opensaml.core.xml.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;

@NotThreadSafe
public class IDIndex {
   @Nonnull
   private final XMLObject owner;
   @Nonnull
   private Map idMappings;

   public IDIndex(@Nonnull XMLObject newOwner) {
      Constraint.isNotNull(newOwner, "ID-owning XMLObject may not be null");
      this.owner = newOwner;
      this.idMappings = new LazyMap();
   }

   public void registerIDMapping(@Nonnull @NotEmpty String id, @Nonnull XMLObject referent) {
      if (id != null) {
         this.idMappings.put(id, referent);
         if (this.owner.hasParent()) {
            this.owner.getParent().getIDIndex().registerIDMapping(id, referent);
         }

      }
   }

   public void registerIDMappings(@Nonnull IDIndex idIndex) {
      if (idIndex != null && !idIndex.isEmpty()) {
         this.idMappings.putAll(idIndex.getIDMappings());
         if (this.owner.hasParent()) {
            this.owner.getParent().getIDIndex().registerIDMappings(idIndex);
         }

      }
   }

   public void deregisterIDMapping(@Nonnull @NotEmpty String id) {
      if (id != null) {
         this.idMappings.remove(id);
         if (this.owner.hasParent()) {
            this.owner.getParent().getIDIndex().deregisterIDMapping(id);
         }

      }
   }

   public void deregisterIDMappings(@Nonnull IDIndex idIndex) {
      if (idIndex != null && !idIndex.isEmpty()) {
         Iterator var2 = idIndex.getIDs().iterator();

         while(var2.hasNext()) {
            String id = (String)var2.next();
            this.idMappings.remove(id);
         }

         if (this.owner.hasParent()) {
            this.owner.getParent().getIDIndex().deregisterIDMappings(idIndex);
         }

      }
   }

   @Nullable
   public XMLObject lookup(@Nonnull @NotEmpty String id) {
      return (XMLObject)this.idMappings.get(id);
   }

   public boolean isEmpty() {
      return this.idMappings.isEmpty();
   }

   @Nonnull
   public Set getIDs() {
      return Collections.unmodifiableSet(this.idMappings.keySet());
   }

   @Nonnull
   protected Map getIDMappings() {
      return Collections.unmodifiableMap(this.idMappings);
   }
}
