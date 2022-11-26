package net.shibboleth.utilities.java.support.net;

import java.net.MalformedURLException;
import javax.annotation.Nullable;

public class BasicURLComparator implements URIComparator {
   private boolean caseInsensitive;

   public boolean isCaseInsensitive() {
      return this.caseInsensitive;
   }

   public void setCaseInsensitive(boolean flag) {
      this.caseInsensitive = flag;
   }

   public boolean compare(@Nullable String uri1, @Nullable String uri2) throws URIException {
      if (uri1 == null) {
         return uri2 == null;
      } else if (uri2 == null) {
         return uri1 == null;
      } else {
         String uri1Canon = null;

         try {
            uri1Canon = SimpleURLCanonicalizer.canonicalize(uri1);
         } catch (MalformedURLException var7) {
            throw new URIException("URI was invalid: " + uri1Canon);
         }

         String uri2Canon = null;

         try {
            uri2Canon = SimpleURLCanonicalizer.canonicalize(uri2);
         } catch (MalformedURLException var6) {
            throw new URIException("URI was invalid: " + uri2Canon);
         }

         return this.isCaseInsensitive() ? uri1Canon.equalsIgnoreCase(uri2Canon) : uri1Canon.equals(uri2Canon);
      }
   }
}
