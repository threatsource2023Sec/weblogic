package weblogic.diagnostics.watch;

import com.oracle.weblogic.diagnostics.expressions.Partition;
import org.glassfish.hk2.api.AnnotationLiteral;

public final class PartitionLiteral extends AnnotationLiteral implements Partition {
   public String toString() {
      return "@Partition";
   }
}
