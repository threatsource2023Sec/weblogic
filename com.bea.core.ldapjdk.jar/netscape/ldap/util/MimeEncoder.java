package netscape.ldap.util;

import java.io.Serializable;

public abstract class MimeEncoder implements Serializable {
   static final long serialVersionUID = 5179250095383961512L;

   public abstract void translate(ByteBuf var1, ByteBuf var2);

   public abstract void eof(ByteBuf var1);
}
