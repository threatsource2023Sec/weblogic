package netscape.ldap.util;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Vector;

public class GetOpt implements Serializable {
   private int m_pos;
   private String optarg;
   private String m_control;
   private Vector m_option = new Vector();
   private Vector m_ParameterList;
   private Hashtable m_optionHashTable;
   private Hashtable m_optionParamHashTable;
   static final long serialVersionUID = -2570196909939660248L;

   public GetOpt(String var1, String[] var2) {
      this.m_control = var1;
      this.m_optionHashTable = new Hashtable();
      this.m_optionParamHashTable = new Hashtable();
      this.m_ParameterList = new Vector();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         String var4 = var2[var3];
         if (var4.length() > 0) {
            if (var4.charAt(0) != '-' && var4.charAt(0) != '/') {
               this.m_ParameterList.addElement(var2[var3]);
            } else if (var4.length() > 1) {
               int var5 = this.m_control.indexOf(var4.charAt(1));
               if (var5 == -1) {
                  System.err.println("Invalid usage. No option -" + var4.charAt(1));
               } else {
                  char[] var6 = new char[]{var4.charAt(1)};
                  String var7 = new String(var6);
                  this.m_optionHashTable.put(var7, "1");
                  if (this.m_control != null && this.m_control.length() > var5 + 1 && this.m_control.charAt(var5 + 1) == ':') {
                     ++var3;
                     if (var3 < var2.length) {
                        this.m_optionParamHashTable.put(var7, var2[var3]);
                     } else {
                        System.err.println("Missing argument for option " + var4);
                     }
                  }
               }
            } else {
               System.err.println("Invalid usage.");
            }
         }
      }

   }

   public boolean hasOption(char var1) {
      boolean var2 = false;
      char[] var3 = new char[]{var1};
      String var4 = new String(var3);
      if (this.m_optionHashTable.get(var4) == "1") {
         var2 = true;
      }

      return var2;
   }

   public String getOptionParam(char var1) {
      char[] var2 = new char[]{var1};
      String var3 = new String(var2);
      String var4 = (String)this.m_optionParamHashTable.get(var3);
      return var4;
   }

   public Vector getParameters() {
      return this.m_ParameterList;
   }
}
