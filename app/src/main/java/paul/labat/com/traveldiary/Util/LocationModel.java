package paul.labat.com.traveldiary.Util;

import android.location.Location;
import android.support.annotation.Nullable;

public class LocationModel {

    @Nullable
    public Location getLocation() {
        return location;
    }

    public void setLocation(@Nullable Location location) {
        this.location = location;
    }

    @Nullable
    private Location location;

    @Nullable
    private String street;
    @Nullable
    private String locality;

    @Nullable
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(@Nullable String countryName) {
        this.countryName = countryName;
    }

    @Nullable
    public String getLocality() {
        return locality;
    }

    public void setLocality(@Nullable String locality) {
        this.locality = locality;
    }

    @Nullable
    public String getStreet() {
        return street;
    }

    public void setStreet(@Nullable String street) {
        this.street = street;
    }

    @Nullable
    private String countryName;



}
