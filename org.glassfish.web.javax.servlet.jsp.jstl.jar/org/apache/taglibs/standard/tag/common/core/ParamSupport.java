package org.apache.taglibs.standard.tag.common.core;

import java.util.LinkedList;
import java.util.List;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;
import org.apache.taglibs.standard.resources.Resources;

public abstract class ParamSupport extends BodyTagSupport {
   protected String name;
   protected String value;
   protected boolean encode = true;

   public ParamSupport() {
      this.init();
   }

   private void init() {
      this.name = this.value = null;
   }

   public int doEndTag() throws JspException {
      Tag t = findAncestorWithClass(this, ParamParent.class);
      if (t == null) {
         throw new JspTagException(Resources.getMessage("PARAM_OUTSIDE_PARENT"));
      } else if (this.name != null && !this.name.equals("")) {
         ParamParent parent = (ParamParent)t;
         String value = this.value;
         if (value == null) {
            if (this.bodyContent != null && this.bodyContent.getString() != null) {
               value = this.bodyContent.getString().trim();
            } else {
               value = "";
            }
         }

         if (this.encode) {
            String enc = this.pageContext.getResponse().getCharacterEncoding();
            parent.addParameter(Util.URLEncode(this.name, enc), Util.URLEncode(value, enc));
         } else {
            parent.addParameter(this.name, value);
         }

         return 6;
      } else {
         return 6;
      }
   }

   public void release() {
      this.init();
   }

   public static class ParamManager {
      private List names = new LinkedList();
      private List values = new LinkedList();
      private boolean done = false;

      public void addParameter(String name, String value) {
         if (this.done) {
            throw new IllegalStateException();
         } else {
            if (name != null) {
               this.names.add(name);
               if (value != null) {
                  this.values.add(value);
               } else {
                  this.values.add("");
               }
            }

         }
      }

      public String aggregateParams(String url) {
         if (this.done) {
            throw new IllegalStateException();
         } else {
            this.done = true;
            StringBuffer newParams = new StringBuffer();

            int questionMark;
            for(questionMark = 0; questionMark < this.names.size(); ++questionMark) {
               newParams.append(this.names.get(questionMark) + "=" + this.values.get(questionMark));
               if (questionMark < this.names.size() - 1) {
                  newParams.append("&");
               }
            }

            if (newParams.length() > 0) {
               questionMark = url.indexOf(63);
               if (questionMark == -1) {
                  return url + "?" + newParams;
               } else {
                  StringBuffer workingUrl = new StringBuffer(url);
                  workingUrl.insert(questionMark + 1, newParams + "&");
                  return workingUrl.toString();
               }
            } else {
               return url;
            }
         }
      }
   }
}
