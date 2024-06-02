package com.G23.ParkIt.mapper;

import com.G23.ParkIt.entity.Listing;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ListingMapper {
    Listing getListingsById(Integer listing_id);
    List<Listing> getAllListings();
    int insertListing(Listing listing);
    int updateListing(Listing listing);
    int deleteListing(Integer listing_id);
    List<Listing> getListingsByUserId(Integer userId);
    void updateListingImageUrl(Integer listingId, String imageUrl);
}
