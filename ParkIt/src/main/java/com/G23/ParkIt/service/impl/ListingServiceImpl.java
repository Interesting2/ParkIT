package com.G23.ParkIt.service.impl;

import com.G23.ParkIt.entity.Listing;
import com.G23.ParkIt.mapper.ListingMapper;
import com.G23.ParkIt.service.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListingServiceImpl implements ListingService {
    @Autowired
    private ListingMapper listingMapper;
    @Override
    public Listing getListingsById(Integer ListingId) {
        return listingMapper.getListingsById(ListingId);
    }
    @Override
    public List<Listing> getAllListings() {
        return listingMapper.getAllListings();
    }
    @Override
    public void insertListing(Listing Listing) {
        listingMapper.insertListing(Listing);
    }
    @Override
    public void updateListing(Listing Listing) {
        listingMapper.updateListing(Listing);
    }
    @Override
    public void deleteListing(Integer ListingId) {
        listingMapper.deleteListing(ListingId);
    }
    @Override
    public List<Listing> getListingsByUserId(Integer userId) {
        return listingMapper.getListingsByUserId(userId);
    }
    @Override
    public void updateListingImageUrl(Integer listingId, String imageUrl) {
        listingMapper.updateListingImageUrl(listingId, imageUrl);
    }
}
