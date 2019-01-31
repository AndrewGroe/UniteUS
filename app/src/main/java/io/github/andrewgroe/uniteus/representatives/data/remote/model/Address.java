package io.github.andrewgroe.uniteus.representatives.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable {

    public final static Creator<Address> CREATOR = new Creator<Address>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        public Address[] newArray(int size) {
            return (new Address[size]);
        }

    };
    @SerializedName("line1")
    @Expose
    private String line1;
    @SerializedName("line2")
    @Expose
    private String line2;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("line3")
    @Expose
    private String line3;

    protected Address(Parcel in) {
        this.line1 = ((String) in.readValue((String.class.getClassLoader())));
        this.line2 = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.state = ((String) in.readValue((String.class.getClassLoader())));
        this.zip = ((String) in.readValue((String.class.getClassLoader())));
        this.line3 = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Address() {
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(line1);
        dest.writeValue(line2);
        dest.writeValue(city);
        dest.writeValue(state);
        dest.writeValue(zip);
        dest.writeValue(line3);
    }

    @Override
    public String toString() {
        String finalString = line1 + '\n' +
                line2 + '\n' +
                city + ", " + state + '\n' +
                zip + '\'';
        finalString = finalString.replace("[", "").replace("]", "");
        return finalString;
    }

    public int describeContents() {
        return 0;
    }

}