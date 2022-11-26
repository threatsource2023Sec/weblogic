package com.bea.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class XmlRuntimeException extends RuntimeException {
   private static final long serialVersionUID = 1L;
   private List _errors;

   public XmlRuntimeException(String m) {
      super(m);
   }

   public XmlRuntimeException(String m, Throwable t) {
      super(m, t);
   }

   public XmlRuntimeException(Throwable t) {
      super(t);
   }

   public XmlRuntimeException(String m, Throwable t, Collection errors) {
      super(m, t);
      if (errors != null) {
         this._errors = Collections.unmodifiableList(new ArrayList(errors));
      }

   }

   public XmlRuntimeException(XmlError error) {
      this(error.toString(), (Throwable)null, (XmlError)error);
   }

   public XmlRuntimeException(String m, Throwable t, XmlError error) {
      this(m, t, (Collection)Collections.singletonList(error));
   }

   public XmlRuntimeException(XmlException xmlException) {
      super(xmlException.getMessage(), xmlException.getCause());
      Collection errors = xmlException.getErrors();
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
