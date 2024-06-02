package com.G23.ParkIt.controller;

import com.G23.ParkIt.entity.Lessee;
import com.G23.ParkIt.entity.Listing;

import com.G23.ParkIt.entity.ParkingSpot;
import com.G23.ParkIt.service.*;
import com.G23.ParkIt.util.JwtUtil;
import com.G23.ParkIt.util.Result;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/listings")
public class ListingController {
    @Autowired
    private ListingService listingService;
    @Autowired
    private LesseeService lesseeService;
    @Autowired
    private ParkingSpotService parkingSpotService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/{listingId}")
    public Listing getListingById(@PathVariable Integer listingId) {
        return listingService.getListingsById(listingId);
    }

    @GetMapping("/getAllListings")
    public List<Listing> getAllListings() {
        List<Listing> listings = listingService.getAllListings(); 
        System.out.println("Print listings: " + listings.size()); 
        for(Listing l: listings)
        {
            System.out.println(l); 
        }
        return listings;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/insertListing")
    public Result insertListing(@RequestPart("listing") String listingStr,
                                @RequestPart("parkingSpot") String parkingSpotStr,
                                @RequestPart(name = "parkingSpotImage", required = false) MultipartFile parkingSpotImage,
                                @RequestHeader("Authorization") String authHeader) {
        ObjectMapper objectMapper = new ObjectMapper();
        Integer user_id = null;
        String imageName = null;
        String imageUrl = null;

        try {
            Listing listing = objectMapper.readValue(listingStr, Listing.class);
            ParkingSpot parkingSpot = objectMapper.readValue(parkingSpotStr, ParkingSpot.class);
            // System.out.println(listingStr); 
            // System.out.println(parkingSpotStr); 

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7); // Extract the token from "Bearer [token]".
                String username = jwtUtil.extractUsername(token);

                user_id = userService.getUserId(username).getUserId();
            }
            else {
                return Result.sysError("Authorization header not attached");
            }

            try {
                if (parkingSpotService.getParkingSpotByAddress(parkingSpot.getAddress()) != null) {
                    parkingSpotService.updateParkingSpot(parkingSpot);
                } else {
                    parkingSpotService.insertParkingSpot(parkingSpot);
                }
                System.out.println("GET ID ");
                System.out.println(parkingSpotService.getParkingSpotByAddress(parkingSpot.getAddress()).getSpotId());
                listing.setSpotId(parkingSpotService.getParkingSpotByAddress(parkingSpot.getAddress()).getSpotId());

                listingService.insertListing(listing);

                /* If an image is contained, store it into the database. */
                if(parkingSpotImage != null && !parkingSpotImage.isEmpty())
                {
                    // String imageName = parkingSpotImage.getOriginalFilename();
                    byte[] imageBytes = parkingSpotImage.getBytes();

                    imageName = String.format("listing_%d", listing.getListingId());
                    imageUrl = "/parkit/listings/images/" + imageName;
                    listing.setImageUrl(imageUrl);

                    // System.out.println(imageUrl);

                    jdbcTemplate.update("INSERT INTO listingImages (name, image_data) VALUES (?, ?)", imageName, imageBytes);

                    jdbcTemplate.update("UPDATE listings SET image_url=? WHERE listing_id=?", imageUrl, listing.getListingId());
                }
                // else {
                //     System.out.println("image is null. ");
                // }

                Lessee newLessee = new Lessee(null, listing.getListingId(), user_id);
                lesseeService.insertLessee(newLessee);
                return Result.success("Listing, Lessee and Parking Spot processed successfully.");
            } catch (Exception e) {
                return Result.sysError("Failed to process listing, lessee and parking spot.");
            }
        }
        catch(Exception e) {
            return Result.sysError(e.getMessage());
        }
    }

    @GetMapping(value = "/images/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getListingImage(  @PathVariable String imageName
                                                    ) {
        Integer user_id = null;

        // if (authHeader != null && authHeader.startsWith("Bearer ")) {
        //     String token = authHeader.substring(7); // Extract the token from "Bearer [token]".
        //     String username = jwtUtil.extractUsername(token);

        //     user_id = userService.getUserId(username).getUserId();
        // }
        // else {
        //     return new ResponseEntity<>(null, HttpStatus.OK);
        // }

        byte[] imageData = jdbcTemplate.queryForObject("SELECT image_data FROM listingImages WHERE name = ?", new Object[]{imageName}, byte[].class);
        return new ResponseEntity<>(imageData, HttpStatus.OK);
    }
    @PutMapping("/updateListing")
    public Result updateListing(@RequestBody Listing listing) {
        try {
            listingService.updateListing(listing);
            return Result.success("Listing updated successfully.");
        } catch (Exception e) {
            return Result.sysError("Failed to update listing.");
        }
    }

    @DeleteMapping("/deleteListing/{listingId}")
    public Result deleteListing(@PathVariable Integer listingId) {
        try {
            listingService.deleteListing(listingId);
            return Result.success("Listing deleted successfully.");
        } catch (Exception e) {
            return Result.sysError("Failed to delete listing.");
        }
    }

    @GetMapping("/getListingsByUser")
    public Result getListingssByUser(@RequestHeader("Authorization") String authHeader) {

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7); // Extract the token.
                String username = jwtUtil.extractUsername(token);
                if (username == null) {
                    return Result.sysError("Invalid token. Username extraction failed.");
                }
                
                Integer user_id = userService.getUserId(username).getUserId();
                System.out.println(user_id);
                if (user_id == null) {
                    return Result.sysError("User not found.");
                }
                
                List<Lessee> lessees = lesseeService.getAllLessees(); 
                List<Listing> listings = listingService.getAllListings(); 
                List<Listing> resultList = new ArrayList<>(); 
                for(Lessee lessee: lessees)
                {
                    if(lessee.getUserId() == user_id) {
                        for(Listing listing: listings) {
                            if(listing.getListingId() == lessee.getListingId())
                            {
                                // System.out.println("Listing added: " + listing.getListingId()); 
                                resultList.add(listing); 
                            }
                        }
                    }
                }

                // List<Listing> listings = listingService.getListingsByUserId(user_id);
                // // List<Listing> listings = listingService.getAllListings(); 
                // System.out.println("Print listings: " + listings.size()); 
                // System.out.println("Id: " + user_id); 
                // for(Listing l: listings)
                // {
                //     System.out.println(l); 
                // }
                return Result.success("Bookings fetched successfully.", resultList);

            } catch (Exception e) {
                return Result.sysError("An error occurred while fetching listings: " + e.getMessage());
            }
        } else {
            return Result.sysError("Authorization token missing or invalid.");
        }
    }
}
