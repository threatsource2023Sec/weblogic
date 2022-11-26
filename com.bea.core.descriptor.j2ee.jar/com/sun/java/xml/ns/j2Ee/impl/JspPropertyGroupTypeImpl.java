package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.DescriptionType;
import com.sun.java.xml.ns.j2Ee.DisplayNameType;
import com.sun.java.xml.ns.j2Ee.IconType;
import com.sun.java.xml.ns.j2Ee.JspPropertyGroupType;
import com.sun.java.xml.ns.j2Ee.PathType;
import com.sun.java.xml.ns.j2Ee.String;
import com.sun.java.xml.ns.j2Ee.TrueFalseType;
import com.sun.java.xml.ns.j2Ee.UrlPatternType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class JspPropertyGroupTypeImpl extends XmlComplexContentImpl implements JspPropertyGroupType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://java.sun.com/xml/ns/j2ee", "description");
   private static final QName DISPLAYNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "display-name");
   private static final QName ICON$4 = new QName("http://java.sun.com/xml/ns/j2ee", "icon");
   private static final QName URLPATTERN$6 = new QName("http://java.sun.com/xml/ns/j2ee", "url-pattern");
   private static final QName ELIGNORED$8 = new QName("http://java.sun.com/xml/ns/j2ee", "el-ignored");
   private static final QName PAGEENCODING$10 = new QName("http://java.sun.com/xml/ns/j2ee", "page-encoding");
   private static final QName SCRIPTINGINVALID$12 = new QName("http://java.sun.com/xml/ns/j2ee", "scripting-invalid");
   private static final QName ISXML$14 = new QName("http://java.sun.com/xml/ns/j2ee", "is-xml");
   private static final QName INCLUDEPRELUDE$16 = new QName("http://java.sun.com/xml/ns/j2ee", "include-prelude");
   private static final QName INCLUDECODA$18 = new QName("http://java.sun.com/xml/ns/j2ee", "include-coda");
   private static final QName ID$20 = new QName("", "id");

   public JspPropertyGroupTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType[] getDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESCRIPTION$0, targetList);
         DescriptionType[] result = new DescriptionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DescriptionType getDescriptionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDescriptionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0);
      }
   }

   public void setDescriptionArray(DescriptionType[] descriptionArray) {
      this.check_orphaned();
      this.arraySetterHelper(descriptionArray, DESCRIPTION$0);
   }

   public void setDescriptionArray(int i, DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, i, (short)2);
   }

   public DescriptionType insertNewDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().insert_element_user(DESCRIPTION$0, i);
         return target;
      }
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void removeDescription(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, i);
      }
   }

   public DisplayNameType[] getDisplayNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DISPLAYNAME$2, targetList);
         DisplayNameType[] result = new DisplayNameType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public DisplayNameType getDisplayNameArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().find_element_user(DISPLAYNAME$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDisplayNameArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISPLAYNAME$2);
      }
   }

   public void setDisplayNameArray(DisplayNameType[] displayNameArray) {
      this.check_orphaned();
      this.arraySetterHelper(displayNameArray, DISPLAYNAME$2);
   }

   public void setDisplayNameArray(int i, DisplayNameType displayName) {
      this.generatedSetterHelperImpl(displayName, DISPLAYNAME$2, i, (short)2);
   }

   public DisplayNameType insertNewDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().insert_element_user(DISPLAYNAME$2, i);
         return target;
      }
   }

   public DisplayNameType addNewDisplayName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DisplayNameType target = null;
         target = (DisplayNameType)this.get_store().add_element_user(DISPLAYNAME$2);
         return target;
      }
   }

   public void removeDisplayName(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISPLAYNAME$2, i);
      }
   }

   public IconType[] getIconArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ICON$4, targetList);
         IconType[] result = new IconType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public IconType getIconArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().find_element_user(ICON$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfIconArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ICON$4);
      }
   }

   public void setIconArray(IconType[] iconArray) {
      this.check_orphaned();
      this.arraySetterHelper(iconArray, ICON$4);
   }

   public void setIconArray(int i, IconType icon) {
      this.generatedSetterHelperImpl(icon, ICON$4, i, (short)2);
   }

   public IconType insertNewIcon(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().insert_element_user(ICON$4, i);
         return target;
      }
   }

   public IconType addNewIcon() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IconType target = null;
         target = (IconType)this.get_store().add_element_user(ICON$4);
         return target;
      }
   }

   public void removeIcon(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ICON$4, i);
      }
   }

   public UrlPatternType[] getUrlPatternArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(URLPATTERN$6, targetList);
         UrlPatternType[] result = new UrlPatternType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public UrlPatternType getUrlPatternArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UrlPatternType target = null;
         target = (UrlPatternType)this.get_store().find_element_user(URLPATTERN$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfUrlPatternArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(URLPATTERN$6);
      }
   }

   public void setUrlPatternArray(UrlPatternType[] urlPatternArray) {
      this.check_orphaned();
      this.arraySetterHelper(urlPatternArray, URLPATTERN$6);
   }

   public void setUrlPatternArray(int i, UrlPatternType urlPattern) {
      this.generatedSetterHelperImpl(urlPattern, URLPATTERN$6, i, (short)2);
   }

   public UrlPatternType insertNewUrlPattern(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UrlPatternType target = null;
         target = (UrlPatternType)this.get_store().insert_element_user(URLPATTERN$6, i);
         return target;
      }
   }

   public UrlPatternType addNewUrlPattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UrlPatternType target = null;
         target = (UrlPatternType)this.get_store().add_element_user(URLPATTERN$6);
         return target;
      }
   }

   public void removeUrlPattern(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(URLPATTERN$6, i);
      }
   }

   public TrueFalseType getElIgnored() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ELIGNORED$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetElIgnored() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ELIGNORED$8) != 0;
      }
   }

   public void setElIgnored(TrueFalseType elIgnored) {
      this.generatedSetterHelperImpl(elIgnored, ELIGNORED$8, 0, (short)1);
   }

   public TrueFalseType addNewElIgnored() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ELIGNORED$8);
         return target;
      }
   }

   public void unsetElIgnored() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ELIGNORED$8, 0);
      }
   }

   public String getPageEncoding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(PAGEENCODING$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPageEncoding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PAGEENCODING$10) != 0;
      }
   }

   public void setPageEncoding(String pageEncoding) {
      this.generatedSetterHelperImpl(pageEncoding, PAGEENCODING$10, 0, (short)1);
   }

   public String addNewPageEncoding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PAGEENCODING$10);
         return target;
      }
   }

   public void unsetPageEncoding() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PAGEENCODING$10, 0);
      }
   }

   public TrueFalseType getScriptingInvalid() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(SCRIPTINGINVALID$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetScriptingInvalid() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SCRIPTINGINVALID$12) != 0;
      }
   }

   public void setScriptingInvalid(TrueFalseType scriptingInvalid) {
      this.generatedSetterHelperImpl(scriptingInvalid, SCRIPTINGINVALID$12, 0, (short)1);
   }

   public TrueFalseType addNewScriptingInvalid() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(SCRIPTINGINVALID$12);
         return target;
      }
   }

   public void unsetScriptingInvalid() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SCRIPTINGINVALID$12, 0);
      }
   }

   public TrueFalseType getIsXml() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ISXML$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetIsXml() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ISXML$14) != 0;
      }
   }

   public void setIsXml(TrueFalseType isXml) {
      this.generatedSetterHelperImpl(isXml, ISXML$14, 0, (short)1);
   }

   public TrueFalseType addNewIsXml() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ISXML$14);
         return target;
      }
   }

   public void unsetIsXml() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ISXML$14, 0);
      }
   }

   public PathType[] getIncludePreludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INCLUDEPRELUDE$16, targetList);
         PathType[] result = new PathType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PathType getIncludePreludeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(INCLUDEPRELUDE$16, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfIncludePreludeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INCLUDEPRELUDE$16);
      }
   }

   public void setIncludePreludeArray(PathType[] includePreludeArray) {
      this.check_orphaned();
      this.arraySetterHelper(includePreludeArray, INCLUDEPRELUDE$16);
   }

   public void setIncludePreludeArray(int i, PathType includePrelude) {
      this.generatedSetterHelperImpl(includePrelude, INCLUDEPRELUDE$16, i, (short)2);
   }

   public PathType insertNewIncludePrelude(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().insert_element_user(INCLUDEPRELUDE$16, i);
         return target;
      }
   }

   public PathType addNewIncludePrelude() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(INCLUDEPRELUDE$16);
         return target;
      }
   }

   public void removeIncludePrelude(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INCLUDEPRELUDE$16, i);
      }
   }

   public PathType[] getIncludeCodaArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INCLUDECODA$18, targetList);
         PathType[] result = new PathType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public PathType getIncludeCodaArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(INCLUDECODA$18, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfIncludeCodaArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INCLUDECODA$18);
      }
   }

   public void setIncludeCodaArray(PathType[] includeCodaArray) {
      this.check_orphaned();
      this.arraySetterHelper(includeCodaArray, INCLUDECODA$18);
   }

   public void setIncludeCodaArray(int i, PathType includeCoda) {
      this.generatedSetterHelperImpl(includeCoda, INCLUDECODA$18, i, (short)2);
   }

   public PathType insertNewIncludeCoda(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().insert_element_user(INCLUDECODA$18, i);
         return target;
      }
   }

   public PathType addNewIncludeCoda() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(INCLUDECODA$18);
         return target;
      }
   }

   public void removeIncludeCoda(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INCLUDECODA$18, i);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$20);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$20);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$20) != null;
      }
   }

   public void setId(java.lang.String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$20);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$20);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$20);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$20);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$20);
      }
   }
}
