package weblogic.common.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.ParamSet;
import weblogic.common.T3Client;
import weblogic.common.T3Exception;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;

public class RemoteEntryPoint implements Externalizable {
   private static final long serialVersionUID = -6860805964340299268L;
   private transient T3Client t3;
   protected transient Object theObject;
   protected String className;
   protected ParamSet params;

   public String className() {
      return this.className;
   }

   public ParamSet params() {
      return this.params;
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      WLObjectInput sis = (WLObjectInput)in;
      this.className = sis.readAbbrevString();
      this.params = (ParamSet)sis.readObjectWL();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      WLObjectOutput sos = (WLObjectOutput)out;
      sos.writeAbbrevString(this.className);
      sos.writeObjectWL(this.params);
   }

   public void initialize() {
   }

   public void destroy() {
      this.t3 = null;
      this.theObject = null;
      this.className = null;
      this.params = null;
   }

   public RemoteEntryPoint() {
      this.initialize();
   }

   public RemoteEntryPoint(Object o, ParamSet params) {
      this.theObject = o;
      this.className = o.getClass().getName();
      this.params = params;
      this.t3 = null;
   }

   public RemoteEntryPoint(Object o) {
      this((Object)o, (ParamSet)null);
   }

   public RemoteEntryPoint(String className, ParamSet params) {
      this((T3Client)null, className, params);
   }

   public RemoteEntryPoint(String className) {
      this((T3Client)null, className, (ParamSet)null);
   }

   public RemoteEntryPoint(T3Client t3, String className, ParamSet params) {
      this.t3 = t3;
      this.className = className;
      this.params = params;
   }

   public T3Client getT3() {
      return this.t3;
   }

   public ParamSet getParamSet() {
      if (this.params == null) {
         this.params = new ParamSet(0);
      }

      return this.params;
   }

   public String getName() {
      return this.className;
   }

   void advertise() {
   }

   void retract() {
   }

   public Object newInstance() throws T3Exception {
      if (this.theObject == null) {
         try {
            this.theObject = Class.forName(this.getName(), true, Thread.currentThread().getContextClassLoader()).newInstance();
         } catch (NoSuchMethodError var2) {
            throw new T3Exception("Class " + this.getName() + " must implement a default constructor.", var2);
         } catch (ClassNotFoundException var3) {
            throw new T3Exception("class: " + this.getName(), var3);
         } catch (InstantiationException var4) {
            throw new T3Exception("class: " + this.getName(), var4);
         } catch (IllegalAccessException var5) {
            throw new T3Exception("class: " + this.getName(), var5);
         }
      }

      return this.theObject();
   }

   public Object theObject() {
      return this.theObject;
   }

   public boolean equals(Object that) {
      return that instanceof RemoteEntryPoint && this.getName().equals(((RemoteEntryPoint)that).getName());
   }

   public int hashCode() {
      return this.getName().hashCode();
   }
}
