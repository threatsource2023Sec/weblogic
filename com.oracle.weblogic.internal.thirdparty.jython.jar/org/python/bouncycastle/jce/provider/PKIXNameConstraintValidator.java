package org.python.bouncycastle.jce.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERIA5String;
import org.python.bouncycastle.asn1.x509.GeneralName;
import org.python.bouncycastle.asn1.x509.GeneralSubtree;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.Integers;
import org.python.bouncycastle.util.Strings;

public class PKIXNameConstraintValidator {
   private Set excludedSubtreesDN = new HashSet();
   private Set excludedSubtreesDNS = new HashSet();
   private Set excludedSubtreesEmail = new HashSet();
   private Set excludedSubtreesURI = new HashSet();
   private Set excludedSubtreesIP = new HashSet();
   private Set permittedSubtreesDN;
   private Set permittedSubtreesDNS;
   private Set permittedSubtreesEmail;
   private Set permittedSubtreesURI;
   private Set permittedSubtreesIP;

   private static boolean withinDNSubtree(ASN1Sequence var0, ASN1Sequence var1) {
      if (var1.size() < 1) {
         return false;
      } else if (var1.size() > var0.size()) {
         return false;
      } else {
         for(int var2 = var1.size() - 1; var2 >= 0; --var2) {
            if (!var1.getObjectAt(var2).equals(var0.getObjectAt(var2))) {
               return false;
            }
         }

         return true;
      }
   }

   public void checkPermittedDN(ASN1Sequence var1) throws PKIXNameConstraintValidatorException {
      this.checkPermittedDN(this.permittedSubtreesDN, var1);
   }

   public void checkExcludedDN(ASN1Sequence var1) throws PKIXNameConstraintValidatorException {
      this.checkExcludedDN(this.excludedSubtreesDN, var1);
   }

   private void checkPermittedDN(Set var1, ASN1Sequence var2) throws PKIXNameConstraintValidatorException {
      if (var1 != null) {
         if (!var1.isEmpty() || var2.size() != 0) {
            Iterator var3 = var1.iterator();

            ASN1Sequence var4;
            do {
               if (!var3.hasNext()) {
                  throw new PKIXNameConstraintValidatorException("Subject distinguished name is not from a permitted subtree");
               }

               var4 = (ASN1Sequence)var3.next();
            } while(!withinDNSubtree(var2, var4));

         }
      }
   }

   private void checkExcludedDN(Set var1, ASN1Sequence var2) throws PKIXNameConstraintValidatorException {
      if (!var1.isEmpty()) {
         Iterator var3 = var1.iterator();

         ASN1Sequence var4;
         do {
            if (!var3.hasNext()) {
               return;
            }

            var4 = (ASN1Sequence)var3.next();
         } while(!withinDNSubtree(var2, var4));

         throw new PKIXNameConstraintValidatorException("Subject distinguished name is from an excluded subtree");
      }
   }

   private Set intersectDN(Set var1, Set var2) {
      HashSet var3 = new HashSet();
      Iterator var4 = var2.iterator();

      while(true) {
         while(var4.hasNext()) {
            ASN1Sequence var5 = ASN1Sequence.getInstance(((GeneralSubtree)var4.next()).getBase().getName().toASN1Primitive());
            if (var1 == null) {
               if (var5 != null) {
                  var3.add(var5);
               }
            } else {
               Iterator var6 = var1.iterator();

               while(var6.hasNext()) {
                  ASN1Sequence var7 = (ASN1Sequence)var6.next();
                  if (withinDNSubtree(var5, var7)) {
                     var3.add(var5);
                  } else if (withinDNSubtree(var7, var5)) {
                     var3.add(var7);
                  }
               }
            }
         }

         return var3;
      }
   }

   private Set unionDN(Set var1, ASN1Sequence var2) {
      if (var1.isEmpty()) {
         if (var2 == null) {
            return var1;
         } else {
            var1.add(var2);
            return var1;
         }
      } else {
         HashSet var3 = new HashSet();
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            ASN1Sequence var5 = (ASN1Sequence)var4.next();
            if (withinDNSubtree(var2, var5)) {
               var3.add(var5);
            } else if (withinDNSubtree(var5, var2)) {
               var3.add(var2);
            } else {
               var3.add(var5);
               var3.add(var2);
            }
         }

         return var3;
      }
   }

   private Set intersectEmail(Set var1, Set var2) {
      HashSet var3 = new HashSet();
      Iterator var4 = var2.iterator();

      while(true) {
         while(var4.hasNext()) {
            String var5 = this.extractNameAsString(((GeneralSubtree)var4.next()).getBase());
            if (var1 == null) {
               if (var5 != null) {
                  var3.add(var5);
               }
            } else {
               Iterator var6 = var1.iterator();

               while(var6.hasNext()) {
                  String var7 = (String)var6.next();
                  this.intersectEmail(var5, var7, var3);
               }
            }
         }

         return var3;
      }
   }

   private Set unionEmail(Set var1, String var2) {
      if (var1.isEmpty()) {
         if (var2 == null) {
            return var1;
         } else {
            var1.add(var2);
            return var1;
         }
      } else {
         HashSet var3 = new HashSet();
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            this.unionEmail(var5, var2, var3);
         }

         return var3;
      }
   }

   private Set intersectIP(Set var1, Set var2) {
      HashSet var3 = new HashSet();
      Iterator var4 = var2.iterator();

      while(true) {
         while(var4.hasNext()) {
            byte[] var5 = ASN1OctetString.getInstance(((GeneralSubtree)var4.next()).getBase().getName()).getOctets();
            if (var1 == null) {
               if (var5 != null) {
                  var3.add(var5);
               }
            } else {
               Iterator var6 = var1.iterator();

               while(var6.hasNext()) {
                  byte[] var7 = (byte[])((byte[])var6.next());
                  var3.addAll(this.intersectIPRange(var7, var5));
               }
            }
         }

         return var3;
      }
   }

   private Set unionIP(Set var1, byte[] var2) {
      if (var1.isEmpty()) {
         if (var2 == null) {
            return var1;
         } else {
            var1.add(var2);
            return var1;
         }
      } else {
         HashSet var3 = new HashSet();
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            byte[] var5 = (byte[])((byte[])var4.next());
            var3.addAll(this.unionIPRange(var5, var2));
         }

         return var3;
      }
   }

   private Set unionIPRange(byte[] var1, byte[] var2) {
      HashSet var3 = new HashSet();
      if (Arrays.areEqual(var1, var2)) {
         var3.add(var1);
      } else {
         var3.add(var1);
         var3.add(var2);
      }

      return var3;
   }

   private Set intersectIPRange(byte[] var1, byte[] var2) {
      if (var1.length != var2.length) {
         return Collections.EMPTY_SET;
      } else {
         byte[][] var3 = this.extractIPsAndSubnetMasks(var1, var2);
         byte[] var4 = var3[0];
         byte[] var5 = var3[1];
         byte[] var6 = var3[2];
         byte[] var7 = var3[3];
         byte[][] var8 = this.minMaxIPs(var4, var5, var6, var7);
         byte[] var9 = min(var8[1], var8[3]);
         byte[] var10 = max(var8[0], var8[2]);
         if (compareTo(var10, var9) == 1) {
            return Collections.EMPTY_SET;
         } else {
            byte[] var11 = or(var8[0], var8[2]);
            byte[] var12 = or(var5, var7);
            return Collections.singleton(this.ipWithSubnetMask(var11, var12));
         }
      }
   }

   private byte[] ipWithSubnetMask(byte[] var1, byte[] var2) {
      int var3 = var1.length;
      byte[] var4 = new byte[var3 * 2];
      System.arraycopy(var1, 0, var4, 0, var3);
      System.arraycopy(var2, 0, var4, var3, var3);
      return var4;
   }

   private byte[][] extractIPsAndSubnetMasks(byte[] var1, byte[] var2) {
      int var3 = var1.length / 2;
      byte[] var4 = new byte[var3];
      byte[] var5 = new byte[var3];
      System.arraycopy(var1, 0, var4, 0, var3);
      System.arraycopy(var1, var3, var5, 0, var3);
      byte[] var6 = new byte[var3];
      byte[] var7 = new byte[var3];
      System.arraycopy(var2, 0, var6, 0, var3);
      System.arraycopy(var2, var3, var7, 0, var3);
      return new byte[][]{var4, var5, var6, var7};
   }

   private byte[][] minMaxIPs(byte[] var1, byte[] var2, byte[] var3, byte[] var4) {
      int var5 = var1.length;
      byte[] var6 = new byte[var5];
      byte[] var7 = new byte[var5];
      byte[] var8 = new byte[var5];
      byte[] var9 = new byte[var5];

      for(int var10 = 0; var10 < var5; ++var10) {
         var6[var10] = (byte)(var1[var10] & var2[var10]);
         var7[var10] = (byte)(var1[var10] & var2[var10] | ~var2[var10]);
         var8[var10] = (byte)(var3[var10] & var4[var10]);
         var9[var10] = (byte)(var3[var10] & var4[var10] | ~var4[var10]);
      }

      return new byte[][]{var6, var7, var8, var9};
   }

   private void checkPermittedEmail(Set var1, String var2) throws PKIXNameConstraintValidatorException {
      if (var1 != null) {
         Iterator var3 = var1.iterator();

         String var4;
         do {
            if (!var3.hasNext()) {
               if (var2.length() == 0 && var1.size() == 0) {
                  return;
               }

               throw new PKIXNameConstraintValidatorException("Subject email address is not from a permitted subtree.");
            }

            var4 = (String)var3.next();
         } while(!this.emailIsConstrained(var2, var4));

      }
   }

   private void checkExcludedEmail(Set var1, String var2) throws PKIXNameConstraintValidatorException {
      if (!var1.isEmpty()) {
         Iterator var3 = var1.iterator();

         String var4;
         do {
            if (!var3.hasNext()) {
               return;
            }

            var4 = (String)var3.next();
         } while(!this.emailIsConstrained(var2, var4));

         throw new PKIXNameConstraintValidatorException("Email address is from an excluded subtree.");
      }
   }

   private void checkPermittedIP(Set var1, byte[] var2) throws PKIXNameConstraintValidatorException {
      if (var1 != null) {
         Iterator var3 = var1.iterator();

         byte[] var4;
         do {
            if (!var3.hasNext()) {
               if (var2.length == 0 && var1.size() == 0) {
                  return;
               }

               throw new PKIXNameConstraintValidatorException("IP is not from a permitted subtree.");
            }

            var4 = (byte[])((byte[])var3.next());
         } while(!this.isIPConstrained(var2, var4));

      }
   }

   private void checkExcludedIP(Set var1, byte[] var2) throws PKIXNameConstraintValidatorException {
      if (!var1.isEmpty()) {
         Iterator var3 = var1.iterator();

         byte[] var4;
         do {
            if (!var3.hasNext()) {
               return;
            }

            var4 = (byte[])((byte[])var3.next());
         } while(!this.isIPConstrained(var2, var4));

         throw new PKIXNameConstraintValidatorException("IP is from an excluded subtree.");
      }
   }

   private boolean isIPConstrained(byte[] var1, byte[] var2) {
      int var3 = var1.length;
      if (var3 != var2.length / 2) {
         return false;
      } else {
         byte[] var4 = new byte[var3];
         System.arraycopy(var2, var3, var4, 0, var3);
         byte[] var5 = new byte[var3];
         byte[] var6 = new byte[var3];

         for(int var7 = 0; var7 < var3; ++var7) {
            var5[var7] = (byte)(var2[var7] & var4[var7]);
            var6[var7] = (byte)(var1[var7] & var4[var7]);
         }

         return Arrays.areEqual(var5, var6);
      }
   }

   private boolean emailIsConstrained(String var1, String var2) {
      String var3 = var1.substring(var1.indexOf(64) + 1);
      if (var2.indexOf(64) != -1) {
         if (var1.equalsIgnoreCase(var2)) {
            return true;
         }

         if (var3.equalsIgnoreCase(var2.substring(1))) {
            return true;
         }
      } else if (var2.charAt(0) != '.') {
         if (var3.equalsIgnoreCase(var2)) {
            return true;
         }
      } else if (this.withinDomain(var3, var2)) {
         return true;
      }

      return false;
   }

   private boolean withinDomain(String var1, String var2) {
      String var3 = var2;
      if (var2.startsWith(".")) {
         var3 = var2.substring(1);
      }

      String[] var4 = Strings.split(var3, '.');
      String[] var5 = Strings.split(var1, '.');
      if (var5.length <= var4.length) {
         return false;
      } else {
         int var6 = var5.length - var4.length;

         for(int var7 = -1; var7 < var4.length; ++var7) {
            if (var7 == -1) {
               if (var5[var7 + var6].equals("")) {
                  return false;
               }
            } else if (!var4[var7].equalsIgnoreCase(var5[var7 + var6])) {
               return false;
            }
         }

         return true;
      }
   }

   private void checkPermittedDNS(Set var1, String var2) throws PKIXNameConstraintValidatorException {
      if (var1 != null) {
         Iterator var3 = var1.iterator();

         String var4;
         do {
            if (!var3.hasNext()) {
               if (var2.length() == 0 && var1.size() == 0) {
                  return;
               }

               throw new PKIXNameConstraintValidatorException("DNS is not from a permitted subtree.");
            }

            var4 = (String)var3.next();
         } while(!this.withinDomain(var2, var4) && !var2.equalsIgnoreCase(var4));

      }
   }

   private void checkExcludedDNS(Set var1, String var2) throws PKIXNameConstraintValidatorException {
      if (!var1.isEmpty()) {
         Iterator var3 = var1.iterator();

         String var4;
         do {
            if (!var3.hasNext()) {
               return;
            }

            var4 = (String)var3.next();
         } while(!this.withinDomain(var2, var4) && !var2.equalsIgnoreCase(var4));

         throw new PKIXNameConstraintValidatorException("DNS is from an excluded subtree.");
      }
   }

   private void unionEmail(String var1, String var2, Set var3) {
      String var4;
      if (var1.indexOf(64) != -1) {
         var4 = var1.substring(var1.indexOf(64) + 1);
         if (var2.indexOf(64) != -1) {
            if (var1.equalsIgnoreCase(var2)) {
               var3.add(var1);
            } else {
               var3.add(var1);
               var3.add(var2);
            }
         } else if (var2.startsWith(".")) {
            if (this.withinDomain(var4, var2)) {
               var3.add(var2);
            } else {
               var3.add(var1);
               var3.add(var2);
            }
         } else if (var4.equalsIgnoreCase(var2)) {
            var3.add(var2);
         } else {
            var3.add(var1);
            var3.add(var2);
         }
      } else if (var1.startsWith(".")) {
         if (var2.indexOf(64) != -1) {
            var4 = var2.substring(var1.indexOf(64) + 1);
            if (this.withinDomain(var4, var1)) {
               var3.add(var1);
            } else {
               var3.add(var1);
               var3.add(var2);
            }
         } else if (var2.startsWith(".")) {
            if (!this.withinDomain(var1, var2) && !var1.equalsIgnoreCase(var2)) {
               if (this.withinDomain(var2, var1)) {
                  var3.add(var1);
               } else {
                  var3.add(var1);
                  var3.add(var2);
               }
            } else {
               var3.add(var2);
            }
         } else if (this.withinDomain(var2, var1)) {
            var3.add(var1);
         } else {
            var3.add(var1);
            var3.add(var2);
         }
      } else if (var2.indexOf(64) != -1) {
         var4 = var2.substring(var1.indexOf(64) + 1);
         if (var4.equalsIgnoreCase(var1)) {
            var3.add(var1);
         } else {
            var3.add(var1);
            var3.add(var2);
         }
      } else if (var2.startsWith(".")) {
         if (this.withinDomain(var1, var2)) {
            var3.add(var2);
         } else {
            var3.add(var1);
            var3.add(var2);
         }
      } else if (var1.equalsIgnoreCase(var2)) {
         var3.add(var1);
      } else {
         var3.add(var1);
         var3.add(var2);
      }

   }

   private void unionURI(String var1, String var2, Set var3) {
      String var4;
      if (var1.indexOf(64) != -1) {
         var4 = var1.substring(var1.indexOf(64) + 1);
         if (var2.indexOf(64) != -1) {
            if (var1.equalsIgnoreCase(var2)) {
               var3.add(var1);
            } else {
               var3.add(var1);
               var3.add(var2);
            }
         } else if (var2.startsWith(".")) {
            if (this.withinDomain(var4, var2)) {
               var3.add(var2);
            } else {
               var3.add(var1);
               var3.add(var2);
            }
         } else if (var4.equalsIgnoreCase(var2)) {
            var3.add(var2);
         } else {
            var3.add(var1);
            var3.add(var2);
         }
      } else if (var1.startsWith(".")) {
         if (var2.indexOf(64) != -1) {
            var4 = var2.substring(var1.indexOf(64) + 1);
            if (this.withinDomain(var4, var1)) {
               var3.add(var1);
            } else {
               var3.add(var1);
               var3.add(var2);
            }
         } else if (var2.startsWith(".")) {
            if (!this.withinDomain(var1, var2) && !var1.equalsIgnoreCase(var2)) {
               if (this.withinDomain(var2, var1)) {
                  var3.add(var1);
               } else {
                  var3.add(var1);
                  var3.add(var2);
               }
            } else {
               var3.add(var2);
            }
         } else if (this.withinDomain(var2, var1)) {
            var3.add(var1);
         } else {
            var3.add(var1);
            var3.add(var2);
         }
      } else if (var2.indexOf(64) != -1) {
         var4 = var2.substring(var1.indexOf(64) + 1);
         if (var4.equalsIgnoreCase(var1)) {
            var3.add(var1);
         } else {
            var3.add(var1);
            var3.add(var2);
         }
      } else if (var2.startsWith(".")) {
         if (this.withinDomain(var1, var2)) {
            var3.add(var2);
         } else {
            var3.add(var1);
            var3.add(var2);
         }
      } else if (var1.equalsIgnoreCase(var2)) {
         var3.add(var1);
      } else {
         var3.add(var1);
         var3.add(var2);
      }

   }

   private Set intersectDNS(Set var1, Set var2) {
      HashSet var3 = new HashSet();
      Iterator var4 = var2.iterator();

      while(true) {
         while(var4.hasNext()) {
            String var5 = this.extractNameAsString(((GeneralSubtree)var4.next()).getBase());
            if (var1 == null) {
               if (var5 != null) {
                  var3.add(var5);
               }
            } else {
               Iterator var6 = var1.iterator();

               while(var6.hasNext()) {
                  String var7 = (String)var6.next();
                  if (this.withinDomain(var7, var5)) {
                     var3.add(var7);
                  } else if (this.withinDomain(var5, var7)) {
                     var3.add(var5);
                  }
               }
            }
         }

         return var3;
      }
   }

   protected Set unionDNS(Set var1, String var2) {
      if (var1.isEmpty()) {
         if (var2 == null) {
            return var1;
         } else {
            var1.add(var2);
            return var1;
         }
      } else {
         HashSet var3 = new HashSet();
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            if (this.withinDomain(var5, var2)) {
               var3.add(var2);
            } else if (this.withinDomain(var2, var5)) {
               var3.add(var5);
            } else {
               var3.add(var5);
               var3.add(var2);
            }
         }

         return var3;
      }
   }

   private void intersectEmail(String var1, String var2, Set var3) {
      String var4;
      if (var1.indexOf(64) != -1) {
         var4 = var1.substring(var1.indexOf(64) + 1);
         if (var2.indexOf(64) != -1) {
            if (var1.equalsIgnoreCase(var2)) {
               var3.add(var1);
            }
         } else if (var2.startsWith(".")) {
            if (this.withinDomain(var4, var2)) {
               var3.add(var1);
            }
         } else if (var4.equalsIgnoreCase(var2)) {
            var3.add(var1);
         }
      } else if (var1.startsWith(".")) {
         if (var2.indexOf(64) != -1) {
            var4 = var2.substring(var1.indexOf(64) + 1);
            if (this.withinDomain(var4, var1)) {
               var3.add(var2);
            }
         } else if (var2.startsWith(".")) {
            if (!this.withinDomain(var1, var2) && !var1.equalsIgnoreCase(var2)) {
               if (this.withinDomain(var2, var1)) {
                  var3.add(var2);
               }
            } else {
               var3.add(var1);
            }
         } else if (this.withinDomain(var2, var1)) {
            var3.add(var2);
         }
      } else if (var2.indexOf(64) != -1) {
         var4 = var2.substring(var2.indexOf(64) + 1);
         if (var4.equalsIgnoreCase(var1)) {
            var3.add(var2);
         }
      } else if (var2.startsWith(".")) {
         if (this.withinDomain(var1, var2)) {
            var3.add(var1);
         }
      } else if (var1.equalsIgnoreCase(var2)) {
         var3.add(var1);
      }

   }

   private void checkExcludedURI(Set var1, String var2) throws PKIXNameConstraintValidatorException {
      if (!var1.isEmpty()) {
         Iterator var3 = var1.iterator();

         String var4;
         do {
            if (!var3.hasNext()) {
               return;
            }

            var4 = (String)var3.next();
         } while(!this.isUriConstrained(var2, var4));

         throw new PKIXNameConstraintValidatorException("URI is from an excluded subtree.");
      }
   }

   private Set intersectURI(Set var1, Set var2) {
      HashSet var3 = new HashSet();
      Iterator var4 = var2.iterator();

      while(true) {
         while(var4.hasNext()) {
            String var5 = this.extractNameAsString(((GeneralSubtree)var4.next()).getBase());
            if (var1 == null) {
               if (var5 != null) {
                  var3.add(var5);
               }
            } else {
               Iterator var6 = var1.iterator();

               while(var6.hasNext()) {
                  String var7 = (String)var6.next();
                  this.intersectURI(var7, var5, var3);
               }
            }
         }

         return var3;
      }
   }

   private Set unionURI(Set var1, String var2) {
      if (var1.isEmpty()) {
         if (var2 == null) {
            return var1;
         } else {
            var1.add(var2);
            return var1;
         }
      } else {
         HashSet var3 = new HashSet();
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            String var5 = (String)var4.next();
            this.unionURI(var5, var2, var3);
         }

         return var3;
      }
   }

   private void intersectURI(String var1, String var2, Set var3) {
      String var4;
      if (var1.indexOf(64) != -1) {
         var4 = var1.substring(var1.indexOf(64) + 1);
         if (var2.indexOf(64) != -1) {
            if (var1.equalsIgnoreCase(var2)) {
               var3.add(var1);
            }
         } else if (var2.startsWith(".")) {
            if (this.withinDomain(var4, var2)) {
               var3.add(var1);
            }
         } else if (var4.equalsIgnoreCase(var2)) {
            var3.add(var1);
         }
      } else if (var1.startsWith(".")) {
         if (var2.indexOf(64) != -1) {
            var4 = var2.substring(var1.indexOf(64) + 1);
            if (this.withinDomain(var4, var1)) {
               var3.add(var2);
            }
         } else if (var2.startsWith(".")) {
            if (!this.withinDomain(var1, var2) && !var1.equalsIgnoreCase(var2)) {
               if (this.withinDomain(var2, var1)) {
                  var3.add(var2);
               }
            } else {
               var3.add(var1);
            }
         } else if (this.withinDomain(var2, var1)) {
            var3.add(var2);
         }
      } else if (var2.indexOf(64) != -1) {
         var4 = var2.substring(var2.indexOf(64) + 1);
         if (var4.equalsIgnoreCase(var1)) {
            var3.add(var2);
         }
      } else if (var2.startsWith(".")) {
         if (this.withinDomain(var1, var2)) {
            var3.add(var1);
         }
      } else if (var1.equalsIgnoreCase(var2)) {
         var3.add(var1);
      }

   }

   private void checkPermittedURI(Set var1, String var2) throws PKIXNameConstraintValidatorException {
      if (var1 != null) {
         Iterator var3 = var1.iterator();

         String var4;
         do {
            if (!var3.hasNext()) {
               if (var2.length() == 0 && var1.size() == 0) {
                  return;
               }

               throw new PKIXNameConstraintValidatorException("URI is not from a permitted subtree.");
            }

            var4 = (String)var3.next();
         } while(!this.isUriConstrained(var2, var4));

      }
   }

   private boolean isUriConstrained(String var1, String var2) {
      String var3 = extractHostFromURL(var1);
      if (!var2.startsWith(".")) {
         if (var3.equalsIgnoreCase(var2)) {
            return true;
         }
      } else if (this.withinDomain(var3, var2)) {
         return true;
      }

      return false;
   }

   private static String extractHostFromURL(String var0) {
      String var1 = var0.substring(var0.indexOf(58) + 1);
      if (var1.indexOf("//") != -1) {
         var1 = var1.substring(var1.indexOf("//") + 2);
      }

      if (var1.lastIndexOf(58) != -1) {
         var1 = var1.substring(0, var1.lastIndexOf(58));
      }

      var1 = var1.substring(var1.indexOf(58) + 1);
      var1 = var1.substring(var1.indexOf(64) + 1);
      if (var1.indexOf(47) != -1) {
         var1 = var1.substring(0, var1.indexOf(47));
      }

      return var1;
   }

   public void checkPermitted(GeneralName var1) throws PKIXNameConstraintValidatorException {
      switch (var1.getTagNo()) {
         case 1:
            this.checkPermittedEmail(this.permittedSubtreesEmail, this.extractNameAsString(var1));
            break;
         case 2:
            this.checkPermittedDNS(this.permittedSubtreesDNS, DERIA5String.getInstance(var1.getName()).getString());
         case 3:
         case 5:
         default:
            break;
         case 4:
            this.checkPermittedDN(ASN1Sequence.getInstance(var1.getName().toASN1Primitive()));
            break;
         case 6:
            this.checkPermittedURI(this.permittedSubtreesURI, DERIA5String.getInstance(var1.getName()).getString());
            break;
         case 7:
            byte[] var2 = ASN1OctetString.getInstance(var1.getName()).getOctets();
            this.checkPermittedIP(this.permittedSubtreesIP, var2);
      }

   }

   public void checkExcluded(GeneralName var1) throws PKIXNameConstraintValidatorException {
      switch (var1.getTagNo()) {
         case 1:
            this.checkExcludedEmail(this.excludedSubtreesEmail, this.extractNameAsString(var1));
            break;
         case 2:
            this.checkExcludedDNS(this.excludedSubtreesDNS, DERIA5String.getInstance(var1.getName()).getString());
         case 3:
         case 5:
         default:
            break;
         case 4:
            this.checkExcludedDN(ASN1Sequence.getInstance(var1.getName().toASN1Primitive()));
            break;
         case 6:
            this.checkExcludedURI(this.excludedSubtreesURI, DERIA5String.getInstance(var1.getName()).getString());
            break;
         case 7:
            byte[] var2 = ASN1OctetString.getInstance(var1.getName()).getOctets();
            this.checkExcludedIP(this.excludedSubtreesIP, var2);
      }

   }

   public void intersectPermittedSubtree(GeneralSubtree var1) {
      this.intersectPermittedSubtree(new GeneralSubtree[]{var1});
   }

   public void intersectPermittedSubtree(GeneralSubtree[] var1) {
      HashMap var2 = new HashMap();

      for(int var3 = 0; var3 != var1.length; ++var3) {
         GeneralSubtree var4 = var1[var3];
         Integer var5 = Integers.valueOf(var4.getBase().getTagNo());
         if (var2.get(var5) == null) {
            var2.put(var5, new HashSet());
         }

         ((Set)var2.get(var5)).add(var4);
      }

      Iterator var6 = var2.entrySet().iterator();

      while(var6.hasNext()) {
         Map.Entry var7 = (Map.Entry)var6.next();
         switch ((Integer)var7.getKey()) {
            case 1:
               this.permittedSubtreesEmail = this.intersectEmail(this.permittedSubtreesEmail, (Set)var7.getValue());
               break;
            case 2:
               this.permittedSubtreesDNS = this.intersectDNS(this.permittedSubtreesDNS, (Set)var7.getValue());
            case 3:
            case 5:
            default:
               break;
            case 4:
               this.permittedSubtreesDN = this.intersectDN(this.permittedSubtreesDN, (Set)var7.getValue());
               break;
            case 6:
               this.permittedSubtreesURI = this.intersectURI(this.permittedSubtreesURI, (Set)var7.getValue());
               break;
            case 7:
               this.permittedSubtreesIP = this.intersectIP(this.permittedSubtreesIP, (Set)var7.getValue());
         }
      }

   }

   private String extractNameAsString(GeneralName var1) {
      return DERIA5String.getInstance(var1.getName()).getString();
   }

   public void intersectEmptyPermittedSubtree(int var1) {
      switch (var1) {
         case 1:
            this.permittedSubtreesEmail = new HashSet();
            break;
         case 2:
            this.permittedSubtreesDNS = new HashSet();
         case 3:
         case 5:
         default:
            break;
         case 4:
            this.permittedSubtreesDN = new HashSet();
            break;
         case 6:
            this.permittedSubtreesURI = new HashSet();
            break;
         case 7:
            this.permittedSubtreesIP = new HashSet();
      }

   }

   public void addExcludedSubtree(GeneralSubtree var1) {
      GeneralName var2 = var1.getBase();
      switch (var2.getTagNo()) {
         case 1:
            this.excludedSubtreesEmail = this.unionEmail(this.excludedSubtreesEmail, this.extractNameAsString(var2));
            break;
         case 2:
            this.excludedSubtreesDNS = this.unionDNS(this.excludedSubtreesDNS, this.extractNameAsString(var2));
         case 3:
         case 5:
         default:
            break;
         case 4:
            this.excludedSubtreesDN = this.unionDN(this.excludedSubtreesDN, (ASN1Sequence)var2.getName().toASN1Primitive());
            break;
         case 6:
            this.excludedSubtreesURI = this.unionURI(this.excludedSubtreesURI, this.extractNameAsString(var2));
            break;
         case 7:
            this.excludedSubtreesIP = this.unionIP(this.excludedSubtreesIP, ASN1OctetString.getInstance(var2.getName()).getOctets());
      }

   }

   private static byte[] max(byte[] var0, byte[] var1) {
      for(int var2 = 0; var2 < var0.length; ++var2) {
         if ((var0[var2] & '\uffff') > (var1[var2] & '\uffff')) {
            return var0;
         }
      }

      return var1;
   }

   private static byte[] min(byte[] var0, byte[] var1) {
      for(int var2 = 0; var2 < var0.length; ++var2) {
         if ((var0[var2] & '\uffff') < (var1[var2] & '\uffff')) {
            return var0;
         }
      }

      return var1;
   }

   private static int compareTo(byte[] var0, byte[] var1) {
      if (Arrays.areEqual(var0, var1)) {
         return 0;
      } else {
         return Arrays.areEqual(max(var0, var1), var0) ? 1 : -1;
      }
   }

   private static byte[] or(byte[] var0, byte[] var1) {
      byte[] var2 = new byte[var0.length];

      for(int var3 = 0; var3 < var0.length; ++var3) {
         var2[var3] = (byte)(var0[var3] | var1[var3]);
      }

      return var2;
   }

   public int hashCode() {
      return this.hashCollection(this.excludedSubtreesDN) + this.hashCollection(this.excludedSubtreesDNS) + this.hashCollection(this.excludedSubtreesEmail) + this.hashCollection(this.excludedSubtreesIP) + this.hashCollection(this.excludedSubtreesURI) + this.hashCollection(this.permittedSubtreesDN) + this.hashCollection(this.permittedSubtreesDNS) + this.hashCollection(this.permittedSubtreesEmail) + this.hashCollection(this.permittedSubtreesIP) + this.hashCollection(this.permittedSubtreesURI);
   }

   private int hashCollection(Collection var1) {
      if (var1 == null) {
         return 0;
      } else {
         int var2 = 0;
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            Object var4 = var3.next();
            if (var4 instanceof byte[]) {
               var2 += Arrays.hashCode((byte[])((byte[])var4));
            } else {
               var2 += var4.hashCode();
            }
         }

         return var2;
      }
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof PKIXNameConstraintValidator)) {
         return false;
      } else {
         PKIXNameConstraintValidator var2 = (PKIXNameConstraintValidator)var1;
         return this.collectionsAreEqual(var2.excludedSubtreesDN, this.excludedSubtreesDN) && this.collectionsAreEqual(var2.excludedSubtreesDNS, this.excludedSubtreesDNS) && this.collectionsAreEqual(var2.excludedSubtreesEmail, this.excludedSubtreesEmail) && this.collectionsAreEqual(var2.excludedSubtreesIP, this.excludedSubtreesIP) && this.collectionsAreEqual(var2.excludedSubtreesURI, this.excludedSubtreesURI) && this.collectionsAreEqual(var2.permittedSubtreesDN, this.permittedSubtreesDN) && this.collectionsAreEqual(var2.permittedSubtreesDNS, this.permittedSubtreesDNS) && this.collectionsAreEqual(var2.permittedSubtreesEmail, this.permittedSubtreesEmail) && this.collectionsAreEqual(var2.permittedSubtreesIP, this.permittedSubtreesIP) && this.collectionsAreEqual(var2.permittedSubtreesURI, this.permittedSubtreesURI);
      }
   }

   private boolean collectionsAreEqual(Collection var1, Collection var2) {
      if (var1 == var2) {
         return true;
      } else if (var1 != null && var2 != null) {
         if (var1.size() != var2.size()) {
            return false;
         } else {
            Iterator var3 = var1.iterator();

            boolean var6;
            do {
               if (!var3.hasNext()) {
                  return true;
               }

               Object var4 = var3.next();
               Iterator var5 = var2.iterator();
               var6 = false;

               while(var5.hasNext()) {
                  Object var7 = var5.next();
                  if (this.equals(var4, var7)) {
                     var6 = true;
                     break;
                  }
               }
            } while(var6);

            return false;
         }
      } else {
         return false;
      }
   }

   private boolean equals(Object var1, Object var2) {
      if (var1 == var2) {
         return true;
      } else if (var1 != null && var2 != null) {
         return var1 instanceof byte[] && var2 instanceof byte[] ? Arrays.areEqual((byte[])((byte[])var1), (byte[])((byte[])var2)) : var1.equals(var2);
      } else {
         return false;
      }
   }

   private String stringifyIP(byte[] var1) {
      String var2 = "";

      int var3;
      for(var3 = 0; var3 < var1.length / 2; ++var3) {
         var2 = var2 + Integer.toString(var1[var3] & 255) + ".";
      }

      var2 = var2.substring(0, var2.length() - 1);
      var2 = var2 + "/";

      for(var3 = var1.length / 2; var3 < var1.length; ++var3) {
         var2 = var2 + Integer.toString(var1[var3] & 255) + ".";
      }

      var2 = var2.substring(0, var2.length() - 1);
      return var2;
   }

   private String stringifyIPCollection(Set var1) {
      String var2 = "";
      var2 = var2 + "[";

      for(Iterator var3 = var1.iterator(); var3.hasNext(); var2 = var2 + this.stringifyIP((byte[])((byte[])var3.next())) + ",") {
      }

      if (var2.length() > 1) {
         var2 = var2.substring(0, var2.length() - 1);
      }

      var2 = var2 + "]";
      return var2;
   }

   public String toString() {
      String var1 = "";
      var1 = var1 + "permitted:\n";
      if (this.permittedSubtreesDN != null) {
         var1 = var1 + "DN:\n";
         var1 = var1 + this.permittedSubtreesDN.toString() + "\n";
      }

      if (this.permittedSubtreesDNS != null) {
         var1 = var1 + "DNS:\n";
         var1 = var1 + this.permittedSubtreesDNS.toString() + "\n";
      }

      if (this.permittedSubtreesEmail != null) {
         var1 = var1 + "Email:\n";
         var1 = var1 + this.permittedSubtreesEmail.toString() + "\n";
      }

      if (this.permittedSubtreesURI != null) {
         var1 = var1 + "URI:\n";
         var1 = var1 + this.permittedSubtreesURI.toString() + "\n";
      }

      if (this.permittedSubtreesIP != null) {
         var1 = var1 + "IP:\n";
         var1 = var1 + this.stringifyIPCollection(this.permittedSubtreesIP) + "\n";
      }

      var1 = var1 + "excluded:\n";
      if (!this.excludedSubtreesDN.isEmpty()) {
         var1 = var1 + "DN:\n";
         var1 = var1 + this.excludedSubtreesDN.toString() + "\n";
      }

      if (!this.excludedSubtreesDNS.isEmpty()) {
         var1 = var1 + "DNS:\n";
         var1 = var1 + this.excludedSubtreesDNS.toString() + "\n";
      }

      if (!this.excludedSubtreesEmail.isEmpty()) {
         var1 = var1 + "Email:\n";
         var1 = var1 + this.excludedSubtreesEmail.toString() + "\n";
      }

      if (!this.excludedSubtreesURI.isEmpty()) {
         var1 = var1 + "URI:\n";
         var1 = var1 + this.excludedSubtreesURI.toString() + "\n";
      }

      if (!this.excludedSubtreesIP.isEmpty()) {
         var1 = var1 + "IP:\n";
         var1 = var1 + this.stringifyIPCollection(this.excludedSubtreesIP) + "\n";
      }

      return var1;
   }
}
