package com.angusyg.winecellar.model.core.web.arguments;

import lombok.Data;

/**
 * Limit argument for repository search built
 * from request parameter {@code limit}
 *
 * @since 0.0.1
 */
@Data
public class Limiteable {
  // Limit value
  private int limit;

  // Flag to indicate if search is limited
  private boolean isLimited;

  /**
   * Creates a non limited {@link Limiteable}
   */
  public Limiteable() {
    this.isLimited = false;
  }

  /**
   * Creates a {@link Limiteable} from a limit
   *
   * @param limit limit value
   */
  public Limiteable(int limit) {
    this.limit = limit;
    this.isLimited = true;
  }
}
