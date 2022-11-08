package com.example.whatsapp.tablayout.profile;

public class profileDataHolder {

    String uid,name,profilePic;

    public profileDataHolder(String name, String profilePic,String uid) {
        this.name = name;
        this.profilePic = profilePic;
        this.uid=uid;
    }

    public profileDataHolder() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
