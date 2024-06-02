package com.G23.ParkIt.service;

import com.G23.ParkIt.entity.Listing;

import java.util.List;

public interface ListingService {
    Listing getListingsById(Integer listingId);
    List<Listing> getAllListings();
    void insertListing(Listing listing);
    void updateListing(Listing listing);
    void deleteListing(Integer listingId);
    List<Listing> getListingsByUserId(Integer userId);
    void updateListingImageUrl(Integer listingId, String imageUrl);
}
