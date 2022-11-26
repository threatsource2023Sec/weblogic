package com.bea.xbean.schema;

import com.bea.xbean.values.NamespaceContext;
import com.bea.xml.QNameSet;
import com.bea.xml.SchemaParticle;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlObject;
import com.bea.xml.XmlQName;
import java.math.BigInteger;
import javax.xml.namespace.QName;

public class SchemaParticleImpl implements SchemaParticle {
   private int _particleType;
   private BigInteger _minOccurs;
   private BigInteger _maxOccurs;
   private SchemaParticle[] _particleChildren;
   private boolean _isImmutable;
   private QNameSet _startSet;
   private QNameSet _excludeNextSet;
   private boolean _isSkippable;
   private boolean _isDeterministic;
   private int _intMinOccurs;
   private int _intMaxOccurs;
   private QNameSet _wildcardSet;
   private int _wildcardProcess;
   private String _defaultText;
   private boolean _isDefault;
   private boolean _isFixed;
   private QName _qName;
   private boolean _isNillable;
   private SchemaType.Ref _typeref;
   protected XmlObject _parseObject;
   private Object _userData;
   private XmlValueRef _defaultValue;
   private static final BigInteger _maxint = BigInteger.valueOf(2147483647L);

   protected void mutate() {
      if (this._isImmutable) {
         throw new IllegalStateException();
      }
   }

   public void setImmutable() {
      this.mutate();
      this._isImmutable = true;
   }

   public boolean hasTransitionRules() {
      return this._startSet != null;
   }

   public boolean hasTransitionNotes() {
      return this._excludeNextSet != null;
   }

   public void setTransitionRules(QNameSet start, boolean isSkippable) {
      this._startSet = start;
      this._isSkippable = isSkippable;
   }

   public void setTransitionNotes(QNameSet excludeNext, boolean isDeterministic) {
      this._excludeNextSet = excludeNext;
      this._isDeterministic = isDeterministic;
   }

   public boolean canStartWithElement(QName name) {
      return name != null && this._startSet.contains(name);
   }

   public QNameSet acceptedStartNames() {
      return this._startSet;
   }

   public QNameSet getExcludeNextSet() {
      return this._excludeNextSet;
   }

   public boolean isSkippable() {
      return this._isSkippable;
   }

   public boolean isDeterministic() {
      return this._isDeterministic;
   }

   public int getParticleType() {
      return this._particleType;
   }

   public void setParticleType(int pType) {
      this.mutate();
      this._particleType = pType;
   }

   public boolean isSingleton() {
      return this._maxOccurs != null && this._maxOccurs.compareTo(BigInteger.ONE) == 0 && this._minOccurs.compareTo(BigInteger.ONE) == 0;
   }

   public BigInteger getMinOccurs() {
      return this._minOccurs;
   }

   public void setMinOccurs(BigInteger min) {
      this.mutate();
      this._minOccurs = min;
      this._intMinOccurs = pegBigInteger(min);
   }

   public int getIntMinOccurs() {
      return this._intMinOccurs;
   }

   public BigInteger getMaxOccurs() {
      return this._maxOccurs;
   }

   public int getIntMaxOccurs() {
      return this._intMaxOccurs;
   }

   public void setMaxOccurs(BigInteger max) {
      this.mutate();
      this._maxOccurs = max;
      this._intMaxOccurs = pegBigInteger(max);
   }

   public SchemaParticle[] getParticleChildren() {
      if (this._particleChildren != null) {
         SchemaParticle[] result = new SchemaParticle[this._particleChildren.length];
         System.arraycopy(this._particleChildren, 0, result, 0, this._particleChildren.length);
         return result;
      } else {
         assert this._particleType != 1 && this._particleType != 3 && this._particleType != 2;

         return null;
      }
   }

   public void setParticleChildren(SchemaParticle[] children) {
      this.mutate();
      this._particleChildren = children;
   }

   public SchemaParticle getParticleChild(int i) {
      return this._particleChildren[i];
   }

   public int countOfParticleChild() {
      return this._particleChildren == null ? 0 : this._particleChildren.length;
   }

   public void setWildcardSet(QNameSet set) {
      this.mutate();
      this._wildcardSet = set;
   }

   public QNameSet getWildcardSet() {
      return this._wildcardSet;
   }

   public void setWildcardProcess(int process) {
      this.mutate();
      this._wildcardProcess = process;
   }

   public int getWildcardProcess() {
      return this._wildcardProcess;
   }

   private static final int pegBigInteger(BigInteger bi) {
      if (bi == null) {
         return Integer.MAX_VALUE;
      } else if (bi.signum() <= 0) {
         return 0;
      } else {
         return bi.compareTo(_maxint) >= 0 ? Integer.MAX_VALUE : bi.intValue();
      }
   }

   public QName getName() {
      return this._qName;
   }

   public void setNameAndTypeRef(QName formname, SchemaType.Ref typeref) {
      this.mutate();
      this._qName = formname;
      this._typeref = typeref;
   }

   public boolean isTypeResolved() {
      return this._typeref != null;
   }

   public void resolveTypeRef(SchemaType.Ref typeref) {
      if (this._typeref != null) {
         throw new IllegalStateException();
      } else {
         this._typeref = typeref;
      }
   }

   public boolean isAttribute() {
      return false;
   }

   public SchemaType getType() {
      return this._typeref == null ? null : this._typeref.get();
   }

   public String getDefaultText() {
      return this._defaultText;
   }

   public boolean isDefault() {
      return this._isDefault;
   }

   public boolean isFixed() {
      return this._isFixed;
   }

   public void setDefault(String deftext, boolean isFixed, XmlObject parseObject) {
      this.mutate();
      this._defaultText = deftext;
      this._isDefault = deftext != null;
      this._isFixed = isFixed;
      this._parseObject = parseObject;
   }

   public boolean isNillable() {
      return this._isNillable;
   }

   public void setNillable(boolean nillable) {
      this.mutate();
      this._isNillable = nillable;
   }

   public XmlAnySimpleType getDefaultValue() {
      if (this._defaultValue != null) {
         return this._defaultValue.get();
      } else if (this._defaultText != null && XmlAnySimpleType.type.isAssignableFrom(this.getType())) {
         if (this._parseObject != null && XmlQName.type.isAssignableFrom(this.getType())) {
            XmlAnySimpleType var1;
            try {
               NamespaceContext.push(new NamespaceContext(this._parseObject));
               var1 = this.getType().newValue(this._defaultText);
            } finally {
               NamespaceContext.pop();
            }

            return var1;
         } else {
            return this.getType().newValue(this._defaultText);
         }
      } else {
         return null;
      }
   }

   public void setDefaultValue(XmlValueRef defaultRef) {
      this.mutate();
      this._defaultValue = defaultRef;
   }

   public Object getUserData() {
      return this._userData;
   }

   public void setUserData(Object data) {
      this._userData = data;
   }
}
