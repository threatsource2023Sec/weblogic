package weblogic.corba.cos.naming.NamingContextAnyPackage;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.Streamable;

public final class AppExceptionHolder implements Streamable {
   public AppException value = null;

   public AppExceptionHolder() {
   }

   public AppExceptionHolder(AppException initialValue) {
      this.value = initialValue;
   }

   public void _read(InputStream i) {
      this.value = AppExceptionHelper.read(i);
   }

   public void _write(OutputStream o) {
      AppExceptionHelper.write(o, this.value);
   }

   public TypeCode _type() {
      return AppExceptionHelper.type();
   }
}
