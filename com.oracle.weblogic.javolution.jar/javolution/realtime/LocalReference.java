package javolution.realtime;

import java.io.Serializable;
import javolution.lang.Reference;
import javolution.util.FastMap;

public class LocalReference implements Reference, Serializable {
   private Object _defaultValue;
   private boolean _hasBeenLocallyOverriden;

   public LocalReference() {
      this((Object)null);
   }

   public LocalReference(Object var1) {
      this._defaultValue = var1;
   }

   public final Object get() {
      return this._hasBeenLocallyOverriden ? this.retrieveValue() : this._defaultValue;
   }

   private Object retrieveValue() {
      for(LocalContext var1 = Context.current().inheritedLocalContext; var1 != null; var1 = var1.getOuter().inheritedLocalContext) {
         Object var2 = var1._references.get(this);
         if (var2 != null) {
            return var2;
         }
      }

      return this._defaultValue;
   }

   public void set(Object var1) {
      LocalContext var2 = Context.current().inheritedLocalContext;
      if (var2 != null) {
         FastMap var3 = var2._references;
         var3.put(this, var1);
         this._hasBeenLocallyOverriden = true;
      } else {
         this._defaultValue = var1;
      }
   }

   public Object getDefault() {
      return this._defaultValue;
   }

   public Object getLocal() {
      LocalContext var1 = Context.current().inheritedLocalContext;
      return var1 != null ? var1._references.get(this) : this._defaultValue;
   }

   public void setDefault(Object var1) {
      this._defaultValue = var1;
   }

   public String toString() {
      return String.valueOf(this.get());
   }
}
