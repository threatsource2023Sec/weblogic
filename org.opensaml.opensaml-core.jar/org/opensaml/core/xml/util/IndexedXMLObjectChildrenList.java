package org.opensaml.core.xml.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;

@NotThreadSafe
public class IndexedXMLObjectChildrenList extends XMLObjectChildrenList {
   private final Map objectIndex;

   public IndexedXMLObjectChildrenList(@Nonnull XMLObject parent) {
      super(parent);
      this.objectIndex = new LazyMap();
   }

   public IndexedXMLObjectChildrenList(@Nonnull XMLObject parent, @Nonnull Collection col) {
      super(parent);
      Constraint.isNotNull(col, "Initial collection cannot be null");
      this.objectIndex = new LazyMap();
      this.addAll(Collections2.filter(col, Predicates.notNull()));
   }

   public void add(int index, @Nullable XMLObject element) {
      super.add(index, element);
      this.indexElement(element);
   }

   public void clear() {
      super.clear();
      this.objectIndex.clear();
   }

   @Nonnull
   public List get(@Nonnull QName typeOrName) {
      this.checkAndCreateIndex(typeOrName);
      return (List)this.objectIndex.get(typeOrName);
   }

   protected void checkAndCreateIndex(@Nonnull QName index) {
      if (!this.objectIndex.containsKey(index)) {
         this.objectIndex.put(index, new LazyList());
      }

   }

   protected void indexElement(@Nullable XMLObject element) {
      if (element != null) {
         QName type = element.getSchemaType();
         if (type != null) {
            this.indexElement(type, element);
         }

         this.indexElement(element.getElementQName(), element);
      }
   }

   protected void indexElement(@Nonnull QName index, @Nullable XMLObject element) {
      List objects = this.get(index);
      objects.add(element);
   }

   public boolean remove(@Nullable XMLObject element) {
      boolean elementRemoved = super.remove(element);
      if (elementRemoved) {
         this.removeElementFromIndex(element);
      }

      return elementRemoved;
   }

   @Nonnull
   public XMLObject remove(int index) {
      XMLObject returnValue = super.remove(index);
      this.removeElementFromIndex(returnValue);
      return returnValue;
   }

   protected void removeElementFromIndex(@Nullable XMLObject element) {
      if (element != null) {
         QName type = element.getSchemaType();
         if (type != null) {
            this.removeElementFromIndex(type, element);
         }

         this.removeElementFromIndex(element.getElementQName(), element);
      }
   }

   protected void removeElementFromIndex(@Nonnull QName index, @Nullable XMLObject element) {
      List objects = this.get(index);
      objects.remove(element);
   }

   @Nullable
   public XMLObject set(int index, @Nullable XMLObject element) {
      XMLObject returnValue = super.set(index, element);
      this.removeElementFromIndex(returnValue);
      this.indexElement(element);
      return returnValue;
   }

   @Nonnull
   public List subList(@Nonnull QName index) {
      this.checkAndCreateIndex(index);
      return new ListView(this, index);
   }
}
