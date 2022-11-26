package weblogic.xml.xpath.stream.axes;

import java.util.ArrayList;
import java.util.List;
import weblogic.xml.xpath.stream.Axis;
import weblogic.xml.xpath.stream.StreamContext;

public final class SelfAxis implements Axis {
   public static final Axis INSTANCE = new SelfAxis();

   private SelfAxis() {
   }

   public int matchNew(StreamContext ctx) {
      return 201;
   }

   public int match(StreamContext ctx) {
      return 202;
   }

   public List getNodeset(StreamContext ctx) {
      List out = new ArrayList();
      out.add(ctx);
      return out;
   }

   public boolean isAllowedInRoot() {
      return true;
   }

   public boolean isAllowedInPredicate() {
      return true;
   }

   public boolean isStringConvertible() {
      return false;
   }

   public String toString() {
      return "self";
   }
}
