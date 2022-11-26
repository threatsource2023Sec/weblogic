package org.apache.openjpa.lib.meta;

import java.security.AccessController;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import serp.util.Strings;

public class CFMetaDataParser extends XMLMetaDataParser {
   static final String[] PACKAGES = new String[]{"java.lang.", "java.util.", "java.math."};
   private static final Localizer _loc = Localizer.forPackage(CFMetaDataParser.class);
   private String _package = null;
   private String _class = null;

   public CFMetaDataParser() {
      this.setParseText(false);
   }

   protected boolean isPackageElementName(String name) {
      return "package".equals(name);
   }

   protected String getPackageAttributeName() {
      return "name";
   }

   protected int getPackageElementDepth() {
      return 1;
   }

   protected boolean isClassElementName(String name) {
      return "class".equals(name);
   }

   protected String getClassAttributeName() {
      return "name";
   }

   protected int getClassElementDepth() {
      return 2;
   }

   protected boolean startElement(String name, Attributes attrs) throws SAXException {
      int depth = this.currentDepth();
      if (depth == 0) {
         return true;
      } else {
         try {
            if (depth == this.getPackageElementDepth() && this.isPackageElementName(name)) {
               return this.startPackage(name, attrs);
            } else if (depth == this.getClassElementDepth() && this.isClassElementName(name)) {
               return this.startClass(name, attrs);
            } else if (depth > this.getClassElementDepth() && this._class != null && this.getClassAttributeName() != null) {
               return this.startClassElement(name, attrs);
            } else {
               return depth > this.getPackageElementDepth() && this._package != null && this.getPackageAttributeName() != null ? this.startPackageElement(name, attrs) : this.startSystemElement(name, attrs);
            }
         } catch (SAXException var5) {
            throw var5;
         } catch (NullPointerException var6) {
            throw this.getException(_loc.get("parse-error", (Object)name), var6);
         }
      }
   }

   protected void endElement(String name) throws SAXException {
      int depth = this.currentDepth();
      if (depth != 0) {
         try {
            if (depth == this.getPackageElementDepth() && this.isPackageElementName(name)) {
               this.endPackage(name);
            } else if (depth == this.getClassElementDepth() && this.isClassElementName(name)) {
               this.endClass(name);
            } else if (depth > this.getClassElementDepth() && this._class != null && this.getClassAttributeName() != null) {
               this.endClassElement(name);
            } else if (depth > this.getPackageElementDepth() && this._package != null && this.getPackageAttributeName() != null) {
               this.endPackageElement(name);
            } else {
               this.endSystemElement(name);
            }

         } catch (SAXException var4) {
            throw var4;
         } catch (NullPointerException var5) {
            throw this.getException(_loc.get("parse-error", (Object)name), var5);
         }
      }
   }

   protected boolean startPackage(String elem, Attributes attrs) throws SAXException {
      if (this.getPackageAttributeName() != null) {
         this._package = attrs.getValue(this.getPackageAttributeName());
         if (this._package == null) {
            this._package = "";
         }
      }

      return true;
   }

   protected void endPackage(String elem) {
      if (this.getPackageAttributeName() != null) {
         this._package = null;
      } else {
         this._package = this.currentText();
      }

   }

   protected boolean startClass(String elem, Attributes attrs) throws SAXException {
      if (this.getClassAttributeName() != null) {
         this._class = attrs.getValue(this.getClassAttributeName());
         if (!StringUtils.isEmpty(this._package) && this._class.indexOf(46) == -1) {
            this._class = this._package + "." + this._class;
         }
      }

      return true;
   }

   protected void endClass(String elem) throws SAXException {
      if (this.getClassAttributeName() != null) {
         this._class = null;
      } else {
         this._class = this.currentText();
         if (!StringUtils.isEmpty(this._package) && this._class.indexOf(46) == -1) {
            this._class = this._package + "." + this._class;
         }
      }

   }

   protected boolean startSystemElement(String name, Attributes attrs) throws SAXException {
      return false;
   }

   protected void endSystemElement(String name) throws SAXException {
   }

   protected boolean startPackageElement(String name, Attributes attrs) throws SAXException {
      return false;
   }

   protected void endPackageElement(String name) throws SAXException {
   }

   protected boolean startClassElement(String name, Attributes attrs) throws SAXException {
      return false;
   }

   protected void endClassElement(String name) throws SAXException {
   }

   protected void reset() {
      super.reset();
      this._package = null;
      this._class = null;
   }

   protected String currentClassName() {
      return this._class;
   }

   protected String currentPackage() {
      return this._package;
   }

   protected Class classForName(String name, boolean resolve) throws SAXException {
      if (name == null) {
         return null;
      } else {
         Class cls = classForName(name, this._package, resolve, this.currentClassLoader());
         if (cls == null) {
            throw this.getException(_loc.get("invalid-class", (Object)name).getMessage());
         } else {
            return cls;
         }
      }
   }

   public static Class classForName(String name, String pkg, boolean resolve, ClassLoader loader) {
      if (StringUtils.isEmpty(name)) {
         return null;
      } else {
         if (loader == null) {
            loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
         }

         boolean fullName = name.indexOf(46) != -1;
         boolean noPackage = StringUtils.isEmpty(pkg);

         try {
            return !fullName && !noPackage ? Strings.toClass(pkg + "." + name, resolve, loader) : Strings.toClass(name, resolve, loader);
         } catch (RuntimeException var10) {
            if (!fullName && !noPackage) {
               try {
                  return Strings.toClass(name, resolve, loader);
               } catch (RuntimeException var9) {
               }
            }

            if (!fullName) {
               int i = 0;

               while(i < PACKAGES.length) {
                  try {
                     return Strings.toClass(PACKAGES[i] + name, resolve, loader);
                  } catch (RuntimeException var8) {
                     ++i;
                  }
               }
            }

            return null;
         }
      }
   }
}
