package com.bea.xbean.schema;

import com.bea.xbean.common.XPath;
import com.bea.xml.SchemaAnnotation;
import com.bea.xml.SchemaComponent;
import com.bea.xml.SchemaIdentityConstraint;
import com.bea.xml.SchemaTypeSystem;
import com.bea.xml.XmlObject;
import java.util.Collections;
import java.util.Map;
import javax.xml.namespace.QName;

public class SchemaIdentityConstraintImpl implements SchemaIdentityConstraint {
   private SchemaContainer _container;
   private String _selector;
   private String[] _fields;
   private SchemaIdentityConstraint.Ref _key;
   private QName _name;
   private int _type;
   private XmlObject _parse;
   private Object _userData;
   private SchemaAnnotation _annotation;
   private Map _nsMap;
   private String _parseTNS;
   private boolean _chameleon;
   private String _filename;
   private volatile XPath _selectorPath;
   private volatile XPath[] _fieldPaths;
   private SchemaIdentityConstraint.Ref _selfref;

   public SchemaIdentityConstraintImpl(SchemaContainer c) {
      this._nsMap = Collections.EMPTY_MAP;
      this._selfref = new SchemaIdentityConstraint.Ref(this);
      this._container = c;
   }

   public void setFilename(String filename) {
      this._filename = filename;
   }

   public String getSourceName() {
      return this._filename;
   }

   public String getSelector() {
      return this._selector;
   }

   public synchronized Object getSelectorPath() {
      XPath p = this._selectorPath;
      if (p == null) {
         try {
            this.buildPaths();
            p = this._selectorPath;
         } catch (XPath.XPathCompileException var3) {
            assert false : "Failed to compile xpath. Should be caught by compiler " + var3;

            return null;
         }
      }

      return p;
   }

   public void setAnnotation(SchemaAnnotation ann) {
      this._annotation = ann;
   }

   public SchemaAnnotation getAnnotation() {
      return this._annotation;
   }

   public void setNSMap(Map nsMap) {
      this._nsMap = nsMap;
   }

   public Map getNSMap() {
      return Collections.unmodifiableMap(this._nsMap);
   }

   public void setSelector(String selector) {
      assert selector != null;

      this._selector = selector;
   }

   public void setFields(String[] fields) {
      assert fields != null && fields.length > 0;

      this._fields = fields;
   }

   public String[] getFields() {
      String[] fields = new String[this._fields.length];
      System.arraycopy(this._fields, 0, fields, 0, fields.length);
      return fields;
   }

   public synchronized Object getFieldPath(int index) {
      XPath[] p = this._fieldPaths;
      if (p == null) {
         try {
            this.buildPaths();
            p = this._fieldPaths;
         } catch (XPath.XPathCompileException var4) {
            assert false : "Failed to compile xpath. Should be caught by compiler " + var4;

            return null;
         }
      }

      return p[index];
   }

   public synchronized void buildPaths() throws XPath.XPathCompileException {
      this._selectorPath = XPath.compileXPath(this._selector, this._nsMap);
      this._fieldPaths = new XPath[this._fields.length];

      for(int i = 0; i < this._fieldPaths.length; ++i) {
         this._fieldPaths[i] = XPath.compileXPath(this._fields[i], this._nsMap);
      }

   }

   public void setReferencedKey(SchemaIdentityConstraint.Ref key) {
      this._key = key;
   }

   public SchemaIdentityConstraint getReferencedKey() {
      return this._key.get();
   }

   public void setConstraintCategory(int type) {
      assert type >= 1 && type <= 3;

      this._type = type;
   }

   public int getConstraintCategory() {
      return this._type;
   }

   public void setName(QName name) {
      assert name != null;

      this._name = name;
   }

   public QName getName() {
      return this._name;
   }

   public int getComponentType() {
      return 5;
   }

   public SchemaTypeSystem getTypeSystem() {
      return this._container.getTypeSystem();
   }

   SchemaContainer getContainer() {
      return this._container;
   }

   public void setParseContext(XmlObject o, String targetNamespace, boolean chameleon) {
      this._parse = o;
      this._parseTNS = targetNamespace;
      this._chameleon = chameleon;
   }

   public XmlObject getParseObject() {
      return this._parse;
   }

   public String getTargetNamespace() {
      return this._parseTNS;
   }

   public String getChameleonNamespace() {
      return this._chameleon ? this._parseTNS : null;
   }

   public boolean isResolved() {
      return this.getConstraintCategory() != 2 || this._key != null;
   }

   public SchemaIdentityConstraint.Ref getRef() {
      return this._selfref;
   }

   public SchemaComponent.Ref getComponentRef() {
      return this.getRef();
   }

   public Object getUserData() {
      return this._userData;
   }

   public void setUserData(Object data) {
      this._userData = data;
   }
}
