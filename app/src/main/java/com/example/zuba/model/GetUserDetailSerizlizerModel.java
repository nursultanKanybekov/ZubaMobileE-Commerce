package com.example.zuba.model;

import java.util.List;

public class GetUserDetailSerizlizerModel {
    private String phone;
    private GetUserProfileInfoModel profile;
    private List<ObligationSerialiszerModel> obligations;

    public GetUserDetailSerizlizerModel(String phone, GetUserProfileInfoModel profile, List<ObligationSerialiszerModel> obligations) {
        this.phone = phone;
        this.profile = profile;
        this.obligations = obligations;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public GetUserProfileInfoModel getProfile() {
        return profile;
    }

    public void setProfile(GetUserProfileInfoModel profile) {
        this.profile = profile;
    }

    public List<ObligationSerialiszerModel> getObligations() {
        return obligations;
    }

    public void setObligations(List<ObligationSerialiszerModel> obligations) {
        this.obligations = obligations;
    }
}
