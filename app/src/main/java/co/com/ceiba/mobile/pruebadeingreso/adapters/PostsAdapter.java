package co.com.ceiba.mobile.pruebadeingreso.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.entities.Post;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostAdapterViewHolder> {
    private List<Post> listPosts;

    static class PostAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView title, body;

        public PostAdapterViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.body = itemView.findViewById(R.id.body);
        }
    }

    public void setListPosts(List<Post> listPosts) {
        this.listPosts = listPosts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewPost = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_item, parent, false);
        return new PostAdapterViewHolder(viewPost);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapterViewHolder holder, int position) {
        Post post = listPosts.get(position);

        holder.title.setText(post.getTitle());
        holder.body.setText(post.getBody());
    }

    @Override
    public int getItemCount() {
        return listPosts.size();
    }
}