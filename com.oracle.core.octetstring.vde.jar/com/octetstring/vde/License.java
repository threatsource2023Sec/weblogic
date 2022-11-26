package com.octetstring.vde;

import com.octetstring.nls.Messages;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

public class License {
   private String company = null;
   private String product = null;
   private String expirationdate = null;
   private byte[] licensehash = null;
   private boolean validated = false;
   private static final String NONCE = "octstring";

   public License(String company, String product, String expirationdate, String keyencoding) {
      this.company = company;
      this.product = product;
      this.expirationdate = expirationdate;
      this.licensehash = new byte[16];
      StringTokenizer kt = new StringTokenizer(keyencoding, ":");

      for(int counter = 0; kt.hasMoreTokens(); ++counter) {
         this.licensehash[counter] = Integer.decode("0x" + kt.nextToken()).byteValue();
      }

   }

   public License() {
      Properties licenseprop = new Properties();
      String ihome = System.getProperty("vde.home");
      String fullpath = null;
      if (ihome == null) {
         fullpath = (String)ServerConfig.getInstance().get("vde.licensefile");
      } else {
         fullpath = ihome + "/" + (String)ServerConfig.getInstance().get("vde.licensefile");
      }

      try {
         FileInputStream is = new FileInputStream(fullpath);
         licenseprop.load(is);
         is.close();
      } catch (FileNotFoundException var7) {
         Logger.getInstance().log(0, this, Messages.getString("License_File_Not_Found___6") + fullpath);
         return;
      } catch (IOException var8) {
         Logger.getInstance().log(0, this, Messages.getString("IO_Error_Reading_License_File___7") + fullpath);
         return;
      }

      this.company = (String)licenseprop.get("company");
      this.product = (String)licenseprop.get("product");
      this.expirationdate = (String)licenseprop.get("expirationdate");
      String keyencoding = (String)licenseprop.get("key");
      this.licensehash = new byte[16];
      StringTokenizer kt = new StringTokenizer(keyencoding, ":");

      for(int counter = 0; kt.hasMoreTokens(); ++counter) {
         this.licensehash[counter] = Integer.decode("0x" + kt.nextToken()).byteValue();
      }

      this.validated = true;
   }

   public boolean checkLicense(String product) {
      this.validated = true;
      if (this.validated && this.product.equals(product)) {
         if (this.expirationdate.equals("never")) {
            return true;
         } else {
            DateFormat df = DateFormat.getDateInstance(3, Locale.US);
            Date exdate = null;

            try {
               exdate = df.parse(this.expirationdate);
            } catch (ParseException var5) {
               return false;
            }

            Date nowdate = new Date();
            if (nowdate.after(exdate)) {
               Logger.getInstance().log(0, this, Messages.getString("License_Expired!_Contact_sales@octetstring.com!_23"));
               return false;
            } else {
               return true;
            }
         }
      } else {
         return false;
      }
   }
}
