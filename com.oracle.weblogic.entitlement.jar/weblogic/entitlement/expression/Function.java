package weblogic.entitlement.expression;

public abstract class Function extends EExprRep {
   protected EExprRep[] mArgs;

   public Function(EExprRep arg) {
      if (arg == null) {
         throw new IllegalArgumentException("Null function argument");
      } else {
         this.mArgs = new EExprRep[1];
         this.mArgs[0] = arg;
      }
   }

   public Function(EExprRep arg1, EExprRep arg2) {
      if (arg1 != null && arg2 != null) {
         this.mArgs = new EExprRep[2];
         this.mArgs[0] = arg1;
         this.mArgs[1] = arg2;
      } else {
         throw new IllegalArgumentException("Null " + (arg1 == null ? "first" : "second") + " function argument");
      }
   }

   public Function(EExprRep[] args) {
      for(int i = 0; i < args.length; ++i) {
         if (args[i] == null) {
            throw new IllegalArgumentException("Null function argument " + i);
         }
      }

      this.mArgs = new EExprRep[args.length];
      System.arraycopy(args, 0, this.mArgs, 0, args.length);
   }

   protected int getDependsOnInternal() {
      int result = 0;

      for(int i = 0; i < this.mArgs.length; ++i) {
         result |= this.mArgs[i].getDependsOn();
      }

      return result;
   }

   protected void setArg(int i, EExprRep arg) {
      if (arg == null) {
         throw new IllegalArgumentException("EExprRep argument is null");
      } else {
         this.mArgs[i] = arg;
      }
   }

   void outForPersist(StringBuffer buf) {
      this.writeTypeId(buf);
      buf.append((char)this.mArgs.length);

      for(int i = 0; i < this.mArgs.length; ++i) {
         this.mArgs[i].outForPersist(buf);
      }

   }

   protected void writeExternalForm(StringBuffer buf) {
      if (this.Enclosed) {
         buf.append('{');
      }

      if (this.mArgs.length > 0) {
         for(int i = 0; i < this.mArgs.length; ++i) {
            if (i > 0) {
               buf.append(this.getTypeId());
            }

            this.mArgs[i].writeExternalForm(buf);
         }
      }

      if (this.Enclosed) {
         buf.append('}');
      }

   }
}
