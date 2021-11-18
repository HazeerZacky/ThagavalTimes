package com.example.thagavaltimes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

    public class AdminMainAdapter extends FirebaseRecyclerAdapter<MainModel, AdminMainAdapter.myAdminViewHolder>{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdminMainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull myAdminViewHolder holder, int position, @NonNull MainModel model) {
        holder.atitle.setText(model.getTitle());
        holder.acategory.setText(model.getCategory());
        holder.adescription.setText(model.getDescription());

        Glide.with(holder.aimg.getContext())
                .load(model.getPurl())
                .placeholder(R.drawable.noimagefound)
                .circleCrop()
                .error(R.drawable.noimagefound)
                .into(holder.aimg);

    }

    @NonNull
    @Override
    public myAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_main_item,parent,false);
        return new myAdminViewHolder(view);
    }

    class myAdminViewHolder extends RecyclerView.ViewHolder {

        CircleImageView aimg ;
        TextView atitle, acategory, adescription;

        public myAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            aimg = (CircleImageView)itemView.findViewById(R.id.img2);
            atitle = (TextView)itemView.findViewById(R.id.atitle);
            acategory = (TextView)itemView.findViewById(R.id.acategory);
            adescription = (TextView)itemView.findViewById(R.id.adescription);

        }
    }
}
