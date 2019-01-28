package io.github.andrewgroe.uniteus.representatives.data.remote.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Official implements Parcelable {

    public final static Creator<Official> CREATOR = new Creator<Official>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Official createFromParcel(Parcel in) {
            return new Official(in);
        }

        public Official[] newArray(int size) {
            return (new Official[size]);
        }

    };
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private List<Address> address = null;
    @SerializedName("party")
    @Expose
    private String party;
    @SerializedName("phones")
    @Expose
    private List<String> phones = null;
    @SerializedName("urls")
    @Expose
    private List<String> urls = null;
    @SerializedName("photoUrl")
    @Expose
    private String photoUrl;
    @SerializedName("channels")
    @Expose
    private List<Channel> channels = null;
    @SerializedName("emails")
    @Expose
    private List<String> emails = null;

    protected Official(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.address, (io.github.andrewgroe.uniteus.representatives.data.remote.model.Address.class.getClassLoader()));
        this.party = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.phones, (String.class.getClassLoader()));
        in.readList(this.urls, (String.class.getClassLoader()));
        this.photoUrl = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.channels, (io.github.andrewgroe.uniteus.representatives.data.remote.model.Channel.class.getClassLoader()));
        in.readList(this.emails, (String.class.getClassLoader()));
    }

    public Official() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(name);
        dest.writeList(address);
        dest.writeValue(party);
        dest.writeList(phones);
        dest.writeList(urls);
        dest.writeValue(photoUrl);
        dest.writeList(channels);
        dest.writeList(emails);
    }

    public int describeContents() {
        return 0;
    }

}