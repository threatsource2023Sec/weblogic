package weblogic.security.credential;

import java.util.Collections;
import java.util.List;

public final class IDCSAudience implements Audience {
   List audience;

   public IDCSAudience(List aud) {
      this.audience = Collections.unmodifiableList(aud);
   }

   public List getAudience() {
      return this.audience;
   }
}
