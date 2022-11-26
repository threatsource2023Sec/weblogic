package com.sun.faces.context;

import com.sun.faces.util.Util;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class RequestCookieMap extends BaseContextMap {
   private final HttpServletRequest request;

   public RequestCookieMap(HttpServletRequest newRequest) {
      this.request = newRequest;
   }

   public Object get(Object key) {
      Util.notNull("key", key);
      Cookie[] cookies = this.request.getCookies();
      if (null == cookies) {
         return null;
      } else {
         String keyString = key.toString();
         Object result = null;

         for(int i = 0; i < cookies.length; ++i) {
            if (cookies[i].getName().equals(keyString)) {
               result = cookies[i];
               break;
            }
         }

         return result;
      }
   }

   public Set entrySet() {
      return Collections.unmodifiableSet(super.entrySet());
   }

   public Set keySet() {
      return Collections.unmodifiableSet(super.keySet());
   }

   public Collection values() {
      return Collections.unmodifiableCollection(super.values());
   }

   public boolean equals(Object obj) {
      return obj != null && obj.getClass() == ExternalContextImpl.theUnmodifiableMapClass && super.equals(obj);
   }

   public int hashCode() {
      int hashCode = 7 * this.request.hashCode();

      for(Iterator i = this.entrySet().iterator(); i.hasNext(); hashCode += i.next().hashCode()) {
      }

      return hashCode;
   }

   protected Iterator getEntryIterator() {
      return new BaseContextMap.EntryIterator(new CookieArrayEnumerator(this.request.getCookies()));
   }

   protected Iterator getKeyIterator() {
      return new BaseContextMap.KeyIterator(new CookieArrayEnumerator(this.request.getCookies()));
   }

   protected Iterator getValueIterator() {
      return new BaseContextMap.ValueIterator(new CookieArrayEnumerator(this.request.getCookies()));
   }

   private static class CookieArrayEnumerator implements Enumeration {
      Cookie[] cookies;
      int curIndex = -1;
      int upperBound;

      public CookieArrayEnumerator(Cookie[] cookies) {
         this.cookies = cookies;
         this.upperBound = this.cookies != null ? this.cookies.length : -1;
      }

      public boolean hasMoreElements() {
         return this.curIndex + 2 <= this.upperBound;
      }

      public Object nextElement() {
         ++this.curIndex;
         if (this.curIndex < this.upperBound) {
            return this.cookies[this.curIndex].getName();
         } else {
            throw new NoSuchElementException();
         }
      }
   }
}
