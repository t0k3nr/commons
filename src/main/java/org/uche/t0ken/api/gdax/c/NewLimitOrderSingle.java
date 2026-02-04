package org.uche.t0ken.api.gdax.c;

import java.math.BigDecimal;

import org.uche.t0ken.api.gdax.OrderType;
import org.uche.t0ken.api.gdax.ProductId;
import org.uche.t0ken.api.gdax.Side;
import org.uche.t0ken.api.gdax.Stp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonInclude(Include.NON_NULL)
public class NewLimitOrderSingle extends NewOrderSingle {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2871150160643669251L;
	private BigDecimal size;
    private BigDecimal price;
    private Boolean post_only;

    public NewLimitOrderSingle() {}

    public NewLimitOrderSingle(BigDecimal size, BigDecimal price, Boolean post_only) {
        this.size = size;
        this.price = price;
        this.post_only = post_only;
    }

    public NewLimitOrderSingle(BigDecimal size, BigDecimal price, Boolean post_only,
                               String clientOid,
                               Side side,
                               ProductId product_id,
                               Stp stp) {
        this.size = size;
        this.price = price;
        this.post_only = post_only;
        setClient_oid(clientOid);
        setType(OrderType.LIMIT);
        setSide(side);
        setProduct_id(product_id);
        setStp(stp);
       
    }

    public Boolean getPost_only() {
        return post_only;
    }

    public void setPost_only(Boolean post_only) {
        this.post_only = post_only;
    }

    public BigDecimal getPrice() {
        return price.setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSize() {
        return size.setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }
}
