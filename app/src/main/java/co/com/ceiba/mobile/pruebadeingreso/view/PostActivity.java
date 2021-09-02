package co.com.ceiba.mobile.pruebadeingreso.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.adapters.PostsAdapter;
import co.com.ceiba.mobile.pruebadeingreso.entities.User;
import co.com.ceiba.mobile.pruebadeingreso.interfaces.ApiJson;
import co.com.ceiba.mobile.pruebadeingreso.interfaces.RetrofitClient;
import co.com.ceiba.mobile.pruebadeingreso.utilities.ProgressDialogLoading;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class PostActivity extends AppCompatActivity {
    private PostsAdapter postsAdapter;
    private RecyclerView recyclerViewPosts;
    private CompositeDisposable compositeDisposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        User user = null;
        Intent i = getIntent();
        if (i.getExtras() != null)
            user = (User) i.getSerializableExtra("objectUser");

        TextView tvName = findViewById(R.id.name);
        TextView tvPhone = findViewById(R.id.phone);
        TextView tvEmail = findViewById(R.id.email);

        if (user != null) {
            tvName.setText(user.getName());
            tvPhone.setText(user.getPhone());
            tvEmail.setText(user.getEmail());
        }

        recyclerViewPosts = findViewById(R.id.recyclerViewPostsResults);
        postsAdapter = new PostsAdapter();
        compositeDisposable = new CompositeDisposable();

        recyclerViewPosts.setLayoutManager(new LinearLayoutManager(this));

        getPosts(user.getId());
    }

    private void getPosts(int userId) {
        Retrofit retrofit = RetrofitClient.getInstance();
        ApiJson jsonPlaceHolderApi = retrofit.create(ApiJson.class);

        ProgressDialogLoading mProgressDialog = new ProgressDialogLoading(this);

        compositeDisposable.add(jsonPlaceHolderApi.getPosts(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mProgressDialog.openDialog())
                .doOnTerminate(mProgressDialog::closeDialog)
                .subscribe(posts -> {
                    postsAdapter.setListPosts(posts);
                    recyclerViewPosts.setAdapter(postsAdapter);
                }));
    }
}
