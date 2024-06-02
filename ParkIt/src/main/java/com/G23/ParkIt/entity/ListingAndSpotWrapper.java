package com.G23.ParkIt.entity;

public class ListingAndSpotWrapper {
    private Listing listing;
    private ParkingSpot parkingSpot;

    public Listing getListing() { return listing; }
    public void setListing(Listing listing) { this.listing = listing; }

    public ParkingSpot getParkingSpot() { return parkingSpot; }
    public void setParkingSpot(ParkingSpot parkingSpot) { this.parkingSpot = parkingSpot; }
}
