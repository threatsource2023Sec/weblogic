package weblogic.common;

import java.security.cert.Certificate;
import java.util.Vector;
import weblogic.security.acl.DefaultUserInfoImpl;

/** @deprecated */
@Deprecated
public final class T3User extends DefaultUserInfoImpl {
   private static final long serialVersionUID = -4431340916243112533L;
   public static final T3User GUEST = new T3User("guest", "guest");

   public T3User() {
   }

   public T3User(String username, String password) {
      super(username, password, "weblogic");
   }

   public T3User(Certificate c) {
      super((String)null, c, "weblogic");
   }

   public T3User username(String val) {
      this.setName(val);
      return this;
   }

   public T3User password(String val) {
      this.setCredential(val);
      return this;
   }

   public String getUsername() {
      return this.getName();
   }

   public Vector getCertificates() {
      return this.hasCertificates() ? this.getCertificates() : null;
   }

   public boolean equals(Object that) {
      if (!(that instanceof T3User)) {
         return false;
      } else {
         T3User other = (T3User)that;
         return this.equals(other.getName(), other.getPassword(), other.getRealmName());
      }
   }

   public boolean equals(String otherId, String pw, String rName) {
      String passwd = this.getPassword();
      String realm = this.getRealmName();
      String id = this.getName();
      return id != null && id.length() > 0 && id.equals(otherId) && passwd != null && passwd.length() > 0 && passwd.equals(pw) && realm != null && realm.length() > 0 && realm.equals(rName);
   }

   public int hashCode() {
      return (this.getName() == null ? 0 : this.getName().hashCode()) | (this.getPassword() == null ? 0 : this.getPassword().hashCode()) | (this.getRealmName() == null ? 0 : this.getRealmName().hashCode());
   }
}
