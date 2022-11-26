package weblogic.xml.xpath.stream.axes;

import java.util.LinkedList;
import java.util.List;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.xpath.stream.Axis;
import weblogic.xml.xpath.stream.Step;
import weblogic.xml.xpath.stream.StreamContext;

public final class AttributeAxis implements Axis {
   public static final Axis INSTANCE = new AttributeAxis();
   private static final boolean DEBUG = false;

   private AttributeAxis() {
   }

   public int match(StreamContext ctx) {
      return 203;
   }

   public int matchNew(StreamContext ctx) {
      if (ctx.getNodeType() == 2) {
         XMLEvent originalEvent = ctx.getEvent();
         AttributeIterator i = ((StartElement)originalEvent).getAttributes();

         while(i.hasNext()) {
            Attribute att = i.next();
            ctx.setAttribute(ctx.getEvent(), att);
            switch (ctx.mStep.matchNodeTestAndPredicates(ctx)) {
               case 100:
                  this.matchAttribute(ctx, ctx.mStep.copyNext(ctx));
                  break;
               case 101:
                  this.matchAttribute(ctx, ctx.mStep.copyNext(ctx));
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
      List out = null;
      if (ctx.getNodeType() == 2) {
         AttributeIterator i = ((StartElement)ctx.getEvent()).getAttributes();
         if (i.hasNext()) {
            out = new LinkedList();
         }

         while(i.hasNext()) {
            StreamContext fixme = new StreamContext();
            fixme.setAttribute(ctx.getEvent(), i.next());
            out.add(fixme);
         }
      }

      return out;
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
      return "attribute";
   }

   private void matchAttribute(StreamContext ctx, Step step) {
      if (step == null) {
         ctx.getAttributeObserver().observeAttribute((StartElement)ctx.getEvent(), ctx.getAttribute());
      } else {
         switch (step.matchNew(ctx)) {
            case 100:
            case 101:
            default:
               step = step.copyNext(ctx);
               this.matchAttribute(ctx, step);
               return;
            case 102:
            case 103:
         }
      }
   }
}
