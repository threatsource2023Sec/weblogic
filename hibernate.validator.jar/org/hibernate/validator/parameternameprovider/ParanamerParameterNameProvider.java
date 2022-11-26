package org.hibernate.validator.parameternameprovider;

import com.thoughtworks.paranamer.AdaptiveParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import com.thoughtworks.paranamer.Paranamer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import javax.validation.ParameterNameProvider;
import org.hibernate.validator.internal.engine.DefaultParameterNameProvider;

public class ParanamerParameterNameProvider implements ParameterNameProvider {
   private final ParameterNameProvider fallBackProvider;
   private final Paranamer paranamer;

   public ParanamerParameterNameProvider() {
      this((Paranamer)null);
   }

   public ParanamerParameterNameProvider(Paranamer paranamer) {
      this.paranamer = (Paranamer)(paranamer != null ? paranamer : new CachingParanamer(new AdaptiveParanamer()));
      this.fallBackProvider = new DefaultParameterNameProvider();
   }

   public List getParameterNames(Constructor constructor) {
      String[] parameterNames;
      synchronized(this.paranamer) {
         parameterNames = this.paranamer.lookupParameterNames(constructor, false);
      }

      return parameterNames != null && parameterNames.length == constructor.getParameterTypes().length ? Arrays.asList(parameterNames) : this.fallBackProvider.getParameterNames(constructor);
   }

   public List getParameterNames(Method method) {
      String[] parameterNames;
      synchronized(this.paranamer) {
         parameterNames = this.paranamer.lookupParameterNames(method, false);
      }

      return parameterNames != null && parameterNames.length == method.getParameterTypes().length ? Arrays.asList(parameterNames) : this.fallBackProvider.getParameterNames(method);
   }
}
