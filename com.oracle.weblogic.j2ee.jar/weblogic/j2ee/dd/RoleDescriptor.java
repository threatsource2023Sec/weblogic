package weblogic.j2ee.dd;

import java.io.StringWriter;
import java.util.Collections;
import java.util.Iterator;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.descriptors.application.SecurityRoleMBean;
import weblogic.utils.io.XMLWriter;

public final class RoleDescriptor extends XMLElementMBeanDelegate implements SecurityRoleMBean {
   private static final long serialVersionUID = 5643331597356419085L;
   private static boolean debug = false;
   private String description;
   private String roleName;

   public RoleDescriptor() {
   }

   public RoleDescriptor(String description, String roleName) {
      this.description = description;
      this.roleName = roleName;
   }

   public RoleDescriptor(String roleName) {
      this.roleName = roleName;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String d) {
      String old = this.description;
      this.description = d;
      this.checkChange("description", old, d);
   }

   public String getRoleName() {
      return this.roleName;
   }

   public void setRoleName(String r) {
      String old = this.roleName;
      this.roleName = r;
      this.checkChange("roleName", old, r);
   }

   public String getName() {
      return this.roleName;
   }

   public void setName(String rn) {
      this.setRoleName(rn);
   }

   public void validateSelf() {
   }

   public Iterator getSubObjectsIterator() {
      return Collections.EMPTY_SET.iterator();
   }

   public void register() throws ManagementException {
      super.register();
   }

   public void unregister() throws ManagementException {
      super.unregister();
   }

   private String indentStr(int x) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < x; ++i) {
         sb.append(' ');
      }

      return sb.toString();
   }

   public String toXML(int indent) {
      String ret = "";
      ret = ret + this.indentStr(indent) + "<security-role>\n";
      indent += 2;
      String d = this.getDescription();
      if (d != null) {
         ret = ret + this.indentStr(indent) + "<description>" + d + "</description>\n";
      }

      ret = ret + this.indentStr(indent) + "<role-name>" + this.getName() + "</role-name>\n";
      indent -= 2;
      ret = ret + this.indentStr(indent) + "</security-role>\n";
      return ret;
   }

   public void toXML(XMLWriter x) {
      x.println("<security-role>");
      x.incrIndent();
      String d = this.getDescription();
      if (d != null) {
         x.println("<description>" + d + "</description>");
      }

      x.println("<role-name>" + this.getName() + "</role-name>");
      x.decrIndent();
      x.println("</security-role>");
   }

   public String toXML() {
      StringWriter w = new StringWriter();
      this.toXML(new XMLWriter(w));
      return w.toString();
   }
}
