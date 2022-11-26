package org.apache.xmlbeans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class XmlException extends Exception {
   private static final long serialVersionUID = 1L;
   private List _errors;

   public XmlException(String m) {
      super(m);
   }

   public XmlException(String m, Throwable t) {
      super(m, t);
   }

   public XmlException(Throwable t) {
      super(t);
   }

   public XmlException(XmlError error) {
      this(error.toString(), (Throwable)null, (XmlError)error);
   }

   public XmlException(String m, Throwable t, XmlError error) {
      this(m, t, (Collection)Collections.singletonList(error));
   }

   public XmlException(String m, Throwable t, Collection errors) {
      super(m, t);
      if (errors != null) {
         this._errors = Collections.unmodifiableList(new ArrayList(errors));
      }

   }

   public XmlException(XmlRuntimeException xmlRuntimeException) {
      super(xmlRuntimeException.getMessage(), xmlRuntimeException.getCause());
      Collection errors = xmlRuntimeException.getErrors();
      if (errors != null) {
         this._errors = Collections.unmodifiableList(new ArrayList(errors));
      }

   }

   public XmlError getError() {
      return this._errors != null && this._errors.size() != 0 ? (XmlError)this._errors.get(0) : null;
   }

   public Collection getErrors() {
      return this._errors;
   }
}
