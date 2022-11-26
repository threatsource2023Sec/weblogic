package weblogic.xml.xpath.stream.axes;

import java.util.Iterator;
import java.util.List;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.xpath.stream.Axis;
import weblogic.xml.xpath.stream.Step;
import weblogic.xml.xpath.stream.StreamContext;

public final class NamespaceAxis implements Axis {
   public static final Axis INSTANCE = new NamespaceAxis();
   private static final boolean DEBUG = false;

   private NamespaceAxis() {
   }

   public int match(StreamContext ctx) {
      return 203;
   }

   public int matchNew(StreamContext ctx) {
      if (ctx.getNodeType() == 2) {
         XMLEvent originalEvent = ctx.getEvent();
         Iterator i = ctx.getNamespaces().iterator();

         while(i.hasNext()) {
            Attribute ns = (Attribute)i.next();
            ctx.setNamespace(ctx.getEvent(), ns);
            switch (ctx.mStep.matchNodeTestAndPredicates(ctx)) {
               case 100:
                  this.matchNamespace(ctx, ctx.mStep.copyNext(ctx));
                  break;
               case 101:
                  this.matchNamespace(ctx, ctx.mStep.copyNext(ctx));
               case 102:
               case 103:
                  break;
               default:
                  throw new IllegalStateException();
            }
         }

         ctx.setEvent(originalEvent);
      }

      return 203;
   }

   public List getNodeset(StreamContext ctx) {
      return null;
   }

   public boolean isAllowedInRoot() {
      return true;
   }

   public boolean isAllowedInPredicate() {
      return true;
   }

   public boolean isStringConvertible() {
      return true;
   }

   public String toString() {
      return "namespace";
   }

   private void matchNamespace(StreamContext ctx, Step step) {
      if (step == null) {
         ctx.getAttributeObserver().observeNamespace((StartElement)ctx.getEvent(), ctx.getAttribute());
      } else {
         switch (step.matchNew(ctx)) {
            case 100:
            case 101:
            default:
               step = step.copyNext(ctx);
               this.matchNamespace(ctx, step);
               return;
            case 102:
            case 103:
         }
      }
   }
}
