package io.github.palharesricardo.clientejavaapi.enumeration;

import java.time.Duration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

/**
 * Enum that classifies the usage plans in the API.
 * 
 * @author Ricardo Palhares
 * @since 04/01/2021
 */
public enum APIUsagePlansEnum {
	
	FREE(20),
    BASIC(40),
    PREMIUM(100);

    private int bucketCapacity;
    
    private APIUsagePlansEnum (int bucketCapacity) {
        this.bucketCapacity = bucketCapacity;
    }
    
    /**
     * Method to get the Bandwidth, that is composed by the bucket capacity and the refill interval.
     * 
     * @author Ricardo Palhares
     * @since 11/06/2020
     * 
     * @return <code>Bandwidth</code> object
     */
    public Bandwidth getLimit() {
        return Bandwidth.classic(bucketCapacity, Refill.intervally(bucketCapacity, Duration.ofMinutes(20)));
    }
    
    /**
     * Method to get the bucket capacity.
     * 
     * @author Ricardo Palhares
     * @since 04/01/2021
     * 
     * @return int
     */
    public int getBucketCapacity() {
        return bucketCapacity;
    }
    
    /**
     * Method to get the right plan by looking the apiKey prefix.
     * 
     * @author Ricardo Palhares
     * @since 04/01/2021
     * 
     * @param apiKey
     * @return <code>APIUsagePlansEnum</code>
     */
    public static APIUsagePlansEnum resolvePlanFromApiKey(String apiKey) {
        
    	if (apiKey == null || apiKey.isEmpty()) {
            return FREE;
        
        } else if (apiKey.startsWith("PX001-")) {
            return PREMIUM;
            
        } else if (apiKey.startsWith("BX001-")) {
            return BASIC;
        }
        return FREE;
    }

}
