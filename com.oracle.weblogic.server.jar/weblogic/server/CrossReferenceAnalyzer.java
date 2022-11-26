package weblogic.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class CrossReferenceAnalyzer {
   private final Map addedReferences = new HashMap();
   private final Map removedReferences = new HashMap();
   private final Set addedOrigins = new HashSet();
   private final Set removedOrigins = new HashSet();

   CrossReferenceAnalyzer(Iterator originalReferences, Iterator currentReferences) {
      Map originalRefs = new HashMap();
      Map currentRefs = new HashMap();
      Map.Entry entry;
      if (originalReferences != null) {
         while(originalReferences.hasNext()) {
            entry = (Map.Entry)originalReferences.next();
            originalRefs.put(entry.getKey(), entry.getValue());
         }
      }

      if (currentReferences != null) {
         while(currentReferences.hasNext()) {
            entry = (Map.Entry)currentReferences.next();
            currentRefs.put(entry.getKey(), entry.getValue());
         }
      }

      this.addedOrigins.addAll(currentRefs.keySet());
      this.addedOrigins.removeAll(originalRefs.keySet());
      this.removedOrigins.addAll(originalRefs.keySet());
      this.removedOrigins.removeAll(currentRefs.keySet());
      Iterator var11 = currentRefs.entrySet().iterator();

      while(var11.hasNext()) {
         Map.Entry currentRef = (Map.Entry)var11.next();
         Set refsForOriginal = Collections.EMPTY_SET;
         Set addedReferentsForThisOrigin = new HashSet((Collection)currentRef.getValue());
         Set removedReferentsForThisOrigin = new HashSet();
         Set originalReferentsForThisOrigin = (Set)originalRefs.get(currentRef.getKey());
         if (originalReferentsForThisOrigin != null) {
            addedReferentsForThisOrigin.removeAll(originalReferentsForThisOrigin);
            removedReferentsForThisOrigin.addAll(originalReferentsForThisOrigin);
         }

         removedReferentsForThisOrigin.removeAll((Collection)currentRef.getValue());
         if (!removedReferentsForThisOrigin.isEmpty()) {
            this.removedReferences.put(currentRef.getKey(), removedReferentsForThisOrigin);
         }

         if (!addedReferentsForThisOrigin.isEmpty()) {
            this.addedReferences.put(currentRef.getKey(), addedReferentsForThisOrigin);
         }
      }

      var11 = this.removedOrigins.iterator();

      while(var11.hasNext()) {
         Object removedOrigin = var11.next();
         this.removedReferences.put(removedOrigin, originalRefs.get(removedOrigin));
      }

   }

   boolean isOriginAdded(Object origin) {
      return this.addedOrigins.contains(origin);
   }

   boolean isOriginRemoved(Object origin) {
      return this.removedOrigins.contains(origin);
   }

   boolean isReferenceAdded(Object origin, Object reference) {
      Set refs = (Set)this.addedReferences.get(origin);
      return refs != null && refs.contains(reference);
   }

   boolean isReferenceRemoved(Object origin, Object reference) {
      Set refs = (Set)this.removedReferences.get(origin);
      return refs != null && refs.contains(reference);
   }

   Collection getOriginsLosingAndGainingReferences() {
      Collection result = new ArrayList();
      Iterator var2 = this.addedReferences.keySet().iterator();

      while(var2.hasNext()) {
         Object origin = var2.next();
         Set references = (Set)this.removedReferences.get(origin);
         if (references != null && !references.isEmpty()) {
            result.add(origin);
         }
      }

      return result;
   }
}
