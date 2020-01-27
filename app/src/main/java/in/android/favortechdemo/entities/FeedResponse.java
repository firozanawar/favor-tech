package in.android.favortechdemo.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FeedResponse {

    @SerializedName("data")
    private List<ImageEntity> imageList;
    @SerializedName("error")
    private Boolean mError;
    @SerializedName("message")
    private String mMessage = "";
    @SerializedName("status")
    private String mStatus;

    public List<ImageEntity> getTeacherList() {
        return imageList;
    }

    public void setTeacherList(List<ImageEntity> mTeacherList) {
        this.imageList = mTeacherList;
    }

    public Boolean getError() {
        return mError;
    }

    public void setError(Boolean mError) {
        this.mError = mError;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }
}
