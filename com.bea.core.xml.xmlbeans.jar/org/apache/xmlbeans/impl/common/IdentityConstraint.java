package org.apache.xmlbeans.impl.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import org.apache.xmlbeans.SchemaIdentityConstraint;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlID;
import org.apache.xmlbeans.XmlIDREF;
import org.apache.xmlbeans.XmlIDREFS;
import org.apache.xmlbeans.XmlObject;

public class IdentityConstraint {
   private ConstraintState _constraintStack;
   private ElementState _elementStack;
   private Collection _errorListener;
   private boolean _invalid;
   private boolean _trackIdrefs;

   public IdentityConstraint(Collection errorListener, boolean trackIdrefs) {
      this._errorListener = errorListener;
      this._trackIdrefs = trackIdrefs;
   }

   public void element(ValidatorListener.Event e, SchemaType st, SchemaIdentityConstraint[] ics) {
      this.newState();

      for(ConstraintState cs = this._constraintStack; cs != null; cs = cs._next) {
         cs.element(e, st);
      }

      for(int i = 0; ics != null && i < ics.length; ++i) {
         this.newConstraintState(ics[i], e, st);
      }

   }

   public void endElement(ValidatorListener.Event e) {
      ConstraintState cs;
      if (this._elementStack._hasConstraints) {
         for(cs = this._constraintStack; cs != null && cs != this._elementStack._savePoint; cs = cs._next) {
            cs.remove(e);
         }

         this._constraintStack = this._elementStack._savePoint;
      }

      this._elementStack = this._elementStack._next;

      for(cs = this._constraintStack; cs != null; cs = cs._next) {
         cs.endElement(e);
      }

   }

   public void attr(ValidatorListener.Event e, QName name, SchemaType st, String value) {
      for(ConstraintState cs = this._constraintStack; cs != null; cs = cs._next) {
         cs.attr(e, name, st, value);
      }

   }

   public void text(ValidatorListener.Event e, SchemaType st, String value, boolean emptyContent) {
      for(ConstraintState cs = this._constraintStack; cs != null; cs = cs._next) {
         cs.text(e, st, value, emptyContent);
      }

   }

   public boolean isValid() {
      return !this._invalid;
   }

   private void newConstraintState(SchemaIdentityConstraint ic, ValidatorListener.Event e, SchemaType st) {
      if (ic.getConstraintCategory() == 2) {
         new KeyrefState(ic, e, st);
      } else {
         new SelectorState(ic, e, st);
      }

   }

   private void buildIdStates() {
      IdState ids = new IdState();
      if (this._trackIdrefs) {
         new IdRefState(ids);
      }

   }

   private void newState() {
      boolean firstTime = this._elementStack == null;
      ElementState st = new ElementState();
      st._next = this._elementStack;
      this._elementStack = st;
      if (firstTime) {
         this.buildIdStates();
      }

   }

   private void emitError(ValidatorListener.Event event, String code, Object[] args) {
      this._invalid = true;
      if (this._errorListener != null) {
         assert event != null;

         this._errorListener.add(errorForEvent(code, args, 0, event));
      }

   }

   public static XmlError errorForEvent(String code, Object[] args, int severity, ValidatorListener.Event event) {
      XmlCursor loc = event.getLocationAsCursor();
      XmlError error;
      if (loc != null) {
         error = XmlError.forCursor(code, args, severity, loc);
      } else {
         Location location = event.getLocation();
         if (location != null) {
            error = XmlError.forLocation(code, args, severity, location.getSystemId(), location.getLineNumber(), location.getColumnNumber(), location.getCharacterOffset());
         } else {
            error = XmlError.forMessage(code, args, severity);
         }
      }

      return error;
   }

   private void emitError(ValidatorListener.Event event, String msg) {
      this._invalid = true;
      if (this._errorListener != null) {
         assert event != null;

         this._errorListener.add(errorForEvent(msg, 0, event));
      }

   }

   public static XmlError errorForEvent(String msg, int severity, ValidatorListener.Event event) {
      XmlCursor loc = event.getLocationAsCursor();
      XmlError error;
      if (loc != null) {
         error = XmlError.forCursor(msg, severity, loc);
      } else {
         Location location = event.getLocation();
         if (location != null) {
            error = XmlError.forLocation(msg, severity, location.getSystemId(), location.getLineNumber(), location.getColumnNumber(), location.getCharacterOffset());
         } else {
            error = XmlError.forMessage(msg, severity);
         }
      }

      return error;
   }

   private void setSavePoint(ConstraintState cs) {
      if (!this._elementStack._hasConstraints) {
         this._elementStack._savePoint = cs;
      }

      this._elementStack._hasConstraints = true;
   }

   private static XmlObject newValue(SchemaType st, String value) {
      try {
         return st.newValue(value);
      } catch (IllegalArgumentException var3) {
         return null;
      }
   }

   static SchemaType getSimpleType(SchemaType st) {
      assert st.isSimpleType() || st.getContentType() == 2 : st + " does not have simple content.";

      while(!st.isSimpleType()) {
         st = st.getBaseType();
      }

      return st;
   }

   static boolean hasSimpleContent(SchemaType st) {
      return st.isSimpleType() || st.getContentType() == 2;
   }

   private static class ElementState {
      ElementState _next;
      boolean _hasConstraints;
      ConstraintState _savePoint;

      private ElementState() {
      }

      // $FF: synthetic method
      ElementState(Object x0) {
         this();
      }
   }

   public class IdRefState extends ConstraintState {
      IdState _ids;
      List _values;

      IdRefState(IdState ids) {
         super();
         this._ids = ids;
         this._values = new ArrayList();
      }

      private void handleValue(ValidatorListener.Event e, SchemaType st, String value) {
         if (value != null) {
            if (st != null && !st.isNoType()) {
               if (XmlIDREFS.type.isAssignableFrom(st)) {
                  XmlIDREFS lv = (XmlIDREFS)IdentityConstraint.newValue(XmlIDREFS.type, value);
                  if (lv == null) {
                     return;
                  }

                  List l = lv.xgetListValue();

                  for(int i = 0; i < l.size(); ++i) {
                     XmlObjectList xmlValue = new XmlObjectList(1);
                     XmlIDREF idref = (XmlIDREF)l.get(i);
                     xmlValue.set(idref, 0);
                     this._values.add(xmlValue);
                  }
               } else if (XmlIDREF.type.isAssignableFrom(st)) {
                  XmlObjectList xmlValuex = new XmlObjectList(1);
                  XmlIDREF idrefx = (XmlIDREF)st.newValue(value);
                  if (idrefx == null) {
                     return;
                  }

                  xmlValuex.set(idrefx, 0);
                  this._values.add(xmlValuex);
               }

            }
         }
      }

      void attr(ValidatorListener.Event e, QName name, SchemaType st, String value) {
         this.handleValue(e, st, value);
      }

      void text(ValidatorListener.Event e, SchemaType st, String value, boolean emptyContent) {
         if (!emptyContent) {
            this.handleValue(e, st, value);
         }
      }

      void remove(ValidatorListener.Event e) {
         Iterator it = this._values.iterator();

         while(it.hasNext()) {
            Object o = it.next();
            if (!this._ids._values.contains(o)) {
               IdentityConstraint.this.emitError(e, "ID not found for IDRef value '" + o + "'");
            }
         }

      }

      void element(ValidatorListener.Event e, SchemaType st) {
      }

      void endElement(ValidatorListener.Event e) {
      }
   }

   public class IdState extends ConstraintState {
      Set _values = new LinkedHashSet();

      IdState() {
         super();
      }

      void attr(ValidatorListener.Event e, QName name, SchemaType st, String value) {
         this.handleValue(e, st, value);
      }

      void text(ValidatorListener.Event e, SchemaType st, String value, boolean emptyContent) {
         if (!emptyContent) {
            this.handleValue(e, st, value);
         }
      }

      private void handleValue(ValidatorListener.Event e, SchemaType st, String value) {
         if (value != null) {
            if (st != null && !st.isNoType()) {
               if (XmlID.type.isAssignableFrom(st)) {
                  XmlObjectList xmlValue = new XmlObjectList(1);
                  XmlObject o = IdentityConstraint.newValue(XmlID.type, value);
                  if (o == null) {
                     return;
                  }

                  xmlValue.set(o, 0);
                  if (this._values.contains(xmlValue)) {
                     IdentityConstraint.this.emitError(e, "cvc-id.2", new Object[]{value});
                  } else {
                     this._values.add(xmlValue);
                  }
               }

            }
         }
      }

      void element(ValidatorListener.Event e, SchemaType st) {
      }

      void endElement(ValidatorListener.Event e) {
      }

      void remove(ValidatorListener.Event e) {
      }
   }

   public class FieldState extends ConstraintState {
      SelectorState _selector;
      XPath.ExecutionContext[] _contexts;
      boolean[] _needsValue;
      XmlObjectList _value;

      FieldState(SelectorState selector, ValidatorListener.Event e, SchemaType st) {
         super();
         this._selector = selector;
         SchemaIdentityConstraint ic = selector._constraint;
         int fieldCount = ic.getFields().length;
         this._contexts = new XPath.ExecutionContext[fieldCount];
         this._needsValue = new boolean[fieldCount];
         this._value = new XmlObjectList(fieldCount);

         for(int i = 0; i < fieldCount; ++i) {
            this._contexts[i] = new XPath.ExecutionContext();
            this._contexts[i].init((XPath)ic.getFieldPath(i));
            if ((this._contexts[i].start() & 1) != 0) {
               if (!IdentityConstraint.hasSimpleContent(st)) {
                  IdentityConstraint.this.emitError(e, "Identity constraint field must have simple content");
               } else {
                  this._needsValue[i] = true;
               }
            }
         }

      }

      void element(ValidatorListener.Event e, SchemaType st) {
         int i;
         for(i = 0; i < this._contexts.length; ++i) {
            if (this._needsValue[i]) {
               IdentityConstraint.this.emitError(e, "Identity constraint field must have simple content");
               this._needsValue[i] = false;
            }
         }

         for(i = 0; i < this._contexts.length; ++i) {
            if ((this._contexts[i].element(e.getName()) & 1) != 0) {
               if (!IdentityConstraint.hasSimpleContent(st)) {
                  IdentityConstraint.this.emitError(e, "Identity constraint field must have simple content");
               } else {
                  this._needsValue[i] = true;
               }
            }
         }

      }

      void attr(ValidatorListener.Event e, QName name, SchemaType st, String value) {
         if (value != null) {
            for(int i = 0; i < this._contexts.length; ++i) {
               if (this._contexts[i].attr(name)) {
                  XmlObject o = IdentityConstraint.newValue(st, value);
                  if (o == null) {
                     return;
                  }

                  boolean set = this._value.set(o, i);
                  if (!set) {
                     IdentityConstraint.this.emitError(e, "Multiple instances of field with xpath: '" + this._selector._constraint.getFields()[i] + "' for a selector");
                  }
               }
            }

         }
      }

      void text(ValidatorListener.Event e, SchemaType st, String value, boolean emptyContent) {
         if (value != null || emptyContent) {
            for(int i = 0; i < this._contexts.length; ++i) {
               if (this._needsValue[i]) {
                  if (emptyContent || !IdentityConstraint.hasSimpleContent(st)) {
                     IdentityConstraint.this.emitError(e, "Identity constraint field must have simple content");
                     return;
                  }

                  SchemaType simpleType = IdentityConstraint.getSimpleType(st);
                  XmlObject o = IdentityConstraint.newValue(simpleType, value);
                  if (o == null) {
                     return;
                  }

                  boolean set = this._value.set(o, i);
                  if (!set) {
                     IdentityConstraint.this.emitError(e, "Multiple instances of field with xpath: '" + this._selector._constraint.getFields()[i] + "' for a selector");
                  }
               }
            }

         }
      }

      void endElement(ValidatorListener.Event e) {
         for(int i = 0; i < this._needsValue.length; ++i) {
            this._contexts[i].end();
            this._needsValue[i] = false;
         }

      }

      void remove(ValidatorListener.Event e) {
         if (this._selector._constraint.getConstraintCategory() == 1 && this._value.unfilled() >= 0) {
            IdentityConstraint.this.emitError(e, "Key " + QNameHelper.pretty(this._selector._constraint.getName()) + " is missing field with xpath: '" + this._selector._constraint.getFields()[this._value.unfilled()] + "'");
         } else {
            this._selector.addFields(this._value, e);
         }

      }
   }

   public class KeyrefState extends SelectorState {
      Map _keyValues = new HashMap();
      private Object CHILD_ADDED = new Object();
      private Object CHILD_REMOVED = new Object();
      private Object SELF_ADDED = new Object();

      KeyrefState(SchemaIdentityConstraint constraint, ValidatorListener.Event e, SchemaType st) {
         super(constraint, e, st);
      }

      void addKeyValues(Set values, boolean child) {
         Iterator it = values.iterator();

         while(it.hasNext()) {
            Object key = it.next();
            Object value = this._keyValues.get(key);
            if (value == null) {
               this._keyValues.put(key, child ? this.CHILD_ADDED : this.SELF_ADDED);
            } else if (value == this.CHILD_ADDED) {
               if (child) {
                  this._keyValues.put(key, this.CHILD_REMOVED);
               } else {
                  this._keyValues.put(key, this.SELF_ADDED);
               }
            } else if (value == this.CHILD_REMOVED && !child) {
               this._keyValues.put(key, this.SELF_ADDED);
            }
         }

      }

      private boolean hasKeyValue(Object key) {
         Object value = this._keyValues.get(key);
         return value != null && value != this.CHILD_REMOVED;
      }

      void remove(ValidatorListener.Event e) {
         for(ConstraintState cs = this._next; cs != null && cs != IdentityConstraint.this._elementStack._savePoint; cs = cs._next) {
            if (cs instanceof SelectorState) {
               SelectorState sel = (SelectorState)cs;
               if (sel._constraint == this._constraint.getReferencedKey()) {
                  this.addKeyValues(sel._values, false);
               }
            }
         }

         Iterator it = this._values.iterator();

         XmlObjectList fields;
         do {
            if (!it.hasNext()) {
               return;
            }

            fields = (XmlObjectList)it.next();
         } while(fields.unfilled() >= 0 || this.hasKeyValue(fields));

         IdentityConstraint.this.emitError(e, "cvc-identity-constraint.4.3", new Object[]{fields, QNameHelper.pretty(this._constraint.getName())});
      }
   }

   public class SelectorState extends ConstraintState {
      SchemaIdentityConstraint _constraint;
      Set _values = new LinkedHashSet();
      XPath.ExecutionContext _context;

      SelectorState(SchemaIdentityConstraint constraint, ValidatorListener.Event e, SchemaType st) {
         super();
         this._constraint = constraint;
         this._context = new XPath.ExecutionContext();
         this._context.init((XPath)this._constraint.getSelectorPath());
         if ((this._context.start() & 1) != 0) {
            this.createFieldState(e, st);
         }

      }

      void addFields(XmlObjectList fields, ValidatorListener.Event e) {
         if (this._constraint.getConstraintCategory() == 2) {
            this._values.add(fields);
         } else if (this._values.contains(fields)) {
            if (this._constraint.getConstraintCategory() == 3) {
               IdentityConstraint.this.emitError(e, "cvc-identity-constraint.4.1", new Object[]{fields, QNameHelper.pretty(this._constraint.getName())});
            } else {
               IdentityConstraint.this.emitError(e, "cvc-identity-constraint.4.2.2", new Object[]{fields, QNameHelper.pretty(this._constraint.getName())});
            }
         } else {
            this._values.add(fields);
         }

      }

      void element(ValidatorListener.Event e, SchemaType st) {
         if ((this._context.element(e.getName()) & 1) != 0) {
            this.createFieldState(e, st);
         }

      }

      void endElement(ValidatorListener.Event e) {
         this._context.end();
      }

      void createFieldState(ValidatorListener.Event e, SchemaType st) {
         IdentityConstraint.this.new FieldState(this, e, st);
      }

      void remove(ValidatorListener.Event e) {
         for(ConstraintState cs = this._next; cs != null; cs = cs._next) {
            if (cs instanceof KeyrefState) {
               KeyrefState kr = (KeyrefState)cs;
               if (kr._constraint.getReferencedKey() == this._constraint) {
                  kr.addKeyValues(this._values, true);
               }
            }
         }

      }

      void attr(ValidatorListener.Event e, QName name, SchemaType st, String value) {
      }

      void text(ValidatorListener.Event e, SchemaType st, String value, boolean emptyContent) {
      }
   }

   public abstract class ConstraintState {
      ConstraintState _next;

      ConstraintState() {
         IdentityConstraint.this.setSavePoint(IdentityConstraint.this._constraintStack);
         this._next = IdentityConstraint.this._constraintStack;
         IdentityConstraint.this._constraintStack = this;
      }

      abstract void element(ValidatorListener.Event var1, SchemaType var2);

      abstract void endElement(ValidatorListener.Event var1);

      abstract void attr(ValidatorListener.Event var1, QName var2, SchemaType var3, String var4);

      abstract void text(ValidatorListener.Event var1, SchemaType var2, String var3, boolean var4);

      abstract void remove(ValidatorListener.Event var1);
   }
}
