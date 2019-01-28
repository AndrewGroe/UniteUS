package io.github.andrewgroe.uniteus.representatives.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Office implements Parcelable {

    public final static Creator<Office> CREATOR = new Creator<Office>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Office createFromParcel(Parcel in) {
            return new Office(in);
        }

        public Office[] newArray(int size) {
            return (new Office[size]);
        }

    };
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("divisionId")
    @Expose
    private String divisionId;
    @SerializedName("levels")
    @Expose
    private List<String> levels = null;
    @SerializedName("roles")
    @Expose
    private List<String> roles = null;
    @SerializedName("officialIndices")
    @Expose
    private List<Integer> officialIndices = null;

    protected Office(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.divisionId = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.levels, (String.class.getClassLoader()));
        in.readList(this.roles, (String.class.getClassLoader()));
        in.readList(this.officialIndices, (Integer.class.getClassLoader()));
    }

    public Office() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(String divisionId) {
        this.divisionId = divisionId;
    }

    public List<String> getLevels() {
        return levels;
    }

    public void setLevels(List<String> levels) {
        this.levels = levels;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<Integer> getOfficialIndices() {
        return officialIndices;
    }

    public void setOfficialIndices(List<Integer> officialIndices) {
        this.officialIndices = officialIndices;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeValue(divisionId);
        dest.writeList(levels);
        dest.writeList(roles);
        dest.writeList(officialIndices);
    }

    public int describeContents() {
        return 0;
    }

}