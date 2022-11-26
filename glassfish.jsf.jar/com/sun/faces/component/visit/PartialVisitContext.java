package com.sun.faces.component.visit;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;

public class PartialVisitContext extends VisitContext {
   private Collection clientIds;
   private Collection ids;
   private Collection unvisitedClientIds;
   private Map subtreeClientIds;
   private FacesContext facesContext;
   private Set hints;

   public PartialVisitContext(FacesContext facesContext, Collection clientIds) {
      this(facesContext, clientIds, (Set)null);
   }

   public PartialVisitContext(FacesContext facesContext, Collection clientIds, Set hints) {
      if (facesContext == null) {
         throw new NullPointerException();
      } else {
         this.facesContext = facesContext;
         this.initializeCollections(clientIds);
         EnumSet hintsEnumSet = hints != null && !hints.isEmpty() ? EnumSet.copyOf(hints) : EnumSet.noneOf(VisitHint.class);
         this.hints = Collections.unmodifiableSet(hintsEnumSet);
      }
   }

   public FacesContext getFacesContext() {
      return this.facesContext;
   }

   public Set getHints() {
      return this.hints;
   }

   public Collection getIdsToVisit() {
      return this.clientIds;
   }

   public Collection getUnvisitedClientIds() {
      return this.unvisitedClientIds;
   }

   public Collection getSubtreeIdsToVisit(UIComponent component) {
      if (!(component instanceof NamingContainer)) {
         throw new IllegalArgumentException("Component is not a NamingContainer: " + component);
      } else {
         String clientId = component.getClientId();
         Collection ids = (Collection)this.subtreeClientIds.get(clientId);
         return (Collection)(ids == null ? Collections.emptyList() : Collections.unmodifiableCollection(ids));
      }
   }

   public VisitResult invokeVisitCallback(UIComponent component, VisitCallback callback) {
      String clientId = this.getVisitId(component);
      if (clientId == null) {
         return VisitResult.ACCEPT;
      } else {
         VisitResult result = callback.visit(this, component);
         this.unvisitedClientIds.remove(clientId);
         return this.unvisitedClientIds.isEmpty() ? VisitResult.COMPLETE : result;
      }
   }

   private void idAdded(String clientId) {
      this.ids.add(this.getIdFromClientId(clientId));
      this.unvisitedClientIds.add(clientId);
      this.addSubtreeClientId(clientId);
   }

   private void idRemoved(String clientId) {
      this.unvisitedClientIds.remove(clientId);
      this.removeSubtreeClientId(clientId);
   }

   private void initializeCollections(Collection clientIds) {
      this.unvisitedClientIds = new HashSet();
      this.ids = new HashSet();
      this.subtreeClientIds = new HashMap();
      this.clientIds = new CollectionProxy(new HashSet());
      this.clientIds.addAll(clientIds);
   }

   private String getVisitId(UIComponent component) {
      String id = component.getId();
      if (id != null && !this.ids.contains(id)) {
         return null;
      } else {
         String clientId = component.getClientId();

         assert clientId != null;

         return this.clientIds.contains(clientId) ? clientId : null;
      }
   }

   private String getIdFromClientId(String clientId) {
      FacesContext facesContext = this.getFacesContext();
      char separator = UINamingContainer.getSeparatorChar(facesContext);
      int lastIndex = clientId.lastIndexOf(separator);
      String id = null;
      if (lastIndex < 0) {
         id = clientId;
      } else if (lastIndex < clientId.length() - 1) {
         id = clientId.substring(lastIndex + 1);
      }

      return id;
   }

   private void addSubtreeClientId(String clientId) {
      FacesContext facesContext = this.getFacesContext();
      char separator = UINamingContainer.getSeparatorChar(facesContext);
      int length = clientId.length();

      for(int i = 0; i < length; ++i) {
         if (clientId.charAt(i) == separator) {
            String namingContainerClientId = clientId.substring(0, i);
            Collection c = (Collection)this.subtreeClientIds.get(namingContainerClientId);
            if (c == null) {
               c = new ArrayList();
               this.subtreeClientIds.put(namingContainerClientId, c);
            }

            ((Collection)c).add(clientId);
         }
      }

   }

   private void removeSubtreeClientId(String clientId) {
      Iterator var2 = this.subtreeClientIds.keySet().iterator();

      while(var2.hasNext()) {
         String key = (String)var2.next();
         if (clientId.startsWith(key)) {
            Collection ids = (Collection)this.subtreeClientIds.get(key);
            ids.remove(clientId);
         }
      }

   }

   private class IteratorProxy implements Iterator {
      private Iterator wrapped;
      private String current;

      private IteratorProxy(Iterator wrapped) {
         this.current = null;
         this.wrapped = wrapped;
      }

      public boolean hasNext() {
         return this.wrapped.hasNext();
      }

      public String next() {
         this.current = (String)this.wrapped.next();
         return this.current;
      }

      public void remove() {
         if (this.current != null) {
            PartialVisitContext.this.idRemoved(this.current);
         }

         this.wrapped.remove();
      }

      // $FF: synthetic method
      IteratorProxy(Iterator x1, Object x2) {
         this(x1);
      }
   }

   private class CollectionProxy extends AbstractCollection {
      private Collection wrapped;

      private CollectionProxy(Collection wrapped) {
         this.wrapped = wrapped;
      }

      public int size() {
         return this.wrapped.size();
      }

      public Iterator iterator() {
         return PartialVisitContext.this.new IteratorProxy(this.wrapped.iterator());
      }

      public boolean add(String o) {
         boolean added = this.wrapped.add(o);
         if (added) {
            PartialVisitContext.this.idAdded(o);
         }

         return added;
      }

      // $FF: synthetic method
      CollectionProxy(Collection x1, Object x2) {
         this(x1);
      }
   }
}
