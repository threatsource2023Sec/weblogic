package com.bea.security.utils.negotiate;

import java.io.Serializable;
import javax.security.auth.Subject;
import org.ietf.jgss.GSSCredential;

public class CredentialObject implements Serializable {
   private static final long serialVersionUID = -1523483421391735531L;
   private Subject delegatedSub;
   private transient GSSCredential credential;

   public CredentialObject(Subject sub) {
      this.delegatedSub = sub;
   }

   public CredentialObject(GSSCredential cred) {
      this.credential = cred;
   }

   public Subject getDelegatedSub() {
      return this.delegatedSub;
   }

   public GSSCredential getCredential() {
      return this.credential;
   }
}
