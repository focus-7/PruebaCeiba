package co.com.ceiba.mobile.pruebadeingreso.adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.entities.User;
import co.com.ceiba.mobile.pruebadeingreso.view.PostActivity;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserAdapterViewHolder> implements Filterable {
    private List<User> listUsers;
    private List<User> listUsersFull;
    private Callback callback;
    private final Context context;

    static class UserAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView name, phone, email;
        Button btnPost;

        public UserAdapterViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.phone = itemView.findViewById(R.id.phone);
            this.email = itemView.findViewById(R.id.email);
            this.btnPost = itemView.findViewById(R.id.btn_view_post);
        }
    }

    public UsersAdapter(Context context) {
        this.context = context;
    }

    public void setListUsers(List<User> listUsers) {
        this.listUsers = listUsers;
        listUsersFull = new ArrayList<>(listUsers);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewUser = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        return new UserAdapterViewHolder(viewUser);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapterViewHolder holder, int position) {
        User user = listUsers.get(position);

        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
        holder.phone.setText(user.getPhone());

        holder.btnPost.setOnClickListener(view -> {
            Intent intent = new Intent(context, PostActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            Bundle bundle = new Bundle();
            bundle.putSerializable("objectUser", (Serializable) user);
            intent.putExtras(bundle);

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<User> filteredUserList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredUserList.addAll(listUsersFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (User user : listUsersFull) {
                    if (user.getName().toLowerCase().contains(filterPattern)) {
                        filteredUserList.add(user);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredUserList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listUsers.clear();
            listUsers.addAll((List) filterResults.values);
            callback.onActionFilter(listUsers.isEmpty());
            notifyDataSetChanged();
        }
    };

    public interface Callback {
        void onActionFilter(boolean isListEmpty);
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

}
