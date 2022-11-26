package org.jboss.weld.annotated.slim.unbacked;

import java.io.ObjectStreamException;
import java.io.Serializable;
import org.jboss.weld.Container;
import org.jboss.weld.resources.MemberTransformer;

public class UnbackedMemberIdentifier implements Serializable {
   private static final long serialVersionUID = -8031539817026460998L;
   private final UnbackedAnnotatedType type;
   private final String memberId;

   public UnbackedMemberIdentifier(UnbackedAnnotatedType type, String memberId) {
      this.type = type;
      this.memberId = memberId;
   }

   public UnbackedAnnotatedType getType() {
      return this.type;
   }

   public String getMemberId() {
      return this.memberId;
   }

   private Object readResolve() throws ObjectStreamException {
      return ((MemberTransformer)Container.instance(this.type.getIdentifier()).services().get(MemberTransformer.class)).getUnbackedMember(this);
   }
}
