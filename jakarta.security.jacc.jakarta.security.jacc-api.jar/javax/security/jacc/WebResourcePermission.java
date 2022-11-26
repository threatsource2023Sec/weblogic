package javax.security.jacc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.security.Permission;
import javax.servlet.http.HttpServletRequest;

public final class WebResourcePermission extends Permission {
   private static final long serialVersionUID = 1L;
   private transient HttpMethodSpec methodSpec;
   private transient URLPatternSpec urlPatternSpec;
   private transient int hashCodeValue;
   private static final transient String EMPTY_STRING = "";
   private static final transient String ESCAPED_COLON = "%3A";
   private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("actions", String.class)};

   public WebResourcePermission(String name, String actions) {
      super(name);
      this.urlPatternSpec = new URLPatternSpec(name);
      this.methodSpec = HttpMethodSpec.getSpec(actions);
   }

   public WebResourcePermission(String urlPatternSpec, String[] HTTPMethods) {
      super(urlPatternSpec);
      this.urlPatternSpec = new URLPatternSpec(urlPatternSpec);
      this.methodSpec = HttpMethodSpec.getSpec(HTTPMethods);
   }

   public WebResourcePermission(HttpServletRequest request) {
      super(getUriMinusContextPath(request));
      this.urlPatternSpec = new URLPatternSpec(super.getName());
      this.methodSpec = HttpMethodSpec.getSpec(request.getMethod());
   }

   public boolean equals(Object o) {
      if (o != null && o instanceof WebResourcePermission) {
         WebResourcePermission that = (WebResourcePermission)o;
         return !this.methodSpec.equals(that.methodSpec) ? false : this.urlPatternSpec.equals(that.urlPatternSpec);
      } else {
         return false;
      }
   }

   public String getActions() {
      return this.methodSpec.getActions();
   }

   public int hashCode() {
      if (this.hashCodeValue == 0) {
         String hashInput = this.urlPatternSpec.toString() + " " + this.methodSpec.hashCode();
         this.hashCodeValue = hashInput.hashCode();
      }

      return this.hashCodeValue;
   }

   public boolean implies(Permission permission) {
      if (!(permission instanceof WebResourcePermission)) {
         return false;
      } else {
         WebResourcePermission that = (WebResourcePermission)permission;
         return !this.methodSpec.implies(that.methodSpec) ? false : this.urlPatternSpec.implies(that.urlPatternSpec);
      }
   }

   private static String getUriMinusContextPath(HttpServletRequest request) {
      String uri = request.getRequestURI();
      if (uri == null) {
         return "";
      } else {
         String contextPath = request.getContextPath();
         int contextLength = contextPath == null ? 0 : contextPath.length();
         if (contextLength > 0) {
            uri = uri.substring(contextLength);
         }

         return uri.equals("/") ? "" : uri.replaceAll(":", "%3A");
      }
   }

   private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
      this.methodSpec = HttpMethodSpec.getSpec((String)inputStream.readFields().get("actions", (Object)null));
      this.urlPatternSpec = new URLPatternSpec(super.getName());
   }

   private synchronized void writeObject(ObjectOutputStream outputStream) throws IOException {
      outputStream.putFields().put("actions", this.getActions());
      outputStream.writeFields();
   }
}
