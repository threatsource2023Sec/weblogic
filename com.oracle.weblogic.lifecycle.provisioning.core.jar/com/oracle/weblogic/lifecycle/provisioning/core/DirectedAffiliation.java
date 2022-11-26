package com.oracle.weblogic.lifecycle.provisioning.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class DirectedAffiliation {
   private final Object principal;
   private final Set affiliates;

   public DirectedAffiliation(Object principal, Set affiliates) {
      Objects.requireNonNull(principal);
      Objects.requireNonNull(affiliates);
      if (affiliates.isEmpty()) {
         throw new IllegalArgumentException("affiliates.isEmpty()");
      } else {
         this.principal = principal;
         Set validAffiliates = new HashSet();
         Iterator var4 = affiliates.iterator();

         while(var4.hasNext()) {
            Object affiliate = var4.next();
            if (affiliate != null && !principal.equals(affiliate)) {
               validAffiliates.add(affiliate);
            }
         }

         if (validAffiliates.isEmpty()) {
            throw new IllegalArgumentException("affiliates contained only principal");
         } else {
            this.affiliates = Collections.unmodifiableSet(validAffiliates);
         }
      }
   }

   public Set getAffiliates() {
      return this.affiliates;
   }

   public Object getPrincipal() {
      return this.principal;
   }

   public int size() {
      Collection affiliates = this.getAffiliates();
      int returnValue;
      if (affiliates != null && !affiliates.isEmpty()) {
         returnValue = affiliates.size();
      } else {
         returnValue = 0;
      }

      return returnValue;
   }

   public boolean contains(Object affiliate) {
      boolean returnValue;
      if (affiliate == null) {
         returnValue = false;
      } else {
         Collection affiliates = this.getAffiliates();
         if (affiliates != null && !affiliates.isEmpty()) {
            returnValue = affiliates.contains(affiliate);
         } else {
            returnValue = false;
         }
      }

      return returnValue;
   }

   public int hashCode() {
      int hashCode = 17;
      Object principal = this.getPrincipal();
      int c = principal == null ? 0 : principal.hashCode();
      hashCode = hashCode * 37 + c;
      Object affiliates = this.getAffiliates();
      c = affiliates == null ? 0 : affiliates.hashCode();
      hashCode = hashCode * 37 + c;
      return hashCode;
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else if (other instanceof DirectedAffiliation) {
         DirectedAffiliation him = (DirectedAffiliation)other;
         Object principal = this.getPrincipal();
         if (principal == null) {
            if (him.getPrincipal() != null) {
               return false;
            }
         } else if (!principal.equals(him.getPrincipal())) {
            return false;
         }

         Object affiliates = this.getAffiliates();
         if (affiliates == null) {
            if (him.getAffiliates() != null) {
               return false;
            }
         } else if (!affiliates.equals(him.getAffiliates())) {
            return false;
         }

         return true;
      } else {
         return false;
      }
   }

   public String toString() {
      return this.getPrincipal() + " --> " + this.getAffiliates();
   }

   public static final Set combine(Set affiliations) {
      Set returnValue = null;
      if (affiliations != null && !affiliations.isEmpty()) {
         Map map = new HashMap();
         Iterator var3 = affiliations.iterator();

         while(var3.hasNext()) {
            DirectedAffiliation da = (DirectedAffiliation)var3.next();
            if (da != null) {
               Object pc = da.getPrincipal();
               if (pc != null) {
                  Set affiliates = da.getAffiliates();
                  if (affiliates != null && !affiliates.isEmpty()) {
                     map.put(pc, affiliates);
                  }
               }
            }
         }

         returnValue = new HashSet();
         Set keys = map.keySet();

         assert keys != null;

         assert !keys.isEmpty();

         Set seen = new HashSet();
         Queue queue = new LinkedList();
         Iterator var16 = keys.iterator();

         label85:
         while(var16.hasNext()) {
            Object key = var16.next();

            assert key != null;

            queue.clear();
            seen.clear();
            queue.add(key);
            seen.add(key);

            while(true) {
               Set affiliates;
               do {
                  if (queue.isEmpty()) {
                     seen.remove(key);
                     returnValue.add(new DirectedAffiliation(key, seen));
                     continue label85;
                  }

                  Object processingComponent = queue.remove();

                  assert processingComponent != null;

                  affiliates = (Set)map.get(processingComponent);
               } while(affiliates == null);

               Iterator var10 = affiliates.iterator();

               while(var10.hasNext()) {
                  Object affiliate = var10.next();

                  assert affiliate != null;

                  if (!seen.contains(affiliate)) {
                     seen.add(affiliate);
                     queue.add(affiliate);
                  }
               }
            }
         }
      }

      Set returnValue;
      if (returnValue == null) {
         returnValue = Collections.emptySet();
      } else {
         returnValue = Collections.unmodifiableSet(returnValue);
      }

      return returnValue;
   }
}
