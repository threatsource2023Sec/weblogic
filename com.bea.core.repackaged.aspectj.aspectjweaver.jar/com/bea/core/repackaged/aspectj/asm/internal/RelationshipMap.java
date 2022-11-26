package com.bea.core.repackaged.aspectj.asm.internal;

import com.bea.core.repackaged.aspectj.asm.IProgramElement;
import com.bea.core.repackaged.aspectj.asm.IRelationship;
import com.bea.core.repackaged.aspectj.asm.IRelationshipMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RelationshipMap extends HashMap implements IRelationshipMap {
   private static final long serialVersionUID = 496638323566589643L;

   public List get(String handle) {
      List relationships = (List)super.get(handle);
      return relationships == null ? null : relationships;
   }

   public List get(IProgramElement source) {
      return this.get(source.getHandleIdentifier());
   }

   public IRelationship get(String source, IRelationship.Kind kind, String relationshipName, boolean runtimeTest, boolean createIfMissing) {
      List relationships = this.get(source);
      Relationship rel;
      if (relationships == null) {
         if (!createIfMissing) {
            return null;
         } else {
            List relationships = new ArrayList();
            rel = new Relationship(relationshipName, kind, source, new ArrayList(), runtimeTest);
            relationships.add(rel);
            super.put(source, relationships);
            return rel;
         }
      } else {
         Iterator it = relationships.iterator();

         IRelationship curr;
         do {
            if (!it.hasNext()) {
               if (createIfMissing) {
                  rel = new Relationship(relationshipName, kind, source, new ArrayList(), runtimeTest);
                  relationships.add(rel);
                  return rel;
               }

               return null;
            }

            curr = (IRelationship)it.next();
         } while(curr.getKind() != kind || !curr.getName().equals(relationshipName) || curr.hasRuntimeTest() != runtimeTest);

         return curr;
      }
   }

   public IRelationship get(IProgramElement source, IRelationship.Kind kind, String relationshipName, boolean runtimeTest, boolean createIfMissing) {
      return this.get(source.getHandleIdentifier(), kind, relationshipName, runtimeTest, createIfMissing);
   }

   public IRelationship get(IProgramElement source, IRelationship.Kind kind, String relationshipName) {
      return this.get(source, kind, relationshipName, false, true);
   }

   public boolean remove(String source, IRelationship relationship) {
      List list = (List)super.get(source);
      return list != null ? list.remove(relationship) : false;
   }

   public void removeAll(String source) {
      super.remove(source);
   }

   public void put(String source, IRelationship relationship) {
      List existingRelationships = (List)super.get(source);
      if (existingRelationships == null) {
         List existingRelationships = new ArrayList();
         existingRelationships.add(relationship);
         super.put(source, existingRelationships);
      } else {
         boolean matched = false;
         Iterator i$ = existingRelationships.iterator();

         while(i$.hasNext()) {
            IRelationship existingRelationship = (IRelationship)i$.next();
            if (existingRelationship.getName().equals(relationship.getName()) && existingRelationship.getKind() == relationship.getKind()) {
               existingRelationship.getTargets().addAll(relationship.getTargets());
               matched = true;
            }
         }

         if (matched) {
            System.err.println("matched = true");
         }

         if (matched) {
            existingRelationships.add(relationship);
         }
      }

   }

   public void put(IProgramElement source, IRelationship relationship) {
      this.put(source.getHandleIdentifier(), relationship);
   }

   public void clear() {
      super.clear();
   }

   public Set getEntries() {
      return this.keySet();
   }
}
