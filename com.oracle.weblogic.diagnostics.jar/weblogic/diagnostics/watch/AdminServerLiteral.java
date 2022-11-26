package weblogic.diagnostics.watch;

import com.oracle.weblogic.diagnostics.expressions.AdminServer;
import org.glassfish.hk2.api.AnnotationLiteral;

public final class AdminServerLiteral extends AnnotationLiteral implements AdminServer {
   public String toString() {
      return "@AdminServer";
   }
}
