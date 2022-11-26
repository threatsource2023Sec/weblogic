package weblogic.messaging.path;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface Key extends Comparable {
   String CLUSTER = "CLUSTER";
   byte IDX_WLS = 0;
   byte IDX_UOO = 1;
   byte IDX_SAF = 2;
   byte IDX_WLI = 3;
   byte IDX_WS = 4;
   byte IDX_UOW = 5;
   List RESERVED_SUBSYSTEMS = Collections.unmodifiableList(new ArrayList(6) {
      private static final long serialVersionUID = 1L;

      {
         this.add(".wls");
         this.add(".uoo");
         this.add(".saf");
         this.add(".wli");
         this.add(".ws");
         this.add(".uow");
      }
   });

   byte getSubsystem();

   String getAssemblyId();

   Serializable getKeyId();

   int hashCode();

   boolean equals(Object var1);

   int compareTo(Object var1);
}
