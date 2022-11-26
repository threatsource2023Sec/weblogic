package com.bea.xbean.schema;

import com.bea.xml.QNameSet;
import com.bea.xml.SchemaProperty;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnySimpleType;
import java.math.BigInteger;
import java.util.Set;
import javax.xml.namespace.QName;

public class SchemaPropertyImpl implements SchemaProperty {
   private QName _name;
   private SchemaType.Ref _typeref;
   private boolean _isAttribute;
   private SchemaType.Ref _containerTypeRef;
   private String _javaPropertyName;
   private BigInteger _minOccurs;
   private BigInteger _maxOccurs;
   private int _hasNillable;
   private int _hasDefault;
   private int _hasFixed;
   private String _defaultText;
   private boolean _isImmutable;
   private SchemaType.Ref _javaBasedOnTypeRef;
   private boolean _extendsSingleton;
   private boolean _extendsArray;
   private boolean _extendsOption;
   private int _javaTypeCode;
   private QNameSet _javaSetterDelimiter;
   private XmlValueRef _defaultValue;
   private Set _acceptedNames;

   private void mutate() {
      if (this._isImmutable) {
         throw new IllegalStateException();
      }
   }

   public void setImmutable() {
      this.mutate();
      this._isImmutable = true;
   }

   public SchemaType getContainerType() {
      return this._containerTypeRef.get();
   }

   public void setContainerTypeRef(SchemaType.Ref typeref) {
      this.mutate();
      this._containerTypeRef = typeref;
   }

   public QName getName() {
      return this._name;
   }

   public void setName(QName name) {
      this.mutate();
      this._name = name;
   }

   public String getJavaPropertyName() {
      return this._javaPropertyName;
   }

   public void setJavaPropertyName(String name) {
      this.mutate();
      this._javaPropertyName = name;
   }

   public boolean isAttribute() {
      return this._isAttribute;
   }

   public void setAttribute(boolean isAttribute) {
      this.mutate();
      this._isAttribute = isAttribute;
   }

   public boolean isReadOnly() {
      return false;
   }

   public SchemaType getType() {
      return this._typeref.get();
   }

   public void setTypeRef(SchemaType.Ref typeref) {
      this.mutate();
      this._typeref = typeref;
   }

   public SchemaType javaBasedOnType() {
      return this._javaBasedOnTypeRef == null ? null : this._javaBasedOnTypeRef.get();
   }

   public boolean extendsJavaSingleton() {
      return this._extendsSingleton;
   }

   public boolean extendsJavaArray() {
      return this._extendsArray;
   }

   public boolean extendsJavaOption() {
      return this._extendsOption;
   }

   public void setExtendsJava(SchemaType.Ref javaBasedOnTypeRef, boolean singleton, boolean option, boolean array) {
      this.mutate();
      this._javaBasedOnTypeRef = javaBasedOnTypeRef;
      this._extendsSingleton = singleton;
      this._extendsOption = option;
      this._extendsArray = array;
   }

   public QNameSet getJavaSetterDelimiter() {
      if (this._isAttribute) {
         return QNameSet.EMPTY;
      } else {
         if (this._javaSetterDelimiter == null) {
            ((SchemaTypeImpl)this.getContainerType()).assignJavaElementSetterModel();
         }

         assert this._javaSetterDelimiter != null;

         return this._javaSetterDelimiter;
      }
   }

   void setJavaSetterDelimiter(QNameSet set) {
      this._javaSetterDelimiter = set;
   }

   public QName[] acceptedNames() {
      return this._acceptedNames == null ? new QName[]{this._name} : (QName[])((QName[])this._acceptedNames.toArray(new QName[this._acceptedNames.size()]));
   }

   public void setAcceptedNames(Set set) {
      this.mutate();
      this._acceptedNames = set;
   }

   public void setAcceptedNames(QNameSet set) {
      this.mutate();
      this._acceptedNames = set.includedQNamesInExcludedURIs();
   }

   public BigInteger getMinOccurs() {
      return this._minOccurs;
   }

   public void setMinOccurs(BigInteger min) {
      this.mutate();
      this._minOccurs = min;
   }

   public BigInteger getMaxOccurs() {
      return this._maxOccurs;
   }

   public void setMaxOccurs(BigInteger max) {
      this.mutate();
      this._maxOccurs = max;
   }

   public int hasNillable() {
      return this._hasNillable;
   }

   public void setNillable(int when) {
      this.mutate();
      this._hasNillable = when;
   }

   public int hasDefault() {
      return this._hasDefault;
   }

   public void setDefault(int when) {
      this.mutate();
      this._hasDefault = when;
   }

   public int hasFixed() {
      return this._hasFixed;
   }

   public void setFixed(int when) {
      this.mutate();
      this._hasFixed = when;
   }

   public String getDefaultText() {
      return this._defaultText;
   }

   public void setDefaultText(String val) {
      this.mutate();
      this._defaultText = val;
   }

   public XmlAnySimpleType getDefaultValue() {
      return this._defaultValue != null ? this._defaultValue.get() : null;
   }

   public void setDefaultValue(XmlValueRef defaultRef) {
      this.mutate();
      this._defaultValue = defaultRef;
   }

   public int getJavaTypeCode() {
      return this._javaTypeCode;
   }

   public void setJavaTypeCode(int code) {
      this.mutate();
      this._javaTypeCode = code;
   }
}
