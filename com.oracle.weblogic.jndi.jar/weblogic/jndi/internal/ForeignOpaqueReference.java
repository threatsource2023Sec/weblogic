package weblogic.jndi.internal;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NamingException;
import weblogic.jndi.OpaqueReference;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;

public class ForeignOpaqueReference implements OpaqueReference, Serializable {
   static final long serialVersionUID = 4404892619941441265L;
   private Hashtable jndiEnvironment;
   private String remoteJNDIName;
   private static ClearOrEncryptedService ces = new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService());

   public ForeignOpaqueReference() {
   }

   public ForeignOpaqueReference(String remoteJNDIName, Hashtable env) {
      this.remoteJNDIName = remoteJNDIName;
      this.jndiEnvironment = env;
   }

   public Object getReferent(Name name, Context ctx) throws NamingException {
      InitialContext context;
      if (this.jndiEnvironment == null) {
         context = new InitialContext();
      } else {
         Hashtable properties = this.decrypt();
         context = new InitialContext(properties);
      }

      Object retVal;
      try {
         retVal = context.lookup(this.remoteJNDIName);
      } finally {
         context.close();
      }

      return retVal;
   }

   private Hashtable decrypt() {
      Hashtable h = (Hashtable)this.jndiEnvironment.clone();
      String username = (String)h.get("java.naming.security.principal");
      if (username != null && username.trim().length() != 0) {
         h.put("java.naming.security.principal", ces.decrypt(username));
      }

      String pass = (String)h.get("java.naming.security.credentials");
      if (pass != null && pass.trim().length() != 0) {
         h.put("java.naming.security.credentials", ces.decrypt(pass));
      }

      return h;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("ForeignOpaqueReference: target=\"");
      buf.append(this.remoteJNDIName);
      buf.append('"');
      if (this.jndiEnvironment != null) {
         Enumeration allProps = this.jndiEnvironment.keys();

         while(allProps.hasMoreElements()) {
            String key = (String)allProps.nextElement();
            String value = (String)this.jndiEnvironment.get(key);
            buf.append(' ');
            buf.append(key);
            buf.append('=');
            buf.append(value);
         }
      }

      return buf.toString();
   }
}
