package org.apache.xmlbeans.impl.soap;

import java.util.Iterator;
import java.util.Vector;

public class MimeHeaders {
   protected Vector headers = new Vector();

   public String[] getHeader(String name) {
      Vector vector = new Vector();

      for(int i = 0; i < this.headers.size(); ++i) {
         MimeHeader mimeheader = (MimeHeader)this.headers.elementAt(i);
         if (mimeheader.getName().equalsIgnoreCase(name) && mimeheader.getValue() != null) {
            vector.addElement(mimeheader.getValue());
         }
      }

      if (vector.size() == 0) {
         return null;
      } else {
         String[] as = new String[vector.size()];
         vector.copyInto(as);
         return as;
      }
   }

   public void setHeader(String name, String value) {
      boolean flag = false;
      if (name != null && !name.equals("")) {
         for(int i = 0; i < this.headers.size(); ++i) {
            MimeHeader mimeheader = (MimeHeader)this.headers.elementAt(i);
            if (mimeheader.getName().equalsIgnoreCase(name)) {
               if (!flag) {
                  this.headers.setElementAt(new MimeHeader(mimeheader.getName(), value), i);
                  flag = true;
               } else {
                  this.headers.removeElementAt(i--);
               }
            }
         }

         if (!flag) {
            this.addHeader(name, value);
         }

      } else {
         throw new IllegalArgumentException("Illegal MimeHeader name");
      }
   }

   public void addHeader(String name, String value) {
      if (name != null && !name.equals("")) {
         int i = this.headers.size();

         for(int j = i - 1; j >= 0; --j) {
            MimeHeader mimeheader = (MimeHeader)this.headers.elementAt(j);
            if (mimeheader.getName().equalsIgnoreCase(name)) {
               this.headers.insertElementAt(new MimeHeader(name, value), j + 1);
               return;
            }
         }

         this.headers.addElement(new MimeHeader(name, value));
      } else {
         throw new IllegalArgumentException("Illegal MimeHeader name");
      }
   }

   public void removeHeader(String name) {
      for(int i = 0; i < this.headers.size(); ++i) {
         MimeHeader mimeheader = (MimeHeader)this.headers.elementAt(i);
         if (mimeheader.getName().equalsIgnoreCase(name)) {
            this.headers.removeElementAt(i--);
         }
      }

   }

   public void removeAllHeaders() {
      this.headers.removeAllElements();
   }

   public Iterator getAllHeaders() {
      return this.headers.iterator();
   }

   public Iterator getMatchingHeaders(String[] names) {
      return new MatchingIterator(names, true);
   }

   public Iterator getNonMatchingHeaders(String[] names) {
      return new MatchingIterator(names, false);
   }

   class MatchingIterator implements Iterator {
      private boolean match;
      private Iterator iterator;
      private String[] names;
      private Object nextHeader;

      private Object nextMatch() {
         label37:
         while(true) {
            if (this.iterator.hasNext()) {
               MimeHeader mimeheader = (MimeHeader)this.iterator.next();
               if (this.names == null) {
                  return this.match ? null : mimeheader;
               }

               for(int i = 0; i < this.names.length; ++i) {
                  if (mimeheader.getName().equalsIgnoreCase(this.names[i])) {
                     if (!this.match) {
                        continue label37;
                     }

                     return mimeheader;
                  }
               }

               if (this.match) {
                  continue;
               }

               return mimeheader;
            }

            return null;
         }
      }

      public boolean hasNext() {
         if (this.nextHeader == null) {
            this.nextHeader = this.nextMatch();
         }

         return this.nextHeader != null;
      }

      public Object next() {
         if (this.nextHeader != null) {
            Object obj = this.nextHeader;
            this.nextHeader = null;
            return obj;
         } else {
            return this.hasNext() ? this.nextHeader : null;
         }
      }

      public void remove() {
         this.iterator.remove();
      }

      MatchingIterator(String[] as, boolean flag) {
         this.match = flag;
         this.names = as;
         this.iterator = MimeHeaders.this.headers.iterator();
      }
   }
}
