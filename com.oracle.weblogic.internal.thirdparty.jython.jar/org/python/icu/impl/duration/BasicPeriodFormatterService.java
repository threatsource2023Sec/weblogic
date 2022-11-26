package org.python.icu.impl.duration;

import java.util.Collection;
import org.python.icu.impl.duration.impl.PeriodFormatterDataService;
import org.python.icu.impl.duration.impl.ResourceBasedPeriodFormatterDataService;

public class BasicPeriodFormatterService implements PeriodFormatterService {
   private static BasicPeriodFormatterService instance;
   private PeriodFormatterDataService ds;

   public static BasicPeriodFormatterService getInstance() {
      if (instance == null) {
         PeriodFormatterDataService ds = ResourceBasedPeriodFormatterDataService.getInstance();
         instance = new BasicPeriodFormatterService(ds);
      }

      return instance;
   }

   public BasicPeriodFormatterService(PeriodFormatterDataService ds) {
      this.ds = ds;
   }

   public DurationFormatterFactory newDurationFormatterFactory() {
      return new BasicDurationFormatterFactory(this);
   }

   public PeriodFormatterFactory newPeriodFormatterFactory() {
      return new BasicPeriodFormatterFactory(this.ds);
   }

   public PeriodBuilderFactory newPeriodBuilderFactory() {
      return new BasicPeriodBuilderFactory(this.ds);
   }

   public Collection getAvailableLocaleNames() {
      return this.ds.getAvailableLocales();
   }
}
