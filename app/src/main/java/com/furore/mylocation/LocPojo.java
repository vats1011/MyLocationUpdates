package com.furore.mylocation;

/**
 * Created by diksha on 22/4/15.
 */
public class LocPojo {
    String latitude,longitude,address,date,time;

    public LocPojo(String latitude,String longitude,String address,String date,String time)
    {
        this.latitude=latitude;
        this.longitude=longitude;
        this.address=address;
     //   this.locName=locName;
        this.date=date;
        this.time=time;

    }
}
