package com.shopcuathuy.entity;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Analytics Event Entity
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@Entity
@Table(name = "analytics_events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AnalyticsEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "event_name", nullable = false)
    private String eventName;

    @NotNull
    @Size(max = 50)
    @Column(name = "event_type", nullable = false)
    private String eventType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Size(max = 1000)
    @Column(name = "event_data")
    private String eventData;

    @Size(max = 100)
    @Column(name = "session_id")
    private String sessionId;

    @Size(max = 100)
    @Column(name = "ip_address")
    private String ipAddress;

    @Size(max = 500)
    @Column(name = "user_agent")
    private String userAgent;

    @Size(max = 100)
    @Column(name = "referrer")
    private String referrer;

    @Column(name = "value")
    private Double value;

    @Column(name = "properties")
    private String properties;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Event type enum
     */
    public enum EventType {
        PAGE_VIEW,
        PRODUCT_VIEW,
        PRODUCT_CLICK,
        ADD_TO_CART,
        REMOVE_FROM_CART,
        ADD_TO_WISHLIST,
        REMOVE_FROM_WISHLIST,
        SEARCH,
        FILTER,
        SORT,
        CHECKOUT_START,
        CHECKOUT_COMPLETE,
        PURCHASE,
        REVIEW,
        RATING,
        LOGIN,
        LOGOUT,
        REGISTER,
        EMAIL_OPEN,
        EMAIL_CLICK,
        SOCIAL_SHARE,
        CUSTOM
    }
}
