package javax.security.jacc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.security.Permission;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public final class WebUserDataPermission extends Permission {
   private static final long serialVersionUID = -970193775626385011L;
   private static final transient String EMPTY_STRING = "";
   private static final transient String ESCAPED_COLON = "%3A";
   private static String[] transportKeys = new String[]{"NONE", "INTEGRAL", "CONFIDENTIAL"};
   private static Map transportHash = new HashMap();
   private static int TT_NONE;
   private static int TT_CONFIDENTIAL;
   private transient URLPatternSpec urlPatternSpec;
   private transient HttpMethodSpec methodSpec;
   private transient int transportType;
   private transient int hashCodeValue;
   private static final ObjectStreamField[] serialPersistentFields;

   public WebUserDataPermission(String name, String actions) {
      super(name);
      this.urlPatternSpec = new URLPatternSpec(name);
      this.parseActions(actions);
   }

   public WebUserDataPermission(String urlPatternSpec, String[] HTTPMethods, String transportType) {
      super(urlPatternSpec);
      this.urlPatternSpec = new URLPatternSpec(urlPatternSpec);
      this.transportType = TT_NONE;
      if (transportType != null) {
         Integer bit = (Integer)transportHash.get(transportType);
         if (bit == null) {
            throw new IllegalArgumentException("illegal transport value");
         }

         this.transportType = bit;
      }

      this.methodSpec = HttpMethodSpec.getSpec(HTTPMethods);
   }

   public WebUserDataPermission(HttpServletRequest request) {
      super(getUriMinusContextPath(request));
      this.urlPatternSpec = new URLPatternSpec(super.getName());
      this.transportType = request.isSecure() ? TT_CONFIDENTIAL : TT_NONE;
      this.methodSpec = HttpMethodSpec.getSpec(request.getMethod());
   }

   public boolean equals(Object o) {
      if (o != null && o instanceof WebUserDataPermission) {
         WebUserDataPermission that = (WebUserDataPermission)o;
         if (this.transportType != that.transportType) {
            return false;
         } else {
            return !this.methodSpec.equals(that.methodSpec) ? false : this.urlPatternSpec.equals(that.urlPatternSpec);
         }
      } else {
         return false;
      }
   }

   public String getActions() {
      String methodSpecActions = this.methodSpec.getActions();
      if (this.transportType == TT_NONE && methodSpecActions == null) {
         return null;
      } else if (this.transportType == TT_NONE) {
         return methodSpecActions;
      } else {
         return methodSpecActions == null ? ":" + transportKeys[this.transportType] : methodSpecActions + ":" + transportKeys[this.transportType];
      }
   }

   public int hashCode() {
      if (this.hashCodeValue == 0) {
         String hashInput = this.urlPatternSpec.toString() + " " + this.methodSpec.hashCode() + ":" + this.transportType;
         this.hashCodeValue = hashInput.hashCode();
      }

      return this.hashCodeValue;
   }

   public boolean implies(Permission permission) {
      if (!(permission instanceof WebUserDataPermission)) {
         return false;
      } else {
         WebUserDataPermission that = (WebUserDataPermission)permission;
         if (this.transportType != TT_NONE && this.transportType != that.transportType) {
            return false;
         } else {
            return !this.methodSpec.implies(that.methodSpec) ? false : this.urlPatternSpec.implies(that.urlPatternSpec);
         }
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

   private void parseActions(String actions) {
      this.transportType = TT_NONE;
      if (actions != null && !actions.equals("")) {
         int colon = actions.indexOf(58);
         if (colon < 0) {
            this.methodSpec = HttpMethodSpec.getSpec(actions);
         } else {
            if (colon == 0) {
               this.methodSpec = HttpMethodSpec.getSpec((String)null);
            } else {
               this.methodSpec = HttpMethodSpec.getSpec(actions.substring(0, colon));
            }

            Integer bit = (Integer)transportHash.get(actions.substring(colon + 1));
            if (bit == null) {
               throw new IllegalArgumentException("illegal transport value");
            }

            this.transportType = bit;
         }
      } else {
         this.methodSpec = HttpMethodSpec.getSpec((String)null);
      }

   }

   private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
      this.parseActions((String)inputStream.readFields().get("actions", (Object)null));
      this.urlPatternSpec = new URLPatternSpec(super.getName());
   }

   private synchronized void writeObject(ObjectOutputStream outputStream) throws IOException {
      outputStream.putFields().put("actions", this.getActions());
      outputStream.writeFields();
   }

   static {
      for(int i = 0; i < transportKeys.length; ++i) {
         transportHash.put(transportKeys[i], i);
      }

      TT_NONE = (Integer)transportHash.get("NONE");
      TT_CONFIDENTIAL = (Integer)transportHash.get("CONFIDENTIAL");
      serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("actions", String.class)};
   }
}
