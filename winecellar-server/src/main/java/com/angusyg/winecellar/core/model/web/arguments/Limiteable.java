package com.angusyg.winecellar.core.model.web.arguments;

import lombok.Data;

@Data
public class Limiteable {
  private int limit;
  private boolean isLimited;

  public Limiteable() {
    this.isLimited = false;
  }

  public Limiteable(int limit) {
    this.limit = limit;
    this.isLimited = true;
  }
}
