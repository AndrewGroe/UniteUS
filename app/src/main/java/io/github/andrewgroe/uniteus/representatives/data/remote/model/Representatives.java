package io.github.andrewgroe.uniteus.representatives.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Representatives implements Parcelable {

    public final static Creator<Representatives> CREATOR = new Creator<Representatives>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Representatives createFromParcel(Parcel in) {
            return new Representatives(in);
        }

        public Representatives[] newArray(int size) {
            return (new Representatives[size]);
        }

    };
    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("offices")
    @Expose
    private List<Office> offices = null;
    @SerializedName("officials")
    @Expose
    private List<Official> officials = null;

    protected Representatives(Parcel in) {
        this.kind = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.offices, (io.github.andrewgroe.uniteus.representatives.data.remote.model.Office.class.getClassLoader()));
        in.readList(this.officials, (io.github.andrewgroe.uniteus.representatives.data.remote.model.Official.class.getClassLoader()));
    }

    public Representatives() {
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }


    public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    public List<Official> getOfficials() {
        return officials;
    }

    public void setOfficials(List<Official> officials) {
        this.officials = officials;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(kind);
        dest.writeList(offices);
        dest.writeList(officials);
    }

    public int describeContents() {
        return 0;
    }

}