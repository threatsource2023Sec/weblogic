package weblogic.xml.xpath.stream.axes;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.xpath.stream.Axis;
import weblogic.xml.xpath.stream.StreamContext;

public final class AncestorAxis implements Axis {
   public static final Axis INSTANCE = new AncestorAxis();
   private static final boolean DEBUG = false;

   private AncestorAxis() {
   }

   public int match(StreamContext ctx) {
      return 203;
   }

   public int matchNew(StreamContext ctx) {
      throw new IllegalStateException();
   }

   public List getNodeset(StreamContext ctx) {
      List out = new ArrayList();
      Stack ancestors = ctx.getAncestorsAndSelf();

      for(int i = 0; i < ancestors.size() - 1; ++i) {
         out.add(new StreamContext((XMLEvent)ancestors.get(i)));
      }

      return out;
   }

   public boolean isAllowedInRoot() {
      return false;
   }

   public boolean isAllowedInPredicate() {
      return true;
   }

   public boolean isStringConvertible() {
      return false;
   }

   public String toString() {
      return "ancestor";
   }
}
