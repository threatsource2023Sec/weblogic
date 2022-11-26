package org.opensaml.core.xml.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;

public class XMLObjectChildrenList extends AbstractList {
   private final XMLObject parent;
   private final List elements;

   public XMLObjectChildrenList(@Nonnull XMLObject newParent) {
      Constraint.isNotNull(newParent, "Parent cannot be null");
      this.parent = newParent;
      this.elements = new LazyList();
   }

   public XMLObjectChildrenList(@Nonnull XMLObject newParent, @Nonnull Collection newElements) {
      Constraint.isNotNull(newParent, "Parent cannot be null");
      Constraint.isNotNull(newElements, "Initial collection cannot be null");
      this.parent = newParent;
      this.elements = new LazyList();
      this.addAll(Collections2.filter(newElements, Predicates.notNull()));
   }

   public int size() {
      return this.elements.size();
   }

   public boolean contains(@Nonnull XMLObject element) {
      return this.elements.contains(element);
   }

   @Nonnull
   public XMLObject get(int index) {
      return (XMLObject)this.elements.get(index);
   }

   @Nullable
   public XMLObject set(int index, @Nullable XMLObject element) {
      if (element == null) {
         return null;
      } else {
         this.setParent(element);
         XMLObject removedElement = (XMLObject)this.elements.set(index, element);
         if (removedElement != null) {
            removedElement.setParent((XMLObject)null);
            this.parent.getIDIndex().deregisterIDMappings(removedElement.getIDIndex());
         }

         this.parent.getIDIndex().registerIDMappings(element.getIDIndex());
         ++this.modCount;
         return removedElement;
      }
   }

   public void add(int index, @Nullable XMLObject element) {
      if (element != null && !this.elements.contains(element)) {
         this.setParent(element);
         this.parent.getIDIndex().registerIDMappings(element.getIDIndex());
         ++this.modCount;
         this.elements.add(index, element);
      }
   }

   @Nonnull
   public XMLObject remove(int index) {
      XMLObject element = (XMLObject)this.elements.remove(index);
      if (element != null) {
         element.releaseParentDOM(true);
         element.setParent((XMLObject)null);
         this.parent.getIDIndex().deregisterIDMappings(element.getIDIndex());
      }

      ++this.modCount;
      return element;
   }

   public boolean remove(@Nullable XMLObject element) {
      boolean elementRemoved = this.elements.remove(element);
      if (elementRemoved && element != null) {
         element.releaseParentDOM(true);
         element.setParent((XMLObject)null);
         this.parent.getIDIndex().deregisterIDMappings(element.getIDIndex());
      }

      return elementRemoved;
   }

   protected void setParent(@Nonnull XMLObject element) {
      XMLObject elemParent = element.getParent();
      if (elemParent != null && elemParent != this.parent) {
         throw new IllegalArgumentException(element.getElementQName() + " is already the child of another XMLObject and may not be inserted into this list");
      } else {
         element.setParent(this.parent);
         element.releaseParentDOM(true);
      }
   }
}
