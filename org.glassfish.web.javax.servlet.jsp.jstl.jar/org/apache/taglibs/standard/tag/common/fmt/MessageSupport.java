package org.apache.taglibs.standard.tag.common.fmt;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;
import org.apache.taglibs.standard.tag.common.core.Util;

public abstract class MessageSupport extends BodyTagSupport {
   public static final String UNDEFINED_KEY = "???";
   protected String keyAttrValue;
   protected boolean keySpecified;
   protected LocalizationContext bundleAttrValue;
   protected boolean bundleSpecified;
   private String var;
   private int scope;
   private List params = new ArrayList();

   public MessageSupport() {
      this.init();
   }

   private void init() {
      this.var = null;
      this.scope = 1;
      this.keyAttrValue = null;
      this.keySpecified = false;
      this.bundleAttrValue = null;
      this.bundleSpecified = false;
   }

   public void setVar(String var) {
      this.var = var;
   }

   public void setScope(String scope) {
      this.scope = Util.getScope(scope);
   }

   public void addParam(Object arg) {
      this.params.add(arg);
   }

   public int doStartTag() throws JspException {
      this.params.clear();
      return 2;
   }

   public int doEndTag() throws JspException {
      String key = null;
      LocalizationContext locCtxt = null;
      if (this.keySpecified) {
         key = this.keyAttrValue;
      } else if (this.bodyContent != null && this.bodyContent.getString() != null) {
         key = this.bodyContent.getString().trim();
      }

      if (key != null && !key.equals("")) {
         String prefix = null;
         if (!this.bundleSpecified) {
            Tag t = findAncestorWithClass(this, BundleSupport.class);
            if (t != null) {
               BundleSupport parent = (BundleSupport)t;
               locCtxt = parent.getLocalizationContext();
               prefix = parent.getPrefix();
            } else {
               locCtxt = BundleSupport.getLocalizationContext(this.pageContext);
            }
         } else {
            locCtxt = this.bundleAttrValue;
            if (locCtxt.getLocale() != null) {
               SetLocaleSupport.setResponseLocale(this.pageContext, locCtxt.getLocale());
            }
         }

         String message = "???" + key + "???";
         if (locCtxt != null) {
            ResourceBundle bundle = locCtxt.getResourceBundle();
            if (bundle != null) {
               try {
                  if (prefix != null) {
                     key = prefix + key;
                  }

                  message = bundle.getString(key);
                  if (!this.params.isEmpty()) {
                     Object[] messageArgs = this.params.toArray();
                     MessageFormat formatter = new MessageFormat("");
                     if (locCtxt.getLocale() != null) {
                        formatter.setLocale(locCtxt.getLocale());
                     } else {
                        Locale locale = SetLocaleSupport.getFormattingLocale(this.pageContext);
                        if (locale != null) {
                           formatter.setLocale(locale);
                        }
                     }

                     formatter.applyPattern(message);
                     message = formatter.format(messageArgs);
                  }
               } catch (MissingResourceException var10) {
                  message = "???" + key + "???";
               }
            }
         }

         if (this.var != null) {
            this.pageContext.setAttribute(this.var, message, this.scope);
         } else {
            try {
               this.pageContext.getOut().print(message);
            } catch (IOException var9) {
               throw new JspTagException(var9.toString(), var9);
            }
         }

         return 6;
      } else {
         try {
            this.pageContext.getOut().print("??????");
            return 6;
         } catch (IOException var11) {
            throw new JspTagException(var11.toString(), var11);
         }
      }
   }

   public void release() {
      this.init();
   }
}
