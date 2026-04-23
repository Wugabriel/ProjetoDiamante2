package com.diamante.restaurant.repository;

import com.diamante.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r WHERE r.active = true " +
           "AND (:category IS NULL OR r.category = :category) " +
           "AND (:location IS NULL OR r.location = :location) " +
           "AND (:minRating IS NULL OR r.rating >= :minRating)")
    List<Restaurant> findWithFilters(
            @Param("category") String category,
            @Param("location") String location,
            @Param("minRating") Double minRating
    );
}
