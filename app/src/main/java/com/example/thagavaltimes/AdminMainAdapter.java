package com.example.thagavaltimes;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

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
    protected void onBindViewHolder(@NonNull myAdminViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull MainModel model) {
        holder.atitle.setText(model.getTitle());
        holder.acategory.setText(model.getCategory());
        holder.adescription.setText(model.getDescription());

        Glide.with(holder.aimg.getContext())
                .load(model.getPurl())
                .placeholder(R.drawable.noimagefound)
                .circleCrop()
                .error(R.drawable.noimagefound)
                .into(holder.aimg);


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.aimg.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,800)
                        .create();

                //dialogPlus.show();

                View view = dialogPlus.getHolderView();

                EditText title = view.findViewById(R.id.txtTitle);
                EditText category = view.findViewById(R.id.txtCategory);
                EditText description = view.findViewById(R.id.txtDescription);
                EditText purl = view.findViewById(R.id.txtPurl);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                title.setText(model.getTitle());
                category.setText(model.getCategory());
                description.setText(model.getDescription());
                purl.setText(model.getPurl());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("title",title.getText().toString());
                        map.put("category",category.getText().toString());
                        map.put("description",description.getText().toString());
                        map.put("purl",purl.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("posts")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.atitle.getContext(), "Data is updeted", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.atitle.getContext(), "Error while updeting", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.atitle.getContext());
                builder.setTitle("Are you sure you want to delete?");
                builder.setMessage("Deleted data can't be undo.");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("posts")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.atitle.getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

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

        Button btnEdit, btnDelete;

        public myAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            aimg = (CircleImageView)itemView.findViewById(R.id.img2);
            atitle = (TextView)itemView.findViewById(R.id.atitle);
            acategory = (TextView)itemView.findViewById(R.id.acategory);
            adescription = (TextView)itemView.findViewById(R.id.adescription);

            btnEdit = (Button)itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button)itemView.findViewById(R.id.btnDelete);

        }
    }
}
