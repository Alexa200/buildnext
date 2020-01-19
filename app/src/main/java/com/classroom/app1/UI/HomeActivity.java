package com.classroom.app1.UI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.classroom.app1.Helpers.DataStatus;
import com.classroom.app1.Helpers.Singleton;
import com.classroom.app1.Model.Builder;
import com.classroom.app1.Model.Categories;
import com.classroom.app1.Model.Product;
import com.classroom.app1.Model.Store;
import com.classroom.app1.R;
import com.classroom.app1.UI.Adapters.BuilderAdapter;
import com.classroom.app1.UI.Adapters.CategoriesAdapter;
import com.classroom.app1.UI.Adapters.ProductAdapter;
import com.classroom.app1.UI.Adapters.StoreAdapter;
import com.classroom.app1.UI.ClickListeners.RecyclerViewClickListener;
import com.classroom.app1.UI.ClickListeners.RecyclerViewClickListenerBuilder;
import com.classroom.app1.UI.ClickListeners.RecyclerViewClickListenerProduct;
import com.classroom.app1.UI.ClickListeners.RecyclerViewClickListenerStore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "data";
    private RecyclerView recyclerView, recyclerView_products, recyclerView_builders, recyclerView_stores;
    private CategoriesAdapter adapter;
    private ProductAdapter products_adapter;
    private BuilderAdapter builders_adapter;
    private StoreAdapter stores_adapter;
    private ArrayList<Categories> catList2 = new ArrayList<>();
    private ArrayList<Product> productsList = new ArrayList<>();
    private ArrayList<Store> storesList = new ArrayList<>();
    private ArrayList<Builder> buildersList = new ArrayList<>();
    private FirebaseFirestore db = Singleton.getDb();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);


        FirebaseApp.initializeApp(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.categories_rc);
        recyclerView_products = findViewById(R.id.products_rc);

        BottomNavigationView mBottomNav = findViewById(R.id.navigation_home);
        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home_button:
                        Intent home = new Intent(getApplicationContext(), UsersOrders.class);
                        startActivity(home);
                        break;
                    case R.id.navigation_cart:
                        Intent cart = new Intent(getApplicationContext(), Cart.class);
                        startActivity(cart);
                        break;
                    case R.id.profil_button:
                        Intent profil = new Intent(getApplicationContext(), Profil.class);
                        startActivity(profil);
                        break;
                    case R.id.builder_button:
                        Toast.makeText(getApplicationContext(),"Feature Coming Soon! Message from UZAIR",Toast.LENGTH_SHORT).show();
//                        Intent builder = new Intent(getApplicationContext(), Builder.class);
//                        startActivity(builder);
                    case R.id.store_button:
                        Toast.makeText(getApplicationContext(),"Feature Coming Soon! Message from UZAIR",Toast.LENGTH_SHORT).show();
//                        Intent store = new Intent(getApplicationContext(), Store.class);
//                        startActivity(store);
                }
                return true;
            }
        });

        init();
        fetchData();
        fetchProducts();
        fetchBuilders();
        fetchStores();


    }

    private void fetchProducts() {
        getProducts(new DataStatus() {
            @Override
            public void onSuccess(ArrayList products) {
                if (products_adapter == null) {
                    products_adapter = new ProductAdapter(productsList, HomeActivity.this, new RecyclerViewClickListenerProduct() {
                                @Override
                                public void onClick(View view, Product product) {
                                    Intent productPage = new Intent(HomeActivity.this, ProductActivity.class);
                                    productPage.putExtra("nomItem", product.getNom());
                                    productPage.putExtra("descItem", product.getDescription());
                                    productPage.putExtra("idItem", product.getId_product());
                                    productPage.putExtra("imgItem", product.getImg());
                                    productPage.putExtra("priceItem", product.getPrice());
                                    productPage.putExtra("idSeller", product.getId_seller());
                                    productPage.putExtra("idCat", product.getId_cat());
                                    startActivity(productPage);
                                }
                            });
                    recyclerView_products.setAdapter(products_adapter);
                } else {
                    products_adapter.getItems().clear();
                    products_adapter.getItems().addAll(productsList);
                    products_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String e) {
                Toast.makeText(HomeActivity.this, e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //
    private void fetchBuilders() {
        getBuilders(new DataStatus() {
            @Override
            public void onSuccess(ArrayList builders) {
                if (builders_adapter == null) {
                    builders_adapter = new BuilderAdapter(buildersList, HomeActivity.this, new RecyclerViewClickListenerBuilder() {
                        @Override
                        public void onClick(View view, Builder builder) {
                            Intent builderPage = new Intent(HomeActivity.this, BuilderActivity.class);
                            builderPage.putExtra("Name", builder.getName());
                            builderPage.putExtra("Address", builder.getAddress());
                            builderPage.putExtra("Description", builder.getDescription());
                            builderPage.putExtra("Phone",builder.getPhone());
                            startActivity(builderPage);
                        }
                    });
//                    recyclerView_builders.setAdapter(builders_adapter);
                } else {
                    builders_adapter.getItems().clear();
                    builders_adapter.getItems().addAll(buildersList);
                    builders_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String e) {
                Toast.makeText(HomeActivity.this, e, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //
    private void fetchStores() {
        getStores(new DataStatus() {
            @Override
            public void onSuccess(ArrayList stores) {
                if (stores_adapter == null) {
                    stores_adapter = new StoreAdapter(storesList, HomeActivity.this, new RecyclerViewClickListenerStore() {
                        @Override
                        public void onClick(View view, Store store) {
                            Intent storePage = new Intent(HomeActivity.this, StoreActivity.class);
                            storePage.putExtra("Name", store.getName());
                            storePage.putExtra("Address", store.getAddress());
                            storePage.putExtra("Description", store.getDescription());
                            storePage.putExtra("Phone", store.getPhone());
                            startActivity(storePage);
                        }
                    });
                   // recyclerView_stores.setAdapter(stores_adapter);
                } else {
                    stores_adapter.getItems().clear();
                    stores_adapter.getItems().addAll(storesList);
                    stores_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String e) {
                Toast.makeText(HomeActivity.this, e, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //

    private void fetchData() {
        getCategories(new DataStatus() {
            @Override
            public void onSuccess(ArrayList categories) {
                if (adapter == null) {
                    adapter = new CategoriesAdapter(categories, HomeActivity.this,
                            new RecyclerViewClickListener() {
                                @Override
                                public void onClick(View view, Categories categories) {
                                    Intent productPage = new Intent(HomeActivity.this, ProductsListActivity.class);
                                    productPage.putExtra("catName", categories.getId_cat());
                                    productPage.putExtra("isSeller", false);
                                    startActivity(productPage);
                                    //overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);

                                }
                            });
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.getItems().clear();
                    adapter.getItems().addAll(categories);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String e) {
                Toast.makeText(HomeActivity.this, e, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getCategories(final DataStatus callback) {

        //final ArrayList<Categories> catList = new ArrayList<>();

        db.collection("categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Map myMap = document.getData();
                                Log.v("dataFire", (String) myMap.toString());

                                // List of categories here
                                Categories categorie = new Categories(document.getId(), (String) myMap.get("name"), (String) myMap.get("icon"), (String) myMap.get("color"));
                                catList2.add(categorie);
                            }
                            callback.onSuccess(catList2);

                        } else {
                            callback.onError("Error in data");
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }
                });
    }

    private void getProducts(final DataStatus callback) {

        db.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.v("dataFireSec",document.getId()+" "+document.get("img"));

                                ArrayList<String> imgs = new ArrayList<>();
                                Map<String, Object> myMap = document.getData();
                                for (Map.Entry<String, Object> entry : myMap.entrySet()) {
                                    if (entry.getKey().equals("img")) {
                                        for (Object s : (ArrayList) entry.getValue()) {
                                            imgs.add((String) s);
                                        }
                                        Log.v("TagImg", entry.getValue().toString());

                                    }
                                }

                                Product product = new Product(document.getId(),
                                        (String) myMap.get("id_seller"),
                                        (String) myMap.get("id_cat"),
                                        (String) myMap.get("nom"),
                                        (String) myMap.get("description"),
                                        imgs,
                                        (Double) myMap.get("price"));
                                productsList.add(product);
                            }
                            callback.onSuccess(productsList);

                        } else {
                            callback.onError("Error in data");
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }
                });
    }

    //

    private void getStores(final DataStatus callback) {

        db.collection("Store")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.v("dataFireSec",document.getId()+" "+document.get("img"));

                                Map<String, Object> myMap = document.getData();
                                Store store = new Store(document.getId(),
                                        (String) myMap.get("Name"),
                                        (String) myMap.get("Description"),
                                        (String) myMap.get("Address"),
                                        (String) myMap.get("Phone"));
                                storesList.add(store);
                            }
                            callback.onSuccess(storesList);

                        } else {
                            callback.onError("Error in data");
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }
                });
    }
    //

    private void getBuilders(final DataStatus callback) {

        db.collection("Builder")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.v("dataFireSec",document.getId()+" "+document.get("img"));

                                Map<String, Object> myMap = document.getData();
                                Builder builder = new Builder(document.getId(),
                                        (String) myMap.get("Name"),
                                        (String) myMap.get("Description"),
                                        (String) myMap.get("Address"),
                                        (String) myMap.get("Phone"));
                                buildersList.add(builder);
                            }
                            callback.onSuccess(buildersList);

                        } else {
                            callback.onError("Error in data");
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                    }
                });
    }

    //

    private void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);

        recyclerView_products.setLayoutManager(gridLayoutManager);
        recyclerView_products.setNestedScrollingEnabled(false);
    }


}
