package com.shoppingCart.service.cart.cleanup;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingCart.repository.SoftDeleteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SoftDeleteCleanupScheduler {

    private final SoftDeleteRepository softDeleteRepository;

    @Value("${app.cleanup.retention-days:15}")
    private long retentionDays;

    /**
     * Runs daily at 02:00 server time.
     * Records stay recoverable for 15 days before permanent deletion.
     */
    @Scheduled(cron = "${app.cleanup.cron:0 0 2 * * *}")
    @Transactional
    public void permanentlyDeleteExpiredRecords() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(retentionDays);

        int cartItems = softDeleteRepository.purgeCartItems(cutoff);
        int carts = softDeleteRepository.purgeCarts(cutoff);
        int orderItems = softDeleteRepository.purgeOrderItems(cutoff);
        int orders = softDeleteRepository.purgeOrders(cutoff);
        int images = softDeleteRepository.purgeImages(cutoff);
        int products = softDeleteRepository.purgeProducts(cutoff);
        int categories = softDeleteRepository.purgeCategories(cutoff);
        int users = softDeleteRepository.purgeUsers(cutoff);

        log.info("Permanent cleanup completed. cutoff={}, cartItems={}, carts={}, orderItems={}, " +
                        "orders={}, images={}, products={}, categories={}, users={}",
                cutoff, cartItems, carts, orderItems, orders, images, products, categories, users);
    }
}
