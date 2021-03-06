package de.rieckpil.websockets;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Stock implements Serializable {

  private static final long serialVersionUID = 6841814625186249817L;

  private String code;
  private double price;
  private long timestamp = System.currentTimeMillis();
  
  public Stock(String code, double price) {
    this.code = code;
    this.price = price;
  }
  
}
