package org.apache.openjpa.meta;

import org.apache.commons.lang.StringUtils;

public class XMLFieldMetaData implements XMLMetaData {
   private String _name;
   private String _xmlname = null;
   private String _xmlnamespace = null;
   private Class _decType = Object.class;
   private int _decCode = 8;
   private Class _type = Object.class;
   private int _code = 8;
   private int _xmltype;

   public XMLFieldMetaData() {
   }

   public XMLFieldMetaData(Class type, String name) {
      this.setType(type);
      this._name = name;
   }

   public Class getType() {
      return this._type == null ? this._decType : this._type;
   }

   public void setType(Class type) {
      this._type = type;
      if (type != null) {
         this.setTypeCode(JavaTypes.getTypeCode(type));
      }

   }

   public int getTypeCode() {
      return this._type == null ? this._decCode : this._code;
   }

   public void setTypeCode(int code) {
      this._code = code;
   }

   public void setName(String name) {
      this._name = name;
   }

   public String getName() {
      return this._name;
   }

   public void setXmlname(String name) {
      this._xmlname = name;
   }

   public String getXmlname() {
      return this._xmlname;
   }

   public void setXmlnamespace(String name) {
      if (!StringUtils.equals("##default", name)) {
         this._xmlnamespace = name;
      }

   }

   public String getXmlnamespace() {
      return this._xmlnamespace;
   }

   public void setXmltype(int type) {
      this._xmltype = type;
   }

   public int getXmltype() {
      return this._xmltype;
   }

   public boolean isXmlRootElement() {
      return false;
   }

   public boolean isXmlElement() {
      return this._xmltype == 1;
   }

   public boolean isXmlAttribute() {
      return this._xmltype == 2;
   }

   public XMLMetaData getFieldMapping(String name) {
      return null;
   }

   public void setXmlRootElement(boolean isXmlRootElement) {
   }

   public void addField(String name, XMLMetaData field) {
   }
}
