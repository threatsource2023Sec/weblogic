package com.octetstring.vde.util;

import com.octetstring.ldapv3.Filter;
import com.octetstring.nls.Messages;
import com.octetstring.vde.syntax.DirectoryString;
import java.util.StringTokenizer;
import java.util.Vector;

public class LDAPURL {
   private String host = null;
   private int port = 389;
   private DirectoryString base = null;
   private Vector attributes = null;
   private int scope = 0;
   private Filter filter = null;
   private String extensions = null;

   public LDAPURL() {
   }

   public LDAPURL(String ldapurl) {
      try {
         LDAPURL url = fromString(ldapurl);
         this.host = url.getHost();
         this.port = url.getPort();
         this.base = url.getBase();
         this.attributes = url.getAttributes();
         this.scope = url.getScope();
         this.filter = url.getFilter();
      } catch (DirectoryException var3) {
      }
   }

   public static LDAPURL fromString(String ldapurl) throws DirectoryException {
      LDAPURL url = new LDAPURL();
      StringTokenizer st = new StringTokenizer(ldapurl, "/", true);

      try {
         String scheme = st.nextToken();
         if (!scheme.equals("ldap:")) {
            throw new DirectoryException(2, Messages.getString("Not_an_LDAP_URL_3"));
         } else {
            String discard = st.nextToken();
            discard = st.nextToken();
            String hostport = st.nextToken();
            int colon = hostport.indexOf(":");
            if (colon < 0) {
               url.setHost(hostport);
            } else {
               url.setHost(hostport.substring(0, colon));
               url.setPort(new Integer(hostport.substring(colon + 1)));
            }

            StringBuffer sb = new StringBuffer();

            while(st.hasMoreElements()) {
               sb.append(st.nextElement());
            }

            String criteria = sb.toString();
            st = new StringTokenizer(criteria, "?", true);
            int qmnum = 0;

            while(true) {
               while(st.hasMoreTokens()) {
                  String tok = st.nextToken();
                  if (tok.equals("?")) {
                     ++qmnum;
                  } else if (qmnum == 0) {
                     url.setBase(new DirectoryString(tok));
                  } else if (qmnum != 1) {
                     if (qmnum == 2) {
                        if (tok.equals("one")) {
                           url.setScope(1);
                        } else if (tok.equals("sub")) {
                           url.setScope(2);
                        }
                     } else if (qmnum == 3) {
                        url.setFilter(ParseFilter.parse(tok));
                     }
                  } else {
                     StringTokenizer ast = new StringTokenizer(tok, ",");
                     Vector attrs = new Vector();

                     while(ast.hasMoreTokens()) {
                        attrs.addElement(new DirectoryString(ast.nextToken()));
                     }

                     url.setAttributes(attrs);
                  }
               }

               return url;
            }
         }
      } catch (Exception var13) {
         throw new DirectoryException(2, Messages.getString("Invalid_LDAP_URL_10"));
      }
   }

   public String getHost() {
      return this.host;
   }

   public void setHost(String v) {
      this.host = v;
   }

   public int getPort() {
      return this.port;
   }

   public void setPort(int v) {
      this.port = v;
   }

   public DirectoryString getBase() {
      return this.base;
   }

   public void setBase(DirectoryString v) {
      this.base = v;
   }

   public int getScope() {
      return this.scope;
   }

   public void setScope(int v) {
      this.scope = v;
   }

   public Vector getAttributes() {
      return this.attributes;
   }

   public void setAttributes(Vector v) {
      this.attributes = v;
   }

   public Filter getFilter() {
      return this.filter;
   }

   public void setFilter(Filter v) {
      this.filter = v;
   }
}
