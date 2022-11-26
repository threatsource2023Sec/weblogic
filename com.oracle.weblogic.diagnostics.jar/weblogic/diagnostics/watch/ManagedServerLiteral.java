package weblogic.diagnostics.watch;

import com.oracle.weblogic.diagnostics.expressions.ManagedServer;
import org.glassfish.hk2.api.AnnotationLiteral;

public final class ManagedServerLiteral extends AnnotationLiteral implements ManagedServer {
   public String toString() {
      return "@ManagedServer";
   }
}
