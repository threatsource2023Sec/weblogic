package weblogic.xml.xpath.stream.axes;

import java.util.List;
import weblogic.xml.xpath.stream.Axis;
import weblogic.xml.xpath.stream.StreamContext;

public final class DocumentRootAxis implements Axis {
   public static final Axis INSTANCE = new DocumentRootAxis();
   private static final boolean DEBUG = false;

   private DocumentRootAxis() {
   }

   public int match(StreamContext ctx) {
      return this.matchNew(ctx);
   }

   public int matchNew(StreamContext ctx) {
      return ctx.getNodeType() == 256 ? 201 : 202;
   }

   public List getNodeset(StreamContext ctx) {
      throw new IllegalStateException();
   }

   public boolean isAllowedInRoot() {
      return true;
   }

   public boolean isStringConvertible() {
      return false;
   }

   public boolean isAllowedInPredicate() {
      return false;
   }

   public String toString() {
      return "[document-root]";
   }
}
