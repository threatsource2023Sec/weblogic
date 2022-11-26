package org.apache.openjpa.meta;

import java.util.HashMap;
import org.apache.commons.lang.StringUtils;

public class XMLClassMetaData implements XMLMetaData {
   private Class _type;
   private int _code = 8;
   private int _xmltype = 0;
   private String _name = null;
   private String _xmlname = null;
   private String _xmlnamespace = null;
   private boolean _isXMLRootElement = false;
   private HashMap _fieldMap = new HashMap();

   public XMLClassMetaData(Class type, String name) {
      this._type = type;
      this._name = name;
   }

   public XMLClassMetaData(Class type) {
      this._type = type;
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
      return this._isXMLRootElement ? null : this._xmlname;
   }

   public void setXmlnamespace(String name) {
      if (!StringUtils.equals("##default", name)) {
         this._xmlnamespace = name;
      }

   }

   public String getXmlnamespace() {
      return this._xmlnamespace;
   }

   public void setXmlRootElement(boolean isXMLRootElement) {
      this._isXMLRootElement = isXMLRootElement;
   }

   public boolean isXmlRootElement() {
      return this._isXMLRootElement;
   }

   public boolean isXmlElement() {
      return false;
   }

   public boolean isXmlAttribute() {
      return false;
   }

   public XMLMetaData getFieldMapping(String name) {
      return (XMLMetaData)this._fieldMap.get(name);
   }

   public void setType(Class type) {
      this._type = type;
   }

   public Class getType() {
      return this._type;
   }

   public int getTypeCode() {
      return this._code;
   }

   public void setXmltype(int type) {
      this._xmltype = type;
   }

   public int getXmltype() {
      return this._xmltype;
   }

   public void addField(String name, XMLMetaData field) {
      this._fieldMap.put(name, field);
   }
}
