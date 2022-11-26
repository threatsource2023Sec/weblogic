package weblogic.servlet.internal.dd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.WebElementMBean;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.utils.io.XMLWriter;

public abstract class BaseServletDescriptor extends XMLElementMBeanDelegate {
   private static final long serialVersionUID = -1306936740447325550L;
   private List errorList = null;

   public void addDescriptorError(String err) {
      this.addDescriptorError(new DescriptorError(err));
   }

   public void addDescriptorError(String err, String msg) {
      this.addDescriptorError(new DescriptorError(err, msg));
   }

   public void addDescriptorError(String err, String msg, String type) {
      this.addDescriptorError(new DescriptorError(err, msg, type));
   }

   public void addDescriptorError(DescriptorError err) {
      if (this.errorList == null) {
         this.errorList = new ArrayList();
      }

      if (err != null) {
         this.errorList.add(err);
      }

   }

   public void removeDescriptorError(String err) {
      if (err != null && this.errorList != null) {
         Iterator i = this.errorList.iterator();

         while(i.hasNext()) {
            DescriptorError de = (DescriptorError)i.next();
            if (err.equals(de.getError())) {
               this.errorList.remove(de);
               break;
            }
         }

      }
   }

   public void addDescriptorError(String[] err) {
      if (err != null) {
         for(int i = 0; i < err.length; ++i) {
            this.addDescriptorError(err[i]);
         }

      }
   }

   public void removeDescriptorErrors() {
      this.errorList = new ArrayList();
   }

   public void setDescriptorErrors(String[] errs) {
      this.removeDescriptorErrors();
      this.addDescriptorError(errs);
   }

   public String[] getDescriptorErrors() {
      if (this.errorList != null && this.errorList.size() != 0) {
         DescriptorError[] errs0 = (DescriptorError[])((DescriptorError[])this.errorList.toArray(new DescriptorError[this.errorList.size()]));
         String[] errs = new String[errs0.length];

         for(int i = 0; i < errs0.length; ++i) {
            errs[i] = errs0[i].toString();
         }

         return errs;
      } else {
         return null;
      }
   }

   protected String indentStr(int indent) {
      if (indent <= 0) {
         return "";
      } else {
         StringBuffer result = new StringBuffer(indent);

         for(int i = 0; i < indent; ++i) {
            result.append(" ");
         }

         return result.toString();
      }
   }

   public abstract void validate() throws DescriptorValidationException;

   public boolean isValid() {
      try {
         this.validate();
         return true;
      } catch (DescriptorValidationException var2) {
         return false;
      }
   }

   protected boolean check(WebElementMBean bsd) {
      if (bsd.isValid()) {
         return true;
      } else {
         this.addDescriptorError(bsd.getDescriptorErrors());
         return false;
      }
   }

   public void toXML(XMLWriter x) {
      x.println(this.toXML());
   }

   public String toXML() {
      return this.toXML(0);
   }

   public abstract String toXML(int var1);

   protected String arrayToString(String[] s) {
      if (s != null && s.length != 0) {
         String result = "{";

         for(int i = 0; i < s.length; ++i) {
            if (i > 0) {
               result = result + ",";
            }

            result = result + s[i];
         }

         result = result + "}";
         return result;
      } else {
         return "";
      }
   }
}
