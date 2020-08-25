package com.example.lbsabyannaufal;

import android.os.Parcel;
import android.os.Parcelable;

public class Location implements Parcelable {

    private String Id;
    private String Latitude;
    private String Longitude;
    private String Place;
    private String Title;

    public Location(){
    }

    public void setId(String id){
        this.Id = id;
    }
    public String getId(){return Id;}

    public String getLatitude(){return Latitude;}
    public void setLatitude(String latitude){
        this.Latitude = latitude;
    }

    public String getLongitude(){return Longitude;}
    public void setLongitude(String longitude){
        this.Longitude = longitude;
    }

    public String getPlace(){return Place;}
    public void setPlace(String place){
        this.Place = place;
    }

    public String getTitle(){return Title;}
    public void setTitle(String title){
        this.Title = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Id);
        parcel.writeString(Latitude);
        parcel.writeString(Longitude);
        parcel.writeString(Place);
        parcel.writeString(Title);
    }

    protected Location(Parcel in) {
        Id = in.readString();
        Latitude = in.readString();
        Longitude = in.readString();
        Place = in.readString();
        Title = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
