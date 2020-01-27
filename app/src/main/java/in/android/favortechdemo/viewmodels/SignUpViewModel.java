package in.android.favortechdemo.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import in.android.favortechdemo.db.UserDBDAO;
import in.android.favortechdemo.entities.FeedResponse;
import in.android.favortechdemo.entities.UserEntity;

public class SignUpViewModel extends ViewModel {

    private String TAG = SignUpViewModel.class.getSimpleName();

    private MutableLiveData<FeedResponse> userProfileMutableLiveData;

    public MutableLiveData<FeedResponse> validateSignUp(UserDBDAO userDBDAO, String mobile, String email, String pwd) {
        if (userProfileMutableLiveData == null) {
            userProfileMutableLiveData = new MutableLiveData<>();
            FeedResponse feedResponse = new FeedResponse();
            if (userDBDAO != null) {
                UserEntity userEntity = new UserEntity();
                userEntity.email = email;
                userEntity.mobile = mobile;
                userEntity.password = pwd;
                userDBDAO.insert(userEntity);
                feedResponse.setError(false);
            } else {
                feedResponse.setError(true);
                feedResponse.setMessage("Something went wrong");
            }
            userProfileMutableLiveData.setValue(feedResponse);
        }
        return userProfileMutableLiveData;
    }


}
