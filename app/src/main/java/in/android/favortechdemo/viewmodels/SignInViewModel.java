package in.android.favortechdemo.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import in.android.favortechdemo.db.UserDBDAO;
import in.android.favortechdemo.entities.FeedResponse;
import in.android.favortechdemo.entities.UserEntity;

public class SignInViewModel extends ViewModel {

    private MutableLiveData<FeedResponse> mutableLiveData;

    public MutableLiveData<FeedResponse> validateLoginActivity(UserDBDAO userDBDAO, String mobile, String pwd) {

        mutableLiveData = new MutableLiveData<>();
        UserEntity userEntity = userDBDAO.getUser(mobile);
        FeedResponse feedResponse = new FeedResponse();
        if (userEntity == null) {
            feedResponse.setError(true);
            feedResponse.setMessage("You are not a user. Please signup");
        } else {
            if (pwd.equals(userEntity.password)) {
                feedResponse.setError(false);
                feedResponse.setMessage("Successfully Sign in");
            } else {
                feedResponse.setError(true);
                feedResponse.setMessage("Credentials are not correct");
            }
        }
        mutableLiveData.setValue(feedResponse);
        return mutableLiveData;
    }


}
