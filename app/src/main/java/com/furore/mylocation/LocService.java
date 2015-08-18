package com.furore.mylocation;

import android.app.Service;
import android.content.Intent;
import android.graphics.Point;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.furore.intern.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vats on 22/04/15.
 */
public class LocService extends Service implements LocationListener, View.OnClickListener {

    protected LocationManager locationManager;
    Location location;

    LayoutInflater li;
    WindowManager wm;
    View myview;

    double latitude,longitude;
    String address;
    String date,time;

    ImageButton ib;
    Point p;

    private static final long MIN_DISTANCE_FOR_UPDATE = 10;
    private static final long MIN_TIME_FOR_UPDATE = 1000 * 60 * 2;

    @Override
    public void onCreate(){
        super.onCreate();


     /*   li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) getSystemService(WINDOW_SERVICE);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                //WindowManager.LayoutParams.TYPE_INPUT_METHOD |
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,// | WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.RIGHT | Gravity.TOP;

        myview = li.inflate(R.layout.serlayout, null);

        ib = (ImageButton) myview.findViewById(R.id.ib_serloc);
        ib.setOnClickListener(this);
        wm.addView(myview, params);*/

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        //onLocationChanged(location);
        if (location!=null){
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            String locLat = String.valueOf(latitude)+","+String.valueOf(longitude);
            Log.d("location",locLat);
        }

        getDateTime();
        address=getAddress();


        Log.d("address",address);

        //updating databse
        DBLocHistory db = new DBLocHistory(getApplicationContext());
        db.open();
        db.createEntry(latitude+"",longitude+"",address,date,time);
        db.close();

        return Service.START_STICKY;
    }

    void getDateTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        date=currentDateandTime.substring(0,currentDateandTime.indexOf(' '));
        time=currentDateandTime.substring(currentDateandTime.indexOf(' '),currentDateandTime.length());
        Log.d("date",date);
        Log.d("time",time);
        Log.d("date and time", currentDateandTime);
    }
    @Override
    public void onLocationChanged(Location location) {
        //TextView locationTv = (TextView) findViewById(R.id.latlongLocation);

        this.location = location;
//        latitude = location.getLatitude();
//        longitude = location.getLongitude();
//        LatLng latLng = new LatLng(latitude, longitude);
//        Log.d("Location","latitude:"+latitude+"\tlongitude :"+longitude);

        //locationTv.setText("Latitude:" + latitude + ", Longitude:" + longitude);
    }
    public void getLoc() {


        Location gpsLocation = getLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            latitude = gpsLocation.getLatitude();
             longitude = gpsLocation.getLongitude();
            String result = "Latitude: " + gpsLocation.getLatitude() +
                    " Longitude: " + gpsLocation.getLongitude();

            Log.d("Location",result);

        } else

        {
            Log.d("Loc err","location can't be retrieved");
        }

    }

/*


            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LocationAddress locationAddress = new LocationAddress();
                locationAddress.getAddressFromLocation(latitude, longitude,
                        getApplicationContext(), new GeocoderHandler());
            } else {
                showSettingsAlert();
            }
 */
    public  String getAddress(){

      /*  Geocoder gc = new Geocoder(this, Locale.getDefault());

        String addr;
        try {
            List<Address> addresses = gc.getFromLocation(latitude, longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\t");
                }
                addr = strReturnedAddress.toString();

                Log.d("Current location add", "" + strReturnedAddress.toString());
                return addr;
            } else {
                Log.d("Current location add", "No Address returned!");
                return null;
            }*/
        try{
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this, Locale.getDefault());

            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (addresses != null) {
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String result=address+"\n"+city+"\n"+state+"\n"+country;
                Log.d("Current location add", "" + result);
                return result;
            }
            else
            {
                Toast.makeText(this,address,Toast.LENGTH_SHORT).show();

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.d("Location","Address can't be retrieved");
            Toast.makeText(this,address,Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    public Location getLocation(String provider) {
        if (locationManager.isProviderEnabled(provider)) {
            locationManager.requestLocationUpdates(provider,
                    MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(provider);
                return location;
            }
        }
        return null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        int i=0;
        return null;
    }



    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ib_serloc)
        {
            //showPopup(new Activity(),p);
        }
    }


}


