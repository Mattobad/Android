package com.example.sarbo.myapplication;

//purpose of this class is to provide each row of data in the form of object
public class DataProvider {

   // private int _id;
    private String taskname;
    private String location;
    private String note;
    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }



    public void setLatitude(double latitude) {

        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }



    public DataProvider(String taskname, String location, String note,double latitude,double longitude){
      // this._id = id;
        this.taskname = taskname;
        this.location = location;
        this.note = note;

        this.latitude = latitude;
        this.longitude = longitude;
    }
    public DataProvider(String taskname, String location, String note){
        // this._id = id;
        this.taskname = taskname;
        this.location = location;
        this.note = note;
       // this.latitude = latitude;
       // this.longitude = longitude;
       }

  /* public void set_id(int _id) {
        this._id = _id;
    }*/

    public void setTaskname(String taskname) {
        this.taskname = taskname;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNote(String note) {
        this.note = note;
    }

   /*public int get_id() {
        return _id;
    }*/

    public String getTaskname() {
        return taskname;
    }

    public String getLocation() {
        return location;
    }

    public String getNote() {
        return note;
    }
}
