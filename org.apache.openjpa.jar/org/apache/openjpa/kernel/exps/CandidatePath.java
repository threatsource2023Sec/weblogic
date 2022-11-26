package org.apache.openjpa.kernel.exps;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import org.apache.commons.lang.ObjectUtils;
import org.apache.openjpa.kernel.Broker;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.kernel.OpCallbacks;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.XMLMetaData;
import org.apache.openjpa.util.ImplHelper;

class CandidatePath extends Val implements Path {
   private LinkedList _actions = null;

   public void get(FieldMetaData field, boolean nullTraversal) {
      if (this._actions == null) {
         this._actions = new LinkedList();
      }

      this._actions.add(new Traversal(field, nullTraversal));
   }

   public Class getType() {
      if (this._actions == null) {
         return this.getCandidateType();
      } else {
         Object last = this._actions.getLast();
         if (last instanceof Class) {
            return (Class)last;
         } else {
            FieldMetaData fmd = ((Traversal)last).field;
            return fmd.getDeclaredType();
         }
      }
   }

   protected Class getCandidateType() {
      ClassMetaData meta = this.getMetaData();
      return meta == null ? Object.class : meta.getDescribedType();
   }

   public void setImplicitType(Class type) {
   }

   public FieldMetaData last() {
      if (this._actions == null) {
         return null;
      } else {
         ListIterator itr = this._actions.listIterator(this._actions.size());

         Object prev;
         do {
            if (!itr.hasPrevious()) {
               return null;
            }

            prev = itr.previous();
         } while(!(prev instanceof Traversal));

         return ((Traversal)prev).field;
      }
   }

   public void castTo(Class type) {
      if (this._actions == null) {
         this._actions = new LinkedList();
      }

      this._actions.add(type);
   }

   protected Object eval(Object candidate, Object orig, StoreContext ctx, Object[] params) {
      if (this._actions == null) {
         return candidate;
      } else {
         Broker tmpBroker = null;
         Iterator itr = this._actions.iterator();

         while(itr.hasNext()) {
            Object action = itr.next();
            if (candidate == null) {
               if (action instanceof Traversal && ((Traversal)action).nullTraversal) {
                  return null;
               }

               throw new NullPointerException();
            }

            if (action instanceof Class) {
               candidate = Filters.convert(candidate, (Class)action);
            } else {
               OpenJPAStateManager sm = null;
               tmpBroker = null;
               if (ImplHelper.isManageable(candidate)) {
                  sm = (OpenJPAStateManager)ImplHelper.toPersistenceCapable(candidate, ctx.getConfiguration()).pcGetStateManager();
               }

               if (sm == null) {
                  tmpBroker = ctx.getBroker();
                  tmpBroker.transactional(candidate, false, (OpCallbacks)null);
                  sm = tmpBroker.getStateManager(candidate);
               }

               try {
                  Traversal traversal = (Traversal)action;
                  candidate = sm.fetchField(traversal.field.getIndex(), true);
               } finally {
                  if (tmpBroker != null) {
                     tmpBroker.nontransactional(sm.getManagedInstance(), (OpCallbacks)null);
                  }

               }
            }
         }

         return candidate;
      }
   }

   public int hashCode() {
      return this._actions == null ? 0 : this._actions.hashCode();
   }

   public boolean equals(Object other) {
      if (other == this) {
         return true;
      } else {
         return !(other instanceof CandidatePath) ? false : ObjectUtils.equals(this._actions, ((CandidatePath)other)._actions);
      }
   }

   public void get(FieldMetaData fmd, XMLMetaData meta) {
   }

   public void get(XMLMetaData meta, String name) {
   }

   public XMLMetaData getXmlMapping() {
      return null;
   }

   private static class Traversal {
      public final FieldMetaData field;
      public final boolean nullTraversal;

      private Traversal(FieldMetaData field, boolean nullTraversal) {
         this.field = field;
         this.nullTraversal = nullTraversal;
      }

      public int hashCode() {
         return this.field.hashCode();
      }

      public boolean equals(Object other) {
         return other == this ? true : ((Traversal)other).field.equals(this.field);
      }

      // $FF: synthetic method
      Traversal(FieldMetaData x0, boolean x1, Object x2) {
         this(x0, x1);
      }
   }
}
