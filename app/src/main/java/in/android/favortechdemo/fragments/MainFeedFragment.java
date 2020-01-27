package in.android.favortechdemo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.android.favortechdemo.MyApplication;
import in.android.favortechdemo.R;
import in.android.favortechdemo.adapter.MainFeedAdapter;
import in.android.favortechdemo.db.ImageDBDAO;
import in.android.favortechdemo.entities.FeedResponse;
import in.android.favortechdemo.entities.ImageEntity;
import in.android.favortechdemo.viewmodels.MainFeedFragmentViewModel;
import retrofit2.Retrofit;


public class MainFeedFragment extends Fragment implements MainFeedAdapter.OnListFragmentInteractionListener {

    @Inject
    public Retrofit retrofit;
    @Inject
    public ImageDBDAO imageDBDAO;
    @BindView(R.id.list)
    RecyclerView rvMainFeed;
    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;
    @BindView(R.id.swipeToRefresh)
    SwipeRefreshLayout swipeToRefresh;
    private Unbinder unbinder;
    private MainFeedFragmentViewModel mViewModel;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MyApplication) context.getApplicationContext()).getApiComponent().inject(this);
    }

    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_feed, container, false);
        unbinder = ButterKnife.bind(this, view);
        rvMainFeed.setLayoutManager((new LinearLayoutManager(getContext())));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainFeedFragmentViewModel.class);
        loaderVisibility(View.VISIBLE);
        getFeedData();
        swipeToRefresh.setOnRefreshListener(this::getFeedData);
    }


    private void getFeedData() {
        mViewModel.getMainFeeds(retrofit, imageDBDAO).observe(this, new Observer<FeedResponse>() {
            @Override
            public void onChanged(@Nullable FeedResponse feedResponse) {
                if (feedResponse != null) {
                    if (feedResponse.getError()) {
                        loaderVisibility(View.GONE);
                    } else {
                        rvMainFeed.setAdapter((new MainFeedAdapter(getContext(), feedResponse.getTeacherList(), MainFeedFragment.this, mViewModel, retrofit)));
                        loaderVisibility(View.GONE);
                        swipeToRefresh.setRefreshing(false);
                    }
                }
            }
        });
    }

    @Override
    public void onListFragmentInteraction(@Nullable ImageEntity imageEntity) {
        Toast.makeText(getActivity(), imageEntity.url, Toast.LENGTH_SHORT).show();
    }


    private void loaderVisibility(int visibility) {
        pbLoading.setVisibility(visibility);
    }

    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}



