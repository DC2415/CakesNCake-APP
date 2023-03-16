package com.example.cakesncafe.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rohit on 19-03-2018.
 */

public class FireUser implements Parcelable {

    String uid;
    String displayName;
    String email;
    boolean emailVerified;
    boolean mobileVerified;
    Double storeCredits;
    String photoURL;
    String mobile;
    String providerId;
    boolean admin;
    int otp;

    public FireUser() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isMobileVerified() {
        return mobileVerified;
    }

    public void setMobileVerified(boolean mobileVerified) {
        this.mobileVerified = mobileVerified;
    }

    public Double getStoreCredits() {
        return storeCredits;
    }

    public void setStoreCredits(Double storeCredits) {
        this.storeCredits = storeCredits;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }

    protected FireUser(Parcel in) {
        uid = in.readString();
        displayName = in.readString();
        email = in.readString();
        emailVerified = in.readByte() != 0;
        mobileVerified = in.readByte() != 0;
        if (in.readByte() == 0) {
            storeCredits = null;
        } else {
            storeCredits = in.readDouble();
        }
        photoURL = in.readString();
        mobile = in.readString();
        providerId = in.readString();
        admin = in.readByte() != 0;
        otp = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(displayName);
        dest.writeString(email);
        dest.writeByte((byte) (emailVerified ? 1 : 0));
        dest.writeByte((byte) (mobileVerified ? 1 : 0));
        if (storeCredits == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(storeCredits);
        }
        dest.writeString(photoURL);
        dest.writeString(mobile);
        dest.writeString(providerId);
        dest.writeByte((byte) (admin ? 1 : 0));
        dest.writeInt(otp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FireUser> CREATOR = new Creator<FireUser>() {
        @Override
        public FireUser createFromParcel(Parcel in) {
            return new FireUser(in);
        }

        @Override
        public FireUser[] newArray(int size) {
            return new FireUser[size];
        }
    };
}
