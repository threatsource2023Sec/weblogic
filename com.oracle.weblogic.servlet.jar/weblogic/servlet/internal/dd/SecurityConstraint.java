package weblogic.servlet.internal.dd;

import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.AuthConstraintMBean;
import weblogic.management.descriptors.webapp.SecurityConstraintMBean;
import weblogic.management.descriptors.webapp.SecurityRoleMBean;
import weblogic.management.descriptors.webapp.UserDataConstraintMBean;
import weblogic.management.descriptors.webapp.WebResourceCollectionMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class SecurityConstraint extends BaseServletDescriptor implements ToXML, SecurityConstraintMBean {
   private static final long serialVersionUID = 2835803302540822938L;
   private static final String WEB_RESOURCE_COLLECTION = "web-resource-collection";
   private static final String AUTH_CONSTRAINT = "auth-constraint";
   private static final String USER_DATA_CONSTRAINT = "user-data-constraint";
   private static final String TRANSPORT_GUARANTEE = "transport-guarantee";
   private static final String DISPLAY_NAME = "display-name";
   private static final String DESCRIPTION = "description";
   public static final int TG_NONE = 0;
   public static final int TG_INTEGRAL = 1;
   public static final int TG_CONFIDENTIAL = 2;
   private String displayName;
   private WebResourceCollectionMBean[] webResources;
   private String[] methods = null;
   private AuthConstraintDescriptor authConstraint;
   private UserDataConstraintMBean userDataConstraint;

   public SecurityConstraint() {
   }

   public SecurityConstraint(WebAppDescriptor wad, Element parentElement) throws DOMProcessingException {
      this.displayName = DOMUtils.getOptionalValueByTagName(parentElement, "display-name");
      List elts = DOMUtils.getOptionalElementsByTagName(parentElement, "web-resource-collection");
      if (elts != null) {
         Iterator i = elts.iterator();
         this.webResources = new WebResourceDescriptor[elts.size()];

         Element e;
         for(int idx = 0; i.hasNext(); this.webResources[idx++] = new WebResourceDescriptor(e)) {
            e = (Element)i.next();
         }
      }

      Element elt = DOMUtils.getOptionalElementByTagName(parentElement, "user-data-constraint");
      if (elt != null) {
         String descr = DOMUtils.getOptionalValueByTagName(elt, "description");
         String transportStr = DOMUtils.getValueByTagName(elt, "transport-guarantee");
         if (transportStr == null) {
            throw new DOMProcessingException("You must specify transport-guarantee inside user-data-constraint element.");
         }

         if ("INTEGRAL".equalsIgnoreCase(transportStr)) {
            this.userDataConstraint = new UserDataConstraint();
            this.userDataConstraint.setDescription(descr);
            this.userDataConstraint.setTransportGuarantee("INTEGRAL");
         } else if ("CONFIDENTIAL".equalsIgnoreCase(transportStr)) {
            this.userDataConstraint = new UserDataConstraint();
            this.userDataConstraint.setDescription(descr);
            this.userDataConstraint.setTransportGuarantee("CONFIDENTIAL");
         } else {
            this.userDataConstraint = new UserDataConstraint();
            this.userDataConstraint.setDescription(descr);
            this.userDataConstraint.setTransportGuarantee("NONE");
         }
      }

      elt = DOMUtils.getOptionalElementByTagName(parentElement, "auth-constraint");
      if (elt != null) {
         this.authConstraint = new AuthConstraintDescriptor(wad, elt);
      }

   }

   public SecurityConstraint(SecurityConstraintMBean mbean) {
      this.setDisplayName(mbean.getDisplayName());
      this.setWebResourceCollection(mbean.getWebResourceCollection());
      this.setAuthConstraint(mbean.getAuthConstraint());
      this.setUserDataConstraint(new UserDataConstraint(mbean.getUserDataConstraint()));
   }

   public String[] getHttpMethods() {
      return this.methods;
   }

   public void setHttpMethods(String[] meth) {
      String[] old = this.methods;
      this.methods = meth;
      if (!comp(old, meth)) {
         this.firePropertyChange("httpMethods", old, meth);
      }

   }

   public boolean hasRoleConstraint() {
      if (this.authConstraint == null) {
         return false;
      } else {
         SecurityRoleMBean[] roles = this.authConstraint.getRoles();
         if (roles != null) {
            for(int i = 0; i < roles.length; ++i) {
               if (roles[i].getRoleName().equals("*")) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public int getTransportGuarantee() {
      if (this.userDataConstraint == null) {
         return 0;
      } else {
         String transportStr = this.userDataConstraint.getTransportGuarantee();
         if ("INTEGRAL".equalsIgnoreCase(transportStr)) {
            return 1;
         } else {
            return "CONFIDENTIAL".equalsIgnoreCase(transportStr) ? 2 : 0;
         }
      }
   }

   public void setTransportGuarantee(int tr) {
      int old = this.getTransportGuarantee();
      switch (tr) {
         case 0:
         default:
            this.userDataConstraint.setTransportGuarantee("NONE");
            break;
         case 1:
            this.userDataConstraint.setTransportGuarantee("INTEGRAL");
            break;
         case 2:
            this.userDataConstraint.setTransportGuarantee("CONFIDENTIAL");
      }

      if (old != this.getTransportGuarantee()) {
         this.firePropertyChange("transportGuarantee", new Integer(this.getTransportGuarantee()), new Integer(tr));
      }

   }

   public WebResourceCollectionMBean[] getWebResourceCollection() {
      return this.webResources;
   }

   public void setWebResourceCollection(WebResourceCollectionMBean[] coll) {
      WebResourceCollectionMBean[] old = this.webResources;
      if (coll != null && coll.length != 0) {
         WebResourceCollectionMBean[] result = new WebResourceCollectionMBean[coll.length];

         for(int i = 0; i < coll.length; ++i) {
            result[i] = coll[i];
         }

         this.webResources = result;
      } else {
         this.webResources = new WebResourceDescriptor[0];
      }

      if (!comp(old, coll)) {
         this.firePropertyChange("webResourceCollection", old, coll);
      }

   }

   public void addWebResourceCollection(WebResourceCollectionMBean x) {
      WebResourceCollectionMBean[] prev = this.getWebResourceCollection();
      if (prev == null) {
         prev = new WebResourceCollectionMBean[]{x};
         this.setWebResourceCollection(prev);
      } else {
         WebResourceCollectionMBean[] curr = new WebResourceCollectionMBean[prev.length + 1];
         System.arraycopy(prev, 0, curr, 0, prev.length);
         curr[prev.length] = x;
         this.setWebResourceCollection(curr);
      }
   }

   public void removeWebResourceCollection(WebResourceCollectionMBean x) {
      WebResourceCollectionMBean[] prev = this.getWebResourceCollection();
      if (prev != null) {
         int offset = -1;

         for(int i = 0; i < prev.length; ++i) {
            if (prev[i].equals(x)) {
               offset = i;
               break;
            }
         }

         if (offset >= 0) {
            WebResourceCollectionMBean[] curr = new WebResourceCollectionMBean[prev.length - 1];
            System.arraycopy(prev, 0, curr, 0, offset);
            System.arraycopy(prev, offset + 1, curr, offset, prev.length - (offset + 1));
            this.setWebResourceCollection(curr);
         }

      }
   }

   public AuthConstraintMBean getAuthConstraint() {
      return this.authConstraint;
   }

   public void setAuthConstraint(AuthConstraintMBean auth) {
      AuthConstraintMBean old = this.authConstraint;
      this.authConstraint = (AuthConstraintDescriptor)auth;
      if (!comp(old, auth)) {
         this.firePropertyChange("authConstraint", old, auth);
      }

   }

   public UserDataConstraintMBean getUserDataConstraint() {
      return this.userDataConstraint;
   }

   public void setUserDataConstraint(UserDataConstraintMBean cons) {
      UserDataConstraintMBean old = this.userDataConstraint;
      this.userDataConstraint = cons;
      if (!comp(old, cons)) {
         this.firePropertyChange("userDataConstraint", old, cons);
      }

   }

   public String getDisplayName() {
      return this.displayName;
   }

   public void setDisplayName(String name) {
      String old = this.displayName;
      this.displayName = name;
      if (!comp(old, name)) {
         this.firePropertyChange("displayName", old, name);
      }

   }

   public String getDisplayString() {
      String s = this.getDisplayName();
      if (s == null || (s = s.trim()).length() == 0) {
         WebResourceCollectionMBean[] webs = this.getWebResourceCollection();

         for(int i = 0; webs != null && i < webs.length; ++i) {
            s = webs[i].getResourceName();
            if (s != null && (s = s.trim()).length() > 0) {
               break;
            }
         }
      }

      return s;
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
      if (this.webResources == null || this.webResources.length == 0) {
         this.addDescriptorError("NO_WEB_RESOURCE");
         throw new DescriptorValidationException();
      }
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<security-constraint>\n";
      indent += 2;
      if (this.getDisplayName() != null) {
         result = result + this.indentStr(indent) + "<display-name>" + this.getDisplayName() + "</display-name>\n";
      }

      if (this.webResources != null) {
         for(int i = 0; i < this.webResources.length; ++i) {
            result = result + this.webResources[i].toXML(indent);
         }
      }

      if (this.authConstraint != null) {
         result = result + this.authConstraint.toXML(indent);
      }

      if (this.userDataConstraint != null) {
         result = result + this.userDataConstraint.toXML(indent);
      }

      indent -= 2;
      result = result + this.indentStr(indent) + "</security-constraint>\n";
      return result;
   }

   public String toString() {
      String result = "SecurityConstraint(";
      result = result + "display=" + this.displayName + ",";
      String[] methods = this.getHttpMethods();
      if (methods == null) {
         result = result + "methods=null,";
      } else {
         String m = "{";

         for(int i = 0; i < methods.length; ++i) {
            m = m + methods[i];
            if (i == methods.length - 1) {
               m = m + "}";
            } else {
               m = m + ",";
            }
         }

         result = result + "methods=" + m + ",";
      }

      result = result + "guarantee=" + this.getTransportGuarantee() + ",";
      result = result + "constrained=" + this.hasRoleConstraint() + ",";
      result = result + "auth=" + this.authConstraint + ",";
      WebResourceCollectionMBean[] wr = this.getWebResourceCollection();
      if (wr == null) {
         result = result + "wrc=null,";
      } else {
         String m = "{";

         for(int i = 0; i < wr.length; ++i) {
            m = m + wr[i];
            if (i == wr.length - 1) {
               m = m + "}";
            } else {
               m = m + ",";
            }
         }

         result = result + "wrc=" + m + ",";
      }

      result = result + "auth=" + this.getAuthConstraint() + ",";
      result = result + "udc=" + this.getUserDataConstraint();
      result = result + ")";
      return result;
   }
}
