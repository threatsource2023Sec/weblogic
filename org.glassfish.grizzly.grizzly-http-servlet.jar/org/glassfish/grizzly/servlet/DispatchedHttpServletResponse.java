package org.glassfish.grizzly.servlet;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class DispatchedHttpServletResponse extends HttpServletResponseWrapper {
   private boolean included = false;

   public DispatchedHttpServletResponse(HttpServletResponse response, boolean included) {
      super(response);
      this.included = included;
      this.setResponse(response);
   }

   private void setResponse(HttpServletResponse response) {
      super.setResponse(response);
   }

   public void setContentLength(int len) {
      if (!this.included) {
         super.setContentLength(len);
      }
   }

   public void setContentType(String type) {
      if (!this.included) {
         super.setContentType(type);
      }
   }

   public void setBufferSize(int size) {
      if (!this.included) {
         super.setBufferSize(size);
      }
   }

   public void reset() {
      if (!this.included) {
         super.reset();
      }
   }

   public void setLocale(Locale loc) {
      if (!this.included) {
         super.setLocale(loc);
      }
   }

   public void addCookie(Cookie cookie) {
      if (!this.included) {
         super.addCookie(cookie);
      }
   }

   public void sendError(int sc, String msg) throws IOException {
      if (!this.included) {
         super.sendError(sc, msg);
      }
   }

   public void sendError(int sc) throws IOException {
      if (!this.included) {
         super.sendError(sc);
      }
   }

   public void sendRedirect(String location) throws IOException {
      if (!this.included) {
         super.sendRedirect(location);
      }
   }

   public void setDateHeader(String name, long date) {
      if (!this.included) {
         super.setDateHeader(name, date);
      }
   }

   public void addDateHeader(String name, long date) {
      if (!this.included) {
         super.addDateHeader(name, date);
      }
   }

   public void setHeader(String name, String value) {
      if (!this.included) {
         super.setHeader(name, value);
      }
   }

   public void addHeader(String name, String value) {
      if (!this.included) {
         super.addHeader(name, value);
      }
   }

   public void setIntHeader(String name, int value) {
      if (!this.included) {
         super.setIntHeader(name, value);
      }
   }

   public void addIntHeader(String name, int value) {
      if (!this.included) {
         super.addIntHeader(name, value);
      }
   }

   public void setStatus(int sc) {
      if (!this.included) {
         super.setStatus(sc);
      }
   }

   public void setStatus(int sc, String sm) {
      if (!this.included) {
         super.setStatus(sc, sm);
      }
   }

   public void setCharacterEncoding(String charEnc) {
      if (!this.included) {
         super.setCharacterEncoding(charEnc);
      }
   }
}
