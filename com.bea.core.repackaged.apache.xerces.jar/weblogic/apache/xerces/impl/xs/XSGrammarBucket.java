package weblogic.apache.xerces.impl.xs;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class XSGrammarBucket {
   Hashtable fGrammarRegistry = new Hashtable();
   SchemaGrammar fNoNSGrammar = null;

   public SchemaGrammar getGrammar(String var1) {
      return var1 == null ? this.fNoNSGrammar : (SchemaGrammar)this.fGrammarRegistry.get(var1);
   }

   public void putGrammar(SchemaGrammar var1) {
      if (var1.getTargetNamespace() == null) {
         this.fNoNSGrammar = var1;
      } else {
         this.fGrammarRegistry.put(var1.getTargetNamespace(), var1);
      }

   }

   public boolean putGrammar(SchemaGrammar var1, boolean var2) {
      SchemaGrammar var3 = this.getGrammar(var1.fTargetNamespace);
      if (var3 != null) {
         return var3 == var1;
      } else if (!var2) {
         this.putGrammar(var1);
         return true;
      } else {
         Vector var4 = var1.getImportedGrammars();
         if (var4 == null) {
            this.putGrammar(var1);
            return true;
         } else {
            Vector var5 = (Vector)var4.clone();

            int var10;
            for(int var9 = 0; var9 < var5.size(); ++var9) {
               SchemaGrammar var6 = (SchemaGrammar)var5.elementAt(var9);
               SchemaGrammar var7 = this.getGrammar(var6.fTargetNamespace);
               if (var7 == null) {
                  Vector var8 = var6.getImportedGrammars();
                  if (var8 != null) {
                     for(var10 = var8.size() - 1; var10 >= 0; --var10) {
                        var7 = (SchemaGrammar)var8.elementAt(var10);
                        if (!var5.contains(var7)) {
                           var5.addElement(var7);
                        }
                     }
                  }
               } else if (var7 != var6) {
                  return false;
               }
            }

            this.putGrammar(var1);

            for(var10 = var5.size() - 1; var10 >= 0; --var10) {
               this.putGrammar((SchemaGrammar)var5.elementAt(var10));
            }

            return true;
         }
      }
   }

   public boolean putGrammar(SchemaGrammar var1, boolean var2, boolean var3) {
      if (!var3) {
         return this.putGrammar(var1, var2);
      } else {
         SchemaGrammar var4 = this.getGrammar(var1.fTargetNamespace);
         if (var4 == null) {
            this.putGrammar(var1);
         }

         if (!var2) {
            return true;
         } else {
            Vector var5 = var1.getImportedGrammars();
            if (var5 == null) {
               return true;
            } else {
               Vector var6 = (Vector)var5.clone();

               int var11;
               for(int var10 = 0; var10 < var6.size(); ++var10) {
                  SchemaGrammar var7 = (SchemaGrammar)var6.elementAt(var10);
                  SchemaGrammar var8 = this.getGrammar(var7.fTargetNamespace);
                  if (var8 == null) {
                     Vector var9 = var7.getImportedGrammars();
                     if (var9 != null) {
                        for(var11 = var9.size() - 1; var11 >= 0; --var11) {
                           var8 = (SchemaGrammar)var9.elementAt(var11);
                           if (!var6.contains(var8)) {
                              var6.addElement(var8);
                           }
                        }
                     }
                  } else {
                     var6.remove(var7);
                  }
               }

               for(var11 = var6.size() - 1; var11 >= 0; --var11) {
                  this.putGrammar((SchemaGrammar)var6.elementAt(var11));
               }

               return true;
            }
         }
      }
   }

   public SchemaGrammar[] getGrammars() {
      int var1 = this.fGrammarRegistry.size() + (this.fNoNSGrammar == null ? 0 : 1);
      SchemaGrammar[] var2 = new SchemaGrammar[var1];
      Enumeration var3 = this.fGrammarRegistry.elements();

      for(int var4 = 0; var3.hasMoreElements(); var2[var4++] = (SchemaGrammar)var3.nextElement()) {
      }

      if (this.fNoNSGrammar != null) {
         var2[var1 - 1] = this.fNoNSGrammar;
      }

      return var2;
   }

   public void reset() {
      this.fNoNSGrammar = null;
      this.fGrammarRegistry.clear();
   }
}
