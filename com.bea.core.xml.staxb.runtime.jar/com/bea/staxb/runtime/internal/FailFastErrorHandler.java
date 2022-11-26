package com.bea.staxb.runtime.internal;

import com.bea.staxb.runtime.internal.util.collections.EmptyIterator;
import com.bea.xml.XmlError;
import com.bea.xml.XmlRuntimeException;
import java.util.AbstractCollection;
import java.util.Iterator;

public class FailFastErrorHandler extends AbstractCollection {
   private static final FailFastErrorHandler INSTANCE = new FailFastErrorHandler();

   public static FailFastErrorHandler getInstance() {
      return INSTANCE;
   }

   private FailFastErrorHandler() {
   }

   public boolean add(Object obj) {
      if (obj instanceof XmlError) {
         XmlError err = (XmlError)obj;
         throw new XmlRuntimeException(err);
      } else {
         throw new XmlRuntimeException("unknown error: " + obj);
      }
   }

   public Iterator iterator() {
      return EmptyIterator.getInstance();
   }

   public int size() {
      return 0;
   }
}
