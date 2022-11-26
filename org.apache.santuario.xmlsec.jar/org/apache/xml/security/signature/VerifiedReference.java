package org.apache.xml.security.signature;

import java.util.Collections;
import java.util.List;

public class VerifiedReference {
   private final boolean valid;
   private final String uri;
   private final List manifestReferences;

   public VerifiedReference(boolean valid, String uri, List manifestReferences) {
      this.valid = valid;
      this.uri = uri;
      if (manifestReferences != null) {
         this.manifestReferences = manifestReferences;
      } else {
         this.manifestReferences = Collections.emptyList();
      }

   }

   public boolean isValid() {
      return this.valid;
   }

   public String getUri() {
      return this.uri;
   }

   public List getManifestReferences() {
      return Collections.unmodifiableList(this.manifestReferences);
   }
}
