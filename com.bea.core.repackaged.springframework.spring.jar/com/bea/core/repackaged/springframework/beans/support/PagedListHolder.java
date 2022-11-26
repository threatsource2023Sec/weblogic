package com.bea.core.repackaged.springframework.beans.support;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PagedListHolder implements Serializable {
   public static final int DEFAULT_PAGE_SIZE = 10;
   public static final int DEFAULT_MAX_LINKED_PAGES = 10;
   private List source;
   @Nullable
   private Date refreshDate;
   @Nullable
   private SortDefinition sort;
   @Nullable
   private SortDefinition sortUsed;
   private int pageSize;
   private int page;
   private boolean newPageSet;
   private int maxLinkedPages;

   public PagedListHolder() {
      this(new ArrayList(0));
   }

   public PagedListHolder(List source) {
      this(source, new MutableSortDefinition(true));
   }

   public PagedListHolder(List source, SortDefinition sort) {
      this.source = Collections.emptyList();
      this.pageSize = 10;
      this.page = 0;
      this.maxLinkedPages = 10;
      this.setSource(source);
      this.setSort(sort);
   }

   public void setSource(List source) {
      Assert.notNull(source, (String)"Source List must not be null");
      this.source = source;
      this.refreshDate = new Date();
      this.sortUsed = null;
   }

   public List getSource() {
      return this.source;
   }

   @Nullable
   public Date getRefreshDate() {
      return this.refreshDate;
   }

   public void setSort(@Nullable SortDefinition sort) {
      this.sort = sort;
   }

   @Nullable
   public SortDefinition getSort() {
      return this.sort;
   }

   public void setPageSize(int pageSize) {
      if (pageSize != this.pageSize) {
         this.pageSize = pageSize;
         if (!this.newPageSet) {
            this.page = 0;
         }
      }

   }

   public int getPageSize() {
      return this.pageSize;
   }

   public void setPage(int page) {
      this.page = page;
      this.newPageSet = true;
   }

   public int getPage() {
      this.newPageSet = false;
      if (this.page >= this.getPageCount()) {
         this.page = this.getPageCount() - 1;
      }

      return this.page;
   }

   public void setMaxLinkedPages(int maxLinkedPages) {
      this.maxLinkedPages = maxLinkedPages;
   }

   public int getMaxLinkedPages() {
      return this.maxLinkedPages;
   }

   public int getPageCount() {
      float nrOfPages = (float)this.getNrOfElements() / (float)this.getPageSize();
      return (int)(!(nrOfPages > (float)((int)nrOfPages)) && (double)nrOfPages != 0.0 ? nrOfPages : nrOfPages + 1.0F);
   }

   public boolean isFirstPage() {
      return this.getPage() == 0;
   }

   public boolean isLastPage() {
      return this.getPage() == this.getPageCount() - 1;
   }

   public void previousPage() {
      if (!this.isFirstPage()) {
         --this.page;
      }

   }

   public void nextPage() {
      if (!this.isLastPage()) {
         ++this.page;
      }

   }

   public int getNrOfElements() {
      return this.getSource().size();
   }

   public int getFirstElementOnPage() {
      return this.getPageSize() * this.getPage();
   }

   public int getLastElementOnPage() {
      int endIndex = this.getPageSize() * (this.getPage() + 1);
      int size = this.getNrOfElements();
      return (endIndex > size ? size : endIndex) - 1;
   }

   public List getPageList() {
      return this.getSource().subList(this.getFirstElementOnPage(), this.getLastElementOnPage() + 1);
   }

   public int getFirstLinkedPage() {
      return Math.max(0, this.getPage() - this.getMaxLinkedPages() / 2);
   }

   public int getLastLinkedPage() {
      return Math.min(this.getFirstLinkedPage() + this.getMaxLinkedPages() - 1, this.getPageCount() - 1);
   }

   public void resort() {
      SortDefinition sort = this.getSort();
      if (sort != null && !sort.equals(this.sortUsed)) {
         this.sortUsed = this.copySortDefinition(sort);
         this.doSort(this.getSource(), sort);
         this.setPage(0);
      }

   }

   protected SortDefinition copySortDefinition(SortDefinition sort) {
      return new MutableSortDefinition(sort);
   }

   protected void doSort(List source, SortDefinition sort) {
      PropertyComparator.sort(source, sort);
   }
}
