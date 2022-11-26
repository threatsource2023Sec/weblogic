package com.octetstring.vde.util;

import com.octetstring.vde.syntax.DirectoryString;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

public class DNUtility {
   private static DNUtility instance = null;

   private DNUtility() {
   }

   public static DNUtility getInstance() {
      if (instance == null) {
         instance = new DNUtility();
      }

      return instance;
   }

   public DirectoryString createDN(Vector rdnComponents) throws InvalidDNException {
      StringBuffer dn = new StringBuffer(64);
      Enumeration rdnEnum = rdnComponents.elements();

      while(rdnEnum.hasMoreElements()) {
         String rdn = (String)rdnEnum.nextElement();
         if (rdn.indexOf("=") < 0) {
            throw new InvalidDNException();
         }

         dn.append(rdn.trim());
         if (rdnEnum.hasMoreElements()) {
            dn.append(",");
         }
      }

      return new DirectoryString(dn.toString());
   }

   public Vector explodeDN(DirectoryString dn) {
      Vector rdnComponents = new Vector();
      if (dn != null) {
         String dnStr = dn.toString();
         if (dnStr != null) {
            StringTokenizer dnTok = new StringTokenizer(dnStr, ",;");
            String lasttoken = null;

            while(true) {
               while(dnTok.hasMoreTokens()) {
                  String rdn;
                  for(rdn = dnTok.nextToken(); rdn.endsWith("\\") && !rdn.endsWith("\\\\") && dnTok.hasMoreTokens(); rdn = rdn.concat(dnTok.nextToken().trim())) {
                     rdn = rdn.concat(",");
                  }

                  rdn = rdn.trim();
                  if (lasttoken != null && (lasttoken.indexOf("=") < 0 || rdn.indexOf("=") < 0)) {
                     lasttoken = lasttoken + "," + rdn;
                  } else {
                     if (lasttoken != null) {
                        rdnComponents.addElement(lasttoken);
                     }

                     lasttoken = rdn;
                  }
               }

               if (lasttoken != null) {
                  rdnComponents.addElement(lasttoken);
               }
               break;
            }
         }
      }

      return rdnComponents;
   }

   public DirectoryString normalize(DirectoryString dn) throws InvalidDNException {
      return this.createDN(this.explodeDN(dn));
   }
}
