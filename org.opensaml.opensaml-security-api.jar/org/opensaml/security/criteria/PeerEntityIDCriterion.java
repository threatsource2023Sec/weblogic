package org.opensaml.security.criteria;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public final class PeerEntityIDCriterion implements Criterion {
   private String peerID;

   public PeerEntityIDCriterion(@Nonnull String peer) {
      this.setPeerID(peer);
   }

   @Nonnull
   public String getPeerID() {
      return this.peerID;
   }

   public void setPeerID(@Nonnull String peer) {
      String trimmed = StringSupport.trimOrNull(peer);
      Constraint.isNotNull(trimmed, "Peer entity ID criteria cannot be null");
      this.peerID = trimmed;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("PeerEntityIDCriterion [peerID=");
      builder.append(this.peerID);
      builder.append("]");
      return builder.toString();
   }

   public int hashCode() {
      return this.peerID.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else {
         return obj instanceof PeerEntityIDCriterion ? this.peerID.equals(((PeerEntityIDCriterion)obj).peerID) : false;
      }
   }
}
