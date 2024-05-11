package hu.mobilalk.porteka_furkeszo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import hu.mobilalk.porteka_furkeszo.R;
import hu.mobilalk.porteka_furkeszo.models.Product;

public class ProductDetailFragment extends Fragment {

    String url;

    public ProductDetailFragment() {
    }

    public static ProductDetailFragment newInstance(Product product) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("product", product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product, container, false);

        TextView productNameTV = rootView.findViewById(R.id.productNameTV);
        TextView priceTv = rootView.findViewById(R.id.itemPriceTextView);
        TextView descriptionTV = rootView.findViewById(R.id.descriptionTV);
        ImageView itemImageIV = rootView.findViewById(R.id.itemImageIV);
        TextView productId = rootView.findViewById(R.id.productIdTextView);

        Bundle args = getArguments();
        if (args != null && args.containsKey("product")) {
            Product product = (Product) args.getSerializable("product");
            if (product != null) {
                productNameTV.setText(product.getName());
                priceTv.setText(product.getPrice());
                descriptionTV.setText(product.getDescription());
                url = product.getImage();
                productId.setText(product.getId());
                Glide.with(requireContext())
                        .load(url)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(itemImageIV);
            }
        }
        return rootView;
    }

    public Product getProductFromView() {
        View rootView = getView();
        TextView productNameTV = null;
        Product product = new Product();

        if (rootView != null) {
            productNameTV = rootView.findViewById(R.id.productNameTV);
            TextView priceTv = rootView.findViewById(R.id.itemPriceTextView);
            TextView productId = rootView.findViewById(R.id.productIdTextView);
            String name = productNameTV.getText().toString();
            String price = priceTv.getText().toString();
            String id = productId.getText().toString();

            product.setName(name);
            product.setPrice(price);
            product.setImage(url);
            product.setId(id);
            return product;
        }
        return product;
    }
}