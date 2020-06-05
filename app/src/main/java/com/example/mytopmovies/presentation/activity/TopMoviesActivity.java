package com.example.mytopmovies.presentation.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.mytopmovies.R;
import com.example.mytopmovies.data.BaseModel;
import com.example.mytopmovies.databinding.ActivityTopMoviesBinding;
import com.example.mytopmovies.presentation.activity.common.DividerItemDecoration;
import com.example.mytopmovies.presentation.activity.common.ListAdapter;
import com.example.mytopmovies.presentation.base.BaseActivity;
import com.example.mytopmovies.presentation.base.BasePresenter;
import com.example.mytopmovies.presentation.route.IRoute;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatDialogFragment;
import dagger.android.support.DaggerFragment;

public class TopMoviesActivity extends BaseActivity<ActivityTopMoviesBinding> implements TopMoviesContract.View {

    @Inject
    TopMoviesContract.Presenter presenter;

    @Inject
    IRoute route;

    private ListAdapter listAdapter;
    private List<BaseModel> resultList = new ArrayList<>();

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_top_movies;
    }

    @Override
    protected void createActivity(@Nullable Bundle savedInstanceState) {
        route.onStart(this);
        listAdapter = new ListAdapter(resultList);
        getBinding().recyclerView.setAdapter(listAdapter);
        getBinding().recyclerView.addItemDecoration(new DividerItemDecoration(this));

        getBinding().recyclerView.setItemAnimator(new DefaultItemAnimator());
        getBinding().recyclerView.setHasFixedSize(true);
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void stopActivity() {
        super.onStop();
        presenter.rxUnsubscribe();
        resultList.clear();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void startActivity() {
        getBinding().setEvent(presenter);
        presenter.setView(this);
        presenter.loadData();

    }

    @Override
    protected void pauseActivity() {

    }

    @Override
    protected void resumeActivity() {

    }

    @Override
    protected void destroyActivity() {
        if (presenter != null) presenter = null;
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }


    @Override
    public <T> void transactionActivity(Class<?> activity, T object) {
        super.onTransactionActivity(activity, true, object);
    }

    @Override
    public void transactionActivity(Class<?> activity) {
        super.onTransactionActivity(activity, true);
    }

//    @Override
//    public void transitionFragment(DaggerFragment fragment, int container) {
//        super.onTransactionFragmentWithBackStack(fragment,container);
//    }

    @Override
    public void transitionFragmentDialog(DaggerAppCompatDialogFragment fragment, int container) {
        //TODO NOT USED
    }

    @Override
    public void titleAppBar(String val) {
        //TODO NOT USED
    }

    @Override
    public void back() {
        super.onBackPressed();
    }

    @Override
    public void restart() {
        super.restartAppBase();
    }

    @Override
    public void updateData(BaseModel baseModel) {
        resultList.add(baseModel);
        listAdapter.notifyItemInserted(resultList.size() - 1);
    }

    @Override
    public void showSnackbar(String s) {
        Snackbar.make(getBinding().listActivityRootView, s, Snackbar.LENGTH_SHORT).show();
    }
}