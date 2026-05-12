package br.com.fusion.banck.domain.entity.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class FusionApiProdutos {

    private UUID id;
    private String name;
    private String description;
    private String brand;
    private String category;
    private BigDecimal price;
    private boolean isPhysical;
    private boolean available;
    private BigDecimal quantity;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
