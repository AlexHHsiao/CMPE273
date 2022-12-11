package com.example.cpme273.wlb.data.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class WlbPageRequest extends PageRequest {

  private int offset;

  public WlbPageRequest(int offset, int limit, Sort sort) {
    super(0, limit, sort);
    this.offset = offset;
  }

  /**
   * sets offset and limit for query
   *
   * @return
   */
  @Override
  public long getOffset() {
    return this.offset;
  }
}
