package in.android.favortechdemo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.android.favortechdemo.MyApplication;
import in.android.favortechdemo.R;
import in.android.favortechdemo.db.UserDBDAO;
import in.android.favortechdemo.entities.FeedResponse;
import in.android.favortechdemo.viewmodels.SignUpViewModel;
import retrofit2.Retrofit;

/**
 * It handles the signup for a particular type of user.
 * <p>
 */
public class SignUpFragment extends Fragment {

    @Inject
    public Retrofit retrofit;
    @BindView(R.id.emailId)
    EditText emailId;
    @BindView(R.id.mobile)
    EditText mobile;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.progress)
    ProgressBar pbLoading;
    @Inject
    UserDBDAO userDBDAO;
    private SignUpViewModel signUpViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MyApplication) context.getApplicationContext()).getApiComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.signup, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);
    }

    @OnClick(R.id.create_profile)
    public void onButtonClick(View view) {
        String mobileNo = mobile.getText().toString();
        if (!isValidMobile(mobileNo)) {
            mobile.setError(getString(R.string.enter_valid_phone_number));
            return;
        }
        validateSignUp(mobileNo, emailId.getText().toString(), password.getText().toString());
    }

    /**
     * This handles the basic validation process in context of mobile number.
     */
    private void validateSignUp(String mobile, String email, String pwd) {

        pbLoading.setVisibility(View.VISIBLE);

        signUpViewModel.validateSignUp(userDBDAO, mobile, email, pwd).observe(this, new Observer<FeedResponse>() {
            @Override
            public void onChanged(@Nullable FeedResponse feedResponse) {
                pbLoading.setVisibility(View.GONE);
                if (feedResponse != null) {
                    if (feedResponse.getError()) {
                        Toast.makeText(getActivity(), feedResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        addFragment();
                    }
                } else {
                    // handle error
                }
            }
        });
    }

    private void addFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        MainFeedFragment myfragment = new MainFeedFragment();
        fragmentTransaction.replace(R.id.myFrame, myfragment);
        fragmentTransaction.commit();
    }

    private boolean isValidMobile(String phone) {
        boolean check;
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            if (phone.length() == 10)
                check = true;
            else
                check = false;
        } else {
            check = false;
        }
        return check;
    }
}
