package weblogicx.jsp.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ProcessTag extends TagSupport {
   private String name;
   private String notName;
   private String value;
   private String notValue;

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public void setNotName(String notName) {
      this.notName = notName;
   }

   public String getNotName() {
      return this.notName;
   }

   public void setValue(String value) {
      this.value = value;
   }

   public String getValue() {
      return this.value;
   }

   public void setNotValue(String notValue) {
      this.notValue = notValue;
   }

   public String getNotValue() {
      return this.notValue;
   }

   public int doStartTag() throws JspException {
      HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
      if (this.name != null && this.notName != null) {
         throw new JspException("Cannot specify name and notName");
      } else {
         if (this.name == null && this.notName == null) {
            this.name = "submit";
         }

         if (this.name != null) {
            if (this.value == null && this.notValue == null) {
               return request.getParameter(this.name) != null && !request.getParameter(this.name).equals("") ? 1 : 0;
            }

            if (this.value != null) {
               return this.value.equals(request.getParameter(this.name)) ? 1 : 0;
            }

            if (this.notValue != null) {
               return this.notValue.equals(request.getParameter(this.name)) ? 0 : 1;
            }
         }

         if (this.notName != null && this.value == null && this.notValue == null) {
            return request.getParameter(this.notName) != null && !request.getParameter(this.notName).equals("") ? 0 : 1;
         } else {
            throw new JspException("Invalid combination of name/notname/value/notvalue attributes, see tag descriptor info");
         }
      }
   }

   public void release() {
      this.name = null;
      this.notName = null;
      this.value = null;
      this.notValue = null;
   }
}
