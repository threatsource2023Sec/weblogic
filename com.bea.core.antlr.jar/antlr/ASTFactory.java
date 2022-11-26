package antlr;

import antlr.collections.AST;
import antlr.collections.impl.ASTArray;
import java.lang.reflect.Constructor;
import java.util.Hashtable;

public class ASTFactory {
   protected String theASTNodeType = null;
   protected Class theASTNodeTypeClass = null;
   protected Hashtable tokenTypeToASTClassMap = null;
   // $FF: synthetic field
   static Class class$antlr$CommonAST;
   // $FF: synthetic field
   static Class class$antlr$Token;

   public ASTFactory() {
   }

   public ASTFactory(Hashtable var1) {
      this.setTokenTypeToASTClassMap(var1);
   }

   public void setTokenTypeASTNodeType(int var1, String var2) throws IllegalArgumentException {
      if (this.tokenTypeToASTClassMap == null) {
         this.tokenTypeToASTClassMap = new Hashtable();
      }

      if (var2 == null) {
         this.tokenTypeToASTClassMap.remove(new Integer(var1));
      } else {
         Class var3 = null;

         try {
            var3 = Utils.loadClass(var2);
            this.tokenTypeToASTClassMap.put(new Integer(var1), var3);
         } catch (Exception var5) {
            throw new IllegalArgumentException("Invalid class, " + var2);
         }
      }
   }

   public Class getASTNodeType(int var1) {
      if (this.tokenTypeToASTClassMap != null) {
         Class var2 = (Class)this.tokenTypeToASTClassMap.get(new Integer(var1));
         if (var2 != null) {
            return var2;
         }
      }

      if (this.theASTNodeTypeClass != null) {
         return this.theASTNodeTypeClass;
      } else {
         return class$antlr$CommonAST == null ? (class$antlr$CommonAST = class$("antlr.CommonAST")) : class$antlr$CommonAST;
      }
   }

   public void addASTChild(ASTPair var1, AST var2) {
      if (var2 != null) {
         if (var1.root == null) {
            var1.root = var2;
         } else if (var1.child == null) {
            var1.root.setFirstChild(var2);
         } else {
            var1.child.setNextSibling(var2);
         }

         var1.child = var2;
         var1.advanceChildToEnd();
      }

   }

   public AST create() {
      return this.create(0);
   }

   public AST create(int var1) {
      Class var2 = this.getASTNodeType(var1);
      AST var3 = this.create(var2);
      if (var3 != null) {
         var3.initialize(var1, "");
      }

      return var3;
   }

   public AST create(int var1, String var2) {
      AST var3 = this.create(var1);
      if (var3 != null) {
         var3.initialize(var1, var2);
      }

      return var3;
   }

   public AST create(int var1, String var2, String var3) {
      AST var4 = this.create(var3);
      if (var4 != null) {
         var4.initialize(var1, var2);
      }

      return var4;
   }

   public AST create(AST var1) {
      if (var1 == null) {
         return null;
      } else {
         AST var2 = this.create(var1.getType());
         if (var2 != null) {
            var2.initialize(var1);
         }

         return var2;
      }
   }

   public AST create(Token var1) {
      AST var2 = this.create(var1.getType());
      if (var2 != null) {
         var2.initialize(var1);
      }

      return var2;
   }

   public AST create(Token var1, String var2) {
      AST var3 = this.createUsingCtor(var1, var2);
      return var3;
   }

   public AST create(String var1) {
      Class var2 = null;

      try {
         var2 = Utils.loadClass(var1);
      } catch (Exception var4) {
         throw new IllegalArgumentException("Invalid class, " + var1);
      }

      return this.create(var2);
   }

   protected AST createUsingCtor(Token var1, String var2) {
      Class var3 = null;
      AST var4 = null;

      try {
         var3 = Utils.loadClass(var2);
         Class[] var5 = new Class[]{class$antlr$Token == null ? (class$antlr$Token = class$("antlr.Token")) : class$antlr$Token};

         try {
            Constructor var6 = var3.getConstructor(var5);
            var4 = (AST)var6.newInstance(var1);
         } catch (NoSuchMethodException var7) {
            var4 = this.create(var3);
            if (var4 != null) {
               var4.initialize(var1);
            }
         }

         return var4;
      } catch (Exception var8) {
         throw new IllegalArgumentException("Invalid class or can't make instance, " + var2);
      }
   }

   protected AST create(Class var1) {
      AST var2 = null;

      try {
         var2 = (AST)var1.newInstance();
         return var2;
      } catch (Exception var4) {
         this.error("Can't create AST Node " + var1.getName());
         return null;
      }
   }

   public AST dup(AST var1) {
      if (var1 == null) {
         return null;
      } else {
         AST var2 = this.create(var1.getClass());
         var2.initialize(var1);
         return var2;
      }
   }

   public AST dupList(AST var1) {
      AST var2 = this.dupTree(var1);

      for(AST var3 = var2; var1 != null; var3 = var3.getNextSibling()) {
         var1 = var1.getNextSibling();
         var3.setNextSibling(this.dupTree(var1));
      }

      return var2;
   }

   public AST dupTree(AST var1) {
      AST var2 = this.dup(var1);
      if (var1 != null) {
         var2.setFirstChild(this.dupList(var1.getFirstChild()));
      }

      return var2;
   }

   public AST make(AST[] var1) {
      if (var1 != null && var1.length != 0) {
         AST var2 = var1[0];
         AST var3 = null;
         if (var2 != null) {
            var2.setFirstChild((AST)null);
         }

         for(int var4 = 1; var4 < var1.length; ++var4) {
            if (var1[var4] != null) {
               if (var2 == null) {
                  var2 = var3 = var1[var4];
               } else if (var3 == null) {
                  var2.setFirstChild(var1[var4]);
                  var3 = var2.getFirstChild();
               } else {
                  var3.setNextSibling(var1[var4]);
                  var3 = var3.getNextSibling();
               }

               while(var3.getNextSibling() != null) {
                  var3 = var3.getNextSibling();
               }
            }
         }

         return var2;
      } else {
         return null;
      }
   }

   public AST make(ASTArray var1) {
      return this.make(var1.array);
   }

   public void makeASTRoot(ASTPair var1, AST var2) {
      if (var2 != null) {
         var2.addChild(var1.root);
         var1.child = var1.root;
         var1.advanceChildToEnd();
         var1.root = var2;
      }

   }

   public void setASTNodeClass(Class var1) {
      if (var1 != null) {
         this.theASTNodeTypeClass = var1;
         this.theASTNodeType = var1.getName();
      }

   }

   public void setASTNodeClass(String var1) {
      this.theASTNodeType = var1;

      try {
         this.theASTNodeTypeClass = Utils.loadClass(var1);
      } catch (Exception var3) {
         this.error("Can't find/access AST Node type" + var1);
      }

   }

   /** @deprecated */
   public void setASTNodeType(String var1) {
      this.setASTNodeClass(var1);
   }

   public Hashtable getTokenTypeToASTClassMap() {
      return this.tokenTypeToASTClassMap;
   }

   public void setTokenTypeToASTClassMap(Hashtable var1) {
      this.tokenTypeToASTClassMap = var1;
   }

   public void error(String var1) {
      System.err.println(var1);
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }
}
