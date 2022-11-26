package javax.faces.component.visit;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public abstract class VisitContext {
   public static final Collection ALL_IDS = new AbstractCollection() {
      public Iterator iterator() {
         throw new UnsupportedOperationException("VisitContext.ALL_IDS does not support this operation");
      }

      public int size() {
         throw new UnsupportedOperationException("VisitContext.ALL_IDS does not support this operation");
      }

      public boolean isEmpty() {
         return false;
      }
   };

   public abstract FacesContext getFacesContext();

   public abstract Collection getIdsToVisit();

   public abstract Collection getSubtreeIdsToVisit(UIComponent var1);

   public abstract VisitResult invokeVisitCallback(UIComponent var1, VisitCallback var2);

   public abstract Set getHints();

   public static VisitContext createVisitContext(FacesContext context, Collection ids, Set hints) {
      VisitContextFactory factory = (VisitContextFactory)FactoryFinder.getFactory("javax.faces.component.visit.VisitContextFactory");
      return factory.getVisitContext(context, ids, hints);
   }

   public static VisitContext createVisitContext(FacesContext context) {
      return createVisitContext(context, (Collection)null, (Set)null);
   }
}
