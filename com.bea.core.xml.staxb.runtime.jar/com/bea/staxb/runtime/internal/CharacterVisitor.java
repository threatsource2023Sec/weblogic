package com.bea.staxb.runtime.internal;

import com.bea.xml.XmlException;
import java.util.Collection;
import javax.xml.namespace.QName;

final class CharacterVisitor extends XmlTypeVisitor {
   private final CharSequence chars;

   CharacterVisitor(RuntimeBindingProperty property, Object parentObject, PullMarshalResult result) throws XmlException {
      super(parentObject, property, result);

      assert !(parentObject instanceof Collection);

      if (parentObject == null) {
         this.chars = null;
      } else {
         this.chars = this.grabChars();
      }

   }

   protected int getState() {
      return 3;
   }

   protected int advance() throws XmlException {
      return 3;
   }

   public XmlTypeVisitor getCurrentChild() throws XmlException {
      throw new AssertionError("no children");
   }

   protected QName getName() {
      throw new AssertionError("no name on " + this);
   }

   protected String getLocalPart() {
      throw new AssertionError("no name on " + this);
   }

   protected String getNamespaceURI() {
      throw new AssertionError("no name on " + this);
   }

   protected String getPrefix() {
      throw new AssertionError("no name on " + this);
   }

   final void predefineNamespaces() throws XmlException {
   }

   protected CharSequence getCharData() {
      return this.chars;
   }

   private CharSequence grabChars() throws XmlException {
      Object parent = this.getParentObject();

      assert parent != null : "bad visitor: this=" + this;

      return this.getBindingProperty().getLexical(parent, this.marshalResult);
   }
}
