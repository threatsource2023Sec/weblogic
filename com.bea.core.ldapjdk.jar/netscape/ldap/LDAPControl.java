package netscape.ldap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import netscape.ldap.ber.stream.BERBoolean;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;
import netscape.ldap.client.JDAPBERTagDecoder;
import netscape.ldap.controls.LDAPEntryChangeControl;
import netscape.ldap.controls.LDAPPasswordExpiredControl;
import netscape.ldap.controls.LDAPPasswordExpiringControl;
import netscape.ldap.controls.LDAPSortControl;
import netscape.ldap.controls.LDAPVirtualListResponse;
import netscape.ldap.util.LDIF;

public class LDAPControl implements Cloneable, Serializable {
   static final long serialVersionUID = 5149887553272603753L;
   public static final String MANAGEDSAIT = "2.16.840.1.113730.3.4.2";
   public static final String PWEXPIRED = "2.16.840.1.113730.3.4.4";
   public static final String PWEXPIRING = "2.16.840.1.113730.3.4.5";
   private String m_oid;
   protected boolean m_critical = false;
   protected byte[] m_value = null;
   private static Hashtable m_controlClassHash = null;

   public LDAPControl() {
   }

   public LDAPControl(String var1, boolean var2, byte[] var3) {
      this.m_oid = var1;
      this.m_critical = var2;
      this.m_value = var3;
   }

   public String getID() {
      return this.m_oid;
   }

   public boolean isCritical() {
      return this.m_critical;
   }

   public byte[] getValue() {
      return this.m_value;
   }

   BERElement getBERElement() {
      BERSequence var1 = new BERSequence();
      var1.addElement(new BEROctetString(this.m_oid));
      var1.addElement(new BERBoolean(this.m_critical));
      if (this.m_value != null && this.m_value.length >= 1) {
         var1.addElement(new BEROctetString(this.m_value, 0, this.m_value.length));
      } else {
         var1.addElement(new BEROctetString((byte[])null));
      }

      return var1;
   }

   public static void register(String var0, Class var1) throws LDAPException {
      if (var1 != null) {
         Class var2;
         for(var2 = var1; var2 != LDAPControl.class && var2 != null; var2 = var2.getSuperclass()) {
         }

         if (var2 == null) {
            throw new LDAPException("controlClass must be a subclass of LDAPControl", 89);
         } else {
            Class[] var3 = new Class[]{String.class, Boolean.TYPE, byte[].class};

            try {
               var1.getConstructor(var3);
            } catch (NoSuchMethodException var5) {
               throw new LDAPException("controlClass does not implement the correct contstructor", 89);
            }

            if (m_controlClassHash == null) {
               m_controlClassHash = new Hashtable();
            }

            m_controlClassHash.put(var0, var1);
         }
      }
   }

   protected static Class lookupControlClass(String var0) {
      return m_controlClassHash == null ? null : (Class)m_controlClassHash.get(var0);
   }

   protected static LDAPControl createControl(String var0, boolean var1, byte[] var2) {
      Class var3 = lookupControlClass(var0);
      if (var3 == null) {
         return new LDAPControl(var0, var1, var2);
      } else {
         Class[] var4 = new Class[]{String.class, Boolean.TYPE, byte[].class};
         Constructor var5 = null;

         try {
            var5 = var3.getConstructor(var4);
         } catch (NoSuchMethodException var10) {
            System.err.println("Caught java.lang.NoSuchMethodException while attempting to instantiate a control of type " + var0);
            return new LDAPControl(var0, var1, var2);
         }

         Object[] var6 = new Object[]{var0, new Boolean(var1), var2};
         LDAPControl var7 = null;

         try {
            var7 = (LDAPControl)var5.newInstance(var6);
         } catch (Exception var11) {
            String var9 = null;
            if (var11 instanceof InvocationTargetException) {
               var9 = ((InvocationTargetException)var11).getTargetException().toString();
            } else {
               var9 = var11.toString();
            }

            System.err.println("Caught " + var9 + " while attempting to" + " instantiate a control of type " + var0);
            var7 = new LDAPControl(var0, var1, var2);
         }

         return var7;
      }
   }

   static LDAPControl parseControl(BERElement var0) {
      BERSequence var1 = (BERSequence)var0;
      String var2 = null;
      boolean var3 = false;
      byte[] var4 = null;

      try {
         var2 = new String(((BEROctetString)var1.elementAt(0)).getValue(), "UTF8");
      } catch (Throwable var6) {
      }

      BERElement var5 = var1.elementAt(1);
      if (var5 instanceof BERBoolean) {
         var3 = ((BERBoolean)var5).getValue();
      } else {
         var4 = ((BEROctetString)var5).getValue();
      }

      if (var1.size() >= 3) {
         var4 = ((BEROctetString)var1.elementAt(2)).getValue();
      }

      return createControl(var2, var3, var4);
   }

   public static LDAPControl[] newInstance(byte[] var0) throws IOException {
      int[] var1 = new int[]{0};
      BERElement var2 = BERElement.getElement(new JDAPBERTagDecoder(), new ByteArrayInputStream(var0), var1);
      LDAPControl[] var3 = null;

      try {
         LDAPMessage var4 = LDAPMessage.parseMessage(var2);
         return var4.getControls();
      } catch (IOException var8) {
         BERTag var5 = (BERTag)var2;
         if (var5.getTag() == 160) {
            BERSequence var6 = (BERSequence)var5.getValue();
            var3 = new LDAPControl[var6.size()];

            for(int var7 = 0; var7 < var6.size(); ++var7) {
               var3[var7] = parseControl(var6.elementAt(var7));
            }
         }

         return var3;
      }
   }

   public Object clone() {
      byte[] var1 = null;
      if (this.m_value != null) {
         var1 = new byte[this.m_value.length];

         for(int var2 = 0; var2 < this.m_value.length; ++var2) {
            var1[var2] = this.m_value[var2];
         }
      }

      LDAPControl var3 = new LDAPControl(this.m_oid, this.m_critical, var1);
      return var3;
   }

   protected byte[] flattenBER(BERSequence var1) {
      ByteArrayOutputStream var2 = new ByteArrayOutputStream();

      try {
         var1.write(var2);
      } catch (IOException var4) {
         return null;
      }

      return var2.toByteArray();
   }

   public String toString() {
      if (this.getID().equals("2.16.840.1.113730.3.4.2")) {
         return "{MANAGEDSITControl: isCritical=" + this.isCritical() + "}";
      } else {
         String var1 = this.getID() + ' ' + this.isCritical();
         if (this.m_value != null) {
            var1 = var1 + ' ' + LDIF.toPrintableString(this.m_value);
         }

         return "LDAPControl {" + var1 + '}';
      }
   }

   static {
      try {
         register("2.16.840.1.113730.3.4.5", LDAPPasswordExpiringControl.class);
         register("2.16.840.1.113730.3.4.4", LDAPPasswordExpiredControl.class);
         register("2.16.840.1.113730.3.4.7", LDAPEntryChangeControl.class);
         register("1.2.840.113556.1.4.474", LDAPSortControl.class);
         register("2.16.840.1.113730.3.4.10", LDAPVirtualListResponse.class);
      } catch (LDAPException var1) {
      }

   }
}
