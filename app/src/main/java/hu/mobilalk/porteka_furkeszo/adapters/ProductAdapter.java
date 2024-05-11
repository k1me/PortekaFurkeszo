package hu.mobilalk.porteka_furkeszo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hu.mobilalk.porteka_furkeszo.R;
import hu.mobilalk.porteka_furkeszo.fragments.ProductDetailFragment;
import hu.mobilalk.porteka_furkeszo.models.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    ArrayList<Product> products;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice());
        String url = product.getImage();
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.image);
        holder.productId.setText(product.getId());

        holder.itemView.setOnClickListener(view -> {
            Fragment fragment = ProductDetailFragment.newInstance(product);
            FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name, productId, price;
        ImageView image;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.itemImageView);
            name = itemView.findViewById(R.id.itemNameTextView);
            price = itemView.findViewById(R.id.itemPriceTV);
            productId = itemView.findViewById(R.id.productIdTextView);
        }
    }
}
