package com.anil.airreportbe.service;

import com.anil.airreportbe.model.Airport;
import org.springframework.stereotype.Service;

@Service
public class DistanceCalculatorService {
    public double calculateDistance(Airport airport1, Airport airport2) {
        double lat1 = airport1.getLatitude();
        double lon1 = airport1.getLongitude();
        double lat2 = airport2.getLatitude();
        double lon2 = airport2.getLongitude();

//        // Implement the Haversine formula or another method to calculate distances
//        // Here's a simplified example using the Haversine formula:
//        double earthRadius = 6371; // Earth's radius in kilometers
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double distance = earthRadius * c;

        return calculateHaversineDistance(lat1, lon1, lat2, lon2);
    }

    public double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        // Radius of the Earth in kilometers
        double earthRadius = 6371;

        // Convert latitude and longitude from degrees to radians
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Haversine formula
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Calculate the distance
        double distance = earthRadius * c;

        return distance;
    }
}
