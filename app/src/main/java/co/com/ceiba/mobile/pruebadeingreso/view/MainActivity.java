package co.com.ceiba.mobile.pruebadeingreso.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;
import java.util.Locale;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.adapters.UsersAdapter;
import co.com.ceiba.mobile.pruebadeingreso.database.AppDatabase;
import co.com.ceiba.mobile.pruebadeingreso.database.UserDao;
import co.com.ceiba.mobile.pruebadeingreso.entities.User;
import co.com.ceiba.mobile.pruebadeingreso.interfaces.ApiJson;
import co.com.ceiba.mobile.pruebadeingreso.interfaces.RetrofitClient;
import co.com.ceiba.mobile.pruebadeingreso.utilities.ProgressDialogLoading;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private UsersAdapter usersAdapter;
    private RecyclerView recyclerViewUsers;
    private AppDatabase db;
    private TextInputEditText edtSearchUsers;
    private RelativeLayout emptyContainer;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDbInstance(getApplicationContext());

        recyclerViewUsers = findViewById(R.id.recyclerViewSearchResults);
        edtSearchUsers = findViewById(R.id.editTextSearch);
        emptyContainer = findViewById(R.id.empty_container);

        usersAdapter = new UsersAdapter(getApplicationContext());
        compositeDisposable = new CompositeDisposable();
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        usersAdapter.setCallback(isListEmpty -> {
            if (isListEmpty)
                emptyContainer.setVisibility(View.VISIBLE);
            else
                emptyContainer.setVisibility(View.GONE);
        });

        listenerSearchUsers();
        loadUsersList();
    }

    private void loadUsersList() {
        List<User> userList = db.userDao().getAllUsers();
        if (userList.isEmpty()) {
            getAllUsers();
        } else {
            usersAdapter.setListUsers(userList);
            recyclerViewUsers.setAdapter(usersAdapter);
        }
    }

    private void getAllUsers() {
        Retrofit retrofit = RetrofitClient.getInstance();
        ApiJson jsonPlaceHolderApi = retrofit.create(ApiJson.class);

        ProgressDialogLoading mProgressDialog = new ProgressDialogLoading(this);

        compositeDisposable.add(jsonPlaceHolderApi.getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> mProgressDialog.openDialog())
                .doOnTerminate(mProgressDialog::closeDialog)
                .subscribe(users -> {
                    usersAdapter.setListUsers(users);
                    recyclerViewUsers.setAdapter(usersAdapter);
                    //Insert All Users In the Database
                    db.userDao().insertAllUsers(users);
                }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void listenerSearchUsers() {
        edtSearchUsers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = edtSearchUsers.getText().toString().toLowerCase(Locale.getDefault());
                usersAdapter.getFilter().filter(text);
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });
    }
}