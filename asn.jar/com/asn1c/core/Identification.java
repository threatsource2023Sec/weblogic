package com.asn1c.core;

import java.io.PrintWriter;

public class Identification implements ASN1Object, Comparable {
   protected int selector;
   public static final int SYNTAXES_SELECTED = 0;
   public static final int SYNTAX_SELECTED = 1;
   public static final int PRESENTATIONCONTEXTID_SELECTED = 2;
   public static final int CONTEXTNEGOTIATION_SELECTED = 3;
   public static final int TRANSFERSYNTAX_SELECTED = 4;
   public static final int FIXED_SELECTED = 5;
   protected ObjectIdentifier abstractOI;
   protected ObjectIdentifier syntax;
   protected int contextId;

   public Identification(int var1, ObjectIdentifier var2, ObjectIdentifier var3) {
      if (var1 != 0) {
         throw new IllegalArgumentException();
      } else {
         this.selector = var1;
         this.abstractOI = var2;
         this.syntax = var3;
      }
   }

   public Identification(int var1, ObjectIdentifier var2) {
      if (var1 != 1 && var1 != 4) {
         throw new IllegalArgumentException();
      } else {
         this.selector = var1;
         this.syntax = var2;
      }
   }

   public Identification(int var1, int var2) {
      if (var1 != 2) {
         throw new IllegalArgumentException();
      } else {
         this.selector = var1;
         this.contextId = var2;
      }
   }

   public Identification(int var1, int var2, ObjectIdentifier var3) {
      if (var1 != 3) {
         throw new IllegalArgumentException();
      } else {
         this.selector = var1;
         this.contextId = var2;
         this.syntax = var3;
      }
   }

   public Identification(int var1) {
      if (var1 != 5) {
         throw new IllegalArgumentException();
      } else {
         this.selector = var1;
      }
   }

   public Identification(Identification var1) {
      this.selector = var1.selector;
      if (var1.abstractOI != null) {
         this.abstractOI = new ObjectIdentifier(var1.abstractOI);
      } else {
         this.abstractOI = null;
      }

      if (var1.syntax != null) {
         this.syntax = new ObjectIdentifier(var1.syntax);
      } else {
         this.syntax = null;
      }

      this.contextId = var1.contextId;
   }

   public Identification() {
      this.selector = -1;
   }

   void setValue(Identification var1) {
      this.selector = var1.selector;
      if (var1.abstractOI != null) {
         this.abstractOI = new ObjectIdentifier(var1.abstractOI);
      } else {
         this.abstractOI = null;
      }

      if (var1.syntax != null) {
         this.syntax = new ObjectIdentifier(var1.syntax);
      } else {
         this.syntax = null;
      }

      this.contextId = var1.contextId;
   }

   public void print(PrintWriter var1, String var2, String var3, String var4, int var5) {
      switch (this.selector) {
         case 0:
            var1.println(var2 + var3 + "syntaxes: {");
            this.abstractOI.print(var1, var2 + "    ", "abstract ", ",", var5);
            this.syntax.print(var1, var2 + "    ", "transfer ", "", var5);
            var1.println(var2 + "}" + var4);
            break;
         case 1:
            this.syntax.print(var1, var2, var3 + "syntax: ", var4, var5);
            break;
         case 2:
            var1.println(var2 + var3 + "presentation-context-id: " + this.contextId + var4);
            break;
         case 3:
            var1.println(var2 + var3 + "context-negotiation: {");
            var1.println(var2 + "    presentation-context-id: " + this.contextId + var4);
            this.syntax.print(var1, var2 + "    ", "transfer-syntax ", "", var5);
            var1.println(var2 + "}" + var4);
            break;
         case 4:
            this.syntax.print(var1, var2, var3 + "transfer-syntax: ", var4, var5);
            break;
         case 5:
            var1.println(var2 + var3 + "fixed: NULL" + var4);
            break;
         default:
            var1.println(var2 + var3 + "INVALID" + var4);
      }

   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      switch (this.selector) {
         case 0:
            var1.append("syntaxes: { abstract = ");
            var1.append(this.abstractOI);
            var1.append(", transfer = ");
            var1.append(this.syntax);
            var1.append(" }");
            break;
         case 1:
            var1.append("syntax: ");
            var1.append(this.syntax);
            break;
         case 2:
            var1.append("presentation-context-id: ");
            var1.append(this.contextId);
            break;
         case 3:
            var1.append("context-negotiation: { presentation-context-id = ");
            var1.append(this.contextId);
            var1.append(", transfer-syntax = ");
            var1.append(this.syntax);
            var1.append(" }");
            break;
         case 4:
            var1.append("transfer-syntax: ");
            var1.append(this.syntax);
            break;
         case 5:
            var1.append("fixed");
            break;
         default:
            var1.append("INVALID");
      }

      return var1.toString();
   }

   public String16 toString16() {
      return new String16(this.toString());
   }

   public String32 toString32() {
      return new String32(this.toString());
   }

   public int getSelector() {
      return this.selector;
   }

   protected void setSelector(int var1) {
      if (this.selector != var1) {
         this.selector = var1;
         this.abstractOI = null;
         this.syntax = null;
         this.contextId = 0;
      }
   }

   public ObjectIdentifier getSyntaxesAbstract() {
      if (this.selector != 0) {
         throw new IllegalStateException();
      } else {
         return this.abstractOI;
      }
   }

   public ObjectIdentifier getSyntaxesTransfer() {
      if (this.selector != 0) {
         throw new IllegalStateException();
      } else {
         return this.syntax;
      }
   }

   public void setSyntaxesAbstract(ObjectIdentifier var1) {
      this.setSelector(0);
      this.abstractOI = var1;
   }

   public void setSyntaxesTransfer(ObjectIdentifier var1) {
      this.setSelector(0);
      this.syntax = var1;
   }

   public void setSyntaxes(ObjectIdentifier var1, ObjectIdentifier var2) {
      this.setSelector(0);
      this.abstractOI = var1;
      this.syntax = var2;
   }

   public ObjectIdentifier getSyntax() {
      if (this.selector != 1) {
         throw new IllegalStateException();
      } else {
         return this.syntax;
      }
   }

   public void setSyntax(ObjectIdentifier var1) {
      this.setSelector(1);
      this.syntax = var1;
   }

   public int getPresentationContextId() {
      if (this.selector != 2) {
         throw new IllegalStateException();
      } else {
         return this.contextId;
      }
   }

   public void setPresentationContextId(int var1) {
      this.setSelector(2);
      this.contextId = var1;
   }

   public int getContextNegotiationPresentationContextId() {
      if (this.selector != 3) {
         throw new IllegalStateException();
      } else {
         return this.contextId;
      }
   }

   public ObjectIdentifier getContextNegotiationTransferSyntax() {
      if (this.selector != 3) {
         throw new IllegalStateException();
      } else {
         return this.syntax;
      }
   }

   public void setContextNegotiationPresentationContextId(int var1) {
      this.setSelector(3);
      this.contextId = var1;
   }

   public void setContextNegotiationTransferSyntax(ObjectIdentifier var1) {
      this.setSelector(3);
      this.syntax = var1;
   }

   public void setContextNegotiation(int var1, ObjectIdentifier var2) {
      this.setSelector(3);
      this.contextId = var1;
      this.syntax = var2;
   }

   public ObjectIdentifier getTransferSyntax() {
      if (this.selector != 4) {
         throw new IllegalStateException();
      } else {
         return this.syntax;
      }
   }

   public void setTransferSyntax(ObjectIdentifier var1) {
      this.setSelector(4);
      this.syntax = var1;
   }

   public void setFixed() {
      this.setSelector(5);
   }

   public int compareTo(Object var1) {
      Identification var2 = (Identification)var1;
      if (this.selector != var2.selector) {
         return this.selector - var2.selector;
      } else {
         switch (this.selector) {
            case 0:
               if (this.abstractOI.compareTo(var2.abstractOI) != 0) {
                  return this.abstractOI.compareTo(var2.abstractOI);
               }

               return this.syntax.compareTo(var2.syntax);
            case 1:
            case 4:
               return this.syntax.compareTo(var2.syntax);
            case 2:
               return this.contextId - var2.contextId;
            case 3:
               if (this.contextId != var2.contextId) {
                  return this.contextId - var2.contextId;
               }

               return this.syntax.compareTo(var2.syntax);
            case 5:
               return 0;
            default:
               return 0;
         }
      }
   }

   public boolean equals(Object var1) {
      return this.compareTo(var1) == 0;
   }
}
